import java.awt.*;
import javax.swing.*;

public class HeaderView extends JComponent {
  public static final int HORIZONTAL = 0;
  public static final int VERTICAL = 1;
  public static final int SIZE = 35;

  int orientation;
  int increment;
  int units;

  public HeaderView(int orientation) {
    this.orientation = orientation;
    units = (int)((double)Toolkit.getDefaultToolkit().getScreenResolution() / (double)2.54);
    increment = units;
  }

  public int getIncrement() {
    return increment;
  }

  public void setPreferredHeight(int height) {
    setPreferredSize(new Dimension(SIZE, height));
  }

  public void setPreferredWidth(int width) {
    setPreferredSize(new Dimension(width, SIZE));
  }

  // ø�s�ϧ�
  protected void paintComponent(Graphics g) {
    int end = 0;
    int start = 0;
    int length = 0;
    String tick = null;

    Rectangle rect = g.getClipBounds();

    // �]�w�����C��
    g.setColor(new Color(200, 200, 255));
    // �Hx, y�y�СB�e�פΰ���ø�s�x��
    // �åH�ثe���w���C��񺡭��n
    g.fillRect(rect.x, rect.y, rect.width, rect.height);

    // �]�w�r��
    g.setFont(new Font("Dialog", Font.PLAIN, 10));
    // �]�w�����C��
    g.setColor(Color.BLACK);

    if (orientation == HORIZONTAL) {
      start = (rect.x / increment) * increment;
      end = (((rect.x + rect.width) / increment) + 1) * increment;
    } 
    else {
      start = (rect.y / increment) * increment;
      end = (((rect.y + rect.height) / increment) + 1) * increment;
    }

    if (start == 0) {
      tick = Integer.toString(0);
      length = 10;

      if (orientation == HORIZONTAL) {
        // ø�s�u��
        g.drawLine(0, SIZE-1, 0, SIZE-length-1);
        // ø�s��r
        g.drawString(tick, 2, 21);
      } 
      else {
        // ø�s�u��
        g.drawLine(SIZE-1, 0, SIZE-length-1, 0);
        // ø�s��r
        g.drawString(tick, 9, 10);
      }
      tick = null;
      start = increment;
    }

    for (int i = start; i < end; i += increment) {
      if (i % units == 0)  {
        length = 10;
        tick = Integer.toString(i/units);
      } 
      else {
        length = 7;
        tick = null;
      }

      if (length != 0) {
        if (orientation == HORIZONTAL) {
          // ø�s�u��
          g.drawLine(i, SIZE-1, i, SIZE-length-1);

          if (tick != null)
            // ø�s��r
            g.drawString(tick, i-3, 21);
        } 
        else {
          // ø�s�u��
          g.drawLine(SIZE-1, i, SIZE-length-1, i);

          if (tick != null)
            // ø�s��r
            g.drawString(tick, 9, i+3);
        }
      }
    }
  }
}
