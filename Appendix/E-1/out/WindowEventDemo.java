// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 2008/10/14 ¤U¤È 05:32:29
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

import java.awt.*;
import java.awt.event.*;
import java.io.PrintStream;

public class WindowEventDemo extends Frame
  implements WindowListener, WindowFocusListener, WindowStateListener
{

  public static void main(String args[])
  {
    new WindowEventDemo();
  }

  public WindowEventDemo()
  {
    super("Window Event Demo");
    addWindowFocusListener(this);
    addWindowListener(this);
    addWindowStateListener(this);
    setSize(new Dimension(200, 200));
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension dimension1 = getSize();
    if(dimension1.height > dimension.height)
      dimension1.height = dimension.height;
    if(dimension1.width > dimension.width)
      dimension1.width = dimension.width;
    setLocation((dimension.width - dimension1.width) / 2, (dimension.height - dimension1.height) / 2);
    setVisible(true);
  }

  public void windowActivated(WindowEvent windowevent)
  {
    System.out.println("\u8996\u7A97\u72C0\u614B\u7531\u975E\u4F5C\u7528\u4E2D\u8B8A\u70BA\u4F5C\u7528\u4E2D");
  }

  public void windowClosed(WindowEvent windowevent)
  {
    System.out.println("\u8996\u7A97\u5DF2\u95DC\u9589");
  }

  public void windowClosing(WindowEvent windowevent)
  {
    System.out.println("\u8996\u7A97\u6B63\u5728\u95DC\u9589");
    dispose();
    System.exit(0);
  }

  public void windowDeactivated(WindowEvent windowevent)
  {
    System.out.println("\u8996\u7A97\u72C0\u614B\u7531\u4F5C\u7528\u4E2D\u8B8A\u70BA\u975E\u4F5C\u7528\u4E2D");
  }

  public void windowDeiconified(WindowEvent windowevent)
  {
    System.out.println("\u8996\u7A97\u72C0\u614B\u7531\u6700\u5C0F\u5316\u8B8A\u70BA\u6B63\u5E38\u72C0\u614B");
  }

  public void windowIconified(WindowEvent windowevent)
  {
    System.out.println("\u8996\u7A97\u72C0\u614B\u7531\u6B63\u5E38\u72C0\u614B\u8B8A\u70BA\u6700\u5C0F\u5316");
  }

  public void windowOpened(WindowEvent windowevent)
  {
    System.out.println("\u8996\u7A97\u958B\u555F");
  }

  public void windowGainedFocus(WindowEvent windowevent)
  {
    System.out.println("\u8996\u7A97\u53D6\u5F97\u8F38\u5165\u7126\u9EDE");
  }

  public void windowLostFocus(WindowEvent windowevent)
  {
    System.out.println("\u8996\u7A97\u5931\u53BB\u8F38\u5165\u7126\u9EDE");
  }

  public void windowStateChanged(WindowEvent windowevent)
  {
    System.out.println("\u8996\u7A97\u72C0\u614B\u6539\u8B8A");
  }
}