import java.awt.*;

import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class FlowLayoutDemo extends javax.swing.JApplet {
    
  // �غc�禡
  public FlowLayoutDemo() {
  }

  public void init() {
    // JDK 1.4���Ϊk
    // ���o��Content Pane 
    Container contentPane = getContentPane();

    // �w�q Layout Manager �� FlowLayout
    contentPane.setLayout(new FlowLayout());

    JButton jbutton1 = new JButton("OK");
    JButton jbutton2 = new JButton("Cancel");
    JButton jbutton3 = new JButton("Yes");
    JButton jbutton4 = new JButton("No");

    // �N����[��Content Pane��
    contentPane.add(jbutton1);
    contentPane.add(jbutton2);
    contentPane.add(jbutton3);
    contentPane.add(jbutton4);
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
