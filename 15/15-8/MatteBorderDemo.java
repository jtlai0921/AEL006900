import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class MatteBorderDemo extends javax.swing.JFrame {
   
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

    new MatteBorderDemo();
  }

  // 建構函式
  public MatteBorderDemo() {
    super("Matte Border Demo");

    final int row = 3;    // 列
    final int column = 2; // 行

    // 定義 Layout Manager 為 GridLayout
    this.setLayout(new GridLayout(row, column));

    Border border[] = new Border[5];

    // 設定邊框內上下左右之內嵌距離
    Insets insets = new Insets(20, 20, 20, 20);
    
    // 建立Matte邊框

    // 建構函式1
    border[0] = new javax.swing.border.MatteBorder(new ImageIcon("images/border.gif"));

    // 建構函式2
    border[1] = new javax.swing.border.MatteBorder(insets, new Color(120,50,0));

    // 建構函式4
    border[2] = new javax.swing.border.MatteBorder(10, 10, 10, 10, Color.GRAY);

    // 建構函式3
    border[3] = new javax.swing.border.MatteBorder(insets, new ImageIcon("images/border.gif"));

    // 建構函式5
    border[4] = new javax.swing.border.MatteBorder(5, 5, 5, 5, new ImageIcon("images/border.gif"));

    for (int i=0; i<border.length; i++) {
      JPanel jpanel = new JPanel();
      jpanel.setLayout(new BorderLayout());
      
      // 設定物件之邊框樣式
      jpanel.setBorder(border[i]);

      // 測試用
      JButton jbutton = new JButton("Matte Border " + (i+1));
      jpanel.add(jbutton, BorderLayout.CENTER);
      
      this.add(jpanel);
    }

    // 設定視窗的大小
    this.setSize(320, 250);

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