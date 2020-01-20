import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class JPasswordFieldDemo extends javax.swing.JFrame {
   
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

    new JPasswordFieldDemo();
  }

  // �غc�禡
  public JPasswordFieldDemo() {
    super("JPasswordField Demo");

    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // �]�w�����ϥ�
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // �]�w�K�X�����ؤ��ݩʭ�
    UIManager.put("PasswordField.border", new javax.swing.border.EtchedBorder(EtchedBorder.LOWERED));
    
    // �]�w��r�Ÿ��{�{���t�v���ݩʭ�
    UIManager.put("PasswordField.caretBlinkRate", 5);
    
    // �]�w��r�Ÿ����e���C�⤧�ݩʭ�
    UIManager.put("PasswordField.caretForeground", Color.RED);
    
    // �]�w�K�X���Ҧr�����ݩʭ�
    UIManager.put("PasswordField.font", new Font("dialog", Font.BOLD, 12));
    
    // �]�w�K�X���e�����ݩʭ�
    UIManager.put("PasswordField.foreground", Color.PINK);
    
    // �]�w�Q����ɪ��I���C�⤧�ݩʭ�
    UIManager.put("PasswordField.selectionBackground", Color.GREEN);
    
    // �]�w�Q����ɪ��e���C�⤧�ݩʭ�
    UIManager.put("PasswordField.selectionForeground", Color.CYAN);
    
    // ���o�K�X���Pluggable Look and Feel�ݩʭ�
    System.out.println("JPasswordField Look and Feel: " + UIManager.getString("PasswordFieldUI"));
    
    // �w�qLayout Manager �� BorderLayout
    this.setLayout(new BorderLayout());

    JPanel jpanel = new JPanel();
    // �w�qJPanel��Layout Manager��SpringLayout
    jpanel.setLayout(new SpringLayout());

    // ���oJPanel��Layout Manager
    SpringLayout layout = (SpringLayout)jpanel.getLayout();

    JLabel[] jlabel = new JLabel[2];
    jlabel[0] = new JLabel("Password 1:");
    jlabel[0].setDisplayedMnemonic('1');
    jlabel[1] = new JLabel("Password 2:");
    jlabel[1].setDisplayedMnemonic('2');
  
    JPasswordField[] jpasswordfield = new JPasswordField[2];

    for (int i = 0; i < jpasswordfield.length; i++) {
      // �]�w�K�X��쪺���
      jpasswordfield[i] = new JPasswordField(10);

      // �]�w���ҩ��ݪ�����A�N��������ҩ��O�X�]Mnemonic Key�^��
      // �Ҩ��o��J�J�I�]Focus�^������C
      jlabel[i].setLabelFor(jpasswordfield[i]);

      jpanel.add(jlabel[i]);
      jpanel.add(jpasswordfield[i]);
    }

    // �]�w�K�X��쪺��ܤ�r
    jpasswordfield[0].setText("password 1");
    // �]�w�^���r����*
    jpasswordfield[0].setEchoChar('*');

    // �]�w�K�X��쪺��ܤ�r
    jpasswordfield[1].setText("password 2");
    // �]�w�^���r����@
    jpasswordfield[1].setEchoChar('@');

    Spring x = Spring.constant(5);
    Spring y = Spring.constant(5);

    Spring maxEast = layout.getConstraint(SpringLayout.EAST, jlabel[0]);

    for (int i = 1; i < jpasswordfield.length; i++) {
      maxEast = Spring.max(maxEast, layout.getConstraint(SpringLayout.EAST, jlabel[i]));
    }

    SpringLayout.Constraints lastConsL = null;
    SpringLayout.Constraints lastConsR = null;
    Spring parentWidth = layout.getConstraint(SpringLayout.EAST, jpanel);
    Spring rWidth = null;
    Spring maxHeightSpring = null;
    Spring rX = Spring.sum(maxEast, Spring.constant(5)); 
    Spring negRX = Spring.minus(rX); 

    for (int i = 0; i < jpasswordfield.length; i++) {
      SpringLayout.Constraints consL = layout.getConstraints(jlabel[i]);
      SpringLayout.Constraints consR = layout.getConstraints(jpasswordfield[i]);

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
    this.setSize(new Dimension(250, 100));
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }
}
