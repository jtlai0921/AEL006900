import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.UIManager;

public class LookandFeelDemo extends javax.swing.JFrame {

  // Main method
  public static void main(String[] args) {

    try {
      // �]�w�P�@�~�t�Φ�����Look and Feel
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch(Exception e) {
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

    // ���o�󥭻O��Look and Feel���O�W��
    System.out.println("�󥭻O��L&F���O: " + UIManager.getCrossPlatformLookAndFeelClassName());

    // ���o�ثe�t�Τw�w�ˤ�Look and Feel���O
    UIManager.LookAndFeelInfo[] info = UIManager.getInstalledLookAndFeels();
    for (int i=0; i<info.length; i++)
      System.out.println("�w�w�ˤ�L&F���O: " + info[i].getClassName());

    // ���o�t�Υثe�ҨϥΪ�Look and Feel
    System.out.println("�ثe�ҨϥΤ�L&F���O: " + UIManager.getLookAndFeel().toString());

    // ���o�P�@�~�t�Φ�����Look and Feel
    System.out.println("�P�@�~�t�Φ�����L&F���O: " + UIManager.getSystemLookAndFeelClassName());

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
