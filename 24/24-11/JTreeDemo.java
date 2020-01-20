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
             // �إ߲ĤG��
             secondLevel = new DefaultMutableTreeNode(line.substring(2));
             // �N�ĤG���[�ܲĤ@��
             topLevel.add(secondLevel);
             break;
           case '3':
             if(secondLevel != null) {
                // �إ߲ĤT��
                thirdLevel = new DefaultMutableTreeNode(line.substring(2));
                // �N�ĤT���[�ܲĤG��
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

    // �إ�JTree�𪬪���
    JTree jtree = new JTree(topLevel);

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
}
