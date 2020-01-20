import java.awt.*;

import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class GridBagLayoutDemo extends javax.swing.JApplet {
    
  // 建構函式
  public GridBagLayoutDemo() {
  }

  public void init() {
    javax.swing.JButton jbutton;

    GridBagLayout gridbaglayout = new GridBagLayout();
    GridBagConstraints gbConstraints = new GridBagConstraints();
    
    // 直接定義JFrame之Layout Manager為GridBagLayout
    this.setLayout(gridbaglayout);

    // 同時改變物件寬度與高度以填滿顯示區域之水平與垂直方向
    gbConstraints.fill = GridBagConstraints.BOTH;
    
    // 依加權比例分配物件間水平方向額外之區域
    gbConstraints.weightx = 1.0;
    jbutton = new JButton("1");
    // 設定Grid Bag Layout中物件的限制
    gridbaglayout.setConstraints(jbutton, gbConstraints);
    // 直接將物件加至JFrame中
    this.add(jbutton);

    jbutton = new JButton("2");
    gridbaglayout.setConstraints(jbutton, gbConstraints);
    this.add(jbutton);

    // 設定物件配置時所佔據區域列的數目
    // 物件將填滿所剩餘的區域列數或行數
    gbConstraints.gridwidth = GridBagConstraints.REMAINDER; 
    jbutton = new JButton("3");
    gridbaglayout.setConstraints(jbutton, gbConstraints);
    this.add(jbutton);

    // 依加權比例分配物件間水平方向額外之區域
    gbConstraints.weightx = 0.0;       
    jbutton = new JButton("4");
    gridbaglayout.setConstraints(jbutton, gbConstraints);
    this.add(jbutton);
    
    // 設定物件配置時所佔據區域列的數目
    // 物件將填滿除了所在列的最後一個單位區域以外之區域列數
    gbConstraints.gridwidth = GridBagConstraints.RELATIVE; 
    jbutton = new JButton("5");
    gridbaglayout.setConstraints(jbutton, gbConstraints);
    this.add(jbutton);
    
    // 設定物件配置時所佔據區域列的數目
    // 物件將填滿所剩餘的區域列數或行數
    gbConstraints.gridwidth = GridBagConstraints.REMAINDER; 
    jbutton = new JButton("6");
    gridbaglayout.setConstraints(jbutton, gbConstraints);
    this.add(jbutton);
    
    // 設定物件配置時所佔據區域列的數目
    gbConstraints.gridwidth = 1;          
    // 設定物件配置時所佔據區域行的數目
    gbConstraints.gridheight = 2;
    // 依加權比例分配物件間垂直方向額外之區域
    gbConstraints.weighty = 1.0;
    jbutton = new JButton("7");
    gridbaglayout.setConstraints(jbutton, gbConstraints);
    this.add(jbutton);
    
    // 依加權比例分配物件間垂直方向額外之區域
    gbConstraints.weighty = 0.0;       
    gbConstraints.gridwidth = GridBagConstraints.REMAINDER; 
    gbConstraints.gridheight = 1;      
    jbutton = new JButton("8");
    gridbaglayout.setConstraints(jbutton, gbConstraints);
    this.add(jbutton);

    jbutton = new JButton("9");
    gridbaglayout.setConstraints(jbutton, gbConstraints);
    this.add(jbutton);
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
