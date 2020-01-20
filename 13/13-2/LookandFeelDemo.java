import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.UIManager;
import com.sun.java.swing.plaf.motif.MotifLookAndFeel;

public class LookandFeelDemo extends javax.swing.JFrame {

  // Main method
  public static void main(String[] args) {

    try {
      // 設定CDE/Motif Look and Feel
      UIManager.setLookAndFeel(new com.sun.java.swing.plaf.motif.MotifLookAndFeel());
    }
    catch(UnsupportedLookAndFeelException e) {
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
