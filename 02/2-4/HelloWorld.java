import java.awt.*;

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
    // 顯示視窗
    this.setVisible(true);
  }
}