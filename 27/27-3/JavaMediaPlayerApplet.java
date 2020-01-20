import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.util.*;
import java.io.*;
import java.text.*;
import java.net.*;

// Java Media Framework
import javax.media.*;

public class JavaMediaPlayerApplet extends JApplet {
  // Media Player 介面
  javax.media.Player player;
  
  Component visualComponent, controlPanel;
  
  Container contentPane;

  static {
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
  }

  public JavaMediaPlayerApplet() {}

  public void init() {
    String filename = null;
    URL url = null;

    contentPane = getContentPane();
    contentPane.setLayout(new BorderLayout());

    filename = getParameter("file");

    try {
      url = new URL(getDocumentBase(), filename);
    } 
    catch (MalformedURLException mue) {
    }
    
    MediaLocator mediaLocator = new MediaLocator(url.toExternalForm());
  
    if (mediaLocator == null) {
      JOptionPane.showMessageDialog(this, "無法開啟檔案.", "Java Media Player Applet", JOptionPane.ERROR_MESSAGE);
      return;
    }
  
    try {
      player = Manager.createPlayer(mediaLocator);
      
      player.addControllerListener(new ControllerAdapter(){
        // 當Media Player控制器完成檢查確認媒體內容時所呼叫的方法
        public void realizeComplete(RealizeCompleteEvent e)  {
          // 儘可能預先處理媒體內容
          player.prefetch();
        }
        
        // 當Media Player控制器完成預先處理媒體內容時所呼叫的方法
        public void prefetchComplete(PrefetchCompleteEvent e) {
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
        public void endOfMedia(EndOfMediaEvent e)  {
          // 設定媒體目前的播放時間
          player.setMediaTime(new Time(0));
          // 停止Media Player，則停止播放媒體內容
          player.stop();
        }        
      });
      
      // 檢查確認媒體內容
      player.realize();
    }
    catch (NoPlayerException npex) {
      npex.printStackTrace();
    }
    catch (IOException ioex) {
      ioex.printStackTrace();
    }
  }

  public void start() {
    if (player != null)
      // 開始Media Player，則開始播放媒體內容
      player.start();
  }

  public void stop() {
    if (player != null) {
      // 停止Media Player，則停止播放媒體內容
      player.stop();
      player.deallocate();
    }
  }

  public void destroy() {
    // 關閉Media Player，則關閉播放媒體
    player.close();
  }
}
