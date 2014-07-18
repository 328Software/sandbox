package org.supply.simulator.sandbox.texture;

import org.supply.simulator.display.renderable.ChunkRenderable;
import org.supply.simulator.display.renderable.AbstractChunkRenderable;

/**
 * Created by Alex on 7/13/2014.
 */
public class GUIMenu extends AbstractChunkRenderable implements ChunkRenderable {
    private boolean isBuilt;
    private boolean isDestroyed;

    public GUIMenu () {
        isBuilt = false;
        isDestroyed = false;
    }

    @Override
    public void build() {
        isBuilt = true;
    }

    @Override
    public void render() {

    }

    @Override
    public void destroy() {

        isDestroyed = true;
    }






    @Override
    public boolean isBuilt() {
        return isBuilt;
    }

    @Override
    public boolean isDestroyed() {
        return isDestroyed;
    }
}
