import java.awt.*;
import javax.swing.*;

public class Corner extends JComponent {
  // 繪製圖形
  protected void paintComponent(Graphics g) {
    // 設定物件顏色
    g.setColor(new Color(200, 200, 255));
    // 以x, y座標、寬度及高度繪製矩形
    // 並以目前指定的顏色填滿面積
    g.fillRect(0, 0, getWidth(), getHeight());
  }
}
