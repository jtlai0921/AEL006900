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

  // Media Player ����
  javax.media.Player player;
  
  Component visualComponent, controlPanel;
  
  JPanel mediaPanel;

  File file = null;

  // �غc�禡
  public MediaAccessory(JFileChooser jfilechooser) {
    // �]�w Etched Border (Demo only)
    Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, Color.white, new Color(142, 142, 142)); 

    this.setBorder(BorderFactory.createTitledBorder(border, "�C�鼽��"));

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

  // �����ݩʪ��A���ܮ�
  public void propertyChange(PropertyChangeEvent e) {
    // ���o���ܪ��ݩʭȦW��
    String prop = e.getPropertyName();

    // �Y��ܥؿ�
    if (prop.equals(JFileChooser.DIRECTORY_CHANGED_PROPERTY)) {
      file = null;

      stopPlayer();  
    } 
    // �Y����ɮ�
    else if (prop.equals(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY)) {
      // ���o�ݩʧ��ܤ����s���ݩʭȤ��e
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
      System.out.println("�L�k�}���ɮ�.");
      return;
    }
  
    try {
      player = Manager.createPlayer(mediaLocator);
      
      player.addControllerListener(new ControllerAdapter(){
        // ��Media Player��������ˬd�T�{�C�餺�e�ɩҩI�s����k
        public void realizeComplete(RealizeCompleteEvent e)  {
          // ���i��w���B�z�C�餺�e
          player.prefetch();
        }
        
        // ��Media Player��������w���B�z�C�餺�e�ɩҩI�s����k
        public void prefetchComplete(PrefetchCompleteEvent e) {
          // ���o�v���ɪ����e�A�H�K���񤧥�
          visualComponent = player.getVisualComponent();
        
          if (visualComponent != null)
            mediaPanel.add(visualComponent, BorderLayout.CENTER);
        
          // ���oMedia Player�w�]������O
          controlPanel = player.getControlPanelComponent();
        
          if (controlPanel != null)
            mediaPanel.add(controlPanel, BorderLayout.SOUTH);
        
          validate();
          
          // �}�lMedia Player�A�h�}�l����C�餺�e
          player.start();
        }  
        
        // ��Media Player�������C�餺�e�����ɩҩI�s����k
        public void endOfMedia(EndOfMediaEvent e)  {
          // �]�w�C��ثe������ɶ�
          player.setMediaTime(new Time(0));
          // ����Media Player�A�h�����C�餺�e
          player.stop();
        }        
      });
      
      // �ˬd�T�{�C�餺�e
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
      
      // ����Media Player�A�h��������C��
      player.close();
      
      // ���mUI�~�[
      mediaPanel.updateUI();
    }  
  }
}
