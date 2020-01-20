import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.tree.*;
import javax.swing.table.TableColumn;

import java.net.URL;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Vector;
import java.io.IOException;
import java.io.File;

// JDIC
import org.jdesktop.jdic.desktop.*;

public class JDICFileExplorer extends JFrame implements 
  TreeExpansionListener,   // 實作TreeExpansionListener介面 
  TreeSelectionListener,   // 實作TreeSelectionListener介面 
  TreeWillExpandListener { // 實作TreeWillExpandListener介面 
  
  String menulabel[]={"File|F", "Help|H"};

  String menuitemlabel[][]={
    {"Exit|X"},
    {"About|A|about.gif"}
  };

  JMenu jmenu[];
  JMenuItem jmenuitem[][] = new JMenuItem[2][1];

  public static ImageIcon computerIcon;
  public static ImageIcon driverIcon;
  public static ImageIcon folderIcon;
  public static ImageIcon fileIcon;

  public static String MY_COMPUTER_FOLDER_PATH = System.getProperty("java.io.tmpdir") + File.separator + "My Computer";

  public static JDICTreeNode selectedTreeNode = null;

  File selectedFile = null;

  JSplitPane jSplitPane = new JSplitPane();
  JTree jTreeDisk = new JTree(createTreeModel());

  JScrollPane tableScrollPane = new JScrollPane();
  JDICTableModel tableModel = new JDICTableModel();
  JDICTable jTable = new JDICTable();

  // Main method
  public static void main(String[] args) {
    UIManager.put("swing.boldMetal", Boolean.FALSE);
    JDialog.setDefaultLookAndFeelDecorated(true);
    JFrame.setDefaultLookAndFeelDecorated(true);
    Toolkit.getDefaultToolkit().setDynamicLayout(true);

    try {
      UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    new JDICFileExplorer();
  }

  // 建構函式
  public JDICFileExplorer() {
    super("JDIC File Explorer");
    
    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 設定視窗圖示
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // 定義Layout Manager 為 BorderLayout
    this.setLayout(new BorderLayout());

    // 建立選單功能列
    JMenuBar jmenubar = createMenuBar();
    
    // 定義視窗使用者介面之選單功能列
    this.setJMenuBar(jmenubar);

    computerIcon = new ImageIcon(cl.getResource("images/Computer.gif"));
    driverIcon = new ImageIcon(cl.getResource("images/Driver.gif"));
    folderIcon = new ImageIcon(cl.getResource("images/Folder.gif"));
    fileIcon = new ImageIcon(cl.getResource("images/File.gif"));

    jTreeDisk.setCellRenderer(new JDICTreeRenderer());
    jTreeDisk.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    jTreeDisk.setSelectionRow(0);
    jTreeDisk.setBackground(Color.white);
    jTreeDisk.setAlignmentX((float) 0.5);
    jTreeDisk.setShowsRootHandles(false);

    // 註冊 TreeExpansionListener
    jTreeDisk.addTreeExpansionListener(this);
    // 註冊 SelectionListener
    jTreeDisk.addTreeSelectionListener(this);
    // 註冊 TreeWillExpandListener
    jTreeDisk.addTreeWillExpandListener(this);

    // 以Inner Class的方式使用MouseAdapter
    jTreeDisk.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        jTreeDisk_mouseClicked(e);
      }
    
      public void mouseEntered(MouseEvent e) {}
      public void mouseExited(MouseEvent e) {}
      public void mousePressed(MouseEvent e) {}
      public void mouseReleased(MouseEvent e) {}
    }); 

    jTable.setBorder(null);
    jTable.setModel(tableModel);
    TableColumn column = jTable.getColumnModel().getColumn(0);

    column.setCellRenderer(new JDICTableRenderer());
    column = jTable.getColumnModel().getColumn(1);
    column.setCellRenderer(new JDICTableRenderer());

    jTable.setShowHorizontalLines(false);
    jTable.setShowVerticalLines(false);
    
    // 以Inner Class的方式使用MouseAdapter
    jTable.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        jTable_mouseClicked(e);
      }
    
      public void mouseEntered(MouseEvent e) {}
      public void mouseExited(MouseEvent e) {}
      public void mousePressed(MouseEvent e) {}
      public void mouseReleased(MouseEvent e) {}
    });   

    tableScrollPane.setViewportView(jTable);

    jSplitPane.setBorder(null);
    // 設定是否以分隔Widget快速展開或摺疊分隔面板
    jSplitPane.setOneTouchExpandable(true);
    // 設定分隔軸的位置
    jSplitPane.setDividerLocation(150);
    jSplitPane.add(new JScrollPane(jTreeDisk), JSplitPane.LEFT);
    jSplitPane.add(tableScrollPane, JSplitPane.RIGHT);

    this.add(jSplitPane, BorderLayout.CENTER);

    this.validate();
    this.setSize(new Dimension(640, 480));

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(true);
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }

  private JMenuBar createMenuBar() {
    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 建立選單功能列
    JMenuBar jmenubar = new JMenuBar();

    jmenu = new JMenu[menulabel.length];
    
    // 建立選單
    for (int i=0; i<menulabel.length; i++){
      // 建立選單
      if (menulabel[i].indexOf("|") != -1)
        jmenu[i] = new JMenu(menulabel[i].substring(0, menulabel[i].indexOf("|")));
      else
        jmenu[i] = new JMenu(menulabel[i]);

      // 設定選單助記碼
      jmenu[i].setMnemonic(menulabel[i].split("\\|")[1].charAt(0));

      if (i != menulabel.length)
        // 加入選單至選單功能列
        jmenubar.add(jmenu[i]);
      else
        // 設定選單功能列中的輔助說明選單
        jmenubar.setHelpMenu(jmenu[i]);
    }

    for(int i=0; i<menulabel.length; i++){
      for(int j=0; j<menuitemlabel[i].length; j++){
        if (menuitemlabel[i][j].equals("-")) {
          // 加入分隔線
          jmenu[i].addSeparator();
          // 或
          // jmenu[i].add(new JSeparator());
        }
        else {
          // 建立選單項目
          if (menuitemlabel[i][j].indexOf("|") != -1)
            jmenuitem[i][j] = new JMenuItem(menuitemlabel[i][j].substring(0, menuitemlabel[i][j].indexOf("|")));
          else
            jmenuitem[i][j] = new JMenuItem(menuitemlabel[i][j]);

          // 設定選單項目助記碼
          jmenuitem[i][j].setMnemonic(menuitemlabel[i][j].split("\\|")[1].charAt(0));

          // 建立圖像
          if (menuitemlabel[i][j].endsWith(".gif")) 
            jmenuitem[i][j].setIcon(new ImageIcon(cl.getResource("images/" + menuitemlabel[i][j].substring(menuitemlabel[i][j].lastIndexOf("|")+1))));

          // 註冊 ActionListener
          jmenuitem[i][j].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              menu_actionPerformed(e);
            }
          });

          // 加入選單項目
          jmenu[i].add(jmenuitem[i][j]);
        }
      }
    }
    
    return jmenubar;
  }

  private void menu_actionPerformed(ActionEvent e) {
    // 取得產生動作事件的選單項目
    JMenuItem menuitem = (JMenuItem)e.getSource();

    if (menuitem.getText().equals("Exit")) { // Exit
      JOptionPane joptionpane = new JOptionPane();
      int iResult = joptionpane.showConfirmDialog(null, "Are you sure to quit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
      
      if (iResult == 0) {
        dispose();
        System.exit(0);
      }
    }
    else if (menuitem.getText().equals("About")) { // About
      JOptionPane.showMessageDialog(null, "JDIC File Explorer", "About", JOptionPane.INFORMATION_MESSAGE);
    }
  }

  private DefaultTreeModel createTreeModel() {
    JDICTreeNode rootNode = null;

    String osName = System.getProperty("os.name").toLowerCase();

    if (osName.startsWith("windows")) {
      File MY_COMPUTER_FOLDER_FILE = new File(MY_COMPUTER_FOLDER_PATH);

      MY_COMPUTER_FOLDER_FILE.mkdirs();

      MY_COMPUTER_FOLDER_FILE.deleteOnExit();

      rootNode = new JDICTreeNode(MY_COMPUTER_FOLDER_FILE);

      JDICTreeNode parent;
      File[] roots = JDICUtility.getRoots();

      int readfirst = 0;

      if (roots[readfirst].getPath().toLowerCase().startsWith("a:")) {
        readfirst = 1;
      }
      for (int i = readfirst; i < roots.length; i++) {
        parent = new JDICTreeNode(roots[i]);

        parent.explore();

        rootNode.add(parent);
      }

      selectedTreeNode = rootNode;
      selectedFile = null;
    } 
    else if (osName.startsWith("linux") || osName.startsWith("sunos") || osName.startsWith("freebsd") || osName.startsWith("mac os")) {
      File rootFile = new File("/");

      rootNode = new JDICTreeNode(rootFile);

      rootNode.explore();
      selectedTreeNode = (JDICTreeNode) rootNode;
      selectedFile = selectedTreeNode.getFile();
    } 

    return new DefaultTreeModel(rootNode);
  }

  // 實作javax.swing.event.TreeExpansionListener的treeExpanded()方法
  public void treeExpanded(TreeExpansionEvent e) {
    DefaultTreeModel treeModel = (DefaultTreeModel) jTreeDisk.getModel();
    TreePath newPath = new TreePath(treeModel.getPathToRoot(selectedTreeNode));

    jTreeDisk.setSelectionPath(newPath);
    jTreeDisk.scrollPathToVisible(newPath);
  }
  
  // 實作javax.swing.event.TreeExpansionListener的treeCollapsed()方法
  public void treeCollapsed(TreeExpansionEvent e) {}

  // 實作javax.swing.event.TreeSelectionListener的valueChanged()方法
  public void valueChanged(TreeSelectionEvent e) {
    selectedTreeNode = (JDICTreeNode) jTreeDisk.getLastSelectedPathComponent();

    selectedFile = null;

    if (selectedTreeNode != null) {
      File selectedDir = (File) selectedTreeNode.getUserObject();
    }

    tableModel.setColumnNames();
    tableModel.setTableData();
    jTable.repaint();       

    tableScrollPane.revalidate();         
  }

  // 實作javax.swing.event.TreeWillExpandListener的treeWillExpand()方法
  public void treeWillExpand(TreeExpansionEvent e) throws ExpandVetoException {
    TreePath path = e.getPath();
    JDICTreeNode selectedNode = (JDICTreeNode) path.getLastPathComponent();

    if (!selectedNode.isExplored()) {
      selectedNode.explore();
    }
  }

  // 實作javax.swing.event.TreeWillExpandListener的treeWillCollapse()方法
  public void treeWillCollapse(TreeExpansionEvent e) {}

  public void jTreeDisk_mouseClicked(MouseEvent e) {
    TreePath curTreePath = jTreeDisk.getClosestPathForLocation(e.getX(), e.getY());

    jTreeDisk.clearSelection();
    jTreeDisk.addSelectionPath(curTreePath);
  }

  private void exploreDirectory(JDICTreeNode parentTreeNode, File selectedSubDir) {
    if (!parentTreeNode.isExplored()) {
      parentTreeNode.explore();
    }

    int count = jTreeDisk.getModel().getChildCount(parentTreeNode);

    for (int i = 0; i < count; i++) {
      Object oneChild = jTreeDisk.getModel().getChild(parentTreeNode, i);

      if (oneChild instanceof JDICTreeNode) {
        File file = (File) ((JDICTreeNode) oneChild).getUserObject();

        if (file.equals(selectedSubDir)) {
          selectedTreeNode = (JDICTreeNode) oneChild;
          break;
        }
      }
    }

    TreePath newPath = new TreePath(selectedTreeNode.getPath());

    if (jTreeDisk.isExpanded(newPath)) {
      jTreeDisk.setSelectionPath(newPath);
      jTreeDisk.scrollPathToVisible(newPath);
    } 
    else {
      jTreeDisk.expandPath(newPath);
    }
  }

  private void expandPaths(JTree tree, List paths) {
    Iterator iter = paths.iterator();

    if (!iter.hasNext()) {
      return;
    }

    JDICTreeNode parentNode = (JDICTreeNode) tree.getModel().getRoot();

    if (!parentNode.isExplored()) {
      parentNode.explore();
    }

    File parentFile = (File) ((JDICTreeNode) parentNode).getUserObject();

    if (parentFile.equals(new File(JDICFileExplorer.MY_COMPUTER_FOLDER_PATH))) {
      int count = jTreeDisk.getModel().getChildCount(parentNode);
      boolean pathNotFound = true;

      for (int i = 0; i < count; i++) {
        Object oneChild = jTreeDisk.getModel().getChild(parentNode, i);
        String onePath = ((JDICTreeNode) oneChild).toString();

        if (onePath.equalsIgnoreCase((String) iter.next())) {
          parentNode = (JDICTreeNode) oneChild;
          pathNotFound = false;
          break;
        }
      }
    } 
    else {
      if (!parentFile.equals((String) iter.next())) {
        return;
      }
    }

    boolean pathNotFound = false;

    while (iter.hasNext() && !pathNotFound) {
      if (!parentNode.isExplored()) {
        parentNode.explore();
      }

      String nextPath = (String) iter.next();

      pathNotFound = true;
      int count = jTreeDisk.getModel().getChildCount(parentNode);

      for (int i = 0; i < count; i++) {
        Object oneChild = jTreeDisk.getModel().getChild(parentNode, i);
        String onePath = ((JDICTreeNode) oneChild).toString();

        if (onePath.equalsIgnoreCase(nextPath)) {
          parentNode = (JDICTreeNode) oneChild;
          pathNotFound = false;
          break;
        }
      }
    }

    if (pathNotFound) {
      return;
    } 
    else {
      selectedTreeNode = parentNode;
      TreePath newPath = new TreePath(selectedTreeNode.getPath());

      if (jTreeDisk.isExpanded(newPath)) {
        jTreeDisk.setSelectionPath(newPath);
        jTreeDisk.scrollPathToVisible(newPath);
      } 
      else {
        jTreeDisk.expandPath(newPath);
      }
    }
  }

  private void exploreDirectory(File selectedDir) {
    // JDK 5.0 泛型Generics
    Vector<Object> paths = new Vector<Object>();
    String selectedAbsPath = selectedDir.getAbsolutePath();

    int beginIndex = 0;
    int endIndex = selectedAbsPath.indexOf(File.separator);

    paths.add(selectedAbsPath.substring(beginIndex, endIndex + 1));
    beginIndex = endIndex + 1;
    endIndex = selectedAbsPath.indexOf(File.separator, beginIndex);

    while (endIndex != -1) {
      paths.add(selectedAbsPath.substring(beginIndex, endIndex));
      beginIndex = endIndex + 1;
      endIndex = selectedAbsPath.indexOf(File.separator, beginIndex);
    }

    String lastPath = selectedAbsPath.substring(beginIndex,  selectedAbsPath.length());

    if ((lastPath != null) && (lastPath.length() != 0)) {
      paths.add(lastPath);
    }

    expandPaths(jTreeDisk, paths);
  }


  private void jTable_mouseClicked(MouseEvent e) {
    int curRow = jTable.rowAtPoint(new Point(e.getX(), e.getY()));

    if (curRow == -1) {
      selectedFile = null;
      jTable.clearSelection();
      return;
    }

    if (SwingUtilities.isRightMouseButton(e)) {
      curRow = jTable.rowAtPoint(new Point(e.getX(), e.getY()));
      jTable.clearSelection();
      jTable.addRowSelectionInterval(curRow, curRow);
    }

    ListSelectionModel lsm = jTable.getSelectionModel();
    int selectedRow = lsm.getMinSelectionIndex();

    JDICDiskObject selectedObject = (JDICDiskObject) tableModel.getValueAt(selectedRow, 0);

    File selectedDir = (File) selectedTreeNode.getUserObject();

    if (selectedDir.equals(new File(JDICFileExplorer.MY_COMPUTER_FOLDER_PATH))) {
      selectedFile = new File(selectedObject.name);
    } 
    else {
      selectedFile = new File(selectedDir.toString() + File.separator + selectedObject.name);
    }

    if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
      if (!selectedFile.isDirectory()) {
        try {
          org.jdesktop.jdic.desktop.Desktop.open(selectedFile);
        } 
        catch (DesktopException ex) {
          ex.printStackTrace();
        }
      } 
      else {
        exploreDirectory(selectedTreeNode, selectedFile);
      }
    }
  }
}
  