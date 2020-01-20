import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.UIManager;

public class LookandFeelDemo extends javax.swing.JFrame {

  // Main method
  public static void main(String[] args) {

    try {
      // 設定與作業系統有關之Look and Feel
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    new LookandFeelDemo();
  }

  // 建構函式
  // 測試用
  public LookandFeelDemo() {
    super("Look and Feel Demo");

    // 直接定義JFrame之Layout Manager為FlowLayout
    this.setLayout(new FlowLayout());

    JButton jbutton1 = new JButton("OK");
    JButton jbutton2 = new JButton("Cancel");
    JButton jbutton3 = new JButton("Yes");
    JButton jbutton4 = new JButton("No");

    // 直接將物件加至JFrame中
    this.add(jbutton1);
    this.add(jbutton2);
    this.add(jbutton3);
    this.add(jbutton4);

    // 取得跨平臺之Look and Feel類別名稱
    System.out.println("跨平臺之L&F類別: " + UIManager.getCrossPlatformLookAndFeelClassName());

    // 取得目前系統已安裝之Look and Feel類別
    UIManager.LookAndFeelInfo[] info = UIManager.getInstalledLookAndFeels();
    for (int i=0; i<info.length; i++)
      System.out.println("已安裝之L&F類別: " + info[i].getClassName());

    // 取得系統目前所使用的Look and Feel
    System.out.println("目前所使用之L&F類別: " + UIManager.getLookAndFeel().toString());

    // 取得與作業系統有關的Look and Feel
    System.out.println("與作業系統有關之L&F類別: " + UIManager.getSystemLookAndFeelClassName());

    // 設定視窗的大小
    this.setSize(250, 150);

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();

    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;

    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;

    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    // 顯示視窗
    this.setVisible(true);
    
    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }
}
