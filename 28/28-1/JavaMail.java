import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.text.*;
import javax.swing.text.html.*;

// Print Job
import java.awt.print.*;

import java.util.*;
import java.io.*;

// JavaMail
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class JavaMail extends JFrame {
  JTextField txtTo = new JTextField();
  JTextField txtCc = new JTextField();
  JTextField txtBcc = new JTextField();
  JTextField txtSubject = new JTextField();
  JEditorPane txtMessage = new JEditorPane();

  JComboBox cboAttachment = new JComboBox();
  JComboBox cboMime = new JComboBox();

  static JComboBox cboFrom = new JComboBox();

  JButton jbutton[];
  JMenu jmenu[];
  JMenuItem jmenuItem[][] = new JMenuItem[4][8];
  JPopupMenu jpopupmenu = new JPopupMenu();

  String host, port, user, name, addr, pass;

  String mime[]={"text/plain", "text/html", "text/rtf"};
  
  String items[]={
      "Send Mail", "New...", "Open", "Save", "Print", 
      "Cut", "Copy", "Paste", "Attach File", "Detach File"};
  String images[]={
      "send.gif", "new.gif", "open.gif", "save.gif", "print.gif", 
      "cut.gif", "copy.gif", "paste.gif", "attach.gif", "detach.gif"};

  String menu[]={"File|F", "Edit|E", "Mail|M", "Help|H"};
  String menuItem[][]={
    {"New...|N|new.gif", "Open|O|open.gif", "-", "Save|S|save.gif", "-", "Print|P|print.gif", "-", "Exit|X"},
    {"Cut|T|cut.gif", "Copy|C|copy.gif", "Paste|P|paste.gif"},
    {"Profile|P", "-", "Attach File|A|attach.gif", "Detach File|D|detach.gif", "-", "Send Mail|D|send.gif"},
    {"About|A|about.gif"}
  };

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

    new JavaMail();
  }

  // 建構函式
  public JavaMail() {
    super("JavaMail SMTP Client");

    loadProperties();
    
    // Get current classloader
    ClassLoader cl = this.getClass().getClassLoader();
   
    this.setIconImage(new ImageIcon(cl.getResource("images/send.gif")).getImage());
    
    // 定義Layout Manager 為 BorderLayout
    this.setLayout(new BorderLayout());

    JToolBar jtoolbar = new JToolBar();
    jtoolbar.setRollover(true);

    int i, j;

    for (i=0; i<mime.length; i++) 
      cboMime.addItem(mime[i]);

    cboMime.setToolTipText("MIME Type");
    cboMime.setMaximumSize(new Dimension(80, 30));
    cboMime.setPreferredSize(new Dimension(80, 30));
    // 選取選項項目
    cboMime.setSelectedIndex(0);

    jbutton = new JButton[images.length];

    for (i=0; i<images.length; i++) {
      jbutton[i] = new JButton(new ImageIcon(cl.getResource("images/" + images[i])));
      jbutton[i].setMaximumSize(new Dimension(30, 30));
      jbutton[i].setMinimumSize(new Dimension(30, 30));
      jbutton[i].setPreferredSize(new Dimension(30, 30));
      jbutton[i].setToolTipText(items[i]);
      jbutton[i].addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          button_actionPerformed(e);
        }
      });

      jtoolbar.add(jbutton[i]);
      
      if (i==0 || i==4)
        jtoolbar.addSeparator();
      
      if (i==7) {
        jtoolbar.addSeparator();
        jtoolbar.add(cboMime);
        jtoolbar.addSeparator();
      }
    }

    jbutton[9].setEnabled(false);  // Detach

    cboAttachment.setToolTipText("Attachment");
    cboAttachment.setPreferredSize(new Dimension(120, 30));
    cboAttachment.setMaximumSize(new Dimension(120, 30));
    // 設定下拉JComboBox類別時，最多可顯示的項目列數
    cboAttachment.setMaximumRowCount(5);

    jtoolbar.add(cboAttachment);

    // 建立選單功能列
    JMenuBar jmenubar = new JMenuBar();
    // 定義視窗使用者介面之選單功能列
    this.setJMenuBar(jmenubar);

    jmenu = new JMenu[menu.length];
    
    for (i=0; i<menu.length; i++){
      jmenu[i] = new JMenu(menu[i].substring(0, menu[i].indexOf("|")));
      jmenu[i].setMnemonic(menu[i].split("\\|")[1].charAt(0));
      jmenubar.add(jmenu[i]);
    }

    for(i=0; i<menu.length; i++){
      for(j=0; j<menuItem[i].length; j++){
        if (menuItem[i][j].equals("-")) {
          jmenu[i].addSeparator();
        }
        else {
          jmenuItem[i][j] = new JMenuItem(menuItem[i][j].substring(0, menuItem[i][j].indexOf("|")));
          jmenuItem[i][j].setMnemonic(menuItem[i][j].split("\\|")[1].charAt(0));

          if (menuItem[i][j].endsWith(".gif")) 
            jmenuItem[i][j].setIcon(new ImageIcon(cl.getResource("images/" + menuItem[i][j].substring(menuItem[i][j].lastIndexOf("|")+1))));

          jmenuItem[i][j].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              menu_actionPerformed(e);
            }
          });

          jmenu[i].add(jmenuItem[i][j]);
        }
      }
    }

    jmenuItem[2][3].setEnabled(false);  // Detach

    // Cut (Popup Menu突顯式選單)
    JMenuItem jmnuCut1 = new JMenuItem(items[5], new ImageIcon(cl.getResource("images/" + images[5])));
    jmnuCut1.setMnemonic('t');
    jmnuCut1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
    jmnuCut1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        menu_actionPerformed(e);
      }
    });
    // Copy (Popup Menu突顯式選單)
    JMenuItem jmnuCopy1 = new JMenuItem(items[6], new ImageIcon(cl.getResource("images/" + images[6])));
    jmnuCopy1.setMnemonic('C');
    jmnuCopy1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
    jmnuCopy1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        menu_actionPerformed(e);
      }
    });
    // Paste (Popup Menu突顯式選單)
    JMenuItem jmnuPaste1 = new JMenuItem(items[7], new ImageIcon(cl.getResource("images/" + images[7])));
    jmnuPaste1.setMnemonic('P');
    jmnuPaste1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
    jmnuPaste1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        menu_actionPerformed(e);
      }
    });
    
    // 建立突顯式選單
    jpopupmenu.add(jmnuCut1);
    jpopupmenu.add(jmnuCopy1);
    jpopupmenu.add(jmnuPaste1);

    JPanel Panel1 = new JPanel();
    Panel1.setLayout(new BorderLayout());
    Panel1.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
    
    JPanel Panel2 = new JPanel();
    Panel2.setLayout(new BorderLayout());
    Panel2.setBorder(BorderFactory.createEtchedBorder());
    Panel2.setMinimumSize(new Dimension(10, 150));
    Panel2.setPreferredSize(new Dimension(10, 150));

    GridLayout gridLayout1 = new GridLayout();
    gridLayout1.setRows(5);
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
    Panel5.setMinimumSize(new Dimension(10, 150));
    Panel5.setPreferredSize(new Dimension(10, 150));

    JPanel Panel6 = new JPanel();
    Panel6.setLayout(new BorderLayout());
    
    Panel3.add(new JLabel("From:"));
    Panel3.add(new JLabel("To:"));
    Panel3.add(new JLabel("Cc:"));
    Panel3.add(new JLabel("Bcc:"));
    Panel3.add(new JLabel("Subject:"));
    Panel2.add(Panel3, BorderLayout.WEST);

    cboFrom.addItem(addr);

    Panel4.add(cboFrom);
    Panel4.add(txtTo);
    Panel4.add(txtCc);
    Panel4.add(txtBcc);
    Panel4.add(txtSubject);
    Panel2.add(Panel4, BorderLayout.CENTER);

    // 突顯式選單
    txtMessage.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        showPopmenu(e);
      }
    });

    JScrollPane jsp1 = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    jsp1.getViewport().add(txtMessage);

    Panel1.add(Panel2, BorderLayout.NORTH);
    Panel1.add(jsp1, BorderLayout.CENTER);  

    this.add(jtoolbar, BorderLayout.NORTH);
    this.add(Panel1, BorderLayout.CENTER);

    this.validate();
    this.setSize(new Dimension(550, 450));

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    this.setResizable(true);
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        dispose();
      }
    });    
  }

  private void loadProperties() {
    try {
      Properties p = new Properties();
      FileInputStream in = new FileInputStream("mail.properties");
      
      p.load(in);
      
      host = p.getProperty("smtp.host");
      port = p.getProperty("smtp.port");
      user = p.getProperty("smtp.username");
      pass = p.getProperty("smtp.password");
      name = p.getProperty("smtp.name");
      addr = p.getProperty("smtp.address");
      
      in.close() ;
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void showPopmenu(MouseEvent e) {
    // 當按下滑鼠按鍵時
    if (e.getButton() == MouseEvent.BUTTON2 || e.getButton() == MouseEvent.BUTTON3) 
      // 顯示突顯式選單
      jpopupmenu.show(e.getComponent(), e.getX(), e.getY());
  }

  private void sendMail() {
    String to      = txtTo.getText();
    String cc      = txtCc.getText();
    String bcc     = txtBcc.getText();
    String subject = txtSubject.getText();
    String msgText = txtMessage.getText();

    JOptionPane joptionpane = new JOptionPane();

    if (to.equals("") && cc.equals("") && bcc.equals("")) {
      joptionpane.showMessageDialog(null, "Please enter the e-mail address.", "SMTP Client", JOptionPane.ERROR_MESSAGE);
    }
    else {
      // 設定系統屬性值
      Properties props = new Properties();
      props.put("mail.transport.protocol", "smtp");
      props.put("mail.smtp.host", host);
      props.put("mail.user", user);
  
      try {
        // 以自訂之MailAuthenticator類別建立javax.mail.Authenticator物件
        javax.mail.Authenticator auth = new MailAuthenticator();
        
        // 建立預設Session物件，並指定javax.mail.Authenticator物件
        Session session = Session.getDefaultInstance(props, auth);
  
        // 設定除錯模式
        session.setDebug(true);
  
        // 以Session物件建立MimeMessage物件
        MimeMessage msg = new MimeMessage(session);
        
        // 設定寄件者郵件地址
        msg.setFrom(new InternetAddress(addr, name));
    
        // 設定收件者郵件地址
        // 並以 parse() 處理多個郵件地址字串
        if (!to.equals(""))
          msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));

        if (!cc.equals(""))
          msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc, false));

        if (!bcc.equals(""))
          msg.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(bcc, false));

        // 設定郵件主旨
        msg.setSubject(subject);

        // 建立MimeBodyPart物件（第一部份：內文）
        MimeBodyPart mbp1 = new MimeBodyPart();
        // 設定內文
        mbp1.setContent(msgText, (String)cboMime.getItemAt(cboMime.getSelectedIndex()));

        // 建立MimeMultipart物件
        Multipart mp = new MimeMultipart();
        // 將內文部份加入MimeMultipart物件之中
        mp.addBodyPart(mbp1);

        FileDataSource fds = null;
        MimeBodyPart mbp2 = null;

        // 處理附件
        for (int i=0; i<cboAttachment.getItemCount(); i++) {
          // 定義檔案附件
          fds = new FileDataSource((String)cboAttachment.getItemAt(i));

          // 建立MimeBodyPart物件（第二部份：附件）
          mbp2 = new MimeBodyPart();

          // 設定FileDataSource物件的DataHandler instance  
          mbp2.setDataHandler(new DataHandler(fds));
          // 設定MimeBodyPart物件的檔案附件
          mbp2.setFileName(fds.getName());

          // 將附件部份加入MimeMultipart物件之中
          mp.addBodyPart(mbp2);
        }
    
        // 以MimeMultipart物件設定郵件內容
        msg.setContent(mp);
    
        // 設定郵件之傳送日期
        msg.setSentDate(new Date());
    
        System.out.println ("傳送郵件及附件 ...");
  
        // 建立Transport為smtp通訊協定之物件
        Transport transport = session.getTransport("smtp");
        // 建立與SMTP郵件伺服器之連結
        transport.connect(host, user, pass);
        // 傳送郵件
        transport.sendMessage(msg, msg.getAllRecipients());
        // 關閉與郵件伺服器之通訊連結
        transport.close();
  
        System.out.println ("傳送郵件及附件完成.");

        joptionpane.showMessageDialog(null, "傳送郵件及附件完成.", "SMTP Client", JOptionPane.INFORMATION_MESSAGE);
      }
      catch (AddressException aex) {
        aex.printStackTrace();
        joptionpane.showMessageDialog(null, "傳送郵件及附件錯誤 - AddressException.", "SMTP Client", JOptionPane.ERROR_MESSAGE);
      }
      catch (MessagingException mex) {
        mex.printStackTrace();
        joptionpane.showMessageDialog(null, "傳送郵件及附件錯誤 - MessagingException.", "SMTP Client", JOptionPane.ERROR_MESSAGE);
      }
      catch (UnsupportedEncodingException uex) {
        uex.printStackTrace();
        joptionpane.showMessageDialog(null, "傳送郵件及附件錯誤 - UnsupportedEncodingException.", "SMTP Client", JOptionPane.ERROR_MESSAGE);
      }
      catch (Exception ex) {
        ex.printStackTrace();
        joptionpane.showMessageDialog(null, "傳送郵件及附件錯誤 - Exception.", "SMTP Client", JOptionPane.ERROR_MESSAGE);
      }
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
    
    jbutton[9].setEnabled(true);
    jmenuItem[2][3].setEnabled(true);
  }

  private void detachFile() {
    if (cboAttachment.getItemCount() > 0) {
      int i = cboAttachment.getSelectedIndex();
      cboAttachment.removeItemAt(i);
    }
    
    if (cboAttachment.getItemCount() == 0) {
      jbutton[9].setEnabled(false);
      jmenuItem[2][3].setEnabled(false);
    }
  }

  private void openFile() {
    JFileChooser jfileChooser = new JFileChooser();
    jfileChooser.setCurrentDirectory(new File("."));
    jfileChooser.setMultiSelectionEnabled(false);

    jfileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
    jfileChooser.setDialogTitle("Open File ...");
    
    if (jfileChooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) 
      return;

    this.repaint();

    File fileName = jfileChooser.getSelectedFile();

    try {
      // load file
      BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
      
      String line, text = "";
      
      while((line = br.readLine()) != null)
        text += (line + System.getProperty("line.separator"));
      
      br.close();
      
      txtMessage.setText(text);
    }   
    catch(Exception ex) {
      JOptionPane.showMessageDialog(null, "Unable to open file. " + ex.toString(),
        "SMTP Client", JOptionPane.ERROR_MESSAGE);
    }
  }

  private void saveFile() {
    JFileChooser jfileChooser = new JFileChooser();
    jfileChooser.setCurrentDirectory(new File("."));
    jfileChooser.setMultiSelectionEnabled(false);

    jfileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
    jfileChooser.setDialogTitle("Save File ...");
    
    if (jfileChooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) 
      return;
    
    this.repaint();

    File fileName = jfileChooser.getSelectedFile();

    try  {
      BufferedReader br = new BufferedReader(new CharArrayReader(txtMessage.getText().toCharArray()));
      FileWriter out = new FileWriter(fileName);
      BufferedWriter bw = new BufferedWriter(out);

      String in = null;
      while((in = br.readLine()) != null) {
          bw.write(in);
          bw.newLine();
      }
      br.close();
      bw.flush();
      bw.close();
      out.close();

      String linesep = System.getProperty("line.separator");

      JOptionPane.showMessageDialog(null, "Message has been saved to " + 
        linesep + fileName.getAbsolutePath(), 
        "SMTP Client", JOptionPane.INFORMATION_MESSAGE);
    }
    catch(Exception ex) {
      JOptionPane.showMessageDialog(null, "Unable to save file. " + ex.toString(),
        "SMTP Client", JOptionPane.ERROR_MESSAGE);
    }
  }

  public void button_actionPerformed(ActionEvent e){
    if(e.getSource() == jbutton[0]){  // Send Mail
      Thread thread = new Thread() {
        public void run() {
          sendMail();
        }
      };
      thread.start();
    }
    else if(e.getSource() == jbutton[1]){  // New 
      txtMessage.setText("");
    }
    else if(e.getSource() == jbutton[2]){  // Open 
      Thread thread = new Thread() {
        public void run() {
          openFile();
        }
      };
      thread.start();
    }
    else if(e.getSource() == jbutton[3]){  // Save 
      if (!txtMessage.getText().trim().equals("")) {
        Thread thread = new Thread() {
          public void run() {
            saveFile();
          }
        };
        thread.start();
      }
    }
    else if(e.getSource() == jbutton[4]){  // Print 
      if (! txtMessage.getText().trim().equals("")) {
        new Thread() {
          public void run() {
            try {
              // 列印內容
              txtMessage.print();
            }
            catch (PrinterException ex) {
              ex.printStackTrace();
            }
          }
        }.start();
      } 
      else {
        JOptionPane.showMessageDialog(null, "無資料可列印", "SMTP Client", JOptionPane.ERROR_MESSAGE);
      }
    }    
    else if(e.getSource() == jbutton[5]){  // Cut
      txtMessage.cut();
    }
    else if(e.getSource() == jbutton[6]){  // Copy
      txtMessage.copy();
    }
    else if(e.getSource() == jbutton[7]){  // Paste
      txtMessage.paste();
    }
    else if(e.getSource() == jbutton[8]){  // Attach File
      Thread thread = new Thread() {
        public void run() {
          attachFile();
        }
      };
      thread.start();
    }
    else if(e.getSource() == jbutton[9]){  // Detach File
      Thread thread = new Thread() {
        public void run() {
          detachFile();
        }
      };
      thread.start();
    }
  }

  public void menu_actionPerformed(ActionEvent e){
    if(e.getSource() == jmenuItem[0][0]){  // New 
      txtMessage.setText("");
    }
    else if(e.getSource() == jmenuItem[0][1]){  // Open 
      Thread thread = new Thread() {
        public void run() {
          openFile();
        }
      };
      thread.start();
    }
    else if(e.getSource() == jmenuItem[0][3]){  // Save 
      if (!txtMessage.getText().trim().equals("")) {
        Thread thread = new Thread() {
          public void run() {
            saveFile();
          }
        };
        thread.start();
      }
    }
    else if(e.getSource() == jmenuItem[0][5]){  // Print 
      if (! txtMessage.getText().trim().equals("")) {
        new Thread() {
          public void run() {
            try {
              // 列印內容
              txtMessage.print();
            }
            catch (PrinterException ex) {
              ex.printStackTrace();
            }
          }
        }.start();
      } 
      else {
        JOptionPane.showMessageDialog(null, "無資料可列印", "SMTP Client", JOptionPane.ERROR_MESSAGE);
      }
    }
    else if(e.getSource() == jmenuItem[0][7]){  // Exit
      JOptionPane joptionpane = new JOptionPane();
      int iResult = joptionpane.showConfirmDialog(null, "Are you sure to quit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
      
      if (iResult == 0) {
        dispose();
      }
    }
    else if(e.getSource() == jmenuItem[1][0]){  // Cut
      txtMessage.cut();
    }
    else if(e.getSource() == jmenuItem[1][1]){  // Copy
      txtMessage.copy();
    }
    else if(e.getSource() == jmenuItem[1][2]){  // Paste
      txtMessage.paste();
    }
    else if(e.getSource() == jmenuItem[2][0]){  // Profile
      new ProfileDialog();
    }
    else if(e.getSource() == jmenuItem[2][2]){  // Attach File
      Thread thread = new Thread() {
        public void run() {
          attachFile();
        }
      };
      thread.start();
    }
    else if(e.getSource() == jmenuItem[2][3]){  // Detach File
      Thread thread = new Thread() {
        public void run() {
          detachFile();
        }
      };
      thread.start();
    }
    else if(e.getSource() == jmenuItem[2][5]){  // Send Mail
      Thread thread = new Thread() {
        public void run() {
          sendMail();
        }
      };
      thread.start();
    }
    else if(e.getSource() == jmenuItem[3][0]){  // About
      JOptionPane.showMessageDialog(null, "SMTP Client", "About", JOptionPane.INFORMATION_MESSAGE);
    }
    else if(e.getActionCommand().equals(items[5])){  // Cut
      txtMessage.cut();
    }
    else if(e.getActionCommand().equals(items[6])){  // Copy
      txtMessage.copy();
    }
    else if(e.getActionCommand().equals(items[7])){  // Paste
      txtMessage.paste();
    }
  }
}

