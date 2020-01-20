import java.awt.*;
import java.applet.*;

public class HelloWorldApplet extends java.applet.Applet { // 繼承java.applet.Applet類別
  private String value1, value2 ;
  private int value3;

  // 建構函式
  public HelloWorldApplet() {
    System.out.println("Applet建構函式");
  }

  public void init() {
    System.out.println("Applet init()");

    // 取得 Applet 之參數
    value1 = getParameter("param1");
    value2 = getParameter("param2");
    // 型別轉換
    value3 = Integer.parseInt(getParameter("param3"));
  }

  public void start() {
    System.out.println("Applet start()");
  }
  
  public void stop() {
    System.out.println("Applet stop()");
  }
  
  public void destroy() {
    System.out.println("Applet destroy()");
  }
  
  public void paint(Graphics g) {
    System.out.println("Applet paint()");
    g.drawString("Hello World Applet: " + value1 + " " + value2 + " " + value3, 20, 50);
  }
}
