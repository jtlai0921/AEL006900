import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import java.net.*;
import java.beans.*;
import java.io.*;

// Java Media Framework
import javax.media.*;

public class MediaAccessory extends JPanel implements ActionListener, PropertyChangeListener {

  // Media Player 介面
  javax.media.Player player;
  
  Component visualComponent, controlPanel;
  
  JPanel mediaPanel;

  File file = null;

  // 建構函式
  public MediaAccessory(JFileChooser jfilechooser) {
    // 設定 Etched Border (Demo only)
    Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, Color.white, new Color(142, 142, 142)); 

    this.setBorder(BorderFactory.createTitledBorder(border, "媒體播放"));

    this.setPreferredSize(new Dimension(150, 150));

    mediaPanel = new JPanel();
    mediaPanel.setLayout(new BorderLayout());

    this.setLayout(new BorderLayout());
    this.add(mediaPanel, BorderLayout.CENTER);

    jfilechooser.addActionListener(this);
    jfilechooser.addPropertyChangeListener(this);

    // Specify a hint for the Manager to use.
    javax.media.Manager.setHint(Manager.LIGHTWEIGHT_RENDERER, new Boolean(true));
  }

  // 當物件的屬性狀態改變時
  public void propertyChange(PropertyChangeEvent e) {
    // 取得改變的屬性值名稱
    String prop = e.getPropertyName();

    // 若選擇目錄
    if (prop.equals(JFileChooser.DIRECTORY_CHANGED_PROPERTY)) {
      file = null;

      stopPlayer();  
    } 
    // 若選擇檔案
    else if (prop.equals(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY)) {
      // 取得屬性改變之後其新的屬性值內容
      file = (File) e.getNewValue();

      if ((file != null) && 
          (file.getName().toLowerCase().endsWith(".avi") ||
           file.getName().toLowerCase().endsWith(".midi") ||
           file.getName().toLowerCase().endsWith(".mov") ||
           file.getName().toLowerCase().endsWith(".mp3") ||
           file.getName().toLowerCase().endsWith(".mpeg") ||
           file.getName().toLowerCase().endsWith(".mpg") ||
           file.getName().toLowerCase().endsWith(".wav") ||
           file.getName().toLowerCase().endsWith(".wma"))) {
       
		    try {
		      final String url = file.toURI().toURL().toString();
		
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
      else {
      	stopPlayer();
      }
    }
  }

  public void showPlayer(String url) {
    stopPlayer();  

    MediaLocator mediaLocator = new MediaLocator(url);
  
    if (mediaLocator == null) {
      System.out.println("無法開啟檔案.");
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
            mediaPanel.add(visualComponent, BorderLayout.CENTER);
        
          // 取得Media Player預設之控制面板
          controlPanel = player.getControlPanelComponent();
        
          if (controlPanel != null)
            mediaPanel.add(controlPanel, BorderLayout.SOUTH);
        
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

  public void actionPerformed(ActionEvent ae) {
    stopPlayer();  
  }

  private void stopPlayer(){
    // Reset Player
    if (player != null) {
      if (visualComponent != null)
        mediaPanel.remove(visualComponent);
      
      if (controlPanel != null)
        mediaPanel.remove(controlPanel);
      
      // 關閉Media Player，則關閉播放媒體
      player.close();
      
      // 重置UI外觀
      mediaPanel.updateUI();
    }  
  }
}
