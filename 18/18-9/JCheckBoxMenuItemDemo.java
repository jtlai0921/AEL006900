import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.border.*;

public class JCheckBoxMenuItemDemo extends javax.swing.JFrame implements ActionListener, ItemListener {

  String menulabel[]={"File|F", "Edit|E", "Options|O", "Help|H"};

  String menuitemlabel[][]={
    {"New|N|new.gif", "Open|O|open.gif", "-", "Save|S|save.gif", "Save As...|A", "-", "Exit|X"},
    {"Cut|T|cut.gif", "Copy|C|copy.gif", "Paste|P|paste.gif"},
    {"Show Tooltips|T", "Show Images|I", "-", "Plain Text|P", "HTML|H", "RTF|R"},
    {"Index|I", "Use Help|U", "-", "About|A|about.gif"}
  };

  JMenu jmenu[];
  JMenuItem jmenuitem[][] = new JMenuItem[4][7];
  JCheckBoxMenuItem jcbmenuitem[] = new JCheckBoxMenuItem[6];

  JLabel jlabel;

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

    new JCheckBoxMenuItemDemo();
  }

  // 建構函式
  public JCheckBoxMenuItemDemo() {
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
          if (i != 2) {
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
            if (j==3) 
              jcbmenuitem[j].setState(true);
              
            if (j>=3) 
              group1.add(jcbmenuitem[j]);
  
//            // 註冊 ActionListener
//            jcbmenuitem[j].addActionListener(this);
//  
            // 註冊 ItemListener
            jcbmenuitem[j].addItemListener(this);
  
            // 加入選單項目
            jmenu[i].add(jcbmenuitem[j]);
          }
        }
      }
    }
    
    return jmenubar;
  }

  // 實作ActionListener介面之方法
  public void actionPerformed(ActionEvent e) {
    // 取得產生動作事件的選單項目
    JMenuItem jmenuitem = (JMenuItem)e.getSource();

    if (jmenuitem.getText().equals("Exit")) { // Exit
      System.exit(0);
    }
    else {
      jlabel.setText("Select "  + jmenuitem.getText() + " Menu Item.");
    }
  }

  public void itemStateChanged(ItemEvent e) {
    // 取得產生項目事件的選單項目
    JCheckBoxMenuItem jcbmenuitem = (JCheckBoxMenuItem)e.getSource();

    // 判斷核取方塊是否被選取
    if(jcbmenuitem.getState()) 
//    if(jcbmenuitem.isSelected()) 
      jlabel.setText("Select "  + jcbmenuitem.getText() + " Menu Item.");
    else
      jlabel.setText("Deselect "  + jcbmenuitem.getText() + " Menu Item.");
  }
}
