import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

// JOGL
import javax.media.opengl.*;
import javax.media.opengl.glu.*;
import com.sun.opengl.util.*;

public class JOGLPanel extends JPanel implements GLEventListener, ActionListener {

  JApplet MainApplet;
  
  JMenu jmenu[];
  JMenuItem jmenuItem[][] = new JMenuItem[1][1];

  String menu[]={"Help|H"};

  String menuItem[][]={{"About|A|about.gif"}};

  static {
    UIManager.put("swing.boldMetal", Boolean.FALSE);
    JDialog.setDefaultLookAndFeelDecorated(true);
    JFrame.setDefaultLookAndFeelDecorated(true);
    Toolkit.getDefaultToolkit().setDynamicLayout(true);
    
    try {
      UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
    }
    catch(Exception exception) { }
  }

  // 建構函式
  public JOGLPanel(JApplet MainApplet) {
    this.MainApplet = MainApplet;

    // 建立GLJPanel物件 
    GLJPanel glpanel = new GLJPanel();

    // 註冊GLEventListener
    glpanel.addGLEventListener(this);

    this.setLayout(new BorderLayout());
    // 將GLJPanel物件加入JPanel中
    this.add(glpanel, BorderLayout.CENTER);

    // 建立選單功能列
    JMenuBar jmenubar = createMenuBar();
    
    // 定義視窗使用者介面之選單功能列
    MainApplet.setJMenuBar(jmenubar);

    this.validate();
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

    // 以黑色清除背景 
    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    // 以平滑曲線描邊 
    gl.glShadeModel(GL.GL_SMOOTH);

    // 啟用光源 
    gl.glEnable(GL.GL_LIGHTING);
    // 啟用光源0 
    gl.glEnable(GL.GL_LIGHT0);
    gl.glEnable(GL.GL_DEPTH_TEST);
  }
    
  // 繪製圖形
  public void display(GLAutoDrawable drawable) {
    // 設定光源位置
    float position[] = { 1.0f, 1.0f, 1.0f, 1.0f };

    javax.media.opengl.GL gl = drawable.getGL();
    com.sun.opengl.util.GLUT glut = new GLUT();

    // 以目前之顏色清除視窗
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

    // 複製目前之矩陣至堆疊最上層
    gl.glPushMatrix();

    // 沿x,y,z座標旋轉指定角度
    gl.glRotatef(30.0f, 1.0f, 1.0f, 0.0f);

    // 設定光源0
    gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, position, 0);

    // 啟用光源 
    gl.glEnable(GL.GL_LIGHTING);

    // 繪製環形曲面 
    glut.glutSolidTorus(0.3f, 0.8f, 50, 50);

    // 自堆疊最上層移除矩陣
    gl.glPopMatrix();
    
    // 即刻執行JOGL指令 
    gl.glFlush();
  }

  // 當視窗大小改變時
  public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    GL gl = drawable.getGL();
    GLU glu = new GLU();

    if(height == 0)
      height = 1;
    
    double ratio = 1.0 * (double) width/(double) height;

    // 設定座標系統
    gl.glMatrixMode(GL.GL_PROJECTION);
    gl.glLoadIdentity();

    // 設定視界大小為視窗之大小
    gl.glViewport(0, 0, width, height);

    // 設定透視度
    glu.gluPerspective(45.0, ratio, 1.0, 1000.0);

    // 設定座標系統
    gl.glMatrixMode(GL.GL_MODELVIEW);
    gl.glLoadIdentity();

    glu.gluLookAt(0.0, 0.0, 5.0, 0.0, 0.0, -1.0, 0.0, 1.0, 0.0);
  }

  // 當顯示模式或裝置改變時
  public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {}
  
  // 實作ActionListener介面之方法
  public void actionPerformed(ActionEvent e) {
    if(e.getSource() == jmenuItem[0][0]){  // About
      JOptionPane.showMessageDialog(null, "JOGL with Swing", "About", JOptionPane.INFORMATION_MESSAGE);
    }
  }
}
