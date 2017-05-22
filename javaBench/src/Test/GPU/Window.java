package Test.GPU;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.HashSet;
import java.util.Set;

public class Window implements GLEventListener {

    public static DisplayMode dm, dm_old;
    private GLU glu = new GLU();
    private float rquad = 0.0f;
    static Set<Cube> cubes = new HashSet<>();
    private static final float point = 1.0f;
    int i = 0;
    float xplus = 0f;
    float yplus = 0f;
    float zplus = 0f;
    float xminus = 0f;
    float yminus = 0f;
    float zminus = 0f;
    int t = 1;
    float z = 10.0f;

    GLCanvas glcanvas;

    JFrame frame;

    public void addCube(float diffx, float diffy, float diffz){
        float[][][] f = {{{point + diffx, point + diffy, -point + diffz}, {-point + diffx, point + diffy, -point + diffz}, {-point + diffx, point + diffy, point + diffz}, {point + diffx, point + diffy, point + diffz}},
                {{point + diffx, -point + diffy, point + diffz}, {-point + diffx, -point + diffy, point + diffz}, {-point + diffx, -point + diffy, -point + diffz}, {point + diffx, -point + diffy, -point+diffz}},
                {{point + diffx, point + diffy, point + diffz}, {-point + diffx, point + diffy, point + diffz}, {-point + diffx, -point + diffy, point + diffz}, {point + diffx, -point + diffy, point + diffz}},
                {{point + diffx, -point + diffy, -point + diffz}, {-point + diffx, -point + diffy, -point + diffz}, {-point + diffx, point + diffy, -point + diffz},{point + diffx, point + diffy, -point + diffz} },
                {{-point + diffx, point + diffy, point + diffz}, {-point + diffx, point + diffy, -point + diffz}, {-point + diffx, -point + diffy, -point + diffz}, {-point + diffx, -point + diffy, point + diffz}},
                {{point + diffx, point + diffy, -point + diffz}, {point + diffx, point + diffy, point + diffz}, {point + diffx, -point + diffy, point + diffz},{point + diffx, -point + diffy, -point + diffz} }};
        float[][] c = {{1f, 0f, 0f}, {0f, 1f, 0f}, {1f, 1f, 0f}, {0f, 0f, 1f}, {1f, 0f, 1f}, {0f, 1f, 1f}};
        synchronized (cubes) {
            cubes.add(new Cube(f, c));
        }
    }

