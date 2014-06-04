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
    private final float posDelta = 0.02f;
    private final float mouseDeltaThresh = 0.000000001f;

    private final float scaleDelta = 0.1f;
    private final Vector3f scaleAddResolution = new Vector3f(scaleDelta, scaleDelta, scaleDelta);
    private final Vector3f scaleMinusResolution = new Vector3f(-scaleDelta, -scaleDelta,-scaleDelta);

    private Camera camera;

    public void init() {
        Keyboard.enableRepeatEvents(true);
        camera = new Camera();
        camera.setModelPos(new Vector3f(0, 0, 0));
        camera.setModelAngle(new Vector3f(0, 0, 0));
        camera.setModelScale(new Vector3f(1, 1, 1));
        camera.setCameraPos(new Vector3f(0, 0, -1));
        camera.setCameraAngle(new Vector3f(0, 0, 0));

    }

    public void refreshInput() {
        Mouse.poll();
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
            }
        }
    }

    //*****Getters
    public Camera getCamera() {
        return this.camera;
    }
    //*****Getters

    //*****Private Methods
//    private void getMouseInput() {
//        Mouse.poll();
//        while(Mouse.next()) {
//            if (Mouse.getDX() > mouseDeltaThresh) {
//                System.out.println("X:" + Mouse.getDX());
//                cameraAngle.x += rotationDelta;
//            } else if (Mouse.getDX() < -mouseDeltaThresh) {
//                System.out.println("X:" + Mouse.getDX());
//                cameraAngle.x -= rotationDelta;
//            }
//            if (Mouse.getDY() > mouseDeltaThresh) {
//                System.out.println("Y:" + Mouse.getDY());
//                cameraAngle.y += rotationDelta;
//            } else if (Mouse.getDY() < -mouseDeltaThresh) {
//                System.out.println("Y:" + Mouse.getDY());
//                cameraAngle.y -= rotationDelta;
//            }
//        }
//    }
//    private void getKeyboardInput() {
//        while(Keyboard.next()) {
//            if (!Keyboard.getEventKeyState()) continue;
//
//            switch (Keyboard.getEventKey()) {
//                // Move
//
//                case Keyboard.KEY_W:
//                    cameraPos.z += posDelta;
//                    break;
//                case Keyboard.KEY_S:
//                    cameraPos.z -= posDelta;
//                    break;
//
//                case Keyboard.KEY_A:
//                    cameraPos.x += posDelta;
//                    break;
//                case Keyboard.KEY_D:
//                    cameraPos.x -= posDelta;
//                    break;
//
//                case Keyboard.KEY_UP:
//                    cameraAngle.x += posDelta;
//                    break;
//                case Keyboard.KEY_DOWN:
//                    cameraAngle.x -= posDelta;
//                    break;
//
//                // Scale
//                case Keyboard.KEY_P:
//                    Vector3f.add(modelScale, scaleAddResolution, modelScale);
//                    break;
//                case Keyboard.KEY_M:
//                    Vector3f.add(modelScale, scaleMinusResolution, modelScale);
//                    break;
//                // Rotation
//
//                case Keyboard.KEY_LEFT:
//                    modelAngle.z += rotationDelta;
//                    break;
//                case Keyboard.KEY_RIGHT:
//                    modelAngle.z -= rotationDelta;
//                    break;
//                case Keyboard.KEY_Y:
//                    cameraAngle.y += posDelta;
//                    break;
//                case Keyboard.KEY_G:
//                    cameraAngle.y -= posDelta;
//                    break;
//                case Keyboard.KEY_T:
//                    cameraAngle.x += posDelta;
//                    break;
//                case Keyboard.KEY_F:
//                    cameraAngle.x -= posDelta;
//                    break;
//            }
//        }
//    }
    //*****Private Methods
}
