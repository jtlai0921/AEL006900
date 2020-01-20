import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class BoxLayoutDemo extends javax.swing.JFrame {

  // Main method
  public static void main(String[] args) {
    UIManager.put("swing.boldMetal", Boolean.FALSE);
    JDialog.setDefaultLookAndFeelDecorated(true);
    JFrame.setDefaultLookAndFeelDecorated(true);
    Toolkit.getDefaultToolkit().setDynamicLayout(true);

    try {
      UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    new BoxLayoutDemo();
  }

  // �غc�禡
  public BoxLayoutDemo() {
    super("Filler Demo");

    // �����w�qJFrame��Layout Manager��FlowLayout
    this.setLayout(new FlowLayout());

    JPanel jpanel = new JPanel();

    // �w�qLayout Manager��BoxLayout
    // �ѥ��ܥk�t�m
    jpanel.setLayout(new BoxLayout(jpanel, BoxLayout.X_AXIS));

    // �ϰ줧�̤p�ؤo
    Dimension min = new Dimension(10, 50);
    // �ϰ줧�̨Τؤo
    Dimension pref = new Dimension(50, 50);
    // �ϰ줧�̤j�ؤo
    Dimension max = new Dimension(100, 100);

    // �إߦۭqFiller�ϰ�
    jpanel.add(new Box.Filler(min, pref, max));

    jpanel.add(new JButton("Button 1"));

    // �إߦۭqFiller�ϰ�
    jpanel.add(new Box.Filler(min, pref, max));

    jpanel.add(new JButton("Button 2"));

    // �إߦۭqFiller�ϰ�
    jpanel.add(new Box.Filler(min, pref, max));

    this.add(jpanel);

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
