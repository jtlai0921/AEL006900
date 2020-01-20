import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.UIManager;
import com.sun.java.swing.plaf.motif.MotifLookAndFeel;

public class LookandFeelDemo extends javax.swing.JFrame {

  // Main method
  public static void main(String[] args) {

    try {
      // �]�wCDE/Motif Look and Feel
      UIManager.setLookAndFeel(new com.sun.java.swing.plaf.motif.MotifLookAndFeel());
    }
    catch(UnsupportedLookAndFeelException e) {
      e.printStackTrace();
    }

    new LookandFeelDemo();
  }

  // �غc�禡
  // ���ե�
  public LookandFeelDemo() {
    super("Look and Feel Demo");

    // �����w�qJFrame��Layout Manager��FlowLayout
    this.setLayout(new FlowLayout());

    JButton jbutton1 = new JButton("OK");
    JButton jbutton2 = new JButton("Cancel");
    JButton jbutton3 = new JButton("Yes");
    JButton jbutton4 = new JButton("No");

    // �����N����[��JFrame��
    this.add(jbutton1);
    this.add(jbutton2);
    this.add(jbutton3);
    this.add(jbutton4);

    // �]�w�������j�p
    this.setSize(250, 150);

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();

    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;

    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;

    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    // ��ܵ���
    this.setVisible(true);
    
    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }
}
