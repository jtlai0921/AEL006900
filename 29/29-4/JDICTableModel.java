import javax.swing.table.AbstractTableModel;

import java.io.File;

public class JDICTableModel extends AbstractTableModel {

  Object columnNamesFile[] = {"Name", "Size", "Type", "Modified"};

  Object columnNamesMyComputer[] = {"Name", "Type", "Size", "FreeSpace"};

  Object columnNames[] = columnNamesFile;

  Object data[][] = getTableData();

  public int getRowCount() {
    return (data == null) ? 0 : data.length;
  }

  public int getColumnCount() {
    return columnNames.length;
  }

  public String getColumnName(int col) {
    return columnNames[col].toString();
  }

  public Class getColumnClass(int column) {
    return getValueAt(0, column).getClass();
  }

  public Object getValueAt(int row, int col) {
    return data[row][col];
  }

  public void setValueAt(Object value, int row, int col) {
    data[row][col] = value;

    fireTableCellUpdated(row, col);
  }

  public boolean isCellEditable(int row, int column) {
    return false;
  }

  public void setColumnNames() {
    columnNames = getColumnNames();
  }

  public void setTableData() {
    data = getTableData();
  }

  private Object[] getColumnNames() {
    JDICTreeNode selectedTreeNode = JDICFileExplorer.selectedTreeNode;

    if (selectedTreeNode == null) {
      return null;
    }

    File selectedDir = (File) selectedTreeNode.getUserObject();

    if (selectedDir.equals(new File(JDICFileExplorer.MY_COMPUTER_FOLDER_PATH))) {
      return columnNamesMyComputer;
    } 
    else {
      return columnNamesFile;
    }
  }

  private Object[][] getTableData() {
    JDICTreeNode selectedTreeNode = JDICFileExplorer.selectedTreeNode;

    if (selectedTreeNode == null) {
      return null;
    }

    File selectedDir = (File) selectedTreeNode.getUserObject();

    if (selectedDir.equals(new File(JDICFileExplorer.MY_COMPUTER_FOLDER_PATH))) {
      File[] drivers = JDICUtility.getRoots();
      int driverNum = drivers.length;
      Object data[][] = new Object[driverNum][columnNames.length];

      int firstDriverNum = 0;

      if (drivers[firstDriverNum].getPath().toLowerCase().startsWith("a:")) {
        firstDriverNum = 1;
      }

      int curDriverNum = 0;

      for (int i = firstDriverNum; i < driverNum; i++) {
        data[curDriverNum][0] = new JDICDiskObject(drivers[i].getAbsolutePath(), JDICDiskObject.TYPE_DRIVER);
        data[curDriverNum][1] = "";
        data[curDriverNum][2] = " " + JDICDiskObject.TYPE_DRIVER;
        data[curDriverNum][3] = (new java.util.Date(drivers[i].lastModified())).toString();
        curDriverNum++;
      }

      return data;
    } 
    else {
      File[] files = selectedDir.listFiles();

      if (files == null) {
        return null;
      }

      int fileNum = files.length;
      Object data[][] = new Object[fileNum][columnNames.length];
      int curFileNum = 0;

      for (int i = 0; i < fileNum; i++) {
        File file = files[i];

        if (!file.isDirectory()) {
          data[curFileNum][0] = new JDICDiskObject(file.getName(), JDICDiskObject.TYPE_FILE);
          data[curFileNum][1] = JDICUtility.length2KB(file.length());
          data[curFileNum][2] = " " + JDICDiskObject.TYPE_FILE;
          data[curFileNum][3] = (new java.util.Date(file.lastModified())).toString();
          curFileNum++;
        }
      }

      for (int i = 0; i < fileNum; i++) {
        File file = files[i];

        if (file.isDirectory()) {
          data[curFileNum][0] = new JDICDiskObject(file.getName(), JDICDiskObject.TYPE_FOLDER);
          data[curFileNum][1] = "";
          data[curFileNum][2] = " " + JDICDiskObject.TYPE_FOLDER;
          data[curFileNum][3] = (new java.util.Date(file.lastModified())).toString();
          curFileNum++;
        }
      }

      return data;
    }
  }
}
