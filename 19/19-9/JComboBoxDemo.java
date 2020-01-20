import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class JComboBoxDemo extends JFrame {

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

    new JComboBoxDemo();
  }

  // �غc�禡
  public JComboBoxDemo() {
    super("JComboBox Renderer Demo");

    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // �]�w�����ϥ�
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // �ŧiLinked-list���U�椸����r�r���B�e���C��B�Ϲ�����ܤ�r
    Object items[][] = {
      {new Font("TimesRoman", Font.PLAIN, 16), Color.BLACK,   
       new ImageIcon(cl.getResource("images/icon1.gif")), "Item 1"},
      {new Font("dialog", Font.ITALIC, 14), Color.GREEN, 
       new ImageIcon(cl.getResource("images/icon2.gif")), "Item 2"},
      {new Font("TimesRoman", Font.BOLD, 18), Color.BLUE,  
       new ImageIcon(cl.getResource("images/icon3.gif")), "Item 3"},
      {new Font("Helvetica", Font.BOLD | Font.ITALIC, 14), Color.GRAY, 
       new ImageIcon(cl.getResource("images/icon4.gif")), "Item 4"},
      {new Font("TimesRoman", Font.PLAIN, 18), Color.PINK,
        new ImageIcon(cl.getResource("images/icon5.gif")), "Item 5"},
      {new Font("Courier", Font.BOLD, 20), Color.YELLOW,
        new ImageIcon(cl.getResource("images/icon6.gif")), "Item 6"},
      {new Font("dialog", Font.ITALIC, 14), Color.DARK_GRAY,
        new ImageIcon(cl.getResource("images/icon7.gif")), "Item 7"},
      {new Font("TimesRoman", Font.PLAIN | Font.ITALIC, 20), Color.RED,
        new ImageIcon(cl.getResource("images/icon8.gif")), "Item 8"}
    };

    JComboBox jcombobox = new JComboBox(items);

    ComboBoxRenderer renderer = new ComboBoxRenderer();
    renderer.setPreferredSize(new Dimension(120, 30));
    // �]�wø�sLinked-list���C�ӳ椸��ListCellRenderer����
    jcombobox.setRenderer(renderer);
    // �]�w�U��JComboBox���O�ɡA�̦h�i��ܪ����ئC��
    jcombobox.setMaximumRowCount(3);

    // �]�w Etched Border (Test only)
    Border border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.white, new Color(142, 142, 142)); 

    JPanel jpanel1 = new JPanel();
    jpanel1.setLayout(new FlowLayout());
    jpanel1.setBorder(BorderFactory.createTitledBorder(border, "����"));
    jpanel1.add(jcombobox);

    // �w�q Layout Manager �� FlowLayout
    this.setLayout(new FlowLayout());

    this.add(jpanel1);

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    this.validate();
    this.setSize(new Dimension(200, 120));
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });    
  }
}
