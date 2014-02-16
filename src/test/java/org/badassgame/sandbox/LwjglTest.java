package org.badassgame.sandbox;

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

    @Test
    public void testStart() {
        new LwjglExample().start();
    }

    @Test
    public void testInputStart() {
        new LwjglInputExample().start();
    }

    @Test
    public void testBlueGreen() {
        new LwjglBlueGreenExample().start();
    }

    @Test
    public void testQuadGL30() {
        new QuadGL30Example();
    }
}
