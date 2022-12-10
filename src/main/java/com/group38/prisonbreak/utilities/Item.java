/**
 *  A class that implements an Item
 * @author Daniel Banks (2107922)
 */

package com.group38.prisonbreak.utilities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 *
 * @author Daniel Banks (2107922)
 */
public abstract class Item implements Drawable {

    protected static final String gameImagesStart = "images/GameImages/";
    protected int imageIndex;

    /** Defines what happens when a player interacts with an Item. */
    public boolean interact() {
        return interact(false);
    }

    /**
     * Called when an entity interacts with an item
     * Defines how an item is intractable
     * @param isPlayer if the entity interacting with the item is the player
     * @return
     */
    public abstract boolean interact(boolean isPlayer);

    /** Get the path of the image for the item. */
    public abstract String getImagePath();

    /**
     * Gets the image of the item
     * @return Image of the item
     */
    public abstract Image getImage();

    @Override
    public void draw(GraphicsContext g) {
        // Needs to be changed when we implement the drawing of tiles
        g.drawImage(getImage(), 0, 0);
    }
}

