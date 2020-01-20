import java.awt.*;
import java.awt.event.*;

public class CardLayoutDemo extends java.awt.Frame {
  java.awt.CardLayout cardlayout = new CardLayout();
  java.awt.Panel panel1 = new Panel();

  public static void main(String args[]){
    new CardLayoutDemo();
  }
  
  // �غc�禡
  public CardLayoutDemo() {
    super("Card Layout Demo");

    // �w�q panel1 �� Layout Manager �� CardLayout
    panel1.setLayout(cardlayout); 
    
    // �t�m�U�h�ù�C�@�h�]�w�@�W��
    panel1.add(new Label("Card 1"), "Card 1");
    panel1.add(new Label("Card 2"), "Card 2");
    panel1.add(new Label("Card 3"), "Card 3");
    panel1.add(new Label("Card 4"), "Card 4");
    panel1.add(new Label("Card 5"), "Card 5");

    // ��� Card 2
    cardlayout.show(panel1, "Card 2");
    
    Button btnFirst = new Button("<< First");
    btnFirst.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cardlayout.first(panel1); 
      }
    });
    
    Button btnPrev = new Button("< Previous");
    btnPrev.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cardlayout.previous(panel1); 
      }
    });
    
    Button btnNext = new Button("Next >");
    btnNext.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cardlayout.next(panel1);  
      }
    });
    
    Button btnLast = new Button("Last >>");
    btnLast.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cardlayout.last(panel1);  
      }
    });

    // �]�wFrame��Layout Manager
    this.setLayout(new FlowLayout()); 
    
    // �N����[��Frame��
    add(btnFirst);
    add(btnPrev);
    add(panel1);
    add(btnNext);
    add(btnLast);

    // �]�w�������j�p
    this.setSize(350, 100);

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