package Test.GPU;

import Controller.ResultController;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.Animator;

import java.util.*;

public class Window implements GLEventListener {

    private final GLU glu = new GLU();
    private static final Set<Cube> cubes = new HashSet<>();
    private static final float point = 1.0f;
    private Float out = 20f;
    private Float rotate = 0f;

    private static long FPS = 0;

    public void addCube(float diffx, float diffy, float diffz){
        Random rand = new Random();
        float[][][] f = {
                {
                    {point + diffx, point + diffy, -point + diffz},
                    {-point + diffx, point + diffy, -point + diffz},
                    {-point + diffx, point + diffy, point + diffz},
                    {point + diffx, point + diffy, point + diffz}
                },
                {
                    {point + diffx, -point + diffy, point + diffz},
                    {-point + diffx, -point + diffy, point + diffz},
                    {-point + diffx, -point + diffy, -point + diffz},
                    {point + diffx, -point + diffy, -point+diffz}
                },
                {
                    {point + diffx, point + diffy, point + diffz},
                    {-point + diffx, point + diffy, point + diffz},
                    {-point + diffx, -point + diffy, point + diffz},
                    {point + diffx, -point + diffy, point + diffz}
                },
                {
                    {point + diffx, -point + diffy, -point + diffz},
                    {-point + diffx, -point + diffy, -point + diffz},
                    {-point + diffx, point + diffy, -point + diffz},
                    {point + diffx, point + diffy, -point + diffz}
                },
                {
                    {-point + diffx, point + diffy, point + diffz},
                    {-point + diffx, point + diffy, -point + diffz},
                    {-point + diffx, -point + diffy, -point + diffz},
                    {-point + diffx, -point + diffy, point + diffz}
                },
                {
                    {point + diffx, point + diffy, -point + diffz},
                    {point + diffx, point + diffy, point + diffz},
                    {point + diffx, -point + diffy, point + diffz},
                    {point + diffx, -point + diffy, -point + diffz}
                }};

        float[][] c = {{rand.nextFloat(), rand.nextFloat(), rand.nextFloat()},
                {rand.nextFloat(), rand.nextFloat(), rand.nextFloat()},
                {rand.nextFloat(), rand.nextFloat(), rand.nextFloat()},
                {rand.nextFloat(), rand.nextFloat(), rand.nextFloat()},
                {rand.nextFloat(), rand.nextFloat(), rand.nextFloat()},
                {rand.nextFloat(), rand.nextFloat(), rand.nextFloat()}};

        synchronized (cubes) {
            cubes.add(new Cube(f, c));
        }
    }

    public void addCubes() {

        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        GLWindow glWindow = GLWindow.create(capabilities);
        glWindow.getDefaultCloseOperation();
        glWindow.addGLEventListener(this);
        glWindow.setSize(1024, 768);

        glWindow.setVisible(true);

        final Animator animator = new Animator(glWindow);
        FPS = 0;

        animator.start();

        float max = 2.5f;
        long end = System.currentTimeMillis() + 30000;
        flag:
        while (true) {
            for (float i = -max; i <= max; i += 2.5f) {
                for (float j = -max; j <= max; j += 2.5f)
                {
                    addCube(max, i, j);
                    addCube(i, max, j);
                    addCube(i, j, max);
                    addCube(-max, i, j);
                    addCube(i, -max, j);
                    addCube(i, j, -max);
                    try {
                        Thread.currentThread().sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (System.currentTimeMillis() > end)
                        break flag;
                    synchronized (rotate) {
                        rotate += 0.5f;
                    }
                }
            }
            synchronized (out) {
                out += 10.0f;
            }
            max += 2.5f;
        }
        ResultController.setGpuResult(FPS);
        FPS = 0;
        animator.stop();
        glWindow.destroy();
    }

    @Override
    public void display( GLAutoDrawable drawable ) {

        final GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT );
        gl.glLoadIdentity();
        synchronized (out) {
            gl.glTranslatef(0f, 0f, -out);
        }

        synchronized (rotate) {
            gl.glRotatef(rotate, 1.0f, 1.0f, 1.0f);
        }
        List<Cube> cc = new ArrayList<>();

        cc.clear();
        synchronized(cubes) {
            cc.addAll(cubes);
        }
        gl.glBegin(GL2.GL_QUADS);
        cc.forEach(cube ->
        {
            Arrays.stream(cube.getWalls()).forEach(wall ->
            {
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
            });
        });

        gl.glEnd();
        gl.glFlush();
        FPS++;

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
        gl.setSwapInterval(0);

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
}