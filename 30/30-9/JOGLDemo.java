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

public class JOGLDemo extends JFrame implements GLEventListener, ActionListener {

  JMenu jmenu[];
  JMenuItem jmenuItem[][] = new JMenuItem[2][1];

  String menu[]={"File|F", "Help|H"};

  String menuItem[][]={{"Exit|X"}, {"About|A|about.gif"}};

  // JOGL
  // JOGL
  javax.media.opengl.GLJPanel glpanel = null;
  javax.media.opengl.GL gl = null;
  javax.media.opengl.glu.GLU glu = new GLU();
  com.sun.opengl.util.GLUT glut = new GLUT();
  GLDrawable drawable = null;

  Texture cubeTex;
  Texture marbleTex;
  float[] g_lightPos = {5.0f, 5.0f, 5.0f, 1.0f};
  float[] g_shadowMatrix = new float[16];
  
  double angle = 0.0;

  public static void main(String[] args) {
    UIManager.put("swing.boldMetal", Boolean.FALSE);
    JDialog.setDefaultLookAndFeelDecorated(true);
    JFrame.setDefaultLookAndFeelDecorated(true);
    Toolkit.getDefaultToolkit().setDynamicLayout(true);

    try {
      UIManager.setLookAndFeel(new javax.swing.plaf.metal.MetalLookAndFeel());
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    new JOGLDemo();
  }

  // 建構函式
  public JOGLDemo() {
    super("JOGL with Reflection");

    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 設定視窗圖示
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // Set OpenGL Capabilities
    javax.media.opengl.GLCapabilities glcapabilities = new GLCapabilities();
    // Set Drawable Factory
    GLDrawableFactory gldrawablefactory = GLDrawableFactory.getFactory();
    drawable = gldrawablefactory.getGLDrawable(this, glcapabilities, null);
    // Set Double Buffer
    glcapabilities.setDoubleBuffered(true);
    
    // 建立GLJPanel物件 
    glpanel = new GLJPanel(glcapabilities);

    // 註冊GLEventListener
    glpanel.addGLEventListener(this);

    this.setLayout(new BorderLayout());
    // 將GLJPanel物件加入JFrame中
    this.add(glpanel, BorderLayout.CENTER);

    // 建立選單功能列
    JMenuBar jmenubar = createMenuBar();
    
    // 定義視窗使用者介面之選單功能列
    this.setJMenuBar(jmenubar);

    this.validate();
    // 設定視窗的大小
    this.setSize(new Dimension(300, 300));

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();

    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;

    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;

    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    this.setResizable(true);
    this.setVisible(true);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });  

    // Animator
    final Animator animator = new Animator(glpanel);

