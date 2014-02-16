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

    LwjglExample example;

    @Before
    public void createFixture() {
        example = new LwjglExample();
    }

    @Test
    public void testStart() {
        example.start();
    }
}
