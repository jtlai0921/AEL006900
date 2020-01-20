import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.event.*;
// Undo and Redo
import javax.swing.undo.*;
// Java Swing Text Component
import javax.swing.text.*;

import java.io.*;
import java.text.*;
import java.util.*;

// Print Job
import java.awt.print.*;  

// 實作CaretListener, DocumentListener與UndoableEditListener介面
public class JTextPaneDemo extends JFrame implements CaretListener, DocumentListener, UndoableEditListener {
    
  JToolBar jtoolbar = new JToolBar();
  JButton jbutton[] = new JButton[13];
  JTextPane jtextpane = new JTextPane();
  JPopupMenu jpopupmenu1 = new JPopupMenu();
  JPopupMenu jpopupmenu2 = new JPopupMenu();
  JPopupMenu jpopupmenu3 = new JPopupMenu();
  JLabel jlabel = new JLabel();

  JComboBox cboFont = new JComboBox();
  JComboBox cboSize = new JComboBox();

  // 文件樣式
  javax.swing.text.StyledDocument document;

  javax.swing.undo.UndoManager undoManager = new UndoManager();

  String menulabel[]={"File|F", "Edit|E", "Style|S", "Help|H"};

  String menuitemlabel[][]={
    {"New|N|new.gif", "Open|O|open.gif", "-", "Save|S|save.gif", "-", "Print|P|print.gif", "-", "Exit|X"},
    {"Undo|U|undo.gif", "Redo|R|redo.gif", "-", "Cut|T|cut.gif", "Copy|C|copy.gif", "Paste|P|paste.gif"},
    {"Font|F", "Size|S"},
    {"About|A|about.gif"}
  };

  String item[]  = {"New", "Open", "Save", "Cut", "Copy", "Paste", "Bold", "Italic", "Underline", "Justify Left", "Justify Center", "Justify Right", "About"};
  String image[] = {"new", "open", "save", "cut", "copy", "paste", "bold", "italic", "underline", "justifyleft", "justifycenter", "justifyright", "about"};

  String font[] = {"Arial", "Courier New", "Dialog", "SansSerif", "Serif", "Tahoma", "Times New Roman", "Verdana"};
  int size[] = {8, 9, 10, 11, 12, 14, 16, 18, 20};

  JMenu jmenu[];
  JMenuItem jmenuitem[][] = new JMenuItem[4][8];
  JMenuItem submenu[] = new JMenuItem[2];
  JMenuItem submenuitem[][] = new JMenuItem[2][9];

  Action action = null;

  UndoAction undoAction;
  RedoAction redoAction;

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

