import java.awt.*;
import java.applet.*;
import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class JNLPApplet extends javax.swing.JApplet {
	javax.swing.JButton jbutton1, jbutton2, jbutton3, jbutton4;
	  
  // 建構函式
  public JNLPApplet() {
    System.out.println("JApplet建構函式");
  }

  public void init() {
    System.out.println("JApplet init()");

		jbutton1 = new JButton("OK");
		jbutton2 = new JButton("Cancel");
		jbutton3 = new JButton("Yes");
		jbutton4 = new JButton("No");

    Container contentPane = getContentPane();
    contentPane.setLayout(new FlowLayout());
    contentPane.add(jbutton1);
    contentPane.add(jbutton2);
    contentPane.add(jbutton3);
    contentPane.add(jbutton4);
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

  static {
    try {
      UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
    }
    catch(Exception e) {}
  }
}
