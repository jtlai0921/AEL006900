import java.awt.Component;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import java.io.File;

// 繼承javax.swing.tree.DefaultTreeCellRenderer
public class JDICTreeRenderer extends DefaultTreeCellRenderer {
  
  // 實作DefaultTreeCellRenderer的getTreeCellRendererComponent()方法
  public Component getTreeCellRendererComponent(JTree tree, Object value, boolean isSelected, boolean isExpanded,  boolean leaf, int row, boolean hasFocus) {

    Component component = super.getTreeCellRendererComponent(tree, value, isSelected, isExpanded, leaf, row, hasFocus);

    if (value != null && value instanceof JDICTreeNode) {
      JDICTreeNode treeNode = (JDICTreeNode) value;

      File selectedDir = (File) treeNode.getUserObject();

      // 設定物件的圖像
      if (selectedDir.equals(new File(JDICFileExplorer.MY_COMPUTER_FOLDER_PATH))) {
        setIcon(JDICFileExplorer.computerIcon);
      } 
      else if (selectedDir.getParent() == null) {
        setIcon(JDICFileExplorer.driverIcon);
      } 
      else {
        setIcon(JDICFileExplorer.folderIcon);
      }

      return component;
    }

    return this;
  }
}
