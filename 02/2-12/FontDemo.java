import java.awt.*;
import java.awt.event.*;

public class FontDemo extends java.awt.Frame {

  public static void main(String args[]){
    new FontDemo();
  }
  
  // 建構函式
  public FontDemo() {
    super("Font Demo");

    Button button;
    
    final int row = 3;    // 列
    final int column = 2; // 行

    // 定義 Layout Manager 為 GridLayout
    setLayout(new GridLayout(row, column));

    // 粗體字型
    button = new Button("BOLD");
    button.setFont(new Font("dialog", Font.BOLD, 10));
    add(button);

    // 標準字型
    button = new Button("PLAIN");
    button.setFont(new Font("dialog", Font.PLAIN, 10));
    add(button);

    // 斜體字型
    button = new Button("ITALIC");
    button.setFont(new Font("dialog", Font.ITALIC, 10));
    add(button);

    // 粗斜體字型
    button = new Button("BOLD/ITALIC");
    button.setFont(new Font("dialog", Font.BOLD | Font.ITALIC, 10));
    add(button);

    // 標準 & 斜體
    button = new Button("PLAIN/ITALIC");
    button.setFont(new Font("dialog", Font.PLAIN | Font.ITALIC, 10));
    add(button);

    // Change Font Size
    button = new Button("Font Size");
    button.setFont(new Font("dialog", Font.ITALIC, 14));
    add(button);

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
