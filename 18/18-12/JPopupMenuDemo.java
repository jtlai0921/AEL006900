import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.border.*;

import javax.swing.event.*;

public class JPopupMenuDemo extends javax.swing.JFrame implements ActionListener, MenuKeyListener, PopupMenuListener {

  JPopupMenu jpopupmenu;

  String menulabel[]={"File|F", "Edit|E", "L & F|L", "Help|H"};

  String menuitemlabel[][]={
    {"Exit|X"},
    {"Cut|T|cut.gif", "Copy|C|copy.gif", "Paste|P|paste.gif"},
    {"Metal|M", "CDE/Motif|C", "Windows XP|W", "Windows Classic|D", "GTK+|G", "Mac|A"},
    {"Index|I", "Use Help|U", "-", "About|A|about.gif"}
  };

  JMenu jmenu[];
  JMenuItem jmenuitem[][] = new JMenuItem[4][6];
  JRadioButtonMenuItem jrbmenuitem[] = new JRadioButtonMenuItem[6];

  JLabel jlabel;

  String classname[] = {
    "javax.swing.plaf.metal.MetalLookAndFeel", 
    "com.sun.java.swing.plaf.motif.MotifLookAndFeel", 
    "com.sun.java.swing.plaf.windows.WindowsLookAndFeel", 
    "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel", 
    "com.sun.java.swing.plaf.gtk.GTKLookAndFeel",
    "com.sun.java.swing.plaf.mac.MacLookAndFeel"};

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

