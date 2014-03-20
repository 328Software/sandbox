package org.badassgame.sandbox.display;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 3/16/14
 * Time: 1:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class GroundChunkData {
    int[][] height;
    Color[][] color;
    boolean[][] cliff;

    public GroundChunkData(int[][] height, Color[][] color, boolean[][] cliff) {
        this.height = height;
        this.color = color;
        this.cliff = cliff;
    }

    public float getHeight(float x, float y) {
        return height[(int)x][(int)y];
    }

    public boolean isCliff(float x, float y) {
        return cliff[(int)x][(int)y];
    }

    public Color getColor(float x, float y) {
        return color[(int)x][(int)y];
    }


    public class Color {

        byte[] rgba;

        Color(byte r, byte g, byte b, byte a) {
            this.rgba = new byte[4];
            this.rgba[0] = r;
            this.rgba[1] = g;
            this.rgba[2] = b;
            this.rgba[3] = a;
        }

        public byte getR() {
            return this.rgba[0];
        }
        public byte getG() {
            return this.rgba[1];
        }
        public byte getB() {
            return this.rgba[2];
        }
        public byte getA() {
            return this.rgba[3];
        }
    }
}
