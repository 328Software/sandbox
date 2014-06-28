package org.supply.simulator.sandbox.alexonly;

/**
 * Created by Alex on 6/28/2014.
 */
public enum TestEnum {
    CLEAR(0),
    CHUNK(1),
    VIEW(2),
    MENU(3),
    ENTITY(4);

    private final int value;

    public static final int COUNT = 5;

    TestEnum(int value) {
        this.value = value;
    }

    public int value () {
        return this.value;
    }
}
