import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

import javax.swing.table.*;

import java.text.MessageFormat;
import java.awt.print.PrinterException;

import java.util.List;

public class RowSorterEventDemo extends JFrame implements RowSorterListener { // 實作RowSorterListener介面 

  JTable jtable;

  String menulabel[]={"File|F", "Options|O", "Help|H"};

  String menuitemlabel[][]={
    {"Print|P|print.gif", "-", "Exit|X"},
    {"Show Grid|S", "Horizontal Line|H", "Vertical Line|V", "Row Selection|V", "Column Selection|C", "Reorder Column|R", "-", "Single Selection|L", "Single Interval Selection|I", "Multiple Interval Selection|M"},
    {"About|A|about.gif"}
  };

  JMenu jmenu[];
  JMenuItem jmenuitem[][] = new JMenuItem[3][3];
  JCheckBoxMenuItem jcbmenuitem[] = new JCheckBoxMenuItem[10];
  
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

    new RowSorterEventDemo();
  }
    
  // 建構函式
  public RowSorterEventDemo() {
    super("Row Sorter Event Demo");

    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 設定視窗圖示
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // 定義 Layout Manager 為 BorderLayout
    this.setLayout(new BorderLayout());

    // 建立選單列
    JMenuBar jmenubar = createMenuBar();
    
    // 定義視窗使用者介面之選單列
    this.setJMenuBar(jmenubar);
  
    String[] columnheader = {"Customer ID", "Customer Name", "Product ID", "Category ID", "Quantity Per Unit", "Price"};
  
    Object[][] data = {
      {new Integer(1),  "Athena", new Integer(1), new Integer(1), "10 boxes x 20 bags", new Integer(18)},
      {new Integer(2),  "Leo",    new Integer(1), new Integer(1), "24 - 12 oz bottles", new Integer(20)},
      {new Integer(3),  "Teresa", new Integer(1), new Integer(2), "12 - 550 ml bottles", new Integer(19)},
      {new Integer(4),  "Ray",    new Integer(2), new Integer(2), "12 - 200 ml jars", new Integer(10)},
      {new Integer(5),  "Flora",  new Integer(2), new Integer(2), "32 - 500 g boxes", new Integer(5)},
      {new Integer(6),  "Shawn",  new Integer(3), new Integer(2), "750 cc per bottle", new Integer(12)},
      {new Integer(7),  "Howard", new Integer(3), new Integer(7), "24 - 50 g pkgs.", new Integer(21)},
      {new Integer(8),  "Gloria", new Integer(3), new Integer(2), "10 boxes x 8 pieces", new Integer(14)},
      {new Integer(9),  "Rachel", new Integer(4), new Integer(6), "16 - 2 kg boxes", new Integer(2)},
      {new Integer(10), "Tony",   new Integer(4), new Integer(8), "10 - 4 oz boxes", new Integer(6)}
    };

    // 建立Table Model
    DefaultTableModel dataModel = new DefaultTableModel();
    
    for (int i=0 ; i < columnheader.length ; i++) {
      // 新增直行標題至Table Column Model
      dataModel.addColumn(columnheader[i]);
    }
    
    for (int i=0 ; i < data.length ; i++) {
      // 新增橫列資料至Table Column Model
      dataModel.addRow(data[i]);
    }
  
    // 以Table Model建立JTable物件
    jtable = new JTable(dataModel);
    jtable.setPreferredScrollableViewportSize(new Dimension(500, 70));
    jtable.setFillsViewportHeight(true);
    
    // 建立表格之排序器（Row Sorter）
    TableRowSorter sorter = new TableRowSorter(dataModel);
    // 設定表格之排序器（Row Sorter）
    jtable.setRowSorter(sorter);

    // 是否顯示表格的格線
    jtable.setShowGrid(true); 
    // 是否顯示表格的水平格線
    jtable.setShowHorizontalLines(true); 
    // 是否顯示表格的垂直格線
    jtable.setShowVerticalLines(true); 
    // 是否允許選擇橫列
    jtable.setRowSelectionAllowed(true);
    // 是否允許選擇直行
    jtable.setColumnSelectionAllowed(true);
    // 是否允許移動直行
    jtable.getTableHeader().setReorderingAllowed(true);
    // 設定多重選擇模式為單選
    jtable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    // 註冊RowSorterListener
    jtable.getRowSorter().addRowSorterListener(this);

    // 捲軸
    JScrollPane jscrollpane = new JScrollPane(jtable);
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
    this.setSize(new Dimension(500, 200));
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });    
  }

  // 實作RowSorterListener介面之方法
  // 當直行欄位之排序改變時
  public void sorterChanged(RowSorterEvent e) {
    int i = 0;
    
    // 取得產生事件之RowSorter物件    
    RowSorter source = (RowSorter)e.getSource();
    
    // 取得排序之Key值
    List<RowSorter.SortKey> sortKeys = source.getSortKeys();
    
    if (sortKeys != null && sortKeys.size() > 0) {
      for (RowSorter.SortKey sortKey : sortKeys) {
        // 取得排序之直行欄位的索引值
        int columnIndex = sortKey.getColumn();
        
        // 判斷排序類型
        if (sortKey.getSortOrder() == SortOrder.ASCENDING) {
          if (i == 0)
            System.out.println("以" + jtable.getColumnName(columnIndex) + "欄位遞增排序");
          else
            System.out.println("曾以" + jtable.getColumnName(columnIndex) + "欄位遞增排序");
        }          
        else if (sortKey.getSortOrder() == SortOrder.DESCENDING) {
          if (i == 0)
            System.out.println("以" + jtable.getColumnName(columnIndex) + "欄位遞減排序");
          else
            System.out.println("曾以" + jtable.getColumnName(columnIndex) + "欄位遞減排序");
        }          
        else if (sortKey.getSortOrder() == SortOrder.UNSORTED) {
          if (i == 0)
            System.out.println("以" + jtable.getColumnName(columnIndex) + "欄位無排序");
          else
            System.out.println("曾以" + jtable.getColumnName(columnIndex) + "欄位無排序");
        }          
          
        i++;
      }
    }
  }
  
  private JMenuBar createMenuBar() {
    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 建立選單列
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
        // 加入選單至選單列
        jmenubar.add(jmenu[i]);
      else
        // 設定選單列中的輔助說明選單
        jmenubar.setHelpMenu(jmenu[i]);
    }

    // 建構群組
    ButtonGroup group1 = new ButtonGroup();

    for(int i=0; i<menulabel.length; i++){
      for(int j=0; j<menuitemlabel[i].length; j++){
        if (menuitemlabel[i][j].equals("-")) {
          // 加入分隔線
          jmenu[i].addSeparator();
          // 或
          // jmenu[i].add(new JSeparator());
        }
        else {
          if (i != 1) {
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
          else {
            if (menuitemlabel[i][j].equals("-")) 
              // 加入分隔線
              jmenu[i].addSeparator();
            // 建立核取方塊選單項目
            else if (menuitemlabel[i][j].indexOf("|") != -1)
              jcbmenuitem[j] = new JCheckBoxMenuItem(menuitemlabel[i][j].substring(0, menuitemlabel[i][j].indexOf("|")));
            else
              jcbmenuitem[j] = new JCheckBoxMenuItem(menuitemlabel[i][j]);
    
            // 設定選單項目助記碼
            jcbmenuitem[j].setMnemonic(menuitemlabel[i][j].split("\\|")[1].charAt(0));

            // 建立圖像
            if (menuitemlabel[i][j].endsWith(".gif")) 
              jcbmenuitem[j].setIcon(new ImageIcon(cl.getResource("images/" + menuitemlabel[i][j].substring(menuitemlabel[i][j].lastIndexOf("|")+1))));

            // 設定核取方塊選單項目之選取狀態
            if (j!=8 || j!=9) 
              jcbmenuitem[j].setState(true);
              
            if (j>=7) 
              group1.add(jcbmenuitem[j]);
              
            // 註冊 ItemListener
            jcbmenuitem[j].addItemListener(new ItemListener() {
              public void itemStateChanged(ItemEvent e) {
                menu_itemStateChanged(e);
              }
            });
  
            // 加入選單項目
            jmenu[i].add(jcbmenuitem[j]);
          }
        }
      }
    }
    
    return jmenubar;
  }

  private void menu_actionPerformed(ActionEvent e) {
    // 取得產生動作事件的選單項目
    JMenuItem jmenuitem = (JMenuItem)e.getSource();

    if (jmenuitem.getText().equals("Print")) { // Print
      printTable();
    }
    else if (jmenuitem.getText().equals("Exit")) { // Exit
      JOptionPane joptionpane = new JOptionPane();
      int iResult = joptionpane.showConfirmDialog(null, "Are you sure to quit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
      
      if (iResult == 0) {
        dispose();
        System.exit(0);
      }
    }
    else if (jmenuitem.getText().equals("About")) { // About
      JOptionPane.showMessageDialog(null, "Row Sorter Event Demo", "About", JOptionPane.INFORMATION_MESSAGE);
    }
  }
  
  private void printTable() {
    // 頁首格式
    MessageFormat headerFormat = new MessageFormat("JTable Header");
    // 頁尾格式
    MessageFormat footerFormat = new MessageFormat("Page {0}");

    // 配合紙張大小，以表格寬度調整列印表格的比例，以便列印時能容納所有的直行
    JTable.PrintMode printMode = JTable.PrintMode.FIT_WIDTH;
    // 不調整列印表格的比例，以表格正常大小列印   
    // JTable.PrintMode printMode = JTable.PrintMode.NORMAL;

    try {
      boolean status = jtable.print(printMode, headerFormat, footerFormat);

      if (status) 
        JOptionPane.showMessageDialog(null, "列印完成", "JTable Print Demo", JOptionPane.INFORMATION_MESSAGE);
      else
        JOptionPane.showMessageDialog(null, "取消列印", "JTable Print Demo", JOptionPane.INFORMATION_MESSAGE);
    } 
    catch (PrinterException pex) {
      pex.printStackTrace();
    } 
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }  
  
  private void menu_itemStateChanged(ItemEvent e) {
    // 取得產生項目事件的選單項目
    JCheckBoxMenuItem jcbmenuitem = (JCheckBoxMenuItem)e.getSource();
    
    if (jcbmenuitem.getText().equals("Show Grid")) { // Show Grid
      // 是否顯示表格的格線
      jtable.setShowGrid(jcbmenuitem.isSelected()); 
    }
    else if (jcbmenuitem.getText().equals("Horizontal Line")) { // Horizontal Line
      // 是否顯示表格的水平格線
      jtable.setShowHorizontalLines(jcbmenuitem.isSelected()); 
    }
    else if (jcbmenuitem.getText().equals("Vertical Line")) { // Vertical Line
      // 是否顯示表格的垂直格線
      jtable.setShowVerticalLines(jcbmenuitem.isSelected()); 
    }
    else if (jcbmenuitem.getText().equals("Row Selection")) { // Row Selection
      // 是否允許選擇橫列
      jtable.setRowSelectionAllowed(jcbmenuitem.isSelected()); 
    }
    else if (jcbmenuitem.getText().equals("Column Selection")) { // Column Selection
      // 是否允許選擇直行
      jtable.setColumnSelectionAllowed(jcbmenuitem.isSelected()); 
    }
    else if (jcbmenuitem.getText().equals("Reorder Column")) { // Reorder Column
      // 是否允許移動直行
      jtable.getTableHeader().setReorderingAllowed(jcbmenuitem.isSelected()); 
    }
    else if (jcbmenuitem.getText().equals("Single Selection")) { // Single Selection
      // 設定多重選擇模式為單選
      jtable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
    }
    else if (jcbmenuitem.getText().equals("Single Interval Selection")) { // Single Interval Selection
      // 設定多重選擇模式為僅可選擇單一區間，但此區間內可選擇多個項目
      jtable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION); 
    }
    else if (jcbmenuitem.getText().equals("Multiple Interval Selection")) { // Multiple Interval Selection
      // 設定多重選擇模式為多重區間選擇
      jtable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); 
    }
  }
}
  