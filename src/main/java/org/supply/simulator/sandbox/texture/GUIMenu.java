package org.supply.simulator.sandbox.texture;

/**
 * Created by Alex on 7/13/2014.
 */
public class GUIMenu{
    private boolean isBuilt;
    private boolean isDestroyed;

    public GUIMenu () {
        isBuilt = false;
        isDestroyed = false;
    }


    public void build() {
        isBuilt = true;
    }

    public void render() {

    }

    public void destroy() {

        isDestroyed = true;
    }







    public boolean isBuilt() {
        return isBuilt;
    }


    public boolean isDestroyed() {
        return isDestroyed;
    }
}
