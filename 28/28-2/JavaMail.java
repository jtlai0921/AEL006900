import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import javax.swing.table.*;
import java.awt.print.*;

import java.net.*;
import java.io.*;
import java.util.*;
import java.text.*;

// JavaMail
import javax.mail.*;
import javax.mail.event.*;
import javax.mail.internet.*;
import javax.mail.search.*;
// JAF
import javax.activation.*;

public class JavaMail extends JFrame {
  String items[]={"Receive Mail", "Previous Mail", "Next Mail", "Print", "Save Attachment"};
  String images[]={"message.gif", "down.gif", "up.gif", "print.gif", "save.gif"};

  JButton jbutton[];

  String menu[]={"File|F","Edit|E","Mail|M","Help|H"};
  String menuItem[][]={
    {"Receive Mail|M|message.gif", "-", "Print|P|print.gif", "-", "Exit|X"},
    {"Cut|T|cut.gif", "Copy|C|copy.gif", "Paste|P|paste.gif"},
    {"Profile|P", "-", "Previous Mail|R|down.gif", "Next Mail|N|up.gif"},
    {"About|A|about.gif"}
  };

  JMenuItem jmenuItem[][] = new JMenuItem[4][5];
  JMenu jmenu[];
  
  JTextField txtFrom = new JTextField();
  JTextField txtTo   = new JTextField();
  JTextField txtCc   = new JTextField();
  JTextField txtDate = new JTextField();
  JTextField txtSubject = new JTextField();
  JEditorPane txtMessage = new JEditorPane();
  JLabel lblStatus = new JLabel();

  JComboBox cboAttachment = new JComboBox();
  boolean blnAttach = false;
  
  String host, port, user, pass;
  private int totalMail=0, newMail=0, currentMail=0;
  private Message[] message;
  private Store store = null;
  private Folder folder = null;

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

  public JavaMail() {
    super("JavaMail POP3 Client");

    // Get current classloader
    ClassLoader cl = this.getClass().getClassLoader();

    this.setIconImage(new ImageIcon(cl.getResource("images/compose.gif")).getImage());

    // �w�qLayout Manager �� BorderLayout
    this.setLayout(new BorderLayout());

    JToolBar jtoolbar = new JToolBar();

    jbutton = new JButton[images.length];
    
    int i, j;

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

      if (i!=4)
        jtoolbar.add(jbutton[i]);

      if (i==0 || i==2 || i==3)
        jtoolbar.addSeparator();
    }

    cboAttachment.setPreferredSize(new Dimension(200, 30));
    cboAttachment.setMaximumSize(new Dimension(200, 30));
    // �]�w�U��JComboBox���O�ɡA�̦h�i��ܪ����ئC��
    cboAttachment.setMaximumRowCount(5);

    jtoolbar.add(cboAttachment);
    jtoolbar.addSeparator();
    jtoolbar.add(jbutton[4]);

    // �إ߿��\��C
    JMenuBar jmenubar = new JMenuBar();
    // �w�q�����ϥΪ̤��������\��C
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

