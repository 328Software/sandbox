package org.supply.simulator.sandbox;

import org.supply.simulator.sandbox.alexonly.OtherBuffers;
import org.supply.simulator.sandbox.prototype.Controller;
import org.supply.simulator.sandbox.quad.FancierQuadTest;
import org.junit.Before;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: Brandon
 * Date: 2/15/14
 * Time: 6:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class LwjglTest {

    @Before
    public void createFixture() {
    }

//    @Test
//    public void testStart() {
//        new LwjglExample().start();
//    }
//
//    @Test
//    public void testInputStart() {
//        new LwjglInputExample().start();
//    }
//
//    @Test
//    public void testBlueGreen() {
//        new LwjglBlueGreenExample().start();
//    }
//
//    @Test
//    public void testQuadGL30() {
//        new QuadGL30Example();
//    }
//
//    @Test
//    public void testPrototype() {
//        new Controller();
//    }

    @Test
    public void testOtherBuffers() {
        OtherBuffers b = new OtherBuffers();
        b.run();
    }

//    @Test
//    public void testTriangles() {
//        new FancierQuadTest(1000,1000);
//    }

//    @Test
//    public void testTriangles2() {
//        new QuadArrayGL30(2,10);
//    }
}
