import java.awt.*;
import java.awt.print.*;
import java.awt.geom.*;
import java.awt.font.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.html.*;

import java.util.*;

public class DocToPrinter implements java.awt.print.Printable {
  JTextArea printPane; 

  PageFormat pf;
  PrinterJob prnJob;

  int currentPage = -1;
  double pageEndY = 0;
  double pageStartY = 0;
  boolean scaleWidthToFit = true; 

  // «Øºc¨ç¦¡
  public DocToPrinter(JTextArea jtextarea) {
    printPane = new JTextArea();
    printPane.setDocument(jtextarea.getDocument());
  }

  public void print() {
    pf = new PageFormat();
    prnJob = PrinterJob.getPrinterJob();

    if (prnJob.printDialog()) {
      prnJob.setPrintable(this, pf);
      
      try {
        prnJob.print();
      }
      catch (Exception ex) {
        pageStartY = 0;
        pageEndY = 0;
        currentPage = -1;

        ex.printStackTrace();
      }
    }
  }
  
  public int print(Graphics g, PageFormat pageFormat, int pageIndex) {
    Graphics2D g2d = (Graphics2D) g;

    double scale = 1.0;
    View pview;
    
    printPane.setSize((int) pageFormat.getImageableWidth(), Integer.MAX_VALUE);
    
    printPane.validate();
    
    pview = printPane.getUI().getRootView(printPane);
    
    if ((scaleWidthToFit) && (printPane.getMinimumSize().getWidth() > pageFormat.getImageableWidth())) {
      scale = pageFormat.getImageableWidth()/printPane.getMinimumSize().getWidth();
      g2d.scale(scale,scale);
    }
    
    g2d.setClip((int)(pageFormat.getImageableX()/scale), (int)(pageFormat.getImageableY()/scale),
        (int)(pageFormat.getImageableWidth()/scale), (int)(pageFormat.getImageableHeight()/scale));
    
    if (pageIndex > currentPage) {
      currentPage = pageIndex;
      pageStartY += pageEndY;
      pageEndY = g2d.getClipBounds().getHeight();
    }
    
    g2d.translate(g2d.getClipBounds().getX(), g2d.getClipBounds().getY());
    
    Rectangle region = new Rectangle(0, (int) -pageStartY, 
        (int)(printPane.getMinimumSize().getWidth()), (int)(printPane.getPreferredSize().getHeight()));

    if (printView(g2d, region, pview)) {
      return Printable.PAGE_EXISTS;
    }
    else {
      pageStartY = 0;
      pageEndY = 0;
      currentPage = -1;
      return Printable.NO_SUCH_PAGE;
    }
  }

  private boolean printView(Graphics2D g2d, Shape region, View pview) {
    boolean pageExists = false;
    
    Rectangle clipRectangle = g2d.getClipBounds();
    
    Shape childregion;
    
    View childView;

    if (pview.getViewCount() > 0) {
      for (int i = 0; i < pview.getViewCount(); i++) {
        childregion = pview.getChildAllocation(i,region);

        if (childregion != null) {
          childView = pview.getView(i);
          
          if (printView(g2d, childregion, childView)) 
            pageExists = true;
        }
      }
    } 
    else if (region.getBounds().getMaxY() >= clipRectangle.getY()) {
      pageExists = true;
      
      if ((region.getBounds().getHeight() > clipRectangle.getHeight()) && (region.intersects(clipRectangle))) {
        pview.paint(g2d,region);
      } 
      else if (region.getBounds().getY() >= clipRectangle.getY()) {
        if (region.getBounds().getMaxY() <= clipRectangle.getMaxY()) {
          pview.paint(g2d,region);
        } 
        else {
          if (region.getBounds().getY() < pageEndY) {
            pageEndY = region.getBounds().getY();
          }
        }
      }
    }
    
    return pageExists;
  }
}

