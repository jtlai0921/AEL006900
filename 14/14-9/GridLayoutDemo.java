import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class GridLayoutDemo extends javax.swing.JFrame {

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

    new GridLayoutDemo();
  }

  // �غc�禡
  public GridLayoutDemo() {
    super("Grid Layout Demo");

    final int row = 4;    // �C
    final int column = 3; // ��

    // �����w�qJFrame��Layout Manager��GridLayout
    this.setLayout(new GridLayout(row, column));

    // �]�wJFrame�����󪺰t�m��V
    this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    
    for (int i=0; i<row; i++) {
      for (int j=0; j<column; j++) {
        // �����N����[��JFrame��
        this.add(new javax.swing.JButton("(" + i + ":" + j + ")"));
      }
    }

    // �]�w�������j�p
    this.setSize(250, 250);

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