    public void addCubes()throws InterruptedException{

        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        // The canvas
        final GLCanvas glcanvas = new GLCanvas(capabilities);
        glcanvas.getDefaultCloseOperation();

        glcanvas.addGLEventListener(this);
        glcanvas.setSize(700, 700);

        final JFrame frame = new JFrame(" Multicolored cube");
        frame.getContentPane().add(glcanvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);
        final FPSAnimator animator = new FPSAnimator(glcanvas, 600, true);


        animator.start();

        Thread cubesThread = new Thread(() -> {
            float max = 2.5f;
            long end = System.currentTimeMillis() + 30000;
            while (System.currentTimeMillis() < end ) {
                for (float i = -max; i <= max; i += 2.5f) {
                    for (float j = -max; j <= max; j += 2.5f) {
                        try {
                            addCube(max, i, j);
                            addCube(i, max, j);
                            addCube(i, j, max);
                            addCube(-max, i, j);
                            addCube(i, -max, j);
                            addCube(i, j, -max);
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                max += 2.5f;
            }
        });
        cubesThread.start();
        try {
            cubesThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(freme/30);
        glcanvas.destroy();
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    public void closeWindow(){
        if(glcanvas != null && frame != null) {
            glcanvas.destroy();
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        }
    }

    int freme = 0;
    @Override
    public void display( GLAutoDrawable drawable ) {

        final GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT );
        gl.glLoadIdentity();
        gl.glTranslatef( 0f, 0f, -z );

        // Rotate The Cube On X, Y & Z
        gl.glRotatef(rquad, 1.0f, 1.0f, 1.0f);

        gl.glBegin(GL2.GL_QUADS); // Start Drawing The Cube
        synchronized(cubes) {
            for(Cube cube : cubes) {
                Wall[] walls = cube.getWalls();
                for (Wall wall : walls) {
                    float[] color = wall.getColor();
                    float[] rightTop = wall.getRightTop();
                    float[] leftTop = wall.getLeftTop();
                    float[] rightBottom = wall.getRightBottom();
                    float[] leftBottom = wall.getLeftBottom();
                    gl.glColor3f(color[0], color[1], color[2]);
                    gl.glVertex3f(rightTop[0], rightTop[1], rightTop[2]);
                    gl.glVertex3f(leftTop[0], leftTop[1], leftTop[2]);
                    gl.glVertex3f(rightBottom[0], rightBottom[1], rightBottom[2]);
                    gl.glVertex3f(leftBottom[0], leftBottom[1], leftBottom[2]);
                }

            }
        }
        gl.glEnd(); // Done Drawing The Quad
        gl.glFlush();

        freme++;

//        if (i==t)
//            addCube(xplus+=2.5f, 0f, 0f);
//        else if(i==2*t)
//            addCube(0.0f, yplus+=2.5f, 0f);
//        else if(i==3*t)
//            addCube(0.0f, 0.0f, zplus+=2.5f);
//        else if(i==4*t)
//            addCube(0.0f, yplus+=2.5f, 0);
//        else if(i==5*t)
//            addCube(xplus+=2.5f, yplus+=2.5f, 0);
//        else if(i==6*t)
//            addCube(xplus+=2.5f, 0, zplus+=2.5f);
//        else if(i==8*t)
//            addCube(xminus-=2.5f, 0.0f, 0f);
//        else if(i==9*t)
//            addCube(0.0f, yminus-=2.5f, 0f);
//        else if(i==10*t) {
//            addCube(0.0f, 0.0f, zminus -= 2.5f);
//            i=0;
//        }
//        i ++;
        z +=0.15f;
//        //giving different colors to different sides
//        gl.glBegin(GL2.GL_QUADS); // Start Drawing The Cube
//        gl.glColor3f(1f,0f,0f); //red color
//        gl.glVertex3f(1.0f, 1.0f, -1.0f); // Top Right Of The Quad (Top)
//        gl.glVertex3f( -1.0f, 1.0f, -1.0f); // Top Left Of The Quad (Top)
//        gl.glVertex3f( -1.0f, 1.0f, 1.0f ); // Bottom Left Of The Quad (Top)
//        gl.glVertex3f( 1.0f, 1.0f, 1.0f ); // Bottom Right Of The Quad (Top)
//
//        gl.glColor3f( 0f,1f,0f ); //green color
//        gl.glVertex3f( 1.0f, -1.0f, 1.0f ); // Top Right Of The Quad
//        gl.glVertex3f( -1.0f, -1.0f, 1.0f ); // Top Left Of The Quad
//        gl.glVertex3f( -1.0f, -1.0f, -1.0f ); // Bottom Left Of The Quad
//        gl.glVertex3f( 1.0f, -1.0f, -1.0f ); // Bottom Right Of The Quad
//
//        gl.glColor3f( 0f,0f,1f ); //blue color
//        gl.glVertex3f( 1.0f, 1.0f, 1.0f ); // Top Right Of The Quad (Front)
//        gl.glVertex3f( -1.0f, 1.0f, 1.0f ); // Top Left Of The Quad (Front)
//        gl.glVertex3f( -1.0f, -1.0f, 1.0f ); // Bottom Left Of The Quad
//        gl.glVertex3f( 1.0f, -1.0f, 1.0f ); // Bottom Right Of The Quad
//
//        gl.glColor3f( 1f,1f,0f ); //yellow (red + green)
//        gl.glVertex3f( 1.0f, -1.0f, -1.0f ); // Bottom Left Of The Quad
//        gl.glVertex3f( -1.0f, -1.0f, -1.0f ); // Bottom Right Of The Quad
//        gl.glVertex3f( -1.0f, 1.0f, -1.0f ); // Top Right Of The Quad (Back)
//        gl.glVertex3f( 1.0f, 1.0f, -1.0f ); // Top Left Of The Quad (Back)
//
//        gl.glColor3f( 1f,0f,1f ); //purple (red + green)
//        gl.glVertex3f( -1.0f, 1.0f, 1.0f ); // Top Right Of The Quad (Left)
//        gl.glVertex3f( -1.0f, 1.0f, -1.0f ); // Top Left Of The Quad (Left)
//        gl.glVertex3f( -1.0f, -1.0f, -1.0f ); // Bottom Left Of The Quad
//        gl.glVertex3f( -1.0f, -1.0f, 1.0f ); // Bottom Right Of The Quad
//
//        gl.glColor3f( 0f,1f, 1f ); //sky blue (blue +green)
//        gl.glVertex3f( 1.0f, 1.0f, -1.0f ); // Top Right Of The Quad (Right)
//        gl.glVertex3f( 1.0f, 1.0f, 1.0f ); // Top Left Of The Quad
//        gl.glVertex3f( 1.0f, -1.0f, 1.0f ); // Bottom Left Of The Quad
//        gl.glVertex3f( 1.0f, -1.0f, -1.0f ); // Bottom Right Of The Quad

        rquad -= 0.05f;

    }

    @Override
    public void dispose( GLAutoDrawable drawable ) {

    }

    @Override
    public void init( GLAutoDrawable drawable ) {

        final GL2 gl = drawable.getGL().getGL2();
        gl.glShadeModel( GL2.GL_SMOOTH );
        gl.glClearColor( 0f, 0f, 0f, 0f );
        gl.glClearDepth( 1.0f );
        gl.glEnable( GL2.GL_DEPTH_TEST );
        gl.glDepthFunc( GL2.GL_LEQUAL );
        gl.glHint( GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST );

    }

    @Override
    public void reshape( GLAutoDrawable drawable, int x, int y, int width, int height ) {

        final GL2 gl = drawable.getGL().getGL2();
        if( height <= 0 )
            height = 1;

        final float h = ( float ) width / ( float ) height;
        gl.glViewport( 0, 0, width, height );
        gl.glMatrixMode( GL2.GL_PROJECTION );
        gl.glLoadIdentity();

        glu.gluPerspective( 45.0f, h, 1.0, 2000.0 );
        gl.glMatrixMode( GL2.GL_MODELVIEW );
        gl.glLoadIdentity();
    }

    public void clearCubes(){
        cubes.clear();
    }

    public Window(){

    }

    public static void main( String[] args ) {
//
//        final GLProfile profile = GLProfile.get(GLProfile.GL2);
//        GLCapabilities capabilities = new GLCapabilities(profile);
//
//        // The canvas
//        final GLCanvas glcanvas = new GLCanvas(capabilities);
//
//        Window cube = new Window();
//        cube.addCube(0f, 0f, 0f);
//        cube.addCubes();
//
//        glcanvas.addGLEventListener(cube);
//        glcanvas.setSize(700, 700);
//
//        final JFrame frame = new JFrame(" Multicolored cube");
//        frame.getContentPane().add(glcanvas);
//        frame.setSize(frame.getContentPane().getPreferredSize());
//        frame.setVisible(true);
//        final FPSAnimator animator = new FPSAnimator(glcanvas, 600, true);
//
//
//        animator.start();
//        try {
//            Thread.sleep(6000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        final GLCanvas glcanvas1 = new GLCanvas( capabilities );
//        glcanvas1.addGLEventListener( cube );
//        glcanvas1.setSize( 500, 500 );
//        frame.getContentPane().add( glcanvas1 );
//        final FPSAnimator animator1 = new FPSAnimator(glcanvas1, 300,true);
//        frame.setVisible( true );
//        animator1.start();
//    }
    }
}