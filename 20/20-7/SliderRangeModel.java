import javax.swing.*;
import javax.swing.event.*;

// 實作BoundedRangeModel介面
public class SliderRangeModel implements BoundedRangeModel {

  protected ChangeEvent changeEvent = null;
  protected EventListenerList listenerList = new EventListenerList();

  protected double value = 0.0;
  protected int extent = 0;
  protected int maximum = 10000;
  protected int minimum = 0;
  protected boolean isAdjusting = false;

  // 建構函式
  public SliderRangeModel() {
  }
  
  // 取得捲軸方塊的大小
  public int getExtent() {
    return extent;
  }

  // 設定捲軸方塊的大小
  public void setExtent(int newExtent) {
    // 設定捲軸的相關屬性值
    setRangeProperties(value, newExtent, minimum, maximum, isAdjusting);
  }

  // 取得捲軸的最大值
  public int getMaximum() {
    return maximum;
  }

  // 設定捲軸的最大值
  public void setMaximum(int newMax) {
    // 設定捲軸的相關屬性值
    setRangeProperties(value, extent, minimum, newMax, isAdjusting);
  }

  // 取得捲軸的最小值
  public int getMinimum() {
    return minimum;
  }

  // 設定捲軸的最小值
  public void setMinimum(int newMin) {
    // 設定捲軸的相關屬性值
    setRangeProperties(value, extent, newMin, maximum, isAdjusting);
  }

  // 取得捲軸的目前值
  public int getValue() {
    return (int)getDoubleValue();
  }

  // 設定捲軸目前的值
  public void setValue(int newValue) {
    setDoubleValue((double)newValue);
  }

  // 取得捲軸的目前值
  public double getDoubleValue() {
    return value;
  }

  // 設定捲軸目前的值
  public void setDoubleValue(double newValue) {
    // 設定捲軸的相關屬性值
    setRangeProperties(newValue, extent, minimum, maximum, isAdjusting);
  }

  // 判斷捲軸之目前值是否為使用者正在移動捲軸時所產生
  public boolean getValueIsAdjusting() {
    return isAdjusting;
  }

  // 設定valueIsAdjusting屬性值
  public void setValueIsAdjusting(boolean b) {
    // 設定捲軸的相關屬性值
    setRangeProperties(value, extent, minimum, maximum, b);
  }

  // 設定捲軸的相關屬性值
  public void setRangeProperties(int newValue, int newExtent, int newMin, int newMax,  boolean newAdjusting) {
    setRangeProperties((double)newValue, newExtent, newMin,  newMax, newAdjusting);
  }

  // 設定捲軸的相關屬性值
  public void setRangeProperties(double newValue, int newExtent, int newMin, int newMax, boolean newAdjusting) {
    if (newMax <= minimum) {
      newMax = minimum + 1;
    }
    if (Math.round(newValue) > newMax) { 
      newValue = newMax;
    }

    boolean stated = false;

    if (newValue != value) {
      value = newValue;
      stated = true;
    }
    if (newMax != maximum) {
      maximum = newMax;
      stated = true;
    }
    if (newAdjusting != isAdjusting) {
      maximum = newMax;
      isAdjusting = newAdjusting;
      stated = true;
    }

    if (stated) {
      fireStateChanged();
    }
  }

  public void addChangeListener(ChangeListener l) {
    listenerList.add(ChangeListener.class, l);
  }

  public void removeChangeListener(ChangeListener l) {
    listenerList.remove(ChangeListener.class, l);
  }

  protected void fireStateChanged() {
    Object[] listeners = listenerList.getListenerList();
    for (int i = listeners.length - 2; i >= 0; i -=2 ) {
      if (listeners[i] == ChangeListener.class) {
        if (changeEvent == null) {
          changeEvent = new ChangeEvent(this);
        }
        ((ChangeListener)listeners[i+1]).stateChanged(changeEvent);
      }
    }
  }
}
