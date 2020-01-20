import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class JComboBoxDemo extends JFrame {

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

    new JComboBoxDemo();
  }

  // 建構函式
  public JComboBoxDemo() {
    super("JComboBox Demo");

    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 設定視窗圖示
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // 設定JComboBox邊框之屬性值
    UIManager.put("ComboBox.border", new javax.swing.border.EtchedBorder(EtchedBorder.LOWERED));
    
    // 設定JComboBox前景之屬性值
    UIManager.put("ComboBox.foreground", Color.PINK);
    
    // 設定JComboBox文字標籤字型之屬性值
    UIManager.put("ComboBox.font", new Font("dialog", Font.BOLD, 12));
    
    // 取得Pluggable Look and Feel屬性值
    System.out.println("JComboBox Look and Feel: " + UIManager.getString("ComboBoxUI"));
   

    final int length = 5;  // 選項項目個數

    // 設定 Etched Border (Test only)
    Border border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.white, new Color(142, 142, 142)); 

    JPanel jpanel1 = new JPanel();
    jpanel1.setLayout(new FlowLayout());
    jpanel1.setBorder(BorderFactory.createTitledBorder(border, "Normal"));
    JPanel jpanel2 = new JPanel();
    jpanel2.setLayout(new FlowLayout());
    jpanel2.setBorder(BorderFactory.createTitledBorder(border, "Editable"));
    JPanel jpanel3 = new JPanel();
    jpanel3.setLayout(new FlowLayout());
    jpanel3.setBorder(BorderFactory.createTitledBorder(border, "Disable"));

    JComboBox jcombobox1 = new JComboBox();

    for (int i=0; i < length; i++)    
      jcombobox1.addItem("Item " + i);

    // 選取第anIndex個選項項目
    jcombobox1.setSelectedIndex(3);
    // 設定下拉JComboBox類別時，最多可顯示的項目列數
    jcombobox1.setMaximumRowCount(3);
    jpanel1.add(jcombobox1);

    JComboBox jcombobox2 = new JComboBox();

    for (int i=0; i < length; i++)    
      jcombobox2.addItem("Item " + i);
    
    // 設定JComboBox類別是否可編輯（Editable）
    jcombobox2.setEditable(true);
    // 設定下拉JComboBox類別時，最多可顯示的項目列數
    jcombobox2.setMaximumRowCount(3);
    jpanel2.add(jcombobox2);

    JComboBox jcombobox3 = new JComboBox();

    for (int i=0; i < length; i++)    
      jcombobox3.addItem("Item " + i);
    
    // 設定JComboBox類別是否回應互動（Enabled）
    jcombobox3.setEnabled(false);
    // 選取第anIndex個選項項目
    jcombobox3.setSelectedIndex(2);
    jpanel3.add(jcombobox3);
    
    // 定義 Layout Manager 為 FlowLayout
    this.setLayout(new FlowLayout());

    this.add(jpanel1);
    this.add(jpanel2);
    this.add(jpanel3);

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    this.validate();
    this.setSize(new Dimension(380, 120));
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });    
  }
}
