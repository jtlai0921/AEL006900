import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class JScrollPaneDemo extends JFrame {
    
  CardLayout cardlayout = new CardLayout();
  JPanel jpanel = new JPanel();

  JList jlist;

  JLabel jlabel;

  String[] items = new String[4];

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

    new JScrollPaneDemo();
  }
  
  // �غc�禡
  public JScrollPaneDemo() {
    super("JScrollPane Demo");

    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // �]�w�����ϥ�
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // �w�q Layout Manager �� BorderLayout
    this.setLayout(new BorderLayout());

    // �w�q jpanel �� Layout Manager �� CardLayout
    jpanel.setLayout(cardlayout); 
    
    for (int i=0; i < 4; i++)  {  
      items[i] = "baby" + i + ".jpg";

      jlabel = new JLabel(new ImageIcon(cl.getResource("images/" + items[i])));
      jpanel.add(jlabel, items[i]);
    }

    cardlayout.show(jpanel, items[0]);

    jlist = new JList(items);
    // ���
    jlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    jlist.setSelectedIndex(0);
    jlist.addListSelectionListener(new ListSelectionListener() {
      // ��ﶵ���سQ����Ψ��������
      public void valueChanged(ListSelectionEvent e) {
        // �P�_���ƥ�O�_���h�Ӥ��P���ƥ󤧤@
        if(e.getValueIsAdjusting())
          return;
        
        // �P�_�ƥ󪺨ӷ�
        JList list = (JList)e.getSource();
        
        // ���o�Q������ت����ޭ�
        int i = list.getSelectedIndex();
        
        cardlayout.show(jpanel, items[i]);
      }
    });
    
    // ���b
    JScrollPane jscrollpane1 = new JScrollPane(jlist);
    jscrollpane1.setWheelScrollingEnabled(true);
    this.add(jscrollpane1, BorderLayout.WEST);

    // ���b
    JScrollPane jscrollpane2 = new JScrollPane(jpanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    jscrollpane2.setWheelScrollingEnabled(true);
    this.add(jscrollpane2, BorderLayout.CENTER);

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    this.validate();
    this.setSize(new Dimension(350, 300));
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });    
  }
}
