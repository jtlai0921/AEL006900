import java.awt.*;
import java.awt.event.*;
import java.applet.*;

public class GridLayoutDemo extends java.applet.Applet {
  private String orientation ;

  public static void main(String args[]){
    GridLayoutFrame frame = new GridLayoutFrame();
    
    // �]�w�������j�p
    frame.setSize(200, 200);

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = frame.getSize();

    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;

    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;

    frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    // ��ܵ���
    frame.setVisible(true);
  }

  // �غc�禡
  public GridLayoutDemo() {
    System.out.println("Applet�غc�禡");
  }
  
  public void init() {
    System.out.println("Applet init()");

    // ���o Java Applet ���Ѽ�
    orientation = getParameter("orientation");

    final int row = 4;    // �C
    final int column = 3; // ��

    // �w�q Layout Manager �� GridLayout
    setLayout(new GridLayout(row, column));
    
    // �]�w���󪺰t�m��V
    if (orientation.equalsIgnoreCase("RIGHT_TO_LEFT"))
      setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    else
      setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    
    for (int i=0; i<row; i++) {
      for (int j=0; j<column; j++) {
        add(new java.awt.Button("(" + i + ", " + j + ")"));
      }
    }
  }
}

class GridLayoutFrame extends java.awt.Frame {
  // �غc�禡
  public GridLayoutFrame() {
    super("Grid Layout Demo");

    final int row = 4;    // �C
    final int column = 3; // ��

    // �w�q Layout Manager �� GridLayout
    setLayout(new GridLayout(row, column));

    setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    
    for (int i=0; i<row; i++) {
      for (int j=0; j<column; j++) {
        add(new java.awt.Button("(" + i + ", " + j + ")"));
      }
    }

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }
}