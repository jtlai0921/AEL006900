import java.awt.*;
import java.awt.event.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import java.util.*;
import java.io.*;
import java.text.*;

public class TreeExpansionEventDemo extends JFrame {
  // Main method
  public static void main(String[] args) {
    UIManager.put("swing.boldMetal", Boolean.FALSE);
    JDialog.setDefaultLookAndFeelDecorated(true);
    JFrame.setDefaultLookAndFeelDecorated(true);
    Toolkit.getDefaultToolkit().setDynamicLayout(true);

    try {
      UIManager.setLookAndFeel(new javax.swing.plaf.metal.MetalLookAndFeel());
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    new TreeExpansionEventDemo();
  }

  // 建構函式
  public TreeExpansionEventDemo() {
    this.setTitle("Tree Expansion Event Demo");
    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 設定視窗圖示
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // 定義 Layout Manager 為 BorderLayout
    this.setLayout(new BorderLayout());

    // 建立選單功能列
    JMenuBar jmenubar = new JMenuBar();
    // 定義視窗使用者介面之選單功能列
    this.setJMenuBar(jmenubar);

    // 建立選單
    JMenu jmenuFile = new JMenu("File");
    jmenuFile.setMnemonic('F');
    jmenubar.add(jmenuFile);

    // 建立選單項目
    JMenuItem jmenuExit = new JMenuItem();
    jmenuExit.setText("Exit");
    jmenuExit.setMnemonic('X');
    jmenuExit.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        JOptionPane joptionpane = new JOptionPane();
        int iResult = joptionpane.showConfirmDialog(null, "Are you sure to quit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (iResult == 0) {
          System.exit(0);
        }
      }
    });

    jmenuFile.add(jmenuExit);

    // 建立選單
    JMenu jmenuHelp = new JMenu("Help");
    jmenuHelp.setMnemonic('H');
    jmenubar.add(jmenuHelp);

    // 建立選單項目
    // About
    JMenuItem jmenuAbout = new JMenuItem("About");
    jmenuAbout.setMnemonic('A');
    jmenuAbout.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "Tree Expansion Event Demo", "About", JOptionPane.INFORMATION_MESSAGE);
      }
    });

    jmenuHelp.add(jmenuAbout);

    JPanel Panel1 = new JPanel();
    Panel1.setLayout(new BorderLayout());
    Panel1.setBorder(BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142)));
    Panel1.setPreferredSize(new Dimension(200, 10));

    Panel1.add(new FileTree(this), BorderLayout.CENTER);

    this.add(Panel1, BorderLayout.CENTER);

    this.validate();
    this.setSize(new Dimension(300, 300));

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
    this.setResizable(false);
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }
}
