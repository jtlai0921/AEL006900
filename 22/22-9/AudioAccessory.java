import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import java.net.*;
import java.beans.*;
import java.io.*;

public class AudioAccessory extends JPanel implements ActionListener, PropertyChangeListener {

  JButton btnPlay, btnStop;
  JLabel lblFilename;

  AudioClip currentClip;
  String currentName = "";

  File file = null;

  // �غc�禡
  public AudioAccessory(JFileChooser jfilechooser) {
    // �]�w Etched Border (Demo only)
    Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, Color.white, new Color(142, 142, 142)); 

    this.setBorder(BorderFactory.createTitledBorder(border, "�C�鼽��"));

    this.setPreferredSize(new Dimension(100, 50));

    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    btnPlay = new JButton(new ImageIcon(cl.getResource("images/play.gif")));
    btnPlay.setPreferredSize(new Dimension(32, 32));
    btnPlay.setEnabled(false);
    btnPlay.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (currentClip != null) {
          currentClip.stop();
          currentClip.play();
        }
      }
    });

    btnStop = new JButton(new ImageIcon(cl.getResource("images/stop.gif")));
    btnStop.setPreferredSize(new Dimension(32, 32));
    btnStop.setEnabled(false);
    btnStop.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (currentClip != null) {
          currentClip.stop();
        }
      }
    });

    lblFilename = new JLabel();

    JPanel jpanel = new JPanel();
    jpanel.add(btnPlay);
    jpanel.add(btnStop);

    this.setLayout(new BorderLayout());
    this.add(jpanel, BorderLayout.CENTER);
    this.add(lblFilename, BorderLayout.SOUTH);

    jfilechooser.addActionListener(this);
    jfilechooser.addPropertyChangeListener(this);
  }

  // �����ݩʪ��A���ܮ�
  public void propertyChange(PropertyChangeEvent e) {
    // ���o���ܪ��ݩʭȦW��
    String prop = e.getPropertyName();

    // �Y��ܥؿ�
    if (prop.equals(JFileChooser.DIRECTORY_CHANGED_PROPERTY)) {
      file = null;

      btnPlay.setEnabled(false);
      btnStop.setEnabled(false);
    } 
    // �Y����ɮ�
    else if (prop.equals(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY)) {
      // ���o�ݩʧ��ܤ����s���ݩʭȤ��e
      file = (File) e.getNewValue();

      if ((file != null) && 
          (file.getName().toLowerCase().endsWith(".au") ||
           file.getName().toLowerCase().endsWith(".wav") ||
           file.getName().toLowerCase().endsWith(".aif") ||
           file.getName().toLowerCase().endsWith(".aiff"))) {
        
        setCurrentClip(file);
      }
      else {
        setCurrentClip(null);
      }
    }
  }

  public void setCurrentClip(File f) {
    if (currentClip != null) { 
      currentClip.stop(); 
    }
    
    if ((f == null) || (f.getName() == null)) {
      lblFilename.setText("no audio selected");
      btnPlay.setEnabled(false);
      btnStop.setEnabled(false);
      return;
    }

    String name = f.getName();
    
    if (name.equals(currentName)) {
      lblFilename.setText(name);
      btnPlay.setEnabled(true);
      btnStop.setEnabled(true);
      return;
    }
    currentName = name;
    
    try {
      URL u = new URL("file:///" + f.getAbsolutePath());
      currentClip = Applet.newAudioClip(u);
    }
    catch (Exception e) {
      e.printStackTrace();
      currentClip = null;
      lblFilename.setText("Error.");
    }
    lblFilename.setText(name);
    btnPlay.setEnabled(true);
    btnStop.setEnabled(true);
  }

  public void actionPerformed(ActionEvent ae) {
    if (currentClip != null) { 
      currentClip.stop(); 
    }
  }
}
