import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

import javax.media.opengl.*;
import javax.media.opengl.glu.*;
import com.sun.opengl.util.*;
import com.sun.opengl.util.texture.*;

public class Model extends JPanel 
  implements GLEventListener, KeyListener, MouseListener, MouseMotionListener, ChangeListener {

  JMenu jmenu[];
  JMenuItem jmenuItem[][] = new JMenuItem[3][1];
  JRadioButtonMenuItem[] jrbmenuitem1 = new JRadioButtonMenuItem[14];
  JRadioButtonMenuItem[] jrbmenuitem2 = new JRadioButtonMenuItem[2];

  String menu[]={"Model|M", "View|V", "Help|H"};

  String menuItem[][]={
    {"Bolt|B", "Cone|C", "Cube|u", "Cylinder|y", "Dodecahedron|D", 
     "Gear|G", "Icosahedron|I", "Octahedron|O", "Rhombic Dodecahedron|R",
     "Sphere|p", "Spring|n", "Teapot|T", "Tetrahedron|e", "Torus|s"},
    {"Solid|S", "Wireframe|W"},
    {"About|A|about.gif"}
  };

  JSlider jslider1, jslider2, jslider3;

  ActionEvent action;

  float angle = 0.0f;
  float factor = 35.0f;

  static double spin = 0.0;

  float[] diffuseMaterial = { 1.0f, 1.0f, 1.0f, 1.0f };
  boolean colorChanged = true;
  boolean solid = true;
  int modelIndex = 0;
  
  int threadDirection = 1;

  javax.media.opengl.GLJPanel glpanel ;
  
  JApplet MainApplet;
  
  static {
    UIManager.put("swing.boldMetal", Boolean.FALSE);
    JDialog.setDefaultLookAndFeelDecorated(true);
    JFrame.setDefaultLookAndFeelDecorated(true);
    Toolkit.getDefaultToolkit().setDynamicLayout(true);

    try {
      UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
    }
    catch(Exception e) {}
  }

  public Model(JApplet MainApplet) {
    this.MainApplet = MainApplet;
    
    javax.media.opengl.GLCapabilities glcapabilities = new GLCapabilities();
    javax.media.opengl.GLDrawableFactory gldrawablefactory = GLDrawableFactory.getFactory();
    GLDrawable drawable = gldrawablefactory.getGLDrawable(this, glcapabilities, null);

    glcapabilities.setDoubleBuffered(true);
    
    glpanel = new GLJPanel(glcapabilities);

    glpanel.addGLEventListener(this);
    glpanel.addKeyListener(this);
    glpanel.addMouseListener(this);
    glpanel.addMouseMotionListener(this);

    final int MAJOR_TICK = 50;
    final int MINOR_TICK = 10;
    
    jslider1 = new JSlider(JSlider.HORIZONTAL, 0, 255, 255);
    jslider1.setFont(new Font("arial", Font.PLAIN, 11));
    // 設定滑動軸的主要刻度間距
    jslider1.setMajorTickSpacing(MAJOR_TICK);
    // 設定滑動軸的次要刻度間距
    jslider1.setMinorTickSpacing(MINOR_TICK);
    // 設定是否顯示滑動軸的數字標籤
    //jslider1.setPaintLabels(true);
    // 設定是否顯示滑動軸的刻度
    //jslider1.setPaintTicks(true);
    // 設定滑動軸旋鈕（Knob）是否緊延著刻度滑行
    jslider1.setSnapToTicks(true); 
    jslider1.putClientProperty("JSlider.isFilled", Boolean.TRUE);     
    jslider1.addChangeListener(this);
    
    jslider2 = new JSlider(JSlider.HORIZONTAL, 0, 255, 255);
    jslider2.setFont(new Font("arial", Font.PLAIN, 11));
    // 設定滑動軸的主要刻度間距
    jslider2.setMajorTickSpacing(MAJOR_TICK);
    // 設定滑動軸的次要刻度間距
    jslider2.setMinorTickSpacing(MINOR_TICK);
    // 設定是否顯示滑動軸的數字標籤
    //jslider2.setPaintLabels(true);
    // 設定是否顯示滑動軸的刻度
    //jslider2.setPaintTicks(true);
    // 設定滑動軸旋鈕（Knob）是否緊延著刻度滑行
    jslider2.setSnapToTicks(true); 
    jslider2.putClientProperty("JSlider.isFilled", Boolean.TRUE);     
    jslider2.addChangeListener(this);
    
    jslider3 = new JSlider(JSlider.HORIZONTAL, 0, 255, 255);
    jslider3.setFont(new Font("arial", Font.PLAIN, 11));
    // 設定滑動軸的主要刻度間距
    jslider3.setMajorTickSpacing(MAJOR_TICK);
    // 設定滑動軸的次要刻度間距
    jslider3.setMinorTickSpacing(MINOR_TICK);
    // 設定是否顯示滑動軸的數字標籤
    //jslider3.setPaintLabels(true);
    // 設定是否顯示滑動軸的刻度
    //jslider3.setPaintTicks(true);
    // 設定滑動軸旋鈕（Knob）是否緊延著刻度滑行
    jslider3.setSnapToTicks(true); 
    jslider3.putClientProperty("JSlider.isFilled", Boolean.TRUE);     
    jslider3.addChangeListener(this);

    Border border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.white, new Color(142, 142, 142)); 

    JPanel jpanel1 = new JPanel();
    jpanel1.setLayout(new BorderLayout());
    jpanel1.setBorder(BorderFactory.createTitledBorder(border, "Red", TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("arial", Font.PLAIN, 11)));
    JPanel jpanel2 = new JPanel();
    jpanel2.setLayout(new BorderLayout());
    jpanel2.setBorder(BorderFactory.createTitledBorder(border, "Green", TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("arial", Font.PLAIN, 11)));
    JPanel jpanel3 = new JPanel();
    jpanel3.setLayout(new BorderLayout());
    jpanel3.setBorder(BorderFactory.createTitledBorder(border, "Blue", TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("arial", Font.PLAIN, 11)));

    jpanel1.add(jslider1, BorderLayout.CENTER);
    jpanel2.add(jslider2, BorderLayout.CENTER);
    jpanel3.add(jslider3, BorderLayout.CENTER);

    JPanel jpanel = new JPanel();
    jpanel.setLayout(new java.awt.GridLayout(3, 0));
    jpanel.setBorder(new TitledBorder("Color"));

    jpanel.add(jpanel1);
    jpanel.add(jpanel2);
    jpanel.add(jpanel3);

    JPanel controlPanel = new JPanel();
    controlPanel.setLayout(new BorderLayout());
    controlPanel.setPreferredSize(new Dimension(150, 150));
    controlPanel.add(jpanel, BorderLayout.NORTH);

    this.setLayout(new BorderLayout());

    this.add(glpanel, BorderLayout.CENTER);
    this.add(controlPanel, BorderLayout.EAST);

    // Set MenuBar
    JMenuBar jmenubar = createMenuBar();
    MainApplet.setJMenuBar(jmenubar);

    this.validate();

    final Animator animator = new Animator(glpanel);
    this.setVisible(true);

    glpanel.requestFocusInWindow();
    animator.start();
  }

  private JMenuBar createMenuBar() {
    // Get current classloader
    ClassLoader cl = this.getClass().getClassLoader();

    JMenuBar jmenubar = new JMenuBar();

    jmenu = new JMenu[menu.length];

    // 建構群組
    ButtonGroup group1 = new ButtonGroup();
    ButtonGroup group2 = new ButtonGroup();
    
    for (int i=0; i<menu.length; i++){
      jmenu[i] = new JMenu(menu[i].substring(0, menu[i].indexOf("|")));
      jmenu[i].setMnemonic(menu[i].split("\\|")[1].charAt(0));
      jmenu[i].setFont(new Font("arial", Font.PLAIN, 11));
      jmenubar.add(jmenu[i]);
    }

    for(int i=0; i<menu.length; i++){
      for(int j=0; j<menuItem[i].length; j++){
        if (i == 0) {
          if (menuItem[i][j].equals("-")) {
            jmenu[i].addSeparator();
          }
          else {
            jrbmenuitem1[j] = new JRadioButtonMenuItem(menuItem[i][j].substring(0, menuItem[i][j].indexOf("|")));
            jrbmenuitem1[j].setMnemonic(menuItem[i][j].split("\\|")[1].charAt(0));
            jrbmenuitem1[j].setFont(new Font("arial", Font.PLAIN, 11));
  
            if (menuItem[i][j].endsWith(".gif")) 
              jrbmenuitem1[j].setIcon(new ImageIcon(cl.getResource("images/" + menuItem[i][j].substring(menuItem[i][j].lastIndexOf("|")+1))));
  
            jrbmenuitem1[j].addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                menu_actionPerformed(e);
              }
            });
  
            jmenu[i].add(jrbmenuitem1[j]);
            group1.add(jrbmenuitem1[j]);
          }          
        }
        else if (i == 1) {
          if (menuItem[i][j].equals("-")) {
            jmenu[i].addSeparator();
          }
          else {
            jrbmenuitem2[j] = new JRadioButtonMenuItem(menuItem[i][j].substring(0, menuItem[i][j].indexOf("|")));
            jrbmenuitem2[j].setMnemonic(menuItem[i][j].split("\\|")[1].charAt(0));
            jrbmenuitem2[j].setFont(new Font("arial", Font.PLAIN, 11));
  
            if (menuItem[i][j].endsWith(".gif")) 
              jrbmenuitem2[j].setIcon(new ImageIcon(cl.getResource("images/" + menuItem[i][j].substring(menuItem[i][j].lastIndexOf("|")+1))));
  
            jrbmenuitem2[j].addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                menu_actionPerformed(e);
              }
            });
  
            jmenu[i].add(jrbmenuitem2[j]);
            group2.add(jrbmenuitem2[j]);
          }          
        }
        else {
          if (menuItem[i][j].equals("-")) {
            jmenu[i].addSeparator();
          }
          else {
            jmenuItem[i][j] = new JMenuItem(menuItem[i][j].substring(0, menuItem[i][j].indexOf("|")));
            jmenuItem[i][j].setMnemonic(menuItem[i][j].split("\\|")[1].charAt(0));
            jmenuItem[i][j].setFont(new Font("arial", Font.PLAIN, 11));
  
            if (menuItem[i][j].endsWith(".gif")) 
              jmenuItem[i][j].setIcon(new ImageIcon(cl.getResource("images/" + menuItem[i][j].substring(menuItem[i][j].lastIndexOf("|")+1))));
  
            jmenuItem[i][j].addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                menu_actionPerformed(e);
              }
            });
  
            jmenu[i].add(jmenuItem[i][j]);
          }
        }
      }
    }

    jrbmenuitem1[0].setSelected(true);
    jrbmenuitem2[0].setSelected(true);
    boolean solid = true;
    int modelIndex = 0;

    return jmenubar;
  }

  public void init(GLAutoDrawable drawable) {
    float ambientLight[] = {0.4f, 0.4f, 0.4f, 1.0f };
    float mat_specular[] = { 1.0f, 1.0f, 1.0f, 1.0f };
    float light_position[] = { -50.0f, 200.0f, 200.0f, 1.0f };

    javax.media.opengl.GL gl = drawable.getGL();
    
    gl.glClearColor(0.0f, 0.1f, 0.1f, 0.0f);
    gl.glShadeModel(GL.GL_SMOOTH);
    gl.glEnable(GL.GL_DEPTH_TEST);

    gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, diffuseMaterial, 0);
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, mat_specular, 0);
    gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, 25.0f);
    gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, light_position, 0);
    gl.glEnable(GL.GL_LIGHTING);
    gl.glEnable(GL.GL_LIGHT0);
    
    gl.glEnable(GL.GL_COLOR_MATERIAL);
    gl.glColorMaterial(GL.GL_FRONT, GL.GL_DIFFUSE);
  }

  public void display(GLAutoDrawable drawable) {
    float position[] = { -50.0f, 200.0f, 200.0f, 1.0f };

    javax.media.opengl.GL gl = drawable.getGL();

    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

    angle = angle + 1.0f;      

    if (angle >= 360.0f)          
      angle = 0.0f;

    if (colorChanged) {
      gl.glColor4fv(diffuseMaterial, 0);
      colorChanged = false;
    }

    gl.glPushMatrix();

    gl.glPushMatrix();

    gl.glRotated(spin, 1.0, 0.0, 0.0);
    gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, position, 0);

    gl.glTranslated(0.0, 0.0, 1.5);
    gl.glEnable(GL.GL_LIGHTING);
    gl.glPopMatrix();

    gl.glPushMatrix();
    gl.glRotatef(angle, 1.0f, 0.0f, 0.0f);    
    gl.glRotatef(angle, 0.0f, 1.0f, 0.0f);
    gl.glRotatef(angle, 0.0f, 0.0f, 1.0f);

    drawModel(gl);

    gl.glPopMatrix();
    
    gl.glFlush();
  }
  
  public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    GL gl = drawable.getGL();
    GLU glu = new GLU();

    if(height == 0)
      height = 1;
    
    double ratio = 1.0 * (double) width/(double) height;

    gl.glMatrixMode(GL.GL_PROJECTION);
    gl.glLoadIdentity();

    gl.glViewport(0, 0, width, height);

    glu.gluPerspective(45.0, ratio, 1.0, 1000.0);

    gl.glMatrixMode(GL.GL_MODELVIEW);
    gl.glLoadIdentity();

    glu.gluLookAt(0.0, 0.0, 5.0, 0.0, 0.0, -1.0, 0.0, 1.0, 0.0);  
  }

  public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {}

  public void keyTyped(KeyEvent e) {}
  public void keyReleased(KeyEvent e) {}
  public void keyPressed(KeyEvent e) {}

  private int prevMouseX, prevMouseY;

  public void mouseEntered(MouseEvent e) {}
  public void mouseExited(MouseEvent e) {}
  public void mouseReleased(MouseEvent e) {}
  public void mouseClicked(MouseEvent e) {}
  public void mousePressed(MouseEvent e) {}
    
  public void mouseDragged(MouseEvent e) {}
  public void mouseMoved(MouseEvent e) {
    int x = e.getX();
    int y = e.getY();
    
    Dimension size = e.getComponent().getSize();

    float thetaX = 360.0f * ( (float)(x-prevMouseX)/(float)size.width);
    float thetaY = 360.0f * ( (float)(prevMouseY-y)/(float)size.height);
    
    prevMouseX = x;
    prevMouseY = y;

    spin = (spin + thetaY) % 360;
    glpanel.display();
  }

  public void stateChanged(ChangeEvent e) {
    if (e.getSource().equals(jslider1)) {
      diffuseMaterial[0] = ((float)jslider1.getValue()/255.0f);
      colorChanged = true;
    }
    else if (e.getSource().equals(jslider2)) {
      diffuseMaterial[1] = ((float)jslider2.getValue()/255.0f);
      colorChanged = true;
    }
    else if (e.getSource().equals(jslider3)) {
      diffuseMaterial[2] = ((float)jslider3.getValue()/255.0f);
      colorChanged = true;
    }

    glpanel.display();
  }
  
  public void menu_actionPerformed(ActionEvent e){
    if(e.getSource() == jmenuItem[2][0]){  // About
      JOptionPane.showMessageDialog(null, "JOGL 3D Models", "About", JOptionPane.INFORMATION_MESSAGE);
    }
    else {
      this.action = e;
      glpanel.display();
    }
  }

  private void drawModel(GL gl){
    com.sun.opengl.util.GLUT glut = new GLUT();

    if (action != null) {
      if (action.getSource() == jrbmenuitem2[0])  // Solid
        solid = true;
      else if (action.getSource() == jrbmenuitem2[1])  // Wireframe
        solid = false;
      else if (action.getSource() == jrbmenuitem1[0])  // Bolt
        modelIndex  = 0;
      else if (action.getSource() == jrbmenuitem1[1])  // Cone
        modelIndex  = 1;
      else if (action.getSource() == jrbmenuitem1[2])  // Cube
        modelIndex  = 2;
      else if (action.getSource() == jrbmenuitem1[3])  // Cylinder
        modelIndex  = 3;
      else if (action.getSource() == jrbmenuitem1[4])  // Dodecahedron
        modelIndex  = 4;
      else if (action.getSource() == jrbmenuitem1[5])  // Gear
        modelIndex  = 5;
      else if (action.getSource() == jrbmenuitem1[6]) // Icosahedron  
        modelIndex  = 6;
      else if (action.getSource() == jrbmenuitem1[7]) // Octahedron
        modelIndex  = 7;
      else if (action.getSource() == jrbmenuitem1[8]) // Rhombic Dodecahedron
        modelIndex  = 8;
      else if (action.getSource() == jrbmenuitem1[9])  // Sphere  
        modelIndex  = 9;
      else if (action.getSource() == jrbmenuitem1[10]) // Spring  
        modelIndex  = 10;
      else if (action.getSource() == jrbmenuitem1[11]) // Teapot
        modelIndex  = 11;
      else if (action.getSource() == jrbmenuitem1[12]) // Tetrahedron
        modelIndex  = 12;
      else if (action.getSource() == jrbmenuitem1[13]) // Torus
        modelIndex  = 13;
      
      switch (modelIndex) {
        case 0:
          bolt(gl);
          break;
        case 1:
          if (solid) 
            glut.glutSolidCone(0.8, 1.5, 50, 50);
          else
            glut.glutWireCone(0.8, 1.5, 50, 50);
          break;
        case 2:
          if (solid)
            glut.glutSolidCube(1.5f);
          else
            glut.glutWireCube(1.5f);
          break;
        case 3:
          if (solid)
            glut.glutSolidCylinder(0.8, 1.5, 50, 50);
          else
            glut.glutWireCylinder(0.8, 1.5, 50, 50);
          break;
        case 4:
          if (solid)
            glut.glutSolidDodecahedron();
          else
            glut.glutWireDodecahedron();
          break;
        case 5:
          gear(gl, 0.5f, 1.2f, 0.5f, 25, 0.2f);
          break;
        case 6:
          if (solid)
            glut.glutSolidIcosahedron();
          else
            glut.glutWireIcosahedron();
          break;
        case 7:
          if (solid)
            glut.glutSolidOctahedron();
          else
            glut.glutWireOctahedron();
          break;
        case 8:
          if (solid)
            glut.glutSolidRhombicDodecahedron();
          else
            glut.glutWireRhombicDodecahedron();
          break;
        case 9:
          if (solid)
            glut.glutSolidSphere(1.2, 50, 50);
          else
            glut.glutWireSphere(1.2, 50, 50);
          break;
        case 10:
          renderThread(gl, true);
          break;
        case 11:
          if (solid)
            glut.glutSolidTeapot(1.0);
          else
            glut.glutWireTeapot(1.0);
          break;
        case 12:
          if (solid)
            glut.glutSolidTetrahedron();
          else
            glut.glutWireTetrahedron();
          break;
        case 13:
          if (solid)
            glut.glutSolidTorus(0.4, 1.2, 50, 50);
          else
            glut.glutWireTorus(0.4, 1.2, 50, 50);
          break;
        default:
          break;
      }
    }
  }
  
  private void bolt(GL gl) {
    gl.glMatrixMode(GL.GL_MODELVIEW);
    gl.glPushMatrix();
    
    renderShaft(gl);
    
    gl.glPushMatrix();
    gl.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
    renderThread(gl, false);
    
    gl.glTranslatef(0.0f, 0.0f, 40.0f/factor);
    renderHead(gl);

    gl.glPopMatrix();
    gl.glPopMatrix();
  }

  private void renderShaft(GL gl) {
    float x, z, angle;      
    float height = 75.0f/factor;
    float diameter = 15.0f/factor;
    float[] normal = new float[3];
    float[][] corners = new float[2][3];  
    float step = (3.1415f/50.0f);  

    gl.glFrontFace(GL.GL_CCW);

    if (solid)
      gl.glBegin(GL.GL_QUAD_STRIP);
    else
      gl.glBegin(GL.GL_LINE_STRIP);

    float length;
    
    for(angle = (2.0f*3.1415f); angle > 0.0f; angle -= step) {
      x = diameter*(float)Math.sin((double)angle);
      z = diameter*(float)Math.cos((double)angle);
      
      corners[0][0] = x;
      corners[0][1] = -height/2.0f;
      corners[0][2] = z;
      
      corners[1][0] = x;
      corners[1][1] = height/2.0f;
      corners[1][2] = z;
      
      normal[0] = corners[1][0];
      normal[1] = 0.0f;
      normal[2] = corners[1][2];
      
      length = (float) Math.sqrt((double)((normal[0]*normal[0]) + (normal[1]*normal[1]) + (normal[2]*normal[2])));
      
      if(length == 0.0f)
        length = 1.0f;
      
      normal[0] /= length;
      normal[1] /= length;
      normal[2] /= length;
      
      gl.glNormal3fv(normal, 0);
      gl.glVertex3fv(corners[0], 0);
      gl.glVertex3fv(corners[1], 0);
    }
    
    gl.glVertex3f(diameter*(float)Math.sin(2.0*3.1415), -height/2.0f, diameter*(float)Math.cos(2.0*3.1415));
    gl.glVertex3f(diameter*(float)Math.sin(2.0*3.1415),  height/2.0f, diameter*(float)Math.cos(2.0*3.1415));
    
    gl.glEnd();
    
    if (solid)
      gl.glBegin(GL.GL_TRIANGLE_FAN);
    else
      gl.glBegin(GL.GL_LINE_STRIP);

    gl.glNormal3f(0.0f, -1.0f, 0.0f);
    gl.glVertex3f(0.0f, -height/2.0f, 0.0f);
    
    for(angle = (2.0f*3.1415f); angle > 0.0f; angle -= step) {
      x = diameter*(float)Math.sin((double)angle);
      z = diameter*(float)Math.cos((double)angle);
    
      gl.glVertex3f(x, -height/2.0f, z);
    }
    
    gl.glVertex3f(diameter*(float)Math.sin(2.0*3.1415), -height/2.0f, diameter*(float)Math.cos(2.0*3.1415));
    gl.glEnd();
  }

  float zstep = .125f/factor;

  private void renderThread(GL gl, boolean elasticity)  {
    float x,y,z,angle;  
    float height = 75.0f/factor;  
    float diameter = 15.0f/factor;

    float[] normal = new float[3];
    float[][] corners = new float[4][3];  

    float step = (3.1415f/32.0f);  
    float revolutions = 8.0f;    
    float threadWidth = 2.0f/factor;  
    float threadThick = 3.0f/factor;  
    
    float PI2 = 2.0f*3.1415f;
    float length;
    
    if (elasticity) {
      if (threadDirection == 1) {
        zstep = zstep + .025f/factor;
        
        if (zstep > .25f/factor) {
          threadDirection = -1;
        }
      }
      else {
        zstep = zstep - .025f/factor;
        
        if (zstep <= .000f/factor) {
          threadDirection = 1;
        }
      }
    }
    else {
      zstep = .125f/factor;
    }
    
    z = -height/2.0f+2/factor;  
    
    for(angle = 0.0f; angle < PI2*revolutions; angle += step)  {
      x = diameter*(float)Math.sin((double)angle);
      y = diameter*(float)Math.cos((double)angle);
    
      corners[0][0] = x;
      corners[0][1] = y;
      corners[0][2] = z;
    
      x = (diameter+threadWidth)*(float)Math.sin((double)angle);
      y = (diameter+threadWidth)*(float)Math.cos((double)angle);
    
      corners[1][0] = x;
      corners[1][1] = y;
      corners[1][2] = z;
    
      x = (diameter+threadWidth)*(float)Math.sin((double)(angle+step));
      y = (diameter+threadWidth)*(float)Math.cos((double)(angle+step));
    
      corners[2][0] = x;
      corners[2][1] = y;
      corners[2][2] = z + zstep;
    
      x = (diameter)*(float)Math.sin((double)(angle+step));
      y = (diameter)*(float)Math.cos((double)(angle+step));
    
      corners[3][0] = x;
      corners[3][1] = y;
      corners[3][2] = z+ zstep;
  
      gl.glFrontFace(GL.GL_CCW);    

      if (solid)
        gl.glBegin(GL.GL_TRIANGLES);
      else
        gl.glBegin(GL.GL_LINE_STRIP);
    
      float[] v1 = new float[3];
      float[] v2 = new float[3];
  
      v1[0] = corners[0][0] - corners[1][0];
      v1[1] = corners[0][1] - corners[1][1];
      v1[2] = corners[0][2] - corners[1][2];
      
      v2[0] = corners[1][0] - corners[2][0];
      v2[1] = corners[1][1] - corners[2][1];
      v2[2] = corners[1][2] - corners[2][2];
      
      normal[0] = v1[1]*v2[2] - v1[2]*v2[1];
      normal[1] = v1[2]*v2[0] - v1[0]*v2[2];
      normal[2] = v1[0]*v2[1] - v1[1]*v2[0];
      
      length = (float) Math.sqrt((double)((normal[0]*normal[0]) + (normal[1]*normal[1]) + (normal[2]*normal[2])));
      
      if(length == 0.0f)
        length = 1.0f;
      
      normal[0] /= length;
      normal[1] /= length;
      normal[2] /= length;
        
      gl.glNormal3fv(normal, 0);
  
      gl.glVertex3fv(corners[0], 0);
      gl.glVertex3fv(corners[1], 0);
      gl.glVertex3fv(corners[2], 0);
  
      gl.glVertex3fv(corners[2], 0);
      gl.glVertex3fv(corners[3], 0);
      gl.glVertex3fv(corners[0], 0);
    
      gl.glEnd();
    
      corners[0][2] += threadThick;
      corners[3][2] += threadThick;
    
      v1[0] = corners[0][0] - corners[1][0];
      v1[1] = corners[0][1] - corners[1][1];
      v1[2] = corners[0][2] - corners[1][2];
      
      v2[0] = corners[1][0] - corners[2][0];
      v2[1] = corners[1][1] - corners[2][1];
      v2[2] = corners[1][2] - corners[2][2];
      
      normal[0] = v1[1]*v2[2] - v1[2]*v2[1];
      normal[1] = v1[2]*v2[0] - v1[0]*v2[2];
      normal[2] = v1[0]*v2[1] - v1[1]*v2[0];
      
      length = (float) Math.sqrt((double)((normal[0]*normal[0]) + (normal[1]*normal[1]) + (normal[2]*normal[2])));
      
      if(length == 0.0f)
        length = 1.0f;
      
      normal[0] /= length;
      normal[1] /= length;
      normal[2] /= length;
            
      normal[0] = -normal[0];
      normal[1] = -normal[1];
      normal[2] = -normal[2];
          
      gl.glFrontFace(GL.GL_CW);
    
      if (solid)
        gl.glBegin(GL.GL_TRIANGLES);
      else
        gl.glBegin(GL.GL_LINE_STRIP);

      gl.glNormal3fv(normal, 0);
  
      gl.glVertex3fv(corners[0], 0);
      gl.glVertex3fv(corners[1], 0);
      gl.glVertex3fv(corners[2], 0);
  
      gl.glVertex3fv(corners[2], 0);
      gl.glVertex3fv(corners[3], 0);
      gl.glVertex3fv(corners[0], 0);
    
      gl.glEnd();
    
      z += zstep;
    }
  }

  private void renderHead(GL gl) {
    float x,y,angle;              
    float height = 15.0f/factor;  
    float diameter = 25.0f/factor;

    float[] normal = new float[3];
    float[][] corners = new float[4][3];  

    float step = (3.1415f/3.0f);  
    float length;
  
    gl.glFrontFace(GL.GL_CCW);

    if (solid)
      gl.glBegin(GL.GL_TRIANGLE_FAN);
    else
        gl.glBegin(GL.GL_LINE_STRIP);
    
    gl.glNormal3f(0.0f, 0.0f, 1.0f);
    
    gl.glVertex3f(0.0f, 0.0f, height/2.0f);
    
    gl.glVertex3f(0.0f, diameter, height/2.0f);
    
    for(angle = (2.0f*3.1415f)-step; angle >= 0; angle -= step) {
      x = diameter*(float)Math.sin((double)angle);
      y = diameter*(float)Math.cos((double)angle);
      
      gl.glVertex3f(x, y, height/2.0f);
    }
    
    gl.glVertex3f(0.0f, diameter, height/2.0f);
    
    gl.glEnd();
    
    if (solid) 
      gl.glBegin(GL.GL_TRIANGLE_FAN); 
    else 
      gl.glBegin(GL.GL_LINE_STRIP);
    
    gl.glNormal3f(0.0f, 0.0f, -1.0f);
    
    gl.glVertex3f(0.0f, 0.0f, -height/2.0f);
    
    for(angle = 0.0f; angle < (2.0f*3.1415f); angle += step) {
      x = diameter*(float)Math.sin((double)angle);
      y = diameter*(float)Math.cos((double)angle);
      
      gl.glVertex3f(x, y, -height/2.0f);
    }
    
    gl.glVertex3f(0.0f, diameter, -height/2.0f);
    
    gl.glEnd();
    
    if (solid)
      gl.glBegin(GL.GL_QUADS);
    else
      gl.glBegin(GL.GL_LINE_STRIP);

    for(angle = 0.0f; angle < (2.0f*3.1415f); angle += step) {
      x = diameter*(float)Math.sin((double)angle);
      y = diameter*(float)Math.cos((double)angle);
      
      corners[0][0] = x;
      corners[0][1] = y;
      corners[0][2] = -height/2.0f;
      
      corners[1][0] = x;
      corners[1][1] = y;
      corners[1][2] = height/2.0f;
      
      x = diameter*(float)Math.sin((double)(angle+step));
      y = diameter*(float)Math.cos((double)(angle+step));
      
      if(angle+step < 3.1415*2.0)  {
        corners[2][0] = x;
        corners[2][1] = y;
        corners[2][2] = height/2.0f;
        
        corners[3][0] = x;
        corners[3][1] = y;
        corners[3][2] = -height/2.0f;
      }
      else {
        corners[2][0] = 0.0f;
        corners[2][1] = diameter;
        corners[2][2] = height/2.0f;
        
        corners[3][0] = 0.0f;
        corners[3][1] = diameter;
        corners[3][2] = -height/2.0f;
      }
      
      float[] v1 = new float[3];
      float[] v2 = new float[3];
  
      v1[0] = corners[0][0] - corners[1][0];
      v1[1] = corners[0][1] - corners[1][1];
      v1[2] = corners[0][2] - corners[1][2];
      
      v2[0] = corners[1][0] - corners[2][0];
      v2[1] = corners[1][1] - corners[2][1];
      v2[2] = corners[1][2] - corners[2][2];
      
      normal[0] = v1[1]*v2[2] - v1[2]*v2[1];
      normal[1] = v1[2]*v2[0] - v1[0]*v2[2];
      normal[2] = v1[0]*v2[1] - v1[1]*v2[0];
      
      length = (float) Math.sqrt((double)((normal[0]*normal[0]) + (normal[1]*normal[1]) + (normal[2]*normal[2])));
      
      if(length == 0.0f)
        length = 1.0f;

      normal[0] /= length;
      normal[1] /= length;
      normal[2] /= length;
  
      gl.glNormal3fv(normal, 0);
      
      gl.glVertex3fv(corners[0], 0);
      gl.glVertex3fv(corners[1], 0);
      gl.glVertex3fv(corners[2], 0);
      gl.glVertex3fv(corners[3], 0);
    }
    
    gl.glEnd();
  }
  
  private void gear(GL gl, float inner_radius, float outer_radius, float width, int teeth, float tooth_depth) {
    int i;
    float r0, r1, r2;
    float angle, da;
    float u, v, len;

    r0 = inner_radius;
    r1 = outer_radius - tooth_depth / 2.0f;
    r2 = outer_radius + tooth_depth / 2.0f;
            
    da = 2.0f * (float) Math.PI / teeth / 4.0f;
            
    gl.glShadeModel(GL.GL_FLAT);

    gl.glNormal3f(0.0f, 0.0f, 1.0f);

    if (solid)
      gl.glBegin(GL.GL_QUAD_STRIP);
    else
      gl.glBegin(GL.GL_LINE_STRIP);
    
    for (i = 0; i <= teeth; i++) {
      angle = i * 2.0f * (float) Math.PI / teeth;
      gl.glVertex3f(r0 * (float)Math.cos(angle), r0 * (float)Math.sin(angle), width * 0.5f);
      gl.glVertex3f(r1 * (float)Math.cos(angle), r1 * (float)Math.sin(angle), width * 0.5f);
      
      if(i < teeth) {
        gl.glVertex3f(r0 * (float)Math.cos(angle), r0 * (float)Math.sin(angle), width * 0.5f);
        gl.glVertex3f(r1 * (float)Math.cos(angle + 3.0f * da), r1 * (float)Math.sin(angle + 3.0f * da), width * 0.5f);
      }
    }

    gl.glEnd();

    if (solid)
      gl.glBegin(GL.GL_QUADS);
    else
      gl.glBegin(GL.GL_LINE_STRIP);

    for (i = 0; i < teeth; i++) {
      angle = i * 2.0f * (float) Math.PI / teeth;
      gl.glVertex3f(r1 * (float)Math.cos(angle), r1 * (float)Math.sin(angle), width * 0.5f);
      gl.glVertex3f(r2 * (float)Math.cos(angle + da), r2 * (float)Math.sin(angle + da), width * 0.5f);
      gl.glVertex3f(r2 * (float)Math.cos(angle + 2.0f * da), r2 * (float)Math.sin(angle + 2.0f * da), width * 0.5f);
      gl.glVertex3f(r1 * (float)Math.cos(angle + 3.0f * da), r1 * (float)Math.sin(angle + 3.0f * da), width * 0.5f);
    }
    gl.glEnd();
    
    if (solid)
      gl.glBegin(GL.GL_QUAD_STRIP);
    else
      gl.glBegin(GL.GL_LINE_STRIP);

    for (i = 0; i <= teeth; i++) {
      angle = i * 2.0f * (float) Math.PI / teeth;
      gl.glVertex3f(r1 * (float)Math.cos(angle), r1 * (float)Math.sin(angle), -width * 0.5f);
      gl.glVertex3f(r0 * (float)Math.cos(angle), r0 * (float)Math.sin(angle), -width * 0.5f);
      gl.glVertex3f(r1 * (float)Math.cos(angle + 3 * da), r1 * (float)Math.sin(angle + 3 * da), -width * 0.5f);
      gl.glVertex3f(r0 * (float)Math.cos(angle), r0 * (float)Math.sin(angle), -width * 0.5f);
    }
    gl.glEnd();
    
    if (solid)
      gl.glBegin(GL.GL_QUADS);
    else
      gl.glBegin(GL.GL_LINE_STRIP);

    for (i = 0; i < teeth; i++) {
      angle = i * 2.0f * (float) Math.PI / teeth;
      gl.glVertex3f(r1 * (float)Math.cos(angle + 3 * da), r1 * (float)Math.sin(angle + 3 * da), -width * 0.5f);
      gl.glVertex3f(r2 * (float)Math.cos(angle + 2 * da), r2 * (float)Math.sin(angle + 2 * da), -width * 0.5f);
      gl.glVertex3f(r2 * (float)Math.cos(angle + da), r2 * (float)Math.sin(angle + da), -width * 0.5f);
      gl.glVertex3f(r1 * (float)Math.cos(angle), r1 * (float)Math.sin(angle), -width * 0.5f);
    }
    gl.glEnd();
    
    if (solid)
      gl.glBegin(GL.GL_QUAD_STRIP);
    else
      gl.glBegin(GL.GL_LINE_STRIP);

    for (i = 0; i < teeth; i++) {
      angle = i * 2.0f * (float) Math.PI / teeth;
      gl.glVertex3f(r1 * (float)Math.cos(angle), r1 * (float)Math.sin(angle), width * 0.5f);
      gl.glVertex3f(r1 * (float)Math.cos(angle), r1 * (float)Math.sin(angle), -width * 0.5f);
      u = r2 * (float)Math.cos(angle + da) - r1 * (float)Math.cos(angle);
      v = r2 * (float)Math.sin(angle + da) - r1 * (float)Math.sin(angle);
      len = (float)Math.sqrt(u * u + v * v);
      u /= len;
      v /= len;
      gl.glNormal3f(v, -u, 0.0f);
      gl.glVertex3f(r2 * (float)Math.cos(angle + da), r2 * (float)Math.sin(angle + da), width * 0.5f);
      gl.glVertex3f(r2 * (float)Math.cos(angle + da), r2 * (float)Math.sin(angle + da), -width * 0.5f);
      gl.glNormal3f((float)Math.cos(angle), (float)Math.sin(angle), 0.0f);
      gl.glVertex3f(r2 * (float)Math.cos(angle + 2 * da), r2 * (float)Math.sin(angle + 2 * da), width * 0.5f);
      gl.glVertex3f(r2 * (float)Math.cos(angle + 2 * da), r2 * (float)Math.sin(angle + 2 * da), -width * 0.5f);
      u = r1 * (float)Math.cos(angle + 3 * da) - r2 * (float)Math.cos(angle + 2 * da);
      v = r1 * (float)Math.sin(angle + 3 * da) - r2 * (float)Math.sin(angle + 2 * da);
      gl.glNormal3f(v, -u, 0.0f);
      gl.glVertex3f(r1 * (float)Math.cos(angle + 3 * da), r1 * (float)Math.sin(angle + 3 * da), width * 0.5f);
      gl.glVertex3f(r1 * (float)Math.cos(angle + 3 * da), r1 * (float)Math.sin(angle + 3 * da), -width * 0.5f);
      gl.glNormal3f((float)Math.cos(angle), (float)Math.sin(angle), 0.0f);
    }
    gl.glVertex3f(r1 * (float)Math.cos(0), r1 * (float)Math.sin(0), width * 0.5f);
    gl.glVertex3f(r1 * (float)Math.cos(0), r1 * (float)Math.sin(0), -width * 0.5f);
    gl.glEnd();
    
    gl.glShadeModel(GL.GL_SMOOTH);
    
    if (solid)
      gl.glBegin(GL.GL_QUAD_STRIP);
    else
      gl.glBegin(GL.GL_LINE_STRIP);

    for (i = 0; i <= teeth; i++) {
      angle = i * 2.0f * (float) Math.PI / teeth;
      gl.glNormal3f(-(float)Math.cos(angle), -(float)Math.sin(angle), 0.0f);
      gl.glVertex3f(r0 * (float)Math.cos(angle), r0 * (float)Math.sin(angle), -width * 0.5f);
      gl.glVertex3f(r0 * (float)Math.cos(angle), r0 * (float)Math.sin(angle), width * 0.5f);
    }
    gl.glEnd();
  }
}
