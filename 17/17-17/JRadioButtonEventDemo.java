import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class JRadioButtonEventDemo extends javax.swing.JFrame implements ActionListener {

  JRadioButton jradiobutton[] = new JRadioButton[7];
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

    new JRadioButtonEventDemo();
  }

  // �غc�禡
  public JRadioButtonEventDemo() {
    super("JRadioButton Demo");
    
    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // �]�w�����ϥ�
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // �w�q Layout Manager �� BorderLayout
    this.setLayout(new BorderLayout());
    
    for (int i=0; i<5; i++) {
      if (i==0) 
        jradiobutton[i] = new JRadioButton("RadioButton " + i, true);
      else
        jradiobutton[i] = new JRadioButton("RadioButton " + i);

      // ���U ActionListener
      jradiobutton[i].addActionListener(this);
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
          .addComponent(jradiobutton[0])
          .addComponent(jradiobutton[1])
          .addComponent(jradiobutton[2]))
        .addContainerGap()
    );

    // �]�w�����s��
    layout.setVerticalGroup(
      // �إߥ���s��
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING) 
        // �[�J�`�Ǹs�զܥ���s�դ�
        .addGroup(layout.createSequentialGroup()
          .addContainerGap()
          .addComponent(jradiobutton[0])
          // �[�J�̨ζ��j�ܸs�դ�  
          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
          .addComponent(jradiobutton[1])
          // �[�J�̨ζ��j�ܸs�դ�  
          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
          .addComponent(jradiobutton[2])
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
          .addComponent(jradiobutton[3])
          .addComponent(jradiobutton[4]))
        .addContainerGap()
    );

    // �]�w�����s��
    layout.setVerticalGroup(
      // �إߥ���s��
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING) 
        // �[�J�`�Ǹs�զܥ���s�դ�
        .addGroup(layout.createSequentialGroup()
          .addContainerGap()
          .addComponent(jradiobutton[3])
          // �[�J�̨ζ��j�ܸs�դ�  
          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
          .addComponent(jradiobutton[4])
        .addContainerGap())
    );    

    // �غc�s��
    ButtonGroup group1 = new ButtonGroup();
    group1.add(jradiobutton[3]);
    group1.add(jradiobutton[4]);    
    
    jradiobutton[5] = new JRadioButton("RadioButton 5", new ImageIcon(cl.getResource("images/greenball.gif")));
    // �]�w���Ҧr��۹��Ϲ���������m
    jradiobutton[5].setHorizontalTextPosition(SwingConstants.RIGHT);
    // �]�w���Ҧr��۹��Ϲ���������m
    jradiobutton[5].setVerticalTextPosition(SwingConstants.CENTER);
    // �]�w���U�ɪ��Ϲ�
    jradiobutton[5].setSelectedIcon(new ImageIcon(cl.getResource("images/redball.gif")));
    // ���U ActionListener
    jradiobutton[5].addActionListener(this);
  
    jradiobutton[6] = new JRadioButton("RadioButton 6", new ImageIcon(cl.getResource("images/greenball.gif")));
    // �]�w���Ҧr��۹��Ϲ���������m
    jradiobutton[6].setHorizontalTextPosition(SwingConstants.RIGHT);
    // �]�w���Ҧr��۹��Ϲ���������m
    jradiobutton[6].setVerticalTextPosition(SwingConstants.CENTER);
    // �]�w���U�ɪ��Ϲ�
    jradiobutton[6].setSelectedIcon(new ImageIcon(cl.getResource("images/redball.gif")));
    // ���U ActionListener
    jradiobutton[6].addActionListener(this);
      
    // �غc�s��
    ButtonGroup group2 = new ButtonGroup();
    group2.add(jradiobutton[5]);
    group2.add(jradiobutton[6]);

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
          .addComponent(jradiobutton[5])
          .addComponent(jradiobutton[6]))
        .addContainerGap()
    );

    // �]�w�����s��
    layout.setVerticalGroup(
      // �إߥ���s��
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING) 
        // �[�J�`�Ǹs�զܥ���s�դ�
        .addGroup(layout.createSequentialGroup()
          .addContainerGap()
          .addComponent(jradiobutton[5])
          // �[�J�̨ζ��j�ܸs�դ�  
          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
          .addComponent(jradiobutton[6])
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

    jlabel = new JLabel("JRadioButton Event");
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

  // ��ʧ@�o�ͮ�
  public void actionPerformed(ActionEvent e) {
    String source = "";

    for (int i=0; i < jradiobutton.length; i++) {
      // �P�_�֨�����O�_�Q���
      if(jradiobutton[i].isSelected()) 
        source += " " + i ;
    }

    jlabel.setText("Select JRadioButton " + source);
  }
}
