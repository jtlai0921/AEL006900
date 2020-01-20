import java.awt.*;
import java.awt.event.*;

public class GridLayoutDemo extends java.awt.Frame {

  public static void main(String args[]){
    new GridLayoutDemo();
  }
  
  // 建構函式
  public GridLayoutDemo() {
    super("Grid Layout Demo");

    final int row = 4;    // 列
    final int column = 3; // 行

    // 定義 Layout Manager 為 GridLayout
    setLayout(new GridLayout(row, column));
    
    for (int i=0; i<row; i++) {
      for (int j=0; j<column; j++) {
        add(new java.awt.Button("(" + i + ", " + j + ")"));
      }
    }

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