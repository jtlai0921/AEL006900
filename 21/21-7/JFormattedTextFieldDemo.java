import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.text.*;
import java.text.NumberFormat.*;

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
    
    JLabel[] jlabel = new JLabel[2];
    jlabel[0] = new JLabel("Currency:");
    jlabel[0].setDisplayedMnemonic('C');
    jlabel[1] = new JLabel("Percent:");
    jlabel[1].setDisplayedMnemonic('P');

    JFormattedTextField[] jformattedtf = new JFormattedTextField[2];

    // 預設格式
    NumberFormat cdefaultFormat = NumberFormat.getCurrencyInstance();
    // 設定小數點位數為2位
    cdefaultFormat.setMinimumFractionDigits(2);
    
    // 顯示格式
    NumberFormat cdisplayFormat = NumberFormat.getCurrencyInstance();
    // 設定小數點位數為2位
    cdisplayFormat.setMinimumFractionDigits(2);
    
    // 編輯格式
    NumberFormat ceditFormat = NumberFormat.getCurrencyInstance();
    // 設定小數點位數為3位
    cdisplayFormat.setMinimumFractionDigits(3);
    
    // 以DefaultFormatterFactory建立JFormattedTextField
    jformattedtf[0] = new JFormattedTextField(
      new DefaultFormatterFactory(
        new NumberFormatter(cdefaultFormat), 
        new NumberFormatter(cdisplayFormat), 
        new NumberFormatter(ceditFormat)));
    jformattedtf[0].setValue(new java.lang.Float(9003510.205));

    // 預設格式
    NumberFormat pdefaultFormat = NumberFormat.getPercentInstance();
    // 設定小數點位數為2位
    pdefaultFormat.setMinimumFractionDigits(2);
    
    // 顯示格式
    NumberFormat pdisplayFormat = NumberFormat.getPercentInstance();
    // 設定小數點位數為2位
    pdisplayFormat.setMinimumFractionDigits(2);
    
    // 編輯格式
    NumberFormat peditFormat = NumberFormat.getNumberInstance();
    // 設定小數點位數為3位
    peditFormat.setMinimumFractionDigits(3);

    // 編輯格式
    NumberFormatter peditFormatter = new NumberFormatter(peditFormat) {
      // 將字串轉換為任意Object物件
      public Object stringToValue(String text) throws ParseException {
        Number number = (Number) super.stringToValue(text);

        if (number != null) 
          number = new Double(number.doubleValue() / 100.0);

        return number;
      }
      
      // 將任意Object物件轉換為字串
      public String valueToString(Object value) throws ParseException {
        Number number = (Number) value;

        if (number != null) 
          number = new Double(number.doubleValue() * 100.0);

        return super.valueToString(number);
      }
    };

    // 建立預設格式DefaultFormatterFactory
    DefaultFormatterFactory dff = new DefaultFormatterFactory(
      new NumberFormatter(pdefaultFormat), 
      new NumberFormatter(pdisplayFormat), 
      peditFormatter);
    
    // Percent
    jformattedtf[1] = new JFormattedTextField(dff);
    jformattedtf[1].setValue(new Double(0.5));

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
    this.setSize(new Dimension(250, 100));
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }
}
