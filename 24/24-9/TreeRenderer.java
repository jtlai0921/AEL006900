import java.awt.*;
import javax.swing.*;

import javax.swing.tree.*;

public class TreeRenderer extends DefaultTreeCellRenderer {
    
  // �غc�禡
  public TreeRenderer() {}

  // ��@javax.swing.tree.TreeCellRenderer������getTreeCellRendererComponent()��k
  public Component getTreeCellRendererComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row, boolean cellHasFocus) {
  
    super.getTreeCellRendererComponent(tree, value, isSelected, expanded, leaf, row, cellHasFocus);

    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();
    
    if (leaf) {
      DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
  
      String text = node.getUserObject().toString();
      
      String icon = "images/" + text.substring(0, 3) + ".gif";
      
      System.out.println(icon);
  
      setIcon(new ImageIcon(cl.getResource(icon)));
    } 

    return this;
  }
} 
