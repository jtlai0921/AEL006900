import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class JRadioButtonDemo extends javax.swing.JFrame implements ItemListener {

  JRadioButton jradiobutton[] = new JRadioButton[7];
  JLabel jlabel;

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

    new JRadioButtonDemo();
  }

  // 建構函式
  public JRadioButtonDemo() {
    super("JRadioButton Demo");

    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 設定視窗圖示
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // 設定選項按鈕邊框之屬性值
    UIManager.put("RadioButton.border", new javax.swing.border.BevelBorder(EtchedBorder.RAISED));
    
    // 設定選項按鈕其文字標籤與按鈕邊框間的內嵌距離之屬性值
    UIManager.put("RadioButton.margin", new Insets(2, 2, 2, 2));
    
    // 設定選項按鈕背景顏色之屬性值
    UIManager.put("RadioButton.background", Color.PINK);
    
    // 設定選項按鈕文字標籤字型之屬性值
    UIManager.put("RadioButton.font", new Font("dialog", Font.BOLD, 12));
    
    // 取得選項按鈕Pluggable Look and Feel屬性值
    System.out.println("JRadioButton Look and Feel: " + UIManager.getString("RadioButtonUI"));
    
    // 定義 Layout Manager 為 BorderLayout
    this.setLayout(new BorderLayout());
    
    for (int i=0; i<5; i++) {
      if (i==0) 
        jradiobutton[i] = new JRadioButton("RadioButton " + i, true);
      else
        jradiobutton[i] = new JRadioButton("RadioButton " + i);

      // 註冊 ItemListener
      jradiobutton[i].addItemListener(this);
    }
    
    // 設定 Etched Border (Test only)
    Border border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.white, new Color(142, 142, 142)); 

    JPanel jpanel1 = new JPanel();
    jpanel1.setBorder(BorderFactory.createTitledBorder(border, "複選"));
    // 定義Layout Manager為GroupLayout
    GroupLayout layout = new GroupLayout(jpanel1);
    jpanel1.setLayout(layout);

    // 設定水平群組
    layout.setHorizontalGroup(
      // 建立循序群組
      layout.createSequentialGroup() 
        .addContainerGap()
        // 加入群組至循序群組中
        // 建立平行群組並沿著垂直方向物件之前沿對齊
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jradiobutton[0])
          .addComponent(jradiobutton[1])
          .addComponent(jradiobutton[2]))
        .addContainerGap()
    );

    // 設定垂直群組
    layout.setVerticalGroup(
      // 建立平行群組
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING) 
        // 加入循序群組至平行群組中
        .addGroup(layout.createSequentialGroup()
          .addContainerGap()
          .addComponent(jradiobutton[0])
          // 加入最佳間隔至群組中  
          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
          .addComponent(jradiobutton[1])
          // 加入最佳間隔至群組中  
          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
          .addComponent(jradiobutton[2])
        .addContainerGap())
    );      
    
    JPanel jpanel2 = new JPanel();
    // 定義Layout Manager為GroupLayout
    layout = new GroupLayout(jpanel2);
    jpanel2.setLayout(layout);

    // 設定水平群組
    layout.setHorizontalGroup(
      // 建立循序群組
      layout.createSequentialGroup() 
        .addContainerGap()
        // 加入群組至循序群組中
        // 建立平行群組並沿著垂直方向物件之前沿對齊
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jradiobutton[3])
          .addComponent(jradiobutton[4]))
        .addContainerGap()
    );

    // 設定垂直群組
    layout.setVerticalGroup(
      // 建立平行群組
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING) 
        // 加入循序群組至平行群組中
        .addGroup(layout.createSequentialGroup()
          .addContainerGap()
          .addComponent(jradiobutton[3])
          // 加入最佳間隔至群組中  
          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
          .addComponent(jradiobutton[4])
        .addContainerGap())
    );    

    // 建構群組
    ButtonGroup group1 = new ButtonGroup();
    group1.add(jradiobutton[3]);
    group1.add(jradiobutton[4]);    
    
    jradiobutton[5] = new JRadioButton("RadioButton 5", new ImageIcon(cl.getResource("images/greenball.gif")));
    // 設定標籤字串相對於圖像的水平位置
    jradiobutton[5].setHorizontalTextPosition(SwingConstants.RIGHT);
    // 設定標籤字串相對於圖像的垂直位置
    jradiobutton[5].setVerticalTextPosition(SwingConstants.CENTER);
    // 設定按下時的圖像
    jradiobutton[5].setSelectedIcon(new ImageIcon(cl.getResource("images/redball.gif")));
    // 註冊 ItemListener
    jradiobutton[5].addItemListener(this);
  
    jradiobutton[6] = new JRadioButton("RadioButton 6", new ImageIcon(cl.getResource("images/greenball.gif")));
    // 設定標籤字串相對於圖像的水平位置
    jradiobutton[6].setHorizontalTextPosition(SwingConstants.RIGHT);
    // 設定標籤字串相對於圖像的垂直位置
    jradiobutton[6].setVerticalTextPosition(SwingConstants.CENTER);
    // 設定按下時的圖像
    jradiobutton[6].setSelectedIcon(new ImageIcon(cl.getResource("images/redball.gif")));
    // 註冊 ItemListener
    jradiobutton[6].addItemListener(this);
      
    // 建構群組
    ButtonGroup group2 = new ButtonGroup();
    group2.add(jradiobutton[5]);
    group2.add(jradiobutton[6]);

    JPanel jpanel3 = new JPanel();
    // 定義Layout Manager為GroupLayout
    layout = new GroupLayout(jpanel3);
    jpanel3.setLayout(layout);

    // 設定水平群組
    layout.setHorizontalGroup(
      // 建立循序群組
      layout.createSequentialGroup() 
        .addContainerGap()
        // 加入群組至循序群組中
        // 建立平行群組並沿著垂直方向物件之前沿對齊
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jradiobutton[5])
          .addComponent(jradiobutton[6]))
        .addContainerGap()
    );

    // 設定垂直群組
    layout.setVerticalGroup(
      // 建立平行群組
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING) 
        // 加入循序群組至平行群組中
        .addGroup(layout.createSequentialGroup()
          .addContainerGap()
          .addComponent(jradiobutton[5])
          // 加入最佳間隔至群組中  
          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
          .addComponent(jradiobutton[6])
        .addContainerGap())
    );    

    JPanel jpanel4 = new JPanel();
    jpanel4.setLayout(new GridLayout(1, 2));
    jpanel4.setBorder(BorderFactory.createTitledBorder(border, "單選"));

    jpanel4.add(jpanel2);
    jpanel4.add(jpanel3);

    JPanel jpanel5 = new JPanel();
    // 設定物件之邊框樣式
    jpanel5.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
    jpanel5.setLayout(new BorderLayout());

    jlabel = new JLabel("JRadioButton Event");
    jpanel5.add(jlabel, BorderLayout.WEST);
    
    this.add(jpanel1, BorderLayout.NORTH);
    this.add(jpanel4, BorderLayout.CENTER);
    this.add(jpanel5, BorderLayout.SOUTH);
    
    // 設定視窗的大小
    this.setSize(280, 280);

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

  public void itemStateChanged(ItemEvent e) {
    String source = "";

    for (int i=0; i < jradiobutton.length; i++) {
      // 判斷核取方塊是否被選取
      if(jradiobutton[i].isSelected()) 
        source += " " + i ;
    }

    jlabel.setText("Select JRadioButton " + source);
  }
}
