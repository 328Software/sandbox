package org.supply.simulator.sandbox;

import org.junit.Before;
import org.junit.Test;
import org.lwjgl.util.vector.Matrix3f;
import org.supply.simulator.sandbox.prototype.util.DebugUtilities;

/**
 * Created by Alex on 6/7/2014.
 */
public class UtilitiesTest {
    @Before
    public void createFixture() {
    }


    @Test
    public void testDebugUtilities() {
        Matrix3f mat = new Matrix3f();

        mat.m00 = 0.1f;
        mat.m01 = 0.2f;
        mat.m02 = 0.3f;

        mat.m10 = 1.1f;
        mat.m11 = 1.2f;
        mat.m12 = 1.3f;

        mat.m20 = 2.1f;
        mat.m21 = 2.2f;
        mat.m22 = 2.3f;

        System.out.println("Begin Matrix Print Test");
        DebugUtilities.printMatrix3f(mat);
        System.out.println("END Matrix Print Test");


    }


}
