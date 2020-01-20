import java.awt.*;
import java.applet.*;
import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class HelloWorldJApplet extends javax.swing.JApplet {

  // 建構函式
  public HelloWorldJApplet() {
    System.out.println("JApplet建構函式");
  }

  static {
    try {
      UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
    }
    catch(Exception e) {}
  }
  
  public void init() {
    System.out.println("JApplet init()");

    DisplayPanel displayPanel = new DisplayPanel();

    // JDK 1.4的用法
    // 取得其Content Pane 
    //Container contentPane = getContentPane();
    // 定義 Layout Manager 為 BorderLayout
    //contentPane.setLayout(new BorderLayout());
    // 將物件加至Content Pane中
    //contentPane.add(displayPanel, BorderLayout.CENTER);

    // JDK 5.0之後的用法
    // 直接定義JFrame之Layout BorderLayout
    this.setLayout(new BorderLayout());
    this.add(displayPanel, BorderLayout.CENTER);
  }

  public void start() {
    System.out.println("JApplet start()");
  }
  
  public void stop() {
    System.out.println("JApplet stop()");
  }
  
  public void destroy() {
    System.out.println("JApplet destroy()");
  }
}

class DisplayPanel extends javax.swing.JPanel {
  // 建構函式
  public DisplayPanel() {
  }

  public void paintComponent(Graphics g){
    super.paintComponent(g);
    g.drawString("Hello World JApplet", 50, 50);
  }
}