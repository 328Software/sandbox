package org.badassgame.sandbox.display.view;

import org.badassgame.sandbox.display.view.representation.Representation;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 2/15/14
 * Time: 9:34 PM
 * To change this template use File | Settings | File Templates.
 */
public interface View<R> extends Viewable<R> {
    void addRepresentation(Representation<R> representation);
}
