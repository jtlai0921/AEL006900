import java.awt.*;
import javax.swing.border.*;

public class SashBorder extends AbstractBorder {

  public static final int RAISED  = 0;  // �Y�_������
  public static final int LOWERED = 1;  // �W�U������

  private int sashType;          // �]�w�������
  private int bevelWidth = 1;    // �]�w����e��
  private int sashWidth = 10;    // �]�w��ؼe��

  private Color highlightInner;  // ���t���G�������C��
  private Color highlightOuter;  // �~�t���G�������C��  
  private Color shadowInner;     // ���t���v�������C��
  private Color shadowOuter;     // �~�t���v�������C��

  // �غc�禡
  public SashBorder(int sashType) {
    this.sashType = sashType;
  }

  // �غc�禡
  public SashBorder(int sashType, int sashWidth) {
    this(sashType, 1, sashWidth);
  }

  // �غc�禡
  public SashBorder(int sashType, int bevelWidth, int sashWidth) {
    this.sashType = sashType;

    if (bevelWidth < 1)
      this.bevelWidth = 1;
    else
      this.bevelWidth = bevelWidth;
    
    if (sashWidth < 10)
      this.sashWidth = 10;
    else
      this.sashWidth = sashWidth;
  }

  // �غc�禡
  public SashBorder(int sashType, Color highlight, Color shadow) {
    this(sashType, 1, 10, highlight.brighter(), highlight, shadow, shadow.brighter());
  }

  // �غc�禡
  public SashBorder(int sashType, Color highlightOuterColor, 
    Color highlightInnerColor, Color shadowOuterColor, 
    Color shadowInnerColor) {

    this(sashType, 1, 10, highlightOuterColor, highlightInnerColor, shadowOuterColor, shadowInnerColor);
  }

  // �غc�禡
  public SashBorder(int sashType, int bevelWidth, int sashWidth, Color highlight, Color shadow) {
    this(sashType, bevelWidth, sashWidth, highlight.brighter(), highlight, shadow, shadow.brighter());
  }

  // �غc�禡
  public SashBorder(int sashType, int bevelWidth, int sashWidth, 
    Color highlightOuterColor, Color highlightInnerColor, 
    Color shadowOuterColor, Color shadowInnerColor) {

    this(sashType, bevelWidth, sashWidth);
    this.highlightOuter = highlightOuterColor;
    this.highlightInner = highlightInnerColor;
    this.shadowOuter = shadowOuterColor;
    this.shadowInner = shadowInnerColor;
  }

  // �H���W���y�С]x, y�^�B�e�ס]width�^�ΰ��ס]height�^�ҧΦ����ϰ�ø�s���
  public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
    int[] xPoints, yPoints;

    Color oldColor = g.getColor();
    int h = height;
    int w = width;
    
    int s = sashWidth;
    int b = bevelWidth;

    g.translate(x, y);

