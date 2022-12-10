package com.group38.prisonbreak.utilities;

import javafx.scene.canvas.GraphicsContext;

/**
 * An interface that everything that is drawable onto the canvas will implement.
 * @author Ben Wager (2108500)
 */

public interface Drawable {

    /**
     * Draw the item.
     * @param g The graphics context
     */
    void draw(GraphicsContext g);
}
