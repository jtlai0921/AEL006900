import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class BorderDemo extends javax.swing.JFrame {
   
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

    new BorderDemo();
  }

  // 建構函式
  public BorderDemo() {
    super("Border Demo");

    final int row = 2;    // 列
    final int column = 3; // 行
    // 定義 Layout Manager 為 GridLayout
    this.setLayout(new GridLayout(row, column));

    // 建立凹下蝕刻邊框
    Border eborder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
                      
    Border border[] = new Border[6];                   

    // 建立含有標題之標題邊框並以凹下蝕刻邊框為邊框
    border[0] = BorderFactory.createTitledBorder(eborder, "Above Top", 
      TitledBorder.LEFT, TitledBorder.ABOVE_TOP); 

    border[1] = BorderFactory.createTitledBorder(eborder, "Top", 
      TitledBorder.CENTER, TitledBorder.TOP); 

    border[2] = BorderFactory.createTitledBorder(eborder, "Below Top", 
      TitledBorder.RIGHT, TitledBorder.BELOW_TOP); 

    border[3] = BorderFactory.createTitledBorder(eborder, "Above Bottom", 
      TitledBorder.LEADING, TitledBorder.ABOVE_BOTTOM); 

    border[4] = BorderFactory.createTitledBorder(eborder, "Bottom", 
      TitledBorder.CENTER, TitledBorder.BOTTOM); 

    border[5] = BorderFactory.createTitledBorder(eborder, "Below Bottom", 
      TitledBorder.TRAILING, TitledBorder.BELOW_BOTTOM); 

    String label[] = {"Left", "Center", "Right", "Leading", "Center", "Trailing"};

    for (int i=0; i<border.length; i++) {
      JPanel jpanel = new JPanel();
      jpanel.setLayout(new BorderLayout());
      // 設定物件之邊框樣式
      jpanel.setBorder(border[i]);
      jpanel.add(new JLabel(label[i], JLabel.CENTER), BorderLayout.CENTER);

      this.add(jpanel);
    }

    // 設定視窗的大小
    this.setSize(400, 150);

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
