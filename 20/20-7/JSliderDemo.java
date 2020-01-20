import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.border.*;
import javax.swing.event.*;

// ��@ChangeListener����
public class JSliderDemo extends JFrame implements ChangeListener {

  JLabel jlabel = new JLabel();
  JSlider jslider;

  SliderRangeModel model = new SliderRangeModel();

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

    new JSliderDemo();
  }

  // �غc�禡
  public JSliderDemo() {
    super("BoundedRangeModel Demo");

    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // �]�w�����ϥ�
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // �w�q Layout Manager �� BorderLayout
    this.setLayout(new BorderLayout());
    
    JPanel jpanel = new JPanel();
    jpanel.setLayout(new FlowLayout());
    
    // �]�w Etched Border (Test only)
    Border border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.white, new Color(142, 142, 142)); 

    JPanel jpanel1 = new JPanel();
    jpanel1.setLayout(new FlowLayout());
    jpanel1.setBorder(BorderFactory.createTitledBorder(border, "Slider"));

    // �HBoundedRangeModel�����إߤ�JSlider���O�]MVC�^Model
    jslider = new JSlider(model);
    // �]�w�ưʶb���t�m��V
    jslider.setOrientation(SwingConstants.HORIZONTAL);
    // �]�w�ưʶb���̤j��
    jslider.setMaximum(100);
    // �]�w�ưʶb���̤p��
    jslider.setMinimum(0);
    // �]�w�ưʶb���D�n��׶��Z
    jslider.setMajorTickSpacing(20);
    // �]�w�ưʶb�����n��׶��Z
    jslider.setMinorTickSpacing(5);
    // �]�w�O�_��ܷưʶb���Ʀr����
    jslider.setPaintLabels(true);
    // �]�w�O�_��ܷưʶb�����
    jslider.setPaintTicks(true);
    // �]�w�ưʶb�۶s�]Knob�^�O�_�򩵵ۨ�׷Ʀ�
    jslider.setSnapToTicks(true); 
    jslider.putClientProperty("JSlider.isFilled", Boolean.TRUE);  
    
    // �HBoundedRangeModel���U ChangeListener
    model.addChangeListener(this); 

    jpanel1.add(jslider);
    jpanel.add(jpanel1);

    this.add(jpanel, BorderLayout.CENTER);

    jlabel.setText("Value: " + new Double(model.getDoubleValue()));
    this.add(jlabel, BorderLayout.SOUTH);

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    this.validate();
    this.setSize(new Dimension(300, 150));
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });    
  }
  
  // ��@ChangeListener��������k
  public void stateChanged(ChangeEvent e) {
    jlabel.setText("Value: " + new Double(model.getDoubleValue()));
  }
}
