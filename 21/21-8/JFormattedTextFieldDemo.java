import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.text.*;
import java.text.NumberFormat.*;

public class JFormattedTextFieldDemo extends javax.swing.JFrame implements FocusListener {
   
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

    new JFormattedTextFieldDemo();
  }

  // �غc�禡
  public JFormattedTextFieldDemo() {
    super("JFormattedTextField Demo");

    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // �]�w�����ϥ�
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

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
    jlabel[1] = new JLabel("Tel:");
    jlabel[1].setDisplayedMnemonic('T');
    jlabel[2] = new JLabel("Fax:");
    jlabel[2].setDisplayedMnemonic('F');
    jlabel[3] = new JLabel("Zip Code:");
    jlabel[3].setDisplayedMnemonic('Z');

    JFormattedTextField[] jformattedtf = new JFormattedTextField[4];

    MaskFormatter[] formatter = new MaskFormatter[4];
    
    String mask[]={"U************", "(##) ###-####", "(##) ###-####", "#####"};

    for (int i=0; i<formatter.length; i++){
      try {
        formatter[i] = new MaskFormatter(mask[i]);
  
        // �]�wPlace Holder�r��
        formatter[i].setPlaceholderCharacter('_');
        // �]�w�O�_�B�z�B�n�r��H�~���r��
        formatter[i].setValueContainsLiteralCharacters(true);
      } 
      catch (ParseException e) {
        e.printStackTrace();
        System.exit(0);
      }
      
      // �HMaskFormatter�إ�JFormattedTextField
      jformattedtf[i] = new JFormattedTextField(formatter[i]);

      // ���U FocusListener
      jformattedtf[i].addFocusListener(this);
    }

    // �HPlace Holder�r��]�w��r��쪺���e (Demo only)
    // formatter[0].setPlaceholder("(03) 351-3400");
    // formatter[1].setPlaceholder("(03) 352-2500");
    // formatter[2].setPlaceholder("32097");
    // formatter[3].setPlaceholder("Name");

    for (int i = 0; i < jformattedtf.length; i++) {
      // �]�w���ҩ��ݪ�����A�N��������ҩ��O�X�]Mnemonic Key�^��
      // �Ҩ��o��J�J�I�]Focus�^������C
      jlabel[i].setLabelFor(jformattedtf[i]);

      jpanel.add(jlabel[i]);
      jpanel.add(jformattedtf[i]);
    }

    Spring x = Spring.constant(5);
    Spring y = Spring.constant(5);

    Spring maxEast = layout.getConstraint(SpringLayout.EAST, jlabel[0]);

    for (int i = 1; i < jformattedtf.length; i++) {
      maxEast = Spring.max(maxEast, layout.getConstraint(SpringLayout.EAST, jlabel[i]));
    }

    SpringLayout.Constraints lastConsL = null;
    SpringLayout.Constraints lastConsR = null;
    Spring parentWidth = layout.getConstraint(SpringLayout.EAST, jpanel);
    Spring rWidth = null;
    Spring maxHeightSpring = null;
    Spring rX = Spring.sum(maxEast, Spring.constant(5)); 
    Spring negRX = Spring.minus(rX); 

    for (int i = 0; i < jformattedtf.length; i++) {
      SpringLayout.Constraints consL = layout.getConstraints(jlabel[i]);
      SpringLayout.Constraints consR = layout.getConstraints(jformattedtf[i]);

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
    this.setSize(new Dimension(250, 160));
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }

  // ���o��J�J�I��
  public void focusGained(FocusEvent e) {
    Component com = e.getComponent();

    if (com instanceof JFormattedTextField) {
      final JFormattedTextField jformattedtf = (JFormattedTextField) com;

      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          //����Ҧ�����r���e
          jformattedtf.selectAll();
        }
      });
    } 
  }

  // ���h��J�J�I��
  public void focusLost(FocusEvent e) {} 
}
