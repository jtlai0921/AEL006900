import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;

import java.io.*;
import java.util.*;

public class ProfileDialog extends JDialog {
  JTextField txtSMTPHost = new JTextField();
  JTextField txtSMTPPort = new JTextField();
  JTextField txtSMTPUser = new JTextField();
  JTextField txtSMTPName = new JTextField();
  JTextField txtSMTPAddr = new JTextField();

  JTextField txtPOP3Host = new JTextField();
  JTextField txtPOP3Port = new JTextField();
  JTextField txtPOP3User = new JTextField();

  JPasswordField txtSMTPPass = new JPasswordField();
  JPasswordField txtPOP3Pass = new JPasswordField();

  JButton btnOK = new JButton();
  JButton btnCancel = new JButton();

  // 建構函式
  public ProfileDialog() {
    this.setTitle("Profile");

    // 定義Layout Manager 為 BorderLayout
    this.setLayout(new BorderLayout());
  
    loadProperties();

    btnOK.setText("OK");
    btnOK.setMnemonic('O');
    btnOK.setPreferredSize(new Dimension(80, 30));
    btnOK.setMinimumSize(new Dimension(80, 30));
    btnOK.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        button_actionPerformed(e);
      }
    });

    btnCancel.setText("Cancel");
    btnCancel.setMnemonic('C');
    btnCancel.setPreferredSize(new Dimension(80, 30));
    btnCancel.setMinimumSize(new Dimension(80, 30));
    btnCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        button_actionPerformed(e);
      }
    });

    // 設定回應字元
    txtSMTPPass.setEchoChar('*');
    txtPOP3Pass.setEchoChar('*');

    JPanel Panel1 = new JPanel();
    Panel1.setLayout(new BorderLayout());

    JPanel Panel2 = new JPanel();
    Panel2.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142)),"Outgoing Mail (SMTP)"));
    Panel2.setPreferredSize(new Dimension(10, 150));
    Panel2.setLayout(new BorderLayout());

    JPanel Panel3 = new JPanel();
    Panel3.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142)),"User Information"));
    Panel3.setPreferredSize(new Dimension(10, 120));
    Panel3.setLayout(new BorderLayout());
    
    JPanel Panel4 = new JPanel();
    Panel4.setBorder(BorderFactory.createEmptyBorder(4,0,4,0));
    Panel4.setPreferredSize(new Dimension(10, 50));

    GridLayout gridLayout1 = new GridLayout();
    gridLayout1.setRows(4);
    gridLayout1.setColumns(1);
    gridLayout1.setHgap(2);
    gridLayout1.setVgap(2);
    JPanel Panel5 = new JPanel();
    Panel5.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
    Panel5.setPreferredSize(new Dimension(80, 10));
    Panel5.setLayout(gridLayout1);
    
    JPanel Panel6 = new JPanel();
    Panel6.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
    Panel6.setLayout(gridLayout1);

    GridLayout gridLayout2 = new GridLayout();
    gridLayout2.setRows(2);
    gridLayout2.setColumns(1);
    gridLayout2.setHgap(2);
    gridLayout2.setVgap(4);
    JPanel Panel7 = new JPanel();
    Panel7.setPreferredSize(new Dimension(80, 10));
    Panel7.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
    Panel7.setLayout(gridLayout2);
    
    JPanel Panel8 = new JPanel();
    Panel8.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
    Panel8.setLayout(gridLayout2);

    JPanel Panel9 = new JPanel();
    Panel9.setLayout(new BorderLayout());

    JPanel Panel10 = new JPanel();
    Panel10.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142)),"Incoming Mail (POP3)"));
    Panel10.setMaximumSize(new Dimension(10, 150));
    Panel10.setMinimumSize(new Dimension(10, 150));
    Panel10.setPreferredSize(new Dimension(10, 150));
    Panel10.setLayout(new BorderLayout());

    JPanel Panel11 = new JPanel();
    Panel11.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
    Panel11.setPreferredSize(new Dimension(80, 10));
    Panel11.setLayout(gridLayout1);
    
    JPanel Panel12 = new JPanel();
    Panel12.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
    Panel12.setLayout(gridLayout1);

    Panel4.add(btnOK);
    Panel4.add(btnCancel);

    int i;
    String labels[]={
        "SMTP Host:", "SMTP Port:", "User name:", "Password:", "Name:", "Address:", 
        "POP3 Host:", "POP3 Port:", "User name:", "Password:"};
    
    for (i=0; i<4; i++) 
      Panel5.add(new JLabel(labels[i]));

    Panel2.add(Panel5, BorderLayout.WEST);
    Panel6.add(txtSMTPHost);
    Panel6.add(txtSMTPPort);
    Panel6.add(txtSMTPUser);
    Panel6.add(txtSMTPPass);
    Panel2.add(Panel6, BorderLayout.CENTER);

    for (i=4; i<6; i++) 
      Panel7.add(new JLabel(labels[i]));

    Panel3.add(Panel7, BorderLayout.WEST);
    Panel8.add(txtSMTPName);
    Panel8.add(txtSMTPAddr);
    Panel3.add(Panel8, BorderLayout.CENTER);
    Panel1.add(Panel2, BorderLayout.NORTH);
    Panel1.add(Panel3, BorderLayout.CENTER);

    for (i=6; i<10; i++) 
      Panel11.add(new JLabel(labels[i]));

    Panel10.add(Panel11, BorderLayout.WEST);
    Panel12.add(txtPOP3Host);
    Panel12.add(txtPOP3Port);
    Panel12.add(txtPOP3User);
    Panel12.add(txtPOP3Pass);
    Panel10.add(Panel12, BorderLayout.CENTER);

    Panel9.add(Panel10, BorderLayout.NORTH);
    
    JTabbedPane jtp1 = new JTabbedPane();
    jtp1.add(Panel1, "SMTP");

    this.add(jtp1, BorderLayout.CENTER);
    this.add(Panel4, BorderLayout.SOUTH);

    // Pack the Dialog
    pack();
    this.validate();
    this.setSize(new Dimension(350, 370));

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension dlgSize = this.getSize();
    if (dlgSize.height > screenSize.height)
      dlgSize.height = screenSize.height;
    if (dlgSize.width > screenSize.width)
      dlgSize.width = screenSize.width;
    this.setLocation((screenSize.width - dlgSize.width) / 2, (screenSize.height - dlgSize.height) / 2);

    this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    this.setModal(true);
    this.setResizable(false);
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
      
      // SMTP
      txtSMTPHost.setText(p.getProperty("smtp.host")) ;
      txtSMTPPort.setText(p.getProperty("smtp.port"));
      txtSMTPUser.setText(p.getProperty("smtp.username"));
      txtSMTPPass.setText(p.getProperty("smtp.password"));
      txtSMTPName.setText(p.getProperty("smtp.name"));
      txtSMTPAddr.setText(p.getProperty("smtp.address"));

      // POP3
      txtPOP3Host.setText(p.getProperty("pop3.host")) ;
      txtPOP3Port.setText(p.getProperty("pop3.port"));
      txtPOP3User.setText(p.getProperty("pop3.username"));
      txtPOP3Pass.setText(p.getProperty("pop3.password"));

      in.close() ;
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
    
  private void saveProperties() {
    try {
      Properties p = new Properties();
      
      FileOutputStream os = new FileOutputStream("mail.properties");
      
      // SMTP
      p.setProperty("smtp.host"    , txtSMTPHost.getText()) ;
      p.setProperty("smtp.port"    , txtSMTPPort.getText()) ;
      p.setProperty("smtp.username", txtSMTPUser.getText()) ;
      p.setProperty("smtp.password", new String(txtSMTPPass.getPassword())) ;
      p.setProperty("smtp.name"    , txtSMTPName.getText()) ;
      p.setProperty("smtp.address" , txtSMTPAddr.getText()) ;

      // POP3
      p.setProperty("pop3.host"    , txtPOP3Host.getText()) ;
      p.setProperty("pop3.port"    , txtPOP3Port.getText()) ;
      p.setProperty("pop3.username", txtPOP3User.getText()) ;
      p.setProperty("pop3.password", new String(txtPOP3Pass.getPassword())) ;
      
      p.store(os, null);
      os.close();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public void button_actionPerformed(ActionEvent e) {
    if (e.getSource() == btnOK) {
      saveProperties();
      if (JavaMail.cboFrom.getItemCount() > 0) {
        JavaMail.cboFrom.removeItemAt(0);
      }
      JavaMail.cboFrom.addItem(txtSMTPAddr.getText());
      dispose();
    }
    else if (e.getSource() == btnCancel) {
      dispose();
    }
  }
}