    showComponent(false);

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
    Panel5.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142)),BorderFactory.createEmptyBorder(4,2,2,0)));
    Panel5.setMaximumSize(new Dimension(10, 30));
    Panel5.setMinimumSize(new Dimension(10, 30));
    Panel5.setPreferredSize(new Dimension(10, 30));
    Panel5.setLayout(new BorderLayout());
    
    Panel3.add(new JLabel("From:"));
    Panel3.add(new JLabel("To:"));
    Panel3.add(new JLabel("Cc:"));
    Panel3.add(new JLabel("Date:"));
    Panel3.add(new JLabel("Subject:"));
    Panel2.add(Panel3, BorderLayout.WEST);

    Panel4.add(txtFrom);
    Panel4.add(txtTo);
    Panel4.add(txtCc);
    Panel4.add(txtDate);
    Panel4.add(txtSubject);
    Panel2.add(Panel4, BorderLayout.CENTER);

    JScrollPane jsp1 = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    jsp1.getViewport().add(txtMessage);

    lblStatus.setHorizontalAlignment(SwingConstants.LEFT);
    lblStatus.setHorizontalTextPosition(SwingConstants.LEFT);
    lblStatus.setText("JavaMail");
    Panel5.add(lblStatus, BorderLayout.CENTER);

    Panel1.add(Panel2, BorderLayout.NORTH);
    Panel1.add(jsp1, BorderLayout.CENTER);  
    Panel1.add(Panel5, BorderLayout.SOUTH);  

    this.add(jtoolbar, BorderLayout.NORTH);
    this.add(Panel1, BorderLayout.CENTER);

    this.validate();
    this.setSize(new Dimension(450, 450));

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

  private void loadProperties() {
    try {
      Properties p = new Properties();
      FileInputStream in = new FileInputStream("mail.properties");
      
      p.load(in);
      
      host = p.getProperty("pop3.host");
      port = p.getProperty("pop3.port");
      user = p.getProperty("pop3.username");
      pass = p.getProperty("pop3.password");
      
      in.close() ;
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void nextMail() {
    currentMail ++;
  
    if (currentMail > totalMail)
      currentMail = totalMail;
  
    showMail(currentMail);
    showComponent(true);
  }

  private void previousMail() {
    -- currentMail;
  
    if (currentMail < 0)
      currentMail = 0;
  
    showMail(currentMail);
    showComponent(true);
  }

  private boolean receiveMail() {
    loadProperties();
  
    try {
      Properties props = System.getProperties();
      props.put("mail.transport.protocol", "pop3");
      props.put("mail.pop3.host", host);
    
      // �H�ۭq��MailAuthenticator���O�إ�javax.mail.Authenticator����
      javax.mail.Authenticator auth = new MailAuthenticator();
      
      // �إ߹w�]Session����A�ë��wjavax.mail.Authenticator����
      Session session = Session.getDefaultInstance(props, auth);
      session.setDebug(true);
  
      // �إ�Store��POP3�q�T��w������
      store = session.getStore("pop3");

      // �إ߻PPOP3�l����A�����s��
      store.connect(host, user, pass);

      // ���o�����Ƨ��]Inbox�^
      folder = store.getFolder("INBOX");

      if (folder == null) {
        System.out.println("�L�����Ƨ�");

        // �����P�l����A�����q�T�s��
        store.close();

        return false;
      }

      try {
        folder.open(Folder.READ_WRITE);
      } 
      catch (MessagingException ex) {
        // ��Ū
        folder.open(Folder.READ_ONLY);
      }

      // ���o�l���Ƨ����A�Ҧ����l��ƥ�
      totalMail = folder.getMessageCount();

      if (totalMail == 0) {
        lblStatus.setText("No messages found.");
        folder.close(false);
        store.close();
        return false;
      }

      // ���o�l���Ƨ����A�Ҧ��s�W���l��ƥ�
      newMail = folder.getNewMessageCount();
      
      // ���o�Ҧ��l��
      message = folder.getMessages();
      return true;
    }
    catch (MessagingException mex) {
      mex.printStackTrace();

      if (folder != null) {
        try {
          // �����l���Ƨ�
          folder.close(false);
        }
        catch (Exception ex1) {
          ex1.printStackTrace();
        }
      }

      if (store != null) {
        try {
          // �����P�l����A�����q�T�s��
          store.close();
        }
        catch (Exception ex2) {
          ex2.printStackTrace();
        }
      }
      return false;
    }
    catch (Exception ex) {
      ex.printStackTrace();
      return false;
    }
  }

  private void showMail(int i) {
    String sReceipient="";
    Address[] addr;
    
    try {
      // �H��̶l��a�}
      txtFrom.setText(message[i].getFrom()[0].toString());

      // �l��ǰe���
      Date sentDate = message[i].getSentDate();
      SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy hh:mm:ss z");
      txtDate.setText(sentDate != null ? sdf.format(sentDate) : "");

      // ����̶l��a�}
      if ((addr = message[i].getRecipients(Message.RecipientType.TO)) != null) {
        sReceipient = addr[0].toString();
        
        for (int j = 1; j < addr.length; j++)
          sReceipient = sReceipient + ", " + addr[j].toString();
          
        txtTo.setText(sReceipient);
      }
    
      // �ƥ������
      if ((addr = message[i].getRecipients(Message.RecipientType.CC)) != null) {
        sReceipient = addr[0].toString();
        
        for (int j = 1; j < addr.length; j++)
          sReceipient = sReceipient + ", " + addr[j].toString();

        txtCc.setText(sReceipient);
      }

      // �l��D��
      txtSubject.setText(message[i].getSubject());

      // Content-Type
      txtMessage.setContentType(message[i].getContentType());
    
      Object obj = message[i].getContent();

      blnAttach = false;
      
      if (obj instanceof String) {
        // ���t����
        txtMessage.setText((String)obj);
      } 
      else if (obj instanceof Multipart){
        // �i��t������
        DefaultComboBoxModel cboModel = new DefaultComboBoxModel();
        cboAttachment.setModel(cboModel);

        MimeMultipart mp = (MimeMultipart)obj;

        txtMessage.setText(mp.getBodyPart(0).getContent()+"");

        for (int j=0, n=mp.getCount(); j<n; j++) {
          Part part = mp.getBodyPart(j);

          String disposition = part.getDisposition();

          if ((disposition != null) &&
              (disposition.equals(Part.ATTACHMENT) ||
               disposition.equals(Part.INLINE))) {

            if (part.getFileName() != null) {
              blnAttach = true;
              cboAttachment.addItem(part.getFileName());
              // �����anIndex�ӿﶵ����
              cboAttachment.setSelectedIndex(0);
            }
          }
        }
      } 
      else {
        txtMessage.setText(obj.toString());
      } 

      txtMessage.setCaretPosition(0);   

      int k = i + 1;
    
      lblStatus.setText("Total messages: " + totalMail + 
          "  New: " + newMail + 
          "  Current: " + k);
    
      this.repaint();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void saveAttachment() {
    JFileChooser jfileChooser = new JFileChooser();
    jfileChooser.setCurrentDirectory(new File("."));
    jfileChooser.setMultiSelectionEnabled(false);
    jfileChooser.setDialogTitle("Select directory to save ...");

    // Select directory only
    jfileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

    if (jfileChooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) 
      return;
    
    this.repaint();

    String pathName = jfileChooser.getSelectedFile().getAbsolutePath();

    String fileName = (String)cboAttachment.getItemAt(cboAttachment.getSelectedIndex());

    try {
      Object obj = message[currentMail].getContent();
      
      if (obj instanceof Multipart) {
        Multipart mp = (Multipart)obj;

        InputStream is = null;

        for (int j=0, n=mp.getCount(); j<n; j++) {
          Part part = mp.getBodyPart(j);

          if ((part.getFileName() != null) && (part.getFileName().equals(fileName))) {
            is = part.getInputStream();
            break;
          }
        }
        
        String filesep = System.getProperty("file.separator");
        File f = new File(pathName + filesep + fileName);

        OutputStream os = new BufferedOutputStream(new FileOutputStream(f));
        int c;
        while ((c = is.read()) != -1)
          os.write(c);

        os.close();

        String linesep = System.getProperty("line.separator");
  
        JOptionPane.showMessageDialog(null, fileName + " has been saved to " + 
          linesep + pathName, "POP3 Client", JOptionPane.INFORMATION_MESSAGE);
      }
    }
    catch (Exception ex){
      ex.printStackTrace();
    }
  }

  private void showComponent(boolean flag) {
    txtFrom.setEditable(false);
    txtTo.setEditable(false);
    txtCc.setEditable(false);
    txtDate.setEditable(false);
    txtSubject.setEditable(false);
    txtMessage.setEditable(true);

    jbutton[1].setEnabled(false);  // previous
    jbutton[2].setEnabled(false);  // next
    jmenuItem[2][2].setEnabled(false); // previous
    jmenuItem[2][3].setEnabled(false); // next

    if (flag) {
      if ((totalMail > 1) && (currentMail == 0)) { 
        // next
        jbutton[2].setEnabled(true);
        jmenuItem[2][3].setEnabled(true);
      }
      else if ((currentMail < (totalMail - 1)) && (currentMail > 0)) {
        jbutton[1].setEnabled(true);
        jbutton[2].setEnabled(true);
        jmenuItem[2][2].setEnabled(true);
        jmenuItem[2][3].setEnabled(true);
      }
      else if ((currentMail == (totalMail - 1)) && (currentMail > 0)) {
        // previous
        jbutton[1].setEnabled(true);
        jmenuItem[2][2].setEnabled(true);
      }
    }
    
    cboAttachment.setVisible(blnAttach ? true : false);
    cboAttachment.setEnabled(blnAttach ? true : false);
    jbutton[4].setVisible(blnAttach ? true : false);
    jbutton[4].setEnabled(blnAttach ? true : false);
  }

  public void button_actionPerformed(ActionEvent e){
    if(e.getSource() == jbutton[0]){  // Receive Mail
      Thread thread = new Thread() {
        public void run() {
          boolean flag = receiveMail();
        
          if (flag) {
            showMail(0);
            showComponent(true);
          }
          else
            showComponent(false);
        }
      };
      thread.start();
    }
    else if(e.getSource() == jbutton[1]){  // Previous 
      Thread thread = new Thread() {
        public void run() {
          previousMail();
        }
      };
      thread.start();
    }
    else if(e.getSource() == jbutton[2]){  // Next 
      Thread thread = new Thread() {
        public void run() {
          nextMail();
        }
      };
      thread.start();
    }
    else if(e.getSource() == jbutton[3]){  // Print 
      if (! txtMessage.getText().trim().equals("")) {
        new Thread() {
          public void run() {
            try {
              // �C�L���e
              txtMessage.print();
            }
            catch (PrinterException ex) {
              ex.printStackTrace();
            }
          }
        }.start();
      } 
      else {
        JOptionPane.showMessageDialog(null, "�L��ƥi�C�L", "POP3 Client", JOptionPane.ERROR_MESSAGE);
      }
    }    
    else if(e.getSource() == jbutton[4]){  // Save
      if (cboAttachment.getItemCount()>0) {
        Thread thread = new Thread() {
          public void run() {
            saveAttachment();
          }
        };
        thread.start();
      }
    }
  }

  public void menu_actionPerformed(ActionEvent e){
    if(e.getSource() == jmenuItem[0][0]){  // Receive Mail
      Thread thread = new Thread() {
        public void run() {
          boolean flag = receiveMail();
        
          if (flag) {
            showMail(0);
            showComponent(true);
          }
          else
            showComponent(false);
        }
      };
      thread.start();
    }
    else if(e.getSource() == jmenuItem[0][2]){  // Print 
      if (! txtMessage.getText().trim().equals("")) {
        new Thread() {
          public void run() {
            try {
              // �C�L���e
              txtMessage.print();
            }
            catch (PrinterException ex) {
              ex.printStackTrace();
            }
          }
        }.start();
      } 
      else {
        JOptionPane.showMessageDialog(null, "�L��ƥi�C�L", "POP3 Client", JOptionPane.ERROR_MESSAGE);
      }
    }
    else if(e.getSource() == jmenuItem[0][4]){  // Exit
      JOptionPane joptionpane = new JOptionPane();
      int iResult = joptionpane.showConfirmDialog(null, "Are you sure to quit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
      
      if (iResult == 0) {
        dispose();
        System.exit(0);
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
    else if(e.getSource() == jmenuItem[2][2]){  // Previous
      Thread thread = new Thread() {
        public void run() {
          previousMail();
        }
      };
      thread.start();
    }
    else if(e.getSource() == jmenuItem[2][3]){  // Next
      Thread thread = new Thread() {
        public void run() {
          nextMail();
        }
      };
      thread.start();
    }
    else if(e.getSource() == jmenuItem[3][0]){  // About
      JOptionPane.showMessageDialog(null, "POP3 Client", "About", JOptionPane.INFORMATION_MESSAGE);
    }
  }
}
