import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class CardLayoutDemo extends javax.swing.JApplet {
  CardLayout cardlayout = new CardLayout();

  javax.swing.JPanel jpanel1 = new JPanel();
    
  // 建構函式
  public CardLayoutDemo() {
  }

  public void init() {
    // 定義 jpanel1 之 Layout Manager 為 CardLayout
    jpanel1.setLayout(cardlayout); 
    
    // 配置各層
    jpanel1.add(new JLabel("Card 1"), "Card 1");
    jpanel1.add(new JLabel("Card 2"), "Card 2");
    jpanel1.add(new JLabel("Card 3"), "Card 3");
    jpanel1.add(new JLabel("Card 4"), "Card 4");
    jpanel1.add(new JLabel("Card 5"), "Card 5");

    // 顯示 Card 2
    cardlayout.show(jpanel1, "Card 2");

    JButton jbtnFirst = new JButton("<< First");
    jbtnFirst.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cardlayout.first(jpanel1); 
      }
    });
    JButton jbtnPrev = new JButton("< Previous");
    jbtnPrev.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cardlayout.previous(jpanel1); 
      }
    });
    JButton jbtnNext = new JButton("Next >");
    jbtnNext.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cardlayout.next(jpanel1);  
      }
    });
    JButton jbtnLast = new JButton("Last >>");
    jbtnLast.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cardlayout.last(jpanel1);  
      }
    });

    // 直接定義JApplet之Layout Manager為FlowLayout
    this.setLayout(new FlowLayout());

    // 直接將物件加至JApplet中
    this.add(jbtnFirst);
    this.add(jbtnPrev);
    this.add(jpanel1);
    this.add(jbtnNext);
    this.add(jbtnLast);
  }

  static {
    UIManager.put("swing.boldMetal", Boolean.FALSE);
    JDialog.setDefaultLookAndFeelDecorated(true);
    JFrame.setDefaultLookAndFeelDecorated(true);
    Toolkit.getDefaultToolkit().setDynamicLayout(true);

    try {
      UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
    }
    catch(Exception e) {}
  }
}
