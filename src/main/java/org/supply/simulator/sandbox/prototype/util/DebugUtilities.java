package org.supply.simulator.sandbox.prototype.util;

import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Matrix4f;

/**
 * Created by Alex on 6/7/2014.
 */
public class DebugUtilities {

    public static void printMatrix3f(Matrix3f mat) {

        System.out.println("{" + mat.m00 + " " + mat.m01 + " " + mat.m02);
        System.out.println(" " + mat.m10 + " " + mat.m11 + " " + mat.m12);
        System.out.println(" " + mat.m20 + " " + mat.m21 + " " + mat.m22 + "}");

    }

    public static void printMatrix4f(Matrix4f mat) {

        System.out.println("{" + mat.m00 + " " + mat.m01 + " " + mat.m02+ " " + mat.m03);
        System.out.println(" " + mat.m10 + " " + mat.m11 + " " + mat.m12+ " " + mat.m13);
        System.out.println(" " + mat.m20 + " " + mat.m21 + " " + mat.m22+ " " + mat.m23);
        System.out.println(" " + mat.m30 + " " + mat.m31 + " " + mat.m32+ " " + mat.m33+"}");

    }
}
