import java.awt.*;
import java.awt.event.*;

public class ChoiceDemo extends java.awt.Frame {

  public static void main(String args[]){
    new ChoiceDemo();
  }
  
  // �غc�禡
  public ChoiceDemo() {
    super("Choice Demo");

    String[] items = {"Choice 1", "Choice 2", "Choice 4", "Choice 5"};
    
    java.awt.Choice choice;

    final int row = 2;    // �C
    final int column = 1; // ��

    // �w�q Layout Manager �� GridLayout
    setLayout(new GridLayout(row, column));

    java.awt.Panel panel1 = new Panel();

    // �غc�禡
    choice = new Choice();
    
    // �]�wChoice������ء]Item�^�A����index��0�}�l�C
    for (int i=0; i < items.length; i++)    
      choice.add(items[i]);

    // �۲�2�Ӷ��ؤ���A���J�@�ﶵ���ءA����index��0�}�l�C    
    choice.insert("Choice 3", 2);

    // �����3�ӿﶵ���ءA����index��0�}�l�C
    choice.select(3);
    panel1.add(choice);
    add(panel1);

    Label label = new Label();
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
