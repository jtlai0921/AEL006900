import java.awt.*;
import java.awt.event.*;

public class ListDemo extends java.awt.Frame {

  public static void main(String args[]){
    new ListDemo();
  }
  
  // �غc�禡
  public ListDemo() {
    super("List Demo");

    final int row = 1;    // �C
    final int column = 3; // ��

    // �w�q Layout Manager �� GridLayout
    setLayout(new GridLayout(row, column));

    // �غc�禡 1
    // �w�]��ܶ��ئC�Ƭ�4
    // �B�����\�h����ܶ���
    java.awt.List list = new List();
    
    // �]�wList�������
    for (int i=0; i < 10; i++)    
      list.add("List " + i);
      
    // �]�w�O�_���\���سQ�h�����  
    list.setMultipleMode(false);

    // ����ﶵ����
    list.select(4);

    java.awt.Panel panel1 = new Panel();
    panel1.add(list);
    add(panel1);

    // �غc�禡 2
    // �i��ܪ����ئC�Ƭ�5
    list = new List(5);
    
    // �]�wList������ء]Item�^
    for (int i=0; i < 10; i++)    
      list.add("List " + i);
      
    // �]�w�O�_���\���سQ�h�����  
    list.setMultipleMode(true);

    // ����ﶵ����
    list.select(4);
    list.select(6);

    java.awt.Panel panel2 = new Panel();
    panel2.add(list);
    add(panel2);

    // �غc�禡 3
    // �i��ܪ����ئC�Ƭ�6
    // �]�w���\���سQ�h�����  
    list = new List(6, true);
    
    // �]�wList������ء]Item�^
    for (int i=0; i < 10; i++)    
      list.add("List " + i);
      
    // ����ﶵ����
    list.select(2);
    list.select(3);

    java.awt.Panel panel3 = new Panel();
    panel3.add(list);
    add(panel3);

    // �]�w�������j�p
    this.setSize(420, 150);

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
