import java.awt.*;
import java.awt.event.*;

public class CustomBorderDemo extends java.awt.Frame {
  
  // Main method
  public static void main(String[] args) {
    new CustomBorderDemo();
  }
  
  // �غc�禡
  public CustomBorderDemo() {
    super("Custom Border Demo");

    // �w�q Layout Manager �� BorderLayout
    this.setLayout(new BorderLayout());

    // �]�w�������j�p
    this.setSize(250, 150);

    CustomBorder border = new CustomBorder(CustomBorder.LOWERED,
      new Color(120,50,0), new Color(245,185,60), Color.RED, Color.BLUE);
      
    this.add(border, BorderLayout.CENTER);

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

class CustomBorder extends java.awt.Panel {
  public static final int RAISED  = 0;
  public static final int LOWERED = 1;

  int borderType;
  Color highlightOuter;
  Color highlightInner;
  Color shadowInner;
  Color shadowOuter;

  // �غc�禡
  public CustomBorder(int borderType) {
    this.borderType = borderType;
  }

  public CustomBorder(int borderType, Color highlight, Color shadow) {
    this(borderType, highlight.brighter(), highlight, shadow, shadow.brighter());
  }

  public CustomBorder(int borderType, Color highlightOuterColor, 
                     Color highlightInnerColor, Color shadowOuterColor, 
                     Color shadowInnerColor) {
    this(borderType);
    this.highlightOuter = highlightOuterColor;
    this.highlightInner = highlightInnerColor;
    this.shadowOuter = shadowOuterColor;
    this.shadowInner = shadowInnerColor;
  }

  public void paint(Graphics g) {
    Color oldColor = g.getColor();

    int x = 0;
    int y = 0;

    int w = this.getSize().width;
    int h = this.getSize().height;
    
    g.translate(x, y);

    if (borderType == RAISED) {
      // �]�wGraphics���󤧥ثe�C�⬰�~�t���G�������C��
      g.setColor(getHighlightOuterColor());
      // ø�s�u��
      g.drawLine(0, 0, 0, h-2);
      g.drawLine(1, 0, w-2, 0);

      // �]�wGraphics���󤧥ثe�C�⬰���t���G�������C��
      g.setColor(getHighlightInnerColor());
      // ø�s�u��
      g.drawLine(1, 1, 1, h-3);
      g.drawLine(2, 1, w-3, 1);

      // �]�wGraphics���󤧥ثe�C�⬰�~�t���v�������C��
      g.setColor(getShadowOuterColor());
      // ø�s�u��
      g.drawLine(0, h-1, w-1, h-1);
      g.drawLine(w-1, 0, w-1, h-2);

      // �]�wGraphics���󤧥ثe�C�⬰���t���v�����C��
      g.setColor(getShadowInnerColor());
      // ø�s�u��
      g.drawLine(1, h-2, w-2, h-2);
      g.drawLine(w-2, 1, w-2, h-3);
     }
    else {
      // �]�wGraphics���󤧥ثe�C�⬰���t���v�������C��
      g.setColor(getShadowInnerColor());
      // ø�s�u��
      g.drawLine(0, 0, 0, h-1);
      g.drawLine(1, 0, w-1, 0);

      // �]�wGraphics���󤧥ثe�C�⬰�~�t���v�������C��
      g.setColor(getShadowOuterColor());
      // ø�s�u��
      g.drawLine(1, 1, 1, h-2);
      g.drawLine(2, 1, w-2, 1);

      // �]�wGraphics���󤧥ثe�C�⬰�~�t���G�������C��
      g.setColor(getHighlightOuterColor());
      // ø�s�u��
      g.drawLine(1, h-1, w-1, h-1);
      g.drawLine(w-1, 1, w-1, h-2);

      // �]�wGraphics���󤧥ثe�C�⬰���t���G�������C��
      g.setColor(getHighlightInnerColor());
      // ø�s�u��
      g.drawLine(2, h-2, w-2, h-2);
      g.drawLine(w-2, 2, w-2, h-3);
    }

    g.translate(-x, -y);
    g.setColor(oldColor);
  }

  public Color getHighlightOuterColor() {
    return highlightOuter;
  }

  public Color getHighlightInnerColor() {
    return highlightInner;
  }

  public Color getShadowInnerColor() {
    return shadowInner;
  }

  public Color getShadowOuterColor(){
    return shadowOuter;
  }
}