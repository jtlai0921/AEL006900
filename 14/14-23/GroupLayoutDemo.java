import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class GroupLayoutDemo extends javax.swing.JFrame {

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

    new GroupLayoutDemo();
  }

  // 建構函式
  public GroupLayoutDemo() {
    super("Group Layout Demo");

    JLabel jLabel1 = new JLabel("Student ID:");
    JTextField jTextField1 = new JTextField();

    // 定義Layout Manager為GroupLayout
    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);

    // 自動產生物件間之間隔
    layout.setAutoCreateGaps(true);
    
    // 自動產生物件與容器間之間隔
    layout.setAutoCreateContainerGaps(true);

    // 設定水平群組
    layout.setHorizontalGroup(
      // 建立循序群組
      layout.createSequentialGroup() 
        .addComponent(jLabel1)
        .addComponent(jTextField1)
    );

    // 設定垂直群組
    layout.setVerticalGroup(
      // 建立平行群組並沿著物件之基準線對齊 
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
        .addComponent(jLabel1)
        .addComponent(jTextField1)
    );

    // 設定視窗的大小
    this.setSize(250, 80);

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
