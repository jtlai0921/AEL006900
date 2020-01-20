import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.event.*;

import javax.swing.colorchooser.*;

public class JColorChooserDemo extends JFrame implements ChangeListener {

  JColorChooser jcolorchooser;
  
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

    new JColorChooserDemo();
  }

  // 建構函式
  public JColorChooserDemo() {
    super("JColorChooser Demo");

    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 設定視窗圖示
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // 定義Layout Manager 為 BorderLayout
    this.setLayout(new BorderLayout());

    jlabel = new JLabel("JColorChooser Demo", JLabel.CENTER);
    jlabel.setForeground(Color.PINK);
    jlabel.setBackground(Color.CYAN);
    jlabel.setOpaque(true);
    jlabel.setFont(new Font("Dialog", Font.BOLD, 20));
    jlabel.setPreferredSize(new Dimension(100, 65));

    JPanel jlabelPanel = new JPanel(new BorderLayout());
    jlabelPanel.add(jlabel, BorderLayout.CENTER);
    jlabelPanel.setBorder(BorderFactory.createTitledBorder(""));

    jcolorchooser = new JColorChooser(jlabel.getForeground());
    jcolorchooser.getSelectionModel().addChangeListener(this);
    jcolorchooser.setBorder(BorderFactory.createTitledBorder("Choose Color"));

    this.add(jlabelPanel, BorderLayout.NORTH);
    this.add(jcolorchooser, BorderLayout.CENTER);

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    this.validate();
    this.pack();
    //this.setSize(new Dimension(400, 300));
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });    
  }

  public void stateChanged(ChangeEvent e) {
    Color newColor = jcolorchooser.getColor();
    jlabel.setForeground(newColor);
  }
}
