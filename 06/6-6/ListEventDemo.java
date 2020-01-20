import java.awt.*;
import java.awt.event.*;

public class ListEventDemo extends java.awt.Frame {

  java.awt.List[] list = new List[3];

  public static void main(String args[]){
    new ListEventDemo();
  }
  
  // 建構函式
  public ListEventDemo() {
    super("List Event Demo");

    final int row = 1;    // 列
    final int column = 3; // 行

    // 定義 Layout Manager 為 GridLayout
    setLayout(new GridLayout(row, column));

    // 建構函式 1
    // 預設顯示項目列數為4
    // 且不允許多重選擇項目
    list[0] = new List();
    // 註冊 ActionListener
    list[0].addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // 自訂方法
        button_actionPerformed(e);
      }
    });
    // 註冊 ItemListener
    list[0].addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        // 自訂方法
        list_itemStateChanged(e);
      }
    });

    
    // 設定List選取項目
    for (int i=0; i < 10; i++)    
      list[0].add("List " + i);
      
    // 設定是否允許項目被多重選擇  
    list[0].setMultipleMode(false);

    // 選取選項項目
    list[0].select(4);

    java.awt.Panel panel1 = new Panel();
    panel1.add(list[0]);
    add(panel1);

    // 建構函式 2
    // 可顯示的項目列數為5
    list[1] = new List(5);
    // 註冊 ActionListener
    list[1].addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // 自訂方法
        button_actionPerformed(e);
      }
    });
    // 註冊 ItemListener
    list[1].addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        // 自訂方法
        list_itemStateChanged(e);
      }
    });
    
    // 設定List選取項目（Item）
    for (int i=0; i < 10; i++)    
      list[1].add("List " + i);
      
    // 設定是否允許項目被多重選擇  
    list[1].setMultipleMode(true);

    // 選取選項項目
    list[1].select(4);
    list[1].select(6);

    java.awt.Panel panel2 = new Panel();
    panel2.add(list[1]);
    add(panel2);

    // 建構函式 3
    // 可顯示的項目列數為6
    // 設定允許項目被多重選擇  
    list[2] = new List(6, true);
    // 註冊 ActionListener
    list[2].addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // 自訂方法
        button_actionPerformed(e);
      }
    });
    // 註冊 ItemListener
    list[2].addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        // 自訂方法
        list_itemStateChanged(e);
      }
    });
    
    // 設定List選取項目（Item）
    for (int i=0; i < 10; i++)    
      list[2].add("List " + i);
      
    // 選取選項項目
    list[2].select(2);
    list[2].select(3);

    java.awt.Panel panel3 = new Panel();
    panel3.add(list[2]);
    add(panel3);

    // 設定視窗的大小
    this.setSize(420, 150);

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();

    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;

    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;

    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    // 顯示視窗
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }

  // 自訂方法
  void button_actionPerformed(ActionEvent e) {
    for (int i=0; i<3; i++) {
      if (e.getSource().equals(list[i]))
        System.out.println("Double click List " + i + "\r\n");
    }
  }

  // 自訂方法
  void list_itemStateChanged(ItemEvent e) {
    // 取得產生項目事件的物件
    List list = (List)(e.getItemSelectable());
    
    String result = "Total items: " + list.getItemCount() + "\r\n";
    
    // 
    int[] index = list.getSelectedIndexes();
    
    for (int i=0; i < index.length; i++) 
      result = result + "   Selected Index: " + index[i] + "\r\n";

    System.out.println(result);
  }
}
