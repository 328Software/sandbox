package org.supply.simulator.sandbox.prototype.display;

/**
 * Created by Alex on 6/1/2014.
 */
public class QuadIds {
    private Integer vaoId, vboId, vboiId, colorVboId;

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