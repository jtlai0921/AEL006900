import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class JLabelDemo extends javax.swing.JFrame {

  // Main method
  public static void main(String[] args) {
    UIManager.put("swing.boldMetal", Boolean.FALSE);
    JDialog.setDefaultLookAndFeelDecorated(true);
    JFrame.setDefaultLookAndFeelDecorated(true);
    Toolkit.getDefaultToolkit().setDynamicLayout(true);

    try {
      UIManager.setLookAndFeel(new javax.swing.plaf.metal.MetalLookAndFeel());
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    new JLabelDemo();
  }

  // �غc�禡
  public JLabelDemo() {
    super("JLabel Demo");

    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // �]�w�����ϥ�
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // �w�q Layout Manager �� BorderLayout
    this.setLayout(new BorderLayout());

    // �إߧt�����D�����D��بåH�W�U�k����ج����
    Border border = BorderFactory.createTitledBorder(
      new EtchedBorder(EtchedBorder.LOWERED), "Label For", 
      TitledBorder.LEFT, TitledBorder.TOP); 

    JLabel[] jlabel = new JLabel[2];
    JTextField[] jTextField = new JTextField[2];
    
    jTextField[0] = new JTextField();
    jTextField[1] = new JTextField();
    
    // �¤�r����
    jlabel[0] = new JLabel("ID:");
    // �H�r���]�w���Ҫ�����U�O�X
    jlabel[0].setDisplayedMnemonic('I');
    // �]�w���ҩ��ݤ�����
    jlabel[0].setLabelFor(jTextField[0]);

    // �¤�r����
    jlabel[1] = new JLabel("Name:");
    // �H�r���]�w���Ҫ�����U�O�X
    jlabel[1].setDisplayedMnemonic('N');
    // �]�w���ҩ��ݤ�����
    jlabel[1].setLabelFor(jTextField[1]);
    
    JPanel jpanel = new JPanel();
    // �w�qLayout Manager��GroupLayout
    GroupLayout layout = new GroupLayout(jpanel);
    jpanel.setLayout(layout);
    // �]�w��ؼ˦�
    jpanel.setBorder(border);

    // �]�w�����s��
    layout.setHorizontalGroup(
      // �إߴ`�Ǹs��
      layout.createSequentialGroup() 
        .addContainerGap()
        // �[�J�s�զܴ`�Ǹs�դ�
        // �إߥ���s�ըêu�۫�����V���󤧫e�u���
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jlabel[0])
          .addComponent(jlabel[1]))
        // �[�J�̨ζ��j�ܴ`�Ǹs�դ�  
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        // �[�J�s�զܴ`�Ǹs�դ�
        // �إߥ���s�ըêu�۫�����V���󤧫�u���
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
          .addComponent(jTextField[0], javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
          .addComponent(jTextField[1], javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE))
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
          .addComponent(jlabel[0])
          .addComponent(jTextField[0], javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE))
        // �[�J�̨ζ��j�ܴ`�Ǹs�դ�  
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        // �[�J�s�զܴ`�Ǹs�դ�
        // �إߥ���s�ըêu�۪��󤧰�ǽu��� 
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jlabel[1])
          .addComponent(jTextField[1], javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE))
        .addContainerGap()
    );
    
    this.add(jpanel, BorderLayout.CENTER);

    // �]�w�������j�p
    this.setSize(250, 150);

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