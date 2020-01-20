import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class SplashDemo extends JFrame {
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

  // �غc�禡
  public SplashDemo() {
    java.awt.SplashScreen splashScreen = null;
    
    try {
      // ���oSplashScreen����
      splashScreen = SplashScreen.getSplashScreen();
      
      if (splashScreen == null) {
        return;
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    try {
      // �إ�SplashScreen����ø��Context
      Graphics2D g2d = splashScreen.createGraphics();
      
      if (g2d == null) {
        return;
      }
    }
    catch (IllegalStateException isex) {
      isex.printStackTrace();
    }

    // Delay
    for(int i=0; i<100; i++) {
      // ��sSplashScreen����
      splashScreen.update();
      
      try {
        Thread.sleep(20);
      }
      catch(InterruptedException e) {}
    }
    
    // ���ñҰʵe��������Ҧ��������t�θ귽
    // �Y�����榹��k�A�bAWT��Swing����������ܤ���
    // SplashScreen�����|�۰�����
    splashScreen.close();

    // ���J��������
    initializeApplication();
    showApplication();
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
