import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.io.*;
import java.net.*;

// Java Media Framework
import javax.media.*;
import javax.media.bean.playerbean.*; 
  
public class JavaMediaPlayer extends JFrame implements ControllerListener {
  
  // Media Player Bean���O
  javax.media.bean.playerbean.MediaPlayer player = null;
  
  Component visualComponent, controlPanel;
  
  Container contentPane;

  JCheckBoxMenuItem jmenuLoop;
  
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

    new JavaMediaPlayer();
  }

  // �غc�禡
  public JavaMediaPlayer() {
    super("Java Media Player");
  
    // Get current classloader
    ClassLoader cl = this.getClass().getClassLoader();

    // Create icons
    ImageIcon image1 = new ImageIcon(cl.getResource("images/jmf.gif"));
    this.setIconImage(image1.getImage());

    contentPane = getContentPane();
    contentPane.setLayout(new BorderLayout());

    // �إ߿��C
    JMenuBar jmenubar = new JMenuBar();
    // �w�q�����ϥΪ̤��������C
    this.setJMenuBar(jmenubar);

    // �إ߿��
    JMenu jmenuFile = new JMenu("File");
    jmenuFile.setMnemonic('F');
    jmenubar.add(jmenuFile);

    // �إ߿�涵��
    // Open
    JMenuItem jmenuOpen = new JMenuItem("Open ...");
    jmenuOpen.setMnemonic('O');
    // ���U ActionListener
    jmenuOpen.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        openFile();
      }
    });
    
    // Exit
    JMenuItem jmenuExit = new JMenuItem("Exit");
    jmenuExit.setMnemonic('X');
    // ���U ActionListener
    jmenuExit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JOptionPane joptionpane = new JOptionPane();
        int iResult = joptionpane.showConfirmDialog(null, "Are you sure to quit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (iResult == 0) {
          dispose();
          System.exit(0);
        }
      }
    });

    jmenuFile.add(jmenuOpen);
    jmenuFile.addSeparator();
    jmenuFile.add(jmenuExit);

    // �إ߿��
    JMenu jmenuPlayer = new JMenu("Player");
    jmenuPlayer.setMnemonic('P');
    jmenubar.add(jmenuPlayer);

    // �إ߿�涵��
    // Auto Loop
    jmenuLoop = new JCheckBoxMenuItem("Auto Loop");
    jmenuLoop.setMnemonic('L');
    jmenuLoop.setState(true);
    jmenuPlayer.add(jmenuLoop);
    // ���U ActionListener
    jmenuLoop.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        setAutoLoop();
      }
    });

    // �إ߿��
    // Help
    JMenu jmenuHelp = new JMenu("Help");
    jmenuHelp.setMnemonic('H');
    jmenubar.add(jmenuHelp);

    // About
    JMenuItem jmenuAbout = new JMenuItem("About");
    jmenuAbout.setMnemonic('A');
    jmenuHelp.add(jmenuAbout);
    jmenuAbout.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "Java Media Player", "About", JOptionPane.INFORMATION_MESSAGE);
      }
    });
    
    this.validate();
    this.setSize(new Dimension(300, 300));

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
        System.exit(0);
      }
    });    

    // Specify a hint for the Manager to use.
    javax.media.Manager.setHint(Manager.LIGHTWEIGHT_RENDERER, new Boolean(true));
  }  

  private void openFile() {
    JFileChooser jfileChooser = new JFileChooser();
    // �]�w�ɮ׹�ܲ������D
    jfileChooser.setDialogTitle("Open File ...");
    // �]�w�ɮ׹�ܲ�������
    jfileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
    jfileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    jfileChooser.setCurrentDirectory(new File("."));
    
    if (jfileChooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
      return;

    this.repaint();
    
    try {
      final String url = jfileChooser.getSelectedFile().toURI().toURL().toString();

      Thread thread = new Thread() {
        public void run() {
          showPlayer(url);
        }
      };
      thread.start();
    }
    catch (MalformedURLException muex) {
      muex.printStackTrace();
    }
  }

  public void setAutoLoop() {
    // �]�w���Ƽ���
    if (player != null) 
      player.setPlaybackLoop(jmenuLoop.getState());
  }

  public void showPlayer(String url) {
    // Reset Player
    if (player != null) {
      if (visualComponent != null)
        contentPane.remove(visualComponent);
      
      if (controlPanel != null)
        contentPane.remove(controlPanel);
      
      // ����Media Player�A�h��������C��
      player.close();
    }  

    // MediaPlayer bean
    player = new javax.media.bean.playerbean.MediaPlayer();
    player.setMediaLocation(url);

    // ���U ControllerListener
    player.addControllerListener((ControllerListener) this);
    // �ˬd�T�{�C�餺�e
    player.realize();

    // ���Ƽ���
    player.setPlaybackLoop(jmenuLoop.getState());
  }
      
  public void controllerUpdate(ControllerEvent event) {
    // ��Media Player��������ˬd�T�{�C�餺�e�ɩҩI�s����k
    if (event instanceof RealizeCompleteEvent) {
      // ���i��w���B�z�C�餺�e
      player.prefetch();
    } 
    // ��Media Player��������w���B�z�C�餺�e�ɩҩI�s����k
    else if (event instanceof PrefetchCompleteEvent) {
      // ���o�v���ɪ����e�A�H�K���񤧥�
      visualComponent = player.getVisualComponent();
    
      if (visualComponent != null)
        contentPane.add(visualComponent, BorderLayout.CENTER);
    
      // ���oMedia Player�w�]������O
      controlPanel = player.getControlPanelComponent();
    
      if (controlPanel != null)
        contentPane.add(controlPanel, BorderLayout.SOUTH);
    
      validate();

      // �}�lMedia Player�A�h�}�l����C�餺�e
      player.start();
    } 
    // ��Media Player�������C�餺�e�����ɩҩI�s����k
    else if (event instanceof EndOfMediaEvent) {
      // �O�_���Ƽ���
      if (!player.isPlayBackLoop()) {
        // �]�w�C��ثe������ɶ�
        player.setMediaTime(new Time(0));
        // ����Media Player�A�h�����C�餺�e
        player.stop();
      }
    }
  }
}
