import java.awt.*;
import java.applet.*;

public class HelloWorldApplet extends java.applet.Applet { // �~��java.applet.Applet���O

  // �غc�禡
  public HelloWorldApplet() {
    System.out.println("Applet�غc�禡");
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