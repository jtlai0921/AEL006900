import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.io.*;
  
public class JInternalFrameDemo extends JFrame {

  JDesktopPane desktop;

  JToolBar jtoolbar = new JToolBar();
  JButton jbutton[] = new JButton[3];
  String item[]  = {"New", "Open", "About"};
  String image[] = {"new", "open", "about"};

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

    new JInternalFrameDemo();
  }

  // �غc�禡
  public JInternalFrameDemo() {
    super("JInternalFrame Demo");

    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // �]�w�����ϥ�
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // �]�w���ʤ����D���I���C�⤧�ݩʭ�
    UIManager.put("InternalFrame.activeTitleBackground", Color.PINK);

    // �]�w���ʤ����D���e���C�⤧�ݩʭ�
    UIManager.put("InternalFrame.activeTitleForeground", Color.RED);

    // �]�w�D���ʤ����D���I���C�⤧�ݩʭ�
    UIManager.put("InternalFrame.inactiveTitleBackground", Color.CYAN);

    // �]�w�D���ʤ����D���e���C�⤧�ݩʭ�
    UIManager.put("InternalFrame.inactiveTitleForeground", Color.BLUE);

    // �]�wInternal Frame��ؤ��ݩʭ�
    UIManager.put("InternalFrame.border", new javax.swing.border.EtchedBorder(EtchedBorder.LOWERED));
    
    // �]�wInternal Frame�I���C�⤧�ݩʭ�
    UIManager.put("InternalFrame.background", Color.PINK);

    // �]�w�����������s���ϥ�
    UIManager.put("InternalFrame.closeIcon", new ImageIcon(cl.getResource("images/close.png")));
    
    // �]�w�����ϥ�
    UIManager.put("InternalFrame.icon", new ImageIcon(cl.getResource("images/java.gif")));
    
    // �]�w�̤p�Ƶ������s���ϥ�
    UIManager.put("InternalFrame.iconifyIcon", new ImageIcon(cl.getResource("images/minimize.png")));
    
    // �]�w�̤j�Ƶ������s���ϥ�
    UIManager.put("InternalFrame.maximizeIcon", new ImageIcon(cl.getResource("images/maximize.png")));

    // �]�w�̤p�Ƶ������s���ϥ�
    UIManager.put("InternalFrame.minimizeIcon", new ImageIcon(cl.getResource("images/restore.png")));
    
    // ���oInternal Frame��Pluggable Look and Feel�ݩʭ�
    System.out.println("JInternalFrame Look and Feel: " + UIManager.getString("InternalFrameUI"));
    
    // �w�q Layout Manager �� BorderLayout
    this.setLayout(new BorderLayout());

    desktop = new JDesktopPane();
    desktop.setDoubleBuffered(true);
    // �]�w�즲Internal Frame�������Ҧ�
    desktop.setDragMode(JDesktopPane.LIVE_DRAG_MODE);
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
    // New
    JMenuItem jmenuNew = new JMenuItem("New");
    jmenuNew.setMnemonic('N');
    // ���U ActionListener
    jmenuNew.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        InternalFrame frame = new InternalFrame();
        
        // �NInternal Frame�����[�J��JDesktopPane���O����
        desktop.add(frame);
    
        try {
          // ����ثe��Internal Frame����
          frame.setSelected(true);
        } 
        catch (java.beans.PropertyVetoException pe) {
          pe.printStackTrace();        
        }
      }
    });
    
    // Open
    JMenuItem jmenuOpen = new JMenuItem("Open ...");
    jmenuOpen.setMnemonic('O');
    // ���U ActionListener
    jmenuOpen.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        open();
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

    jmenuFile.add(jmenuNew);
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
        JOptionPane.showMessageDialog(null, "JInternalFrame Demo", "About", JOptionPane.INFORMATION_MESSAGE);
      }
    });

    for (int i=0; i<image.length; i++) {
      jbutton[i] = new JButton(new ImageIcon(cl.getResource("images/" + image[i] + ".gif")));
      jbutton[i].setPreferredSize(new Dimension(32, 32));
      jbutton[i].setToolTipText(item[i]);
      
      // ���U ActionListener
      jbutton[i].addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          button_actionPerformed(e);
        }
      });

      jtoolbar.add(jbutton[i]);

      if (i==1)
        jtoolbar.addSeparator();
    }

    this.add(jtoolbar, BorderLayout.NORTH);
    
    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    this.validate();
    this.setSize(new Dimension(400, 400));
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });    
  }  

  private void button_actionPerformed(ActionEvent e) {
    if (e.getSource().equals(jbutton[0])) { // New
      InternalFrame frame = new InternalFrame();
      
      // �NInternal Frame�����[�J��JDesktopPane���O����
      desktop.add(frame);
  
      try {
        // ����ثe��Internal Frame����
        frame.setSelected(true);
      } 
      catch (java.beans.PropertyVetoException pe) {
        pe.printStackTrace();        
      }
    }
    else if (e.getSource().equals(jbutton[1])) { // Open
      open();
    }
    else if (e.getSource().equals(jbutton[2])) { // About
      JOptionPane.showMessageDialog(null, "JInternalFrame Demo", "About", JOptionPane.INFORMATION_MESSAGE);
    } 
  }

  private void open() {
    JFileChooser jfilechooser = new JFileChooser();
    
    // �]�w�ɮ׹�ܲ������D
    jfilechooser.setDialogTitle("�}���ɮ�");
    // �]�w�ɮ׹�ܲ����ثe�ؿ�
    jfilechooser.setCurrentDirectory(new File("."));
    // �]�w�ɮ׹�ܲ�������
    jfilechooser.setDialogType(JFileChooser.OPEN_DIALOG);
    jfilechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    
    if (jfilechooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) 
      return;
      
    String filename = jfilechooser.getSelectedFile().toString();
    String title = jfilechooser.getSelectedFile().getName().toString();
    
    InternalFrame frame = new InternalFrame(filename, title);
    
    desktop.add(frame);

    try {
      frame.setSelected(true);
    } 
    catch (java.beans.PropertyVetoException pe) {
      pe.printStackTrace();        
    }
  }

  public void cascadeFrame() {
    int x = 0;
    int y = 0;

    try {
      // ���o�ୱ���O���Ҧ���Internal Frame����
      JInternalFrame[] frames = desktop.getAllFrames();
      // ���o�ୱ���O���ثe���Ĭ��ʡ]Active�^��Internal Frame����
      JInternalFrame selectedFrame = desktop.getSelectedFrame();

      for (int i=frames.length-1; i>=0; i--) {
        frames[i].setMaximum(false);
        frames[i].setIcon(false);
        frames[i].setLocation(x, y);

        x += 20;
        y += 20;
      }
      if (selectedFrame != null)
        // �]�w�ୱ���O���ثe���Ĭ��ʡ]Active�^��Internal Frame����
        desktop.setSelectedFrame(selectedFrame);
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }
}

