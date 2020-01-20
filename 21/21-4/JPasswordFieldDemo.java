import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class JPasswordFieldDemo extends javax.swing.JFrame {
   
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

    new JPasswordFieldDemo();
  }

  // 建構函式
  public JPasswordFieldDemo() {
    super("JPasswordField Demo");

    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 設定視窗圖示
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // 設定密碼欄位邊框之屬性值
    UIManager.put("PasswordField.border", new javax.swing.border.EtchedBorder(EtchedBorder.LOWERED));
    
    // 設定脫字符號閃爍的速率之屬性值
    UIManager.put("PasswordField.caretBlinkRate", 5);
    
    // 設定脫字符號的前景顏色之屬性值
    UIManager.put("PasswordField.caretForeground", Color.RED);
    
    // 設定密碼標籤字型之屬性值
    UIManager.put("PasswordField.font", new Font("dialog", Font.BOLD, 12));
    
    // 設定密碼欄位前景之屬性值
    UIManager.put("PasswordField.foreground", Color.PINK);
    
    // 設定被選取時的背景顏色之屬性值
    UIManager.put("PasswordField.selectionBackground", Color.GREEN);
    
    // 設定被選取時的前景顏色之屬性值
    UIManager.put("PasswordField.selectionForeground", Color.CYAN);
    
    // 取得密碼欄位Pluggable Look and Feel屬性值
    System.out.println("JPasswordField Look and Feel: " + UIManager.getString("PasswordFieldUI"));
    
    // 定義Layout Manager 為 BorderLayout
    this.setLayout(new BorderLayout());

    JPanel jpanel = new JPanel();
    // 定義JPanel之Layout Manager為SpringLayout
    jpanel.setLayout(new SpringLayout());

    // 取得JPanel之Layout Manager
    SpringLayout layout = (SpringLayout)jpanel.getLayout();

    JLabel[] jlabel = new JLabel[2];
    jlabel[0] = new JLabel("Password 1:");
    jlabel[0].setDisplayedMnemonic('1');
    jlabel[1] = new JLabel("Password 2:");
    jlabel[1].setDisplayedMnemonic('2');
  
    JPasswordField[] jpasswordfield = new JPasswordField[2];

    for (int i = 0; i < jpasswordfield.length; i++) {
      // 設定密碼欄位的行數
      jpasswordfield[i] = new JPasswordField(10);

      // 設定標籤所屬的元件，代表當執行標籤易記碼（Mnemonic Key）時
      // 所取得輸入焦點（Focus）的元件。
      jlabel[i].setLabelFor(jpasswordfield[i]);

      jpanel.add(jlabel[i]);
      jpanel.add(jpasswordfield[i]);
    }

    // 設定密碼欄位的顯示文字
    jpasswordfield[0].setText("password 1");
    // 設定回應字元為*
    jpasswordfield[0].setEchoChar('*');

    // 設定密碼欄位的顯示文字
    jpasswordfield[1].setText("password 2");
    // 設定回應字元為@
    jpasswordfield[1].setEchoChar('@');

    Spring x = Spring.constant(5);
    Spring y = Spring.constant(5);

    Spring maxEast = layout.getConstraint(SpringLayout.EAST, jlabel[0]);

    for (int i = 1; i < jpasswordfield.length; i++) {
      maxEast = Spring.max(maxEast, layout.getConstraint(SpringLayout.EAST, jlabel[i]));
    }

    SpringLayout.Constraints lastConsL = null;
    SpringLayout.Constraints lastConsR = null;
    Spring parentWidth = layout.getConstraint(SpringLayout.EAST, jpanel);
    Spring rWidth = null;
    Spring maxHeightSpring = null;
    Spring rX = Spring.sum(maxEast, Spring.constant(5)); 
    Spring negRX = Spring.minus(rX); 

    for (int i = 0; i < jpasswordfield.length; i++) {
      SpringLayout.Constraints consL = layout.getConstraints(jlabel[i]);
      SpringLayout.Constraints consR = layout.getConstraints(jpasswordfield[i]);

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
    this.setSize(new Dimension(250, 100));
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }
}
