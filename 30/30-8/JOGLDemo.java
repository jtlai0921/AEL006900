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
  private javax.media.opengl.GLJPanel glpanel = null;
  private javax.media.opengl.GL gl = null;
  private javax.media.opengl.glu.GLU glu = new GLU();
  //private com.sun.opengl.util.GLUT glut = new GLUT();
  private GLDrawable drawable = null;

  // Light Position (光源位置)
  float[] lightPositionR = { 0.0f, 0.0f, 75.0f, 1.0f };
  // 散射
  float[] diffuseLight = { 0.5f, 0.5f, 0.5f, 1.0f };   
  // 高光
  float[] specularLight = { 1.0f, 1.0f, 1.0f, 1.0f };   
  // Light Position (光源位置)
  float[] lightPosition = { 0.0f, 0.0f, 100.0f, 1.0f };

  float objectXRot; // x 旋轉
  float objectYRot; // y 旋轉
  float objectZRot; // z 旋轉
  
  float[] diffuseMaterial = { 1.0f, 1.0f, 1.0f, 1.0f };

  // Texture貼圖  
  Texture cubeTex;

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
    super("JOGL with Texture");

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

    gl.glShadeModel(GL.GL_SMOOTH);   
    gl.glEnable(GL.GL_DEPTH_TEST);   
    gl.glEnable(GL.GL_CULL_FACE);    
    gl.glFrontFace(GL.GL_CCW);       
  
    gl.glEnable(GL.GL_LIGHTING);     
    
    // Light 0
    gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, diffuseLight, 0);
    gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, specularLight, 0);
    gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, lightPosition, 0);
    gl.glLightf(GL.GL_LIGHT0, GL.GL_SPOT_CUTOFF, 40.0f);
    gl.glLightf(GL.GL_LIGHT0, GL.GL_SPOT_EXPONENT, 80.0f);
  
    // Light 1
    gl.glLightfv(GL.GL_LIGHT1, GL.GL_DIFFUSE, diffuseMaterial, 0);
    gl.glLightfv(GL.GL_LIGHT1, GL.GL_SPECULAR, diffuseMaterial, 0);
    gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, lightPositionR, 0);

    // 啟用光源 
    gl.glEnable(GL.GL_LIGHT0);
    gl.glEnable(GL.GL_LIGHT1);
  
    gl.glEnable(GL.GL_COLOR_MATERIAL);
    gl.glColorMaterial(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE);
  
    // 設定物體的材質
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, specularLight, 0);
    gl.glMateriali(GL.GL_FRONT, GL.GL_SHININESS, 128);
  
    // 以目前之顏色清除視窗
    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

    // 載入圖像以作為貼圖之用
    cubeTex = loadTexture("opengl.jpg");
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

    // increase rotation values
    objectXRot += 0.5f;
    objectYRot += 1.0f;
    objectZRot += 0.5f;
    
    // clear screen and depth buffer
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
  
    // 載入單位矩陣
    gl.glLoadIdentity();                    
  
    // Move everything back to (0, 0, -150)
    gl.glTranslatef(0.0f, 0.0f, -150.0f);
  
    // 複製目前之矩陣至堆疊最上層
    gl.glPushMatrix();

    // 設定目前繪圖顏色
    gl.glColor3f(1.0f, 1.0f, 1.0f);
    // 沿x座標旋轉指定角度
    gl.glRotatef(objectXRot, 1.0f, 0.0f, 0.0f);
    // 沿y座標旋轉指定角度
    gl.glRotatef(objectYRot, 0.0f, 1.0f, 0.0f);
    // 沿z座標旋轉指定角度
    gl.glRotatef(objectZRot, 0.0f, 0.0f, 1.0f);

    drawCube();
    
    // 自堆疊最上層移除矩陣
    gl.glPopMatrix();
    // 即刻執行JOGL指令 
    gl.glFlush();

    drawable.swapBuffers(); 
  }

  // 當視窗大小改變時
  public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    GL gl = drawable.getGL();

    if (height == 0)
      height = 1;
      
    // 設定視界大小  
    gl.glViewport(0, 0, width, height);

    // 設定座標系統
    gl.glMatrixMode(GL.GL_PROJECTION);
    // 載入單位矩陣
    gl.glLoadIdentity();

    // 設定透視度
    glu.gluPerspective(54.0, (double)width/(double)height, 1.0, 1000.0);
  
    // 設定座標系統
    gl.glMatrixMode(GL.GL_MODELVIEW);
    // 載入單位矩陣
    gl.glLoadIdentity();
  }

  // 當顯示模式或裝置改變時
  public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {}

  private void drawCube(){
    float[] cubeColor = { 1.0f, 1.0f, 1.0f, 1.0f };
    
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, cubeColor, 0);
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, cubeColor, 0);
    gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, 50.0f);

    gl.glEnable(GL.GL_TEXTURE_2D);
    gl.glTexEnvf(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_MODULATE);

    // 啟用貼圖
    cubeTex.enable();
    // 繫結貼圖
    cubeTex.bind();

    gl.glBegin(GL.GL_QUADS);

      // front
      // 設定物體表面的法線
      gl.glNormal3f(0.0f, 0.0f, 1.0f);
      // 設定Texture的座標與頂點（Vertex）
      gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-30.0f, -30.0f, 30.0f);
      gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( 30.0f, -30.0f, 30.0f);
      gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( 30.0f,  30.0f, 30.0f);
      gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-30.0f,  30.0f, 30.0f);
    
      // back
      // 設定物體表面的法線
      gl.glNormal3f(0.0f, 0.0f, -1.0f);
      // 設定Texture的座標與頂點（Vertex）
      gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f( 30.0f, -30.0f, -30.0f);
      gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-30.0f, -30.0f, -30.0f);
      gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-30.0f,  30.0f, -30.0f);
      gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f( 30.0f,  30.0f, -30.0f);
    
      // top
      // 設定物體表面的法線
      gl.glNormal3f(0.0f, 1.0f, 0.0f);
      // 設定Texture的座標與頂點（Vertex）
      gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-30.0f, 30.0f,  30.0f);
      gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( 30.0f, 30.0f,  30.0f);
      gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( 30.0f, 30.0f, -30.0f);
      gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-30.0f, 30.0f, -30.0f);
    
      // bottom
      // 設定物體表面的法線
      gl.glNormal3f(0.0f, -1.0f, 0.0f);
      // 設定Texture的座標與頂點（Vertex）
      gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-30.0f, -30.0f, -30.0f);
      gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( 30.0f, -30.0f, -30.0f);
      gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( 30.0f, -30.0f,  30.0f);
      gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-30.0f, -30.0f,  30.0f);
    
      // left
      // 設定物體表面的法線
      gl.glNormal3f(-1.0f, 0.0f, 0.0f);
      // 設定Texture的座標與頂點（Vertex）
      gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-30.0f, -30.0f, -30.0f);
      gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-30.0f, -30.0f,  30.0f);
      gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-30.0f,  30.0f,  30.0f);
      gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-30.0f,  30.0f, -30.0f);
    
      // right
      // 設定物體表面的法線
      gl.glNormal3f(1.0f, 0.0f, 0.0f);
      // 設定Texture的座標與頂點（Vertex）
      gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(30.0f, -30.0f,  30.0f);
      gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(30.0f, -30.0f, -30.0f);
      gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(30.0f,  30.0f, -30.0f);
      gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(30.0f,  30.0f,  30.0f);
  
    gl.glEnd();
    // 停止貼圖
    cubeTex.disable();  
  
    gl.glDisable(GL.GL_TEXTURE_2D);
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
      JOptionPane.showMessageDialog(null, "JOGL with Texture", "About", JOptionPane.INFORMATION_MESSAGE);
    }
  }
}
