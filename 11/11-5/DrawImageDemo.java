import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.util.*;

public class DrawImageDemo extends java.applet.Applet implements MouseListener, MouseMotionListener {
  int x, y;

  Image image;
  
  boolean blnDrag = false;
  
  // �غc�禡
  public DrawImageDemo() {
  }

  public void init() {
    // �]�w�I���C��
    setBackground(Color.white);

    // Ū��Java Archive�ɮפ����Ϲ��ɮ�
    ClassLoader cl = this.getClass().getClassLoader();
    Toolkit tk = Toolkit.getDefaultToolkit();

    // ���o�Ϲ�
    image = tk.createImage(cl.getResource("images/SuperDuke.gif"));

    // ���U MouseListener
    this.addMouseListener(this);    
    // ���U MouseMotionListener
    this.addMouseMotionListener(this);    
  }

  public void update(Graphics g) {
    paint(g);
  }

  public void paint (Graphics g) {
    if (blnDrag) {
      // �]�wGraphics���󤧥ثe�C��
      g.setColor(Color.white);
      // ø�s�x�ΨåH�ثe���w���C��񺡭��n
      // �h�M���e��
      g.fillRect(0, 0, this.getSize().width, this.getSize().height);
      
      // ø�s�Ϲ�
      g.drawImage(image, x, y, null);
    }
  }

  // ��@MouseListener��������k
  public void mousePressed(MouseEvent e){
    blnDrag = true;
  }
  
  public void mouseReleased(MouseEvent e){
    blnDrag = false;
  }
  
  public void mouseEntered(MouseEvent e){}
  public void mouseExited(MouseEvent e){}
  public void mouseClicked(MouseEvent e){}
  
  // ��@MouseMotionListener��������k
  public void mouseDragged(MouseEvent e){
    x = e.getX();
    y = e.getY();

    repaint();
  }

  public void mouseMoved(MouseEvent e){
    x = e.getX();
    y = e.getY();

    repaint();
  }
}
