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

public class JTableDemo extends JFrame implements MouseListener { // 實作MouseListener介面 

  JTable jtable;

  String menulabel[]={"File|F", "Help|H"};

  String menuitemlabel[][]={
    {"Print|P|print.gif", "-", "Exit|X"},
    {"About|A|about.gif"}
  };
  
  JMenu jmenu[];
  JMenuItem jmenuitem[][] = new JMenuItem[2][3];
      
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

    new JTableDemo();
  }
    
  // 建構函式
  public JTableDemo() {
    super("JTable Demo");

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
  
    // 建立JTable物件
    jtable = new JTable(data, columnheader);
    jtable.setPreferredScrollableViewportSize(new Dimension(500, 70));
    jtable.setFillsViewportHeight(true);

    // 註冊 MouseListener
    jtable.addMouseListener(this); 
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

  // 實作MouseListener介面之方法
  public void mouseClicked(MouseEvent e) {
    // 取得表格的列數
    int numRows = jtable.getRowCount();
    // 取得表格的行數
    int numColumns = jtable.getColumnCount();
    
    // 取得JTable類別之Table Model
    TableModel dataModel = jtable.getModel();

    for (int i=0; i < numRows; i++) {
      for (int j=0; j < numColumns; j++) {
        // 取得指定行（column）列（row）之單元欄位的值
        System.out.print(dataModel.getValueAt(i, j) + "  ");
      }
      System.out.println();
    }
  }

  public void mouseEntered(MouseEvent e) {}

  public void mouseExited(MouseEvent e) {}

  public void mousePressed(MouseEvent e) {}

  public void mouseReleased(MouseEvent e) {}

  public void mouseDragged(MouseEvent e) {}

  public void mouseMoved(MouseEvent e) {}

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
      JOptionPane.showMessageDialog(null, "JTable Demo", "About", JOptionPane.INFORMATION_MESSAGE);
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
}
