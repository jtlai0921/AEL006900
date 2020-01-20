import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
import com.sun.java.swing.plaf.motif.MotifLookAndFeel;
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

public class JNLPApplicationFrame extends JFrame implements ActionListener {
  JPanel contentPane;
  JMenuBar jMenuBar1 = new JMenuBar();
  JMenu jMenuFile = new JMenu();
  JMenu jMenuLF = new JMenu();
  JMenuItem jMenuLF1 = new JMenuItem();
  JMenuItem jMenuLF2 = new JMenuItem();
  JMenuItem jMenuLF3 = new JMenuItem();
  JMenuItem jMenuFileExit = new JMenuItem();
  JMenu jMenuHelp = new JMenu();
  JMenuItem jMenuHelpAbout = new JMenuItem();
  JLabel jLabel1 = new JLabel();
  BorderLayout borderLayout1 = new BorderLayout();
  JTextArea jTextArea1 = new JTextArea();
  JButton jButton1 = new JButton();

  public JNLPApplicationFrame() {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    UIManager.put("swing.boldMetal", Boolean.FALSE);
    JDialog.setDefaultLookAndFeelDecorated(true);
    JFrame.setDefaultLookAndFeelDecorated(true);
    Toolkit.getDefaultToolkit().setDynamicLayout(true);

    try {
      this.setUndecorated(true);
      this.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      contentPane = (JPanel) this.getContentPane();
      contentPane.setLayout(borderLayout1);

      this.setTitle("JNLP Application");
      this.setJMenuBar(jMenuBar1);
      this.setSize(new Dimension(200, 200));

      jLabel1.setText("Metal");
      jTextArea1.setText("Swing Metal L&F");
      JScrollPane jscrollpane = new JScrollPane(jTextArea1);
  
      jButton1.setText("OK");
      JPanel jPanel = new JPanel();
      jPanel.add(jButton1);
  
      jMenuFile.setText("File");
      jMenuFileExit.setText("Exit");
      jMenuFileExit.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          System.exit(0);
        }
      });
      jMenuFile.add(jMenuFileExit);

      jMenuLF.setText("L & F");
      jMenuLF1.setText("Metal");
      jMenuLF1.addActionListener(this);
      jMenuLF2.setText("Motif");
      jMenuLF2.addActionListener(this);
      jMenuLF3.setText("Windows");
      jMenuLF3.addActionListener(this);
      
      jMenuLF.add(jMenuLF1);
      jMenuLF.add(jMenuLF2);
      jMenuLF.add(jMenuLF3);
      
      jMenuHelp.setText("Help");
      jMenuHelpAbout.setText("About");

      jMenuHelp.add(jMenuHelpAbout);
      jMenuBar1.add(jMenuFile);
      jMenuBar1.add(jMenuLF);
      jMenuBar1.add(jMenuHelp);

      contentPane.add(jLabel1, BorderLayout.NORTH);
      contentPane.add(jscrollpane, BorderLayout.CENTER);
      contentPane.add(jPanel, BorderLayout.SOUTH);    
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      System.exit(0);
    }
  }

  public void actionPerformed(ActionEvent e) {
    JMenuItem src = (JMenuItem)e.getSource();
    
    try {
      if((JMenuItem)e.getSource() == jMenuLF1)
        UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
      else if((JMenuItem)e.getSource() == jMenuLF2)
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
      else if((JMenuItem)e.getSource() == jMenuLF3)
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
    }
    catch(Exception ex) {}
    
    SwingUtilities.updateComponentTreeUI(this);
  }
}
