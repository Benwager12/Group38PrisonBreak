/**
 *  A class that implements an Item
 * @author Daniel Banks (2107922)
 */

package com.group38.prisonbreak.utilities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Item implements Drawable {

    /** Defines what happens when a player interacts with an Item. */
    public void interact() {
        interact(false);
    }

    public abstract void interact(boolean isPlayer);

    /** Get the path of the image for the item. */
    public abstract String getImagePath();

    @Override
    public void draw(GraphicsContext g) {
        Image img = FileUtilities.loadImageFromResource(getImagePath());

        // Needs to be changed when we implement the drawing of tiles
        g.drawImage(img, 0, 0);
    }
}

