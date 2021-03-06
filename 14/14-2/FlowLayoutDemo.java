import java.awt.*;

import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class FlowLayoutDemo extends javax.swing.JApplet {
    
  // 建構函式
  public FlowLayoutDemo() {
  }

  public void init() {
    // JDK 1.4的用法
    // 取得其Content Pane 
    Container contentPane = getContentPane();

    // 定義 Layout Manager 為 FlowLayout
    contentPane.setLayout(new FlowLayout());

    JButton jbutton1 = new JButton("OK");
    JButton jbutton2 = new JButton("Cancel");
    JButton jbutton3 = new JButton("Yes");
    JButton jbutton4 = new JButton("No");

    // 將物件加至Content Pane中
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
