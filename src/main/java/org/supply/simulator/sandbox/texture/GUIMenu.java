package org.supply.simulator.sandbox.texture;

import org.supply.simulator.display.supplyrenderable.AbstractChunkSupplyRenderable;
import org.supply.simulator.display.supplyrenderable.ChunkSupplyRenderable;

/**
 * Created by Alex on 7/13/2014.
 */
public class GUIMenu extends AbstractChunkSupplyRenderable implements ChunkSupplyRenderable {
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
