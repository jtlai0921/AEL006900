import java.awt.*;

public class HelloWorld { 
  public static void main(String args[]){
    // 建立java.awt.Frame物件並名為frame
    java.awt.Frame frame = new Frame();
    // 設定視窗標題
    frame.setTitle("Hello World");
    // 設定視窗的大小
    frame.setSize(200, 200);
    // 顯示視窗
    frame.setVisible(true);
  }
}