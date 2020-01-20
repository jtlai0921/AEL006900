import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.event.*;

public class JTextAreaDemo extends JFrame {
  
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

    new JTextAreaDemo();
  }

  // 建構函式
  public JTextAreaDemo() {
    super("JTextArea Demo");

    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 設定視窗圖示
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // 設定文字區域邊框之屬性值
    UIManager.put("TextArea.border", new javax.swing.border.EtchedBorder(EtchedBorder.LOWERED));
    
    // 設定脫字符號閃爍的速率之屬性值
    UIManager.put("TextArea.caretBlinkRate", 5);
    
    // 設定脫字符號的前景顏色之屬性值
    UIManager.put("TextArea.caretForeground", Color.RED);
    
    // 設定文字標籤字型之屬性值
    UIManager.put("TextArea.font", new Font("dialog", Font.BOLD, 12));
    
    // 設定可格式化的文字區域前景之屬性值
    UIManager.put("TextArea.foreground", Color.PINK);
    
    // 設定被選取時的背景顏色之屬性值
    UIManager.put("TextArea.selectionBackground", Color.GREEN);
    
    // 設定被選取時的前景顏色之屬性值
    UIManager.put("TextArea.selectionForeground", Color.CYAN);
    
    // 取得文字區域Pluggable Look and Feel屬性值
    System.out.println("JTextArea Look and Feel: " + UIManager.getString("TextAreaUI"));

    // 定義Layout Manager 為 GridLayout
    this.setLayout(new GridLayout(1, 2));
    
    JTextArea jtextarea1 = new JTextArea();
    // 設定自動換行的規則
    jtextarea1.setWrapStyleWord(true);
    // 設定JTextArea當文字超過行寬時，是否自動換行。
    jtextarea1.setLineWrap(true);
    jtextarea1.setEditable(true);
    // 設定文字字型
    jtextarea1.setFont(new Font("Courier New", Font.PLAIN, 11));
    
    // 設定捲軸面板
    JScrollPane jscrollpane1 = new JScrollPane(jtextarea1);
    // 垂直捲軸：當超過JTextArea列數時才自動顯示垂直捲軸
    jscrollpane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    // 水平捲軸：當超過JTextArea行數時才自動顯示水平捲軸
    jscrollpane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    
    JTextArea jtextarea2 = new JTextArea();
    // 設定自動換行的規則
    jtextarea2.setWrapStyleWord(false);
    // 設定JTextArea當文字超過行寬時，是否自動換行。
    jtextarea2.setLineWrap(false);
    jtextarea2.setEditable(true);
    // 設定文字字型
    jtextarea2.setFont(new Font("Courier New", Font.PLAIN, 11));
    
    // 設定捲軸面板
    JScrollPane jscrollpane2 = new JScrollPane(jtextarea2);
    // 垂直捲軸：當超過JTextArea列數時才自動顯示垂直捲軸
    jscrollpane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    // 水平捲軸：當超過JTextArea行數時才自動顯示水平捲軸
    jscrollpane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    this.add(jscrollpane1);
    this.add(jscrollpane2);

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    this.validate();
    this.setSize(new Dimension(400, 200));
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });    
  }
}
