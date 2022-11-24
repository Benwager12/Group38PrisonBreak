/**
 *  A class that implements an Item
 * @author Daniel Banks (2107922)
 */

package com.group38.prisonbreak.utilities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Item implements Drawable {

    /** Defines what happens when a player interacts with an Item. */
    public abstract void interact();

    /** Get the path of the image for the item. */
    public abstract String getImagePath();

    @Override
    public void draw(GraphicsContext g) {
        Image img = FileUtilities.loadImageFromResource(getImagePath());

        // Needs to be changed when we implement the drawing of tiles
        g.drawImage(img, 0, 0);
    }
}

