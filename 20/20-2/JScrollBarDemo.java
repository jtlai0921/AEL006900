import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.border.*;
import javax.swing.event.*;

public class JScrollBarDemo extends JFrame {

  JScrollBar jscrollbar1, jscrollbar2, jscrollbar3;
  JPanel colorPanel;
  JLabel jlabel = new JLabel();
  
  int red, green, blue;

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

    new JScrollBarDemo();
  }

  // 建構函式
  public JScrollBarDemo() {
    super("JScrollBar Demo");

    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 設定視窗圖示
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // 定義 Layout Manager 為 BorderLayout
    this.setLayout(new BorderLayout());

    red = 0;
    green = 0;
    blue = 0;

    colorPanel = new JPanel();
    colorPanel.setLayout(new FlowLayout());
    colorPanel.setBackground(new Color(0, 0, 0));
    
    // 設定 Etched Border (Demo only)
    Border border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.white, new Color(142, 142, 142)); 

    JPanel jpanel1 = new JPanel();
    jpanel1.setLayout(new FlowLayout());
    jpanel1.setBorder(BorderFactory.createTitledBorder(border, "Red"));
    JPanel jpanel2 = new JPanel();
    jpanel2.setLayout(new FlowLayout());
    jpanel2.setBorder(BorderFactory.createTitledBorder(border, "Green"));
    JPanel jpanel3 = new JPanel();
    jpanel3.setLayout(new FlowLayout());
    jpanel3.setBorder(BorderFactory.createTitledBorder(border, "Blue"));

    // red
    jscrollbar1 = new JScrollBar(SwingConstants.HORIZONTAL, 0, 0, 0, 255);
    jscrollbar1.setPreferredSize(new Dimension(200, 20));
    jscrollbar1.addAdjustmentListener(new AdjustmentListener() {
      public void adjustmentValueChanged(AdjustmentEvent e) {
        red = jscrollbar1.getValue();
        
        colorPanel.setBackground(new Color(red, green, blue));
        jlabel.setText("Color red: " + jscrollbar1.getValue() + ", green: " + jscrollbar2.getValue() + ", blue: " + jscrollbar3.getValue());
      }
    });
    
    // green
    jscrollbar2 = new JScrollBar(SwingConstants.HORIZONTAL, 0, 0, 0, 255);
    jscrollbar2.setPreferredSize(new Dimension(200, 20));
    jscrollbar2.addAdjustmentListener(new AdjustmentListener() {
      public void adjustmentValueChanged(AdjustmentEvent e) {
        green = jscrollbar2.getValue();
        
        colorPanel.setBackground(new Color(red, green, blue));
        jlabel.setText("Color red: " + jscrollbar1.getValue() + ", green: " + jscrollbar2.getValue() + ", blue: " + jscrollbar3.getValue());
      }
    });

    // blue
    jscrollbar3 = new JScrollBar(SwingConstants.HORIZONTAL, 0, 0, 0, 255);
    jscrollbar3.setPreferredSize(new Dimension(200, 20));
    jscrollbar3.addAdjustmentListener(new AdjustmentListener() {
      public void adjustmentValueChanged(AdjustmentEvent e) {
        blue = jscrollbar3.getValue();
        
        colorPanel.setBackground(new Color(red, green, blue));
        jlabel.setText("Color red: " + jscrollbar1.getValue() + ", green: " + jscrollbar2.getValue() + ", blue: " + jscrollbar3.getValue());
      }
    });

    jpanel1.add(jscrollbar1);
    jpanel2.add(jscrollbar2);
    jpanel3.add(jscrollbar3);

    colorPanel.add(jpanel1);
    colorPanel.add(jpanel2);
    colorPanel.add(jpanel3);

    this.add(colorPanel, BorderLayout.CENTER);

    jlabel.setText("Color red: " + jscrollbar1.getValue() + ", green: " + jscrollbar2.getValue() + ", blue: " + jscrollbar3.getValue());
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
    this.setSize(new Dimension(300, 250));
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });    
  }
}
