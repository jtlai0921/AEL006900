import java.awt.*;
import javax.swing.*;

// 實作javax.swing.ListCellRenderer介面
public class ListRenderer extends JLabel implements ListCellRenderer { 

  // 建構函式
  public ListRenderer() {}

  // 實作javax.swing.ListCellRenderer介面的getListCellRendererComponent()方法
  public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) { 
    Font font   = null;
    Color color = null;
    Icon icon   = null;
    String text = null;
  
    if (value instanceof Object[]) { 
      Object values[] = (Object[])value;
  
      font = (Font)values[0];
      color = (Color)values[1];
      icon = (Icon)values[2];
      text = (String)values[3];
    }  
    else { 
      // 取得JList物件的文字字型
      font = list.getFont();
      // 取得JList物件的前景顏色
      color = list.getForeground();
      text = "";
    } 
    
    if (!isSelected) { 
      // 設定JList物件的前景顏色
      this.setForeground(color);
    } 
    
    if (icon != null) { 
      // 設定JList物件的圖像
      this.setIcon(icon);
    } 
    
    // 設定JList物件的文字字型
    this.setFont(font);
    // 設定JList物件的顯示文字
    this.setText(text);
    
    return this;
  } 
} 
