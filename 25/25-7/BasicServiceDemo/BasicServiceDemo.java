import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

import java.io.*;
import java.net.*;

// JNLP API
import javax.jnlp.*;

public class BasicServiceDemo extends javax.swing.JFrame {

  private JButton btnOpen = new JButton();

  private JTextField txtURL = new JTextField();

  // JNLP BasicService
  private BasicService bs;

  private URL codebase = null;
   
  // Main method
  public static void main(String[] args) {
    UIManager.put("swing.boldMetal", Boolean.FALSE);
    JDialog.setDefaultLookAndFeelDecorated(true);
    JFrame.setDefaultLookAndFeelDecorated(true);
    Toolkit.getDefaultToolkit().setDynamicLayout(true);

    try {
      UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    new BasicServiceDemo();
  }

  // 建構函式
  public BasicServiceDemo() {
    super("JNLP BasicService");

    initJNLPService();

    Container contentPane = getContentPane();
    // 定義 contentPane 之 Layout Manager 為 BorderLayout
    contentPane.setLayout(new BorderLayout());

    JLabel lblURL = new JLabel("URL:");
    lblURL.setDisplayedMnemonic('U');

    // 設定標籤所屬的元件，代表當執行標籤易記碼（Mnemonic Key）時
    // 所取得輸入焦點（Focus）的元件。
    lblURL.setLabelFor(txtURL);

    btnOpen.setMnemonic('O');
    btnOpen.setText("Open URL");
    btnOpen.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnOpen_actionPerformed(e);
      }
    });

    JPanel panel1 = new JPanel();
    panel1.setLayout(new BorderLayout());
    panel1.setBorder(BorderFactory.createEmptyBorder(10,5,5,5));

    JPanel panel2 = new JPanel();
    panel2.setLayout(new FlowLayout());

    panel1.add(lblURL, BorderLayout.WEST);
    panel1.add(txtURL, BorderLayout.CENTER);
    panel2.add(btnOpen);
    contentPane.add(panel1, BorderLayout.CENTER);
    contentPane.add(panel2, BorderLayout.SOUTH);

    this.validate();
    this.setSize(new Dimension(330, 130));

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    this.setLocation((screenSize.width - frameSize.width) / 2, 
      (screenSize.height - frameSize.height) / 2);

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }

  private void initJNLPService() {
    try { 
      // 查詢服務
      bs = (BasicService)ServiceManager.lookup("javax.jnlp.BasicService"); 
    } 
    catch (UnavailableServiceException e) { 
      // 無法查詢到指定服務，回傳UnavailableServiceException
      bs = null; 
    }
    
    if (bs != null) { 
      // 回傳Java應用程式的位置，並回傳java.net.URL型別
      codebase = bs.getCodeBase(); 
      
      txtURL.setText(codebase.toString());
    }
  }

  private void btnOpen_actionPerformed(ActionEvent e) {
    if (bs != null) { 
      // 檢查瀏覽器是否支援JNLP
      if (bs.isWebBrowserSupported()) {
        if (codebase != null) {
          // 啟動用戶端瀏覽器開啟URL
          bs.showDocument(codebase); 
        }
      }
    }
  }
}
