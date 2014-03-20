package org.badassgame.sandbox.display;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 3/16/14
 * Time: 12:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class QuadIds {
    protected Integer vaoId, vboId, vboiId, colorVboId;

    public QuadIds(Integer vaoId, Integer vboId, Integer vboiId, Integer colorVboId) {
        this.vaoId = vaoId;
        this.vboId = vboId;
        this.vboiId = vboiId;
        this.colorVboId = colorVboId;
    }

    int getVaoId() {
        return vaoId;
    }

    int getVboId() {
        return vboId;
    }

    int getVboiId() {
        return vboiId;
    }

    int getColorVboId() {
        return colorVboId;
    }
}
