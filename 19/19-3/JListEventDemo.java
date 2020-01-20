import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

import java.io.*;

public class JListEventDemo extends JFrame implements ListSelectionListener { // 實作ListSelectionListener介面
    
  JTextArea jtextArea = new JTextArea();

  // Main method
  public static void main(String[] args) {
    UIManager.put("swing.boldMetal", Boolean.FALSE);
    JDialog.setDefaultLookAndFeelDecorated(true);
    JFrame.setDefaultLookAndFeelDecorated(true);
    Toolkit.getDefaultToolkit().setDynamicLayout(true);

    try {
      UIManager.setLookAndFeel(new javax.swing.plaf.metal.MetalLookAndFeel());
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    new JListEventDemo();
  }

  // 建構函式
  public JListEventDemo() {
    super("JList Event Demo");

    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 設定視窗圖示
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // 定義 Layout Manager 為 BorderLayout
    this.setLayout(new BorderLayout());

    // 設定 Etched Border (Demo only)
    Border border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.white, new Color(142, 142, 142)); 

    JPanel jpanel1 = new JPanel();
    jpanel1.setLayout(new BorderLayout());
    jpanel1.setBorder(BorderFactory.createTitledBorder(border, "選項"));
    JPanel jpanel2 = new JPanel();
    jpanel2.setLayout(new BorderLayout());
    jpanel2.setBorder(BorderFactory.createTitledBorder(border, "結果"));

    final int length = 12;  // 選項項目個數
    String[] items = new String[length];

    for (int i=0; i < length; i++)    
      items[i] = "Item " + i;

    JList jlist = new JList(items);
    // 註冊 addListSelectionListener
    jlist.addListSelectionListener(this);

    // 捲軸
    JScrollPane jscrollpane1 = new JScrollPane(jlist);
    jpanel1.add(jscrollpane1, BorderLayout.CENTER);

    jtextArea.setEditable(false);
    // 捲軸
    JScrollPane jscrollpane2 = new JScrollPane(jtextArea);
    jpanel2.add(jscrollpane2, BorderLayout.CENTER);

    this.add(jpanel1, BorderLayout.WEST);
    this.add(jpanel2, BorderLayout.CENTER);

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    this.validate();
    this.setSize(new Dimension(350, 250));
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });    
  }

  // 當選項項目被選取或取消選取時
  public void valueChanged(ListSelectionEvent e) {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    
    pw.println("第一個索引值: " + e.getFirstIndex() + ", 最後一個索引值: " + e.getLastIndex());
    
    // 判斷此事件是否為多個不同更改事件之一
    boolean adjust = e.getValueIsAdjusting();
    
    if (!adjust) {
      JList source = (JList)e.getSource();
      
      // 取得被選取項目的索引值
      int selections[] = source.getSelectedIndices();
      // 取得已被選取的項目
      Object selectionValues[] = source.getSelectedValues();
      
      for (int i=0, n=selections.length; i<n; i++) {
        if (i==0) {
          pw.print("  選取項目: ");
        }
        pw.print(selections[i] + "(" + selectionValues[i] + ") ");
      }
      pw.println();
    }
    jtextArea.append(sw.toString());
  }
}