    new JTextPaneDemo();
  }

  // 建構函式
  public JTextPaneDemo() {
    super("JTextPane Demo");
  
    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 設定視窗圖示
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // 設定文字面板邊框之屬性值
    UIManager.put("TextPane.border", new javax.swing.border.EtchedBorder(EtchedBorder.LOWERED));
    
    // 設定脫字符號的前景顏色之屬性值
    UIManager.put("TextPane.caretForeground", Color.RED);
    
    // 設定文字標籤字型之屬性值
    UIManager.put("TextPane.font", new Font("dialog", Font.BOLD, 12));
    
    // 設定文字面板前景之屬性值
    UIManager.put("TextPane.foreground", Color.PINK);
    
    // 設定被選取時的背景顏色之屬性值
    UIManager.put("TextPane.selectionBackground", Color.GREEN);
    
    // 設定被選取時的前景顏色之屬性值
    UIManager.put("TextPane.selectionForeground", Color.CYAN);
    
    // 取得文字面板Pluggable Look and Feel屬性值
    System.out.println("JTextPane Look and Feel: " + UIManager.getString("TextPaneUI"));

    // 定義Layout Manager 為 BorderLayout
    this.setLayout(new BorderLayout());

    // 建立選單功能列
    JMenuBar jmenubar = createMenuBar();
    
    // 定義視窗使用者介面之選單功能列
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
    jpopupmenu1.add(jmnuCut1);
    jpopupmenu1.add(jmnuCopy1);
    jpopupmenu1.add(jmnuPaste1);

    for (int i=0; i<image.length; i++) {
      jbutton[i] = new JButton();

      if (i==0 || i==1 || i==2 || i==12) {      
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
        case 6: // Bold
          action = new StyledEditorKit.BoldAction();
          action.putValue(Action.NAME, "Bold");
          // 設定按鈕的動作
          jbutton[6].setAction(action);
          break;
        case 7: // Italic
          action = new StyledEditorKit.ItalicAction();
          action.putValue(Action.NAME, "Italic");
          // 設定按鈕的動作
          jbutton[7].setAction(action);
          break;
        case 8: // Underline
          action = new StyledEditorKit.UnderlineAction();
          action.putValue(Action.NAME, "Underline");
          // 設定按鈕的動作
          jbutton[8].setAction(action);
          break;
        case 9: // Justify Left
          // 向左對齊
          action = new StyledEditorKit.AlignmentAction("Justify Left", 0);
          action.putValue(Action.NAME, "Justify Left");
          // 設定按鈕的動作
          jbutton[9].setAction(action);
          break;
        case 10: // Justify Left
          // 向水平中央對齊
          action = new StyledEditorKit.AlignmentAction("Justify Center", 4);
          action.putValue(Action.NAME, "Justify Center");
          // 設定按鈕的動作
          jbutton[10].setAction(action);
          break;
        case 11: // Justify Right
          // 向右對齊
          action = new StyledEditorKit.AlignmentAction("Justify Right", 2);
          action.putValue(Action.NAME, "Justify Right");
          // 設定按鈕的動作
          jbutton[11].setAction(action);
          break;
        default:
      }

      jbutton[i].setIcon(new ImageIcon(cl.getResource("images/" + image[i] + ".gif")));
      jbutton[i].setText("");
      jbutton[i].setPreferredSize(new Dimension(32, 32));
      jbutton[i].setToolTipText(item[i]);

      jtoolbar.add(jbutton[i]);

      if (i==2 || i==5 || i==8 || i==11)
        // 加入分隔線
        jtoolbar.addSeparator();
    }

    // 設定是否可編輯
    jtextpane.setEditable(true);
    // 設定脫字符號目前的位置
    jtextpane.setCaretPosition(0);

    jtextpane.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        showPopmenu(e);
      }
    });

    // 註冊CaretListener
    jtextpane.addCaretListener(this);

    // 取得MVC架構中的Model部份(文件樣式)
    document = jtextpane.getStyledDocument();
    // 註冊DocumentListener
    document.addDocumentListener(this);
    // 註冊UndoableEditListener
    document.addUndoableEditListener(this);
    
    // 設定捲軸面板
    JScrollPane jscrollpane = new JScrollPane(jtextpane);
    // 垂直捲軸：當超過jtextpane列數時才自動顯示垂直捲軸
    jscrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    // 水平捲軸：當超過jtextpane行數時才自動顯示水平捲軸
    jscrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    JPanel jpanel = new JPanel(new GridLayout(1, 1));
    jlabel = new JLabel("Status: ");
    jpanel.add(jlabel);

    this.add(jtoolbar, BorderLayout.NORTH);
    this.add(jscrollpane, BorderLayout.CENTER);
    this.add(jpanel, BorderLayout.SOUTH);

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    this.validate();
    this.setSize(new Dimension(500, 300));
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
          if (i==0 || i==3) {
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
            switch (j) { // 設定JMenuItem的Action
              case 0: // Undo
                undoAction = new UndoAction();

                // 建立選單項目
                jmenuitem[i][j] = new JMenuItem();
                // 設定選單項目的動作
                jmenuitem[i][j].setAction(undoAction);
                // 設定選單項目助記碼
                jmenuitem[i][j].setIcon(new ImageIcon(cl.getResource("images/" + menuitemlabel[i][j].substring(menuitemlabel[i][j].lastIndexOf("|")+1))));
                jmenuitem[i][j].setMnemonic(menuitemlabel[i][j].split("\\|")[1].charAt(0));
                break;
              case 1: // Redo
                redoAction = new RedoAction();

                // 建立選單項目
                jmenuitem[i][j] = new JMenuItem();
                // 設定選單項目的動作
                jmenuitem[i][j].setAction(redoAction);
                // 設定選單項目助記碼
                jmenuitem[i][j].setIcon(new ImageIcon(cl.getResource("images/" + menuitemlabel[i][j].substring(menuitemlabel[i][j].lastIndexOf("|")+1))));
                jmenuitem[i][j].setMnemonic(menuitemlabel[i][j].split("\\|")[1].charAt(0));
                break;
              case 3: // Cut
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
              case 4: // Copy
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
              case 5: // Paste
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
          else if (i==2) { // Style Menu
            if (j==0) { // Font
              // 建立子選單
              submenu[j] = new JMenu(menuitemlabel[i][j].substring(0, menuitemlabel[i][j].indexOf("|")));
              // 設定選單項目助記碼
              submenu[j].setMnemonic(menuitemlabel[i][j].split("\\|")[1].charAt(0));

              for (int k=0; k<font.length; k++) {
                action = new StyledEditorKit.FontFamilyAction(font[k], font[k]);
                //action.putValue(Action.NAME, font[i]);
              
                submenuitem[j][k] = new JMenuItem();
                // 設定選單項目的動作
                submenuitem[j][k].setAction(action);
                submenuitem[j][k].setText(font[k]);
                submenu[j].add(submenuitem[j][k]);
              }
              jmenu[i].add(submenu[j]);
            }
            else if (j==1) { // Font Size
              // 建立子選單
              submenu[j] = new JMenu(menuitemlabel[i][j].substring(0, menuitemlabel[i][j].indexOf("|")));
              // 設定選單項目助記碼
              submenu[j].setMnemonic(menuitemlabel[i][j].split("\\|")[1].charAt(0));

              for (int k=0; k<size.length; k++) {
                action = new StyledEditorKit.FontSizeAction(size[k]+"", size[k]);
                //action.putValue(Action.NAME, font[i]);
              
                submenuitem[j][k] = new JMenuItem();
                // 設定選單項目的動作
                submenuitem[j][k].setAction(action);
                submenuitem[j][k].setText(size[k]+"");
                submenu[j].add(submenuitem[j][k]);
              }
              jmenu[i].add(submenu[j]);
            }
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
      jpopupmenu1.show(e.getComponent(), e.getX(), e.getY());
  }

  private void menu_actionPerformed(ActionEvent e) {
    // 取得產生動作事件的選單項目
    JMenuItem menuitem = (JMenuItem)e.getSource();

    if (menuitem.getText().equals("New")) { // New
      jtextpane.setText("");
    }
    else if (menuitem.getText().equals("Open")) { // Open
      open();
    }
    else if (menuitem.getText().equals("Save")) { // Save
      save();
    }
    else if (menuitem.getText().equals("Print")) { // Print
      if (! jtextpane.getText().equals("")) {
        new Thread() {
          public void run() {
            try {
              // 列印內容
              jtextpane.print();
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
      JOptionPane.showMessageDialog(null, "JTextPane Demo", "About", JOptionPane.INFORMATION_MESSAGE);
    }
  }

  private void button_actionPerformed(ActionEvent e) {
    if (e.getSource().equals(jbutton[0])) { // New
      jtextpane.setText("");
    }
    else if (e.getSource().equals(jbutton[1])) { // Open
      open();
    }
    else if (e.getSource().equals(jbutton[2])) { // Save
      save();
    }
    else if (e.getSource().equals(jbutton[12])) { // About
      JOptionPane.showMessageDialog(null, "JTextPane Demo", "About", JOptionPane.INFORMATION_MESSAGE);
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
      jtextpane.read(in, filename);
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
      jtextpane.write(out);

      JOptionPane.showMessageDialog(null, "資料已寫入至檔案 " + filename, "About", JOptionPane.INFORMATION_MESSAGE);
    }  
    catch (IOException e) {
      e.printStackTrace();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  // 
  // 實作CaretListener介面的方法
  // 

  // 當脫字符號的位置改變時
  public void caretUpdate(CaretEvent e) {
    // 取得脫字符號目前的位置
    final int dot = e.getDot();
    // 取得選取文字時脫字符號的最後位置
    // 若無選取文字，則此位置等於getDot()方法所回傳的目前位置
    final int mark = e.getMark();
    
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        if (dot == mark) {  
          // 無選取文字
          jlabel.setText("游標目前位置: " + dot);
        } 
        else if (dot < mark) {
          // 選取文字
          jlabel.setText("選取由 " + dot + " 至 " + mark + " 的內容");
        } 
        else {
          // 選取文字
          jlabel.setText("選取由 " + mark + " 至 " + dot + " 的內容");
        }
      }
    });
  }

  // 
  // 實作DocumentListener介面的方法
  // 

  // 當文字編輯物件的內容或屬性變更時
  public void changedUpdate(DocumentEvent e) {
    updateDocumentStatus(e);
  }

  // 當插入內容至文字編輯物件時
  public void insertUpdate(DocumentEvent e) {
    updateDocumentStatus(e);
  }

  // 當自文字編輯物件移除內容時
  public void removeUpdate(DocumentEvent e) {
    updateDocumentStatus(e);
  }

  private void updateDocumentStatus(DocumentEvent e) {
    // 取得產生文件事件的MVC Model
    Document docModel = e.getDocument();
    // 取得內容變更的長度
    int length = e.getLength();

    // 取得事件類型 (e.getType())
    System.out.println(e.getType().toString() + ": " + length + " 字元, 文字長度: " + docModel.getLength());
  }

  // 
  // 實作UndoableEditListener介面的方法
  // 

  // 當發生復原或重複編輯動作時
  public void undoableEditHappened(UndoableEditEvent e) {
    // 新增一UndoableEdit物件至目前的文字編輯物件
    if (undoManager.addEdit(e.getEdit())) {
      undoAction.updateUndoStatus();
      redoAction.updateRedoStatus();
    }
    else
      System.out.println("Can not create UndoManager.");
  }
  
  class UndoAction extends AbstractAction {
    public UndoAction() {
      super("Undo");
      setEnabled(false);
    }
  
    public void actionPerformed(ActionEvent e) {
      // 復原
      try {
        // 執行復原，若文字編輯物件不支援復原功能時
        // 則將產生CannotUndoException之例外
        undoManager.undo();
      } 
      catch (CannotUndoException ex) {
        ex.printStackTrace();
      }
      
      updateUndoStatus();
      redoAction.updateRedoStatus();
    }
  
    public void updateUndoStatus() {
      // 判斷是否可以復原編輯
      if (undoManager.canUndo()) {
        putValue(Action.NAME, undoManager.getUndoPresentationName());
        // 設定啟用Undo選單項目
        setEnabled(true);
      } 
      else {
        putValue(Action.NAME, "Undo");
        // 設定不啟用Undo選單項目
        setEnabled(false);
      }  
    }
  }
  
  class RedoAction extends AbstractAction {
    public RedoAction() {
      super("Redo");
      setEnabled(false);
    }
  
    public void actionPerformed(ActionEvent e) {
      // 復原
      try {
        // 執行復原，若文字編輯物件不支援重複功能時
        // 則將產生CannotRedoException之例外
        undoManager.redo();
      } 
      catch (CannotRedoException ex) {
        ex.printStackTrace();
      }
      
      updateRedoStatus();
      undoAction.updateUndoStatus();
    }
  
    public void updateRedoStatus() {
      // 判斷是否可以重複編輯
      if (undoManager.canRedo()) {
        putValue(Action.NAME, undoManager.getRedoPresentationName());
        // 設定啟用Redo選單項目
        setEnabled(true);
      } 
      else {
        putValue(Action.NAME, "Redo");
        // 設定不啟用Redo選單項目
        setEnabled(false);
      }
    }
  }  
}