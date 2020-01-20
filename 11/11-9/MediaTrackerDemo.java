import java.awt.*;
import java.awt.event.*;
import java.applet.*;

public class MediaTrackerDemo extends java.applet.Applet {

  Image[] image = new Image[2];

  // �غc�禡
  public MediaTrackerDemo() {
  }

  public void init(){
    setBackground(Color.white);

    // Ū��Java Archive�ɮפ����Ϲ��ɮ�
    ClassLoader cl = this.getClass().getClassLoader();
    Toolkit tk = Toolkit.getDefaultToolkit();

    MediaTracker mt = new MediaTracker(this);

    // ���o�Ϲ�
    image[0] = tk.createImage(cl.getResource("images/duke.gif"));

    if (image[0] != null)
      mt.addImage(image[0], 0);

    try {
      mt.waitForID(0);
    }
    catch(InterruptedException ex) {
      ex.printStackTrace();
    }

    // ���o�Ϲ�
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
    // �]�wGraphics���󤧥ثe�C��
    g.setColor(Color.white);
    // ø�s�x�ΨåH�ثe���w���C��񺡭��n
    // �h�M���e��
    g.fillRect(0, 0, this.getSize().width, this.getSize().height);
    
    // ø�s�Ϲ�
    g.drawImage(image[0], 10, 10, null);
    g.drawImage(image[1], 50, 50, null);
  }
}
