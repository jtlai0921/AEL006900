import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.util.*;

public class GraphicsDemo extends java.applet.Applet implements MouseListener { // 實作MouseListener介面 
  
  // 建構函式
  public GraphicsDemo() {
  }

  public void init() {
    // 設定背景顏色
    setBackground(Color.white);

    // 註冊 MouseListener
    this.addMouseListener(this);
  }

  public void paint(Graphics g) {
    int r, x1, x2, y1, y2;
    int red, green, blue;

    Random random = new Random();

    // 取得Applet之尺寸
    Dimension dimension = this.getSize();

    int width = dimension.width;
    int height = dimension.height;
    
    for (int i=0; i<100; i++) {
      r = random.nextInt(9);
      x1 = random.nextInt(width);  
      y1 = random.nextInt(height); 
      x2 = random.nextInt(width); 
      y2 = random.nextInt(height);  
      red = random.nextInt(255); 
      green = random.nextInt(255); 
      blue = random.nextInt(255); 
      
      // 設定Graphics物件之目前顏色
      g.setColor(new Color(red, green, blue)); 

      switch (r) {
        case 0:
          g.drawLine(x1, y1, x2, y2);  
          break;
        case 1:
          g.draw3DRect(x1, y1, x2-x1, y2-y1, true); 
          break;
        case 2:
          g.drawOval(x1, y1, x2-x1, y2-y1); 
          break;
        case 3:
          g.drawRect(x1, y1, x2-x1, y2-y1); 
          break;
        case 4:
          g.drawRoundRect(x1, y1, x2-x1, y2-y1, 10, 10); 
          break;
        case 5:
          g.fill3DRect(x1, y1, x2-x1, y2-y1, true); 
          break;
        case 6:
          g.fillOval(x1, y1, x2-x1, y2-y1); 
          break;
        case 7:
          g.fillRect(x1, y1, x2-x1, y2-y1); 
          break;
        case 8:
          g.fillRoundRect(x1, y1, x2-x1, y2-y1, 10, 10); 
          break;
        default:
          break;
      }
    }    
  }

  // 實作MouseListener介面之方法
  public void mouseClicked(MouseEvent e) {
    this.repaint();
  }

  public void mouseEntered(MouseEvent e) {}
  public void mouseExited(MouseEvent e) {}
  public void mousePressed(MouseEvent e) {}
  public void mouseReleased(MouseEvent e) {}
}
