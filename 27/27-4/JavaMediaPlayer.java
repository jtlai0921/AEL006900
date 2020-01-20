import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.io.*;
import java.net.*;

// Java Media Framework
import javax.media.*;
import javax.media.bean.playerbean.*; 
  
public class JavaMediaPlayer extends JFrame implements ControllerListener {
  
  // Media Player Bean類別
  javax.media.bean.playerbean.MediaPlayer player = null;
  
  Component visualComponent, controlPanel;
  
  Container contentPane;

  JCheckBoxMenuItem jmenuLoop;
  
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
  
    // Get current classloader
    ClassLoader cl = this.getClass().getClassLoader();

    // Create icons
    ImageIcon image1 = new ImageIcon(cl.getResource("images/jmf.gif"));
    this.setIconImage(image1.getImage());

    contentPane = getContentPane();
    contentPane.setLayout(new BorderLayout());

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
    JMenu jmenuPlayer = new JMenu("Player");
    jmenuPlayer.setMnemonic('P');
    jmenubar.add(jmenuPlayer);

    // 建立選單項目
    // Auto Loop
    jmenuLoop = new JCheckBoxMenuItem("Auto Loop");
    jmenuLoop.setMnemonic('L');
    jmenuLoop.setState(true);
    jmenuPlayer.add(jmenuLoop);
    // 註冊 ActionListener
    jmenuLoop.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        setAutoLoop();
      }
    });

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
    this.setSize(new Dimension(300, 300));

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
    JFileChooser jfileChooser = new JFileChooser();
    // 設定檔案對話盒的標題
    jfileChooser.setDialogTitle("Open File ...");
    // 設定檔案對話盒的型式
    jfileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
    jfileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    jfileChooser.setCurrentDirectory(new File("."));
    
    if (jfileChooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
      return;

    this.repaint();
    
    try {
      final String url = jfileChooser.getSelectedFile().toURI().toURL().toString();

      Thread thread = new Thread() {
        public void run() {
          showPlayer(url);
        }
      };
      thread.start();
    }
    catch (MalformedURLException muex) {
      muex.printStackTrace();
    }
  }

  public void setAutoLoop() {
    // 設定重複播放
    if (player != null) 
      player.setPlaybackLoop(jmenuLoop.getState());
  }

  public void showPlayer(String url) {
    // Reset Player
    if (player != null) {
      if (visualComponent != null)
        contentPane.remove(visualComponent);
      
      if (controlPanel != null)
        contentPane.remove(controlPanel);
      
      // 關閉Media Player，則關閉播放媒體
      player.close();
    }  

    // MediaPlayer bean
    player = new javax.media.bean.playerbean.MediaPlayer();
    player.setMediaLocation(url);

    // 註冊 ControllerListener
    player.addControllerListener((ControllerListener) this);
    // 檢查確認媒體內容
    player.realize();

    // 重複播放
    player.setPlaybackLoop(jmenuLoop.getState());
  }
      
  public void controllerUpdate(ControllerEvent event) {
    // 當Media Player控制器完成檢查確認媒體內容時所呼叫的方法
    if (event instanceof RealizeCompleteEvent) {
      // 儘可能預先處理媒體內容
      player.prefetch();
    } 
    // 當Media Player控制器完成預先處理媒體內容時所呼叫的方法
    else if (event instanceof PrefetchCompleteEvent) {
      // 取得影像檔的內容，以便播放之用
      visualComponent = player.getVisualComponent();
    
      if (visualComponent != null)
        contentPane.add(visualComponent, BorderLayout.CENTER);
    
      // 取得Media Player預設之控制面板
      controlPanel = player.getControlPanelComponent();
    
      if (controlPanel != null)
        contentPane.add(controlPanel, BorderLayout.SOUTH);
    
      validate();

      // 開始Media Player，則開始播放媒體內容
      player.start();
    } 
    // 當Media Player控制器播放媒體內容完畢時所呼叫的方法
    else if (event instanceof EndOfMediaEvent) {
      // 是否重複播放
      if (!player.isPlayBackLoop()) {
        // 設定媒體目前的播放時間
        player.setMediaTime(new Time(0));
        // 停止Media Player，則停止播放媒體內容
        player.stop();
      }
    }
  }
}
