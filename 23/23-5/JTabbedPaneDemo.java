import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.event.*;

import java.io.*;
import java.text.*;
import java.util.*;

// Print Job
import java.awt.print.*;  

public class JTabbedPaneDemo extends JFrame {
    
  JToolBar jtoolbar = new JToolBar();
  JButton jbutton[] = new JButton[7];
  
  JTextArea jtextarea;
  
  JPopupMenu jpopupmenu1 = new JPopupMenu();
  JPopupMenu jpopupmenu2 = new JPopupMenu();

  String menulabel[]={"File|F", "Edit|E", "Tab|T", "Help|H"};

  String menuitemlabel[][]={
    {"New|N|new.gif", "Open|O|open.gif", "-", "Save|S|save.gif", "-", "Print|P|print.gif", "-", "Exit|X"},
    {"Cut|T|cut.gif", "Copy|C|copy.gif", "Paste|P|paste.gif"},
    {"New Tab", "-", "Wrap", "Scroll", "Top", "Bottom", "Left", "Right"},
    {"About|A|about.gif"}
  };

  String item[]  = {"New", "Open", "Save", "Cut", "Copy", "Paste", "About"};
  String image[] = {"new", "open", "save", "cut", "copy", "paste", "about"};

  JMenu jmenu[];
  JMenuItem jmenuitem[][] = new JMenuItem[4][8];

  JTabbedPane jtabbedpane;
  JRadioButtonMenuItem[] jrbmenuitem = new JRadioButtonMenuItem[8];

  ImageIcon tabImage;
  
  // JDK 5.0 泛型Generics
  static Vector<Object> vTextArea = new Vector<Object>();

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

