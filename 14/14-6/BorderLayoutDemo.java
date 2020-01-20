import java.awt.*;

import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class BorderLayoutDemo extends javax.swing.JApplet {
    
  // 建構函式
  public BorderLayoutDemo() {
  }

  public void init() {
    // 直接定義JApplet之Layout Manager為FlowLayout
    this.setLayout(new BorderLayout());

    JButton jbutton1 = new JButton("EAST");
    JButton jbutton2 = new JButton("SOUTH");
    JButton jbutton3 = new JButton("WEST");
    JButton jbutton4 = new JButton("NORTH");
    JButton jbutton5 = new JButton("CENTER");

    // 直接將物件加至JApplet中
    this.add(jbutton1, BorderLayout.EAST);
    this.add(jbutton2, BorderLayout.SOUTH);
    this.add(jbutton3, BorderLayout.WEST);
    this.add(jbutton4, BorderLayout.NORTH);
    this.add(jbutton5, BorderLayout.CENTER);
  }

  static {
    UIManager.put("swing.boldMetal", Boolean.FALSE);
    JDialog.setDefaultLookAndFeelDecorated(true);
    JFrame.setDefaultLookAndFeelDecorated(true);
    Toolkit.getDefaultToolkit().setDynamicLayout(true);

    try {
      UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
    }
    catch(Exception e) {}
  }
}
