package org.supply.simulator.sandbox.texture;

/**
 * Created by Alex on 6/29/2014.
 */
public class VertexData {
    // Vertex data
    private float[] xyzw = new float[] {0f, 0f, 0f, 1f};
    private byte[] rgba = new byte[] {1, 1, 1, 0};

    // Elements per parameter
    public static final int positionElementCount = 4;
    public static final int colorElementCount = 4;


    // Setters
    public void setXYZ(float x, float y, float z) {
        this.setXYZW(x, y, z, 1f);
    }

    public void setRGB(byte r, byte g, byte b) {
        this.setRGBA(r, g, b, (byte)0);
    }

    public void setXYZW(float x, float y, float z, float w) {
        this.xyzw = new float[] {x, y, z, w};
    }

    public void setRGBA(byte r, byte g, byte b, byte a) {
        this.rgba = new byte[] {r, g, b, a};
    }

    // Getters
    public PositionColorPair getElements() {
        float[] flout = new float[VertexData.positionElementCount];
        byte[] bout = new byte[VertexData.colorElementCount];
        int i = 0;

        // Insert XYZW elements
        flout[i++] = this.xyzw[0];
        flout[i++] = this.xyzw[1];
        flout[i++] = this.xyzw[2];
        flout[i] = this.xyzw[3];
        // Insert RGBA elementsv

        i = 0;

        bout[i++] = this.rgba[0];
        bout[i++] = this.rgba[1];
        bout[i++] = this.rgba[2];
        bout[i] = this.rgba[3];

        return new PositionColorPair(flout, bout);
    }



    public class PositionColorPair {
        private float[] positionData;
        private byte[] colorData;

        public PositionColorPair(float[] positionData, byte[] colorData) {
            this.positionData = positionData;
            this.colorData = colorData;
        }

        public float[] getPositionData() {
            return positionData;
        }

        public byte[] getColorData() {
            return colorData;
        }
    }

}
