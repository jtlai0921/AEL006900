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
    JTextField jTextField2 = new JTextField();

    // �w�qLayout Manager��GroupLayout
    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);

    // �]�w�����s��
    layout.setHorizontalGroup(
      // �إߴ`�Ǹs��
      layout.createSequentialGroup() 
        .addContainerGap()
        // �[�J�s�զܴ`�Ǹs�դ�
        // �إߥ���s�ըêu�۫�����V���󤧫e�u���
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jLabel1)
          .addComponent(jLabel2))
        // �[�J�̨ζ��j�ܴ`�Ǹs�դ�
        // �åHRELATED���ζ��j�]�wjLabel1�PjTextField1���󶡦����p��  
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        // �[�J�s�զܴ`�Ǹs�դ�
        // �إߥ���s�ըêu�۫�����V���󤧫�u���
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
          .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
          .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE))
        .addContainerGap()
    );

    // �]�w�����s��
    layout.setVerticalGroup(
      // �إߴ`�Ǹs��
      layout.createSequentialGroup() 
        .addContainerGap()
        // �[�J�s�զܴ`�Ǹs�դ�
        // �إߥ���s�ըêu�۪��󤧰�ǽu��� 
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel1)
          .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE))
        // �[�J�̨ζ��j�ܴ`�Ǹs�դ�
        // �åHRELATED���ζ��j�]�wjLabel1�PjTextField1���󶡦����p��  
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        // �[�J�s�զܴ`�Ǹs�դ�
        // �إߥ���s�ըêu�۪��󤧰�ǽu��� 
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel2)
          .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE))
        .addContainerGap()
    );

    // �]�w�������j�p
    this.setSize(300, 110);

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
