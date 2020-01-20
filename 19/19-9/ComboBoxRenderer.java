import java.awt.*;
import javax.swing.*;

// 實作javax.swing.ListCellRenderer介面
public class ComboBoxRenderer extends JLabel implements ListCellRenderer { 

  // 建構函式
  public ComboBoxRenderer() {}

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
      // 取得JComboBox物件的文字字型
      font = list.getFont();
      // 取得JComboBox物件的前景顏色
      color = list.getForeground();
      text = "";
    } 
    
    if (!isSelected) { 
      // 設定JComboBox物件的前景顏色
      this.setForeground(color);
    } 
    
    if (icon != null) { 
      // 設定JComboBox物件的圖像
      this.setIcon(icon);
    } 
    
    // 設定JComboBox物件的文字字型
    this.setFont(font);
    // 設定JComboBox物件的顯示文字
    this.setText(text);
    
    return this;
  } 
} 
