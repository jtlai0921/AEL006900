/* �HC�y���}�oOpenGL�{�� */
/* DrawRect.c            */

#include <windows.h>
#include <gl/glut.h>

/* �D�{�� */
void main(void) {
  /* �]�w��ܼҦ� */
  glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB);
  /* �إߵ��� */
  glutCreateWindow("DrawRect");
  /* �w�qø�Ϩ禡���۩w��RenderScene */
  glutDisplayFunc(RenderScene);
  /* �w�q������j�p���ܮɩҩI�s���禡 */
  glutReshapeFunc(ChangeSize);
  /* �۩w�禡�H��l������ */
  Initialize();
  /* �i�JOpenGL�j�� */
  glutMainLoop();
}

/* ��l������ */
void Initialize(void) {
  /* �H���w�C��M���I�� */
  glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
}

/* ø�� */
void RenderScene(void) {
  /* �H�ثe���C��M������ */
  glClear(GL_COLOR_BUFFER_BIT);
  /* �]�w�ثeø���C�⬰���� */
  glColor3f(1.0f, 0.0f, 0.0f);
  /* ø�s�x�ΨåH�ثe�C��񺡭��n */
  glRectf(100.0f, 150.0f, 150.0f, 100.0f);
  /* �Y�����OpenGL���O */
  glFlush();
}

/* ������j�p���ܮ� */
void ChangeSize(GLsizei w, GLsizei h) {
  /* �]�w���ɤj�p���������j�p */
  glViewport(0, 0, w, h);
  /* ���]�y�Шt��*/
  glMatrixMode(GL_PROJECTION);
  glLoadIdentity();

  if (w <= h) 
    glOrtho (0.0f, 250.0f, 0.0f, 250.0f*h/w, 1.0, -1.0);
  else 
    glOrtho (0.0f, 250.0f*w/h, 0.0f, 250.0f, 1.0, -1.0);

  glMatrixMode(GL_MODELVIEW);
  glLoadIdentity();
}
