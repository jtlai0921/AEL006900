import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import java.io.*;
import java.net.*;

public class JEditorPaneDemo extends JFrame{
  JTextField txtURL = new JTextField();
  JEditorPane htmlPane = new JEditorPane();

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

    new JEditorPaneDemo();
  }

  // 建構函式
  public JEditorPaneDemo() {
    super("JEditorPane Demo");

    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 設定視窗圖示
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // 定義Layout Manager 為 BorderLayout
    this.setLayout(new BorderLayout());

    JLabel jlabel1 = new JLabel("URL:");

    htmlPane.setEditorKit(new javax.swing.text.html.HTMLEditorKit());
    htmlPane.setEditable(false);

    txtURL.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        txtURL_actionPerformed(e);
      }
    });

    JPanel Panel1 = new JPanel();
    Panel1.setLayout(new BorderLayout());
    Panel1.setPreferredSize(new Dimension(10, 40));

    JPanel Panel2 = new JPanel();
    Panel2.setLayout(new GridLayout(1, 1));
    Panel2.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));
    Panel2.setPreferredSize(new Dimension(40, 10));

    JPanel Panel3 = new JPanel();
    Panel3.setLayout(new GridLayout(1, 1));
    Panel3.setBorder(BorderFactory.createEmptyBorder(5,0,5,5));

    Panel2.add(jlabel1);
    Panel1.add(Panel2, BorderLayout.WEST);
    Panel3.add(txtURL);
    Panel1.add(Panel3, BorderLayout.CENTER);
    JScrollPane jscrollpane = new JScrollPane();
    jscrollpane.getViewport().add(htmlPane);

    this.add(Panel1, BorderLayout.NORTH);
    this.add(jscrollpane, BorderLayout.CENTER);

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    this.validate();
    this.setSize(new Dimension(300, 300));
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }

  private void txtURL_actionPerformed(ActionEvent e) {
    String url = this.txtURL.getText().toString().trim();
    
    showHTML(url);
  }

  private void showHTML(String url) {
    this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
    
    try {
      if (url.length() > 0) {
        if (!url.startsWith("http://") && !url.startsWith("file:/")) {
          if (url.indexOf(':') == 1) {
            url = "file:/" + url;
          }
          else {
            url = "http://" + url;
          }
        }
    
        java.net.URL source = new URL(url);
        htmlPane.setPage(source);
        txtURL.setText(url);
        txtURL.select(0, url.length());
      }
    }
    catch (java.net.MalformedURLException muex) {
      JOptionPane.showMessageDialog(this, muex.toString() + ": " + url,
        "Java Web Browser", JOptionPane.ERROR_MESSAGE);
    }
    catch (Exception ex){
      JOptionPane.showMessageDialog(this, ex.toString() + ": " + url,
        "Java Web Browser", JOptionPane.ERROR_MESSAGE);
    }
    
    this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
  }
}
