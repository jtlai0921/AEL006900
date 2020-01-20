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
  // Media Player ����
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
      JOptionPane.showMessageDialog(this, "�L�k�}���ɮ�.", "Java Media Player Applet", JOptionPane.ERROR_MESSAGE);
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
            contentPane.add(visualComponent, BorderLayout.CENTER);
        
          // ���oMedia Player�w�]������O
          controlPanel = player.getControlPanelComponent();
        
          if (controlPanel != null)
            contentPane.add(controlPanel, BorderLayout.SOUTH);
        
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

  public void start() {
    if (player != null)
      // �}�lMedia Player�A�h�}�l����C�餺�e
      player.start();
  }

  public void stop() {
    if (player != null) {
      // ����Media Player�A�h�����C�餺�e
      player.stop();
      player.deallocate();
    }
  }

  public void destroy() {
    // ����Media Player�A�h��������C��
    player.close();
  }
}
