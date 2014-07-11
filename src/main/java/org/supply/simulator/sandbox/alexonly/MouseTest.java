package org.supply.simulator.sandbox.alexonly;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import org.supply.simulator.sandbox.prototype.input.Camera;

/**
 * Created by Alex on 7/10/2014.
 */
public class MouseTest {
    private Camera camera;

    private final float rotationDelta = 0.02f;
    private final float rotationDelta2 = 3f;
    private final float posDelta = 0.02f;
    private final float mouseDeltaThresh = 0.000000001f;

    private final float scaleDelta = 0.1f;
    private final Vector3f scaleAddResolution = new Vector3f(scaleDelta, scaleDelta, scaleDelta);
    private final Vector3f scaleMinusResolution = new Vector3f(-scaleDelta, -scaleDelta,-scaleDelta);

    public void run() {
        Keyboard.enableRepeatEvents(true);
        camera = new Camera();
        camera.setModelPos(new Vector3f(0, 0, 0));
        camera.setModelAngle(new Vector3f(0, 0, 0));
        camera.setModelScale(new Vector3f(1, 1, 1));
        camera.setCameraPos(new Vector3f(0, 0, -1));
        camera.setCameraAngle(new Vector3f(0, 0, 0));
        while(!Display.isCloseRequested()) {
            go();
            Display.sync(60);

            Display.update();
        }
    }

    private void go() {
        Mouse.poll();
        System.out.println("MOUSE XY: " + Mouse.getX() + ", "+ Mouse.getY());




        //doKeyboard();
    }

    private void doKeyboard() {
        while(Keyboard.next()/*||Mouse.next()|| Mouse.isInsideWindow()*/) {
            // Only listen to events where the key was pressed (down event)
            if (!Keyboard.getEventKeyState()) continue;

            switch (Keyboard.getEventKey()) {

                case Keyboard.KEY_W:
                    camera.moveNorth(posDelta);
                    break;
                case Keyboard.KEY_S:
                    camera.moveSouth(posDelta);
                    break;
                case Keyboard.KEY_A:
                    camera.moveEast(posDelta);
                    break;
                case Keyboard.KEY_D:
                    camera.moveWest(posDelta);
                    break;

                case Keyboard.KEY_UP:
                    camera.rotateUp(rotationDelta);
                    break;
                case Keyboard.KEY_DOWN:
                    camera.rotateDown(rotationDelta);
                    break;
                case Keyboard.KEY_RIGHT:
                    camera.rotateRight(rotationDelta);
                    break;
                case Keyboard.KEY_LEFT:
                    camera.rotateLeft(rotationDelta);
                    break;

                case Keyboard.KEY_I:
                    camera.rotateMUp(rotationDelta2);
                    break;
                case Keyboard.KEY_K:
                    camera.rotateMDown(rotationDelta2);
                    break;
                case Keyboard.KEY_L:
                    camera.rotateMRight(rotationDelta2);
                    break;
                case Keyboard.KEY_J:
                    camera.rotateMLeft(rotationDelta2);
                    break;
            }
        }
    }


}
