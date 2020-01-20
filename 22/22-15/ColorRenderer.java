import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

// 實作javax.swing.ListCellRenderer介面
public class ColorRenderer extends JPanel implements ListCellRenderer {
  Color currentColor;

  // 建構函式
  public ColorRenderer() {
    currentColor = Color.black;
    setBorder(new CompoundBorder(new MatteBorder(2, 10, 2, 10, Color.white), new LineBorder(Color.black)));
  }

  // 實作javax.swing.ListCellRenderer介面的getListCellRendererComponent()方法
  public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) { 
    if(value instanceof Color)
      currentColor = (Color)value;

    return this;
  }

  public void paint(Graphics g) {
    setBackground(currentColor);
    super.paint(g);
  }
}