    if (sashType == RAISED) {
      // �ϥΤ��t���G�������C��
      g.setColor(getHighlightInnerColor(c));
      // 1      
      xPoints = new int[] {s, w-s-1, w-s-b-1, s+b};
      yPoints = new int[] {0, 0, b, b};
      g.fillPolygon(xPoints, yPoints, 4);
      // 3
      xPoints = new int[] {0, s, s+b, b};
      yPoints = new int[] {s, s, s+b, s+b};
      g.fillPolygon(xPoints, yPoints, 4);
      // 4
      xPoints = new int[] {0, 0, b, b};
      yPoints = new int[] {s, h-s-1, h-s-b-1, s+b};
      g.fillPolygon(xPoints, yPoints, 4);
      // 6
      xPoints = new int[] {s, s, s+b, s+b};
      yPoints = new int[] {h-s-1, h-1, h-b-1, h-s-b-1};
      g.fillPolygon(xPoints, yPoints, 4);

      // �ϥΤ��t���v�������C��
      g.setColor(getShadowInnerColor(c));
      // 2
      xPoints = new int[] {s, s, s+b, s+b};
      yPoints = new int[] {0, s, s+b, b};
      g.fillPolygon(xPoints, yPoints, 4);
      // 5
      xPoints = new int[] {0, s, s+b, b};
      yPoints = new int[] {h-s-1, h-s-1, h-s-b-1, h-s-b-1};
      g.fillPolygon(xPoints, yPoints, 4);
      // 8
      xPoints = new int[] {w-s-1, w-s-1, w-s-b-1, w-s-b-1};
      yPoints = new int[] {h-s-1, h-1, h-b-1, h-s-b-1};
      g.fillPolygon(xPoints, yPoints, 4);
      // 11
      xPoints = new int[] {w-s-1, w-1, w-b, w-s-b-1};
      yPoints = new int[] {s, s, s+b, s+b};
      g.fillPolygon(xPoints, yPoints, 4);

      // �ϥΤ��t���G�������t��פ��C��
      g.setColor(getHighlightDarkerColor(c));
      // 7
      xPoints = new int[] {s, w-s-1, w-s-b-1, s+b};
      yPoints = new int[] {h-1, h-1, h-b-1, h-b-1};
      g.fillPolygon(xPoints, yPoints, 4);
      // 9
      xPoints = new int[] {w-1, w-s-1, w-s-b-1, w-b-1};
      yPoints = new int[] {h-s-1, h-s-1, h-s-b-1, h-s-b-1};
      g.fillPolygon(xPoints, yPoints, 4);
      // 10
      xPoints = new int[] {w-1, w-1, w-b-1, w-b-1};
      yPoints = new int[] {s, h-s-1, h-s-b-1, s+b};
      g.fillPolygon(xPoints, yPoints, 4);
      // 12
      xPoints = new int[] {w-s-1, w-s-1, w-s-b-1, w-s-b-1};
      yPoints = new int[] {0, s, s+b, b};
      g.fillPolygon(xPoints, yPoints, 4);
      
      // �ϥΥ~�t���G�������C��
      g.setColor(getHighlightOuterColor(c));
      g.drawLine(s, 0, w-s-1, 0);
      g.drawLine(0, s, s, s);
      g.drawLine(w-s-1, s, w-1, s);
      g.drawLine(s, 0, s, s);
      g.drawLine(0, s, 0, h-s-1);
      g.drawLine(s, h-s-1, s, h-1);

      // �ϥΥ~�t���v�������C��
      g.setColor(getShadowOuterColor(c));
      g.drawLine(0, h-s-1, s, h-s-1);
      g.drawLine(w-s-1, h-s-1, w-1, h-s-1);
      g.drawLine(s, h-1, w-s-1, h-1);
      g.drawLine(w-s-1, 0, w-s-1, s);
      g.drawLine(w-1, s, w-1, h-s-1);
      g.drawLine(w-s-1, h-s-1, w-s-1, h-1);

      // �ϥΤ��t���v�������C��
      g.setColor(getShadowInnerColor(c));
      // �׽u
      g.drawLine(s, 0, s+b, b);
      g.drawLine(s, s, s+b, s+b);
      g.drawLine(0, s, b, s+b);
      g.drawLine(0, h-s-1, b, h-s-b-1);
      g.drawLine(s, h-s-1, s+b, h-s-b-1);
      g.drawLine(s, h-1, s+b, h-b-1);
      g.drawLine(w-s-1, h-1, w-s-b-1, h-b-1);
      g.drawLine(w-s-1, h-s-1, w-s-b-1, h-s-b-1);
      g.drawLine(w-1, h-s-1, w-b-1, h-s-b-1);
      g.drawLine(w-1, s, w-b-1, s+b);
      g.drawLine(w-s-1, s, w-s-b-1, s+b);
      g.drawLine(w-s-1, 0, w-s-b-1, b);
   } 
    else if (sashType == LOWERED) {
      // �ϥΤ��t���v�������C��
      g.setColor(getShadowInnerColor(c));
      // 1      
      xPoints = new int[] {s, w-s-1, w-s-b-1, s+b};
      yPoints = new int[] {0, 0, b, b};
      g.fillPolygon(xPoints, yPoints, 4);
      // 3
      xPoints = new int[] {0, s, s+b, b};
      yPoints = new int[] {s, s, s+b, s+b};
      g.fillPolygon(xPoints, yPoints, 4);
      // 4
      xPoints = new int[] {0, 0, b, b};
      yPoints = new int[] {s, h-s-1, h-s-b-1, s+b};
      g.fillPolygon(xPoints, yPoints, 4);
      // 6
      xPoints = new int[] {s, s, s+b, s+b};
      yPoints = new int[] {h-s-1, h-1, h-b-1, h-s-b-1};
      g.fillPolygon(xPoints, yPoints, 4);

      // �ϥΤ��t���G�������C��
      g.setColor(getHighlightInnerColor(c));
      // 2
      xPoints = new int[] {s, s, s+b, s+b};
      yPoints = new int[] {0, s, s+b, b};
      g.fillPolygon(xPoints, yPoints, 4);
      // 5
      xPoints = new int[] {0, s, s+b, b};
      yPoints = new int[] {h-s-1, h-s-1, h-s-b-1, h-s-b-1};
      g.fillPolygon(xPoints, yPoints, 4);
      // 8
      xPoints = new int[] {w-s-1, w-s-1, w-s-b-1, w-s-b-1};
      yPoints = new int[] {h-s-1, h-1, h-b-1, h-s-b-1};
      g.fillPolygon(xPoints, yPoints, 4);
      // 11
      xPoints = new int[] {w-s-1, w-1, w-b, w-s-b-1};
      yPoints = new int[] {s, s, s+b, s+b};
      g.fillPolygon(xPoints, yPoints, 4);

      // �ϥΤ��t���v�������G��פ��C��
      g.setColor(getShadowBrighterColor(c));
      // 7
      xPoints = new int[] {s, w-s-1, w-s-b-1, s+b};
      yPoints = new int[] {h-1, h-1, h-b-1, h-b-1};
      g.fillPolygon(xPoints, yPoints, 4);
      // 9
      xPoints = new int[] {w-1, w-s-1, w-s-b-1, w-b-1};
      yPoints = new int[] {h-s-1, h-s-1, h-s-b-1, h-s-b-1};
      g.fillPolygon(xPoints, yPoints, 4);
      // 10
      xPoints = new int[] {w-1, w-1, w-b-1, w-b-1};
      yPoints = new int[] {s, h-s-1, h-s-b-1, s+b};
      g.fillPolygon(xPoints, yPoints, 4);
      // 12
      xPoints = new int[] {w-s-1, w-s-1, w-s-b-1, w-s-b-1};
      yPoints = new int[] {0, s, s+b, b};
      g.fillPolygon(xPoints, yPoints, 4);
      
      // �ϥΥ~�t���v�������C��
      g.setColor(getShadowOuterColor(c));
      g.drawLine(s, 0, w-s-1, 0);
      g.drawLine(0, s, s, s);
      g.drawLine(w-s-1, s, w-1, s);
      g.drawLine(s, 0, s, s);
      g.drawLine(0, s, 0, h-s-1);
      g.drawLine(s, h-s-1, s, h-1);

      // �ϥΥ~�t���G�������C��
      g.setColor(getHighlightOuterColor(c));
      g.drawLine(0, h-s-1, s, h-s-1);
      g.drawLine(w-s-1, h-s-1, w-1, h-s-1);
      g.drawLine(s, h-1, w-s-1, h-1);
      g.drawLine(w-s-1, 0, w-s-1, s);
      g.drawLine(w-1, s, w-1, h-s-1);
      g.drawLine(w-s-1, h-s-1, w-s-1, h-1);

      // �ϥΤ��t���G�������C��
      g.setColor(getHighlightInnerColor(c));
      // �׽u
      g.drawLine(s, 0, s+b, b);
      g.drawLine(s, s, s+b, s+b);
      g.drawLine(0, s, b, s+b);
      g.drawLine(0, h-s-1, b, h-s-b-1);
      g.drawLine(s, h-s-1, s+b, h-s-b-1);
      g.drawLine(s, h-1, s+b, h-b-1);
      g.drawLine(w-s-1, h-1, w-s-b-1, h-b-1);
      g.drawLine(w-s-1, h-s-1, w-s-b-1, h-s-b-1);
      g.drawLine(w-1, h-s-1, w-b-1, h-s-b-1);
      g.drawLine(w-1, s, w-b-1, s+b);
      g.drawLine(w-s-1, s, w-s-b-1, s+b);
      g.drawLine(w-s-1, 0, w-s-b-1, b);
    }

