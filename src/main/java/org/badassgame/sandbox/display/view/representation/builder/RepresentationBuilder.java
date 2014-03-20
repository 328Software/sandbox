package org.badassgame.sandbox.display.view.representation.builder;

import org.badassgame.sandbox.display.view.Viewable;
import org.badassgame.sandbox.display.view.representation.Representation;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 3/9/14
 * Time: 2:24 PM
 * To change this template use File | Settings | File Templates.
 */
public interface RepresentationBuilder<R> {
    Representation<R> build(Viewable<R> viewable);
}
