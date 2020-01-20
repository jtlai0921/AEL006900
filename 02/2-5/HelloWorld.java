import java.awt.*;
import java.awt.event.*;

public class HelloWorld extends java.awt.Frame { // 繼承java.awt.Frame類別
  public static void main(String args[]){
    new HelloWorld();
  }

  // 建構函式
  public HelloWorld() {
    // 設定視窗標題
    super("Hello World");

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

    // 視窗事件
    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }
}