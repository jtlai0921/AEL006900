import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;
import javax.swing.plaf.basic.*;
 
public class ContrastTheme extends DefaultMetalTheme {
  // 設定Primary主題顏色
  private final ColorUIResource primary1 = new ColorUIResource(0, 0, 0);
  private final ColorUIResource primary2 = new ColorUIResource(204, 204, 204);
  private final ColorUIResource primary3 = new ColorUIResource(255, 255, 255);
  
  // 設定Secondary主題顏色
  private final ColorUIResource secondary1 = new ColorUIResource(102, 102, 102);
  private final ColorUIResource secondary2 = new ColorUIResource(204, 204, 204);
  private final ColorUIResource secondary3 = new ColorUIResource(255, 255, 255);

  private final ColorUIResource primaryHighlight = new ColorUIResource(102,102,102);
  private final ColorUIResource controlHighlight = new ColorUIResource(102,102,102);
  
  // 取得Primary主題顏色
  protected ColorUIResource getPrimary1() { return primary1; } 
  protected ColorUIResource getPrimary2() { return primary2; }
  protected ColorUIResource getPrimary3() { return primary3; }
  
  // 取得Secondary主題顏色
  protected ColorUIResource getSecondary1() { return secondary1; }
  protected ColorUIResource getSecondary2() { return secondary2; }
  protected ColorUIResource getSecondary3() { return secondary3; }

  public ColorUIResource getPrimaryControlHighlight() { return primaryHighlight;}
  public ColorUIResource getControlHighlight() { return super.getSecondary3(); }
  
  public ColorUIResource getFocusColor() { return getBlack(); }
  
  public ColorUIResource getTextHighlightColor() { return getBlack(); }
  public ColorUIResource getHighlightedTextColor() { return getWhite(); }
  
  public ColorUIResource getMenuSelectedBackground() { return getBlack(); }
  public ColorUIResource getMenuSelectedForeground() { return getWhite(); }
  public ColorUIResource getAcceleratorForeground() { return getBlack(); }
  public ColorUIResource getAcceleratorSelectedForeground() { return getWhite(); }
  
  public void addCustomEntriesToTable(UIDefaults table) {
    Border blackLineBorder = new BorderUIResource(new LineBorder( getBlack() ));
    Border whiteLineBorder = new BorderUIResource(new LineBorder( getWhite() ));
    
    Object textBorder = new BorderUIResource( new CompoundBorder(blackLineBorder, new BasicBorders.MarginBorder()));

    table.put("TextArea.border", textBorder);
    table.put("TextField.border", textBorder);
    table.put("TextPane.font", textBorder);
    table.put("ToolTip.border", blackLineBorder);
  }
}
