import java.awt.*;
import java.awt.event.*;
import java.applet.*;

public class HelloWorld extends Applet {
  private String value1, value2 ;
  private int value3;

  public static void main(String args[]){
    HelloWorldFrame frame = new HelloWorldFrame();
    
    // 設定視窗的大小
    frame.setSize(200, 200);

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = frame.getSize();

    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;

    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;

    frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    // 顯示視窗
    frame.setVisible(true);
  }

  // 建構函式
  public HelloWorld() {
    System.out.println("Applet建構函式");
  }
  
  public void init() {
    System.out.println("Applet init()");

    // 取得 Applet 之參數
    value1 = getParameter("param1");
    value2 = getParameter("param2");
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

class HelloWorldFrame extends java.awt.Frame {
  // 建構函式
  public HelloWorldFrame() {
    super("Hello World");

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }
  
  public void paint(Graphics g) {
    g.drawString("Hello World Frame", 50, 50);
  }
}