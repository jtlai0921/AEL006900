import java.awt.*;
import java.applet.*;

public class HelloWorldApplet extends java.applet.Applet { // �~��java.applet.Applet���O
  private String value1, value2 ;
  private int value3;

  // �غc�禡
  public HelloWorldApplet() {
    System.out.println("Applet�غc�禡");
  }

  public void init() {
    System.out.println("Applet init()");

    // ���o Applet ���Ѽ�
    value1 = getParameter("param1");
    value2 = getParameter("param2");
    // ���O�ഫ
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
