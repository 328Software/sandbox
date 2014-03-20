package org.badassgame.sandbox.display.view;

import org.badassgame.sandbox.display.view.representation.Representation;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 3/9/14
 * Time: 1:01 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Viewable<R> {
    void addToView(View<R> view);
    Representation<R> getRepresentation();
}
