import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

import javax.swing.tree.*;

public class JTreeDemo extends JFrame implements MouseListener { // ��@MouseListener���� 
      
  String seconditem[]={"Chapter 1 Java SE 6.0", "Chapter 2 Java AWT", "Chapter 3 Layout Manager"};
  
  String thirditem[][]={
    {"1-1 �w�˻P�]�w", "1-2 Java�{���u��", "1-3 Java����O", "1-4 HTML Converter", "1-5 Java Monitoring & Management Console"},
    {"2-1 Java AWT������", "2-2 Java���ε{��", "2-3 Java Applet", "2-4 Component��H���O"},
    {"3-1 Layout Manager������", "3-2 Flow Layout", "3-3 Border Layout", "3-4 Card Layout", "3-5 Grid Layout", "3-6 Grid Bag Layout"}
  };

  DefaultMutableTreeNode topLevel;
  DefaultMutableTreeNode secondLevel[] = new DefaultMutableTreeNode[3];
  DefaultMutableTreeNode thirdLevel[][] = new DefaultMutableTreeNode[3][6];

  JTree jtree ;
  
  JPopupMenu jpopupmenu = new JPopupMenu();

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

    // �]�wø�sLinked-list���C�ӳ椸��DefaultTreeCellRenderer����
    jtree.setCellRenderer(new TreeRenderer());

    //  �إ߿�涵�� (Demo Only)
    // Cut
    JMenuItem jmnuCut = new JMenuItem("Cut", new ImageIcon(cl.getResource("images/cut.gif")));
    jmnuCut.setMnemonic('t');
    jmnuCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
    
    // Copy
    JMenuItem jmnuCopy = new JMenuItem("Copy", new ImageIcon(cl.getResource("images/copy.gif")));
    jmnuCopy.setMnemonic('C');
    jmnuCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));

    // Paste
    JMenuItem jmnuPaste = new JMenuItem("Paste", new ImageIcon(cl.getResource("images/paste.gif")));
    jmnuPaste.setMnemonic('P');
    jmnuPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
    
    // �إ߬��㦡���
    jpopupmenu.add(jmnuCut);
    jpopupmenu.add(jmnuCopy);
    jpopupmenu.add(jmnuPaste);
    
    jtree.add(jpopupmenu);
    
    // ���U MouseListener
    jtree.addMouseListener(this);    
    
    // ���b
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

  // ��@MouseListener��������k
  public void mouseClicked(MouseEvent e) {}

  public void mouseEntered(MouseEvent e) {}

  public void mouseExited(MouseEvent e) {}

  public void mousePressed(MouseEvent e) {}

  public void mouseReleased(MouseEvent e) {
    if (e.isPopupTrigger() || e.getButton() == MouseEvent.BUTTON3) {
      int x = e.getX();
      int y = e.getY();
      
      // ���o���w��mx�Py�B��Tree Path���|�C 
      TreePath path = jtree.getPathForLocation(x, y);
      
      if (path == null)
        return;

      // �P�_�`�I�O�_���i�}
      if (jtree.isExpanded(path))
        // �N�ҫ��w�����|�����`�I�]�w���P�|���`�I
        jtree.collapsePath(path);
      else
        // �N�ҫ��w�����|�����`�I�]�w���i�}�Υi�˵����`�I
        jtree.expandPath(path);

      // �]�wTree Path���|�ܥثe��ܤ���
      jtree.setSelectionPath(path);
      
      // �N���|���Ҧ������|�`�I���i�}�úu��
      jtree.scrollPathToVisible(path);
      
      // ��ܬ��㦡���
      jpopupmenu.show(jtree, x, y);
    }
  }

  public void mouseDragged(MouseEvent e) {}

  public void mouseMoved(MouseEvent e) {}
}
