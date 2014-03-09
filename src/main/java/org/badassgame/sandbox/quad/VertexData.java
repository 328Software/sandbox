package org.badassgame.sandbox.quad;

public class VertexData {
    // Vertex data
    private float[] xyzw = new float[] {0f, 0f, 0f, 1f};
    private byte[] rgba = new byte[] {1, 1, 1, 0};
//    private float[] st = new float[] {0f, 0f};

    // The amount of bytes an element has
    public static final int posElementBytes = 4;
    public static final int colorElementBytes = 1;

    // Elements per parameter
    public static final int positionElementCount = 4;
    public static final int colorElementCount = 4;
//    public static final int textureElementCount = 2;

    // Bytes per parameter
    public static final int positionBytesCount = positionElementCount * posElementBytes;
    public static final int colorByteCount = colorElementCount * colorElementBytes;
//    public static final int textureByteCount = textureElementCount * posElementBytes;

    // Byte offsets per parameter
    public static final int positionByteOffset = 0;
    public static final int colorByteOffset = positionByteOffset + positionBytesCount;
    public static final int textureByteOffset = colorByteOffset + colorByteCount;

    // The amount of elements that a vertex has
    public static final int elementCount = positionElementCount +
            colorElementCount;// + textureElementCount;
    // The size of a vertex in bytes, like in C/C++: sizeof(Vertex)
    public static final int stride = positionBytesCount + colorByteCount;// +
            //textureByteCount;

    // Setters
    public void setXYZ(float x, float y, float z) {
        this.setXYZW(x, y, z, 1f);
    }

    public void setRGB(byte r, byte g, byte b) {
        this.setRGBA(r, g, b, (byte)0);
    }

//    public void setST(float s, float t) {
//        this.st = new float[] {s, t};
//    }

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





        // Insert ST elements
//        out[i++] = this.st[0];
//        out[i++] = this.st[1];

        return new PositionColorPair(flout, bout);
    }

    public float[] getXYZW() {
        return new float[] {this.xyzw[0], this.xyzw[1], this.xyzw[2], this.xyzw[3]};
    }

    public float[] getXYZ() {
        return new float[] {this.xyzw[0], this.xyzw[1], this.xyzw[2]};
    }

    public float[] getRGBA() {
        return new float[] {this.rgba[0], this.rgba[1], this.rgba[2], this.rgba[3]};
    }

    public float[] getRGB() {
        return new float[] {this.rgba[0], this.rgba[1], this.rgba[2]};
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
//    public float[] getST() {
//        return new float[] {this.st[0], this.st[1]};
//    }
}