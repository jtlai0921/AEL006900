import java.awt.*;
import javax.swing.*;

// 膥┯SpinnerNumberModel摸
public class BlueModel extends SpinnerNumberModel {

  // 篶ㄧΑ
  public BlueModel(int value) {
    // 砞﹚Spinner絪胯ンヘ玡计 (value)
    // 砞﹚秨﹍计0 (minimum)
    // 砞﹚挡计255 (maximum)
    // 砞﹚患糤计5 (stepSize)
    super(value, 0, 255, 5);
  }

  public int getIntValue() {
    Integer value = (Integer)getValue();
    return value.intValue();
  }

  public Color getColor() {
    int value = getIntValue();

    return new Color(0, 0, value);
  }
}
