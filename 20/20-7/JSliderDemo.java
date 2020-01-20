import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.border.*;
import javax.swing.event.*;

// 實作ChangeListener介面
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

  // 建構函式
  public JSliderDemo() {
    super("BoundedRangeModel Demo");

    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 設定視窗圖示
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // 定義 Layout Manager 為 BorderLayout
    this.setLayout(new BorderLayout());
    
    JPanel jpanel = new JPanel();
    jpanel.setLayout(new FlowLayout());
    
    // 設定 Etched Border (Test only)
    Border border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.white, new Color(142, 142, 142)); 

    JPanel jpanel1 = new JPanel();
    jpanel1.setLayout(new FlowLayout());
    jpanel1.setBorder(BorderFactory.createTitledBorder(border, "Slider"));

    // 以BoundedRangeModel介面建立之JSlider類別（MVC）Model
    jslider = new JSlider(model);
    // 設定滑動軸的配置方向
    jslider.setOrientation(SwingConstants.HORIZONTAL);
    // 設定滑動軸的最大值
    jslider.setMaximum(100);
    // 設定滑動軸的最小值
    jslider.setMinimum(0);
    // 設定滑動軸的主要刻度間距
    jslider.setMajorTickSpacing(20);
    // 設定滑動軸的次要刻度間距
    jslider.setMinorTickSpacing(5);
    // 設定是否顯示滑動軸的數字標籤
    jslider.setPaintLabels(true);
    // 設定是否顯示滑動軸的刻度
    jslider.setPaintTicks(true);
    // 設定滑動軸旋鈕（Knob）是否緊延著刻度滑行
    jslider.setSnapToTicks(true); 
    jslider.putClientProperty("JSlider.isFilled", Boolean.TRUE);  
    
    // 以BoundedRangeModel註冊 ChangeListener
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
  
  // 實作ChangeListener介面之方法
  public void stateChanged(ChangeEvent e) {
    jlabel.setText("Value: " + new Double(model.getDoubleValue()));
  }
}
