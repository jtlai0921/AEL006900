import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class BoxLayoutDemo extends javax.swing.JFrame {

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

    new BoxLayoutDemo();
  }

  // 建構函式
  public BoxLayoutDemo() {
    super("Box Layout Demo");

    // 直接定義JFrame之Layout Manager為FlowLayout
    this.setLayout(new FlowLayout());

    JPanel jpanel1 = new JPanel();

    // 定義Layout Manager為BoxLayout
    // 由左至右配置
    jpanel1.setLayout(new BoxLayout(jpanel1, BoxLayout.X_AXIS));
    jpanel1.add(new JButton("X_AXIS 1"));
    jpanel1.add(new JButton("X_AXIS 2"));
    jpanel1.add(new JButton("X_AXIS 3"));

    // 直接將物件加至JFrame中
    this.add(jpanel1);

    JPanel jpanel2 = new JPanel();

    // 定義Layout Manager為BoxLayout
    // 由上至下配置
    jpanel2.setLayout(new BoxLayout(jpanel2, BoxLayout.Y_AXIS));
    jpanel2.add(new JButton("Y_AXIS 1"));
    jpanel2.add(new JButton("Y_AXIS 2"));
    jpanel2.add(new JButton("Y_AXIS 3"));

    // 直接將物件加至JFrame中
    this.add(jpanel2);

    // 設定視窗的大小
    this.setSize(300, 180);

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
