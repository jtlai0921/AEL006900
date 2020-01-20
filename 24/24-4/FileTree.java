import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import javax.swing.filechooser.FileSystemView;

import java.io.*;
import java.util.*;

public class FileTree extends JPanel implements
    TreeExpansionListener,   // 實作TreeExpansionListener介面
    TreeWillExpandListener { // 實作TreeWillExpandListener介面

  JTree fileTree;
  DefaultTreeModel treeModel;
  DefaultMutableTreeNode topNode;
  FileView fileView = new FileView();
  JComboBox cboDrive  = new JComboBox();
   
  TreeExpansionEventDemo parent;
  File[] roots;
  
  public FileTree(TreeExpansionEventDemo parent){
    this.parent = parent;

    roots = File.listRoots();

    for (int i=0; i<roots.length; i++) {    
      cboDrive.addItem(roots[i].getPath());
    }
    cboDrive.setSelectedIndex(0);

    // 註冊 ItemListener
    cboDrive.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        selectDrive();
      }
    });

    fileTree = new JTree();

    setFileTree(roots[0].getPath());
    
    fileTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    
    // 註冊TreeExpansionListener
    fileTree.addTreeExpansionListener(this);
    // 註冊TreeWillExpandListener
    fileTree.addTreeWillExpandListener(this);

    this.setLayout(new BorderLayout());

    JPanel Panel1 = new JPanel();
    Panel1.setLayout(new BorderLayout());

    JPanel Panel2 = new JPanel();
    Panel2.setLayout(new BorderLayout());
    Panel2.setBorder(BorderFactory.createEmptyBorder(0,0,4,1));

    JScrollPane jsp1 = new JScrollPane();
    jsp1.getViewport().add(fileTree);

    Panel2.add(cboDrive, BorderLayout.CENTER);
    Panel1.add(Panel2,   BorderLayout.NORTH);
    Panel1.add(jsp1, BorderLayout.CENTER);
    this.add(Panel1, BorderLayout.CENTER);
  }

  // 實作TreeExpansionListener介面的方法
  // 當樹狀物件之節點被折疊時所呼叫的方法
  public void treeCollapsed(TreeExpansionEvent e) {
    DefaultMutableTreeNode theNode = (DefaultMutableTreeNode)e.getPath().getLastPathComponent();

    System.out.println(theNode + "節點被折疊");
  }
    
  // 當樹狀物件之節點被展開時所呼叫的方法
  public void treeExpanded(TreeExpansionEvent e) {
    DefaultMutableTreeNode theNode = (DefaultMutableTreeNode)e.getPath().getLastPathComponent();

    System.out.println(theNode + "節點被展開");
  }

  // 實作TreeWillExpandListener介面的方法
  // 當樹狀物件之節點將被折疊時所呼叫的方法
  public void treeWillCollapse(TreeExpansionEvent e) throws ExpandVetoException {
    DefaultMutableTreeNode theNode = (DefaultMutableTreeNode)e.getPath().getLastPathComponent();

    System.out.println(theNode + "節點將被折疊");
  }

  // 當樹狀物件之節點將被展開時所呼叫的方法
  public void treeWillExpand(TreeExpansionEvent e) throws ExpandVetoException {
    DefaultMutableTreeNode theNode = (DefaultMutableTreeNode)e.getPath().getLastPathComponent();

    System.out.println(theNode + "節點將被展開");

    if (theNode.getChildCount() == 1){
      FileObject child = (FileObject)((DefaultMutableTreeNode)theNode.getChildAt(0)).getUserObject();
    
      if (child.getText().equals("NA") && child.getPath().equals("NA")){
        FileObject obj = (FileObject)theNode.getUserObject();
        
        File[] files = fileView.getFiles(new File(obj.getPath()), true);
      
        addNode(theNode, files);
      }
    }
  }

  private void selectDrive(){
    try {
      String selectDrive = roots[cboDrive.getSelectedIndex()].getPath();
      setFileTree(selectDrive);
    }
    catch (Exception ex){
    }
  }
  
  public void setFileTree(String topDir){
    File file = new File(topDir);
      
    try {
      topNode = new DefaultMutableTreeNode(new FileObject(topDir, file.getPath(), false, "Root Directory: " + file.getName()));
      
      treeModel = new DefaultTreeModel(topNode);
  
      // Gets the list of shown (not hidden) files. 
      File[] files = fileView.getFiles(new File(topDir), true);
      
      // JDK 5.0 泛型Generics
      TreeSet<Object> set = new TreeSet<Object>(new FileComparator());
  
      for (int i = 0; i<files.length; i++){
        set.add(files[i]);
      }
      
      files = new File[set.size()];
      Iterator iter = set.iterator();
      
      int k = 0;
      
      while (iter.hasNext()){
        files[k++] = (File)iter.next();
      }
      
      addNode(topNode, files);
  
      fileTree.setModel(treeModel);
    }
    catch (Exception ex){
      topNode = new DefaultMutableTreeNode(new FileObject(topDir, file.getPath(), false, "Root Directory: " + file.getName()));
      
      treeModel = new DefaultTreeModel(topNode);
      fileTree.setModel(treeModel);
    }
  }

  private void addNode(DefaultMutableTreeNode topNode, File[] files){
    DefaultMutableTreeNode node = null;

    if (treeModel.getChildCount(topNode)>=1){
      node = (DefaultMutableTreeNode)treeModel.getChild(topNode, 0);
    }
    else{
      node = topNode;
    }
    
    if (node != null && node.getChildCount() == 0 && node.toString().equals("NA")){
      treeModel.removeNodeFromParent(node);
    }

    // JDK 5.0 泛型Generics
    Vector<Object> vfile = new Vector<Object>();
      
    for (int i = 0; i < files.length; i++){
      File newFile = files[i];

      if (newFile.isDirectory()){
        DefaultMutableTreeNode node1 = new DefaultMutableTreeNode(new FileObject(newFile.getName(), newFile.getPath(), true, newFile.getName()));

        if (newFile.listFiles().length > 0){
          DefaultMutableTreeNode node2 = new DefaultMutableTreeNode(new FileObject("NA", "NA", false, "NA"));
          node1.add(node2);
        }
        else{
          DefaultMutableTreeNode node2 = new DefaultMutableTreeNode(new FileObject("", "", false, "(empty folder)"));
          node1.add(node2);
        }
        
        treeModel.insertNodeInto(node1, topNode, topNode.getChildCount());
      }
      else{
        vfile.add(new FileObject(newFile.getName(), newFile.getPath(), false, newFile.getName()));
      }
    }
    
    for (int i = 0; i < vfile.size(); i++){
      treeModel.insertNodeInto(new DefaultMutableTreeNode((FileObject) vfile.elementAt(i)), topNode, topNode.getChildCount());
    }    
  }

  private String topLevel(){
    String retVal = null;
    String path = fileView.getDefaultDirectory().getPath();
    String filesep = System.getProperty("file.separator");
    
    if (path.startsWith(filesep)){
      retVal = filesep;
    }
    else{
      path = path.substring(0, path.indexOf(filesep));
      File file = new File(path + filesep);
      retVal = file.getPath();
    }
    return retVal;
  }

  // JDK 5.0 泛型Generics
  private class FileComparator implements Comparator<Object>{  
    public int compare(Object o1, Object o2) {
      int retVal = 0;
      File f1, f2;
      f1 = (File)o1;
      f2 = (File)o2;
      retVal = f1.getName().compareToIgnoreCase(f2.getName());
      return retVal;
    }
  }
}

class FileView extends FileSystemView {
  public FileView() {
    super();
  }

  public File createNewFolder(File containingDir) throws IOException {
    return null;
  }
}

class FileObject {
  private String name;
  private String path;
  private boolean isDir;
  private String text;

  public FileObject(String name, String path,  boolean isDir, String text) {
    this.name = name;
    this.path = path;
    this.isDir = isDir;
    this.text = text;
  }

  public String getPath() {
    return path;
  }

  public String toString() {
    return name;
  }

  public boolean isDir() {
    return isDir;
  }

  public String getText() {
    return text;
  }
  
  public String getName() {
    return name;
  }
}
