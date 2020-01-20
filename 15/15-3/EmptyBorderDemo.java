import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class EmptyBorderDemo extends javax.swing.JFrame {
   
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

    new EmptyBorderDemo();
  }

  // 建構函式
  public EmptyBorderDemo() {
    super("Empty Border Demo");

    final int row = 2;    // 列
    final int column = 1; // 行

    // 定義 Layout Manager 為 GridLayout
    this.setLayout(new GridLayout(row, column));

    Border border[] = new Border[2];
    
    // 建立無任何樣式之邊框

    // 設定邊框內上下左右之內嵌距離
    Insets insets = new Insets(10, 10, 10, 10);

    // 建構函式1
    border[0] = new javax.swing.border.EmptyBorder(insets);

    // 建構函式2
    border[1] = new javax.swing.border.EmptyBorder(20, 20, 20, 20);

    for (int i=0; i<border.length; i++) {
      JPanel jpanel = new JPanel();
      jpanel.setLayout(new BorderLayout());
      
      // 設定物件之邊框樣式
      jpanel.setBorder(border[i]);

      // 測試用
      // 取得邊框內上下左右之內嵌距離
      Insets inset = border[i].getBorderInsets(jpanel);
      
      System.out.println("Inset " + (i+1) + " Top, Left, Bottom, Right: " + 
        inset.top + ", " + inset.left + ", " + inset.bottom + ", " + inset.right);

      JButton jbutton = new JButton("Empty Border " + (i+1));
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
