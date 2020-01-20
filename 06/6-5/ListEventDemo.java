import java.awt.*;
import java.awt.event.*;

public class ListEventDemo extends java.awt.Frame implements ActionListener, ItemListener {

  java.awt.List[] list = new List[3];

  public static void main(String args[]){
    new ListEventDemo();
  }
  
  // �غc�禡
  public ListEventDemo() {
    super("List Event Demo");

    final int row = 1;    // �C
    final int column = 3; // ��

    // �w�q Layout Manager �� GridLayout
    setLayout(new GridLayout(row, column));

    // �غc�禡 1
    // �w�]��ܶ��ئC�Ƭ�4
    // �B�����\�h����ܶ���
    list[0] = new List();
    // ���U ActionListener
    list[0].addActionListener(this);
    // ���U ItemListener
    list[0].addItemListener(this);
    
    // �]�wList�������
    for (int i=0; i < 10; i++)    
      list[0].add("List " + i);
      
    // �]�w�O�_���\���سQ�h�����  
    list[0].setMultipleMode(false);

    // ����ﶵ����
    list[0].select(4);

    java.awt.Panel panel1 = new Panel();
    panel1.add(list[0]);
    add(panel1);

    // �غc�禡 2
    // �i��ܪ����ئC�Ƭ�5
    list[1] = new List(5);
    // ���U ActionListener
    list[1].addActionListener(this);
    // ���U ItemListener
    list[1].addItemListener(this);
    
    // �]�wList������ء]Item�^
    for (int i=0; i < 10; i++)    
      list[1].add("List " + i);
      
    // �]�w�O�_���\���سQ�h�����  
    list[1].setMultipleMode(true);

    // ����ﶵ����
    list[1].select(4);
    list[1].select(6);

    java.awt.Panel panel2 = new Panel();
    panel2.add(list[1]);
    add(panel2);

    // �غc�禡 3
    // �i��ܪ����ئC�Ƭ�6
    // �]�w���\���سQ�h�����  
    list[2] = new List(6, true);
    // ���U ActionListener
    list[2].addActionListener(this);
    // ���U ItemListener
    list[2].addItemListener(this);
    
    // �]�wList������ء]Item�^
    for (int i=0; i < 10; i++)    
      list[2].add("List " + i);
      
    // ����ﶵ����
    list[2].select(2);
    list[2].select(3);

    java.awt.Panel panel3 = new Panel();
    panel3.add(list[2]);
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

  // ��ʧ@�o�ͮ�
  public void actionPerformed(ActionEvent e) {
    for (int i=0; i<3; i++) {
      if (e.getSource().equals(list[i]))
        System.out.println("Double click List " + i + "\r\n");
    }
  }

  // ���ت��A���ܮ�
  public void itemStateChanged(ItemEvent e) {
    // ���o���Ͷ��بƥ󪺪���
    List list = (List)(e.getItemSelectable());
    
    String result = "Total items: " + list.getItemCount() + "\r\n";
    
    // 
    int[] index = list.getSelectedIndexes();
    
    for (int i=0; i < index.length; i++) 
      result = result + "   Selected Index: " + index[i] + "\r\n";

    System.out.println(result);
  }
}
