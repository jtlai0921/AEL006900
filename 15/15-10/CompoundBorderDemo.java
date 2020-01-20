import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class CompoundBorderDemo extends javax.swing.JFrame {
   
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

    new CompoundBorderDemo();
  }

  // 建構函式
  public CompoundBorderDemo() {
    super("Compound Border Demo");

    final int row = 2;    // 列
    final int column = 1; // 行

    // 定義 Layout Manager 為 GridLayout
    this.setLayout(new GridLayout(row, column));

    // 建立凹下蝕刻邊框
    Border inborder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

    // 建立凹下斜邊邊框
    Border outborder = BorderFactory.createBevelBorder(BevelBorder.LOWERED);

    Border border[] = new Border[2];

    // 建立複合邊框

    // 建構函式1
    border[0] = new javax.swing.border.CompoundBorder();

    // 建構函式2
    border[1] = new javax.swing.border.CompoundBorder(outborder, inborder);

    for (int i=0; i<border.length; i++) {
      JPanel jpanel = new JPanel();
      jpanel.setLayout(new BorderLayout());
      
      // 設定物件之邊框樣式
      jpanel.setBorder(border[i]);

      // 測試用
      jpanel.add(new JLabel("Compound Border " + (i+1), JLabel.CENTER), BorderLayout.CENTER);
      
      this.add(jpanel);
    }

    // 設定視窗的大小
    this.setSize(200, 150);

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