import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class CustomBorderDemo extends javax.swing.JFrame {
   
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

    new CustomBorderDemo();
  }

  // 建構函式
  public CustomBorderDemo() {
    super("Compound Border Demo");

    final int row = 2;    // 列
    final int column = 2; // 行

    // 定義 Layout Manager 為 GridLayout
    this.setLayout(new GridLayout(row, column));

    Border border[] = new Border[4];

    // 建立自訂邊框
    border[0] = new SashBorder(SashBorder.RAISED, 10, 20);

    border[1] = new SashBorder(SashBorder.RAISED, 10, 20, 
      Color.BLACK, Color.RED, Color.GREEN, Color.BLUE);

    border[2] = new SashBorder(SashBorder.LOWERED, 10, 20);

    border[3] = new SashBorder(SashBorder.LOWERED, 10, 20, 
      Color.BLACK, Color.RED, Color.GREEN, Color.BLUE);

    for (int i=0; i<border.length; i++) {
      JPanel jpanel = new JPanel();
      jpanel.setLayout(new BorderLayout());
      
      // 設定物件之邊框樣式
      jpanel.setBorder(border[i]);

      // 測試用
      jpanel.add(new JLabel("Custom Border " + (i+1), JLabel.CENTER), BorderLayout.CENTER);
      
      this.add(jpanel);
    }

    // 設定視窗的大小
    this.setSize(420, 220);

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