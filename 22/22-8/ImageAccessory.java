import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import java.beans.*;
import java.io.*;

public class ImageAccessory extends JComponent implements PropertyChangeListener {
  ImageIcon picture = null;
  File file = null;

  // �غc�禡
  public ImageAccessory(JFileChooser jfilechooser) {
    // �]�w Etched Border (Demo only)
    Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, Color.white, new Color(142, 142, 142)); 

    this.setBorder(BorderFactory.createTitledBorder(border, "�w���Ϲ�"));

    this.setPreferredSize(new Dimension(100, 50));
    
    jfilechooser.addPropertyChangeListener(this);
  }

  // �����ݩʪ��A���ܮ�
  public void propertyChange(PropertyChangeEvent e) {
    // ���o���ܪ��ݩʭȦW��
    String prop = e.getPropertyName();

    boolean update = false;

    // �Y��ܥؿ�
    if (prop.equals(JFileChooser.DIRECTORY_CHANGED_PROPERTY)) {
      file = null;
      update = true;
    } 
    // �Y����ɮ�
    else if (prop.equals(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY)) {
      // ���o�ݩʧ��ܤ����s���ݩʭȤ��e
      file = (File) e.getNewValue();
      update = true;
    }

    if (update) {
      picture = null;

      if (isShowing()) {
        createImage();
        repaint();
      }
    }
  }

  public void createImage() {
    if (file == null) {
      picture = null;
      return;
    }

    ImageIcon tmpIcon = new ImageIcon(file.getPath());

    if (tmpIcon != null) {
      if (tmpIcon.getIconWidth() > 90) {
        picture = new ImageIcon(tmpIcon.getImage().getScaledInstance(90, -1, Image.SCALE_DEFAULT));
      } 
      else { 
        picture = tmpIcon;
      }
    }
  }

  // ø�s�Ϲ�
  protected void paintComponent(Graphics g) {
    if (picture == null) {
      createImage();
    }

    if (picture != null) {
      int x = getWidth() /2 - picture.getIconWidth() /2;
      int y = getHeight()/2 - picture.getIconHeight()/2;

      if (y < 0) {
        y = 0;
      }

      if (x < 5) {
        x = 5;
      }

      picture.paintIcon(this, g, x, y);
    }
  }
}
