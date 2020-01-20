import java.awt.*;
import java.awt.event.*;

public class ColorDemo extends java.awt.Frame {

  public static void main(String args[]){
    new ColorDemo();
  }
  
  // 建構函式
  public ColorDemo() {
    super("Color Demo");

    Button button;
    
    final int row = 3;    // 列
    final int column = 1; // 行

    // 定義 Layout Manager 為 GridLayout
    setLayout(new GridLayout(row, column));

    button = new Button("Default");
    add(button);

    button = new Button("Color");
    // 設定背景顏色
    button.setBackground(Color.PINK);
    // 設定前景顏色
    button.setForeground(Color.CYAN);
    add(button);

    button = new Button("Custom");
    // 自訂背景顏色
    button.setBackground(new Color(120,50,0));
    // 自訂前景顏色
    button.setForeground(new Color(245,185,60));
    add(button);

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
