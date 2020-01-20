import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class BorderLayoutDemo extends javax.swing.JFrame {

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

    new BorderLayoutDemo();
  }

  // 建構函式
  public BorderLayoutDemo() {
    super("Border Layout Demo");

    // 直接定義JFrame之Layout Manager為FlowLayout
    this.setLayout(new BorderLayout());

    JButton jbutton1 = new JButton("EAST");
    JButton jbutton2 = new JButton("SOUTH");
    JButton jbutton3 = new JButton("WEST");
    JButton jbutton4 = new JButton("NORTH");
    JButton jbutton5 = new JButton("CENTER");

    // 直接將物件加至JFrame中
    this.add(jbutton1, BorderLayout.EAST);
    this.add(jbutton2, BorderLayout.SOUTH);
    this.add(jbutton3, BorderLayout.WEST);
    this.add(jbutton4, BorderLayout.NORTH);
    this.add(jbutton5, BorderLayout.CENTER);

    // 設定視窗的大小
    this.setSize(250, 250);

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
