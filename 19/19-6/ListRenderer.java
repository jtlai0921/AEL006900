import java.awt.*;
import javax.swing.*;

// ��@javax.swing.ListCellRenderer����
public class ListRenderer extends JLabel implements ListCellRenderer { 

  // �غc�禡
  public ListRenderer() {}

  // ��@javax.swing.ListCellRenderer������getListCellRendererComponent()��k
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
      // ���oJList���󪺤�r�r��
      font = list.getFont();
      // ���oJList���󪺫e���C��
      color = list.getForeground();
      text = "";
    } 
    
    if (!isSelected) { 
      // �]�wJList���󪺫e���C��
      this.setForeground(color);
    } 
    
    if (icon != null) { 
      // �]�wJList���󪺹Ϲ�
      this.setIcon(icon);
    } 
    
    // �]�wJList���󪺤�r�r��
    this.setFont(font);
    // �]�wJList������ܤ�r
    this.setText(text);
    
    return this;
  } 
} 
