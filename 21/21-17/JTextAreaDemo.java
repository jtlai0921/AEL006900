import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.event.*;
// Java Swing Text Component
import javax.swing.text.*;

import java.io.*;
import java.text.*;
import java.util.*;

// Print Job
import java.awt.print.*;  

public class JTextAreaDemo extends JFrame {
    
  JToolBar jtoolbar = new JToolBar();
  JButton jbutton[] = new JButton[7];
  JTextArea jtextarea = new JTextArea();
  JPopupMenu jpopupmenu = new JPopupMenu();

  String menulabel[]={"File|F", "Edit|E", "Help|H"};

  String menuitemlabel[][]={
    {"New|N|new.gif", "Open|O|open.gif", "-", "Save|S|save.gif", "-", "Print|P|print.gif", "-", "Exit|X"},
    {"Cut|T|cut.gif", "Copy|C|copy.gif", "Paste|P|paste.gif"},
    {"About|A|about.gif"}
  };

  String item[]  = {"New", "Open", "Save", "Cut", "Copy", "Paste", "About"};
  String image[] = {"new", "open", "save", "cut", "copy", "paste", "about"};

  JMenu jmenu[];
  JMenuItem jmenuitem[][] = new JMenuItem[3][8];

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

    new JTextAreaDemo();
  }

  // 建構函式
  public JTextAreaDemo() {
    super("JTextArea Demo");
  
    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 設定視窗圖示
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // 定義Layout Manager 為 BorderLayout
    this.setLayout(new BorderLayout());

    // 建立選單列
    JMenuBar jmenubar = createMenuBar();
    
    // 定義視窗使用者介面之選單列
    this.setJMenuBar(jmenubar);
    
    // Cut
    JMenuItem jmnuCut1 = new JMenuItem();
    action = new DefaultEditorKit.CutAction();
    action.putValue(Action.NAME, "Cut");
    // 設定選單項目的動作
    jmnuCut1.setAction(action);
    jmnuCut1.setIcon(new ImageIcon(cl.getResource("images/" + image[3] + ".gif")));
    jmnuCut1.setText(item[3]);
    jmnuCut1.setMnemonic('t');
    jmnuCut1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));

    // Copy
    JMenuItem jmnuCopy1 = new JMenuItem();
    action = new DefaultEditorKit.CopyAction();
    action.putValue(Action.NAME, "Copy");
    // 設定選單項目的動作
    jmnuCopy1.setAction(action);
    jmnuCopy1.setIcon(new ImageIcon(cl.getResource("images/" + image[4] + ".gif")));
    jmnuCopy1.setText(item[4]);
    jmnuCopy1.setMnemonic('C');
    jmnuCopy1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));

    // Paste
    JMenuItem jmnuPaste1 = new JMenuItem();
    action = new DefaultEditorKit.PasteAction();
    action.putValue(Action.NAME, "Paste");
    // 設定選單項目的動作
    jmnuPaste1.setAction(action);
    jmnuPaste1.setIcon(new ImageIcon(cl.getResource("images/" + image[5] + ".gif")));
    jmnuPaste1.setText(item[5]);
    jmnuPaste1.setMnemonic('P');
    jmnuPaste1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
    
    // 建立突顯式選單
    jpopupmenu.add(jmnuCut1);
    jpopupmenu.add(jmnuCopy1);
    jpopupmenu.add(jmnuPaste1);

    for (int i=0; i<image.length; i++) {
      jbutton[i] = new JButton();

      if (i==0 || i==1 || i==2 || i==6) {      
        // 註冊 ActionListener
        jbutton[i].addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            button_actionPerformed(e);
          }
        });
      }

      switch (i) {
        case 3: // Cut
          action = new DefaultEditorKit.CutAction();
          action.putValue(Action.NAME, "Cut");
          // 設定按鈕的動作
          jbutton[3].setAction(action);
          break;
        case 4: // Copy
          action = new DefaultEditorKit.CopyAction();
          action.putValue(Action.NAME, "Copy");
          // 設定按鈕的動作
          jbutton[4].setAction(action);
          break;
        case 5: // Paste
          action = new DefaultEditorKit.PasteAction();
          action.putValue(Action.NAME, "Paste");
          // 設定按鈕的動作
          jbutton[5].setAction(action);
          break;
        default:
      }

      jbutton[i].setIcon(new ImageIcon(cl.getResource("images/" + image[i] + ".gif")));
      jbutton[i].setText("");
      jbutton[i].setPreferredSize(new Dimension(32, 32));
      jbutton[i].setToolTipText(item[i]);

      jtoolbar.add(jbutton[i]);

      if (i==2 || i==5)
        // 加入分隔線
        jtoolbar.addSeparator();
    }  
     
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

    // 設定捲軸面板
    JScrollPane jscrollpane = new JScrollPane(jtextarea);
    // 垂直捲軸：當超過JTextArea列數時才自動顯示垂直捲軸
    jscrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    // 水平捲軸：當超過JTextArea行數時才自動顯示水平捲軸
    jscrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    this.add(jtoolbar, BorderLayout.NORTH);
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
          if (i==0 || i==2) {
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
          else if (i==1) { // Edit Menu
            switch (j) { // 設定JButton的Action
              case 0: // Cut
                jmenuitem[i][j] = new JMenuItem();
                action = new DefaultEditorKit.CutAction();
                action.putValue(Action.NAME, "Cut");
                // 設定選單項目的動作
                jmenuitem[i][j].setAction(action);
                jmenuitem[i][j].setIcon(new ImageIcon(cl.getResource("images/" + image[j] + ".gif")));
                jmenuitem[i][j].setText(item[j]);
                jmenuitem[i][j].setMnemonic('t');
                jmenuitem[i][j].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
                break;
              case 1: // Copy
                jmenuitem[i][j] = new JMenuItem();
                action = new DefaultEditorKit.CopyAction();
                action.putValue(Action.NAME, "Copy");
                // 設定選單項目的動作
                jmenuitem[i][j].setAction(action);
                jmenuitem[i][j].setIcon(new ImageIcon(cl.getResource("images/" + image[j] + ".gif")));
                jmenuitem[i][j].setText(item[j]);
                jmenuitem[i][j].setMnemonic('C');
                jmenuitem[i][j].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
                break;
              case 2: // Paste
                jmenuitem[i][j] = new JMenuItem();
                action = new DefaultEditorKit.PasteAction();
                action.putValue(Action.NAME, "Paste");
                // 設定選單項目的動作
                jmenuitem[i][j].setAction(action);
                jmenuitem[i][j].setIcon(new ImageIcon(cl.getResource("images/" + image[j] + ".gif")));
                jmenuitem[i][j].setText(item[j]);
                jmenuitem[i][j].setMnemonic('P');
                jmenuitem[i][j].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
                break;
              default:
            }

            // 加入選單項目
            jmenu[i].add(jmenuitem[i][j]);
          }
        }
      }
    }
    
    return jmenubar;
  }

  private void showPopmenu(MouseEvent e) {
    // 當按下滑鼠按鍵時
    if (e.getButton() == MouseEvent.BUTTON2 || e.getButton() == MouseEvent.BUTTON3) 
      // 顯示突顯式選單
      jpopupmenu.show(e.getComponent(), e.getX(), e.getY());
  }

  private void menu_actionPerformed(ActionEvent e) {
    // 取得產生動作事件的選單項目
    JMenuItem menuitem = (JMenuItem)e.getSource();

    if (menuitem.getText().equals("New")) { // New
      jtextarea.setText("");
    }
    else if (menuitem.getText().equals("Open")) { // Open
      open();
    }
    else if (menuitem.getText().equals("Save")) { // Save
      save();
    }
    else if (menuitem.getText().equals("Print")) { // Print
      if (! jtextarea.getText().equals("")) {
        new Thread() {
          public void run() {
            try {
              // 列印內容
              jtextarea.print();
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
    else if (menuitem.getText().equals("About")) { // About
      JOptionPane.showMessageDialog(null, "JTextArea Demo", "About", JOptionPane.INFORMATION_MESSAGE);
    }
  }

  private void button_actionPerformed(ActionEvent e) {
    if (e.getSource().equals(jbutton[0])) { // New
      jtextarea.setText("");
    }
    else if (e.getSource().equals(jbutton[1])) { // Open
      open();
    }
    else if (e.getSource().equals(jbutton[2])) { // Save
      save();
    }
    else if (e.getSource().equals(jbutton[6])) { // About
      JOptionPane.showMessageDialog(null, "JTextArea Demo", "About", JOptionPane.INFORMATION_MESSAGE);
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
    
    if (jfilechooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) 
      return;
      
    String filename = jfilechooser.getSelectedFile().toString();
    
    try {
      // 建立檔案輸入串流
      FileReader in = new FileReader(filename);
      
      // 自檔案串流中讀取資料至文字區域中
      jtextarea.read(in, filename);
    }  
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void save() {
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
      jtextarea.write(out);

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
