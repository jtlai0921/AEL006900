import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.event.*;

public class JTextAreaDemo extends JFrame {
  
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

    new JTextAreaDemo();
  }

  // �غc�禡
  public JTextAreaDemo() {
    super("JTextArea Demo");

    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // �]�w�����ϥ�
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // �]�w��r�ϰ���ؤ��ݩʭ�
    UIManager.put("TextArea.border", new javax.swing.border.EtchedBorder(EtchedBorder.LOWERED));
    
    // �]�w��r�Ÿ��{�{���t�v���ݩʭ�
    UIManager.put("TextArea.caretBlinkRate", 5);
    
    // �]�w��r�Ÿ����e���C�⤧�ݩʭ�
    UIManager.put("TextArea.caretForeground", Color.RED);
    
    // �]�w��r���Ҧr�����ݩʭ�
    UIManager.put("TextArea.font", new Font("dialog", Font.BOLD, 12));
    
    // �]�w�i�榡�ƪ���r�ϰ�e�����ݩʭ�
    UIManager.put("TextArea.foreground", Color.PINK);
    
    // �]�w�Q����ɪ��I���C�⤧�ݩʭ�
    UIManager.put("TextArea.selectionBackground", Color.GREEN);
    
    // �]�w�Q����ɪ��e���C�⤧�ݩʭ�
    UIManager.put("TextArea.selectionForeground", Color.CYAN);
    
    // ���o��r�ϰ�Pluggable Look and Feel�ݩʭ�
    System.out.println("JTextArea Look and Feel: " + UIManager.getString("TextAreaUI"));

    // �w�qLayout Manager �� GridLayout
    this.setLayout(new GridLayout(1, 2));
    
    JTextArea jtextarea1 = new JTextArea();
    // �]�w�۰ʴ��檺�W�h
    jtextarea1.setWrapStyleWord(true);
    // �]�wJTextArea���r�W�L��e�ɡA�O�_�۰ʴ���C
    jtextarea1.setLineWrap(true);
    jtextarea1.setEditable(true);
    // �]�w��r�r��
    jtextarea1.setFont(new Font("Courier New", Font.PLAIN, 11));
    
    // �]�w���b���O
    JScrollPane jscrollpane1 = new JScrollPane(jtextarea1);
    // �������b�G��W�LJTextArea�C�Ʈɤ~�۰���ܫ������b
    jscrollpane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    // �������b�G��W�LJTextArea��Ʈɤ~�۰���ܤ������b
    jscrollpane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    
    JTextArea jtextarea2 = new JTextArea();
    // �]�w�۰ʴ��檺�W�h
    jtextarea2.setWrapStyleWord(false);
    // �]�wJTextArea���r�W�L��e�ɡA�O�_�۰ʴ���C
    jtextarea2.setLineWrap(false);
    jtextarea2.setEditable(true);
    // �]�w��r�r��
    jtextarea2.setFont(new Font("Courier New", Font.PLAIN, 11));
    
    // �]�w���b���O
    JScrollPane jscrollpane2 = new JScrollPane(jtextarea2);
    // �������b�G��W�LJTextArea�C�Ʈɤ~�۰���ܫ������b
    jscrollpane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    // �������b�G��W�LJTextArea��Ʈɤ~�۰���ܤ������b
    jscrollpane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    this.add(jscrollpane1);
    this.add(jscrollpane2);

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    this.validate();
    this.setSize(new Dimension(400, 200));
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });    
  }
}
