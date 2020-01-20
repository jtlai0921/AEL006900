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

public class FileSaveServiceDemo extends javax.swing.JFrame {

  JToolBar jtoolbar = new JToolBar();
  JButton jbutton[] = new JButton[10];
  JTextField txtURL = new JTextField();
  JTextPane txtContent = new JTextPane();
  
  // JNLP Service
  private FileContents fc = null;
  private FileOpenService fos = null;
  private FileSaveService fss = null;
  
  private ClassLoader cl;    
  private String item[]  = {"New", "Open", "Save", "Save As", "Print", "Page Setup", "Cut", "Copy", "Paste", "About"};
  private String image[] = {"new", "open", "save", "saveas",  "print", "pagesetup",  "cut", "copy", "paste", "about"};
   
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

    new FileSaveServiceDemo();
  }

  // 建構函式
  public FileSaveServiceDemo() {
    super("JNLP FileSaveService");

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
    // New
    JMenuItem jmnuNew = new JMenuItem(item[0], KeyEvent.VK_N);
    jmnuNew.setIcon(new ImageIcon(cl.getResource("images/" + image[0] + ".gif")));
    KeyStroke keystroke = KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK);
    jmnuNew.setAccelerator(keystroke);
    jmenu1.add(jmnuNew);
    jmnuNew.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        menu_actionPerformed(e);
      }
    });

    // Open
    JMenuItem jmnuOpen = new JMenuItem(item[1], new ImageIcon(cl.getResource("images/" + image[1] + ".gif")));
    jmnuOpen.setMnemonic(KeyEvent.VK_O);
    jmnuOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
    jmenu1.add(jmnuOpen);
    jmnuOpen.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        menu_actionPerformed(e);
      }
    });

    // 新增分隔線
    jmenu1.addSeparator();

    // Save
    JMenuItem jmnuSave = new JMenuItem(item[2], new ImageIcon(cl.getResource("images/" + image[2] + ".gif")));
    jmnuSave.setMnemonic('S');
    jmnuSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
    jmenu1.add(jmnuSave);
    jmnuSave.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        menu_actionPerformed(e);
      }
    });

    // Save As
    JMenuItem jmnuSaveAs = new JMenuItem(item[3], new ImageIcon(cl.getResource("images/" + image[3] + ".gif")));
    jmnuSaveAs.setMnemonic('A');
    jmnuSaveAs.setDisplayedMnemonicIndex(5);
    jmenu1.add(jmnuSaveAs);
    jmnuSaveAs.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        menu_actionPerformed(e);
      }
    });

    jmenu1.addSeparator();
    
    // Print
    JMenuItem jmnuPrint = new JMenuItem(item[4], new ImageIcon(cl.getResource("images/" + image[4] + ".gif")));
    jmnuPrint.setMnemonic('P');
    jmnuPrint.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
    jmenu1.add(jmnuPrint);
    jmnuPrint.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        menu_actionPerformed(e);
      }
    });

    // Page Setup
    JMenuItem jmnuPage = new JMenuItem(item[5], new ImageIcon(cl.getResource("images/" + image[5] + ".gif")));
    jmnuPage.setMnemonic('u');
    jmnuPage.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK));
    jmenu1.add(jmnuPage);
    jmnuPage.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        menu_actionPerformed(e);
      }
    });

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
    // Edit
    JMenu jmenu2 = new JMenu("Edit");
    jmenu2.setMnemonic('E');
    jmenubar.add(jmenu2);

    // Cut
    JMenuItem jmnuCut = new JMenuItem(item[6], new ImageIcon(cl.getResource("images/" + image[6] + ".gif")));
    jmnuCut.setMnemonic('t');
    jmnuCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
    jmenu2.add(jmnuCut);
    jmnuCut.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        menu_actionPerformed(e);
      }
    });

    // Copy
    JMenuItem jmnuCopy = new JMenuItem(item[7], new ImageIcon(cl.getResource("images/" + image[7] + ".gif")));
    jmnuCopy.setMnemonic('C');
    jmnuCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
    jmenu2.add(jmnuCopy);
    jmnuCopy.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        menu_actionPerformed(e);
      }
    });

    // Paste
    JMenuItem jmnuPaste = new JMenuItem(item[8], new ImageIcon(cl.getResource("images/" + image[8] + ".gif")));
    jmnuPaste.setMnemonic('P');
    jmnuPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
    jmenu2.add(jmnuPaste);
    jmnuPaste.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        menu_actionPerformed(e);
      }
    });

    // 建立選單
    // Help
    JMenu jmenu3 = new JMenu("Help");
    jmenu3.setMnemonic('H');
    jmenubar.add(jmenu3);

    // About
    JMenuItem jmnuAbout = new JMenuItem(item[9], new ImageIcon(cl.getResource("images/" + image[9] + ".gif")));
    jmnuAbout.setMnemonic('A');
    jmenu3.add(jmnuAbout);
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
      
      if (i==3 || i==5 || i==8)
        jtoolbar.addSeparator();
    }

    jtoolbar.addSeparator();
    
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    String font[] = ge.getAvailableFontFamilyNames();
    
    JComboBox cboFont = new JComboBox();
    cboFont.setPreferredSize(new Dimension(120, 32));
    cboFont.setMaximumSize(new Dimension(120, 32));
    cboFont.setToolTipText("Font");

    for(int i=0; i < font.length; i++) 
      cboFont.addItem(font[i]);

    // 選取第anIndex個選項項目
    cboFont.setSelectedIndex(0);
    // 設定下拉JComboBox類別時，最多可顯示的項目列數
    cboFont.setMaximumRowCount(10);
    cboFont.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox)e.getSource();
        String fontName = (String)cb.getSelectedItem();
        txtContent.setFont(new Font(fontName, Font.PLAIN, 12));
      }
    });
    jtoolbar.add(cboFont);
    jtoolbar.addSeparator();

    // JTextPane    
    txtContent.setFont(new Font("Courier New", Font.PLAIN, 12));
    
    JScrollPane jscrollpane = new JScrollPane(txtContent, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    contentPane.add(jtoolbar,    BorderLayout.NORTH);
    contentPane.add(jscrollpane, BorderLayout.CENTER);

    this.validate();
    this.setSize(new Dimension(500, 300));

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

  private String open() {
    String str = null;
    
    if (fos == null) {
      try {
        // 查詢FileOpenService服務
        fos = (FileOpenService)ServiceManager.lookup("javax.jnlp.FileOpenService");
      } 
      catch(UnavailableServiceException e) {
        // 無法查詢到指定服務，回傳UnavailableServiceException
        fos = null;
      }
    }

    if (fos != null) {
      try {
        fc = fos.openFileDialog(null, null);
  
        if (fc == null) return null;
  
        BufferedReader br = new BufferedReader(new InputStreamReader(fc.getInputStream()));
        StringBuffer sb = new StringBuffer((int)fc.getLength());
        String line = br.readLine();
        // Line Separator
        String linesep = System.getProperty("line.separator");
    
        while(line != null) {
          sb.append(line + linesep);
          line = br.readLine();
        }
        br.close();

        str = sb.toString();
      } 
      catch(IOException ioe) {
        ioe.printStackTrace();
      }
    }

    return str;
  }

  private void save(String content) {
    if (fss == null) {
      try {
        // 查詢FileSaveService服務
        fss = (FileSaveService)ServiceManager.lookup("javax.jnlp.FileSaveService");
      } 
      catch(UnavailableServiceException e) {
        // 無法查詢到指定服務，回傳UnavailableServiceException
        fss = null;
      }
    }

    if (fss != null) {
      try {
        // 未指定檔案名稱
        if (fc == null) {
          fc = fss.saveFileDialog(null, null, new ByteArrayInputStream(content.getBytes()), null);
        }
        // 已指定檔案名稱
        else if (fc != null) {
          int size = content.length() * 2;
          
          if (size > fc.getMaxLength()) 
            fc.setMaxLength(size);
          
          BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fc.getOutputStream(true)));
          bw.write(content);
          bw.close();
        }
      } 
      catch(IOException ioe) {
        ioe.printStackTrace();
      }
    }
  }

  private void saveAs(String content) {
    if (fss == null) {
      try {
        // 查詢FileSaveService服務
        fss = (FileSaveService)ServiceManager.lookup("javax.jnlp.FileSaveService");
      } 
      catch(UnavailableServiceException e) {
        // 無法查詢到指定服務，回傳UnavailableServiceException
        fss = null;
      }
    }

    if (fss != null) {
      try {
        fc = fss.saveFileDialog(null, null, new ByteArrayInputStream(content.getBytes()), null);
      } 
      catch(IOException ioe) {
        ioe.printStackTrace();
      }
    }
  }

  private void menu_actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals(item[0])) { // new
      txtContent.setText("");
      fc = null;
    }
    else if (e.getActionCommand().equals(item[1])) { // open
      String str = open();

      if (str != null) 
        txtContent.setText(str);
    }
    else if (e.getActionCommand().equals(item[2])) { // save
      save(txtContent.getText());
    }
    else if (e.getActionCommand().equals(item[3])) { // save as
      saveAs(txtContent.getText());
    }
    else if (e.getActionCommand().equals(item[9])) { // about
      JOptionPane.showMessageDialog(null, "JNLP FileSaveService", "About", JOptionPane.INFORMATION_MESSAGE);
    }
  }

  private void button_actionPerformed(ActionEvent e) {
    if (e.getSource().equals(jbutton[0])) { // new
      txtContent.setText("");
      fc = null;
    }
    else if (e.getSource().equals(jbutton[1])) { // open
      String str = open();

      if (str != null) 
        txtContent.setText(str);
    }
    else if (e.getSource().equals(jbutton[2])) { // save
      save(txtContent.getText());
    }
    else if (e.getSource().equals(jbutton[3])) { // save as
      saveAs(txtContent.getText());
    }
    else if (e.getSource().equals(jbutton[9])) { // about
      JOptionPane.showMessageDialog(null, "JNLP FileSaveService", "About", JOptionPane.INFORMATION_MESSAGE);
    }
  }
}
