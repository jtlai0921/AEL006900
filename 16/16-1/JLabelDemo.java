import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class JLabelDemo extends javax.swing.JFrame {

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

    new JLabelDemo();
  }

  // 建構函式
  public JLabelDemo() {
    super("JLabel Demo");

    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 設定視窗圖示
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // 定義 Layout Manager 為 BorderLayout
    this.setLayout(new BorderLayout());

    // 建立凹下蝕刻邊框
    Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

    JLabel[] jlabel = new JLabel[3];
    // 純文字標籤
    jlabel[0] = new JLabel("Plain Label");
    // 設定水平對齊方式
    jlabel[0].setHorizontalAlignment(JLabel.CENTER);

    // 圖像標籤
    jlabel[1] = new JLabel("Image Label", new ImageIcon(cl.getResource("images/dukeswing.gif")), JLabel.LEADING);
    // 設定字型
    jlabel[1].setFont(new Font("dialog", Font.BOLD, 10));
    // 設定前景顏色
    jlabel[1].setForeground(new Color(120,50,0));
    // 設定標籤字串相對於圖像的水平位置
    jlabel[1].setHorizontalTextPosition(JLabel.LEADING);
    // 設定標籤字串相對於圖像的垂直位置
    jlabel[1].setVerticalTextPosition(JLabel.CENTER);
    // 以字元設定標籤的按鍵助記碼
    jlabel[1].setDisplayedMnemonic('e');
    // 設定標籤助記碼之index值
    jlabel[1].setDisplayedMnemonicIndex(4);
    // 設定標籤之邊框樣式
    jlabel[1].setBorder(border);

    // HTML標籤
    jlabel[2] = new JLabel("<html><font face=Verdana size=2 color=#FF0000><i>HTML Label</i></font></html>");
    // 設定水平對齊方式
    jlabel[2].setHorizontalAlignment(JLabel.TRAILING);
    // 設定垂直對齊方式
    jlabel[2].setVerticalAlignment(JLabel.TOP);

    this.add(jlabel[0], BorderLayout.NORTH);
    this.add(jlabel[1], BorderLayout.CENTER);
    this.add(jlabel[2], BorderLayout.SOUTH);

    // 設定視窗的大小
    this.setSize(200, 200);

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