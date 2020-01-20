import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class FlowLayoutDemo extends javax.swing.JFrame {

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

    new FlowLayoutDemo();
  }

  // 建構函式
  public FlowLayoutDemo() {
    super("Flow Layout Demo");

    // JDK 1.4的用法
    // 取得其Content Pane 
    Container contentPane = getContentPane();

    // 定義 Layout Manager 為 FlowLayout
    contentPane.setLayout(new FlowLayout());

    JButton jbutton1 = new JButton("OK");
    JButton jbutton2 = new JButton("Cancel");
    JButton jbutton3 = new JButton("Yes");
    JButton jbutton4 = new JButton("No");

    // 將物件加至Content Pane中
    contentPane.add(jbutton1);
    contentPane.add(jbutton2);
    contentPane.add(jbutton3);
    contentPane.add(jbutton4);

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
