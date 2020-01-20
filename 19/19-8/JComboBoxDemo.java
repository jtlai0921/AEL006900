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
    super("JComboBox Demo");

    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // �]�w�����ϥ�
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // �]�wJComboBox��ؤ��ݩʭ�
    UIManager.put("ComboBox.border", new javax.swing.border.EtchedBorder(EtchedBorder.LOWERED));
    
    // �]�wJComboBox�e�����ݩʭ�
    UIManager.put("ComboBox.foreground", Color.PINK);
    
    // �]�wJComboBox��r���Ҧr�����ݩʭ�
    UIManager.put("ComboBox.font", new Font("dialog", Font.BOLD, 12));
    
    // ���oPluggable Look and Feel�ݩʭ�
    System.out.println("JComboBox Look and Feel: " + UIManager.getString("ComboBoxUI"));
   

    final int length = 5;  // �ﶵ���حӼ�

    // �]�w Etched Border (Test only)
    Border border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.white, new Color(142, 142, 142)); 

    JPanel jpanel1 = new JPanel();
    jpanel1.setLayout(new FlowLayout());
    jpanel1.setBorder(BorderFactory.createTitledBorder(border, "Normal"));
    JPanel jpanel2 = new JPanel();
    jpanel2.setLayout(new FlowLayout());
    jpanel2.setBorder(BorderFactory.createTitledBorder(border, "Editable"));
    JPanel jpanel3 = new JPanel();
    jpanel3.setLayout(new FlowLayout());
    jpanel3.setBorder(BorderFactory.createTitledBorder(border, "Disable"));

    JComboBox jcombobox1 = new JComboBox();

    for (int i=0; i < length; i++)    
      jcombobox1.addItem("Item " + i);

    // �����anIndex�ӿﶵ����
    jcombobox1.setSelectedIndex(3);
    // �]�w�U��JComboBox���O�ɡA�̦h�i��ܪ����ئC��
    jcombobox1.setMaximumRowCount(3);
    jpanel1.add(jcombobox1);

    JComboBox jcombobox2 = new JComboBox();

    for (int i=0; i < length; i++)    
      jcombobox2.addItem("Item " + i);
    
    // �]�wJComboBox���O�O�_�i�s��]Editable�^
    jcombobox2.setEditable(true);
    // �]�w�U��JComboBox���O�ɡA�̦h�i��ܪ����ئC��
    jcombobox2.setMaximumRowCount(3);
    jpanel2.add(jcombobox2);

    JComboBox jcombobox3 = new JComboBox();

    for (int i=0; i < length; i++)    
      jcombobox3.addItem("Item " + i);
    
    // �]�wJComboBox���O�O�_�^�����ʡ]Enabled�^
    jcombobox3.setEnabled(false);
    // �����anIndex�ӿﶵ����
    jcombobox3.setSelectedIndex(2);
    jpanel3.add(jcombobox3);
    
    // �w�q Layout Manager �� FlowLayout
    this.setLayout(new FlowLayout());

    this.add(jpanel1);
    this.add(jpanel2);
    this.add(jpanel3);

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    this.validate();
    this.setSize(new Dimension(380, 120));
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });    
  }
}
