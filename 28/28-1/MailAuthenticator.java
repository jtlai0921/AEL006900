import java.io.*;
import java.util.*;
import java.text.*;
import javax.swing.*;

import javax.mail.*;

// �ۭqMailAuthenticator���O�A���~��Authenticator���O
public class MailAuthenticator extends javax.mail.Authenticator {
  // �غc�禡
  public MailAuthenticator() {
  }

  // ��@Authenticator���O��getPasswordAuthentication��k
  protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
    String user=null, pass=null;
    Properties p = new Properties();
    
    try {
      FileInputStream in = new FileInputStream("mail.properties");
      
      p.load(in);
      
      user = p.getProperty("smtp.username");
      pass = p.getProperty("smtp.password");
      
      in.close() ;
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    
    // ���o�ϥΪ̦W��
    if (user.equals(""))
      user = JOptionPane.showInputDialog("Please enter the Username.");
    
    // ���o�ϥΪ̱K�X
    if (pass.equals(""))
      pass = JOptionPane.showInputDialog("Please enter the Password.");
    
    try {
      FileOutputStream os = new FileOutputStream("mail.properties");
      
      p.setProperty("smtp.username", user) ;
      p.setProperty("smtp.password", pass) ;
      
      p.store(os, null);
      os.close();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    
    // �^�ǨϥΪ̦W�٤αK�X
    return new javax.mail.PasswordAuthentication(user, pass);
  }
}