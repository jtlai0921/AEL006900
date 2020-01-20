import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class EtchedBorderDemo extends javax.swing.JFrame {
   
  // Main method
  public static void main(String[] args) {
    UIManager.put("swing.boldMetal", Boolean.FALSE);
    JDialog.setDefaultLookAndFeelDecorated(true);
    JFrame.setDefaultLookAndFeelDecorated(true);
    Toolkit.getDefaultToolkit().setDynamicLayout(true);

    try {
      UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    new EtchedBorderDemo();
  }

  // 建構函式
  public EtchedBorderDemo() {
    super("Etched Border Demo");

    final int row = 4;    // 列
    final int column = 1; // 行

    // 定義 Layout Manager 為 GridLayout
    this.setLayout(new GridLayout(row, column));

    Border border[] = new Border[4];
    
    // 建立蝕刻邊框

    // 建構函式1
    border[0] = new javax.swing.border.EtchedBorder();

    // 建構函式2
    // 凸起之蝕刻邊框
    border[1] = new javax.swing.border.EtchedBorder(EtchedBorder.RAISED);

    // 建構函式3
    // 凹下之蝕刻邊框
    border[2] = new javax.swing.border.EtchedBorder(Color.PINK, Color.CYAN);

    // 建構函式4
    // 凹下之蝕刻邊框
    border[3] = new javax.swing.border.EtchedBorder(EtchedBorder.RAISED, 
      new Color(120,50,0), new Color(245,185,60));

    for (int i=0; i<border.length; i++) {
      JPanel jpanel = new JPanel();
      jpanel.setLayout(new BorderLayout());
      
      // 設定物件之邊框樣式
      jpanel.setBorder(border[i]);

      // 測試用
      JButton jbutton = new JButton("Etched Border " + (i+1));
      jpanel.add(jbutton, BorderLayout.CENTER);
      
      this.add(jpanel);
    }

    // 設定視窗的大小
    this.setSize(250, 200);

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();

    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;

    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;

    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    // 顯示視窗
    this.setVisible(true);
    
    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }
}