import java.awt.*;
import java.awt.event.*;

public class ButtonDemo extends java.awt.Frame {

  public static void main(String args[]){
    new ButtonDemo();
  }
  
  // �غc�禡
  public ButtonDemo() {
    super("Button Demo");

    final int row = 3;    // �C
    final int column = 2; // ��

    // �w�q Layout Manager �� GridLayout
    setLayout(new GridLayout(row, column));

    java.awt.Button button;
    
    // �غc�禡 1
    button = new Button();
    // �]�wButton���O����r����
    button.setLabel("OK");
    add(button);

    // �غc�禡 2
    button = new Button("Cancel");
    add(button);

    // �]�w�r�� - �ʱ���r��
    button = new Button("Font Demo");
    button.setFont(new Font("dialog", Font.BOLD | Font.ITALIC, 10));
    add(button);

    // �]�w�C��
    button = new Button("Color Demo");
    // �]�w�I���C��
    button.setBackground(new Color(120,50,0));
    // �]�w�e���C��
    button.setForeground(new Color(245,185,60));
    add(button);

    // �]�w�O�_�^������
    button = new Button("Disable");
    button.setEnabled(false);
    add(button);

    // �]�w���ë��O�s
    button = new Button("Invisible");
    button.setVisible(false);
    add(button);

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
}
