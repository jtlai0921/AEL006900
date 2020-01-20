import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.text.*;
import java.text.NumberFormat.*;

public class JFormattedTextFieldDemo extends javax.swing.JFrame {
   
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
    
    JLabel[] jlabel = new JLabel[2];
    jlabel[0] = new JLabel("Currency:");
    jlabel[0].setDisplayedMnemonic('C');
    jlabel[1] = new JLabel("Percent:");
    jlabel[1].setDisplayedMnemonic('P');

    JFormattedTextField[] jformattedtf = new JFormattedTextField[2];

    // �w�]�榡
    NumberFormat cdefaultFormat = NumberFormat.getCurrencyInstance();
    // �]�w�p���I��Ƭ�2��
    cdefaultFormat.setMinimumFractionDigits(2);
    
    // ��ܮ榡
    NumberFormat cdisplayFormat = NumberFormat.getCurrencyInstance();
    // �]�w�p���I��Ƭ�2��
    cdisplayFormat.setMinimumFractionDigits(2);
    
    // �s��榡
    NumberFormat ceditFormat = NumberFormat.getCurrencyInstance();
    // �]�w�p���I��Ƭ�3��
    cdisplayFormat.setMinimumFractionDigits(3);
    
    // �HDefaultFormatterFactory�إ�JFormattedTextField
    jformattedtf[0] = new JFormattedTextField(
      new DefaultFormatterFactory(
        new NumberFormatter(cdefaultFormat), 
        new NumberFormatter(cdisplayFormat), 
        new NumberFormatter(ceditFormat)));
    jformattedtf[0].setValue(new java.lang.Float(9003510.205));

    // �w�]�榡
    NumberFormat pdefaultFormat = NumberFormat.getPercentInstance();
    // �]�w�p���I��Ƭ�2��
    pdefaultFormat.setMinimumFractionDigits(2);
    
    // ��ܮ榡
    NumberFormat pdisplayFormat = NumberFormat.getPercentInstance();
    // �]�w�p���I��Ƭ�2��
    pdisplayFormat.setMinimumFractionDigits(2);
    
    // �s��榡
    NumberFormat peditFormat = NumberFormat.getNumberInstance();
    // �]�w�p���I��Ƭ�3��
    peditFormat.setMinimumFractionDigits(3);

    // �s��榡
    NumberFormatter peditFormatter = new NumberFormatter(peditFormat) {
      // �N�r���ഫ�����NObject����
      public Object stringToValue(String text) throws ParseException {
        Number number = (Number) super.stringToValue(text);

        if (number != null) 
          number = new Double(number.doubleValue() / 100.0);

        return number;
      }
      
      // �N���NObject�����ഫ���r��
      public String valueToString(Object value) throws ParseException {
        Number number = (Number) value;

        if (number != null) 
          number = new Double(number.doubleValue() * 100.0);

        return super.valueToString(number);
      }
    };

    // �إ߹w�]�榡DefaultFormatterFactory
    DefaultFormatterFactory dff = new DefaultFormatterFactory(
      new NumberFormatter(pdefaultFormat), 
      new NumberFormatter(pdisplayFormat), 
      peditFormatter);
    
    // Percent
    jformattedtf[1] = new JFormattedTextField(dff);
    jformattedtf[1].setValue(new Double(0.5));

    for (int i = 0; i < jformattedtf.length; i++) {
      // �]�w���ҩ��ݪ�����A�N����������ҩ��O�X�]Mnemonic Key�^��
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
    this.setSize(new Dimension(250, 100));
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }
}