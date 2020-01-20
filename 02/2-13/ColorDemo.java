import java.awt.*;
import java.awt.event.*;

public class ColorDemo extends java.awt.Frame {

  public static void main(String args[]){
    new ColorDemo();
  }
  
  // �غc�禡
  public ColorDemo() {
    super("Color Demo");

    Button button;
    
    final int row = 3;    // �C
    final int column = 1; // ��

    // �w�q Layout Manager �� GridLayout
    setLayout(new GridLayout(row, column));

    button = new Button("Default");
    add(button);

    button = new Button("Color");
    // �]�w�I���C��
    button.setBackground(Color.PINK);
    // �]�w�e���C��
    button.setForeground(Color.CYAN);
    add(button);

    button = new Button("Custom");
    // �ۭq�I���C��
    button.setBackground(new Color(120,50,0));
    // �ۭq�e���C��
    button.setForeground(new Color(245,185,60));
    add(button);

    // �]�w�������j�p
    this.setSize(200, 150);

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
