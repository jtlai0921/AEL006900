import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class DefaultListModelDemo extends JFrame {

  JList jlist;
  DefaultListModel listModel;
  int count = 12;  // 選項項目個數

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

    new DefaultListModelDemo();
  }

  // 建構函式
  public DefaultListModelDemo() {
    super("DefaultListModel Demo");

    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 設定視窗圖示
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // Linked-list模型
    listModel = new DefaultListModel();

    // 將物件加至Linked-list中
    for (int i=0; i < count; i++)    
      listModel.addElement("Item " + i);

    // 以DefaultListModel建立JList類別之（MVC）Model
    jlist = new JList(listModel);
    // 捲軸
    JScrollPane jscrollpane1 = new JScrollPane(jlist);

    // 設定 Etched Border (Demo only)
    Border border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.white, new Color(142, 142, 142)); 

    JPanel jpanel1 = new JPanel();
    jpanel1.setLayout(new BorderLayout());
    jpanel1.setBorder(BorderFactory.createTitledBorder(border, "選項項目"));
    jpanel1.add(jscrollpane1, BorderLayout.CENTER);

    JButton btnAdd = new JButton("Add");
    btnAdd.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        listModel.addElement("Item " + count);
        count++;
      }
    });
    
    JButton btnRemove = new JButton("Remove");
    btnRemove.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (listModel.getSize() > 0) {
          // 取得被選取項目的索引值
          int index = jlist.getSelectedIndex();
          
          // 移除Linked-list中第index個物件
          listModel.remove(index);
  
          // 取得Linked-list的大小
          int size = listModel.getSize();
  
          if (index == listModel.getSize()) {
            index--;
          }
  
          // 選取第index個選項項目
          jlist.setSelectedIndex(index);
          jlist.ensureIndexIsVisible(index);
          
          count--;
        }
      }
    });

    JPanel jpanel2 = new JPanel();
    
    GroupLayout jpanel2Layout = new GroupLayout(jpanel2);
    jpanel2.setLayout(jpanel2Layout);
    jpanel2Layout.setHorizontalGroup(
      jpanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jpanel2Layout.createSequentialGroup()
        .addGap(20, 20, 20)
        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(20, 20, 20)
        .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(50, Short.MAX_VALUE))
    );
    jpanel2Layout.setVerticalGroup(
      jpanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jpanel2Layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jpanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
          .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addContainerGap())
    );

    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(jpanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
      .addComponent(jpanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
        .addComponent(jpanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jpanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
    );

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    this.validate();
    this.setSize(new Dimension(250, 250));
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });    
  }
}
