import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;

public class AquaTheme extends DefaultMetalTheme {
  // 設定Primary主題顏色
  private final ColorUIResource primary1 = new ColorUIResource(100, 150, 150);
  private final ColorUIResource primary2 = new ColorUIResource(130, 190, 190);
  private final ColorUIResource primary3 = new ColorUIResource(150, 230, 230);

  // 設定Secondary主題顏色
  private final ColorUIResource secondary1 = new ColorUIResource(110, 110, 110);
  private final ColorUIResource secondary2 = new ColorUIResource(160, 160, 160);
  private final ColorUIResource secondary3 = new ColorUIResource(220, 245, 245);

  // 取得Primary主題顏色
  protected ColorUIResource getPrimary1() { return primary1; }
  protected ColorUIResource getPrimary2() { return primary2; }
  protected ColorUIResource getPrimary3() { return primary3; }

  // 取得Secondary主題顏色
  protected ColorUIResource getSecondary1() { return secondary1; }
  protected ColorUIResource getSecondary2() { return secondary2; }
  protected ColorUIResource getSecondary3() { return secondary3; }
}
