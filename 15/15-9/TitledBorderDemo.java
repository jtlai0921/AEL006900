import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class TitledBorderDemo extends javax.swing.JFrame {
   
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

    new TitledBorderDemo();
  }

  // 建構函式
  public TitledBorderDemo() {
    super("Titled Border Demo");

    final int row = 3;    // 列
    final int column = 3; // 行

    // 定義 Layout Manager 為 GridLayout
    this.setLayout(new GridLayout(row, column));

    // 建立凹下蝕刻邊框以作為標題邊框之邊框
    Border eborder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

    Border border[] = new Border[7];

    // 設定邊框內上下左右之內嵌距離
    Insets insets = new Insets(20, 20, 20, 20);
    
    // 建立標題邊框

    // 建構函式1
    border[0] = new javax.swing.border.TitledBorder(eborder);

    // 建構函式2
    border[1] = new javax.swing.border.TitledBorder(eborder, "Default Position");

    // 建構函式3
    border[2] = new javax.swing.border.TitledBorder(eborder, "Left-Top", 
      TitledBorder.LEFT, TitledBorder.TOP);

    // 建構函式4
    border[3] = new javax.swing.border.TitledBorder(eborder, "Right-Bottom", 
      TitledBorder.RIGHT, TitledBorder.BOTTOM, 
      new Font("sansserif", Font.PLAIN | Font.ITALIC, 12));

    // 建構函式5
    border[4] = new javax.swing.border.TitledBorder(eborder, "Center-Above Top", 
      TitledBorder.CENTER, TitledBorder.ABOVE_TOP, 
      new Font("dialog", Font.BOLD | Font.ITALIC, 12),
      new Color(245,185,60));

    // 建構函式6
    border[5] = new javax.swing.border.TitledBorder("Only Title");
    
    // 複合標題邊框
    border[6] = new javax.swing.border.TitledBorder(border[2], "Right-Bottom", 
      TitledBorder.RIGHT, TitledBorder.BOTTOM);
    
    for (int i=0; i<border.length; i++) {
      JPanel jpanel = new JPanel();
      jpanel.setLayout(new BorderLayout());
      
      // 設定物件之邊框樣式
      jpanel.setBorder(border[i]);

      // 測試用
      jpanel.add(new JLabel("Titled Border " + (i+1), JLabel.CENTER), BorderLayout.CENTER);
      
      this.add(jpanel);
    }

    // 設定視窗的大小
    this.setSize(400, 250);

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