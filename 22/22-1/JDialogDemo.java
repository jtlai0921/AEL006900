import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class JDialogDemo extends JFrame {
  
  JDialog jdialog = new JDialog((Frame) null,  "JDialog Demo", true);

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
    
    new JDialogDemo();
  }

  // �غc�禡
  public JDialogDemo() {
    super("JDialog Demo");

    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // �]�w�����ϥ�
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // �w�qLayout Manager �� FlowLayout
    this.setLayout(new FlowLayout());

    JButton[] jbutton = new JButton[6];
    
    jbutton[0] = new JButton("Modal");
    jbutton[0].addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // �]�w��ܲ����W���j��^��
        jdialog.setModal(true);
        
        // ��ܹ�ܲ�
        jdialog.setVisible(true);
      }
    });

    jbutton[1] = new JButton("Modaless");
    jbutton[1].addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // �]�w��ܲ����D�W���j��^��
        jdialog.setModal(false);
        
        // ��ܹ�ܲ�
        jdialog.setVisible(true);
      }
    });

    jbutton[2] = new JButton("Application Modal");
    jbutton[2].addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // �]�w��ܲ������ε{�������W���j��^��
        jdialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        
        // ��ܹ�ܲ�
        jdialog.setVisible(true);
      }
    });

    jbutton[3] = new JButton("Document Modal");
    jbutton[3].addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // �]�w��ܲ�����������W���j��^��
        jdialog.setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
        
        // ��ܹ�ܲ�
        jdialog.setVisible(true);
      }
    });

    jbutton[4] = new JButton("Modaless");
    jbutton[4].addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // �]�w��ܲ����D�W���j��^��
        jdialog.setModalityType(Dialog.ModalityType.MODELESS);
        
        // ��ܹ�ܲ�
        jdialog.setVisible(true);
      }
    });

    jbutton[5] = new JButton("Toolkit Modal");
    jbutton[5].addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // �]�w��ܲ����u��M�������W���j��^��
        jdialog.setModalityType(Dialog.ModalityType.TOOLKIT_MODAL);
        
        // ��ܹ�ܲ�
        jdialog.setVisible(true);
      }
    });

    this.add(jbutton[0]);
    this.add(jbutton[1]);
    this.add(jbutton[2]);
    this.add(jbutton[3]);
    this.add(jbutton[4]);
    this.add(jbutton[5]);

    createDialog();

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    this.validate();
    this.setSize(new Dimension(200, 210));
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });    
  }
  
  private void createDialog() {
    // �����w�qjdialog��Layout Manager��BorderLayout
    jdialog.setLayout(new BorderLayout());

    JLabel jlabel = new JLabel("JDialog Message.");

    JButton jbtnOK = new JButton("OK");
    jbtnOK.setPreferredSize(new Dimension(75, 25));
    jbtnOK.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // �����ܲ�
        jdialog.dispose();
      }
    });

    JButton jbtnCancel = new JButton("Cancel");
    jbtnCancel.setPreferredSize(new Dimension(75, 25));
    jbtnCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // �����ܲ�
        jdialog.dispose();
      }
    });
    
    JPanel jpanel = new JPanel();
    jpanel.setLayout(new FlowLayout());
    jpanel.add(jbtnOK);
    jpanel.add(jbtnCancel);

    // �����N����[��JDialog��
    jdialog.add(jlabel, BorderLayout.CENTER);
    jdialog.add(jpanel, BorderLayout.SOUTH);

    // �]�w��ܲ����j�p
    jdialog.setSize(new Dimension(200, 120));

    // Center the Dialog
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension dialogSize = jdialog.getSize();

    if (dialogSize.height > screenSize.height)
      dialogSize.height = screenSize.height;

    if (dialogSize.width > screenSize.width)
      dialogSize.width = screenSize.width;

    jdialog.setLocation((screenSize.width - dialogSize.width) / 2, (screenSize.height - dialogSize.height) / 2);
  }
}
