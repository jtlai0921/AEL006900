import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;

public class ViewSourceFrame extends JFrame {
  // 建構函式
  public ViewSourceFrame(String htmlSource, String url) {
    this.setTitle("View Source: " + url);

    // Get current classloader
    ClassLoader cl = this.getClass().getClassLoader();
    // Create icons
    ImageIcon image1 = new ImageIcon(cl.getResource("images/web.gif"));
    this.setIconImage(image1.getImage());
    
    // 定義Layout Manager 為 BorderLayout
    this.setLayout(new BorderLayout());

    // Show HTML Source
    JTextArea txtSource = new JTextArea();
    txtSource.setText(htmlSource);
    txtSource.setCaretPosition(0);

    JButton btnClose = new JButton();
    btnClose.setText("Close");
    btnClose.setMnemonic('C');
    btnClose.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        dispose();
      }
    });

    // 設定預設指令鈕
    getRootPane().setDefaultButton(btnClose);

    JPanel Panel1 = new JPanel();
    Panel1.setLayout(new BorderLayout());
    Panel1.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
    
    JPanel Panel2 = new JPanel();
    Panel2.setLayout(new FlowLayout());
    Panel2.setPreferredSize(new Dimension(10, 40));
    
    JScrollPane jScrollPane1 = new JScrollPane();
    jScrollPane1.getViewport().add(txtSource);
    Panel1.add(jScrollPane1, BorderLayout.CENTER);
    Panel2.add(btnClose);
    this.add(Panel1, BorderLayout.CENTER);
    this.add(Panel2, BorderLayout.SOUTH);

    this.validate();
    this.setSize(new Dimension(400, 300));

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    this.setResizable(true);
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        dispose();
      }
    });    
  }
}
