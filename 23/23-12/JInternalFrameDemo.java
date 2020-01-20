import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.io.*;
  
public class JInternalFrameDemo extends JFrame {

  JDesktopPane desktop;

  JToolBar jtoolbar = new JToolBar();
  JButton jbutton[] = new JButton[3];
  String item[]  = {"New", "Open", "About"};
  String image[] = {"new", "open", "about"};

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

    new JInternalFrameDemo();
  }

  // 建構函式
  public JInternalFrameDemo() {
    super("JInternalFrame Demo");

    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 設定視窗圖示
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // 設定活動中標題的背景顏色之屬性值
    UIManager.put("InternalFrame.activeTitleBackground", Color.PINK);

    // 設定活動中標題的前景顏色之屬性值
    UIManager.put("InternalFrame.activeTitleForeground", Color.RED);

    // 設定非活動中標題的背景顏色之屬性值
    UIManager.put("InternalFrame.inactiveTitleBackground", Color.CYAN);

    // 設定非活動中標題的前景顏色之屬性值
    UIManager.put("InternalFrame.inactiveTitleForeground", Color.BLUE);

    // 設定Internal Frame邊框之屬性值
    UIManager.put("InternalFrame.border", new javax.swing.border.EtchedBorder(EtchedBorder.LOWERED));
    
    // 設定Internal Frame背景顏色之屬性值
    UIManager.put("InternalFrame.background", Color.PINK);

    // 設定關閉視窗按鈕的圖示
    UIManager.put("InternalFrame.closeIcon", new ImageIcon(cl.getResource("images/close.png")));
    
    // 設定視窗圖示
    UIManager.put("InternalFrame.icon", new ImageIcon(cl.getResource("images/java.gif")));
    
    // 設定最小化視窗按鈕的圖示
    UIManager.put("InternalFrame.iconifyIcon", new ImageIcon(cl.getResource("images/minimize.png")));
    
    // 設定最大化視窗按鈕的圖示
    UIManager.put("InternalFrame.maximizeIcon", new ImageIcon(cl.getResource("images/maximize.png")));

    // 設定最小化視窗按鈕的圖示
    UIManager.put("InternalFrame.minimizeIcon", new ImageIcon(cl.getResource("images/restore.png")));
    
    // 取得Internal Frame之Pluggable Look and Feel屬性值
    System.out.println("JInternalFrame Look and Feel: " + UIManager.getString("InternalFrameUI"));
    
    // 定義 Layout Manager 為 BorderLayout
    this.setLayout(new BorderLayout());

    desktop = new JDesktopPane();
    desktop.setDoubleBuffered(true);
    // 設定拖曳Internal Frame視窗的模式
    desktop.setDragMode(JDesktopPane.LIVE_DRAG_MODE);
    //this.setContentPane(desktop);
    this.add(desktop, BorderLayout.CENTER);

    // 建立選單列
    JMenuBar jmenubar = new JMenuBar();
    // 定義視窗使用者介面之選單列
    this.setJMenuBar(jmenubar);

    // 建立選單
    JMenu jmenuFile = new JMenu("File");
    jmenuFile.setMnemonic('F');
    jmenubar.add(jmenuFile);

    // 建立選單項目
    // New
    JMenuItem jmenuNew = new JMenuItem("New");
    jmenuNew.setMnemonic('N');
    // 註冊 ActionListener
    jmenuNew.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        InternalFrame frame = new InternalFrame();
        
        // 將Internal Frame視窗加入至JDesktopPane類別之中
        desktop.add(frame);
    
        try {
          // 選取目前的Internal Frame視窗
          frame.setSelected(true);
        } 
        catch (java.beans.PropertyVetoException pe) {
          pe.printStackTrace();        
        }
      }
    });
    
    // Open
    JMenuItem jmenuOpen = new JMenuItem("Open ...");
    jmenuOpen.setMnemonic('O');
    // 註冊 ActionListener
    jmenuOpen.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        open();
      }
    });
    
    // Exit
    JMenuItem jmenuExit = new JMenuItem("Exit");
    jmenuExit.setMnemonic('X');
    // 註冊 ActionListener
    jmenuExit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JOptionPane joptionpane = new JOptionPane();
        int iResult = joptionpane.showConfirmDialog(null, "Are you sure to quit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (iResult == 0) {
          dispose();
          System.exit(0);
        }
      }
    });

    jmenuFile.add(jmenuNew);
    jmenuFile.add(jmenuOpen);
    jmenuFile.addSeparator();
    jmenuFile.add(jmenuExit);

    // 建立選單
    JMenu jmenuWindow = new JMenu("Window");
    jmenuWindow.setMnemonic('W');
    jmenubar.add(jmenuWindow);

    // 建立選單項目
    // Cascade
    JMenuItem jmenuCascade = new JMenuItem("Cascade");
    jmenuCascade.setMnemonic('C');
    // 註冊 ActionListener
    jmenuCascade.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cascadeFrame();
      }
    });

    jmenuWindow.add(jmenuCascade);

    // 建立選單
    // Help
    JMenu jmenuHelp = new JMenu("Help");
    jmenuHelp.setMnemonic('H');
    jmenubar.add(jmenuHelp);

    // About
    JMenuItem jmenuAbout = new JMenuItem("About");
    jmenuAbout.setMnemonic('A');
    jmenuHelp.add(jmenuAbout);
    jmenuAbout.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "JInternalFrame Demo", "About", JOptionPane.INFORMATION_MESSAGE);
      }
    });

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

      if (i==1)
        jtoolbar.addSeparator();
    }

    this.add(jtoolbar, BorderLayout.NORTH);
    
    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    this.validate();
    this.setSize(new Dimension(400, 400));
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });    
  }  

  private void button_actionPerformed(ActionEvent e) {
    if (e.getSource().equals(jbutton[0])) { // New
      InternalFrame frame = new InternalFrame();
      
      // 將Internal Frame視窗加入至JDesktopPane類別之中
      desktop.add(frame);
  
      try {
        // 選取目前的Internal Frame視窗
        frame.setSelected(true);
      } 
      catch (java.beans.PropertyVetoException pe) {
        pe.printStackTrace();        
      }
    }
    else if (e.getSource().equals(jbutton[1])) { // Open
      open();
    }
    else if (e.getSource().equals(jbutton[2])) { // About
      JOptionPane.showMessageDialog(null, "JInternalFrame Demo", "About", JOptionPane.INFORMATION_MESSAGE);
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
    jfilechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    
    if (jfilechooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) 
      return;
      
    String filename = jfilechooser.getSelectedFile().toString();
    String title = jfilechooser.getSelectedFile().getName().toString();
    
    InternalFrame frame = new InternalFrame(filename, title);
    
    desktop.add(frame);

    try {
      frame.setSelected(true);
    } 
    catch (java.beans.PropertyVetoException pe) {
      pe.printStackTrace();        
    }
  }

  public void cascadeFrame() {
    int x = 0;
    int y = 0;

    try {
      // 取得桌面面板中所有的Internal Frame視窗
      JInternalFrame[] frames = desktop.getAllFrames();
      // 取得桌面面板中目前有效活動（Active）的Internal Frame視窗
      JInternalFrame selectedFrame = desktop.getSelectedFrame();

      for (int i=frames.length-1; i>=0; i--) {
        frames[i].setMaximum(false);
        frames[i].setIcon(false);
        frames[i].setLocation(x, y);

        x += 20;
        y += 20;
      }
      if (selectedFrame != null)
        // 設定桌面面板中目前有效活動（Active）的Internal Frame視窗
        desktop.setSelectedFrame(selectedFrame);
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }
}

// 自訂繼承JInternalFrame之類別
class InternalFrame extends JInternalFrame {
  static int frameCount = 0;
  static final int xOffset = 30, yOffset = 30;

  // 建構函式
  public InternalFrame() {
    this("", "");
  }

  // 建構函式
  public InternalFrame(String filename, String title) {
    // 設定視窗標題
    // 設定可調整Internal Frame視窗大小
    // 設定可關閉Internal Frame視窗
    // 設定可最大化Internal Frame視窗
    // 設定可圖示化Internal Frame視窗
    // public JInternalFrame(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable)
    super(title, true, true, true, true);

    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 設定Internal Frame視窗圖示
    this.setFrameIcon(new ImageIcon(cl.getResource("images/java.gif")));

    ++frameCount;
    
    // 定義 Layout Manager 為 BorderLayout
    this.setLayout(new BorderLayout());

    JTextArea jtextarea = new JTextArea();

    // 設定自動換行的規則
    jtextarea.setWrapStyleWord(false);
    // 設定JTextArea當文字超過行寬時，是否自動換行。
    jtextarea.setLineWrap(false);
    // 設定是否可編輯
    jtextarea.setEditable(true);
    // 設定脫字符號目前的位置
    jtextarea.setCaretPosition(0);

    // 設定捲軸面板
    JScrollPane jscrollpane = new JScrollPane(jtextarea);
    // 垂直捲軸：當超過JTextArea列數時才自動顯示垂直捲軸
    jscrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    // 水平捲軸：當超過JTextArea行數時才自動顯示水平捲軸
    jscrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    if (!filename.equals("")) {
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
  
    this.add(jscrollpane, BorderLayout.CENTER);
    
    // 設定Internal Frame視窗大小
    this.setSize(300, 300);
    // 設定Internal Frame視窗的位置
    this.setLocation(xOffset*frameCount, yOffset*frameCount);
    // 設定是否顯示物件
    this.setVisible(true);
  }
}
