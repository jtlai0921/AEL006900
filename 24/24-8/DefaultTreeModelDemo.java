import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

import javax.swing.tree.*;

public class DefaultTreeModelDemo extends JFrame implements TreeModelListener { // ��@TreeModelListener����

  String seconditem[]={"Chapter 1 Java SE 6.0", "Chapter 2 Java AWT", "Chapter 3 Layout Manager"};
  
  String thirditem[][]={
    {"1-1 �w�˻P�]�w", "1-2 Java�{���u��", "1-3 Java����O", "1-4 HTML Converter", "1-5 Java Monitoring & Management Console"},
    {"2-1 Java AWT������", "2-2 Java���ε{��", "2-3 Java Applet", "2-4 Component��H���O"},
    {"3-1 Layout Manager������", "3-2 Flow Layout", "3-3 Border Layout", "3-4 Card Layout", "3-5 Grid Layout", "3-6 Grid Bag Layout"}
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

  // �غc�禡
  public DefaultTreeModelDemo() {
    super("DefaultTreeModel Demo");

    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // �]�w�����ϥ�
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // �]�w�����ϥ�
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // �]�w�`�I�����|�Ϲ�
    UIManager.put("Tree.collapsedIcon", new ImageIcon(cl.getResource("images/collapsed.gif")));
    
    // �]�w�`�I���i�}�Ϲ�
    UIManager.put("Tree.expandedIcon", new ImageIcon(cl.getResource("images/expanded.gif")));

    // �]�w�`�I�������Ϲ�
    UIManager.put("Tree.closedIcon", new ImageIcon(cl.getResource("images/closed.gif")));

    // �]�w�`�I���}�ҹϹ�
    UIManager.put("Tree.openIcon", new ImageIcon(cl.getResource("images/open.gif")));

    // �]�w���`�I���Ϲ�
    UIManager.put("Tree.leafIcon", new ImageIcon(cl.getResource("images/File.gif")));

    // �w�q Layout Manager �� BorderLayout
    this.setLayout(new BorderLayout());

    // �إ߲Ĥ@��
    topLevel = new DefaultMutableTreeNode("Java S.E. 6.0�����{���]�p");

    // �إ߲ĤG��
    for (int i=0; i<seconditem.length; i++){
      secondLevel[i] = new DefaultMutableTreeNode(seconditem[i]);

      // �N�ĤG���[�ܲĤ@��
      topLevel.add(secondLevel[i]);

      // �إ߲ĤT��
      for(int j=0; j<thirditem[i].length; j++){
        thirdLevel[i][j] = new DefaultMutableTreeNode(thirditem[i][j]);

        // �N�ĤT���[�ܲĤG��
        secondLevel[i].add(thirdLevel[i][j]);
      }
    }      

    // �إ߾𪬪���ҫ�
    treeModel = new DefaultTreeModel(topLevel);
    
    // ���UTreeModelListener
    treeModel.addTreeModelListener(this);

    // �إ�JTree�𪬪���
    jtree = new JTree(treeModel);
    
    // �]�wJTree���󬰥i�s��
    jtree.setEditable(true);
    
    // ���oJTree����MVC�[�c����ܪ�Model����
    jtree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    
    jtree.setShowsRootHandles(true);
    
    // ���b
    JScrollPane jscrollpane = new JScrollPane(jtree, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    jscrollpane.setWheelScrollingEnabled(true);
    this.add(jscrollpane, BorderLayout.CENTER);

    // �]�w Etched Border (Demo only)
    Border border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.white, new Color(142, 142, 142)); 

    JPanel jpanel1 = new JPanel();
    jpanel1.setLayout(new BorderLayout());
    jpanel1.setBorder(BorderFactory.createTitledBorder(border, "�𪬪���"));
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
    
    // ���o�ثe��Tree Path���|
    TreePath parentPath = jtree.getSelectionPath();

    if (parentPath == null) {
      parentNode = topLevel;
    } 
    else {
      // ���o���I�蠟�`�I
      parentNode = (DefaultMutableTreeNode)(parentPath.getLastPathComponent());
    }

    DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);

    if (parentNode == null) {
      parentNode = topLevel;
    }

    // �ۤ��`�I��index�Ӥl�`�I���ᴡ�J�l�`�I
    treeModel.insertNodeInto(childNode, parentNode, parentNode.getChildCount());

    jtree.scrollPathToVisible(new TreePath(childNode.getPath()));

    return childNode;
  }

  public void removeNode() {
    // ���o�ثe��Tree Path���|
    TreePath currentSelection = jtree.getSelectionPath();

    if (currentSelection != null) {
      // ���o���I�蠟�`�I
      DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) (currentSelection.getLastPathComponent());
      MutableTreeNode parent = (MutableTreeNode)(currentNode.getParent());

      if (parent != null) {
        // �۸`�I�����`�I���������`�I
        treeModel.removeNodeFromParent(currentNode);
        return;
      }
    } 
  }

  // ��@TreeModelListener��������k
  // ��𪬪���ҫ����`�I�Q�ק�ɩҩI�s����k
  public void treeNodesChanged(TreeModelEvent e) {
    // ���o���ͨƥ󤧸`�I�����`�I
    DefaultMutableTreeNode theNode = (DefaultMutableTreeNode)e.getTreePath().getLastPathComponent();

    // ���o�l���ޭȡA�����ޭȬ��w�ק�`�I����m
    int index = e.getChildIndices()[0];
    
    theNode = (DefaultMutableTreeNode)(theNode.getChildAt(index));
    
    System.out.println("�`�I�Q�קאּ" + theNode.getUserObject());
  }
  
  // ��۾𪬪���ҫ����J�`�I�ɩҩI�s����k
  public void treeNodesInserted(TreeModelEvent e) {
    // ���o���ͨƥ󤧸`�I�����`�I
    DefaultMutableTreeNode theNode = (DefaultMutableTreeNode)e.getTreePath().getLastPathComponent();

    System.out.println("��" + theNode + "���ᴡ�J�`�I");
  } 
  
  // ��۾𪬪���ҫ������`�I�ɩҩI�s����k
  public void treeNodesRemoved(TreeModelEvent e) {
    // ���o���ͨƥ󤧸`�I�����`�I
    DefaultMutableTreeNode theNode = (DefaultMutableTreeNode)e.getTreePath().getLastPathComponent();

    System.out.println("��" + theNode + "���Ჾ���`�I");
  }
  
  // ��𪬪���ҫ����`�I���|���c�Q�ק�ɩҩI�s����k
  public void treeStructureChanged(TreeModelEvent e) {
    System.out.println("�`�I���|���c�Q�ק�");
  }
}
