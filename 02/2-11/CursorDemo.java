import java.awt.*;
import java.awt.event.*;

public class CursorDemo extends java.awt.Frame {

  public static void main(String args[]){
    new CursorDemo();
  }
  
  // �غc�禡
  public CursorDemo() {
    super("Cursor Demo");

    Button button;
    
    final int row = 7;    // �C
    final int column = 2; // ��

    // �w�q Layout Manager �� GridLayout
    setLayout(new GridLayout(row, column));
    
    // �w�]�ƹ�����
    button = new Button("DEFAULT");
    button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    add(button);

    // ��ܺ�T�פ��ƹ�����
    button = new Button("CROSSHAIR");
    button.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
    add(button);

    // ��ܳs�u�]��Ρ^���ƹ�����
    button = new Button("HAND");
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    add(button);

    // ���ʷƹ�����
    button = new Button("MOVE");
    button.setCursor(new Cursor(Cursor.MOVE_CURSOR));
    add(button);

    // ��ܤ�r���ƹ�����
    button = new Button("TEXT");
    button.setCursor(new Cursor(Cursor.TEXT_CURSOR));
    add(button);

    // ���ԡ]���L���^�ƹ�����
    button = new Button("WAIT");
    button.setCursor(new Cursor(Cursor.WAIT_CURSOR));
    add(button);

    // �վ㥪�������j�p���ƹ�����
    button = new Button("W RESIZE");
    button.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
    add(button);

    // �վ�W�諫���j�p���ƹ�����
    button = new Button("N RESIZE");
    button.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
    add(button);

    // �վ�U�諫���j�p���ƹ�����
    button = new Button("S RESIZE");
    button.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
    add(button);

    // �վ�k�������j�p���ƹ�����
    button = new Button("E RESIZE");
    button.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
    add(button);

    // �վ㥪�W��﨤�u�j�p���ƹ�����
    button = new Button("NW RESIZE");
    button.setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR));
    add(button);

    // �վ㥪�U��﨤�u�j�p���ƹ�����
    button = new Button("SW RESIZE");
    button.setCursor(new Cursor(Cursor.SW_RESIZE_CURSOR));
    add(button);

    // �վ�k�U��﨤�u�j�p���ƹ�����
    button = new Button("SE RESIZE");
    button.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
    add(button);

    // �վ�k�W��﨤�u�j�p���ƹ�����
    button = new Button("NE RESIZE");
    button.setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR));
    add(button);

    // �]�w�������j�p
    this.setSize(300, 300);

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
