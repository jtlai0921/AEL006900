import javax.swing.*;
import javax.swing.event.*;

// ��@BoundedRangeModel����
public class ProgressRangeModel implements BoundedRangeModel {

  protected ChangeEvent changeEvent = null;
  protected EventListenerList listenerList = new EventListenerList();

  protected double value = 0.0;
  protected int extent = 0; // not used in JProgressBar
  protected int maximum = 10000;
  protected int minimum = 0;
  protected boolean isAdjusting = false; // not used in JProgressBar

  // �غc�禡
  public ProgressRangeModel() {
  }
  
  // not used in JProgressBar
  public int getExtent() {
    return extent;
  }

  // not used in JProgressBar
  public void setExtent(int newExtent) {
    // �]�w�i�צC�������ݩʭ�
    setRangeProperties(value, newExtent, minimum, maximum, isAdjusting);
  }

  // ���o�i�צC���̤j��
  public int getMaximum() {
    return maximum;
  }

  // �]�w�i�צC���̤j��
  public void setMaximum(int newMax) {
    // �]�w�i�צC�������ݩʭ�
    setRangeProperties(value, extent, minimum, newMax, isAdjusting);
  }

  // ���o�i�צC���̤p��
  public int getMinimum() {
    return minimum;
  }

  // �]�w�i�צC���̤p��
  public void setMinimum(int newMin) {
    // �]�w�i�צC�������ݩʭ�
    setRangeProperties(value, extent, newMin, maximum, isAdjusting);
  }

  // ���o�i�צC���ثe��
  public int getValue() {
    return (int)getDoubleValue();
  }

  // �]�w�i�צC�ثe����
  public void setValue(int newValue) {
    setDoubleValue((double)newValue);
  }

  // ���o�i�צC���ثe��
  public double getDoubleValue() {
    return value;
  }

  // �]�w�i�צC�ثe����
  public void setDoubleValue(double newValue) {
    // �]�w�i�צC�������ݩʭ�
    setRangeProperties(newValue, extent, minimum, maximum, isAdjusting);
  }

  // not used in JProgressBar
  public boolean getValueIsAdjusting() {
    return isAdjusting;
  }

  // not used in JProgressBar
  public void setValueIsAdjusting(boolean b) {
    // �]�w�i�צC�������ݩʭ�
    setRangeProperties(value, extent, minimum, maximum, b);
  }

  // �]�w�i�צC�������ݩʭ�
  public void setRangeProperties(int newValue, int newExtent, int newMin, int newMax,  boolean newAdjusting) {
    setRangeProperties((double)newValue, newExtent, newMin,  newMax, newAdjusting);
  }

  // �]�w�i�צC�������ݩʭ�
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
