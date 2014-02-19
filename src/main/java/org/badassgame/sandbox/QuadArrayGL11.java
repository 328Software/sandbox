package org.badassgame.sandbox;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 2/18/14
 * Time: 6:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class QuadArrayGL11 {

    private static final int ARRAY_SIZE = 20;
    private static final double QUAD_SIZE = 1.0;

    String windowTitle = "You suck";

    public boolean closeRequested = false;


    public void run() {

        createWindow();
        initGL();

        while (!closeRequested) {
            pollInput();
            renderGL();

            Display.update();
        }

        cleanup();
    }

    private void initGL() {

		/* OpenGL */
        int width = Display.getDisplayMode().getWidth();
        int height = Display.getDisplayMode().getHeight();

        GL11.glViewport(0, 0, width, height); // Reset The Current Viewport
        GL11.glMatrixMode(GL11.GL_PROJECTION); // Select The Projection Matrix
        GL11.glLoadIdentity(); // Reset The Projection Matrix
        GLU.gluPerspective(45.0f, ((float) width / (float) height), 0.1f, 100.0f); // Calculate The Aspect Ratio Of The Window
        GL11.glMatrixMode(GL11.GL_MODELVIEW); // Select The Modelview Matrix
        GL11.glLoadIdentity(); // Reset The Modelview Matrix

        GL11.glShadeModel(GL11.GL_SMOOTH); // Enables Smooth Shading
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // Black Background
        GL11.glClearDepth(1.0f); // Depth Buffer Setup
        GL11.glEnable(GL11.GL_DEPTH_TEST); // Enables Depth Testing
        GL11.glDepthFunc(GL11.GL_LEQUAL); // The Type Of Depth Test To Do
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST); // Really Nice Perspective Calculations

    }


    private void renderGL() {

        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); // Clear The Screen And The Depth Buffer
        GL11.glLoadIdentity(); // Reset The View

        for (int i = 0; i < ARRAY_SIZE; i++) {
            for (int j = 0; j < ARRAY_SIZE; j++) {

                GL11.glBegin(GL11.GL_QUADS); // Start Drawing The Cube
                if (j%2 == 0) {
                    GL11.glColor3f(0.0f, 1.0f, 0.0f); // Set The Color To Green
                } else {
                    GL11.glColor3f(1.0f, 0.0f, 0.0f); // Set The Color To Red
                }
                GL11.glVertex3d(QUAD_SIZE, QUAD_SIZE, -QUAD_SIZE); // Top Right Of The Quad (Top)
                GL11.glVertex3d(-QUAD_SIZE, QUAD_SIZE, -QUAD_SIZE); // Top Left Of The Quad (Top)
                GL11.glVertex3d(-QUAD_SIZE, QUAD_SIZE, QUAD_SIZE); // Bottom Left Of The Quad (Top)
                GL11.glVertex3d(QUAD_SIZE, QUAD_SIZE, QUAD_SIZE); // Bottom Right Of The Quad (Top)
                GL11.glEnd(); // Done Drawing The Quad
                GL11.glTranslated(-(i*QUAD_SIZE), -(j*QUAD_SIZE), -6.0); // Move Left And Into The Screen
            }
        }

    }

    /**
     * Poll Input
     */
    public void pollInput() {

        // scroll through key events
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE)
                    closeRequested = true;
            }
        }

        if (Display.isCloseRequested()) {
            closeRequested = true;
        }
    }


    private void createWindow() {
        try {
            Display.setDisplayMode(new DisplayMode(640, 480));
            Display.setVSyncEnabled(true);
            Display.setTitle(windowTitle);
            Display.create();
        } catch (LWJGLException e) {
            Sys.alert("Error", "Initialization failed!\n\n" + e.getMessage());
            System.exit(0);
        }
    }

    /**
     * Destroy and clean up resources
     */
    private void cleanup() {
        Display.destroy();
    }

    public static void main(String[] args) {
        QuadArrayGL11 lesson = new QuadArrayGL11();
        lesson.run();
    }
}