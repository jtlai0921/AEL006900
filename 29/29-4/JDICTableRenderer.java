import java.awt.Component;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

// 繼承javax.swing.table.DefaultTableCellRenderer
public class JDICTableRenderer extends DefaultTableCellRenderer {

  // 實作DefaultTableCellRenderer的getTableCellRendererComponent()方法
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

    if (value != null && value instanceof JDICDiskObject) {
      JDICDiskObject diskObject = (JDICDiskObject) value;

      ((JLabel) component).setText(diskObject.name);
      ((JLabel) component).setHorizontalAlignment(JLabel.LEFT);

      if (diskObject.type.equals(JDICDiskObject.TYPE_DRIVER)) {
        ((JLabel) component).setIcon(JDICFileExplorer.driverIcon);
      } 
      else if (diskObject.type.equals(JDICDiskObject.TYPE_FOLDER)) {
        ((JLabel) component).setIcon(JDICFileExplorer.folderIcon);
      } 
      else {
        ((JLabel) component).setIcon(JDICFileExplorer.fileIcon);
      }

      return component;
    } 
    else if (value != null && value instanceof String) {
      ((JLabel) component).setHorizontalAlignment(JLabel.RIGHT);

      return component;
    }

    return null;
  }
}
