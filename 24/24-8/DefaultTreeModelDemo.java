import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

import javax.swing.tree.*;

public class DefaultTreeModelDemo extends JFrame implements TreeModelListener { // 實作TreeModelListener介面

  String seconditem[]={"Chapter 1 Java SE 6.0", "Chapter 2 Java AWT", "Chapter 3 Layout Manager"};
  
  String thirditem[][]={
    {"1-1 安裝與設定", "1-2 Java程式工具", "1-3 Java控制面板", "1-4 HTML Converter", "1-5 Java Monitoring & Management Console"},
    {"2-1 Java AWT之介紹", "2-2 Java應用程式", "2-3 Java Applet", "2-4 Component抽象類別"},
    {"3-1 Layout Manager之介紹", "3-2 Flow Layout", "3-3 Border Layout", "3-4 Card Layout", "3-5 Grid Layout", "3-6 Grid Bag Layout"}
  };

  DefaultMutableTreeNode topLevel;
  DefaultMutableTreeNode secondLevel[] = new DefaultMutableTreeNode[3];
  DefaultMutableTreeNode thirdLevel[][] = new DefaultMutableTreeNode[3][6];
  
  DefaultTreeModel treeModel;

  JTree jtree;
  
  int newNode = 1;  

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

    new DefaultTreeModelDemo();
  }

  // 建構函式
  public DefaultTreeModelDemo() {
    super("DefaultTreeModel Demo");

    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 設定視窗圖示
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

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

    // 建立樹狀物件模型
    treeModel = new DefaultTreeModel(topLevel);
    
    // 註冊TreeModelListener
    treeModel.addTreeModelListener(this);

    // 建立JTree樹狀物件
    jtree = new JTree(treeModel);
    
    // 設定JTree物件為可編輯
    jtree.setEditable(true);
    
    // 取得JTree物件MVC架構中選擇的Model部份
    jtree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    
    jtree.setShowsRootHandles(true);
    
    // 捲軸
    JScrollPane jscrollpane = new JScrollPane(jtree, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    jscrollpane.setWheelScrollingEnabled(true);
    this.add(jscrollpane, BorderLayout.CENTER);

    // 設定 Etched Border (Demo only)
    Border border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.white, new Color(142, 142, 142)); 

    JPanel jpanel1 = new JPanel();
    jpanel1.setLayout(new BorderLayout());
    jpanel1.setBorder(BorderFactory.createTitledBorder(border, "樹狀物件"));
    jpanel1.add(jscrollpane, BorderLayout.CENTER);

    JButton btnAdd = new JButton("Add");
    btnAdd.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addNode("New Node " + newNode++);
      }
    });
     
    JButton btnRemove = new JButton("Remove");
    btnRemove.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        removeNode();
      }
    });

    JPanel jpanel2 = new JPanel();
    
    GroupLayout jpanel2Layout = new GroupLayout(jpanel2);
    jpanel2.setLayout(jpanel2Layout);
    jpanel2Layout.setHorizontalGroup(
      jpanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jpanel2Layout.createSequentialGroup()
        .addGap(40, 40, 40)
        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(30, 30, 30)
        .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(40, Short.MAX_VALUE))
    );
    jpanel2Layout.setVerticalGroup(
      jpanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jpanel2Layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jpanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
          .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addContainerGap())
    );

    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(jpanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
      .addComponent(jpanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
        .addComponent(jpanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jpanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
    );

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    this.validate();
    this.setSize(new Dimension(300, 350));
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });    
  }

  public DefaultMutableTreeNode addNode(Object child) {
    DefaultMutableTreeNode parentNode = null;
    
    // 取得目前之Tree Path路徑
    TreePath parentPath = jtree.getSelectionPath();

    if (parentPath == null) {
      parentNode = topLevel;
    } 
    else {
      // 取得所點選之節點
      parentNode = (DefaultMutableTreeNode)(parentPath.getLastPathComponent());
    }

    DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);

    if (parentNode == null) {
      parentNode = topLevel;
    }

    // 自父節點第index個子節點之後插入子節點
    treeModel.insertNodeInto(childNode, parentNode, parentNode.getChildCount());

    jtree.scrollPathToVisible(new TreePath(childNode.getPath()));

    return childNode;
  }

  public void removeNode() {
    // 取得目前之Tree Path路徑
    TreePath currentSelection = jtree.getSelectionPath();

    if (currentSelection != null) {
      // 取得所點選之節點
      DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) (currentSelection.getLastPathComponent());
      MutableTreeNode parent = (MutableTreeNode)(currentNode.getParent());

      if (parent != null) {
        // 自節點之父節點中移除此節點
        treeModel.removeNodeFromParent(currentNode);
        return;
      }
    } 
  }

  // 實作TreeModelListener介面的方法
  // 當樹狀物件模型的節點被修改時所呼叫的方法
  public void treeNodesChanged(TreeModelEvent e) {
    // 取得產生事件之節點的父節點
    DefaultMutableTreeNode theNode = (DefaultMutableTreeNode)e.getTreePath().getLastPathComponent();

    // 取得子索引值，此索引值為已修改節點的位置
    int index = e.getChildIndices()[0];
    
    theNode = (DefaultMutableTreeNode)(theNode.getChildAt(index));
    
    System.out.println("節點被修改為" + theNode.getUserObject());
  }
  
  // 當自樹狀物件模型插入節點時所呼叫的方法
  public void treeNodesInserted(TreeModelEvent e) {
    // 取得產生事件之節點的父節點
    DefaultMutableTreeNode theNode = (DefaultMutableTreeNode)e.getTreePath().getLastPathComponent();

    System.out.println("自" + theNode + "之後插入節點");
  } 
  
  // 當自樹狀物件模型移除節點時所呼叫的方法
  public void treeNodesRemoved(TreeModelEvent e) {
    // 取得產生事件之節點的父節點
    DefaultMutableTreeNode theNode = (DefaultMutableTreeNode)e.getTreePath().getLastPathComponent();

    System.out.println("自" + theNode + "之後移除節點");
  }
  
  // 當樹狀物件模型的節點路徑結構被修改時所呼叫的方法
  public void treeStructureChanged(TreeModelEvent e) {
    System.out.println("節點路徑結構被修改");
  }
}
