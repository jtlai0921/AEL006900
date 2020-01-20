import java.io.*;
import java.util.*;
import java.text.*;
import javax.swing.*;

import javax.mail.*;

// 自訂MailAuthenticator類別，並繼承Authenticator類別
public class MailAuthenticator extends javax.mail.Authenticator {
  // 建構函式
  public MailAuthenticator() {
  }

  // 實作Authenticator類別之getPasswordAuthentication方法
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
    
    // 取得使用者名稱
    if (user.equals(""))
      user = JOptionPane.showInputDialog("Please enter the Username.");
    
    // 取得使用者密碼
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
    
    // 回傳使用者名稱及密碼
    return new javax.mail.PasswordAuthentication(user, pass);
  }
}