    glpanel.requestFocusInWindow();
    // 開始動畫
    animator.start();
  }

  private JMenuBar createMenuBar() {
    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 建立選單功能列
    JMenuBar jmenubar = new JMenuBar();

    jmenu = new JMenu[menu.length];
    
    // 建立選單
    for (int i=0; i<menu.length; i++){
      jmenu[i] = new JMenu(menu[i].substring(0, menu[i].indexOf("|")));
      // 設定選單助記碼
      jmenu[i].setMnemonic(menu[i].split("\\|")[1].charAt(0));
      // 加入選單至選單功能列
      jmenubar.add(jmenu[i]);
    }

    for(int i=0; i<menu.length; i++){
      for(int j=0; j<menuItem[i].length; j++){
        if (menuItem[i][j].equals("-")) {
          // 加入分隔線
          jmenu[i].addSeparator();
        }
        else {
          // 建立選單項目
          jmenuItem[i][j] = new JMenuItem(menuItem[i][j].substring(0, menuItem[i][j].indexOf("|")));
          // 設定選單項目助記碼
          jmenuItem[i][j].setMnemonic(menuItem[i][j].split("\\|")[1].charAt(0));

          // 建立選單項目圖像
          if (menuItem[i][j].endsWith(".gif")) 
            jmenuItem[i][j].setIcon(new ImageIcon(cl.getResource("images/" + menuItem[i][j].substring(menuItem[i][j].lastIndexOf("|")+1))));

          // 註冊 ActionListener
          jmenuItem[i][j].addActionListener(this);

          // 加入選單項目
          jmenu[i].add(jmenuItem[i][j]);
        }
      }
    }

    return jmenubar;
  }

  // 實作GLEventListener介面之方法
  // 初始化JOGL
  public void init(GLAutoDrawable drawable) {
    gl = drawable.getGL();
    
    // Clear window with color   
    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
  
    // set the shading model
    gl.glShadeModel(GL.GL_SMOOTH);
  
    // set up a single white light
    float lightColor[] = { 1.0f, 1.0f, 1.0f, 1.0f };
  
    gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, lightColor, 0);
    gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, lightColor, 0);
  
    gl.glEnable(GL.GL_LIGHTING);
    gl.glEnable(GL.GL_LIGHT0);
    gl.glEnable(GL.GL_DEPTH_TEST);

    gl.glEnable(GL.GL_TEXTURE_2D);
    gl.glTexEnvf(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_MODULATE);
  
    // load the textures
    cubeTex = loadTexture("opengl.jpg");
    marbleTex = loadTexture("marble.jpg");

    gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
  
    float[] plane = { 0.0f, 1.0f, 0.0f, 0.0f };
  
    // dot product of plane and light position
    float dot = plane[0] * g_lightPos[0] + plane[1] * g_lightPos[1] + 
                plane[1] * g_lightPos[2] + plane[3] * g_lightPos[3];
  
    // first column
    g_shadowMatrix[0]  = dot  - g_lightPos[0] * plane[0];
    g_shadowMatrix[4]  = 0.0f - g_lightPos[0] * plane[1];
    g_shadowMatrix[8]  = 0.0f - g_lightPos[0] * plane[2];
    g_shadowMatrix[12] = 0.0f - g_lightPos[0] * plane[3];
  
    // second column
    g_shadowMatrix[1]  = 0.0f - g_lightPos[1] * plane[0];
    g_shadowMatrix[5]  =  dot - g_lightPos[1] * plane[1];
    g_shadowMatrix[9]  = 0.0f - g_lightPos[1] * plane[2];
    g_shadowMatrix[13] = 0.0f - g_lightPos[1] * plane[3];
  
    // third column
    g_shadowMatrix[2]  = 0.0f - g_lightPos[2] * plane[0];
    g_shadowMatrix[6]  = 0.0f - g_lightPos[2] * plane[1];
    g_shadowMatrix[10] =  dot - g_lightPos[2] * plane[2];
    g_shadowMatrix[14] = 0.0f - g_lightPos[2] * plane[3];
  
    // fourth column
    g_shadowMatrix[3]  = 0.0f - g_lightPos[3] * plane[0];
    g_shadowMatrix[7]  = 0.0f - g_lightPos[3] * plane[1];
    g_shadowMatrix[11] = 0.0f - g_lightPos[3] * plane[2];
    g_shadowMatrix[15] =  dot - g_lightPos[3] * plane[3];    
  }

  // 載入圖像以作為貼圖之用
  private Texture loadTexture(String filename) {
    com.sun.opengl.util.texture.Texture tex = null;

    // Get current classloader
    ClassLoader cl = this.getClass().getClassLoader();
    
    try {
      // 載入圖像檔案
      tex = com.sun.opengl.util.texture.TextureIO.newTexture(cl.getResourceAsStream("images/" + filename), false, TextureIO.JPG);
      // 設定Texture的參數
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
  
  // 繪製圖形
  public void display(GLAutoDrawable drawable) {
    gl = drawable.getGL();

    gl.glLoadIdentity();
    glu.gluLookAt(0.0, 3.0, 10.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);
  
    // Clear
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT | GL.GL_STENCIL_BUFFER_BIT);

    // rotate the scene
    angle += 3.0;
    gl.glRotated(-angle/8.0, 0.0, 1.0, 0.0);
    gl.glRotated(10.0 * Math.sin(angle/45.0), 1.0, 0.0, 0.0);

    gl.glColorMask(false, false, false, false);
    gl.glDepthMask(false);

    gl.glEnable(GL.GL_STENCIL_TEST);
    gl.glStencilFunc(GL.GL_ALWAYS, 1, ~0);
    gl.glStencilOp(GL.GL_REPLACE, GL.GL_REPLACE, GL.GL_REPLACE);

    drawSurface();

    gl.glColorMask(true, true, true, true);
    gl.glDepthMask(true);
    
    gl.glStencilFunc(GL.GL_EQUAL, 1, ~0);
    
    gl.glStencilOp(GL.GL_KEEP, GL.GL_KEEP, GL.GL_KEEP);

    gl.glPushMatrix();
      gl.glScalef(1.0f, -1.0f, 1.0f);
      gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, g_lightPos, 0);
      drawCube();
    gl.glPopMatrix();

    gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, g_lightPos, 0);
    gl.glEnable(GL.GL_BLEND);
    drawSurface();
    gl.glDisable(GL.GL_BLEND);

    gl.glPushMatrix();
      gl.glDisable(GL.GL_TEXTURE_2D);
      gl.glDisable(GL.GL_LIGHTING);
      gl.glDisable(GL.GL_DEPTH_TEST);
      gl.glEnable(GL.GL_BLEND);
      gl.glStencilOp(GL.GL_KEEP, GL.GL_KEEP, GL.GL_INCR);
      gl.glColor4f(0.0f, 0.0f, 0.0f, 0.5f);
    
      gl.glMultMatrixf(g_shadowMatrix, 0);
      drawCube();
    
      gl.glEnable(GL.GL_TEXTURE_2D);
      gl.glEnable(GL.GL_DEPTH_TEST);
      gl.glDisable(GL.GL_BLEND);
      gl.glEnable(GL.GL_LIGHTING);
    gl.glPopMatrix();
    gl.glDisable(GL.GL_STENCIL_TEST);

    gl.glPushMatrix();
      drawCube();
    gl.glPopMatrix();
  }

  // 當視窗大小改變時
  public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    GL gl = drawable.getGL();
    GLU glu = new GLU();

    if (height == 0)
      height = 1;
      
    // 設定視界大小  
    gl.glViewport(0, 0, width, height);

    // 設定座標系統
    gl.glMatrixMode(GL.GL_PROJECTION);
    // 載入單位矩陣
    gl.glLoadIdentity();

    // 設定透視度
    glu.gluPerspective(45.0, (double)width/(double)height, 1.0, 100.0);
  
    // 設定座標系統
    gl.glMatrixMode(GL.GL_MODELVIEW);
    // 載入單位矩陣
    gl.glLoadIdentity();
  }

  // 當顯示模式或裝置改變時
  public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {}

  private void drawCube()  {
    float[] cubeColor = { 1.0f, 1.0f, 1.0f, 1.0f };
    
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, cubeColor, 0);
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, cubeColor, 0);
    gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, 50.0f);

    gl.glTranslatef(0.0f, 2.0f, 0.0f);
    gl.glRotated(angle, 1.0, 0.5, 1.0);
  
    gl.glEnable(GL.GL_TEXTURE_2D);
    gl.glTexEnvf(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_MODULATE);

    // Enable texture
    cubeTex.enable();
    // Bind texture
    cubeTex.bind();
  
    gl.glBegin(GL.GL_QUADS);

      // front
      gl.glNormal3f(0.0f, 0.0f, 1.0f);
      gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-1.0f, -1.0f, 1.0f);
      gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( 1.0f, -1.0f, 1.0f);
      gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( 1.0f,  1.0f, 1.0f);
      gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-1.0f,  1.0f, 1.0f);
    
      // back
      gl.glNormal3f(0.0f, 0.0f, -1.0f);
      gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f( 1.0f, -1.0f, -1.0f);
      gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-1.0f, -1.0f, -1.0f);
      gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-1.0f,  1.0f, -1.0f);
      gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f( 1.0f,  1.0f, -1.0f);
    
      // top
      gl.glNormal3f(0.0f, 1.0f, 0.0f);
      gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-1.0f, 1.0f,  1.0f);
      gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( 1.0f, 1.0f,  1.0f);
      gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( 1.0f, 1.0f, -1.0f);
      gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-1.0f, 1.0f, -1.0f);
    
      // bottom
      gl.glNormal3f(0.0f, -1.0f, 0.0f);
      gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-1.0f, -1.0f, -1.0f);
      gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( 1.0f, -1.0f, -1.0f);
      gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( 1.0f, -1.0f,  1.0f);
      gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-1.0f, -1.0f,  1.0f);
    
      // left
      gl.glNormal3f(-1.0f, 0.0f, 0.0f);
      gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-1.0f, -1.0f, -1.0f);
      gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-1.0f, -1.0f,  1.0f);
      gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-1.0f,  1.0f,  1.0f);
      gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-1.0f,  1.0f, -1.0f);
    
      // right
      gl.glNormal3f(1.0f, 0.0f, 0.0f);
      gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(1.0f, -1.0f,  1.0f);
      gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(1.0f, -1.0f, -1.0f);
      gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(1.0f,  1.0f, -1.0f);
      gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(1.0f,  1.0f,  1.0f);
  
    gl.glEnd();
    cubeTex.disable();  
  
    gl.glDisable(GL.GL_TEXTURE_2D);
  } 

  private void drawSurface(){
    float[] surfaceColor = { 1.0f, 1.0f, 1.0f, 0.6f };
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, surfaceColor, 0);
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, surfaceColor, 0);
    gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, 200.0f);

    // 啟用貼圖
    cubeTex.enable();
    marbleTex.enable();
    // 繫結貼圖
    marbleTex.bind();
  
    gl.glBegin(GL.GL_QUADS);
    gl.glNormal3f(0.0f, 1.0f, 0.0f);
  
    float x = -5.0f;
    float z = -5.0f;
  
    for (int i = 0; i < 10; i++, x += 1.0f) {
      for (int j = 0; j < 10; j++, z += 1.0f) {
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(x, 0.0f, z);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(x + 1.0f, 0.0f, z);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(x + 1.0f, 0.0f, z + 1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(x, 0.0f, z + 1.0f);
      }
      z = -5.0f;
    }
  
    gl.glEnd();

    marbleTex.disable();  
  } 
    
  // 實作ActionListener介面之方法
  public void actionPerformed(ActionEvent e) {
    if(e.getSource() == jmenuItem[0][0]){  // Exit
      JOptionPane joptionpane = new JOptionPane();
      int iResult = joptionpane.showConfirmDialog(null, "Are you sure to quit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
      
      if (iResult == 0) {
        new Thread(new Runnable() {
          public void run() {
            System.exit(0);
          }
        }).start();
      }
    }
    else if(e.getSource() == jmenuItem[1][0]){  // About
      JOptionPane.showMessageDialog(null, "JOGL with Reflection", "About", JOptionPane.INFORMATION_MESSAGE);
    }
  }
}
