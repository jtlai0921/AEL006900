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

  // 建構函式
  public JSliderDemo() {
    super("JSlider Demo");

    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 設定視窗圖示
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // 設定滑動軸邊框之屬性值
    UIManager.put("Slider.border", new javax.swing.border.EtchedBorder(EtchedBorder.LOWERED));
    
    // 設定滑動軸前景顏色之屬性值
    UIManager.put("Slider.foreground", Color.RED);
    
    // 設定滑動軸背景顏色之屬性值
    UIManager.put("Slider.background", Color.PINK);
    
    // 取得滑動軸Pluggable Look and Feel屬性值
    System.out.println("JSlider Look and Feel: " + UIManager.getString("SliderUI"));

    // 定義 Layout Manager 為 BorderLayout
    this.setLayout(new BorderLayout());
    
    JPanel jpanel = new JPanel();
    jpanel.setLayout(new FlowLayout());
    
    // 設定 Etched Border (Test only)
    Border border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.white, new Color(142, 142, 142)); 

    JPanel jpanel1 = new JPanel();
    jpanel1.setLayout(new FlowLayout());
    jpanel1.setBorder(BorderFactory.createTitledBorder(border, "預設"));
    JPanel jpanel2 = new JPanel();
    jpanel2.setLayout(new FlowLayout());
    jpanel2.setBorder(BorderFactory.createTitledBorder(border, "自定"));

    // 建構函式 1
    jslider1 = new JSlider();
    jslider1.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        jlabel.setText("(預設JSlider) Value: " + jslider1.getValue());
      }
    });
    
    // 建構函式 1
    jslider2 = new JSlider();
    // 設定滑動軸的配置方向
    jslider2.setOrientation(SwingConstants.HORIZONTAL);
    // 設定滑動軸的最大值
    jslider2.setMaximum(100);
    // 設定滑動軸的最小值
    jslider2.setMinimum(0);
    // 設定滑動軸的主要刻度間距
    jslider2.setMajorTickSpacing(20);
    // 設定滑動軸的次要刻度間距
    jslider2.setMinorTickSpacing(5);
    // 設定是否顯示滑動軸的數字標籤
    jslider2.setPaintLabels(true);
    // 設定是否顯示滑動軸的刻度
    jslider2.setPaintTicks(true);
    // 設定滑動軸旋鈕（Knob）是否緊延著刻度滑行
    jslider2.setSnapToTicks(true); 
    jslider2.putClientProperty("JSlider.isFilled", Boolean.TRUE);     
    jslider2.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        jlabel.setText("(自定JSlider) Value: " + jslider2.getValue());
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
