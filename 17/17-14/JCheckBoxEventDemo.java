import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

// Java Swing Event
import javax.swing.event.*;
 
public class JCheckBoxEventDemo extends javax.swing.JFrame implements ChangeListener {

  JCheckBox jcheckbox[] = new JCheckBox[7];
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

    new JCheckBoxEventDemo();
  }

  // 建構函式
  public JCheckBoxEventDemo() {
    super("JCheckBox Demo");

    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 設定視窗圖示
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // 定義 Layout Manager 為 BorderLayout
    this.setLayout(new BorderLayout());

    for (int i=0; i<5; i++) {
      if (i==0) 
        jcheckbox[i] = new JCheckBox("CheckBox " + i, true);
      else
        jcheckbox[i] = new JCheckBox("CheckBox " + i);

      // 註冊 ChangeListener
      jcheckbox[i].addChangeListener(this);
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
          .addComponent(jcheckbox[0])
          .addComponent(jcheckbox[1])
          .addComponent(jcheckbox[2]))
        .addContainerGap()
    );

    // 設定垂直群組
    layout.setVerticalGroup(
      // 建立平行群組
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING) 
        // 加入循序群組至平行群組中
        .addGroup(layout.createSequentialGroup()
          .addContainerGap()
          .addComponent(jcheckbox[0])
          // 加入最佳間隔至群組中  
          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
          .addComponent(jcheckbox[1])
          // 加入最佳間隔至群組中  
          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
          .addComponent(jcheckbox[2])
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
          .addComponent(jcheckbox[3])
          .addComponent(jcheckbox[4]))
        .addContainerGap()
    );

    // 設定垂直群組
    layout.setVerticalGroup(
      // 建立平行群組
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING) 
        // 加入循序群組至平行群組中
        .addGroup(layout.createSequentialGroup()
          .addContainerGap()
          .addComponent(jcheckbox[3])
          // 加入最佳間隔至群組中  
          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
          .addComponent(jcheckbox[4])
        .addContainerGap())
    );    

    // 建構群組
    ButtonGroup group1 = new ButtonGroup();
    group1.add(jcheckbox[3]);
    group1.add(jcheckbox[4]);

    jcheckbox[5] = new JCheckBox("CheckBox 5", new ImageIcon(cl.getResource("images/check.gif")));
    // 設定標籤字串相對於圖像的水平位置
    jcheckbox[5].setHorizontalTextPosition(SwingConstants.RIGHT);
    // 設定標籤字串相對於圖像的垂直位置
    jcheckbox[5].setVerticalTextPosition(SwingConstants.CENTER);
    // 設定按下時的圖像
    jcheckbox[5].setSelectedIcon(new ImageIcon(cl.getResource("images/checkselected.gif")));
    // 註冊 ChangeListener
    jcheckbox[5].addChangeListener(this);
  
    jcheckbox[6] = new JCheckBox("CheckBox 6", new ImageIcon(cl.getResource("images/brownarrow.gif")));
    // 設定標籤字串相對於圖像的水平位置
    jcheckbox[6].setHorizontalTextPosition(SwingConstants.RIGHT);
    // 設定標籤字串相對於圖像的垂直位置
    jcheckbox[6].setVerticalTextPosition(SwingConstants.CENTER);
    // 設定是否當滑鼠移過上方時的效果
    jcheckbox[6].setRolloverEnabled(true);
    // 設定滑鼠移過指令鈕上方時的圖像
    jcheckbox[6].setRolloverIcon(new ImageIcon(cl.getResource("images/greenarrow.gif")));
    // 設定滑鼠移過指令鈕上方且被選取（Selected）時的圖像
    jcheckbox[6].setRolloverSelectedIcon(new ImageIcon(cl.getResource("images/greenarrow.gif"))); 
    // 設定按下時的圖像
    jcheckbox[6].setSelectedIcon(new ImageIcon(cl.getResource("images/bluearrow.gif")));
    // 註冊 ChangeListener
    jcheckbox[6].addChangeListener(this);
      
    // 建構群組
    ButtonGroup group2 = new ButtonGroup();
    group2.add(jcheckbox[5]);
    group2.add(jcheckbox[6]);

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
          .addComponent(jcheckbox[5])
          .addComponent(jcheckbox[6]))
        .addContainerGap()
    );

    // 設定垂直群組
    layout.setVerticalGroup(
      // 建立平行群組
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING) 
        // 加入循序群組至平行群組中
        .addGroup(layout.createSequentialGroup()
          .addContainerGap()
          .addComponent(jcheckbox[5])
          // 加入最佳間隔至群組中  
          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
          .addComponent(jcheckbox[6])
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

    jlabel = new JLabel("JCheckBox Event");
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

  // 當物件狀態改變時
  public void stateChanged(ChangeEvent e) {
    String source = "";

    for (int i=0; i < jcheckbox.length; i++) {
      if (e.getSource().equals(jcheckbox[i]))
        source = "Roll over " + jcheckbox[i].getText();
    }

    jlabel.setText(source);
  }
}
