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
    JLabel jLabel2 = new JLabel("Student Name:");
    JTextField jTextField1 = new JTextField();
    JTextField jTextField2 = new JTextField();

    // 定義Layout Manager為GroupLayout
    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);

    // 設定水平群組
    layout.setHorizontalGroup(
      // 建立循序群組
      layout.createSequentialGroup() 
        .addContainerGap()
        // 加入群組至循序群組中
        // 建立平行群組並沿著垂直方向物件之前沿對齊
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jLabel1)
          .addComponent(jLabel2))
        // 加入最佳間隔至循序群組中
        // 並以RELATED類形間隔設定jLabel1與jTextField1物件間有關聯性  
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        // 加入群組至循序群組中
        // 建立平行群組並沿著垂直方向物件之後沿對齊
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
          .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
          .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE))
        .addContainerGap()
    );

    // 設定垂直群組
    layout.setVerticalGroup(
      // 建立循序群組
      layout.createSequentialGroup() 
        .addContainerGap()
        // 加入群組至循序群組中
        // 建立平行群組並沿著物件之基準線對齊 
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel1)
          .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE))
        // 加入最佳間隔至循序群組中
        // 並以RELATED類形間隔設定jLabel1與jTextField1物件間有關聯性  
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        // 加入群組至循序群組中
        // 建立平行群組並沿著物件之基準線對齊 
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel2)
          .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE))
        .addContainerGap()
    );

    // 設定視窗的大小
    this.setSize(300, 110);

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
