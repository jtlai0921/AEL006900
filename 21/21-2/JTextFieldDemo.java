import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class JTextFieldDemo extends javax.swing.JFrame {
   
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

    new JTextFieldDemo();
  }

  // 建構函式
  public JTextFieldDemo() {
    super("JTextField Demo");

    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 設定視窗圖示
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // 設定文字欄位邊框之屬性值
    UIManager.put("TextField.border", new javax.swing.border.EtchedBorder(EtchedBorder.LOWERED));
    
    // 設定脫字符號閃爍的速率之屬性值
    UIManager.put("TextField.caretBlinkRate", 5);
    
    // 設定脫字符號的前景顏色之屬性值
    UIManager.put("TextField.caretForeground", Color.RED);
    
    // 設定文字標籤字型之屬性值
    UIManager.put("TextField.font", new Font("dialog", Font.BOLD, 12));
    
    // 設定文字欄位前景之屬性值
    UIManager.put("TextField.foreground", Color.PINK);
    
    // 設定被選取時的背景顏色之屬性值
    UIManager.put("TextField.selectionBackground", Color.GREEN);
    
    // 設定被選取時的前景顏色之屬性值
    UIManager.put("TextField.selectionForeground", Color.CYAN);
    
    // 取得文字欄位Pluggable Look and Feel屬性值
    System.out.println("JTextField Look and Feel: " + UIManager.getString("TextFieldUI"));
    
    // 定義Layout Manager 為 BorderLayout
    this.setLayout(new BorderLayout());

    JPanel jpanel = new JPanel();
    // 定義JPanel之Layout Manager為SpringLayout
    jpanel.setLayout(new SpringLayout());

    // 取得JPanel之Layout Manager
    SpringLayout layout = (SpringLayout)jpanel.getLayout();

    JLabel[] jlabel = new JLabel[4];
    jlabel[0] = new JLabel("Name:");
    jlabel[0].setDisplayedMnemonic('N');
    jlabel[1] = new JLabel("Birth:");
    jlabel[1].setDisplayedMnemonic('B');
    jlabel[2] = new JLabel("Tel.:");
    jlabel[2].setDisplayedMnemonic('T');
    jlabel[3] = new JLabel("E-Mail:");
    jlabel[3].setDisplayedMnemonic('E');

    JTextField[] jtextfield = new JTextField[4];

    for (int i = 0; i < jtextfield.length; i++) {
      jtextfield[i] = new JTextField(10);

      // 設定標籤所屬的元件，代表當執行標籤易記碼（Mnemonic Key）時
      // 所取得輸入焦點（Focus）的元件。
      jlabel[i].setLabelFor(jtextfield[i]);

      jpanel.add(jlabel[i]);
      jpanel.add(jtextfield[i]);
    }

    // 設定文字欄位的顯示文字
    jtextfield[0].setText("Athena");
    // 設定文字欄位的行數
    // jtextfield[0].setColumns(8);
    // 設定背景顏色
    jtextfield[0].setBackground(Color.PINK);
    // 設定前景顏色
    jtextfield[0].setForeground(Color.CYAN);
    // 選取所有的文字內容
    jtextfield[0].selectAll();

    // 設定文字欄位的顯示文字
    jtextfield[1].setText("2001/05/10");
    // 設定文字欄位的水平對齊方式
    jtextfield[1].setHorizontalAlignment(SwingConstants.RIGHT);

    // 設定文字欄位的顯示文字
    jtextfield[2].setText("1234567890");
    // 設定字型
    jtextfield[2].setFont(new Font("dialog", Font.PLAIN | Font.ITALIC, 12));
    // 設定游標目前的位置
    jtextfield[2].setCaretPosition(5);

    // 設定文字欄位的顯示文字
    jtextfield[3].setText("athena@yahoo.com.tw");
    // 設定被選取文字內容的顏色
    jtextfield[3].setSelectedTextColor(Color.RED);
    // 設定被選取文字內容的啟始位置
    jtextfield[3].setSelectionStart(3);
    // 設定被選取文字內容的結束位置
    jtextfield[3].setSelectionEnd(10);

    Spring x = Spring.constant(5);
    Spring y = Spring.constant(5);

    Spring maxEast = layout.getConstraint(SpringLayout.EAST, jlabel[0]);

    for (int i = 1; i < jtextfield.length; i++) {
      maxEast = Spring.max(maxEast, layout.getConstraint(SpringLayout.EAST, jlabel[i]));
    }

    SpringLayout.Constraints lastConsL = null;
    SpringLayout.Constraints lastConsR = null;
    Spring parentWidth = layout.getConstraint(SpringLayout.EAST, jpanel);
    Spring rWidth = null;
    Spring maxHeightSpring = null;
    Spring rX = Spring.sum(maxEast, Spring.constant(5)); 
    Spring negRX = Spring.minus(rX); 

    for (int i = 0; i < jtextfield.length; i++) {
      SpringLayout.Constraints consL = layout.getConstraints(jlabel[i]);
      SpringLayout.Constraints consR = layout.getConstraints(jtextfield[i]);

      consL.setX(x);
      consR.setX(rX);

      rWidth = consR.getWidth(); 
      
      consR.setWidth(Spring.sum(Spring.sum(parentWidth, negRX), Spring.constant(-5)));

      if (i == 0) {
        consL.setY(y);
        consR.setY(y);
        maxHeightSpring = Spring.sum(y, Spring.max(consL.getHeight(), consR.getHeight()));
      } 
      else {  
        Spring y1 = Spring.sum(Spring.max(lastConsL.getConstraint(SpringLayout.SOUTH), lastConsR.getConstraint(SpringLayout.SOUTH)), Spring.constant(5));

        consL.setY(y1);
        consR.setY(y1);
        maxHeightSpring = Spring.sum(Spring.constant(5), Spring.sum(maxHeightSpring, Spring.max(consL.getHeight(), consR.getHeight())));
      }
      lastConsL = consL;
      lastConsR = consR;
    }  
    
    SpringLayout.Constraints consParent = layout.getConstraints(jpanel);
    consParent.setConstraint(SpringLayout.EAST, Spring.sum(rX, Spring.sum(rWidth, Spring.constant(5))));
    consParent.setConstraint(SpringLayout.SOUTH, Spring.sum(maxHeightSpring, Spring.constant(5)));

    this.add(jpanel, BorderLayout.CENTER);

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    this.validate();
    this.setSize(new Dimension(250, 150));
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }
}
