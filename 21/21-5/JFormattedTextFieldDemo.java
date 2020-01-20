import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.text.*;
import java.text.NumberFormat.*;
import java.net.*;

public class JFormattedTextFieldDemo extends javax.swing.JFrame {
   
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

    new JFormattedTextFieldDemo();
  }

  // 建構函式
  public JFormattedTextFieldDemo() {
    super("JFormattedTextField Demo");

    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 設定視窗圖示
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // 定義Layout Manager 為 BorderLayout
    this.setLayout(new BorderLayout());

    JPanel jpanel = new JPanel();
    // 定義JPanel之Layout Manager為SpringLayout
    jpanel.setLayout(new SpringLayout());

    // 取得JPanel之Layout Manager
    SpringLayout layout = (SpringLayout)jpanel.getLayout();
    
    JLabel[] jlabel = new JLabel[6];
    jlabel[0] = new JLabel("Integer:");
    jlabel[0].setDisplayedMnemonic('I');
    jlabel[1] = new JLabel("Float:");
    jlabel[1].setDisplayedMnemonic('F');
    jlabel[2] = new JLabel("Currency:");
    jlabel[2].setDisplayedMnemonic('C');
    jlabel[3] = new JLabel("Percent:");
    jlabel[3].setDisplayedMnemonic('P');
    jlabel[4] = new JLabel("Date:");
    jlabel[4].setDisplayedMnemonic('D');
    jlabel[5] = new JLabel("URL:");
    jlabel[5].setDisplayedMnemonic('U');

    JFormattedTextField[] jformattedtf = new JFormattedTextField[6];

    // Integer
    jformattedtf[0] = new JFormattedTextField(new Integer(570820));
    
    // Float
    jformattedtf[1] = new JFormattedTextField();
    // 設定JFormattedTextField的內容
    jformattedtf[1].setValue(new Float(90.05102));
    
    // Currency
    jformattedtf[2] = new JFormattedTextField(java.text.NumberFormat.getCurrencyInstance());
    jformattedtf[2].setValue(new java.lang.Float(9003510.205));
    
    // Percent
    jformattedtf[3] = new JFormattedTextField(java.text.NumberFormat.getPercentInstance());
    jformattedtf[3].setValue(new java.lang.Float(0.5));

    // Date
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd hh:mm aaa");
    String date = formatter.format(new java.util.Date());
    jformattedtf[4] = new JFormattedTextField(date);
    
    // URL
    java.net.URL url = null;
    
    try {
       url = new URL("http://java.sun.com/");
    } 
    catch (MalformedURLException ex) { }

    jformattedtf[5] = new JFormattedTextField(url);


    for (int i = 0; i < jformattedtf.length; i++) {
      // 設定標籤所屬的元件，代表當執行標籤易記碼（Mnemonic Key）時
      // 所取得輸入焦點（Focus）的元件。
      jlabel[i].setLabelFor(jformattedtf[i]);

      jpanel.add(jlabel[i]);
      jpanel.add(jformattedtf[i]);
    }

    Spring x = Spring.constant(5);
    Spring y = Spring.constant(5);

    Spring maxEast = layout.getConstraint(SpringLayout.EAST, jlabel[0]);

    for (int i = 1; i < jformattedtf.length; i++) {
      maxEast = Spring.max(maxEast, layout.getConstraint(SpringLayout.EAST, jlabel[i]));
    }

    SpringLayout.Constraints lastConsL = null;
    SpringLayout.Constraints lastConsR = null;
    Spring parentWidth = layout.getConstraint(SpringLayout.EAST, jpanel);
    Spring rWidth = null;
    Spring maxHeightSpring = null;
    Spring rX = Spring.sum(maxEast, Spring.constant(5)); 
    Spring negRX = Spring.minus(rX); 

    for (int i = 0; i < jformattedtf.length; i++) {
      SpringLayout.Constraints consL = layout.getConstraints(jlabel[i]);
      SpringLayout.Constraints consR = layout.getConstraints(jformattedtf[i]);

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
    this.setSize(new Dimension(250, 210));
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }
}
