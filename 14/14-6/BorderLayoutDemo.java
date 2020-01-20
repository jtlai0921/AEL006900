import java.awt.*;

import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class BorderLayoutDemo extends javax.swing.JApplet {
    
  // �غc�禡
  public BorderLayoutDemo() {
  }

  public void init() {
    // �����w�qJApplet��Layout Manager��FlowLayout
    this.setLayout(new BorderLayout());

    JButton jbutton1 = new JButton("EAST");
    JButton jbutton2 = new JButton("SOUTH");
    JButton jbutton3 = new JButton("WEST");
    JButton jbutton4 = new JButton("NORTH");
    JButton jbutton5 = new JButton("CENTER");

    // �����N����[��JApplet��
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