    g.translate(-x, -y);
    g.setColor(oldColor);
  }

  // ���o���w������ؤ��W�U���k�����O�Z��
  public Insets getBorderInsets(Component c) {
    return new Insets(sashWidth, sashWidth, sashWidth, sashWidth);
  }

  // �N���w������ؤ��W�U���k�����O�Z�����]��sashInset
  // ���椧��æ^��java.awt.Insets���O����
  public Insets getBorderInsets(Component c, Insets insets) {
    insets.left = insets.top = insets.right = insets.bottom = sashWidth;

    return insets;
  }

  // ���o��ؤ��t���G�������C��
  public Color getHighlightInnerColor(Component c) {
    Color highlight = getHighlightInnerColor();

    return highlight != null? highlight : c.getBackground().brighter();
  }

  // ���o��إ~�t���G�������C��
  public Color getHighlightOuterColor(Component c) {
    Color highlight = getHighlightOuterColor();

    return highlight != null? highlight : c.getBackground().brighter().brighter();
  }

  // ���o��ؤ��t���v�������C��
  public Color getShadowInnerColor(Component c) {
    Color shadow = getShadowInnerColor();

    return shadow != null? shadow : c.getBackground().darker();
  }

  // ���o��إ~�t���v�������C��
  public Color getShadowOuterColor(Component c) {
    Color shadow = getShadowOuterColor();

    return shadow != null? shadow : c.getBackground().darker().darker();
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

  public Color getShadowOuterColor() {
    return shadowOuter;
  }

  // ���t���G�������t��פ��C��
  private Color getHighlightDarkerColor(Component c) {
    Color highlight = getHighlightInnerColor();

    return highlight != null? highlight.darker() : c.getBackground().darker().darker();
  }

  // ���t���v�������G��פ��C��
  private Color getShadowBrighterColor(Component c) {
    Color shadow = getShadowInnerColor();

    return shadow != null? shadow.brighter() : c.getBackground().brighter().brighter();
  }

  // ���o�������
  public int getSashType() {
    return sashType;
  }

  // ���o����e��
  public int getBevelWidth() {
    return bevelWidth;
  }

  // ���o��ؼe��
  public int getSashWidth() {
    return sashWidth;
  }

  // �P�_��جO�_�����z��
  public boolean isBorderOpaque() { 
    return true; 
  }
}
