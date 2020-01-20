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

  // �غc�禡
  public GroupLayoutDemo() {
    super("Group Layout Demo");

    JLabel jLabel1 = new JLabel("Student ID:");
    JLabel jLabel2 = new JLabel("Student Name:");
    JTextField jTextField1 = new JTextField();

    // �w�qLayout Manager��GroupLayout
    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);

    // �۰ʲ��ͪ��󶡤����j
    layout.setAutoCreateGaps(true);
    
    // �۰ʲ��ͪ���P�e���������j
    layout.setAutoCreateContainerGaps(true);

    // �]�w�����s��
    layout.setHorizontalGroup(
      // �إߴ`�Ǹs��
      layout.createSequentialGroup() 
        // �[�J�s�զܴ`�Ǹs�դ�
        // �إߥ���s�ըêu�۫�����V���󤧫e�u���
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jLabel1)
          .addComponent(jLabel2))
        // �[�J�̨ζ��j�ܴ`�Ǹs�դ�
        // �åHRELATED���ζ��j�]�wjLabel1�PjTextField1���󶡦����p��  
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jTextField1)
    );

    // �]�w�����s��
    layout.setVerticalGroup(
      // �إߴ`�Ǹs��
      layout.createSequentialGroup() 
        // �[�J�s�զܴ`�Ǹs�դ�
        // �إߥ���s�ըêu�۪��󤧰�ǽu��� 
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel1)
          .addComponent(jTextField1))
        .addComponent(jLabel2)
    );

    // �]�w�������j�p
    this.setSize(250, 120);

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();

    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;

    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;

    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    // ��ܵ���
    this.setVisible(true);
    
    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }
}
