import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import java.beans.*;
import java.io.*;

public class ImageAccessory extends JComponent implements PropertyChangeListener {
  ImageIcon picture = null;
  File file = null;

  // 建構函式
  public ImageAccessory(JFileChooser jfilechooser) {
    // 設定 Etched Border (Demo only)
    Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, Color.white, new Color(142, 142, 142)); 

    this.setBorder(BorderFactory.createTitledBorder(border, "預覽圖像"));

    this.setPreferredSize(new Dimension(100, 50));
    
    jfilechooser.addPropertyChangeListener(this);
  }

  // 當物件的屬性狀態改變時
  public void propertyChange(PropertyChangeEvent e) {
    // 取得改變的屬性值名稱
    String prop = e.getPropertyName();

    boolean update = false;

    // 若選擇目錄
    if (prop.equals(JFileChooser.DIRECTORY_CHANGED_PROPERTY)) {
      file = null;
      update = true;
    } 
    // 若選擇檔案
    else if (prop.equals(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY)) {
      // 取得屬性改變之後其新的屬性值內容
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

  // 繪製圖像
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
