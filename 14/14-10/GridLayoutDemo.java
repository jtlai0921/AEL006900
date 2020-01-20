import java.awt.*;

import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class GridLayoutDemo extends javax.swing.JApplet {
    
  // 建構函式
  public GridLayoutDemo() {
  }

  public void init() {
    final int row = 4;    // 列
    final int column = 3; // 行

    // 直接定義JApplet之Layout Manager為GridLayout
    this.setLayout(new GridLayout(row, column));

    // 取得 Applet 之參數
    String orientation = getParameter("orientation");

    // 設定JApplet中物件的配置方向
    if (orientation.equalsIgnoreCase("RIGHT_TO_LEFT"))
      this.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    else
      this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    
    for (int i=0; i<row; i++) {
      for (int j=0; j<column; j++) {
        // 直接將物件加至JApplet中
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
