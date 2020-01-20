import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class JListDemo extends JFrame {

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

    new JListDemo();
  }

  // 建構函式
  public JListDemo() {
    super("JList Demo");

    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 設定視窗圖示
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // 設定檢視清單邊框之屬性值
    UIManager.put("List.border", new javax.swing.border.EtchedBorder(EtchedBorder.LOWERED));
    
    // 設定檢視清單前景之屬性值
    UIManager.put("List.foreground", Color.PINK);
    
    // 設定檢視清單文字標籤字型之屬性值
    UIManager.put("List.font", new Font("dialog", Font.BOLD, 12));
    
    // 取得檢視清單Pluggable Look and Feel屬性值
    System.out.println("JList Look and Feel: " + UIManager.getString("ListUI"));
   
    // 定義 Layout Manager 為 FlowLayout
    this.setLayout(new FlowLayout());

    // 設定 Etched Border (Demo only)
    Border border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.white, new Color(142, 142, 142)); 

    JPanel jpanel1 = new JPanel();
    jpanel1.setLayout(new FlowLayout());
    jpanel1.setBorder(BorderFactory.createTitledBorder(border, "單選"));
    JPanel jpanel2 = new JPanel();
    jpanel2.setLayout(new FlowLayout());
    jpanel2.setBorder(BorderFactory.createTitledBorder(border, "區間"));
    JPanel jpanel3 = new JPanel();
    jpanel3.setLayout(new FlowLayout());
    jpanel3.setBorder(BorderFactory.createTitledBorder(border, "多重選擇"));
    JPanel jpanel4 = new JPanel();
    jpanel4.setLayout(new FlowLayout());
    jpanel4.setBorder(BorderFactory.createTitledBorder(border, "水平排列"));
    JPanel jpanel5 = new JPanel();
    jpanel5.setLayout(new FlowLayout());
    jpanel5.setBorder(BorderFactory.createTitledBorder(border, "垂直排列"));

    final int length = 12;  // 選項項目個數
    String[] items = new String[length];

    for (int i=0; i < length; i++)    
      items[i] = "Item " + i;

    JList jlist1 = new JList(items);
    // 設定檢視清單物件中最多可顯示的項目列數（row）
    jlist1.setVisibleRowCount(4);
    // 單選
    jlist1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    // 捲軸
    JScrollPane jscrollpane1 = new JScrollPane(jlist1);
    jpanel1.add(jscrollpane1);

    JList jlist2 = new JList(items);
    // 設定檢視清單物件中最多可顯示的項目列數（row）
    jlist2.setVisibleRowCount(4);
    // 僅可選擇單一區間（Interval），但此區間內可選擇多個項目
    jlist2.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    // 設定選擇區間（Interval）
    jlist2.setSelectionInterval(3, 6);
    // 捲軸
    JScrollPane jscrollpane2 = new JScrollPane(jlist2);
    jpanel2.add(jscrollpane2);
    
    JList jlist3 = new JList(items);
    // 設定檢視清單物件中最多可顯示的項目列數（row）
    jlist3.setVisibleRowCount(4);
    // 允許多重選擇
    jlist3.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    // 設定選擇區間（Interval）
    jlist3.setSelectionInterval(2, 4);
    // 新增選擇區間（Interval）
    jlist3.addSelectionInterval(7, 10);
    // 捲軸
    JScrollPane jscrollpane3 = new JScrollPane(jlist3);
    jpanel3.add(jscrollpane3);
 
    JList jlist4 = new JList(items);
    // 設定檢視清單物件中最多可顯示的項目列數（row）
    jlist4.setVisibleRowCount(4);
    // 僅可選擇單一區間（Interval），但此區間內可選擇多個項目
    jlist4.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    // 先以水平排列項目
    jlist4.setLayoutOrientation(JList.HORIZONTAL_WRAP);
    // 設定選擇區間（Interval）
    jlist4.setSelectionInterval(3, 6);
    // 捲軸
    JScrollPane jscrollpane4 = new JScrollPane(jlist4);
    jpanel4.add(jscrollpane4);
    
    JList jlist5 = new JList(items);
    // 設定檢視清單物件中最多可顯示的項目列數（row）
    jlist5.setVisibleRowCount(4);
    // 允許多重選擇
    jlist5.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    // 先以垂直排列項目
    jlist5.setLayoutOrientation(JList.VERTICAL_WRAP);
    // 設定選擇區間（Interval）
    jlist5.setSelectionInterval(2, 4);
    // 新增選擇區間（Interval）
    jlist5.addSelectionInterval(7, 10);
    // 捲軸
    JScrollPane jscrollpane5 = new JScrollPane(jlist5);
    jpanel5.add(jscrollpane5);
    
    this.add(jpanel1);
    this.add(jpanel2);
    this.add(jpanel3);
    this.add(jpanel4);
    this.add(jpanel5);

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    this.validate();
    this.setSize(new Dimension(340, 290));
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });    
  }
}
