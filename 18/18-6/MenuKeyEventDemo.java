import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

import javax.swing.event.*;

public class MenuKeyEventDemo extends javax.swing.JFrame implements MenuKeyListener {

  String menulabel[]={"File|F", "Edit|E", "Help|H"};

  String menuitemlabel[][]={
    {"New|N|new.gif", "Open|O|open.gif", "-", "Save|S|save.gif", "Save As...|A", "-", "Sub Menu", "-", "Exit|X"},
    {"Cut|T|cut.gif", "Copy|C|copy.gif", "Paste|P|paste.gif"},
    {"Index|I", "Use Help|U", "-", "About|A|about.gif"}
  };

  String submenuitemlabel[]={"Basic", "Intermediate", "Advance"};

  JMenu jmenu[];
  JMenuItem jmenuitem[][] = new JMenuItem[3][9];
  JMenuItem submenuitem[] = new JMenuItem[3];

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

    new MenuKeyEventDemo();
  }

  // 建構函式
  public MenuKeyEventDemo() {
    super("JMenuItem Event Demo");

    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 設定視窗圖示
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // 建立選單列
    JMenuBar jmenubar = createMenuBar();
    
    // 定義視窗使用者介面之選單列
    this.setJMenuBar(jmenubar);

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

    for(int i=0; i<menulabel.length; i++){
      for(int j=0; j<menuitemlabel[i].length; j++){
        if (i==0 && j==6) { // 子選單
          // 建立子選單
          JMenu submenu = new JMenu(menuitemlabel[i][j]);

          // 建立選單項目
          for (int k=0; k<submenuitemlabel.length; k++){
            submenuitem[k] = new JMenuItem(submenuitemlabel[k]);

            // 註冊 MenuKeyListener
            submenuitem[k].addMenuKeyListener(this);
      
            // 加入選單項目至子選單
            submenu.add(submenuitem[k]);
          }
      
          jmenu[i].add(submenu);
        }
        else if (menuitemlabel[i][j].equals("-")) {
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

          // 註冊 MenuKeyListener
          jmenuitem[i][j].addMenuKeyListener(this);

          // 加入選單項目
          jmenu[i].add(jmenuitem[i][j]);
        }
      }
    }
    
    return jmenubar;
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
}
