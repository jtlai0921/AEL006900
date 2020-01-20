import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

import javax.swing.tree.*;

public class JTreeDemo extends JFrame {
      
  String seconditem[]={"Chapter 1 Java SE 6.0", "Chapter 2 Java AWT", "Chapter 3 Layout Manager"};
  
  String thirditem[][]={
    {"1-1 安裝與設定", "1-2 Java程式工具", "1-3 Java控制面板", "1-4 HTML Converter", "1-5 Java Monitoring & Management Console"},
    {"2-1 Java AWT之介紹", "2-2 Java應用程式", "2-3 Java Applet", "2-4 Component抽象類別"},
    {"3-1 Layout Manager之介紹", "3-2 Flow Layout", "3-3 Border Layout", "3-4 Card Layout", "3-5 Grid Layout", "3-6 Grid Bag Layout"}
  };

  DefaultMutableTreeNode topLevel;
  DefaultMutableTreeNode secondLevel[] = new DefaultMutableTreeNode[3];
  DefaultMutableTreeNode thirdLevel[][] = new DefaultMutableTreeNode[3][6];

  // Main method
  public static void main(String[] args) {
    UIManager.put("swing.boldMetal", Boolean.FALSE);
    JDialog.setDefaultLookAndFeelDecorated(true);
    JFrame.setDefaultLookAndFeelDecorated(true);
    Toolkit.getDefaultToolkit().setDynamicLayout(true);

    try {
      UIManager.setLookAndFeel(new javax.swing.plaf.metal.MetalLookAndFeel());
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    new JTreeDemo();
  }
    
  // 建構函式
  public JTreeDemo() {
    super("JTree Demo");

    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 設定視窗圖示
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // 設定節點之折疊圖像
    UIManager.put("Tree.collapsedIcon", new ImageIcon(cl.getResource("images/collapsed.gif")));
    
    // 設定節點之展開圖像
    UIManager.put("Tree.expandedIcon", new ImageIcon(cl.getResource("images/expanded.gif")));

    // 設定節點之關閉圖像
    UIManager.put("Tree.closedIcon", new ImageIcon(cl.getResource("images/closed.gif")));

    // 設定節點之開啟圖像
    UIManager.put("Tree.openIcon", new ImageIcon(cl.getResource("images/open.gif")));

    // 設定葉節點之圖像
    UIManager.put("Tree.leafIcon", new ImageIcon(cl.getResource("images/File.gif")));
    
    // 定義 Layout Manager 為 BorderLayout
    this.setLayout(new BorderLayout());

    // 建立第一階
    topLevel = new DefaultMutableTreeNode("Java S.E. 6.0視窗程式設計");

    // 建立第二階
    for (int i=0; i<seconditem.length; i++){
      secondLevel[i] = new DefaultMutableTreeNode(seconditem[i]);

      // 將第二階加至第一階
      topLevel.add(secondLevel[i]);

      // 建立第三階
      for(int j=0; j<thirditem[i].length; j++){
        thirdLevel[i][j] = new DefaultMutableTreeNode(thirditem[i][j]);

        // 將第三階加至第二階
        secondLevel[i].add(thirdLevel[i][j]);
      }
    }      

    // 建立JTree樹狀物件
    JTree jtree = new JTree(topLevel);

    // 設定繪製Linked-list中每個單元的DefaultTreeCellRenderer物件
    jtree.setCellRenderer(new TreeRenderer());

    // 捲軸
    JScrollPane jscrollpane = new JScrollPane(jtree, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    jscrollpane.setWheelScrollingEnabled(true);
    this.add(jscrollpane, BorderLayout.CENTER);

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    this.validate();
    this.setSize(new Dimension(300, 300));
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });    
  }
}
