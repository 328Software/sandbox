package org.supply.simulator.sandbox.prototype.input;

import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Alex on 6/3/2014.
 */
public class  Camera {
    private Vector3f modelPos;
    private Vector3f modelAngle;
    private Vector3f modelScale;
    private Vector3f cameraPos;
    private Vector3f cameraAngle;


    //***** Movement Methods
    public void moveNorth(float posDelta) {
        cameraPos.y -= posDelta;
    }

    public void moveSouth(float posDelta) {
        cameraPos.y += posDelta;
    }

    public void moveEast(float posDelta) {
        cameraPos.x += posDelta;
    }

    public void moveWest(float posDelta) {
        cameraPos.x -= posDelta;
    }

    public void rotateUp(float rotationDelta) {
        cameraAngle.x += rotationDelta;
    }

    public void rotateDown(float rotationDelta) {
        cameraAngle.x -= rotationDelta;
    }

    public void rotateLeft(float rotationDelta) {
        cameraAngle.y -= rotationDelta;
    }

    public void rotateRight(float rotationDelta) {
        cameraAngle.y += rotationDelta;
    }


    public void rotateMUp(float rotationDelta) {
        modelAngle.x += rotationDelta;
    }

    public void rotateMDown(float rotationDelta) {
        modelAngle.x -= rotationDelta;
    }

    public void rotateMLeft(float rotationDelta) {
        modelAngle.y -= rotationDelta;
    }

    public void rotateMRight(float rotationDelta) {
        modelAngle.y += rotationDelta;
    }
    //***** Movement Methods

    //*****Setters
    public void setCameraAngle(Vector3f cameraAngle) {
        this.cameraAngle = cameraAngle;
    }

    public void setModelPos(Vector3f modelPos) {
        this.modelPos = modelPos;
    }

    public void setModelAngle(Vector3f modelAngle) {
        this.modelAngle = modelAngle;
    }

    public void setModelScale(Vector3f modelScale) {
        this.modelScale = modelScale;
    }

    public void setCameraPos(Vector3f cameraPos) {
        this.cameraPos = cameraPos;
    }
    //*****Setters

    //*****Getters
    public Vector3f getCameraAngle() {
        return this.cameraAngle;
    }

    public Vector3f getModelPos() {
        return this.modelPos;
    }

    public Vector3f getModelAngle() {
        return this.modelAngle;
    }

    public Vector3f getModelScale() {
        return this.modelScale;
    }

    public Vector3f getCameraPos() {
        return this.cameraPos;
    }
    //*****Getters

}
