import java.awt.*;
import javax.swing.*;

// ��@javax.swing.ListCellRenderer����
public class ComboBoxRenderer extends JLabel implements ListCellRenderer { 

  // �غc�禡
  public ComboBoxRenderer() {}

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
      // ���oJComboBox���󪺤�r�r��
      font = list.getFont();
      // ���oJComboBox���󪺫e���C��
      color = list.getForeground();
      text = "";
    } 
    
    if (!isSelected) { 
      // �]�wJComboBox���󪺫e���C��
      this.setForeground(color);
    } 
    
    if (icon != null) { 
      // �]�wJComboBox���󪺹Ϲ�
      this.setIcon(icon);
    } 
    
    // �]�wJComboBox���󪺤�r�r��
    this.setFont(font);
    // �]�wJComboBox������ܤ�r
    this.setText(text);
    
    return this;
  } 
} 
