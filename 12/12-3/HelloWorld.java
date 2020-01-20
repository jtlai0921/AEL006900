import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class HelloWorld extends javax.swing.JApplet {

  static {
    try {
      UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
    }
    catch(Exception e) {}
  }

  public static void main(String args[]){
    HelloWorldJFrame frame = new HelloWorldJFrame();
  }
  
  // �غc�禡
  public HelloWorld() {
    System.out.println("JApplet�غc�禡");
  }

  public void init() {
    System.out.println("JApplet init()");

    DisplayPanel displayPanel = new DisplayPanel("Hello World JApplet");

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

class HelloWorldJFrame extends javax.swing.JFrame {
  // �غc�禡
  public HelloWorldJFrame() {
    super("Hello World JFrame");

    DisplayPanel displayPanel = new DisplayPanel("Hello World JFrame");

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

    this.validate();
    this.setSize(new Dimension(200, 200));

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }
}

class DisplayPanel extends javax.swing.JPanel {
  String text;
  
  // �غc�禡
  public DisplayPanel(String text) {
    this.text = text;
  }

  public void paintComponent(Graphics g){
    super.paintComponent(g);
    g.drawString(text, 50, 50);
  }
}