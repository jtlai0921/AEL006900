import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

import javax.swing.tree.*;

import java.net.*;

public class JTreeDemo extends JFrame implements TreeSelectionListener { // ��@TreeSelectionListener����
      
  String seconditem[]={"Chapter 1 Java SE 6.0", "Chapter 2 Java AWT", "Chapter 3 Layout Manager"};
  
  String thirditem[][]={
    {"1-1 �w�˻P�]�w", "1-2 Java�{���u��", "1-3 Java����O", "1-4 HTML Converter", "1-5 Java Monitoring & Management Console"},
    {"2-1 Java AWT������", "2-2 Java���ε{��", "2-3 Java Applet", "2-4 Component��H���O"},
    {"3-1 Layout Manager������", "3-2 Flow Layout", "3-3 Border Layout", "3-4 Card Layout", "3-5 Grid Layout", "3-6 Grid Bag Layout"}
  };

  DefaultMutableTreeNode topLevel;
  DefaultMutableTreeNode secondLevel[] = new DefaultMutableTreeNode[3];
  DefaultMutableTreeNode thirdLevel[][] = new DefaultMutableTreeNode[3][6];

  JTree jtree;
  JEditorPane htmlPane;

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
    
  // �غc�禡
  public JTreeDemo() {
    super("JTree Demo");

    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

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

    // �إ�JTree�𪬪���
    jtree = new JTree(topLevel);

    // ���UTreeSelectionListener
    jtree.addTreeSelectionListener(this);

    // ���b
    JScrollPane jsp1 = new JScrollPane(jtree, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    jsp1.setWheelScrollingEnabled(true);

    htmlPane = new JEditorPane();
    htmlPane.setEditable(false);

    //initHelp();
    JScrollPane jsp2 = new JScrollPane(htmlPane, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    jsp2.setWheelScrollingEnabled(true);

    // ���j���O
    // �������j
    JSplitPane jsplitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jsp1, jsp2);
    // �]�w�O�_�H���jWidget�ֳt�i�}�κP�|���j���O
    jsplitpane.setOneTouchExpandable(true);
    // �]�w���j�b����m
    jsplitpane.setDividerLocation(150);
    jsplitpane.setPreferredSize(new Dimension(450, 200));

    this.add(jsplitpane, BorderLayout.CENTER);

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    this.validate();
    this.setSize(new Dimension(450, 300));
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });    
  }
  
  // ��@TreeSelectionListener��������k
  // ��𪬪��󤧸`�I�Q����ɡ]�h���ܭȧ��ܡ^�ҩI�s����k
  public void valueChanged(TreeSelectionEvent e) {
    DefaultMutableTreeNode node = (DefaultMutableTreeNode) jtree.getLastSelectedPathComponent();

    if (node == null) return;

    Object nodeInfo = node.getUserObject();
    
    if (node.isLeaf()) {
      String urlString = "doc/"+ nodeInfo.toString().substring(0, nodeInfo.toString().indexOf(" ")) + ".html";
      
      try {
        URL docURL = getClass().getResource(urlString);
        
        htmlPane.setPage(docURL);
      } 
      catch (Exception ex) {
        htmlPane.setText("<font face=Verdana size=3>File Not Found.</font>");
        ex.printStackTrace();
      }
    } 
  }  
}
