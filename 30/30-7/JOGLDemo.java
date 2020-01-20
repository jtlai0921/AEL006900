import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

// JOGL
import javax.media.opengl.*;
import javax.media.opengl.glu.*;
import com.sun.opengl.util.*;

public class JOGLDemo extends JFrame implements GLEventListener, ActionListener, MouseListener, MouseMotionListener {

  JMenu jmenu[];
  JMenuItem jmenuItem[][] = new JMenuItem[2][1];

  String menu[]={"File|F", "Help|H"};

  String menuItem[][]={{"Exit|X"}, {"About|A|about.gif"}};

  // JOGL
  private GLU glu = new GLU();
  private GLUT glut = new GLUT();
  private float view_rotx = 20.0f, view_roty = 30.0f, view_rotz = 0.0f;
  private int ball;
  private float angle = 0.0f;

  private int prevMouseX, prevMouseY;
  private boolean mouseRButtonDown = false;

  public static void main(String[] args) {
    UIManager.put("swing.boldMetal", Boolean.FALSE);
    JDialog.setDefaultLookAndFeelDecorated(true);
    JFrame.setDefaultLookAndFeelDecorated(true);
    Toolkit.getDefaultToolkit().setDynamicLayout(true);

    try {
      UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    new JOGLDemo();
  }

  // 建構函式
  public JOGLDemo() {
    super("JOGL with Mouse Event");

    // 取得目前之Class Loader
    ClassLoader cl = this.getClass().getClassLoader();

    // 設定視窗圖示
    this.setIconImage(new ImageIcon(cl.getResource("images/java.png")).getImage());

    // Set OpenGL Capabilities
    javax.media.opengl.GLCapabilities glcapabilities = new GLCapabilities();
    // Set Drawable Factory
    GLDrawableFactory gldrawablefactory = GLDrawableFactory.getFactory();
    GLDrawable gldrawable = gldrawablefactory.getGLDrawable(this, glcapabilities, null);
    // Set Double Buffer
    glcapabilities.setDoubleBuffered(true);
    
    // 建立GLJPanel物件 
    GLJPanel glpanel = new GLJPanel(glcapabilities);

    // 註冊GLEventListener
    glpanel.addGLEventListener(this);
    // 註冊MouseListener
    glpanel.addMouseListener(this);
    // 註冊MouseMotionListener
    glpanel.addMouseMotionListener(this);

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

    GL gl = drawable.getGL();

    gl.setSwapInterval(1);

    float white[] = { 1.0f, 1.0f, 1.0f, 1.0f };

    gl.glEnable(GL.GL_CULL_FACE);
    // 啟用光源 
    gl.glEnable(GL.GL_LIGHTING);
    // 啟用光源0 
    gl.glEnable(GL.GL_LIGHT0);
    gl.glEnable(GL.GL_DEPTH_TEST);

    /* make the Ball */
    ball = gl.glGenLists(1);

    GLUquadric quad;
    quad = glu.gluNewQuadric();
    glu.gluQuadricOrientation(quad, GLU.GLU_OUTSIDE);
    glu.gluQuadricNormals    (quad, GLU.GLU_SMOOTH);
    glu.gluQuadricTexture    (quad, true);

    gl.glNewList(ball, GL.GL_COMPILE);
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, white, 0);

    glut.glutWireSphere(5, 25, 25);
    gl.glEndList();
            
    gl.glEnable(GL.GL_NORMALIZE);
  }

  // 繪製圖形
  public void display(GLAutoDrawable drawable) {
    angle += 100.0f;

    GL gl = drawable.getGL();
    
    if ((drawable instanceof GLJPanel) &&
        !((GLJPanel) drawable).isOpaque() &&
        ((GLJPanel) drawable).shouldPreserveColorBufferIfTranslucent()) {

      // 以目前之顏色清除視窗
      gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
    } 
    else {
      // 以目前之顏色清除視窗
      gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
    }
            
    // 複製目前之矩陣至堆疊最上層
    gl.glPushMatrix();
    // 沿x座標旋轉指定角度
    gl.glRotatef(view_rotx, 1.0f, 0.0f, 0.0f);
    // 沿y座標旋轉指定角度
    gl.glRotatef(view_roty, 0.0f, 1.0f, 0.0f);
    // 沿z座標旋轉指定角度
    gl.glRotatef(view_rotz, 0.0f, 0.0f, 1.0f);
            
    // 沿x,y,z座標旋轉指定角度
    gl.glRotatef(angle, 0.0f, 0.0f, 1.0f);
    gl.glCallList(ball);
    // 自堆疊最上層移除矩陣
    gl.glPopMatrix();
    // 即刻執行JOGL指令 
    gl.glFlush();
  }

  // 當視窗大小改變時
  public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    GL gl = drawable.getGL();

    float h = (float)height / (float)width;
            
    // 設定座標系統
    gl.glMatrixMode(GL.GL_PROJECTION);

    gl.glLoadIdentity();
    gl.glFrustum(-1.0f, 1.0f, -h, h, 5.0f, 60.0f);
    // 設定座標系統
    gl.glMatrixMode(GL.GL_MODELVIEW);
    gl.glLoadIdentity();
    gl.glTranslatef(0.0f, 0.0f, -40.0f);
  }

  // 當顯示模式或裝置改變時
  public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {}

  // 實作滑鼠事件的方法
  public void mouseEntered(MouseEvent e) {}
  public void mouseExited(MouseEvent e) {}
  public void mouseClicked(MouseEvent e) {}

  public void mousePressed(MouseEvent e) {
    prevMouseX = e.getX();
    prevMouseY = e.getY();
    if ((e.getModifiers() & e.BUTTON3_MASK) != 0) {
      mouseRButtonDown = true;
    }
  }
    
  public void mouseReleased(MouseEvent e) {
    if ((e.getModifiers() & e.BUTTON3_MASK) != 0) {
      mouseRButtonDown = false;
    }
  }
    
  // 實作滑鼠移動事件的方法
  public void mouseMoved(MouseEvent e) {}

  public void mouseDragged(MouseEvent e) {
    int x = e.getX();
    int y = e.getY();
    Dimension size = e.getComponent().getSize();

    float thetaY = 360.0f * ( (float)(x-prevMouseX)/(float)size.width);
    float thetaX = 360.0f * ( (float)(prevMouseY-y)/(float)size.height);
    
    prevMouseX = x;
    prevMouseY = y;

    view_rotx += thetaX;
    view_roty += thetaY;
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
      JOptionPane.showMessageDialog(null, "JOGL with Mouse Event", "About", JOptionPane.INFORMATION_MESSAGE);
    }
  }
}
