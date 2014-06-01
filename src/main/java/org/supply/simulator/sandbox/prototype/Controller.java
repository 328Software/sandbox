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
            display.render(input);

            // Good Engine sends what entities to display
            //display.receiveEntities(GoodEngine.sendEntities(display.sendView()));

            // Calculation changes to display
           // display.render();


        }

        // Destroy OpenGL (Display)
        display.destroyOpenGL();
    }


}

