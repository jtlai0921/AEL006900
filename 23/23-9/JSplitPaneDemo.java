import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class JSplitPaneDemo extends JFrame {
    
  CardLayout cardlayout = new CardLayout();
  JPanel jpanel, bottomPanel;
  JList jlist;
  JLabel jlabel, lblName;
  JSplitPane jsplitpane;
  JSplitPane topSplitpane;

  String[] items = new String[12];

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

    new JSplitPaneDemo();
  }

  // 建構函式
  public JSplitPaneDemo() {
    super("JSplitPane Demo");

    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 設定視窗圖示
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // 定義 Layout Manager 為 BorderLayout
    this.setLayout(new BorderLayout());

    // 定義 jpanel 之 Layout Manager 為 CardLayout
    jpanel = new JPanel();
    jpanel.setLayout(cardlayout); 

    lblName = new JLabel();
    
    for (int i=0; i < items.length; i++)  {  
      items[i] = "icon" + i + ".png";

      jlabel = new JLabel(new ImageIcon(cl.getResource("images/" + items[i])));
      jpanel.add(jlabel, items[i]);
    }

    cardlayout.show(jpanel, items[0]);

    jlist = new JList(items);
    // 單選
    jlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    jlist.setSelectedIndex(0);
    jlist.addListSelectionListener(new ListSelectionListener() {
      // 當選項項目被選取或取消選取時
      public void valueChanged(ListSelectionEvent e) {
        // 判斷此事件是否為多個不同更改事件之一
        if(e.getValueIsAdjusting())
          return;
        
        // 判斷事件的來源
        JList list = (JList)e.getSource();
        
        // 取得被選取項目的索引值
        int i = list.getSelectedIndex();
        
        cardlayout.show(jpanel, items[i]);
        
        lblName.setText("Select icon" + i + ".png");
      }
    });
    
    // 捲軸
    JScrollPane jscrollpane1 = new JScrollPane(jlist);
    jscrollpane1.setWheelScrollingEnabled(true);
    jscrollpane1.setMinimumSize(new Dimension(100, 50));

    // 捲軸
    JScrollPane jscrollpane2 = new JScrollPane(jpanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    jscrollpane2.setWheelScrollingEnabled(true);
    jscrollpane2.setMinimumSize(new Dimension(100, 50));

    // 分隔面板
    // 水平分隔
    jsplitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jscrollpane1, jscrollpane2);
    // 設定是否以分隔Widget快速展開或摺疊分隔面板
    jsplitpane.setOneTouchExpandable(true);
    // 設定分隔軸的位置
    jsplitpane.setDividerLocation(100);
    jsplitpane.setPreferredSize(new Dimension(400, 200));

    // 定義 bottomPanel 之 Layout Manager 為 BorderLayout
    bottomPanel = new JPanel();
    bottomPanel.setLayout(new BorderLayout()); 
    bottomPanel.add(lblName, BorderLayout.CENTER);
    bottomPanel.setMinimumSize(new Dimension(100, 50));

    // 分隔面板
    // 垂直分隔
    topSplitpane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, jsplitpane, bottomPanel);
    // 設定是否以分隔Widget快速展開或摺疊分隔面板
    topSplitpane.setOneTouchExpandable(true);
    // 設定分隔軸的位置
    topSplitpane.setDividerLocation(150);
    
    this.add(topSplitpane, BorderLayout.CENTER);

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
}
