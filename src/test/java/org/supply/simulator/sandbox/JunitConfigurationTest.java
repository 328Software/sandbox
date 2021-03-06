package org.supply.simulator.sandbox;

import org.junit.*;
import org.supply.simulator.sandbox.examples.Asdf;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: Brandon
 * Date: 2/15/14
 * Time: 3:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class JunitConfigurationTest {

    @Before
    public void createFixture() {
        assertTrue(true);
        System.out.println("creating a fixture");
    }

    @Test
    public void basicUnitTest() {
        assertTrue(true);
        System.out.println("running unit test...");
        System.out.println(new Asdf());
    }

    @Test
    public void basicUnitTestTwo() {
        assertTrue(true);
        System.out.println("running unit test two...");
        try {
            Class.forName("org.supply.simulator.data.impl.impl");
        } catch (ClassNotFoundException e) {
            fail("could not find class impl");
        }
    }
}
