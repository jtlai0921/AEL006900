import java.awt.*;
import javax.swing.*;

public class Corner extends JComponent {
  // ø�s�ϧ�
  protected void paintComponent(Graphics g) {
    // �]�w�����C��
    g.setColor(new Color(200, 200, 255));
    // �Hx, y�y�СB�e�פΰ���ø�s�x��
    // �åH�ثe���w���C��񺡭��n
    g.fillRect(0, 0, getWidth(), getHeight());
  }
}
