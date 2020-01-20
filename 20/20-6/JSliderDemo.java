import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.border.*;
import javax.swing.event.*;

public class JSliderDemo extends JFrame {

  JLabel jlabel = new JLabel();
  JSlider jslider1, jslider2;

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
    super("JSlider Demo");

    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // �]�w�����ϥ�
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // �]�w�ưʶb��ؤ��ݩʭ�
    UIManager.put("Slider.border", new javax.swing.border.EtchedBorder(EtchedBorder.LOWERED));
    
    // �]�w�ưʶb�e���C�⤧�ݩʭ�
    UIManager.put("Slider.foreground", Color.RED);
    
    // �]�w�ưʶb�I���C�⤧�ݩʭ�
    UIManager.put("Slider.background", Color.PINK);
    
    // ���o�ưʶbPluggable Look and Feel�ݩʭ�
    System.out.println("JSlider Look and Feel: " + UIManager.getString("SliderUI"));

    // �w�q Layout Manager �� BorderLayout
    this.setLayout(new BorderLayout());
    
    JPanel jpanel = new JPanel();
    jpanel.setLayout(new FlowLayout());
    
    // �]�w Etched Border (Test only)
    Border border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.white, new Color(142, 142, 142)); 

    JPanel jpanel1 = new JPanel();
    jpanel1.setLayout(new FlowLayout());
    jpanel1.setBorder(BorderFactory.createTitledBorder(border, "�w�]"));
    JPanel jpanel2 = new JPanel();
    jpanel2.setLayout(new FlowLayout());
    jpanel2.setBorder(BorderFactory.createTitledBorder(border, "�۩w"));

    // �غc�禡 1
    jslider1 = new JSlider();
    jslider1.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        jlabel.setText("(�w�]JSlider) Value: " + jslider1.getValue());
      }
    });
    
    // �غc�禡 1
    jslider2 = new JSlider();
    // �]�w�ưʶb���t�m��V
    jslider2.setOrientation(SwingConstants.HORIZONTAL);
    // �]�w�ưʶb���̤j��
    jslider2.setMaximum(100);
    // �]�w�ưʶb���̤p��
    jslider2.setMinimum(0);
    // �]�w�ưʶb���D�n��׶��Z
    jslider2.setMajorTickSpacing(20);
    // �]�w�ưʶb�����n��׶��Z
    jslider2.setMinorTickSpacing(5);
    // �]�w�O�_��ܷưʶb���Ʀr����
    jslider2.setPaintLabels(true);
    // �]�w�O�_��ܷưʶb�����
    jslider2.setPaintTicks(true);
    // �]�w�ưʶb�۶s�]Knob�^�O�_�򩵵ۨ�׷Ʀ�
    jslider2.setSnapToTicks(true); 
    jslider2.putClientProperty("JSlider.isFilled", Boolean.TRUE);     
    jslider2.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        jlabel.setText("(�۩wJSlider) Value: " + jslider2.getValue());
      }
    });

    jpanel1.add(jslider1);
    jpanel2.add(jslider2);

    jpanel.add(jpanel1);
    jpanel.add(jpanel2);

    this.add(jpanel, BorderLayout.CENTER);

    jlabel.setText("(Default JSlider) Value: " + jslider1.getValue());
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
    this.setSize(new Dimension(250, 220));
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });    
  }
}
