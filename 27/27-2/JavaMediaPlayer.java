import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.io.*;
import java.net.*;

// Java Media Framework
import javax.media.*;
  
public class JavaMediaPlayer extends JFrame {

  // Media Player 介面
  javax.media.Player player = null;
  
  PlayerFrame frame = null;

  JDesktopPane desktop;

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

    new JavaMediaPlayer();
  }

  // 建構函式
  public JavaMediaPlayer() {
    super("Java Media Player");

    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 設定視窗圖示
    this.setIconImage(new ImageIcon(cl.getResource("images/jmf.gif")).getImage());

    // 定義Layout Manager 為 BorderLayout
    this.setLayout(new BorderLayout());

    desktop = new JDesktopPane();
    desktop.setDoubleBuffered(true);
    desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
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
    // Open
    JMenuItem jmenuOpen = new JMenuItem("Open ...");
    jmenuOpen.setMnemonic('O');
    // 註冊 ActionListener
    jmenuOpen.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        openFile();
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
        JOptionPane.showMessageDialog(null, "Java Media Player", "About", JOptionPane.INFORMATION_MESSAGE);
      }
    });
    
    this.validate();
    this.setSize(new Dimension(400, 400));

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    this.setResizable(true);
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });    

    // Specify a hint for the Manager to use.
    javax.media.Manager.setHint(Manager.LIGHTWEIGHT_RENDERER, new Boolean(true));
  }  

  private void openFile() {
    JFileChooser jfilechooser = new JFileChooser();
    // 設定檔案對話盒的標題
    jfilechooser.setDialogTitle("Open File ...");
    // 設定檔案對話盒的型式
    jfilechooser.setDialogType(JFileChooser.OPEN_DIALOG);
    jfilechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    jfilechooser.setCurrentDirectory(new File("."));
    
    if (jfilechooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
      return;

    this.repaint();
    
    try {
      final String url = jfilechooser.getSelectedFile().toURI().toURL().toString();
      final String title = jfilechooser.getSelectedFile().getName().toString();

      Thread thread = new Thread() {
        public void run() {
          showPlayer(url, title);
        }
      };
      thread.start();
    }
    catch (MalformedURLException muex) {
      muex.printStackTrace();
    }
  }

  public void showPlayer(String url, String title) {
    MediaLocator mediaLocator = new MediaLocator(url);
  
    if (mediaLocator == null) {
      JOptionPane.showMessageDialog(this, "無法開啟檔案.", "Java Media Player", JOptionPane.ERROR_MESSAGE);
      return;
    }
  
    try {
      player = Manager.createPlayer(mediaLocator);
    }
    catch (NoPlayerException npex) {
      npex.printStackTrace();
    }
    catch (IOException ioex) {
      ioex.printStackTrace();
    }

    if (player != null) {
      PlayerFrame frame = new PlayerFrame(player, title);
      
      desktop.add(frame);

      try {
        frame.setSelected(true);
      } 
      catch (java.beans.PropertyVetoException pe) {
        pe.printStackTrace();        
      }
    }
  }

  public void cascadeFrame() {
    int x = 0;
    int y = 0;

    try {
      JInternalFrame[] frames = desktop.getAllFrames();
      JInternalFrame selectedFrame = desktop.getSelectedFrame();

      for (int i=frames.length-1; i>=0; i--) {
        frames[i].setMaximum(false);
        frames[i].setIcon(false);
        frames[i].setLocation(x, y);

        x += 20;
        y += 20;
      }
      if (selectedFrame != null)
        desktop.setSelectedFrame(selectedFrame);
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }
}

class PlayerFrame extends JInternalFrame {
  static int frameCount = 0;
  static final int xOffset = 30, yOffset = 30;

  Component visualComponent = null;
  Component controlPanel = null;
  
  int videoWidth = 0;
  int videoHeight = 0;
  int controlHeight = 50;
  
  public PlayerFrame(Player player, String title) {
    super(title, true, true, true, true);

    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 設定Internal Frame視窗圖示
    this.setFrameIcon(new ImageIcon(cl.getResource("images/java.gif")));

    ++frameCount;
    
    // 定義 Layout Manager 為 BorderLayout
    getContentPane().setLayout(new BorderLayout());

    final Player mplayer = player;

    mplayer.addControllerListener(new ControllerAdapter(){
      // 當Media Player控制器完成檢查確認媒體內容時所呼叫的方法
      public void realizeComplete(RealizeCompleteEvent e)  {
        // 儘可能預先處理媒體內容
        mplayer.prefetch();
      }
      
      // 當Media Player控制器完成預先處理媒體內容時所呼叫的方法
      public void prefetchComplete(PrefetchCompleteEvent e) {
        if (visualComponent != null)
          return;

        // 取得影像檔的內容，以便播放之用
        visualComponent = mplayer.getVisualComponent();
      
        if (visualComponent != null) {
          Dimension size = visualComponent.getPreferredSize();
          videoWidth = size.width;
          videoHeight = size.height;
          getContentPane().add(visualComponent, BorderLayout.CENTER);
        } 
        else
          videoWidth = 300;
      
        // 取得Media Player預設之控制面板
        controlPanel = mplayer.getControlPanelComponent();
      
        if (controlPanel != null) {
          controlHeight = controlPanel.getPreferredSize().height;
          getContentPane().add(controlPanel, BorderLayout.SOUTH);
        } 

        setSize(videoWidth + 10, videoHeight + controlHeight + 40);
      
        validate();
        
        // 開始Media Player，則開始播放媒體內容
        mplayer.start();
      }  
      
      // 當Media Player控制器播放媒體內容完畢時所呼叫的方法
      public void endOfMedia(EndOfMediaEvent e)  {
        // 設定媒體目前的播放時間
        mplayer.setMediaTime(new Time(0));
        // 停止Media Player，則停止播放媒體內容
        mplayer.stop();
      }        
    });
    
    // 檢查確認媒體內容
    mplayer.realize();

    this.addInternalFrameListener(new InternalFrameAdapter() {
      public void internalFrameClosing(InternalFrameEvent e) {
        // 關閉Media Player，則關閉播放媒體
        mplayer.close();
      }
    });
    
    this.setVisible(true);
    this.setLocation(xOffset*frameCount, yOffset*frameCount);
  }
}
