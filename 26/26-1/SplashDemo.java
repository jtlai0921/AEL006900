import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class SplashDemo extends JFrame {
  JWindow splashScreen = null;
  JLabel splashLabel = null;
  JFrame splashFrame = null;

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
    
    new SplashDemo();
  }

  // 建構函式
  public SplashDemo() {
    splashFrame = new JFrame();
    splashFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // 建立啟動畫面
    createSplashScreen();

    // 顯示啟動畫面
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        showSplashScreen();
      }
    });

    // 載入視窗物件
    initializeApplication();

    // Delay
    for (long i=0L; i<1000000000L; i++) {}
    
    // 隱藏啟動畫面
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        showApplication();
        hideSplashScreen();
      }
    });
  }    

  private void createSplashScreen() {
    splashLabel = new JLabel(new ImageIcon(this.getClass().getClassLoader().getResource("images/Splash.jpg")));
    
    splashScreen = new JWindow(splashFrame);
    splashScreen.getContentPane().add(splashLabel);
    splashScreen.pack();
    
    // Center the Splash screen
    Rectangle screenRect = splashFrame.getGraphicsConfiguration().getBounds();
    splashScreen.setLocation(
      screenRect.x + screenRect.width/2 - splashScreen.getSize().width/2,
      screenRect.y + screenRect.height/2 - splashScreen.getSize().height/2);
  }

  private void showSplashScreen() {
    // 顯示
    splashScreen.setVisible(true);
  }

  private void hideSplashScreen() {
    // 隱藏
    splashScreen.setVisible(false);
    splashScreen = null;
    splashLabel = null;
  }

  private void initializeApplication() {
    this.setLayout(new BorderLayout());

    this.setUndecorated(true);
    this.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
    this.setTitle("Splash Demo");

    // Get current classloader
    ClassLoader cl = this.getClass().getClassLoader();

    JMenuBar jmenubar = new JMenuBar();
    this.setJMenuBar(jmenubar);

    JMenu jmenuFile = new JMenu();
    jmenuFile.setText("File");
  
    JMenuItem jmenuFileExit = new JMenuItem("Exit");
    jmenuFileExit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        System.exit(0);
      }
    });

    jmenuFile.add(jmenuFileExit);

    jmenubar.add(jmenuFile);

    JMenu jmenuHelp = new JMenu();
    jmenuHelp.setText("Help");

    // About
    JMenuItem jmenuAbout = new JMenuItem("About", new ImageIcon(cl.getResource("images/about.gif")));
    jmenuAbout.setMnemonic('A');
    jmenuAbout.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "Splash Demo", "About", JOptionPane.INFORMATION_MESSAGE);
      }
    });

    jmenuHelp.add(jmenuAbout);
    jmenubar.add(jmenuHelp);

    this.add(new JPanel(), BorderLayout.CENTER);
  }
  
  private void showApplication(){
    this.validate();
    this.setSize(new Dimension(250, 250));

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(true);
    this.setVisible(true);
  }
}
