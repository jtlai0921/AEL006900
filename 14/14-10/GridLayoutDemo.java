import java.awt.*;

import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class GridLayoutDemo extends javax.swing.JApplet {
    
  // �غc�禡
  public GridLayoutDemo() {
  }

  public void init() {
    final int row = 4;    // �C
    final int column = 3; // ��

    // �����w�qJApplet��Layout Manager��GridLayout
    this.setLayout(new GridLayout(row, column));

    // ���o Applet ���Ѽ�
    String orientation = getParameter("orientation");

    // �]�wJApplet�����󪺰t�m��V
    if (orientation.equalsIgnoreCase("RIGHT_TO_LEFT"))
      this.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    else
      this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    
    for (int i=0; i<row; i++) {
      for (int j=0; j<column; j++) {
        // �����N����[��JApplet��
        this.add(new javax.swing.JButton("(" + i + ":" + j + ")"));
      }
    }
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
