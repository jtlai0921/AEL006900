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

    final int row = 4;    // 列
    final int column = 2; // 行

    // 定義 Layout Manager 為 GridLayout
    this.setLayout(new GridLayout(row, column));

    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();
    
    Border border[] = new Border[8];                   

    // 建立凹下斜邊邊框
    border[0] = BorderFactory.createBevelBorder(BevelBorder.LOWERED);
    // 建立凸起斜邊邊框
    border[1] = BorderFactory.createRaisedBevelBorder();
    // 建立凹下蝕刻邊框
    border[2] = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
    // 建立凸起蝕刻邊框
    border[3] = BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.white, new Color(150, 150, 150));
    // 建立無任何樣式之邊框
    border[4] = BorderFactory.createEmptyBorder();
    // 建立線條邊框
    border[5] = BorderFactory.createLineBorder(Color.red);
    // 建立自訂框邊樣式之邊框
    border[6] = BorderFactory.createMatteBorder(9, 9, 9, 9, new ImageIcon(cl.getResource("images/border.gif")));
    // 建立複合邊框
    // 邊框內緣為凹下斜邊邊框
    // 邊框外緣為凸起斜邊邊框
    border[7] = BorderFactory.createCompoundBorder(border[0], border[1]);

    String label[] = {
      "Lowered Bevel Border", "Raised Bevel Border", 
      "Lowered Etched Border", "Raised Etched Border",
      "Empty Border", "Line Border", 
      "Matte Border", "Compound Border" };

    for (int i=0; i<border.length; i++) {
      JPanel jpanel = new JPanel();
      jpanel.setLayout(new BorderLayout());
      // 設定物件之邊框樣式
      jpanel.setBorder(border[i]);
      jpanel.add(new JLabel(label[i], JLabel.CENTER), BorderLayout.CENTER);

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
