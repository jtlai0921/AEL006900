import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

// Synth Look and Feel
import javax.swing.plaf.synth.*;


public class SynthLookandFeelDemo extends javax.swing.JFrame {

  // Main method
  public static void main(String[] args) {
    UIManager.put("swing.boldMetal", Boolean.FALSE);
    JDialog.setDefaultLookAndFeelDecorated(true);
    JFrame.setDefaultLookAndFeelDecorated(true);
    Toolkit.getDefaultToolkit().setDynamicLayout(true);

//    try {
//      UIManager.setLookAndFeel(new javax.swing.plaf.metal.MetalLookAndFeel());
//    }
//    catch(Exception e) {
//      e.printStackTrace();
//    }

    new SynthLookandFeelDemo();
  }

  // �غc�禡
  public SynthLookandFeelDemo() {
    super("Synth Look and Feel Demo");
    
    // �إ�SynthLookAndFeel
    SynthLookAndFeel laf = new SynthLookAndFeel();
    
    try {
      // �ۿ�J��y���JSynth Look and Feel��XML�ɮצ�Java�{��
      laf.load(SynthLookandFeelDemo.class.getResourceAsStream("synth.xml"), SynthLookandFeelDemo.class);

      // �]�wSynth Look and Feel
      UIManager.setLookAndFeel(laf);
    } 
    catch (UnsupportedLookAndFeelException e) {
      System.out.println("���䴩Synth Look and Feel");
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // �]�w�����ϥ�
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // �w�q Layout Manager �� GridLayout
    this.setLayout(new GridLayout(2, 2));

    JButton[] jbutton = new JButton[4];

    // HTML����
    jbutton[0] = new JButton("<html><font face=Verdana size=2>HTML Text</font></html>");

    // �]�w�w�]���s
    this.getRootPane().setDefaultButton(jbutton[0]);

    // �]�wNormal Icon�����s  
    jbutton[1] = new JButton("Image", new ImageIcon(cl.getResource("images/duke.gif")));
    // �H�r���]�w����U�O�X
    jbutton[1].setMnemonic('I');
    
    // �]�wPressed Icon�����s  
    jbutton[2] = new JButton("Pressed Button", new ImageIcon(cl.getResource("images/duke.gif")));
    // �H�r���]�w����U�O�X
    jbutton[2].setMnemonic('P');
    // �]�w���Ҧr��۹��Ϲ���������m
    jbutton[2].setHorizontalTextPosition(SwingConstants.CENTER);
    // �]�w���Ҧr��۹��Ϲ���������m
    jbutton[2].setVerticalTextPosition(SwingConstants.BOTTOM);
    // �]�w���U�ɪ��Ϲ�
    jbutton[2].setPressedIcon(new ImageIcon(cl.getResource("images/press.gif")));

    // �]Rollover Icon�����s  
    jbutton[3] = new JButton("Rollover Button");
    // �H�r���]�w����U�O�X
    jbutton[3].setMnemonic('R');
    // �]�w���Ҧr��۹��Ϲ���������m
    jbutton[3].setHorizontalTextPosition(SwingConstants.CENTER);
    // �]�w���Ҧr��۹��Ϲ���������m
    jbutton[3].setVerticalTextPosition(SwingConstants.BOTTOM);
    // �]�w�O�_�ҥηƹ����ܫ��s�W��ɪ�Rollover�ĪG
    jbutton[3].setRolloverEnabled(true);
    // �]�w���s���`�ɪ��Ϲ�
    jbutton[3].setIcon(new ImageIcon(cl.getResource("images/swing.gif")));
    // �]�w�ƹ����ܫ��s�W��ɪ��Ϲ�
    jbutton[3].setRolloverIcon(new ImageIcon(cl.getResource("images/rollover.gif")));
    
    for (int i=0; i<jbutton.length; i++)
      this.add(jbutton[i]);

    // �]�w�������j�p
    this.setSize(320, 250);

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();

    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;

    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;

    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    // ��ܵ���
    this.setVisible(true);
    
    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }
}
