import java.awt.*;
import java.awt.event.*;

// ��@ItemListener����
public class CheckboxEventDemo extends java.awt.Frame implements ItemListener {

  Checkbox[] checkbox = new Checkbox[3];

  // Main method
  public static void main(String[] args) {
    new CheckboxEventDemo();
  }

  // �غc�禡
  public CheckboxEventDemo() {
    super("Checkbox Event Demo");

    String[] label = {"Basic", "Intermediate", "Advance"};

    java.awt.Panel panel1 = new Panel();
    panel1.setLayout(new GridLayout(3, 1));

    for (int i=0; i < checkbox.length; i++) {
      checkbox[i] = new Checkbox(label[i]);
      
      // ���U ItemListener
      checkbox[i].addItemListener(this);
  
      panel1.add(checkbox[i]);
    }

    add(panel1);
    
    // �]�w�������j�p
    this.setSize(200, 120);

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
  
  public void itemStateChanged(ItemEvent e) {
    String source = null;

    // ���o���Ͷ��بƥ󪺪���
    Checkbox cb = (Checkbox)(e.getItemSelectable());
    
    for (int i=0; i < checkbox.length; i++) {
      if(cb == checkbox[i]) {
        checkbox[i].setState(true);
        source = "Select " + checkbox[i].getLabel() + " checkbox";
      }
      else
        checkbox[i].setState(false);
    }

    System.out.println(source);
  }
}
