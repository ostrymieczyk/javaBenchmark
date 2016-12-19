package Test.GPU;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.ovr.OVRVector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Created by Robert on 17.12.2016.
 */
public class Window {

    // The window handle
    private long window;

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        try {
            init();
            loop();

            // Free the window callbacks and destroy the window
            glfwFreeCallbacks(window);
            glfwDestroyWindow(window);
        } finally {
            // Terminate GLFW and free the error callback
            glfwTerminate();
            glfwSetErrorCallback(null).free();
        }
    }

    private void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure our window
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        int WIDTH = 500;
        int HEIGHT = 500;

        // Create the window
        window = glfwCreateWindow(WIDTH, HEIGHT, "Hello World!", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true); // We will detect this in our rendering loop
        });

        // Get the resolution of the primary monitor
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        // Center our window
        glfwSetWindowPos(
                window,
                (vidmode.width() - WIDTH) / 2,
                (vidmode.height() - HEIGHT) / 2
        );

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
//        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
    }

    List<float[]> list = new ArrayList<float[]>();
    List<float[]> list1 = new ArrayList<float[]>();
    Random random = new Random();

    float[] generateList(){
        float [] f = new float[3];
        for (int i=0; i < f.length; i++){
            f[i] = random.nextFloat();
        }
        return f;
    }

    private void loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        // Set the clear color
        glClearColor(0.5f, 0.0f, 0.0f, 0.0f);

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while ( !glfwWindowShouldClose(window) ) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            int g = 0;
            for (int j = 0; j<list.size(); j+=3) {
                glBegin(GL_TRIANGLES);

                glColor3f(list1.get(g)[0], list1.get(g)[1], list1.get(g)[2]);
                glVertex3f(list.get(j)[0], list.get(j)[1], list.get(j)[2]);
                glVertex3f(list.get(j+1)[0], list.get(j+1)[1], list.get(j+1)[2]);
                glVertex3f(list.get(j+2)[0], list.get(j+2)[1], list.get(j+2)[2]);
                g++;
                glEnd();
            }

            for (int y = 0; y< 1; y++) {
                float[] c = generateList();
                glBegin(GL_TRIANGLES);
                glColor3f(c[0], c[1], c[2]);
                for (int i = 0; i < 3; i++) {
                    float[] f = generateList();
                    glVertex3f(f[0], f[1], f[2]);
                    list.add(f);
                }
                glEnd();
                list1.add(c);
            }

            if(list.size()>1000000){
                list.clear();
            }

            glfwSwapBuffers(window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();

        }
    }

    public static void main(String[] args) {
        System.out.println(System.console());

        new Thread() {
            @Override
            public void run() {
                super.run();
                new Window().run();
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                super.run();
                new Window().run();
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                super.run();
                new Window().run();
            }
        }.start();
    }


}
