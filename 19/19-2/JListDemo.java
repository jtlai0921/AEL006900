import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class JListDemo extends JFrame {

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

    new JListDemo();
  }

  // �غc�禡
  public JListDemo() {
    super("JList Demo");

    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // �]�w�����ϥ�
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // �]�w�˵��M����ؤ��ݩʭ�
    UIManager.put("List.border", new javax.swing.border.EtchedBorder(EtchedBorder.LOWERED));
    
    // �]�w�˵��M��e�����ݩʭ�
    UIManager.put("List.foreground", Color.PINK);
    
    // �]�w�˵��M���r���Ҧr�����ݩʭ�
    UIManager.put("List.font", new Font("dialog", Font.BOLD, 12));
    
    // ���o�˵��M��Pluggable Look and Feel�ݩʭ�
    System.out.println("JList Look and Feel: " + UIManager.getString("ListUI"));
   
    // �w�q Layout Manager �� FlowLayout
    this.setLayout(new FlowLayout());

    // �]�w Etched Border (Demo only)
    Border border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.white, new Color(142, 142, 142)); 

    JPanel jpanel1 = new JPanel();
    jpanel1.setLayout(new FlowLayout());
    jpanel1.setBorder(BorderFactory.createTitledBorder(border, "���"));
    JPanel jpanel2 = new JPanel();
    jpanel2.setLayout(new FlowLayout());
    jpanel2.setBorder(BorderFactory.createTitledBorder(border, "�϶�"));
    JPanel jpanel3 = new JPanel();
    jpanel3.setLayout(new FlowLayout());
    jpanel3.setBorder(BorderFactory.createTitledBorder(border, "�h�����"));
    JPanel jpanel4 = new JPanel();
    jpanel4.setLayout(new FlowLayout());
    jpanel4.setBorder(BorderFactory.createTitledBorder(border, "�����ƦC"));
    JPanel jpanel5 = new JPanel();
    jpanel5.setLayout(new FlowLayout());
    jpanel5.setBorder(BorderFactory.createTitledBorder(border, "�����ƦC"));

    final int length = 12;  // �ﶵ���حӼ�
    String[] items = new String[length];

    for (int i=0; i < length; i++)    
      items[i] = "Item " + i;

    JList jlist1 = new JList(items);
    // �]�w�˵��M�檫�󤤳̦h�i��ܪ����ئC�ơ]row�^
    jlist1.setVisibleRowCount(4);
    // ���
    jlist1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    // ���b
    JScrollPane jscrollpane1 = new JScrollPane(jlist1);
    jpanel1.add(jscrollpane1);

    JList jlist2 = new JList(items);
    // �]�w�˵��M�檫�󤤳̦h�i��ܪ����ئC�ơ]row�^
    jlist2.setVisibleRowCount(4);
    // �ȥi��ܳ�@�϶��]Interval�^�A�����϶����i��ܦh�Ӷ���
    jlist2.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    // �]�w��ܰ϶��]Interval�^
    jlist2.setSelectionInterval(3, 6);
    // ���b
    JScrollPane jscrollpane2 = new JScrollPane(jlist2);
    jpanel2.add(jscrollpane2);
    
    JList jlist3 = new JList(items);
    // �]�w�˵��M�檫�󤤳̦h�i��ܪ����ئC�ơ]row�^
    jlist3.setVisibleRowCount(4);
    // ���\�h�����
    jlist3.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    // �]�w��ܰ϶��]Interval�^
    jlist3.setSelectionInterval(2, 4);
    // �s�W��ܰ϶��]Interval�^
    jlist3.addSelectionInterval(7, 10);
    // ���b
    JScrollPane jscrollpane3 = new JScrollPane(jlist3);
    jpanel3.add(jscrollpane3);
 
    JList jlist4 = new JList(items);
    // �]�w�˵��M�檫�󤤳̦h�i��ܪ����ئC�ơ]row�^
    jlist4.setVisibleRowCount(4);
    // �ȥi��ܳ�@�϶��]Interval�^�A�����϶����i��ܦh�Ӷ���
    jlist4.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    // ���H�����ƦC����
    jlist4.setLayoutOrientation(JList.HORIZONTAL_WRAP);
    // �]�w��ܰ϶��]Interval�^
    jlist4.setSelectionInterval(3, 6);
    // ���b
    JScrollPane jscrollpane4 = new JScrollPane(jlist4);
    jpanel4.add(jscrollpane4);
    
    JList jlist5 = new JList(items);
    // �]�w�˵��M�檫�󤤳̦h�i��ܪ����ئC�ơ]row�^
    jlist5.setVisibleRowCount(4);
    // ���\�h�����
    jlist5.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    // ���H�����ƦC����
    jlist5.setLayoutOrientation(JList.VERTICAL_WRAP);
    // �]�w��ܰ϶��]Interval�^
    jlist5.setSelectionInterval(2, 4);
    // �s�W��ܰ϶��]Interval�^
    jlist5.addSelectionInterval(7, 10);
    // ���b
    JScrollPane jscrollpane5 = new JScrollPane(jlist5);
    jpanel5.add(jscrollpane5);
    
    this.add(jpanel1);
    this.add(jpanel2);
    this.add(jpanel3);
    this.add(jpanel4);
    this.add(jpanel5);

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    this.validate();
    this.setSize(new Dimension(340, 290));
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });    
  }
}
