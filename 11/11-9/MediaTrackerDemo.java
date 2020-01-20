import java.awt.*;
import java.awt.event.*;
import java.applet.*;

public class MediaTrackerDemo extends java.applet.Applet {

  Image[] image = new Image[2];

  // 建構函式
  public MediaTrackerDemo() {
  }

  public void init(){
    setBackground(Color.white);

    // 讀取Java Archive檔案內的圖像檔案
    ClassLoader cl = this.getClass().getClassLoader();
    Toolkit tk = Toolkit.getDefaultToolkit();

    MediaTracker mt = new MediaTracker(this);

    // 取得圖像
    image[0] = tk.createImage(cl.getResource("images/duke.gif"));

    if (image[0] != null)
      mt.addImage(image[0], 0);

    try {
      mt.waitForID(0);
    }
    catch(InterruptedException ex) {
      ex.printStackTrace();
    }

    // 取得圖像
    image[1] = tk.createImage(cl.getResource("images/SuperDuke.gif"));

    if (image[1] != null)
      mt.addImage(image[1], 1);

    try {
      mt.waitForID(1);
    }
    catch(InterruptedException ex) {
      ex.printStackTrace();
    }
  }

  public void update(Graphics g) {
    paint(g);
  }

  public void paint(Graphics g) {
    // 設定Graphics物件之目前顏色
    g.setColor(Color.white);
    // 繪製矩形並以目前指定之顏色填滿面積
    // 則清除畫面
    g.fillRect(0, 0, this.getSize().width, this.getSize().height);
    
    // 繪製圖像
    g.drawImage(image[0], 10, 10, null);
    g.drawImage(image[1], 50, 50, null);
  }
}
