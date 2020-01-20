import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

// Synth Look and Feel
import javax.swing.plaf.synth.*;


public class SynthLookandFeelDemo extends javax.swing.JFrame {

  // Main method
  public static void main(String[] args) {
    UIManager.put("swing.boldMetal", Boolean.FALSE);
    JDialog.setDefaultLookAndFeelDecorated(true);
    JFrame.setDefaultLookAndFeelDecorated(true);
    Toolkit.getDefaultToolkit().setDynamicLayout(true);

//    try {
//      UIManager.setLookAndFeel(new javax.swing.plaf.metal.MetalLookAndFeel());
//    }
//    catch(Exception e) {
//      e.printStackTrace();
//    }

    new SynthLookandFeelDemo();
  }

  // 建構函式
  public SynthLookandFeelDemo() {
    super("Synth Look and Feel Demo");
    
    // 建立SynthLookAndFeel
    SynthLookAndFeel laf = new SynthLookAndFeel();
    
    try {
      // 自輸入串流載入Synth Look and Feel的XML檔案至Java程式
      laf.load(SynthLookandFeelDemo.class.getResourceAsStream("synth.xml"), SynthLookandFeelDemo.class);

      // 設定Synth Look and Feel
      UIManager.setLookAndFeel(laf);
    } 
    catch (UnsupportedLookAndFeelException e) {
      System.out.println("不支援Synth Look and Feel");
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 設定視窗圖示
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // 定義 Layout Manager 為 GridLayout
    this.setLayout(new GridLayout(2, 2));

    JButton[] jbutton = new JButton[4];

    // HTML標籤
    jbutton[0] = new JButton("<html><font face=Verdana size=2>HTML Text</font></html>");

    // 設定預設按鈕
    this.getRootPane().setDefaultButton(jbutton[0]);

    // 設定Normal Icon之按鈕  
    jbutton[1] = new JButton("Image", new ImageIcon(cl.getResource("images/duke.gif")));
    // 以字元設定按鍵助記碼
    jbutton[1].setMnemonic('I');
    
    // 設定Pressed Icon之按鈕  
    jbutton[2] = new JButton("Pressed Button", new ImageIcon(cl.getResource("images/duke.gif")));
    // 以字元設定按鍵助記碼
    jbutton[2].setMnemonic('P');
    // 設定標籤字串相對於圖像的水平位置
    jbutton[2].setHorizontalTextPosition(SwingConstants.CENTER);
    // 設定標籤字串相對於圖像的垂直位置
    jbutton[2].setVerticalTextPosition(SwingConstants.BOTTOM);
    // 設定按下時的圖像
    jbutton[2].setPressedIcon(new ImageIcon(cl.getResource("images/press.gif")));

    // 設Rollover Icon之按鈕  
    jbutton[3] = new JButton("Rollover Button");
    // 以字元設定按鍵助記碼
    jbutton[3].setMnemonic('R');
    // 設定標籤字串相對於圖像的水平位置
    jbutton[3].setHorizontalTextPosition(SwingConstants.CENTER);
    // 設定標籤字串相對於圖像的垂直位置
    jbutton[3].setVerticalTextPosition(SwingConstants.BOTTOM);
    // 設定是否啟用滑鼠移至按鈕上方時的Rollover效果
    jbutton[3].setRolloverEnabled(true);
    // 設定按鈕正常時的圖像
    jbutton[3].setIcon(new ImageIcon(cl.getResource("images/swing.gif")));
    // 設定滑鼠移至按鈕上方時的圖像
    jbutton[3].setRolloverIcon(new ImageIcon(cl.getResource("images/rollover.gif")));
    
    for (int i=0; i<jbutton.length; i++)
      this.add(jbutton[i]);

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
