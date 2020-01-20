import java.awt.*;
import java.applet.*;
import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class HelloWorldJApplet extends javax.swing.JApplet {

  // �غc�禡
  public HelloWorldJApplet() {
    System.out.println("JApplet�غc�禡");
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

    // JDK 1.4���Ϊk
    // ���o��Content Pane 
    //Container contentPane = getContentPane();
    // �w�q Layout Manager �� BorderLayout
    //contentPane.setLayout(new BorderLayout());
    // �N����[��Content Pane��
    //contentPane.add(displayPanel, BorderLayout.CENTER);

    // JDK 5.0���᪺�Ϊk
    // �����w�qJFrame��Layout BorderLayout
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
  // �غc�禡
  public DisplayPanel() {
  }

  public void paintComponent(Graphics g){
    super.paintComponent(g);
    g.drawString("Hello World JApplet", 50, 50);
  }
}