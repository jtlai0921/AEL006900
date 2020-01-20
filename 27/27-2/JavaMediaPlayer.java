import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.io.*;
import java.net.*;

// Java Media Framework
import javax.media.*;
  
public class JavaMediaPlayer extends JFrame {

  // Media Player ����
  javax.media.Player player = null;
  
  PlayerFrame frame = null;

  JDesktopPane desktop;

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

    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // �]�w�����ϥ�
    this.setIconImage(new ImageIcon(cl.getResource("images/jmf.gif")).getImage());

    // �w�qLayout Manager �� BorderLayout
    this.setLayout(new BorderLayout());

    desktop = new JDesktopPane();
    desktop.setDoubleBuffered(true);
    desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
    //this.setContentPane(desktop);
    this.add(desktop, BorderLayout.CENTER);

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
    JMenu jmenuWindow = new JMenu("Window");
    jmenuWindow.setMnemonic('W');
    jmenubar.add(jmenuWindow);

    // �إ߿�涵��
    // Cascade
    JMenuItem jmenuCascade = new JMenuItem("Cascade");
    jmenuCascade.setMnemonic('C');
    // ���U ActionListener
    jmenuCascade.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cascadeFrame();
      }
    });

    jmenuWindow.add(jmenuCascade);

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
    this.setSize(new Dimension(400, 400));

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
    JFileChooser jfilechooser = new JFileChooser();
    // �]�w�ɮ׹�ܲ������D
    jfilechooser.setDialogTitle("Open File ...");
    // �]�w�ɮ׹�ܲ�������
    jfilechooser.setDialogType(JFileChooser.OPEN_DIALOG);
    jfilechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    jfilechooser.setCurrentDirectory(new File("."));
    
    if (jfilechooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
      return;

    this.repaint();
    
    try {
      final String url = jfilechooser.getSelectedFile().toURI().toURL().toString();
      final String title = jfilechooser.getSelectedFile().getName().toString();

      Thread thread = new Thread() {
        public void run() {
          showPlayer(url, title);
        }
      };
      thread.start();
    }
    catch (MalformedURLException muex) {
      muex.printStackTrace();
    }
  }

  public void showPlayer(String url, String title) {
    MediaLocator mediaLocator = new MediaLocator(url);
  
    if (mediaLocator == null) {
      JOptionPane.showMessageDialog(this, "�L�k�}���ɮ�.", "Java Media Player", JOptionPane.ERROR_MESSAGE);
      return;
    }
  
    try {
      player = Manager.createPlayer(mediaLocator);
    }
    catch (NoPlayerException npex) {
      npex.printStackTrace();
    }
    catch (IOException ioex) {
      ioex.printStackTrace();
    }

    if (player != null) {
      PlayerFrame frame = new PlayerFrame(player, title);
      
      desktop.add(frame);

      try {
        frame.setSelected(true);
      } 
      catch (java.beans.PropertyVetoException pe) {
        pe.printStackTrace();        
      }
    }
  }

  public void cascadeFrame() {
    int x = 0;
    int y = 0;

    try {
      JInternalFrame[] frames = desktop.getAllFrames();
      JInternalFrame selectedFrame = desktop.getSelectedFrame();

      for (int i=frames.length-1; i>=0; i--) {
        frames[i].setMaximum(false);
        frames[i].setIcon(false);
        frames[i].setLocation(x, y);

        x += 20;
        y += 20;
      }
      if (selectedFrame != null)
        desktop.setSelectedFrame(selectedFrame);
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }
}

class PlayerFrame extends JInternalFrame {
  static int frameCount = 0;
  static final int xOffset = 30, yOffset = 30;

  Component visualComponent = null;
  Component controlPanel = null;
  
  int videoWidth = 0;
  int videoHeight = 0;
  int controlHeight = 50;
  
  public PlayerFrame(Player player, String title) {
    super(title, true, true, true, true);

    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // �]�wInternal Frame�����ϥ�
    this.setFrameIcon(new ImageIcon(cl.getResource("images/java.gif")));

    ++frameCount;
    
    // �w�q Layout Manager �� BorderLayout
    getContentPane().setLayout(new BorderLayout());

    final Player mplayer = player;

    mplayer.addControllerListener(new ControllerAdapter(){
      // ��Media Player��������ˬd�T�{�C�餺�e�ɩҩI�s����k
      public void realizeComplete(RealizeCompleteEvent e)  {
        // ���i��w���B�z�C�餺�e
        mplayer.prefetch();
      }
      
      // ��Media Player��������w���B�z�C�餺�e�ɩҩI�s����k
      public void prefetchComplete(PrefetchCompleteEvent e) {
        if (visualComponent != null)
          return;

        // ���o�v���ɪ����e�A�H�K���񤧥�
        visualComponent = mplayer.getVisualComponent();
      
        if (visualComponent != null) {
          Dimension size = visualComponent.getPreferredSize();
          videoWidth = size.width;
          videoHeight = size.height;
          getContentPane().add(visualComponent, BorderLayout.CENTER);
        } 
        else
          videoWidth = 300;
      
        // ���oMedia Player�w�]������O
        controlPanel = mplayer.getControlPanelComponent();
      
        if (controlPanel != null) {
          controlHeight = controlPanel.getPreferredSize().height;
          getContentPane().add(controlPanel, BorderLayout.SOUTH);
        } 

        setSize(videoWidth + 10, videoHeight + controlHeight + 40);
      
        validate();
        
        // �}�lMedia Player�A�h�}�l����C�餺�e
        mplayer.start();
      }  
      
      // ��Media Player�������C�餺�e�����ɩҩI�s����k
      public void endOfMedia(EndOfMediaEvent e)  {
        // �]�w�C��ثe������ɶ�
        mplayer.setMediaTime(new Time(0));
        // ����Media Player�A�h�����C�餺�e
        mplayer.stop();
      }        
    });
    
    // �ˬd�T�{�C�餺�e
    mplayer.realize();

    this.addInternalFrameListener(new InternalFrameAdapter() {
      public void internalFrameClosing(InternalFrameEvent e) {
        // ����Media Player�A�h��������C��
        mplayer.close();
      }
    });
    
    this.setVisible(true);
    this.setLocation(xOffset*frameCount, yOffset*frameCount);
  }
}