// �ۭq�~��JInternalFrame�����O
class InternalFrame extends JInternalFrame {
  static int frameCount = 0;
  static final int xOffset = 30, yOffset = 30;

  // �غc�禡
  public InternalFrame() {
    this("", "");
  }

  // �غc�禡
  public InternalFrame(String filename, String title) {
    // �]�w�������D
    // �]�w�i�վ�Internal Frame�����j�p
    // �]�w�i����Internal Frame����
    // �]�w�i�̤j��Internal Frame����
    // �]�w�i�ϥܤ�Internal Frame����
    // public JInternalFrame(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable)
    super(title, true, true, true, true);

    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // �]�wInternal Frame�����ϥ�
    this.setFrameIcon(new ImageIcon(cl.getResource("images/java.gif")));

    ++frameCount;
    
    // �w�q Layout Manager �� BorderLayout
    this.setLayout(new BorderLayout());

    JTextArea jtextarea = new JTextArea();

    // �]�w�۰ʴ��檺�W�h
    jtextarea.setWrapStyleWord(false);
    // �]�wJTextArea���r�W�L��e�ɡA�O�_�۰ʴ���C
    jtextarea.setLineWrap(false);
    // �]�w�O�_�i�s��
    jtextarea.setEditable(true);
    // �]�w��r�Ÿ��ثe����m
    jtextarea.setCaretPosition(0);

    // �]�w���b���O
    JScrollPane jscrollpane = new JScrollPane(jtextarea);
    // �������b�G��W�LJTextArea�C�Ʈɤ~�۰���ܫ������b
    jscrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    // �������b�G��W�LJTextArea��Ʈɤ~�۰���ܤ������b
    jscrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    if (!filename.equals("")) {
      try {
        // �إ��ɮ׿�J��y
        FileReader in = new FileReader(filename);
        
        // ���ɮצ�y��Ū����Ʀܤ�r�ϰ줤
        jtextarea.read(in, filename);
      }
      catch (FileNotFoundException e) {
        e.printStackTrace();
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  
    this.add(jscrollpane, BorderLayout.CENTER);
    
    // �]�wInternal Frame�����j�p
    this.setSize(300, 300);
    // �]�wInternal Frame��������m
    this.setLocation(xOffset*frameCount, yOffset*frameCount);
    // �]�w�O�_��ܪ���
    this.setVisible(true);
  }
}
