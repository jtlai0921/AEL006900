import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.io.*;

public class JFileChooserDemo extends JFrame {
  
  String menulabel[]={"File|F", "Help|H"};

  String menuitemlabel[][]={
    {"Open|O|open.gif", "Save|S|save.gif", "-", "Exit|X"},
    {"About|A|about.gif"}
  };

  JMenu jmenu[];
  JMenuItem jmenuitem[][] = new JMenuItem[2][4];

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

    new JFileChooserDemo();
  }

  // 建構函式
  public JFileChooserDemo() {
    super("JFileChooser Demo");

    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 設定視窗圖示
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // 建立選單列
    JMenuBar jmenubar = createMenuBar();
    
    // 定義視窗使用者介面之選單列
    this.setJMenuBar(jmenubar);

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    this.validate();
    this.setSize(new Dimension(200, 200));
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

    if (menuitem.getText().equals("Open")) { // Open
      open();
    }
    else if (menuitem.getText().equals("Save")) { // Save
      save();
    }
    else if (menuitem.getText().equals("Exit")) { // Exit
      JOptionPane joptionpane = new JOptionPane();
      int iResult = joptionpane.showConfirmDialog(null, "Are you sure to quit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
      
      if (iResult == 0) {
        dispose();
        System.exit(0);
      }
    }
    else if (menuitem.getText().equals("About")) { // About
      JOptionPane.showMessageDialog(null, "JFileChooser Demo", "About", JOptionPane.INFORMATION_MESSAGE);
    }
  }

  private void open() {
    JFileChooser jfilechooser = new JFileChooser();
    
    // 設定檔案對話盒的標題
    jfilechooser.setDialogTitle("開啟檔案");
    // 設定檔案對話盒的目前目錄
    jfilechooser.setCurrentDirectory(new File("."));
    // 設定檔案對話盒的型式
    jfilechooser.setDialogType(JFileChooser.OPEN_DIALOG);
    // 是否允許多重選擇檔案
    jfilechooser.setMultiSelectionEnabled(false);

    // 處理篩選條件（Filter）
    CustomFileFilter[] filter = new CustomFileFilter[4];
    
    filter[0] = new CustomFileFilter("JPEG Files", "jpg");
    filter[1] = new CustomFileFilter("GIF Files" , "gif");
    filter[2] = new CustomFileFilter("TIFF Files", "tif");
    filter[3] = new CustomFileFilter("PNG Files" , "png");

    for (int i=0; i < filter.length; i++) 
      // 將篩選條件加入至Linked-list之中
      jfilechooser.addChoosableFileFilter(filter[i]);

    // 設定目前的篩選條件
    jfilechooser.setFileFilter(filter[0]);
    
    if (jfilechooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) 
      return;
      
    System.out.println("開啟檔案對話盒回傳值: " + jfilechooser.getSelectedFile());    
  }

  private void save() {
    JFileChooser jfilechooser = new JFileChooser();
    
    // 設定檔案對話盒的標題
    jfilechooser.setDialogTitle("儲存檔案");
    // 設定檔案對話盒的目前目錄
    jfilechooser.setCurrentDirectory(new File("."));
    // 設定檔案對話盒的型式
    jfilechooser.setDialogType(JFileChooser.SAVE_DIALOG);
    // 是否允許多重選擇檔案
    jfilechooser.setMultiSelectionEnabled(false);
    
    // 處理篩選條件（Filter）
    CustomFileFilter[] filter = new CustomFileFilter[4];
    
    filter[0] = new CustomFileFilter("JPEG Files", "jpg");
    filter[1] = new CustomFileFilter("GIF Files" , "gif");
    filter[2] = new CustomFileFilter("TIFF Files", "tif");
    filter[3] = new CustomFileFilter("PNG Files" , "png");

    for (int i=0; i < filter.length; i++) 
      // 將篩選條件加入至Linked-list之中
      jfilechooser.addChoosableFileFilter(filter[i]);

    // 設定目前的篩選條件
    jfilechooser.setFileFilter(filter[0]);

    if (jfilechooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) 
      return;
      
    System.out.println("儲存檔案對話盒回傳值: " + jfilechooser.getSelectedFile());
  }
}

