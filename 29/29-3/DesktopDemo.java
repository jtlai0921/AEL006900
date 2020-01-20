import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.text.html.*;

import java.net.*;
import java.io.*;
import java.util.*;

// JDIC Desktop
import org.jdesktop.jdic.desktop.*;

public class DesktopDemo extends JFrame {

  org.jdesktop.jdic.desktop.Desktop desktop;

  public static enum Action {OPEN, EDIT, PRINT};

  // Mail
  //JTextField txtFrom = new JTextField();
  JTextField txtTo = new JTextField();
  JTextField txtCc = new JTextField();
  JTextField txtBcc = new JTextField();
  JTextField txtSubject = new JTextField();
  JEditorPane txtMessage = new JEditorPane();
  JComboBox cboAttachment = new JComboBox();

  JButton jbutton[];

  String items[] ={"Open", "Edit", "Print", "Browse", "Attach File", "Detach File", "Send Mail", "About"};
  String images[]={"open.gif", "edit.gif", "print.gif", "web.gif", "attach.gif", "detach.gif", "send.gif", "about.gif"};

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
    super("JDIC Desktop Demo");

    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 設定視窗圖示
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // 定義Layout Manager 為 BorderLayout
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
        fileAction(DesktopDemo.Action.OPEN);
      }
    });

    // Edit
    JMenuItem jmenuEdit = new JMenuItem("Edit", new ImageIcon(cl.getResource("images/edit.gif")));
    jmenuEdit.setMnemonic('E');
    jmenuEdit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
    // 註冊 ActionListener
    jmenuEdit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        fileAction(DesktopDemo.Action.EDIT);
      }
    });

    // Print
    JMenuItem jmenuPrint = new JMenuItem("Print", new ImageIcon(cl.getResource("images/print.gif")));
    jmenuPrint.setMnemonic('P');
    jmenuPrint.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
    jmenuPrint.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        fileAction(DesktopDemo.Action.PRINT);
      }
    });

    // Browse
    JMenuItem jmenuBrowse = new JMenuItem("Browse", new ImageIcon(cl.getResource("images/web.gif")));
    jmenuBrowse.setMnemonic('B');
    jmenuBrowse.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));
    jmenuBrowse.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        browseAction();
      }
    });

    // Mail
    JMenuItem jmenuMail = new JMenuItem("Mail", new ImageIcon(cl.getResource("images/send.gif")));
    jmenuMail.setMnemonic('M');
    jmenuMail.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
    // 註冊 ActionListener
    jmenuMail.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        mailAction();
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
    jmenuFile.add(jmenuPrint);
    jmenuFile.add(jmenuBrowse);
    jmenuFile.add(jmenuMail);
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
        JOptionPane.showMessageDialog(null, "JDIC Desktop Demo", "About", JOptionPane.INFORMATION_MESSAGE);
      }
    });

    // Toolbar
    JToolBar jtoolbar = new JToolBar();
    jtoolbar.setRollover(true);

    jbutton = new JButton[images.length];

    cboAttachment.setToolTipText("Attachment");
    cboAttachment.setPreferredSize(new Dimension(120, 32));
    cboAttachment.setMaximumSize(new Dimension(120, 32));
    // 設定下拉JComboBox類別時，最多可顯示的項目列數
    cboAttachment.setMaximumRowCount(5);

    for (int i=0; i<images.length; i++) {
      jbutton[i] = new JButton(new ImageIcon(cl.getResource("images/" + images[i])));
      jbutton[i].setMaximumSize(new Dimension(32, 32));
      jbutton[i].setMinimumSize(new Dimension(32, 32));
      jbutton[i].setPreferredSize(new Dimension(32, 32));
      jbutton[i].setToolTipText(items[i]);
      jbutton[i].addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          button_actionPerformed(e);
        }
      });

      jtoolbar.add(jbutton[i]);
      
      if (i==3 || i==6)
        jtoolbar.addSeparator();
      
      if (i==5) {
        jtoolbar.addSeparator();
        jtoolbar.add(cboAttachment);
        jtoolbar.addSeparator();
      }
    }

    jbutton[5].setEnabled(false);  // Detach File

    JPanel Panel1 = new JPanel();
    Panel1.setLayout(new BorderLayout());
    Panel1.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
    
    JPanel Panel2 = new JPanel();
    Panel2.setLayout(new BorderLayout());
    Panel2.setBorder(BorderFactory.createEtchedBorder());
    Panel2.setMinimumSize(new Dimension(10, 120));
    Panel2.setPreferredSize(new Dimension(10, 120));

    GridLayout gridLayout1 = new GridLayout();
    gridLayout1.setRows(4);
    gridLayout1.setColumns(1);
    gridLayout1.setHgap(2);
    gridLayout1.setVgap(4);

    JPanel Panel3 = new JPanel();
    Panel3.setLayout(gridLayout1);
    Panel3.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));

    JPanel Panel4 = new JPanel();
    Panel4.setLayout(gridLayout1);
    Panel4.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));

    JPanel Panel5 = new JPanel();
    Panel5.setBorder(BorderFactory.createEtchedBorder());
    Panel5.setLayout(new FlowLayout());
    Panel5.setMinimumSize(new Dimension(10, 120));
    Panel5.setPreferredSize(new Dimension(10, 120));

    JPanel Panel6 = new JPanel();
    Panel6.setLayout(new BorderLayout());
    
    //Panel3.add(new JLabel("From:"));
    Panel3.add(new JLabel("To:"));
    Panel3.add(new JLabel("Cc:"));
    Panel3.add(new JLabel("Bcc:"));
    Panel3.add(new JLabel("Subject:"));
    Panel2.add(Panel3, BorderLayout.WEST);

    //Panel4.add(txtFrom);
    Panel4.add(txtTo);
    Panel4.add(txtCc);
    Panel4.add(txtBcc);
    Panel4.add(txtSubject);
    Panel2.add(Panel4, BorderLayout.CENTER);

    JScrollPane jsp1 = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    jsp1.getViewport().add(txtMessage);

    Panel1.add(Panel2, BorderLayout.NORTH);
    Panel1.add(jsp1, BorderLayout.CENTER);  

    this.add(jtoolbar, BorderLayout.NORTH);
    this.add(Panel1, BorderLayout.CENTER);

    this.validate();
    this.setSize(new Dimension(450, 400));

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

  public void button_actionPerformed(ActionEvent e){
    if(e.getSource() == jbutton[0]){  // Open
      fileAction(DesktopDemo.Action.OPEN);
    }
    else if(e.getSource() == jbutton[1]){  // Edit 
      fileAction(DesktopDemo.Action.EDIT);
    }
    else if(e.getSource() == jbutton[2]){  // Print 
      fileAction(DesktopDemo.Action.PRINT);
    }
    else if(e.getSource() == jbutton[3]){  // Browse 
      browseAction();
    }
    else if(e.getSource() == jbutton[4]){  // Attach File
      Thread thread = new Thread() {
        public void run() {
          attachFile();
        }
      };
      thread.start();
    }
    else if(e.getSource() == jbutton[5]){  // Detach File
      Thread thread = new Thread() {
        public void run() {
          detachFile();
        }
      };
      thread.start();
    }
    else if(e.getSource() == jbutton[6]){  // Send Mail
      mailAction();
    }
    else if(e.getSource() == jbutton[7]){  // About
      JOptionPane.showMessageDialog(null, "JDIC Desktop Demo", "About", JOptionPane.INFORMATION_MESSAGE);
    }
  }

  private void fileAction(DesktopDemo.Action action) {
    JFileChooser jfileChooser = new JFileChooser();
    jfileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
    jfileChooser.setDialogTitle("Open file ...");
    
    if (jfileChooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) 
      return;

    File fileName = new File(jfileChooser.getSelectedFile().toString());

    try {
      switch(action) {
        case OPEN:
          // 開啟檔案
          org.jdesktop.jdic.desktop.Desktop.open(fileName);
          break;
        case EDIT:
          // 編輯檔案
          org.jdesktop.jdic.desktop.Desktop.edit(fileName);
          break;
        case PRINT:
          // 列印檔案
          org.jdesktop.jdic.desktop.Desktop.print(fileName);
          break;
      }
    }
    catch(DesktopException ex) {
      ex.printStackTrace();
    }
  }

  private void mailAction() {
    //String from    = txtFrom.getText();
    String to      = txtTo.getText();
    String cc      = txtCc.getText();
    String bcc     = txtBcc.getText();
    String subject = txtSubject.getText();
    String message = txtMessage.getText();

    if (to.equals("")) {
      try {
        // 執行作業系統預設郵件工具
        org.jdesktop.jdic.desktop.Desktop.mail();
      } 
      catch(DesktopException dex) {
        dex.printStackTrace();
      }
    }
    else {
      try {
        // 建立Message物件
        org.jdesktop.jdic.desktop.Message msg = new org.jdesktop.jdic.desktop.Message();
  
        if (!to.equals("")) {
          // Regular Expression 
          String[] toResult = to.split(",");
          
          // 泛型Generics
          Vector<Object> toList = new Vector<Object>();
          
          for (int i=0; i<toResult.length; i++)
            toList.add(toResult[i].trim());
  
          // 設定收件者 (To) 郵件地址
          msg.setToAddrs(toList); 
        }
  
        if (!cc.equals("")) {
          // Regular Expression 
          String[] ccResult = cc.split(",");
          
          // 泛型Generics
          Vector<Object> ccList = new Vector<Object>();
          
          for (int i=0; i<ccResult.length; i++)
            ccList.add(ccResult[i].trim());
  
          // 設定副本收件者 (Cc) 郵件地址
          msg.setCcAddrs(ccList); 
        }
  
        if (!bcc.equals("")) {
          // Regular Expression 
          String[] bccResult = bcc.split(",");
          
          // 泛型Generics
          Vector<Object> bccList = new Vector<Object>();
          
          for (int i=0; i<bccResult.length; i++)
            bccList.add(bccResult[i].trim());
  
          // 設定密件副本收件者 (Bcc) 郵件地址
          msg.setBccAddrs(bccList); 
        }
  
        if (!subject.equals("")) {
          // 設定郵件主旨
          msg.setSubject(subject); 
        }
  
        if (!message.equals("")) {
          // 設定郵件內文
          msg.setBody(message); 
        }
  
        // 處理附件
        if (cboAttachment.getItemCount() > 0) {
          // 泛型Generics
          Vector<Object> attachList = new Vector<Object>();
    
          for (int i=0; i<cboAttachment.getItemCount(); i++) {
             attachList.add((String)cboAttachment.getItemAt(i));
          }
          // 設定檔案附件
          msg.setAttachments(attachList);
        }
  
        // 執行作業系統預設郵件工具
        org.jdesktop.jdic.desktop.Desktop.mail(msg);
      } 
      catch(DesktopException dex) {
        dex.printStackTrace();
      }
      catch(Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  private void browseAction() {
    String urlString = JOptionPane.showInputDialog(null, "請輸入URL", "JDIC Desktop", JOptionPane.QUESTION_MESSAGE);

    try {
      if (urlString.length() > 0) {
        if (!urlString.startsWith("http://") && !urlString.startsWith("file:/")) {
          if (urlString.indexOf(':') == 1) {
            urlString = "file:/" + urlString;
          }
          else {
            urlString = "http://" + urlString;
          }
        }

        // 以預設瀏覽器執行URL
        org.jdesktop.jdic.desktop.Desktop.browse(new URL(urlString));
      }
    }
    catch(DesktopException ex) {
      ex.printStackTrace();
    }
    catch (MalformedURLException muex) {
      muex.printStackTrace();
    }
  }

  private void attachFile() {
    JFileChooser jfileChooser = new JFileChooser();
    jfileChooser.setCurrentDirectory(new File("."));
    jfileChooser.setMultiSelectionEnabled(false);

    jfileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
    jfileChooser.setDialogTitle("Attach File ...");
    
    if (jfileChooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) 
      return;

    this.repaint();

    File attachment = jfileChooser.getSelectedFile();

    cboAttachment.addItem(attachment.getAbsolutePath());

    // 選取選項項目
    cboAttachment.setSelectedIndex(cboAttachment.getItemCount()-1);
    
    jbutton[5].setEnabled(true);
  }

  private void detachFile() {
    if (cboAttachment.getItemCount() > 0) {
      int i = cboAttachment.getSelectedIndex();
      cboAttachment.removeItemAt(i);
    }
    
    if (cboAttachment.getItemCount() == 0) {
      jbutton[5].setEnabled(false);
    }
  }
}
