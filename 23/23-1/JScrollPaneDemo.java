import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class JScrollPaneDemo extends JFrame {
    
  CardLayout cardlayout = new CardLayout();
  JPanel jpanel = new JPanel();

  JList jlist;

  JLabel jlabel;

  String[] items = new String[4];

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

    new JScrollPaneDemo();
  }
  
  // 建構函式
  public JScrollPaneDemo() {
    super("JScrollPane Demo");

    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 設定視窗圖示
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // 定義 Layout Manager 為 BorderLayout
    this.setLayout(new BorderLayout());

    // 定義 jpanel 之 Layout Manager 為 CardLayout
    jpanel.setLayout(cardlayout); 
    
    for (int i=0; i < 4; i++)  {  
      items[i] = "baby" + i + ".jpg";

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
      }
    });
    
    // 捲軸
    JScrollPane jscrollpane1 = new JScrollPane(jlist);
    jscrollpane1.setWheelScrollingEnabled(true);
    this.add(jscrollpane1, BorderLayout.WEST);

    // 捲軸
    JScrollPane jscrollpane2 = new JScrollPane(jpanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    jscrollpane2.setWheelScrollingEnabled(true);
    this.add(jscrollpane2, BorderLayout.CENTER);

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    this.validate();
    this.setSize(new Dimension(350, 300));
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });    
  }
}
