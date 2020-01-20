import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

import javax.swing.tree.*;

import java.util.*;
import java.io.*;
import java.net.*;

public class JTreeDemo extends JFrame {
      
  DefaultMutableTreeNode topLevel;
  DefaultMutableTreeNode secondLevel;
  DefaultMutableTreeNode thirdLevel;

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

  	URL url = getClass().getResource("tree.properties");
  
  	try {
      InputStream is = url.openStream();
      InputStreamReader sr = new InputStreamReader(is, "big5");
      BufferedReader br = new BufferedReader(sr);

      String line = br.readLine();

      while(line != null) {
      	char nodetype = line.charAt(0);

      	switch(nodetype) {
           case '2':
             // 建立第二階
             secondLevel = new DefaultMutableTreeNode(line.substring(2));
             // 將第二階加至第一階
             topLevel.add(secondLevel);
             break;
           case '3':
             if(secondLevel != null) {
                // 建立第三階
                thirdLevel = new DefaultMutableTreeNode(line.substring(2));
                // 將第三階加至第二階
               secondLevel.add(thirdLevel);
             }
             break;
           default:
             break;
        }
        line = br.readLine();
      }
      
      br.close();
    } 
  	catch (IOException e) {}

    // 建立JTree樹狀物件
    JTree jtree = new JTree(topLevel);

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
