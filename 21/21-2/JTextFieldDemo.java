import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class JTextFieldDemo extends javax.swing.JFrame {
   
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

    new JTextFieldDemo();
  }

  // �غc�禡
  public JTextFieldDemo() {
    super("JTextField Demo");

    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // �]�w�����ϥ�
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // �]�w��r�����ؤ��ݩʭ�
    UIManager.put("TextField.border", new javax.swing.border.EtchedBorder(EtchedBorder.LOWERED));
    
    // �]�w��r�Ÿ��{�{���t�v���ݩʭ�
    UIManager.put("TextField.caretBlinkRate", 5);
    
    // �]�w��r�Ÿ����e���C�⤧�ݩʭ�
    UIManager.put("TextField.caretForeground", Color.RED);
    
    // �]�w��r���Ҧr�����ݩʭ�
    UIManager.put("TextField.font", new Font("dialog", Font.BOLD, 12));
    
    // �]�w��r���e�����ݩʭ�
    UIManager.put("TextField.foreground", Color.PINK);
    
    // �]�w�Q����ɪ��I���C�⤧�ݩʭ�
    UIManager.put("TextField.selectionBackground", Color.GREEN);
    
    // �]�w�Q����ɪ��e���C�⤧�ݩʭ�
    UIManager.put("TextField.selectionForeground", Color.CYAN);
    
    // ���o��r���Pluggable Look and Feel�ݩʭ�
    System.out.println("JTextField Look and Feel: " + UIManager.getString("TextFieldUI"));
    
    // �w�qLayout Manager �� BorderLayout
    this.setLayout(new BorderLayout());

    JPanel jpanel = new JPanel();
    // �w�qJPanel��Layout Manager��SpringLayout
    jpanel.setLayout(new SpringLayout());

    // ���oJPanel��Layout Manager
    SpringLayout layout = (SpringLayout)jpanel.getLayout();

    JLabel[] jlabel = new JLabel[4];
    jlabel[0] = new JLabel("Name:");
    jlabel[0].setDisplayedMnemonic('N');
    jlabel[1] = new JLabel("Birth:");
    jlabel[1].setDisplayedMnemonic('B');
    jlabel[2] = new JLabel("Tel.:");
    jlabel[2].setDisplayedMnemonic('T');
    jlabel[3] = new JLabel("E-Mail:");
    jlabel[3].setDisplayedMnemonic('E');

    JTextField[] jtextfield = new JTextField[4];

    for (int i = 0; i < jtextfield.length; i++) {
      jtextfield[i] = new JTextField(10);

      // �]�w���ҩ��ݪ�����A�N��������ҩ��O�X�]Mnemonic Key�^��
      // �Ҩ��o��J�J�I�]Focus�^������C
      jlabel[i].setLabelFor(jtextfield[i]);

      jpanel.add(jlabel[i]);
      jpanel.add(jtextfield[i]);
    }

    // �]�w��r��쪺��ܤ�r
    jtextfield[0].setText("Athena");
    // �]�w��r��쪺���
    // jtextfield[0].setColumns(8);
    // �]�w�I���C��
    jtextfield[0].setBackground(Color.PINK);
    // �]�w�e���C��
    jtextfield[0].setForeground(Color.CYAN);
    // ����Ҧ�����r���e
    jtextfield[0].selectAll();

    // �]�w��r��쪺��ܤ�r
    jtextfield[1].setText("2001/05/10");
    // �]�w��r��쪺��������覡
    jtextfield[1].setHorizontalAlignment(SwingConstants.RIGHT);

    // �]�w��r��쪺��ܤ�r
    jtextfield[2].setText("1234567890");
    // �]�w�r��
    jtextfield[2].setFont(new Font("dialog", Font.PLAIN | Font.ITALIC, 12));
    // �]�w��Хثe����m
    jtextfield[2].setCaretPosition(5);

    // �]�w��r��쪺��ܤ�r
    jtextfield[3].setText("athena@yahoo.com.tw");
    // �]�w�Q�����r���e���C��
    jtextfield[3].setSelectedTextColor(Color.RED);
    // �]�w�Q�����r���e���ҩl��m
    jtextfield[3].setSelectionStart(3);
    // �]�w�Q�����r���e��������m
    jtextfield[3].setSelectionEnd(10);

    Spring x = Spring.constant(5);
    Spring y = Spring.constant(5);

    Spring maxEast = layout.getConstraint(SpringLayout.EAST, jlabel[0]);

    for (int i = 1; i < jtextfield.length; i++) {
      maxEast = Spring.max(maxEast, layout.getConstraint(SpringLayout.EAST, jlabel[i]));
    }

    SpringLayout.Constraints lastConsL = null;
    SpringLayout.Constraints lastConsR = null;
    Spring parentWidth = layout.getConstraint(SpringLayout.EAST, jpanel);
    Spring rWidth = null;
    Spring maxHeightSpring = null;
    Spring rX = Spring.sum(maxEast, Spring.constant(5)); 
    Spring negRX = Spring.minus(rX); 

    for (int i = 0; i < jtextfield.length; i++) {
      SpringLayout.Constraints consL = layout.getConstraints(jlabel[i]);
      SpringLayout.Constraints consR = layout.getConstraints(jtextfield[i]);

      consL.setX(x);
      consR.setX(rX);

      rWidth = consR.getWidth(); 
      
      consR.setWidth(Spring.sum(Spring.sum(parentWidth, negRX), Spring.constant(-5)));

      if (i == 0) {
        consL.setY(y);
        consR.setY(y);
        maxHeightSpring = Spring.sum(y, Spring.max(consL.getHeight(), consR.getHeight()));
      } 
      else {  
        Spring y1 = Spring.sum(Spring.max(lastConsL.getConstraint(SpringLayout.SOUTH), lastConsR.getConstraint(SpringLayout.SOUTH)), Spring.constant(5));

        consL.setY(y1);
        consR.setY(y1);
        maxHeightSpring = Spring.sum(Spring.constant(5), Spring.sum(maxHeightSpring, Spring.max(consL.getHeight(), consR.getHeight())));
      }
      lastConsL = consL;
      lastConsR = consR;
    }  
    
    SpringLayout.Constraints consParent = layout.getConstraints(jpanel);
    consParent.setConstraint(SpringLayout.EAST, Spring.sum(rX, Spring.sum(rWidth, Spring.constant(5))));
    consParent.setConstraint(SpringLayout.SOUTH, Spring.sum(maxHeightSpring, Spring.constant(5)));

    this.add(jpanel, BorderLayout.CENTER);

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    this.validate();
    this.setSize(new Dimension(250, 150));
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }
}
