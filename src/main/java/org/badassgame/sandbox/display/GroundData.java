package org.badassgame.sandbox.display;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 3/16/14
 * Time: 1:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class GroundData {
    private int[] xyz;
    private byte[] rgba;

    private static final int COORDINATE_ELEMENT_SIZE_IN_BYTES = 4;
    private static final int COLOR_ELEMENT_SIZE_IN_BYTES = 4;

    private static final int COORDINATE_ELEMENT_COUNT = 3;
    private static final int COLOR_ELEMENT_COUNT = 4;

    private static final int COORDINATE_TOTAL_SIZE_IN_BYTES = COORDINATE_ELEMENT_SIZE_IN_BYTES * COORDINATE_ELEMENT_COUNT;
    private static final int COLOR_TOTAL_SIZE_IN_BYTES = COLOR_ELEMENT_SIZE_IN_BYTES * COLOR_ELEMENT_COUNT;

    public void setXYZ(int x, int y, int z) {
        if (this.xyz == null) this.xyz = new int[3];
        this.xyz[0] = x;
        this.xyz[1] = y;
        this.xyz[2] = z;
    }

    public void setRGB(byte r, byte g, byte b) {
        if(this.rgba == null) this.rgba = new byte[4];
        this.rgba[0] = r;
        this.rgba[1] = g;
        this.rgba[2] = b;
    }

    public int[] getXYZ() {
        return this.xyz;
    }

    public byte[] getRGBA() {
        return this.rgba;
    }

}
