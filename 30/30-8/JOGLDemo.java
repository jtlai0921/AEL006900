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

  // Light Position (������m)
  float[] lightPositionR = { 0.0f, 0.0f, 75.0f, 1.0f };
  // ���g
  float[] diffuseLight = { 0.5f, 0.5f, 0.5f, 1.0f };   
  // ����
  float[] specularLight = { 1.0f, 1.0f, 1.0f, 1.0f };   
  // Light Position (������m)
  float[] lightPosition = { 0.0f, 0.0f, 100.0f, 1.0f };

  float objectXRot; // x ����
  float objectYRot; // y ����
  float objectZRot; // z ����
  
  float[] diffuseMaterial = { 1.0f, 1.0f, 1.0f, 1.0f };

  // Texture�K��  
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

  // �غc�禡
  public JOGLDemo() {
    super("JOGL with Texture");

    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // �]�w�����ϥ�
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // Set OpenGL Capabilities
    javax.media.opengl.GLCapabilities glcapabilities = new GLCapabilities();
    // Set Drawable Factory
    GLDrawableFactory gldrawablefactory = GLDrawableFactory.getFactory();
    drawable = gldrawablefactory.getGLDrawable(this, glcapabilities, null);
    // Set Double Buffer
    glcapabilities.setDoubleBuffered(true);
    
    // �إ�GLJPanel���� 
    glpanel = new GLJPanel(glcapabilities);

    // ���UGLEventListener
    glpanel.addGLEventListener(this);

    this.setLayout(new BorderLayout());
    // �NGLJPanel����[�JJFrame��
    this.add(glpanel, BorderLayout.CENTER);

    // �إ߿��\��C
    JMenuBar jmenubar = createMenuBar();
    
    // �w�q�����ϥΪ̤��������\��C
    this.setJMenuBar(jmenubar);

    this.validate();
    // �]�w�������j�p
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
    // �}�l�ʵe
    animator.start();
  }

  private JMenuBar createMenuBar() {
    // ���o�ثe��Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // �إ߿��\��C
    JMenuBar jmenubar = new JMenuBar();

    jmenu = new JMenu[menu.length];
    
    // �إ߿��
    for (int i=0; i<menu.length; i++){
      jmenu[i] = new JMenu(menu[i].substring(0, menu[i].indexOf("|")));
      // �]�w���U�O�X
      jmenu[i].setMnemonic(menu[i].split("\\|")[1].charAt(0));
      // �[�J���ܿ��\��C
      jmenubar.add(jmenu[i]);
    }

    for(int i=0; i<menu.length; i++){
      for(int j=0; j<menuItem[i].length; j++){
        if (menuItem[i][j].equals("-")) {
          // �[�J���j�u
          jmenu[i].addSeparator();
        }
        else {
          // �إ߿�涵��
          jmenuItem[i][j] = new JMenuItem(menuItem[i][j].substring(0, menuItem[i][j].indexOf("|")));
          // �]�w��涵�اU�O�X
          jmenuItem[i][j].setMnemonic(menuItem[i][j].split("\\|")[1].charAt(0));

          // �إ߿�涵�عϹ�
          if (menuItem[i][j].endsWith(".gif")) 
            jmenuItem[i][j].setIcon(new ImageIcon(cl.getResource("images/" + menuItem[i][j].substring(menuItem[i][j].lastIndexOf("|")+1))));

          // ���U ActionListener
          jmenuItem[i][j].addActionListener(this);

          // �[�J��涵��
          jmenu[i].add(jmenuItem[i][j]);
        }
      }
    }

    return jmenubar;
  }

  // ��@GLEventListener��������k
  // ��l��JOGL
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

    // �ҥΥ��� 
    gl.glEnable(GL.GL_LIGHT0);
    gl.glEnable(GL.GL_LIGHT1);
  
    gl.glEnable(GL.GL_COLOR_MATERIAL);
    gl.glColorMaterial(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE);
  
    // �]�w���骺����
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, specularLight, 0);
    gl.glMateriali(GL.GL_FRONT, GL.GL_SHININESS, 128);
  
    // �H�ثe���C��M������
    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

    // ���J�Ϲ��H�@���K�Ϥ���
    cubeTex = loadTexture("opengl.jpg");
  }

  // ���J�Ϲ��H�@���K�Ϥ���
  private Texture loadTexture(String filename) {
    com.sun.opengl.util.texture.Texture tex = null;

    // Get current classloader
    ClassLoader cl = this.getClass().getClassLoader();
    
    try {
      // ���J�Ϲ��ɮ�
      tex = com.sun.opengl.util.texture.TextureIO.newTexture(cl.getResourceAsStream("images/" + filename), false, TextureIO.JPG);
      // �]�wTexture���Ѽ�
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
  
  // ø�s�ϧ�
  public void display(GLAutoDrawable drawable) {
    gl = drawable.getGL();

    // increase rotation values
    objectXRot += 0.5f;
    objectYRot += 1.0f;
    objectZRot += 0.5f;
    
    // clear screen and depth buffer
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
  
    // ���J���x�}
    gl.glLoadIdentity();                    
  
    // Move everything back to (0, 0, -150)
    gl.glTranslatef(0.0f, 0.0f, -150.0f);
  
    // �ƻs�ثe���x�}�ܰ��|�̤W�h
    gl.glPushMatrix();

    // �]�w�ثeø���C��
    gl.glColor3f(1.0f, 1.0f, 1.0f);
    // �ux�y�б�����w����
    gl.glRotatef(objectXRot, 1.0f, 0.0f, 0.0f);
    // �uy�y�б�����w����
    gl.glRotatef(objectYRot, 0.0f, 1.0f, 0.0f);
    // �uz�y�б�����w����
    gl.glRotatef(objectZRot, 0.0f, 0.0f, 1.0f);

    drawCube();
    
    // �۰��|�̤W�h�����x�}
    gl.glPopMatrix();
    // �Y�����JOGL���O 
    gl.glFlush();

    drawable.swapBuffers(); 
  }

  // ������j�p���ܮ�
  public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    GL gl = drawable.getGL();

    if (height == 0)
      height = 1;
      
    // �]�w���ɤj�p  
    gl.glViewport(0, 0, width, height);

    // �]�w�y�Шt��
    gl.glMatrixMode(GL.GL_PROJECTION);
    // ���J���x�}
    gl.glLoadIdentity();

    // �]�w�z����
    glu.gluPerspective(54.0, (double)width/(double)height, 1.0, 1000.0);
  
    // �]�w�y�Шt��
    gl.glMatrixMode(GL.GL_MODELVIEW);
    // ���J���x�}
    gl.glLoadIdentity();
  }

  // ����ܼҦ��θ˸m���ܮ�
  public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {}

  private void drawCube(){
    float[] cubeColor = { 1.0f, 1.0f, 1.0f, 1.0f };
    
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, cubeColor, 0);
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, cubeColor, 0);
    gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, 50.0f);

    gl.glEnable(GL.GL_TEXTURE_2D);
    gl.glTexEnvf(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_MODULATE);

    // �ҥζK��
    cubeTex.enable();
    // ô���K��
    cubeTex.bind();

    gl.glBegin(GL.GL_QUADS);

      // front
      // �]�w��������k�u
      gl.glNormal3f(0.0f, 0.0f, 1.0f);
      // �]�wTexture���y�лP���I�]Vertex�^
      gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-30.0f, -30.0f, 30.0f);
      gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( 30.0f, -30.0f, 30.0f);
      gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( 30.0f,  30.0f, 30.0f);
      gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-30.0f,  30.0f, 30.0f);
    
      // back
      // �]�w��������k�u
      gl.glNormal3f(0.0f, 0.0f, -1.0f);
      // �]�wTexture���y�лP���I�]Vertex�^
      gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f( 30.0f, -30.0f, -30.0f);
      gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-30.0f, -30.0f, -30.0f);
      gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-30.0f,  30.0f, -30.0f);
      gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f( 30.0f,  30.0f, -30.0f);
    
      // top
      // �]�w��������k�u
      gl.glNormal3f(0.0f, 1.0f, 0.0f);
      // �]�wTexture���y�лP���I�]Vertex�^
      gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-30.0f, 30.0f,  30.0f);
      gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( 30.0f, 30.0f,  30.0f);
      gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( 30.0f, 30.0f, -30.0f);
      gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-30.0f, 30.0f, -30.0f);
    
      // bottom
      // �]�w��������k�u
      gl.glNormal3f(0.0f, -1.0f, 0.0f);
      // �]�wTexture���y�лP���I�]Vertex�^
      gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-30.0f, -30.0f, -30.0f);
      gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( 30.0f, -30.0f, -30.0f);
      gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( 30.0f, -30.0f,  30.0f);
      gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-30.0f, -30.0f,  30.0f);
    
      // left
      // �]�w��������k�u
      gl.glNormal3f(-1.0f, 0.0f, 0.0f);
      // �]�wTexture���y�лP���I�]Vertex�^
      gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-30.0f, -30.0f, -30.0f);
      gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-30.0f, -30.0f,  30.0f);
      gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-30.0f,  30.0f,  30.0f);
      gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-30.0f,  30.0f, -30.0f);
    
      // right
      // �]�w��������k�u
      gl.glNormal3f(1.0f, 0.0f, 0.0f);
      // �]�wTexture���y�лP���I�]Vertex�^
      gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(30.0f, -30.0f,  30.0f);
      gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(30.0f, -30.0f, -30.0f);
      gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(30.0f,  30.0f, -30.0f);
      gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(30.0f,  30.0f,  30.0f);
  
    gl.glEnd();
    // ����K��
    cubeTex.disable();  
  
    gl.glDisable(GL.GL_TEXTURE_2D);
  }
  
  // ��@ActionListener��������k
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
