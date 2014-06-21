package org.supply.simulator.sandbox.sample;

import org.lwjgl.LWJGLException;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.Drawable;

/**
 * Created by Alex on 6/20/2014.
 */
public class Test implements Drawable{
    @Override
    public boolean isCurrent() throws LWJGLException {
        return false;
    }

    @Override
    public void makeCurrent() throws LWJGLException {

    }

    @Override
    public void releaseContext() throws LWJGLException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void setCLSharingProperties(PointerBuffer pointerBuffer) throws LWJGLException {

    }
}
