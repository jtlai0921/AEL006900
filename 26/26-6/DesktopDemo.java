import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.event.*;

import java.awt.Desktop;

import java.net.*;
import java.io.*;

public class DesktopDemo extends JFrame {

  java.awt.Desktop desktop;

  JTextField txtURL = new JTextField();

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

    new DesktopDemo();
  }

  // 建構函式
  public DesktopDemo() {
    super("Desktop Demo");

    // 若作業系統支援Desktop類別之功能
    if (Desktop.isDesktopSupported()) {
      // 取得Desktop物件
      desktop = Desktop.getDesktop();
    }

    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 設定視窗圖示
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // 定義Layout Manager 為 FlowLayout
    this.setLayout(new BorderLayout());

    JLabel jlabel1 = new JLabel("URL:");

    // 建立選單列
    JMenuBar jmenubar = new JMenuBar();
    // 定義視窗使用者介面之選單列
    this.setJMenuBar(jmenubar);

    // 建立選單
    JMenu jmenuFile = new JMenu("File");
    jmenuFile.setMnemonic('F');
    jmenubar.add(jmenuFile);

    // 建立選單項目
    // Open
    JMenuItem jmenuOpen = new JMenuItem("Open", new ImageIcon(cl.getResource("images/open.gif")));
    jmenuOpen.setMnemonic('O');
    jmenuOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
    // 註冊 ActionListener
    jmenuOpen.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        fileAction(java.awt.Desktop.Action.OPEN);
      }
    });

    // Edit
    JMenuItem jmenuEdit = new JMenuItem("Edit", new ImageIcon(cl.getResource("images/edit.gif")));
    jmenuEdit.setMnemonic('E');
    jmenuEdit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
    // 註冊 ActionListener
    jmenuEdit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        fileAction(java.awt.Desktop.Action.EDIT);
      }
    });

    // Mail
    JMenuItem jmenuMail = new JMenuItem("Mail", new ImageIcon(cl.getResource("images/mail.gif")));
    jmenuMail.setMnemonic('M');
    jmenuMail.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
    // 註冊 ActionListener
    jmenuMail.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        mailAction();
      }
    });

    // Print
    JMenuItem jmenuPrint = new JMenuItem("Print", new ImageIcon(cl.getResource("images/print.gif")));
    jmenuPrint.setMnemonic('P');
    jmenuPrint.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
    jmenuPrint.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        fileAction(java.awt.Desktop.Action.PRINT);
      }
    });

    // Exit
    JMenuItem jmenuExit = new JMenuItem();
    jmenuExit.setText("Exit");
    jmenuExit.setMnemonic('X');
    jmenuExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
    // 註冊 ActionListener
    jmenuExit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JOptionPane joptionpane = new JOptionPane();
        int iResult = joptionpane.showConfirmDialog(null, "Are you sure to quit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (iResult == 0) {
          System.exit(0);
        }
      }
    });

    jmenuFile.add(jmenuOpen);
    jmenuFile.add(jmenuEdit);
    jmenuFile.addSeparator();
    jmenuFile.add(jmenuMail);
    jmenuFile.addSeparator();
    jmenuFile.add(jmenuPrint);
    jmenuFile.addSeparator();
    jmenuFile.add(jmenuExit);

    // 建立選單
    // Help
    JMenu jmenuHelp = new JMenu("Help");
    jmenuHelp.setMnemonic('H');
    jmenubar.add(jmenuHelp);

    // About
    JMenuItem jmenuAbout = new JMenuItem("About", new ImageIcon(cl.getResource("images/about.gif")));
    jmenuAbout.setMnemonic('A');
    jmenuHelp.add(jmenuAbout);
    jmenuAbout.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "Java Desktop Demo", "About", JOptionPane.INFORMATION_MESSAGE);
      }
    });

    txtURL.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        browseAction();
      }
    });

    JPanel Panel1 = new JPanel();
    Panel1.setLayout(new BorderLayout());
    Panel1.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0));

    JPanel Panel2 = new JPanel();
    Panel2.setLayout(new BorderLayout());
    Panel2.setPreferredSize(new Dimension(10, 40));
    Panel2.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(), BorderFactory.createEmptyBorder(2, 0, 2, 0)));

    JPanel Panel3 = new JPanel();
    Panel3.setLayout(new BorderLayout());
    Panel3.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));
    Panel3.setPreferredSize(new Dimension(40, 10));

    JPanel Panel4 = new JPanel();
    Panel4.setLayout(new BorderLayout());
    Panel4.setBorder(BorderFactory.createEmptyBorder(5,0,5,5));

    Panel4.add(txtURL,  BorderLayout.CENTER);
    Panel3.add(jlabel1, BorderLayout.CENTER);
    Panel2.add(Panel4,  BorderLayout.CENTER);
    Panel2.add(Panel3,  BorderLayout.WEST);
    Panel1.add(Panel2,  BorderLayout.NORTH);
    this.add(Panel1, BorderLayout.CENTER);

    this.validate();
    this.setSize(new Dimension(350, 250));

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(true);
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });    
  }

  private void fileAction(Desktop.Action action) {
    JFileChooser jfileChooser = new JFileChooser();
    jfileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
    jfileChooser.setDialogTitle("Open file ...");
    
    if (jfileChooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) 
      return;

    File fileName = new File(jfileChooser.getSelectedFile().toString());

    try {
      switch(action) {
        case OPEN:
          desktop.open(fileName);
          break;
        case EDIT:
          desktop.edit(fileName);
          break;
        case PRINT:
          desktop.print(fileName);
          break;
      }
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  private void mailAction() {
    String mailto = JOptionPane.showInputDialog(null, "Input \"mailto:\" URI:", "Desktop Demo", JOptionPane.QUESTION_MESSAGE);

    URI uriMailTo = null;

    try {
      if (mailto != null) {
        uriMailTo = new URI("mailto", mailto, null);
        desktop.mail(uriMailTo);
      } 
      else {
        desktop.mail();
      }
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  private void browseAction() {
    URI uri = null;

    try {
      uri = new URI(txtURL.getText());
      desktop.browse(uri);
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }
}
