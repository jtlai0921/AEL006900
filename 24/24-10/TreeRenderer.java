import java.awt.*;
import javax.swing.*;

import javax.swing.tree.*;

public class TreeRenderer extends DefaultTreeCellRenderer {
    
  // 建構函式
  public TreeRenderer() {}

  // 實作javax.swing.tree.TreeCellRenderer介面的getTreeCellRendererComponent()方法
  public Component getTreeCellRendererComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row, boolean cellHasFocus) {
  
    super.getTreeCellRendererComponent(tree, value, isSelected, expanded, leaf, row, cellHasFocus);

    // 取得目前之Class Loader
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
