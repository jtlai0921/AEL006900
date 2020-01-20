import java.awt.*;
import java.awt.event.*;

public class CursorDemo extends java.awt.Frame {

  public static void main(String args[]){
    new CursorDemo();
  }
  
  // 建構函式
  public CursorDemo() {
    super("Cursor Demo");

    Button button;
    
    final int row = 7;    // 列
    final int column = 2; // 行

    // 定義 Layout Manager 為 GridLayout
    setLayout(new GridLayout(row, column));
    
    // 預設滑鼠指標
    button = new Button("DEFAULT");
    button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    add(button);

    // 選擇精確度之滑鼠指標
    button = new Button("CROSSHAIR");
    button.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
    add(button);

    // 選擇連線（手形）之滑鼠指標
    button = new Button("HAND");
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    add(button);

    // 移動滑鼠指標
    button = new Button("MOVE");
    button.setCursor(new Cursor(Cursor.MOVE_CURSOR));
    add(button);

    // 選擇文字之滑鼠指標
    button = new Button("TEXT");
    button.setCursor(new Cursor(Cursor.TEXT_CURSOR));
    add(button);

    // 等候（忙碌中）滑鼠指標
    button = new Button("WAIT");
    button.setCursor(new Cursor(Cursor.WAIT_CURSOR));
    add(button);

    // 調整左側水平大小之滑鼠指標
    button = new Button("W RESIZE");
    button.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
    add(button);

    // 調整上方垂直大小之滑鼠指標
    button = new Button("N RESIZE");
    button.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
    add(button);

    // 調整下方垂直大小之滑鼠指標
    button = new Button("S RESIZE");
    button.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
    add(button);

    // 調整右側水平大小之滑鼠指標
    button = new Button("E RESIZE");
    button.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
    add(button);

    // 調整左上方對角線大小之滑鼠指標
    button = new Button("NW RESIZE");
    button.setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR));
    add(button);

    // 調整左下方對角線大小之滑鼠指標
    button = new Button("SW RESIZE");
    button.setCursor(new Cursor(Cursor.SW_RESIZE_CURSOR));
    add(button);

    // 調整右下方對角線大小之滑鼠指標
    button = new Button("SE RESIZE");
    button.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
    add(button);

    // 調整右上方對角線大小之滑鼠指標
    button = new Button("NE RESIZE");
    button.setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR));
    add(button);

    // 設定視窗的大小
    this.setSize(300, 300);

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
