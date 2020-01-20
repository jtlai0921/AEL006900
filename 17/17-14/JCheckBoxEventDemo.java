import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

// Java Swing Event
import javax.swing.event.*;
 
public class JCheckBoxEventDemo extends javax.swing.JFrame implements ChangeListener {

  JCheckBox jcheckbox[] = new JCheckBox[7];
  JLabel jlabel;

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

    new JCheckBoxEventDemo();
  }

  // �غc�禡
  public JCheckBoxEventDemo() {
    super("JCheckBox Demo");

    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // �]�w�����ϥ�
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // �w�q Layout Manager �� BorderLayout
    this.setLayout(new BorderLayout());

    for (int i=0; i<5; i++) {
      if (i==0) 
        jcheckbox[i] = new JCheckBox("CheckBox " + i, true);
      else
        jcheckbox[i] = new JCheckBox("CheckBox " + i);

      // ���U ChangeListener
      jcheckbox[i].addChangeListener(this);
    }

    // �]�w Etched Border (Test only)
    Border border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.white, new Color(142, 142, 142)); 

    JPanel jpanel1 = new JPanel();
    jpanel1.setBorder(BorderFactory.createTitledBorder(border, "�ƿ�"));
    // �w�qLayout Manager��GroupLayout
    GroupLayout layout = new GroupLayout(jpanel1);
    jpanel1.setLayout(layout);

    // �]�w�����s��
    layout.setHorizontalGroup(
      // �إߴ`�Ǹs��
      layout.createSequentialGroup() 
        .addContainerGap()
        // �[�J�s�զܴ`�Ǹs�դ�
        // �إߥ���s�ըêu�۫�����V���󤧫e�u���
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jcheckbox[0])
          .addComponent(jcheckbox[1])
          .addComponent(jcheckbox[2]))
        .addContainerGap()
    );

    // �]�w�����s��
    layout.setVerticalGroup(
      // �إߥ���s��
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING) 
        // �[�J�`�Ǹs�զܥ���s�դ�
        .addGroup(layout.createSequentialGroup()
          .addContainerGap()
          .addComponent(jcheckbox[0])
          // �[�J�̨ζ��j�ܸs�դ�  
          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
          .addComponent(jcheckbox[1])
          // �[�J�̨ζ��j�ܸs�դ�  
          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
          .addComponent(jcheckbox[2])
        .addContainerGap())
    );    

    JPanel jpanel2 = new JPanel();
    // �w�qLayout Manager��GroupLayout
    layout = new GroupLayout(jpanel2);
    jpanel2.setLayout(layout);

    // �]�w�����s��
    layout.setHorizontalGroup(
      // �إߴ`�Ǹs��
      layout.createSequentialGroup() 
        .addContainerGap()
        // �[�J�s�զܴ`�Ǹs�դ�
        // �إߥ���s�ըêu�۫�����V���󤧫e�u���
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jcheckbox[3])
          .addComponent(jcheckbox[4]))
        .addContainerGap()
    );

    // �]�w�����s��
    layout.setVerticalGroup(
      // �إߥ���s��
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING) 
        // �[�J�`�Ǹs�զܥ���s�դ�
        .addGroup(layout.createSequentialGroup()
          .addContainerGap()
          .addComponent(jcheckbox[3])
          // �[�J�̨ζ��j�ܸs�դ�  
          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
          .addComponent(jcheckbox[4])
        .addContainerGap())
    );    

    // �غc�s��
    ButtonGroup group1 = new ButtonGroup();
    group1.add(jcheckbox[3]);
    group1.add(jcheckbox[4]);

    jcheckbox[5] = new JCheckBox("CheckBox 5", new ImageIcon(cl.getResource("images/check.gif")));
    // �]�w���Ҧr��۹��Ϲ���������m
    jcheckbox[5].setHorizontalTextPosition(SwingConstants.RIGHT);
    // �]�w���Ҧr��۹��Ϲ���������m
    jcheckbox[5].setVerticalTextPosition(SwingConstants.CENTER);
    // �]�w���U�ɪ��Ϲ�
    jcheckbox[5].setSelectedIcon(new ImageIcon(cl.getResource("images/checkselected.gif")));
    // ���U ChangeListener
    jcheckbox[5].addChangeListener(this);
  
    jcheckbox[6] = new JCheckBox("CheckBox 6", new ImageIcon(cl.getResource("images/brownarrow.gif")));
    // �]�w���Ҧr��۹��Ϲ���������m
    jcheckbox[6].setHorizontalTextPosition(SwingConstants.RIGHT);
    // �]�w���Ҧr��۹��Ϲ���������m
    jcheckbox[6].setVerticalTextPosition(SwingConstants.CENTER);
    // �]�w�O�_��ƹ����L�W��ɪ��ĪG
    jcheckbox[6].setRolloverEnabled(true);
    // �]�w�ƹ����L���O�s�W��ɪ��Ϲ�
    jcheckbox[6].setRolloverIcon(new ImageIcon(cl.getResource("images/greenarrow.gif")));
    // �]�w�ƹ����L���O�s�W��B�Q����]Selected�^�ɪ��Ϲ�
    jcheckbox[6].setRolloverSelectedIcon(new ImageIcon(cl.getResource("images/greenarrow.gif"))); 
    // �]�w���U�ɪ��Ϲ�
    jcheckbox[6].setSelectedIcon(new ImageIcon(cl.getResource("images/bluearrow.gif")));
    // ���U ChangeListener
    jcheckbox[6].addChangeListener(this);
      
    // �غc�s��
    ButtonGroup group2 = new ButtonGroup();
    group2.add(jcheckbox[5]);
    group2.add(jcheckbox[6]);

    JPanel jpanel3 = new JPanel();
    // �w�qLayout Manager��GroupLayout
    layout = new GroupLayout(jpanel3);
    jpanel3.setLayout(layout);

    // �]�w�����s��
    layout.setHorizontalGroup(
      // �إߴ`�Ǹs��
      layout.createSequentialGroup() 
        .addContainerGap()
        // �[�J�s�զܴ`�Ǹs�դ�
        // �إߥ���s�ըêu�۫�����V���󤧫e�u���
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jcheckbox[5])
          .addComponent(jcheckbox[6]))
        .addContainerGap()
    );

    // �]�w�����s��
    layout.setVerticalGroup(
      // �إߥ���s��
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING) 
        // �[�J�`�Ǹs�զܥ���s�դ�
        .addGroup(layout.createSequentialGroup()
          .addContainerGap()
          .addComponent(jcheckbox[5])
          // �[�J�̨ζ��j�ܸs�դ�  
          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
          .addComponent(jcheckbox[6])
        .addContainerGap())
    );    

    JPanel jpanel4 = new JPanel();
    jpanel4.setLayout(new GridLayout(1, 2));
    jpanel4.setBorder(BorderFactory.createTitledBorder(border, "���"));

    jpanel4.add(jpanel2);
    jpanel4.add(jpanel3);

    JPanel jpanel5 = new JPanel();
    // �]�w������ؼ˦�
    jpanel5.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
    jpanel5.setLayout(new BorderLayout());

    jlabel = new JLabel("JCheckBox Event");
    jpanel5.add(jlabel, BorderLayout.WEST);
    
    this.add(jpanel1, BorderLayout.NORTH);
    this.add(jpanel4, BorderLayout.CENTER);
    this.add(jpanel5, BorderLayout.SOUTH);

    // �]�w�������j�p
    this.setSize(280, 280);

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

  // ���󪬺A���ܮ�
  public void stateChanged(ChangeEvent e) {
    String source = "";

    for (int i=0; i < jcheckbox.length; i++) {
      if (e.getSource().equals(jcheckbox[i]))
        source = "Roll over " + jcheckbox[i].getText();
    }

    jlabel.setText(source);
  }
}
