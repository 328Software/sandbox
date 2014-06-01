package org.supply.simulator.sandbox.prototype.input;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Alex on 6/1/2014.
 */
public class Input {

    private final float rotationDelta = 3f;
    private final float scaleDelta = 0.1f;
    private final float posDelta = 0.02f;
    private final float mouseDeltaThresh = 0.000000001f;

    private Matrix4f modelMatrix = null;
    private Vector3f modelPos = null;
    private Vector3f modelAngle = null;
    private Vector3f modelScale = null;
    private Vector3f cameraPos = null;
    private Vector3f cameraAngle = null;

    public void init() {
        modelPos = new Vector3f(0, 0, 0);
        modelAngle = new Vector3f(0, 0, 0);
        modelScale = new Vector3f(1, 1, 1);
        cameraPos = new Vector3f(0, 0, -1);
        cameraAngle = new Vector3f(0, 0, 0);
    }

    public void refreshInput() {
        //-- Input processing

        Keyboard.enableRepeatEvents(true);

        Vector3f scaleAddResolution = new Vector3f(scaleDelta, scaleDelta, scaleDelta);
        Vector3f scaleMinusResolution = new Vector3f(-scaleDelta, -scaleDelta,
                -scaleDelta);
//

        while(Keyboard.next()/*|| Mouse.isInsideWindow()*/) {
            // Only listen to events where the key was pressed (down event)
            if (!Keyboard.getEventKeyState()) continue;

            switch (Keyboard.getEventKey()) {
                // Move

                case Keyboard.KEY_W:
                    cameraPos.z += posDelta;
                    break;
                case Keyboard.KEY_S:
                    cameraPos.z -= posDelta;
                    break;

                case Keyboard.KEY_A:
                    cameraPos.x += posDelta;
                    break;
                case Keyboard.KEY_D:
                    cameraPos.x -= posDelta;
                    break;


                case Keyboard.KEY_UP:
                    cameraAngle.x += posDelta;
                    break;
                case Keyboard.KEY_DOWN:
                    cameraAngle.x -= posDelta;
                    break;

                // Scale
                case Keyboard.KEY_P:
                    Vector3f.add(modelScale, scaleAddResolution, modelScale);
                    break;
                case Keyboard.KEY_M:
                    Vector3f.add(modelScale, scaleMinusResolution, modelScale);
                    break;
                // Rotation
                case Keyboard.KEY_LEFT:
                    modelAngle.z += rotationDelta;
                    break;
                case Keyboard.KEY_RIGHT:
                    modelAngle.z -= rotationDelta;
                    break;
                case Keyboard.KEY_Y:
                    cameraAngle.y += posDelta;
                    break;
                case Keyboard.KEY_G:
                    cameraAngle.y -= posDelta;
                    break;
                case Keyboard.KEY_T:
                    cameraAngle.x += posDelta;
                    break;
                case Keyboard.KEY_F:
                    cameraAngle.x -= posDelta;
                    break;
            }


            if (Mouse.getDX()>mouseDeltaThresh) {
                System.out.println("X:"+Mouse.getDX());
                cameraAngle.x += rotationDelta;
            } else if (Mouse.getDX()<-mouseDeltaThresh) {
                System.out.println("X:"+Mouse.getDX());
                cameraAngle.x -= rotationDelta;
            }
            if (Mouse.getDY()>mouseDeltaThresh) {
                System.out.println("Y:"+Mouse.getDY());
                cameraAngle.y += rotationDelta;
            } else if (Mouse.getDY()<-mouseDeltaThresh) {
                System.out.println("Y:"+Mouse.getDY());
                cameraAngle.y -= rotationDelta;
            }

        }
    }

    public Vector3f getCameraAngle() {
        return cameraAngle;
    }

    public Matrix4f getModelMatrix() {
        return modelMatrix;
    }

    public Vector3f getModelPos() {
        return modelPos;
    }

    public Vector3f getModelAngle() {
        return modelAngle;
    }

    public Vector3f getModelScale() {
        return modelScale;
    }

    public Vector3f getCameraPos() {
        return cameraPos;
    }
}
