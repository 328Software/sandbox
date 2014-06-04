package org.supply.simulator.sandbox.prototype;

import org.supply.simulator.sandbox.prototype.display.UserDisplay;
import org.supply.simulator.sandbox.prototype.input.Input;

/**
 * Created by Alex on 6/1/2014.
 */
public class Controller {

    public Controller () {
        UserDisplay display = UserDisplay.instance();


        display.setQuadRowsCols(1000, 1000);
        Input input = new Input();

        input.init();
        display.init();

        while (!display.isCloseRequested()) {

            // Update current input from user
            input.refreshInput();

            display.receiveInput(input.getCamera());

            display.render();

        }

        // Destroy OpenGL (Display)
        display.destroyOpenGL();
    }


}

