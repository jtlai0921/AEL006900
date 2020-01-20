import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

import java.awt.Image;
import java.awt.image.*;
import java.io.*;
import java.nio.*;
import java.text.*;
import java.util.*;
import java.lang.Math;

// JOGL
import javax.media.opengl.*;
import javax.media.opengl.glu.*;
import com.sun.opengl.util.*;
import com.sun.opengl.util.texture.*;

public class MoveLight extends JPanel implements GLEventListener, ChangeListener { 
  JMenu jmenu[];
  JMenuItem jmenuItem[][] = new JMenuItem[1][1];

  String menu[]={"Help|H"};

  String menuItem[][]={
    {"About|A|about.gif"}
  };

  JApplet MainApplet;

  JSlider jslider1, jslider2, jslider3;
  
  // JOGL
  javax.media.opengl.GLJPanel glpanel = null;
  javax.media.opengl.GL gl = null;
  javax.media.opengl.glu.GLU glu = new GLU();
  com.sun.opengl.util.GLUT glut = new GLUT();
  GLDrawable drawable = null;

  // Red, Green, and Blue light positions
  float[] lightPositionR = { 0.0f, 0.0f, 75.0f, 1.0f };

  // World light properties: diffuse, specular, position
  float[] diffuseLight = { 0.5f, 0.5f, 0.5f, 1.0f };   
  float[] specularLight = { 1.0f, 1.0f, 1.0f, 1.0f };   
  float[] lightPosition = { 0.0f, 0.0f, 100.0f, 1.0f };

  float objectXRot;   // the cube's x rotation
  float objectYRot;   // the cube's y rotation
  float objectZRot;   // the cube's z rotation
  
  float redXRot;      // red light x rotation
  float redYRot;      // red light y rotation

  float[] diffuseMaterial = { 1.0f, 1.0f, 1.0f, 1.0f };
  boolean colorChanged = true;
  
  Texture cubeTex;

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

  // Constructor
  public MoveLight(JApplet MainApplet) {
    this.MainApplet = MainApplet;
    
    GLCapabilities glcapabilities = new GLCapabilities();
    GLDrawableFactory gldrawablefactory = GLDrawableFactory.getFactory();
    drawable = gldrawablefactory.getGLDrawable(this, glcapabilities, null);
    glcapabilities.setDoubleBuffered(true);
    
    glpanel = new GLJPanel(glcapabilities);
    glpanel.addGLEventListener(this);

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
    ButtonGroup group = new ButtonGroup();
    
    for (int i=0; i<menu.length; i++){
      jmenu[i] = new JMenu(menu[i].substring(0, menu[i].indexOf("|")));
      jmenu[i].setMnemonic(menu[i].split("\\|")[1].charAt(0));
      jmenu[i].setFont(new Font("arial", Font.PLAIN, 11));
      jmenubar.add(jmenu[i]);
    }

    for(int i=0; i<menu.length; i++){
      for(int j=0; j<menuItem[i].length; j++){
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

    return jmenubar;
  }
  
  public void init(GLAutoDrawable drawable) {
    gl = drawable.getGL();

    gl.glShadeModel(GL.GL_SMOOTH);    
    gl.glEnable(GL.GL_DEPTH_TEST);    
    gl.glEnable(GL.GL_CULL_FACE);     
    gl.glFrontFace(GL.GL_CCW);        
  
    gl.glEnable(GL.GL_LIGHTING);      
    
    gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, diffuseLight, 0);
    gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, specularLight, 0);
    gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, lightPosition, 0);
    gl.glLightf(GL.GL_LIGHT0, GL.GL_SPOT_CUTOFF, 40.0f);
    gl.glLightf(GL.GL_LIGHT0, GL.GL_SPOT_EXPONENT, 80.0f);
  
