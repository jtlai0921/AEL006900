import java.awt.*;
import javax.swing.border.*;

public class SashBorder extends AbstractBorder {

  public static final int RAISED  = 0;  // 凸起之斜邊
  public static final int LOWERED = 1;  // 凹下之斜邊

  private int sashType;          // 設定邊框類型
  private int bevelWidth = 1;    // 設定斜邊寬度
  private int sashWidth = 10;    // 設定邊框寬度

  private Color highlightInner;  // 內緣明亮部分之顏色
  private Color highlightOuter;  // 外緣明亮部分之顏色  
  private Color shadowInner;     // 內緣陰影部分之顏色
  private Color shadowOuter;     // 外緣陰影部分之顏色

  // 建構函式
  public SashBorder(int sashType) {
    this.sashType = sashType;
  }

  // 建構函式
  public SashBorder(int sashType, int sashWidth) {
    this(sashType, 1, sashWidth);
  }

  // 建構函式
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

  // 建構函式
  public SashBorder(int sashType, Color highlight, Color shadow) {
    this(sashType, 1, 10, highlight.brighter(), highlight, shadow, shadow.brighter());
  }

  // 建構函式
  public SashBorder(int sashType, Color highlightOuterColor, 
    Color highlightInnerColor, Color shadowOuterColor, 
    Color shadowInnerColor) {

    this(sashType, 1, 10, highlightOuterColor, highlightInnerColor, shadowOuterColor, shadowInnerColor);
  }

  // 建構函式
  public SashBorder(int sashType, int bevelWidth, int sashWidth, Color highlight, Color shadow) {
    this(sashType, bevelWidth, sashWidth, highlight.brighter(), highlight, shadow, shadow.brighter());
  }

  // 建構函式
  public SashBorder(int sashType, int bevelWidth, int sashWidth, 
    Color highlightOuterColor, Color highlightInnerColor, 
    Color shadowOuterColor, Color shadowInnerColor) {

    this(sashType, bevelWidth, sashWidth);
    this.highlightOuter = highlightOuterColor;
    this.highlightInner = highlightInnerColor;
    this.shadowOuter = shadowOuterColor;
    this.shadowInner = shadowInnerColor;
  }

  // 以左上角座標（x, y）、寬度（width）及高度（height）所形成的區域繪製邊框
  public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
    int[] xPoints, yPoints;

    Color oldColor = g.getColor();
    int h = height;
    int w = width;
    
    int s = sashWidth;
    int b = bevelWidth;

    g.translate(x, y);

    if (sashType == RAISED) {
      // 使用內緣明亮部分的顏色
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

      // 使用內緣陰影部分的顏色
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

      // 使用內緣明亮部分較暗色度之顏色
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
      
      // 使用外緣明亮部分的顏色
      g.setColor(getHighlightOuterColor(c));
      g.drawLine(s, 0, w-s-1, 0);
      g.drawLine(0, s, s, s);
      g.drawLine(w-s-1, s, w-1, s);
      g.drawLine(s, 0, s, s);
      g.drawLine(0, s, 0, h-s-1);
      g.drawLine(s, h-s-1, s, h-1);

      // 使用外緣陰影部分的顏色
      g.setColor(getShadowOuterColor(c));
      g.drawLine(0, h-s-1, s, h-s-1);
      g.drawLine(w-s-1, h-s-1, w-1, h-s-1);
      g.drawLine(s, h-1, w-s-1, h-1);
      g.drawLine(w-s-1, 0, w-s-1, s);
      g.drawLine(w-1, s, w-1, h-s-1);
      g.drawLine(w-s-1, h-s-1, w-s-1, h-1);

      // 使用內緣陰影部分的顏色
      g.setColor(getShadowInnerColor(c));
      // 斜線
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
      // 使用內緣陰影部分的顏色
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

      // 使用內緣明亮部分的顏色
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

      // 使用內緣陰影部分較亮色度之顏色
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
      
      // 使用外緣陰影部分的顏色
      g.setColor(getShadowOuterColor(c));
      g.drawLine(s, 0, w-s-1, 0);
      g.drawLine(0, s, s, s);
      g.drawLine(w-s-1, s, w-1, s);
      g.drawLine(s, 0, s, s);
      g.drawLine(0, s, 0, h-s-1);
      g.drawLine(s, h-s-1, s, h-1);

      // 使用外緣明亮部分的顏色
      g.setColor(getHighlightOuterColor(c));
      g.drawLine(0, h-s-1, s, h-s-1);
      g.drawLine(w-s-1, h-s-1, w-1, h-s-1);
      g.drawLine(s, h-1, w-s-1, h-1);
      g.drawLine(w-s-1, 0, w-s-1, s);
      g.drawLine(w-1, s, w-1, h-s-1);
      g.drawLine(w-s-1, h-s-1, w-s-1, h-1);

      // 使用內緣明亮部分的顏色
      g.setColor(getHighlightInnerColor(c));
      // 斜線
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

  // 取得指定物件之邊框內上下左右的內嵌距離
  public Insets getBorderInsets(Component c) {
    return new Insets(sashWidth, sashWidth, sashWidth, sashWidth);
  }

  // 將指定物件之邊框內上下左右的內嵌距離均設為sashInset
  // 執行之後並回傳java.awt.Insets類別物件
  public Insets getBorderInsets(Component c, Insets insets) {
    insets.left = insets.top = insets.right = insets.bottom = sashWidth;

    return insets;
  }

  // 取得邊框內緣明亮部分的顏色
  public Color getHighlightInnerColor(Component c) {
    Color highlight = getHighlightInnerColor();

    return highlight != null? highlight : c.getBackground().brighter();
  }

  // 取得邊框外緣明亮部分的顏色
  public Color getHighlightOuterColor(Component c) {
    Color highlight = getHighlightOuterColor();

    return highlight != null? highlight : c.getBackground().brighter().brighter();
  }

  // 取得邊框內緣陰影部分的顏色
  public Color getShadowInnerColor(Component c) {
    Color shadow = getShadowInnerColor();

    return shadow != null? shadow : c.getBackground().darker();
  }

  // 取得邊框外緣陰影部分的顏色
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

  // 內緣明亮部分較暗色度之顏色
  private Color getHighlightDarkerColor(Component c) {
    Color highlight = getHighlightInnerColor();

    return highlight != null? highlight.darker() : c.getBackground().darker().darker();
  }

  // 內緣陰影部分較亮色度之顏色
  private Color getShadowBrighterColor(Component c) {
    Color shadow = getShadowInnerColor();

    return shadow != null? shadow.brighter() : c.getBackground().brighter().brighter();
  }

  // 取得邊框類型
  public int getSashType() {
    return sashType;
  }

  // 取得斜邊寬度
  public int getBevelWidth() {
    return bevelWidth;
  }

  // 取得邊框寬度
  public int getSashWidth() {
    return sashWidth;
  }

  // 判斷邊框是否為不透明
  public boolean isBorderOpaque() { 
    return true; 
  }
}
