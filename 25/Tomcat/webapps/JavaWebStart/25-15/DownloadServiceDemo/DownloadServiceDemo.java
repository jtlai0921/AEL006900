import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.border.*;
import javax.swing.event.*;

import java.io.*;
import java.net.*;
import java.util.*;

// JNLP API
import javax.jnlp.*;

public class DownloadServiceDemo extends javax.swing.JFrame {

  JToolBar jtoolbar = new JToolBar();
  JButton jbutton[] = new JButton[2];
  
  // JNLP Service
  private DownloadService ds = null;
  
  private ClassLoader cl;    
  private String item[]  = {"Load", "About"};
  private String image[] = {"load", "about"};
   
  // Main method
  public static void main(String[] args) {
    UIManager.put("swing.boldMetal", Boolean.FALSE);
    JDialog.setDefaultLookAndFeelDecorated(true);
    JFrame.setDefaultLookAndFeelDecorated(true);
    Toolkit.getDefaultToolkit().setDynamicLayout(true);

    try {
      UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    new DownloadServiceDemo();
  }

  // 建構函式
  public DownloadServiceDemo() {
    super("JNLP DownloadService");

    // Get current classloader
    cl = this.getClass().getClassLoader(); 

    Container contentPane = getContentPane();
    contentPane.setLayout(new BorderLayout());

    // 建立選單列
    JMenuBar jmenubar = new JMenuBar();
    // 定義視窗使用者介面之選單列
    this.setJMenuBar(jmenubar);

    // 建立選單
    JMenu jmenu1 = new JMenu("File");
    jmenu1.setMnemonic('F');
    jmenubar.add(jmenu1);

    // 建立選單項目並使用選單快速鍵
    // Load
    JMenuItem jmnuLoad = new JMenuItem(item[0], KeyEvent.VK_L);
    jmnuLoad.setIcon(new ImageIcon(cl.getResource("images/" + image[0] + ".gif")));
    KeyStroke keystroke = KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK);
    jmnuLoad.setAccelerator(keystroke);
    jmenu1.add(jmnuLoad);
    jmnuLoad.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        menu_actionPerformed(e);
      }
    });

    // 新增分隔線
    jmenu1.addSeparator();

    // Exit
    JMenuItem jmnuExit = new JMenuItem("Exit");
    jmnuExit.setMnemonic('X');
    jmnuExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
    jmenu1.add(jmnuExit);
    jmnuExit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JOptionPane joptionpane = new JOptionPane();
        int iResult = joptionpane.showConfirmDialog(null, "Are you sure to quit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (iResult == 0) {
          dispose();
          System.exit(0);
        }
      }
    });

    // 建立選單
    // Help
    JMenu jmenu2 = new JMenu("Help");
    jmenu2.setMnemonic('H');
    jmenubar.add(jmenu2);

    // About
    JMenuItem jmnuAbout = new JMenuItem(item[1], new ImageIcon(cl.getResource("images/" + image[1] + ".gif")));
    jmnuAbout.setMnemonic('A');
    jmenu2.add(jmnuAbout);
    jmnuAbout.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        menu_actionPerformed(e);
      }
    });

    for (int i=0; i<image.length; i++) {
      jbutton[i] = new JButton(new ImageIcon(cl.getResource("images/" + image[i] + ".gif")));
      jbutton[i].setPreferredSize(new Dimension(32, 32));
      jbutton[i].setToolTipText(item[i]);
      
      // 註冊 ActionListener
      jbutton[i].addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          button_actionPerformed(e);
        }
      });

      jtoolbar.add(jbutton[i]);
      
      if (i==1)
        jtoolbar.addSeparator();
    }

    contentPane.add(jtoolbar, BorderLayout.NORTH);

    this.validate();
    this.setSize(new Dimension(300, 150));

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    this.setLocation((screenSize.width - frameSize.width) / 2, 
      (screenSize.height - frameSize.height) / 2);

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }

  private void loadCache() {
    if (ds == null) {
      try {
        // 查詢DownloadService服務
        ds = (DownloadService)ServiceManager.lookup("javax.jnlp.DownloadService");
      } 
      catch(UnavailableServiceException e) {
        // 無法查詢到指定服務，回傳UnavailableServiceException
        ds = null;
      }
    }

    if (ds != null) {
      try {
        String urlname = JOptionPane.showInputDialog(null, "Please input the URL for JNLP cache:", "Load JNLP Cache", JOptionPane.QUESTION_MESSAGE);

        URL url = new URL(urlname); 
        
        // 檢查指定的資源是否已建立快取
        if (ds.isResourceCached(url, null)) { 
          // 移除指定的資源
          ds.removeResource(url, null); 
        }
        // 顯示預設的下載進度視窗 
        DownloadServiceListener dsl = ds.getDefaultProgressWindow(); 
        
        // 下載指定的資源
        ds.loadResource(url, null, dsl); 

        JOptionPane.showMessageDialog(null, "The resource cache, " + urlname + ", has been loaded.", "Load JNLP Cache", JOptionPane.INFORMATION_MESSAGE);
      } 
      catch(Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  private void menu_actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals(item[0])) { // Load
      loadCache();
    }
    else if (e.getActionCommand().equals(item[1])) { // about
      JOptionPane.showMessageDialog(null, "JNLP DownloadService", "About", JOptionPane.INFORMATION_MESSAGE);
    }
  }

  private void button_actionPerformed(ActionEvent e) {
    if (e.getSource().equals(jbutton[0])) { // Load
      loadCache();
    }
    else if (e.getSource().equals(jbutton[1])) { // about
      JOptionPane.showMessageDialog(null, "JNLP DownloadService", "About", JOptionPane.INFORMATION_MESSAGE);
    }
  }
}