    new JTabbedPaneDemo();
  }

  // 建構函式
  public JTabbedPaneDemo() {
    super("JTabbedPane Demo");
  
    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 設定視窗圖示
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    tabImage = new ImageIcon(cl.getResource("images/newpage.gif"));

    // 定義Layout Manager 為 BorderLayout
    this.setLayout(new BorderLayout());

    // 建立選單列
    JMenuBar jmenubar = createMenuBar();
    
    // 定義視窗使用者介面之選單列
    this.setJMenuBar(jmenubar);
    
    // 設定不啟用Undo選單項目
    jmenuitem[1][0].setEnabled(false);
    // 設定不啟用Redo選單項目
    jmenuitem[1][1].setEnabled(false);

    // Cut
    JMenuItem jmnuCut1 = new JMenuItem(item[3], new ImageIcon(cl.getResource("images/" + image[3] + ".gif")));
    jmnuCut1.setMnemonic('t');
    jmnuCut1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
    jmnuCut1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        menu_actionPerformed(e);
      }
    });
    // Copy
    JMenuItem jmnuCopy1 = new JMenuItem(item[4], new ImageIcon(cl.getResource("images/" + image[4] + ".gif")));
    jmnuCopy1.setMnemonic('C');
    jmnuCopy1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
    jmnuCopy1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        menu_actionPerformed(e);
      }
    });

    // Paste
    JMenuItem jmnuPaste1 = new JMenuItem(item[5], new ImageIcon(cl.getResource("images/" + image[5] + ".gif")));
    jmnuPaste1.setMnemonic('P');
    jmnuPaste1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
    jmnuPaste1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        menu_actionPerformed(e);
      }
    });
    
    // 建立突顯式選單
    jpopupmenu1.add(jmnuCut1);
    jpopupmenu1.add(jmnuCopy1);
    jpopupmenu1.add(jmnuPaste1);

    JMenuItem jmenuAdd1 = new JMenuItem("New Tab");
    jmenuAdd1.setMnemonic('N');
    jmenuAdd1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        createTab();
      }
    });

    // Close Tab
    JMenuItem jmenuClose1 = new JMenuItem("Close Tab");
    jmenuClose1.setMnemonic('C');
    jmenuClose1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        closeTab();
      }
    });
    // 建立突顯式選單
    jpopupmenu2.add(jmenuAdd1);
    jpopupmenu2.add(jmenuClose1);

    for (int i=0; i<image.length; i++) {
      jbutton[i] = new JButton(new ImageIcon(cl.getResource("images/" + image[i] + ".gif")));
      jbutton[i].setPreferredSize(new Dimension(32, 32));
      jbutton[i].setToolTipText(item[i]);
      
      // 註冊 ActionListener
      jbutton[i].addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          button_actionPerformed(e);
        }
      });

      jtoolbar.add(jbutton[i]);

      if (i==2 || i==5)
        jtoolbar.addSeparator();
    }
   
    jtabbedpane = new JTabbedPane();
    // 設定分頁的置放位置
    jtabbedpane.setTabPlacement(SwingConstants.TOP);
    // 設定分頁的排列原則
    jtabbedpane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    jtabbedpane.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        showPopmenu(e);
      }
    });

    // Create a new tab
    createTab();
    
    this.add(jtoolbar, BorderLayout.NORTH);
    this.add(jtabbedpane, BorderLayout.CENTER);

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    this.validate();
    this.setSize(new Dimension(400, 300));
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
          if (i == 2) { 
            if (j == 0 || j== 1) {
              if (menuitemlabel[i][j].equals("-")) 
                // 加入分隔線
                jmenu[i].addSeparator();
              else {
                jmenuitem[i][j] = new JMenuItem(menuitemlabel[i][j]);
                    
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
            else {
              // 建立選項按鈕選單項目
              if (menuitemlabel[i][j].indexOf("|") != -1)
                jrbmenuitem[j] = new JRadioButtonMenuItem(menuitemlabel[i][j].substring(0, menuitemlabel[i][j].indexOf("|")));
              else
                jrbmenuitem[j] = new JRadioButtonMenuItem(menuitemlabel[i][j]);
  
              // 建立選項按鈕選單項目
              jrbmenuitem[j] = new JRadioButtonMenuItem(menuitemlabel[i][j]);
      
              // 設定選項按鈕選單項目之選取狀態
              if (j==3 || j==4) 
                jrbmenuitem[j].setSelected(true);
  
              // 註冊 ActionListener
              jrbmenuitem[j].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                  menu_actionPerformed(e);
                }
              });
            }
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
    }

    // 建立子選單
    JMenu submenu1 = new JMenu("Layout Policy");
    submenu1.setMnemonic(KeyEvent.VK_L);
    JMenu submenu2 = new JMenu("Placement");
    submenu2.setMnemonic(KeyEvent.VK_P);

    // 建構群組
    ButtonGroup group1 = new ButtonGroup();
    ButtonGroup group2 = new ButtonGroup();

    submenu1.add(jrbmenuitem[2]);
    submenu1.add(jrbmenuitem[3]);
    jmenu[2].add(submenu1);
    submenu2.add(jrbmenuitem[4]);
    submenu2.add(jrbmenuitem[5]);
    submenu2.add(jrbmenuitem[6]);
    submenu2.add(jrbmenuitem[7]);
    jmenu[2].add(submenu2);
    group1.add(jrbmenuitem[2]);
    group1.add(jrbmenuitem[3]);
    group2.add(jrbmenuitem[4]);
    group2.add(jrbmenuitem[5]);
    group2.add(jrbmenuitem[6]);
    group2.add(jrbmenuitem[7]);
    
    return jmenubar;
  }

  private void showPopmenu(MouseEvent e) {
    // 當按下滑鼠按鍵時
    if (e.getButton() == MouseEvent.BUTTON2 || e.getButton() == MouseEvent.BUTTON3) {
      int j = jtabbedpane.getSelectedIndex();    
      JTextArea currentPane = (JTextArea) vTextArea.elementAt(j);

      if (e.getSource().equals(currentPane)) { // JTextArea
        // 顯示突顯式選單
        jpopupmenu1.show(e.getComponent(), e.getX(), e.getY());
      }
      else if (e.getSource().equals(jtabbedpane)) { // JTabbedPane
        // 顯示突顯式選單
        jpopupmenu2.show(e.getComponent(), e.getX(), e.getY());
      }
    }
  }

  private void createTab() {
    jtextarea = new JTextArea();

    // 設定自動換行的規則
    jtextarea.setWrapStyleWord(false);
    // 設定JTextArea當文字超過行寬時，是否自動換行。
    jtextarea.setLineWrap(false);
    // 設定是否可編輯
    jtextarea.setEditable(true);
    // 設定脫字符號目前的位置
    jtextarea.setCaretPosition(0);
    
    // 設定文字字型
    jtextarea.setFont(new Font("Courier New", Font.PLAIN, 11));
    jtextarea.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        showPopmenu(e);
      }
    });

    vTextArea.add(jtextarea);
        
    // 設定捲軸面板
    JScrollPane jscrollpane = new JScrollPane();
    // 新增分頁
    jtabbedpane.addTab("(Blank)", tabImage, jscrollpane);
    // 選取第index個分頁
    jtabbedpane.setSelectedIndex(jtabbedpane.getTabCount()-1);
    // 取得捲軸面板的視界
    jscrollpane.getViewport().add((java.awt.Component) vTextArea.elementAt(jtabbedpane.getSelectedIndex()));
    // 垂直捲軸：當超過JTextArea列數時才自動顯示垂直捲軸
    jscrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    // 水平捲軸：當超過JTextArea行數時才自動顯示水平捲軸
    jscrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
  }

  private void closeTab() {
    if (jtabbedpane.getTabCount() > 1) {
      // 取得已被選取分頁的索引值
      int j = jtabbedpane.getSelectedIndex();
      // 移除第index個分頁
      jtabbedpane.removeTabAt(j);
      vTextArea.remove(j);
    }
  }

  private void menu_actionPerformed(ActionEvent e) {
    // 取得產生動作事件的選單項目
    JMenuItem menuitem = (JMenuItem)e.getSource();

    final JTextArea currentPane = (JTextArea) vTextArea.elementAt(jtabbedpane.getSelectedIndex());

    if (menuitem.getText().equals("New")) { // New
      currentPane.setText("");
    }
    else if (menuitem.getText().equals("Open")) { // Open
      open();
    }
    else if (menuitem.getText().equals("Save")) { // Save
      save();
    }
    else if (menuitem.getText().equals("Print")) { // Print
      if (! currentPane.getText().equals("")) {
        new Thread() {
          public void run() {
            try {
              // 列印內容
              currentPane.print();
            }
            catch (PrinterException ex) {
              ex.printStackTrace();
            }
          }
        }.start();
      } 
      else {
        JOptionPane.showMessageDialog(null, "無資料可列印", "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
    else if (menuitem.getText().equals("Exit")) { // Exit
      JOptionPane joptionpane = new JOptionPane();
      int iResult = joptionpane.showConfirmDialog(null, "Are you sure to quit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
      
      if (iResult == 0) {
        dispose();
        System.exit(0);
      }
    }
    else if (menuitem.getText().equals("Cut")) { // Cut
      // 剪下
      currentPane.cut();
    }
    else if (menuitem.getText().equals("Copy")) { // Copy
      // 複製
      currentPane.copy();
    }
    else if (menuitem.getText().equals("Paste")) { // Paste
      // 貼上
      currentPane.paste();
    } 
    else if (menuitem.getText().equals("New Tab")) { // New Tab
      createTab();
    } 
    else if (menuitem.getText().equals("Wrap")) { // Wrap
      // 設定分頁的排列原則
      jtabbedpane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
      jtabbedpane.revalidate();
      jtabbedpane.repaint();
    } 
    else if (menuitem.getText().equals("Scroll")) { // Scroll
      // 設定分頁的排列原則
      jtabbedpane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
      jtabbedpane.revalidate();
      jtabbedpane.repaint();
    } 
    else if (menuitem.getText().equals("Top")) { // Top
      // 設定分頁的置放位置
      jtabbedpane.setTabPlacement(SwingConstants.TOP);
      jtabbedpane.revalidate();
      jtabbedpane.repaint();
    } 
    else if (menuitem.getText().equals("Bottom")) { // Bottom
      // 設定分頁的置放位置
      jtabbedpane.setTabPlacement(SwingConstants.BOTTOM);
      jtabbedpane.revalidate();
      jtabbedpane.repaint();
    } 
    else if (menuitem.getText().equals("Left")) { // Left
      // 設定分頁的置放位置
      jtabbedpane.setTabPlacement(SwingConstants.LEFT);
      jtabbedpane.revalidate();
      jtabbedpane.repaint();
    } 
    else if (menuitem.getText().equals("Right")) { // Right
      // 設定分頁的置放位置
      jtabbedpane.setTabPlacement(SwingConstants.RIGHT);
      jtabbedpane.revalidate();
      jtabbedpane.repaint();
    } 
    else if (menuitem.getText().equals("About")) { // About
      JOptionPane.showMessageDialog(null, "JTabbedPane Demo", "About", JOptionPane.INFORMATION_MESSAGE);
    }
  }

  private void button_actionPerformed(ActionEvent e) {
    final JTextArea currentPane = (JTextArea) vTextArea.elementAt(jtabbedpane.getSelectedIndex());

    if (e.getSource().equals(jbutton[0])) { // New
      currentPane.setText("");
    }
    else if (e.getSource().equals(jbutton[1])) { // Open
      open();
    }
    else if (e.getSource().equals(jbutton[2])) { // Save
      save();
    }
    else if (e.getSource().equals(jbutton[3])) { // Cut
      // 剪下
      currentPane.cut();
    }
    else if (e.getSource().equals(jbutton[4])) { // Copy
      // 複製
      currentPane.copy();
    }
    else if (e.getSource().equals(jbutton[5])) { // Paste
      // 貼上
      currentPane.paste();
    }
    else if (e.getSource().equals(jbutton[6])) { // About
      JOptionPane.showMessageDialog(null, "JTabbedPane Demo", "About", JOptionPane.INFORMATION_MESSAGE);
    } 
  }

  private void open() {
    final JTextArea currentPane = (JTextArea) vTextArea.elementAt(jtabbedpane.getSelectedIndex());

    JFileChooser jfilechooser = new JFileChooser();
    
    // 設定檔案對話盒的標題
    jfilechooser.setDialogTitle("開啟檔案");
    // 設定檔案對話盒的目前目錄
    jfilechooser.setCurrentDirectory(new File("."));
    // 設定檔案對話盒的型式
    jfilechooser.setDialogType(JFileChooser.OPEN_DIALOG);
    
    if (jfilechooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) 
      return;
      
    String filename = jfilechooser.getSelectedFile().toString();
    
    try {
      // 建立檔案輸入串流
      FileReader in = new FileReader(filename);
      
      // 自檔案串流中讀取資料至文字區域中
      currentPane.read(in, filename);
      
      // 設定分頁標題
      jtabbedpane.setTitleAt(jtabbedpane.getSelectedIndex(), jfilechooser.getSelectedFile().getName());
      jtabbedpane.revalidate();
      jtabbedpane.repaint();
    }  
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void save() {
    final JTextArea currentPane = (JTextArea) vTextArea.elementAt(jtabbedpane.getSelectedIndex());

    JFileChooser jfilechooser = new JFileChooser();
    
    // 設定檔案對話盒的標題
    jfilechooser.setDialogTitle("儲存檔案");
    // 設定檔案對話盒的目前目錄
    jfilechooser.setCurrentDirectory(new File("."));
    // 設定檔案對話盒的型式
    jfilechooser.setDialogType(JFileChooser.SAVE_DIALOG);
    
    if (jfilechooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) 
      return;
      
    File filename = jfilechooser.getSelectedFile();
    
    try {
      // 建立檔案輸出串流
      FileWriter out = new FileWriter(filename);
    
      // 將文字區域的資料寫入至檔案輸出串流中
      currentPane.write(out);

      // 設定分頁標題
      jtabbedpane.setTitleAt(jtabbedpane.getSelectedIndex(), filename.getName());
      jtabbedpane.revalidate();
      jtabbedpane.repaint();

      JOptionPane.showMessageDialog(null, "資料已寫入至檔案 " + filename, "About", JOptionPane.INFORMATION_MESSAGE);
    }  
    catch (IOException e) {
      e.printStackTrace();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