    gl.glLightfv(GL.GL_LIGHT1, GL.GL_DIFFUSE, diffuseMaterial, 0);
    gl.glLightfv(GL.GL_LIGHT1, GL.GL_SPECULAR, diffuseMaterial, 0);
    gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, lightPositionR, 0);

    gl.glEnable(GL.GL_LIGHT0);
    gl.glEnable(GL.GL_LIGHT1);
  
    gl.glEnable(GL.GL_COLOR_MATERIAL);
    gl.glColorMaterial(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE);
  
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, specularLight, 0);
    gl.glMateriali(GL.GL_FRONT, GL.GL_SHININESS, 128);
  
    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

    cubeTex = loadTexture("opengl.jpg");
  }

  private Texture loadTexture(String filename) {
    Texture tex = null;

    // Get current classloader
    ClassLoader cl = this.getClass().getClassLoader();
    
    try {
      tex = TextureIO.newTexture(cl.getResourceAsStream("images/" + filename), false, TextureIO.JPG);
      tex.setTexParameteri(GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
      tex.setTexParameteri(GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
      tex.setTexParameteri(GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
      tex.setTexParameteri(GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
    }
    catch(Exception e) { 
      System.out.println("Error loading texture " + filename);  
    }

    return tex;
  }  

  public void display(GLAutoDrawable drawable) {
    gl = drawable.getGL();

    objectXRot += 0.5f;
    objectYRot += 1.0f;
    objectZRot += 0.5f;
    
    redXRot += 1.8f;
    redYRot += 0.6f;

    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
  
    gl.glLoadIdentity();                    
  
    gl.glTranslatef(0.0f, 0.0f, -150.0f);
  
    gl.glPushMatrix();
    gl.glRotatef(redYRot, 0.0f, 1.0f, 0.0f);
    gl.glRotatef(redXRot, 1.0f, 0.0f, 0.0f);

    gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, lightPositionR, 0);

    if (colorChanged) {
      gl.glLightfv(GL.GL_LIGHT1, GL.GL_DIFFUSE, diffuseMaterial, 0);
      gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, lightPositionR, 0);
      gl.glLightfv(GL.GL_LIGHT1, GL.GL_SPECULAR, diffuseMaterial, 0);
      gl.glColor4fv(diffuseMaterial, 0);
      colorChanged = false;
    }
    
    gl.glTranslatef(lightPositionR[0], lightPositionR[1], lightPositionR[2]);

    gl.glPushAttrib(GL.GL_LIGHTING_BIT);
    gl.glDisable(GL.GL_LIGHTING);  

    gl.glColor4fv(diffuseMaterial, 0);
    glut.glutSolidSphere(2.5, 10, 10);

    gl.glEnable(GL.GL_LIGHTING);
    gl.glPopAttrib();      
    gl.glPopMatrix();

    gl.glPushMatrix();

    gl.glColor3f(1.0f, 1.0f, 1.0f);
    gl.glRotatef(objectXRot, 1.0f, 0.0f, 0.0f);
    gl.glRotatef(objectYRot, 0.0f, 1.0f, 0.0f);
    gl.glRotatef(objectZRot, 0.0f, 0.0f, 1.0f);

    drawCube();

    gl.glPopMatrix();

    gl.glFlush();
    drawable.swapBuffers();
  }

  public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    GL gl = drawable.getGL();

    if (height == 0)
      height = 1;
      
    gl.glViewport(0, 0, width, height);

    gl.glMatrixMode(GL.GL_PROJECTION);
    gl.glLoadIdentity();

    glu.gluPerspective(54.0, (double)width/(double)height, 1.0, 1000.0);
  
    gl.glMatrixMode(GL.GL_MODELVIEW);
    gl.glLoadIdentity();
  }

  public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {}

  private void drawCube(){
    float[] cubeColor = { 1.0f, 1.0f, 1.0f, 1.0f };
    
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, cubeColor, 0);
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, cubeColor, 0);
    gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, 50.0f);

    gl.glEnable(GL.GL_TEXTURE_2D);
    gl.glTexEnvf(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_MODULATE);
    // Enable texture
    cubeTex.enable();
    // Bind texture
    cubeTex.bind();

    gl.glBegin(GL.GL_QUADS);

      // front
      gl.glNormal3f(0.0f, 0.0f, 1.0f);
      gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-30.0f, -30.0f, 30.0f);
      gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( 30.0f, -30.0f, 30.0f);
      gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( 30.0f,  30.0f, 30.0f);
      gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-30.0f,  30.0f, 30.0f);
    
      // back
      gl.glNormal3f(0.0f, 0.0f, -1.0f);
      gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f( 30.0f, -30.0f, -30.0f);
      gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-30.0f, -30.0f, -30.0f);
      gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-30.0f,  30.0f, -30.0f);
      gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f( 30.0f,  30.0f, -30.0f);
    
      // top
      gl.glNormal3f(0.0f, 1.0f, 0.0f);
      gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-30.0f, 30.0f,  30.0f);
      gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( 30.0f, 30.0f,  30.0f);
      gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( 30.0f, 30.0f, -30.0f);
      gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-30.0f, 30.0f, -30.0f);
    
      // bottom
      gl.glNormal3f(0.0f, -1.0f, 0.0f);
      gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-30.0f, -30.0f, -30.0f);
      gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( 30.0f, -30.0f, -30.0f);
      gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( 30.0f, -30.0f,  30.0f);
      gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-30.0f, -30.0f,  30.0f);
    
      // left
      gl.glNormal3f(-1.0f, 0.0f, 0.0f);
      gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-30.0f, -30.0f, -30.0f);
      gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-30.0f, -30.0f,  30.0f);
      gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-30.0f,  30.0f,  30.0f);
      gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-30.0f,  30.0f, -30.0f);
    
      // right
      gl.glNormal3f(1.0f, 0.0f, 0.0f);
      gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(30.0f, -30.0f,  30.0f);
      gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(30.0f, -30.0f, -30.0f);
      gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(30.0f,  30.0f, -30.0f);
      gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(30.0f,  30.0f,  30.0f);
  
    gl.glEnd();
    cubeTex.disable();  
  
    gl.glDisable(GL.GL_TEXTURE_2D);
  }

  // Methods required for the implementation of ChangeListener
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
    if(e.getSource() == jmenuItem[0][0]){  // About
      JOptionPane.showMessageDialog(null, "JOGL Moving and Rotating Light.\r\n\r\nOriginal idea from OpenGL Game Programming in C.", "About", JOptionPane.INFORMATION_MESSAGE);
    }
  }
}