    new JPopupMenuDemo();
  }

  // 建構函式
  public JPopupMenuDemo() {
    super("JPopupMenu Demo");

    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 設定視窗圖示
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // 建立選單列
    JMenuBar jmenubar = createMenuBar();
    
    // 定義視窗使用者介面之選單列
    this.setJMenuBar(jmenubar);

    // 建立突顯式選單
    jpopupmenu = createJPopupMenu("Popup Menu"); 
    
    // 註冊 MenuKeyListener
    jpopupmenu.addMenuKeyListener(this);
    // 註冊 PopupMenuListener
    jpopupmenu.addPopupMenuListener(this);

    jlabel = new JLabel(" ");

    JPanel jpanel = new JPanel();
    // 設定物件之邊框樣式
    jpanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
    jpanel.setLayout(new BorderLayout());
    jpanel.add(jlabel, BorderLayout.WEST);

    JEditorPane txtMessage = new JEditorPane();
    // 註冊 MouseListener
    // 以處理當按下滑鼠按鍵時顯示突顯式選單
    txtMessage.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        showJPopupMenu(e);
      }
    
      public void mousePressed(MouseEvent e) {
        showJPopupMenu(e);
      }
    
      public void mouseReleased(MouseEvent e) {
        showJPopupMenu(e);
      }
    });

    JScrollPane jsp = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    jsp.getViewport().add(txtMessage);

    // 定義 Layout Manager 為 BorderLayout
    this.setLayout(new BorderLayout());
    this.add(jpanel, BorderLayout.SOUTH);
    this.add(jsp, BorderLayout.CENTER);
   
    // 設定視窗的大小
    this.setSize(250, 250);

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();

    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;

    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;

    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    // 顯示視窗
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

    // 建構群組
    ButtonGroup group = new ButtonGroup();

    for(int i=0; i<menulabel.length; i++){
      for(int j=0; j<menuitemlabel[i].length; j++){
        if (menuitemlabel[i][j].equals("-")) {
          // 加入分隔線
          jmenu[i].addSeparator();
          // 或
          // jmenu[i].add(new JSeparator());
        }
        else if (i == 2) { // L & F 選單
          if (menuitemlabel[i][j].equals("-")) 
            // 加入分隔線
            jmenu[i].addSeparator();
          // 建立選項按鈕選單項目
          else if (menuitemlabel[i][j].indexOf("|") != -1)
            jrbmenuitem[j] = new JRadioButtonMenuItem(menuitemlabel[i][j].substring(0, menuitemlabel[i][j].indexOf("|")));
          else
            jrbmenuitem[j] = new JRadioButtonMenuItem(menuitemlabel[i][j]);
  
          // 設定選單項目助記碼
          jrbmenuitem[j].setMnemonic(menuitemlabel[i][j].split("\\|")[1].charAt(0));
  
          // 建立圖像
          if (menuitemlabel[i][j].endsWith(".gif")) 
            jrbmenuitem[j].setIcon(new ImageIcon(cl.getResource("images/" + menuitemlabel[i][j].substring(menuitemlabel[i][j].lastIndexOf("|")+1))));

          // 設定選項按鈕選單項目之選取狀態
          if (j==0) 
            jrbmenuitem[j].setSelected(true);

          // 設定是否啟用選單項目
          jrbmenuitem[j].setEnabled(isLookAndFeelSupported(classname[j]));
            
          group.add(jrbmenuitem[j]);

          // 註冊 ActionListener
          jrbmenuitem[j].addActionListener(this);

          // 加入選單項目
          jmenu[i].add(jrbmenuitem[j]);
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
          jmenuitem[i][j].addActionListener(this);

          // 加入選單項目
          jmenu[i].add(jmenuitem[i][j]);
        }
      }
    }
    
    return jmenubar;
  }

  private JPopupMenu createJPopupMenu(String label) {
    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 建立突顯式選單
    JPopupMenu popupmenu = new JPopupMenu(label);

    // Cut, Copy, Paste
    for(int i=1, j=0; j<menuitemlabel[i].length; j++){
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
      jmenuitem[i][j].addActionListener(this);

      // 加入選單項目
      popupmenu.add(jmenuitem[i][j]);
    }

    return popupmenu;
  }
  
  private boolean isLookAndFeelSupported(String lnfname) {
    try { 
      Class lnfclass = Class.forName(lnfname);
      javax.swing.LookAndFeel lnf = (LookAndFeel)(lnfclass.newInstance());
      
      // 判斷作業系統是否支援Look and Feel
      return lnf.isSupportedLookAndFeel();
    } 
    catch(Exception e) { 
      return false;
    }
  }

  private void showJPopupMenu(MouseEvent e) {
    // 判斷滑鼠事件是否與突顯式選單有關
    if (jpopupmenu.isPopupTrigger(e)) {
      // 或
//    if (e.isPopupTrigger()) {
      // 顯示突顯式選單
      jpopupmenu.show(e.getComponent(), e.getX(), e.getY());

      // 或依序執行以下方法
//      jpopupmenu.setLocation(e.getX(), e.getY());
//      jpopupmenu.setInvoker(e.getComponent());
//      jpopupmenu.setVisible(true);
    }
  }

  // 實作ActionListener介面之方法
  public void actionPerformed(ActionEvent e) {
    // 取得產生動作事件的選單項目
    JMenuItem jmenuitem = (JMenuItem)e.getSource();

    if (jmenuitem.getText().equals("Metal")) { 
      jlabel.setText("Select "  + jmenuitem.getText() + " Menu Item.");

      try {
        // 設定Look and Feel
        UIManager.setLookAndFeel(classname[0]);

        // 執行階段變更Look and Feel
        SwingUtilities.updateComponentTreeUI(this);
      }
      catch(Exception ex) {}
    }
    else if (jmenuitem.getText().equals("CDE/Motif")) { 
      jlabel.setText("Select "  + jmenuitem.getText() + " Menu Item.");

      try {
        // 設定Look and Feel
        UIManager.setLookAndFeel(classname[1]);

        // 執行階段變更Look and Feel
        SwingUtilities.updateComponentTreeUI(this);
      }
      catch(Exception ex) {}
    }
    else if (jmenuitem.getText().equals("Windows XP")) { 
      jlabel.setText("Select "  + jmenuitem.getText() + " Menu Item.");

      try {
        // 設定Look and Feel
        UIManager.setLookAndFeel(classname[2]);

        // 執行階段變更Look and Feel
        SwingUtilities.updateComponentTreeUI(this);
      }
      catch(Exception ex) {}
    }
    else if (jmenuitem.getText().equals("Windows Classic")) { 
      jlabel.setText("Select "  + jmenuitem.getText() + " Menu Item.");

      try {
        // 設定Look and Feel
        UIManager.setLookAndFeel(classname[3]);

        // 執行階段變更Look and Feel
        SwingUtilities.updateComponentTreeUI(this);
      }
      catch(Exception ex) {}
    }
    else if (jmenuitem.getText().equals("GTK+")) { 
      jlabel.setText("Select "  + jmenuitem.getText() + " Menu Item.");

      try {
        // 設定Look and Feel
        UIManager.setLookAndFeel(classname[4]);

        // 執行階段變更Look and Feel
        SwingUtilities.updateComponentTreeUI(this);
      }
      catch(Exception ex) {}
    }
    else if (jmenuitem.getText().equals("Mac")) { 
      jlabel.setText("Select "  + jmenuitem.getText() + " Menu Item.");

      try {
        // 設定Look and Feel
        UIManager.setLookAndFeel(classname[5]);

        // 執行階段變更Look and Feel
        SwingUtilities.updateComponentTreeUI(this);
      }
      catch(Exception ex) {}
    }
    else if (jmenuitem.getText().equals("Exit")) { // Exit
      System.exit(0);
    }
    else {
      jlabel.setText("Select "  + jmenuitem.getText() + " Menu Item.");
    }
  }

  // 實作MenuKeyListener介面之方法
  // 當按下鍵盤按鍵時所呼叫的方法
  public void menuKeyPressed(MenuKeyEvent e) {
    // 判斷是否按下Alt鍵
    if (e.isAltDown()) 
      System.out.println("Press Alt key") ;
    
    // 判斷是否按下Ctrl鍵
    if (e.isControlDown()) 
      System.out.println("Press Control key") ;
    
    // 判斷是否按下Shift鍵
    if (e.isShiftDown()) 
      System.out.println("Press Shift key") ;
    
    // 回傳按鍵所代表的按鍵值
    System.out.println("Key Pressed: " + e.getKeyCode());
 
    // 取得產生此事件之選單項目的階層路徑
    MenuElement[] element = e.getPath();
    
    for (int i=0; i<element.length; i++) 
      System.out.println("Menu Item: " + element[i].getComponent()) ;
  }  

  // 當釋放鍵盤按鍵時所呼叫的方法
  public void menuKeyReleased(MenuKeyEvent e) {}
  // 當按下並釋放鍵盤按鍵時所呼叫的方法
  public void menuKeyTyped(MenuKeyEvent e) {} 
  
  // 實作PopupMenuListener介面之方法
  // 當取消突顯式選單時所呼叫的方法
  public void popupMenuCanceled(PopupMenuEvent e) {
    jlabel.setText("JPopupMenu is canceled.");
  }

  // 當隱藏突顯式選單之前所呼叫的方法
  public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
    jlabel.setText("JPopupMenu will be invisible.");
  }

  // 當顯示突顯式選單之前所呼叫的方法
  public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
    jlabel.setText("JPopupMenu will be visible.");
  }
}
