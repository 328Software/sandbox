package org.supply.simulator.sandbox.prototype.input;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Alex on 6/1/2014.
 */
public class InputData {


    private Matrix4f modelMatrix= new Matrix4f();
    private Vector3f modelPos   = new Vector3f(0, 0, 0);
    private Vector3f modelAngle = new Vector3f(0, 0, 0);
    private Vector3f modelScale = new Vector3f(1, 1, 1);
    private Vector3f cameraAngle= new Vector3f(0, 0, -1);
    private Vector3f cameraPos  = new Vector3f(0, 0, 0);

    public Matrix4f getModelMatrix() {
        return modelMatrix;
    }

    public void setModelMatrix(Matrix4f modelMatrix) {
        this.modelMatrix = modelMatrix;
    }

    public Vector3f getModelPos() {
        return modelPos;
    }

    public void setModelPos(Vector3f modelPos) {
        this.modelPos = modelPos;
    }

    public Vector3f getModelAngle() {
        return modelAngle;
    }

    public void setModelAngle(Vector3f modelAngle) {
        this.modelAngle = modelAngle;
    }

    public Vector3f getModelScale() {
        return modelScale;
    }

    public void setModelScale(Vector3f modelScale) {
        this.modelScale = modelScale;
    }

    public Vector3f getCameraPos() {
        return cameraPos;
    }

    public void setCameraPos(Vector3f cameraPos) {
        this.cameraPos = cameraPos;
    }

    public Vector3f getCameraAngle() {
        return cameraAngle;
    }

    public void setCameraAngle(Vector3f cameraAngle) {
        this.cameraAngle = cameraAngle;
    }


}
