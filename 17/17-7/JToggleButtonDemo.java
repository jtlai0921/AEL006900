import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.util.*;

public class JToggleButtonDemo extends javax.swing.JFrame {

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

    new JToggleButtonDemo();
  }

  // �غc�禡
  public JToggleButtonDemo() {
    super("JToggleButton Demo");

    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // �]�w�����ϥ�
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // �]�w�}�����s��ؤ��ݩʭ�
    UIManager.put("ToggleButton.border", new javax.swing.border.BevelBorder(EtchedBorder.RAISED));

    // �]�w�}�����s���r���һP���s��ض������O�Z�����ݩʭ�
    UIManager.put("ToggleButton.margin", new Insets(2, 2, 2, 2));

    // �]�w�}�����s�I���C�⤧�ݩʭ�
    UIManager.put("ToggleButton.background", Color.PINK);

    // �]�w�}�����s��r���Ҧr�����ݩʭ�
    UIManager.put("ToggleButton.font", new Font("dialog", Font.BOLD, 12));

    // ���o�}�����sPluggable Look and Feel�ݩʭ�
    System.out.println("JToggleButton Look and Feel: " + UIManager.getString("ToggleButtonUI"));

    // �w�q Layout Manager �� BorderLayout
    this.setLayout(new BorderLayout());

    JToolBar jtoolbar = new JToolBar();

    String title[] = {"Left", "Right", "Home", "Reload", "Stop", "Receive", "Send"};
    JToggleButton jbutton[] = new JToggleButton[7];

    for (int i=0; i<title.length; i++) {
      jbutton[i] = new JToggleButton(new ImageIcon(cl.getResource("images/" + title[i] + ".gif")));
      jbutton[i].setPreferredSize(new Dimension(36, 36));
      jbutton[i].setToolTipText(title[i]);

      jtoolbar.add(jbutton[i]);

      if (i==2 || i==4)
        jtoolbar.addSeparator();
    }

    this.add(jtoolbar, BorderLayout.NORTH);

    // �غc�s��
    ButtonGroup group1 = new ButtonGroup();
    group1.add(jbutton[0]);
    group1.add(jbutton[1]);
    group1.add(jbutton[2]);

    // �غc�s��
    ButtonGroup group2 = new ButtonGroup();
    group2.add(jbutton[3]);
    group2.add(jbutton[4]);

    // �غc�s��
    ButtonGroup group3 = new ButtonGroup();
    group3.add(jbutton[5]);
    group3.add(jbutton[6]);

    // �ܽd���s�s�ժ���k
    // ���o���s�s�դ��Ҧ���AbstractButton
    Enumeration<AbstractButton> elements = group1.getElements();

    System.out.println("AbstractButton in Group 1:");
    
    while (elements.hasMoreElements()) {
      AbstractButton button = (AbstractButton) elements.nextElement();
      
      System.out.println(button.getToolTipText());
    }

    // �]�w�������j�p
    this.setSize(320, 150);

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
