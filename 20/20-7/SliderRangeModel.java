import javax.swing.*;
import javax.swing.event.*;

// ��@BoundedRangeModel����
public class SliderRangeModel implements BoundedRangeModel {

  protected ChangeEvent changeEvent = null;
  protected EventListenerList listenerList = new EventListenerList();

  protected double value = 0.0;
  protected int extent = 0;
  protected int maximum = 10000;
  protected int minimum = 0;
  protected boolean isAdjusting = false;

  // �غc�禡
  public SliderRangeModel() {
  }
  
  // ���o���b������j�p
  public int getExtent() {
    return extent;
  }

  // �]�w���b������j�p
  public void setExtent(int newExtent) {
    // �]�w���b�������ݩʭ�
    setRangeProperties(value, newExtent, minimum, maximum, isAdjusting);
  }

  // ���o���b���̤j��
  public int getMaximum() {
    return maximum;
  }

  // �]�w���b���̤j��
  public void setMaximum(int newMax) {
    // �]�w���b�������ݩʭ�
    setRangeProperties(value, extent, minimum, newMax, isAdjusting);
  }

  // ���o���b���̤p��
  public int getMinimum() {
    return minimum;
  }

  // �]�w���b���̤p��
  public void setMinimum(int newMin) {
    // �]�w���b�������ݩʭ�
    setRangeProperties(value, extent, newMin, maximum, isAdjusting);
  }

  // ���o���b���ثe��
  public int getValue() {
    return (int)getDoubleValue();
  }

  // �]�w���b�ثe����
  public void setValue(int newValue) {
    setDoubleValue((double)newValue);
  }

  // ���o���b���ثe��
  public double getDoubleValue() {
    return value;
  }

  // �]�w���b�ثe����
  public void setDoubleValue(double newValue) {
    // �]�w���b�������ݩʭ�
    setRangeProperties(newValue, extent, minimum, maximum, isAdjusting);
  }

  // �P�_���b���ثe�ȬO�_���ϥΪ̥��b���ʱ��b�ɩҲ���
  public boolean getValueIsAdjusting() {
    return isAdjusting;
  }

  // �]�wvalueIsAdjusting�ݩʭ�
  public void setValueIsAdjusting(boolean b) {
    // �]�w���b�������ݩʭ�
    setRangeProperties(value, extent, minimum, maximum, b);
  }

  // �]�w���b�������ݩʭ�
  public void setRangeProperties(int newValue, int newExtent, int newMin, int newMax,  boolean newAdjusting) {
    setRangeProperties((double)newValue, newExtent, newMin,  newMax, newAdjusting);
  }

  // �]�w���b�������ݩʭ�
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
