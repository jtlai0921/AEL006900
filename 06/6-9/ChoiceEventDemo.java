import java.awt.*;
import java.awt.event.*;

public class ChoiceEventDemo extends java.awt.Frame {

  java.awt.Choice choice;
  java.awt.Label label;

  public static void main(String args[]){
    new ChoiceEventDemo();
  }
  
  // �غc�禡
  public ChoiceEventDemo() {
    super("Choice Event Demo");

    String[] items = {"Choice 1", "Choice 2", "Choice 4", "Choice 5"};
    
    final int row = 2;    // �C
    final int column = 1; // ��

    // �w�q Layout Manager �� GridLayout
    setLayout(new GridLayout(row, column));

    java.awt.Panel panel1 = new Panel();

    // �غc�禡
    choice = new Choice();
    // ���U ItemListener
    choice.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        // ���o���Ͷ��بƥ󪺪���
        Choice ch = (Choice)(e.getItemSelectable());
        
        label.setText("Total items: " + ch.getItemCount() + ",    Selected Item: " + ch.getSelectedItem() + ",    Selected Index: " + ch.getSelectedIndex());
      }
    });
    
    // �]�wChoice������ء]Item�^�A����index��0�}�l�C
    for (int i=0; i < items.length; i++)    
      choice.add(items[i]);

    // �۲�2�Ӷ��ؤ���A���J�@�ﶵ���ءA����index��0�}�l�C    
    choice.insert("Choice 3", 2);

    // �����3�ӿﶵ���ءA����index��0�}�l�C
    choice.select(3);
    panel1.add(choice);
    add(panel1);

    label = new Label();
    // Demo Only
    label.setText("Total items: " + choice.getItemCount() + ",    First Item: " + choice.getItem(0) + ",    Init Selected Index: " + choice.getSelectedIndex());
    add(label);

    // �]�w�������j�p
    this.setSize(350, 100);

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();

    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;

    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;

    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    // ��ܵ���
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }
}
