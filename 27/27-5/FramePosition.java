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
import javax.media.control.*;
  
public class FramePosition extends JFrame {
  
  // Media Player 介面
  javax.media.Player player;
  
  javax.media.control.FramePositioningControl fpc;
  
  Component visualComponent, controlPanel;
  
  Container contentPane;

  JButton btnBack = new JButton();
  JButton btnForward = new JButton();
  JLabel lblStatus = new JLabel();

  Object wait = new Object();
  boolean startOK = true;
  int totalFrames = FramePositioningControl.FRAME_UNKNOWN;
  int currentFrame = 0;
  
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

    new FramePosition();
  }

  // 建構函式
  public FramePosition() {
    super("JMF Frame Position");
  
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
        JOptionPane.showMessageDialog(null, "JMF Frame Position", "About", JOptionPane.INFORMATION_MESSAGE);
      }
    });

    btnBack.setEnabled(false);
    btnBack.setIcon(new ImageIcon(cl.getResource("images/back.gif")));
    btnBack.setDisabledIcon(new ImageIcon(cl.getResource("images/backD.gif")));
    btnBack.setMinimumSize(new Dimension(30, 30));
    btnBack.setMaximumSize(new Dimension(30, 30));
    btnBack.setPreferredSize(new Dimension(30, 30));
    btnBack.setToolTipText("Back");
    btnBack.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        button_actionPerformed(e);
      }
    });
    
    btnForward.setEnabled(false);
    btnForward.setIcon(new ImageIcon(cl.getResource("images/fwd.gif")));
    btnForward.setDisabledIcon(new ImageIcon(cl.getResource("images/fwdD.gif")));
    btnForward.setMaximumSize(new Dimension(30, 30));
    btnForward.setMinimumSize(new Dimension(30, 30));
    btnForward.setPreferredSize(new Dimension(30, 30));
    btnForward.setToolTipText("Forward");
    btnForward.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        button_actionPerformed(e);
      }
    });
    
    JToolBar jtoolbar = new JToolBar();
    jtoolbar.setRollover(true);
    jtoolbar.add(btnBack);
    jtoolbar.add(btnForward);

    lblStatus.setPreferredSize(new Dimension(40, 25));
    lblStatus.setText("");

    JPanel Panel1 = new JPanel();
    Panel1.setLayout(new BorderLayout());
    Panel1.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142)),BorderFactory.createEmptyBorder(2,4,2,4)));

    Panel1.add(lblStatus, BorderLayout.CENTER);

    contentPane.add(jtoolbar, BorderLayout.NORTH);
    contentPane.add(Panel1, BorderLayout.SOUTH);
    
    this.validate();
    this.setSize(new Dimension(300, 350));

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

    MediaLocator mediaLocator = new MediaLocator(url);
  
    if (mediaLocator == null) {
      JOptionPane.showMessageDialog(this, "無法開啟檔案.", "JMF Frame Position", JOptionPane.ERROR_MESSAGE);
      return;
    }
  
    try {
      player = Manager.createPlayer(mediaLocator);

      player.addControllerListener(new ControllerAdapter(){
        
        public void configureComplete(ConfigureCompleteEvent e) {
          synchronized (wait) {
            startOK = true;
            wait.notifyAll();
          }
        }

        // 當Media Player控制器完成檢查確認媒體內容時所呼叫的方法
        public void realizeComplete(RealizeCompleteEvent e)  {
          synchronized (wait) {
            startOK = true;
            wait.notifyAll();
          }
        }
        
        // 當Media Player控制器完成預先處理媒體內容時所呼叫的方法
        public void prefetchComplete(PrefetchCompleteEvent e) {
          synchronized (wait) {
            startOK = true;
            wait.notifyAll();
          }
        }  

        public void resourceUnavailable(ResourceUnavailableEvent e) {
          synchronized (wait) {
            startOK = false;
            wait.notifyAll();
          }
        }
        
        // 當Media Player控制器播放媒體內容完畢時所呼叫的方法
        public void endOfMedia(EndOfMediaEvent e)  {
          // 設定媒體目前的播放時間
          player.setMediaTime(new Time(0));
        }        
      });
      
      // 檢查確認媒體內容
      player.realize();

      // 取得javax.media.control.FramePositioningControl物件
      fpc = (FramePositioningControl)player.getControl("javax.media.control.FramePositioningControl");
    
      if (fpc == null)
        this.lblStatus.setText("Media Player不支援FramePositioningControl功能");
      else {    
        javax.media.Time duration = player.getDuration();
      
        if (duration != Duration.DURATION_UNKNOWN) {
          this.lblStatus.setText("媒體時間長度: " + duration.getSeconds());
    
          // 將媒體的時間長度轉換為相對的畫面格數
          totalFrames = fpc.mapTimeToFrame(duration);
  
          if (totalFrames != FramePositioningControl.FRAME_UNKNOWN)
            this.lblStatus.setText("總畫面: " + totalFrames);
          else
            this.lblStatus.setText("FramePositioningControl不支援mapTimeToFrame方法");
        } 
        else 
          this.lblStatus.setText("無法取得媒體時間長度"); 
        
        // 儘可能預先處理媒體內容
        player.prefetch();
  
        if (!waitForState(player.Prefetched)) {
          this.lblStatus.setText("無法預先處理媒體內容");
          return;
        }
  
        // 取得影像檔的內容，以便播放之用
        visualComponent = player.getVisualComponent();
      
        if (visualComponent != null) {
          contentPane.add(visualComponent, BorderLayout.CENTER);
          btnForward.setEnabled(true);
        }
        else {
          btnBack.setEnabled(false);
          btnForward.setEnabled(false);
        }
    
        this.validate();    
        this.setVisible(true);
      }
    }
    catch (NoPlayerException npex) {
      npex.printStackTrace();
    }
    catch (IOException ioex) {
      ioex.printStackTrace();
    }
  } 

  boolean waitForState(int state) {
    synchronized (wait) {
      try {
        while (player.getState() < state && startOK)
          wait.wait();
      } 
      catch (Exception e) {}
    }
    return startOK;
  }

  public void button_actionPerformed(ActionEvent e) {
    String str = "";
    
    if (e.getSource() == btnBack) {
      if (currentFrame > 0) {
        int Number = fpc.skip(-1);
         str = "向前移 " + Number + " 格畫面. ";
      }
    }  
    else if (e.getSource() == btnForward) {
      if (currentFrame < totalFrames) {
        int Number = fpc.skip(1);
         str = "向後移 " + Number + " 格畫面. ";
      }
    }

    // 將媒體的時間長度轉換為相對的畫面格數
    currentFrame = fpc.mapTimeToFrame(player.getMediaTime());
    
    if (currentFrame != FramePositioningControl.FRAME_UNKNOWN)
      str = str + "目前位置: " + currentFrame + "/" + totalFrames ;
    
    this.lblStatus.setText(str);

    if (currentFrame > 0)
      btnBack.setEnabled(true);
    else
      btnBack.setEnabled(false);
      
    if (currentFrame < totalFrames)
      btnForward.setEnabled(true);
    else
      btnForward.setEnabled(false);
  }
}
