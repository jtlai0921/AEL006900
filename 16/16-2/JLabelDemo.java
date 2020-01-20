import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class JLabelDemo extends javax.swing.JFrame {

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

    new JLabelDemo();
  }

  // 建構函式
  public JLabelDemo() {
    super("JLabel Demo");

    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 設定視窗圖示
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // 定義 Layout Manager 為 BorderLayout
    this.setLayout(new BorderLayout());

    // 建立含有標題之標題邊框並以凹下蝕刻邊框為邊框
    Border border = BorderFactory.createTitledBorder(
      new EtchedBorder(EtchedBorder.LOWERED), "Label For", 
      TitledBorder.LEFT, TitledBorder.TOP); 

    JLabel[] jlabel = new JLabel[2];
    JTextField[] jTextField = new JTextField[2];
    
    jTextField[0] = new JTextField();
    jTextField[1] = new JTextField();
    
    // 純文字標籤
    jlabel[0] = new JLabel("ID:");
    // 以字元設定標籤的按鍵助記碼
    jlabel[0].setDisplayedMnemonic('I');
    // 設定標籤所屬之物件
    jlabel[0].setLabelFor(jTextField[0]);

    // 純文字標籤
    jlabel[1] = new JLabel("Name:");
    // 以字元設定標籤的按鍵助記碼
    jlabel[1].setDisplayedMnemonic('N');
    // 設定標籤所屬之物件
    jlabel[1].setLabelFor(jTextField[1]);
    
    JPanel jpanel = new JPanel();
    // 定義Layout Manager為GroupLayout
    GroupLayout layout = new GroupLayout(jpanel);
    jpanel.setLayout(layout);
    // 設定邊框樣式
    jpanel.setBorder(border);

    // 設定水平群組
    layout.setHorizontalGroup(
      // 建立循序群組
      layout.createSequentialGroup() 
        .addContainerGap()
        // 加入群組至循序群組中
        // 建立平行群組並沿著垂直方向物件之前沿對齊
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jlabel[0])
          .addComponent(jlabel[1]))
        // 加入最佳間隔至循序群組中  
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        // 加入群組至循序群組中
        // 建立平行群組並沿著垂直方向物件之後沿對齊
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
          .addComponent(jTextField[0], javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
          .addComponent(jTextField[1], javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE))
        .addContainerGap()
    );

    // 設定垂直群組
    layout.setVerticalGroup(
      // 建立循序群組
      layout.createSequentialGroup() 
        .addContainerGap()
        // 加入群組至循序群組中
        // 建立平行群組並沿著物件之基準線對齊 
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jlabel[0])
          .addComponent(jTextField[0], javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE))
        // 加入最佳間隔至循序群組中  
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        // 加入群組至循序群組中
        // 建立平行群組並沿著物件之基準線對齊 
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jlabel[1])
          .addComponent(jTextField[1], javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE))
        .addContainerGap()
    );
    
    this.add(jpanel, BorderLayout.CENTER);

    // 設定視窗的大小
    this.setSize(250, 150);

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
}