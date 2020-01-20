import java.awt.*;
import java.awt.event.*;

// ��@ ActionListener ����
public class ButtonEventDemo extends java.awt.Frame implements ActionListener {

  Button[] button = new Button[6];

  public static void main(String[] args) {
    new ButtonEventDemo();
  }

  // �غc�禡
  public ButtonEventDemo() {
    super("Button Event Demo");

    // �w�q Layout Manager �� FlowLayout
    setLayout(new FlowLayout());

    String[] label = {"New", "Open", "Save", "Cut", "Copy", "Paste"};

    for (int i=0; i<6; i++) {
      button[i] = new Button(label[i]);

      // ���U ActionListener
      button[i].addActionListener(this);

      add(button[i]);
    }

    // �]�w�������j�p
    this.setSize(200, 200);

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
  
  // ��ʧ@�o�ͮ�
  public void actionPerformed(ActionEvent e) {
//    String source = null;
//    for (int i=0; i<6; i++) {
//      if (e.getSource().equals(button[i]))
//        source = "Press " + button[i].getActionCommand() + " button";
//    }

    String source = "Press " + e.getActionCommand() + " button";
    
    System.out.println(source);
  }
}
