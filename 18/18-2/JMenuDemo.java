import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.border.*;

public class JMenuDemo extends javax.swing.JFrame { 

  String menulabel[]={"File|F", "Edit|E", "Help|H"};

  String menuitemlabel[][]={
    {"New|new.gif", "Open|open.gif", "-", "Save|save.gif", "Save As...", "-", "Sub Menu", "-", "Exit"},
    {"Cut|cut.gif", "Copy|copy.gif", "Paste|paste.gif"},
    {"Index", "Use Help", "-", "About|about.gif"}
  };

  String submenuitemlabel[]={"Basic", "Intermediate", "Advance"};

  JMenu jmenu[];
  JMenuItem jmenuitem[][] = new JMenuItem[3][9];
  JMenuItem submenuitem[] = new JMenuItem[3];

  static JLabel jlabel;

  Action action = null;

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

    new JMenuDemo();
  }

  // 建構函式
  public JMenuDemo() {
    super("JMenu Demo");

    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 設定視窗圖示
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // 建立選單列
    JMenuBar jmenubar = createMenuBar();
    
    // 定義視窗使用者介面之選單列
    this.setJMenuBar(jmenubar);

    jlabel = new JLabel(" ");

    JPanel jpanel = new JPanel();
    // 設定物件之邊框樣式
    jpanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
    jpanel.setLayout(new BorderLayout());
    jpanel.add(jlabel, BorderLayout.WEST);
    
    // 定義 Layout Manager 為 BorderLayout
    this.setLayout(new BorderLayout());
    this.add(jpanel, BorderLayout.SOUTH);
    
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

            action = new MenuItemAction(submenuitemlabel[k]);

            // 設定選單項目的動作
            submenuitem[k].setAction(action);

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
          String title = "";
          
          // 建立選單項目
          if (menuitemlabel[i][j].indexOf("|") != -1)
            title = menuitemlabel[i][j].substring(0, menuitemlabel[i][j].indexOf("|"));
          else
            title = menuitemlabel[i][j];

          if (menuitemlabel[i][j].endsWith(".gif")) {
            action = new MenuItemAction(title, new ImageIcon(cl.getResource("images/" + menuitemlabel[i][j].substring(menuitemlabel[i][j].lastIndexOf("|")+1))));
          }
          else {
            action = new MenuItemAction(title);
          }

          jmenuitem[i][j] = new JMenuItem();

          // 設定選單項目的動作
          jmenuitem[i][j].setAction(action);

          // 加入選單項目
          jmenu[i].add(jmenuitem[i][j]);
        }
      }
    }
    
    return jmenubar;
  }

  class MenuItemAction extends AbstractAction {
    public MenuItemAction(String text) {
      super(text);
    }
  
    public MenuItemAction(String text, ImageIcon icon) {
      super(text, icon);
    }
  
    public void actionPerformed(ActionEvent e) {
      // 取得產生動作事件的選單項目
      JMenuItem jmenuitem = (JMenuItem)e.getSource();
  
      if (jmenuitem.getText().equals("Exit")) { // Exit
        System.exit(0);
      }
      else {
        JMenuDemo.jlabel.setText("Select "  + jmenuitem.getText() + " Menu Item.");
      }
    }
  }  
}
