import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class JSplitPaneDemo extends JFrame {
    
  CardLayout cardlayout = new CardLayout();
  JPanel jpanel, bottomPanel;
  JList jlist;
  JLabel jlabel, lblName;
  JSplitPane jsplitpane;
  JSplitPane topSplitpane;

  String[] items = new String[12];

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

    new JSplitPaneDemo();
  }

  // �غc�禡
  public JSplitPaneDemo() {
    super("JSplitPane Demo");

    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // �]�w�����ϥ�
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // �w�q Layout Manager �� BorderLayout
    this.setLayout(new BorderLayout());

    // �w�q jpanel �� Layout Manager �� CardLayout
    jpanel = new JPanel();
    jpanel.setLayout(cardlayout); 

    lblName = new JLabel();
    
    for (int i=0; i < items.length; i++)  {  
      items[i] = "icon" + i + ".png";

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
        
        lblName.setText("Select icon" + i + ".png");
      }
    });
    
    // ���b
    JScrollPane jscrollpane1 = new JScrollPane(jlist);
    jscrollpane1.setWheelScrollingEnabled(true);
    jscrollpane1.setMinimumSize(new Dimension(100, 50));

    // ���b
    JScrollPane jscrollpane2 = new JScrollPane(jpanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    jscrollpane2.setWheelScrollingEnabled(true);
    jscrollpane2.setMinimumSize(new Dimension(100, 50));

    // ���j���O
    // �������j
    jsplitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jscrollpane1, jscrollpane2);
    // �]�w�O�_�H���jWidget�ֳt�i�}�κP�|���j���O
    jsplitpane.setOneTouchExpandable(true);
    // �]�w���j�b����m
    jsplitpane.setDividerLocation(100);
    jsplitpane.setPreferredSize(new Dimension(400, 200));

    // �w�q bottomPanel �� Layout Manager �� BorderLayout
    bottomPanel = new JPanel();
    bottomPanel.setLayout(new BorderLayout()); 
    bottomPanel.add(lblName, BorderLayout.CENTER);
    bottomPanel.setMinimumSize(new Dimension(100, 50));

    // ���j���O
    // �������j
    topSplitpane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, jsplitpane, bottomPanel);
    // �]�w�O�_�H���jWidget�ֳt�i�}�κP�|���j���O
    topSplitpane.setOneTouchExpandable(true);
    // �]�w���j�b����m
    topSplitpane.setDividerLocation(150);
    
    this.add(topSplitpane, BorderLayout.CENTER);

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    this.validate();
    this.setSize(new Dimension(350, 250));
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });    
  }
}
