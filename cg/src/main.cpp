#include <GL/glut.h>

#include "bullseye.cpp"

int main(int argc, char **argv)
{
  glutInit(&argc, argv);
  glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB);
  glutInitWindowSize(1024, 768);
  glutInitWindowPosition(200, 200);
  glutCreateWindow("Bullseye");
  glutReshapeFunc(reshape);
  glutDisplayFunc(display);
  glutKeyboardFunc(getKeyboardInput);
  glutTimerFunc(ANIMATION_MSEC, animate, 0);
  glutMainLoop();

  return 0;
}
