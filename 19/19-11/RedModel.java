import java.awt.*;
import javax.swing.*;

// 繼承SpinnerNumberModel類別
public class RedModel extends SpinnerNumberModel {

  // 建構函式
  public RedModel(int value) {
    // 設定Spinner的編輯物件目前的數字 (value)
    // 設定開始數字為0 (minimum)
    // 設定結束數字為255 (maximum)
    // 設定序列的遞增數字為5 (stepSize)
    super(value, 0, 255, 5);
  }

  public int getIntValue() {
    Integer value = (Integer)getValue();
    return value.intValue();
  }

  public Color getColor() {
    int value = getIntValue();

    return new Color(value, 0, 0);
  }
}
