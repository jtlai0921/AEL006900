import java.awt.*;
import java.applet.*;

public class HelloWorldApplet extends java.applet.Applet { // 繼承java.applet.Applet類別

  // 建構函式
  public HelloWorldApplet() {
    System.out.println("Applet建構函式");
  }

  public void init() {
    System.out.println("Applet init()");
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
    g.drawString("Hello World Applet", 50, 50);
  }  
}