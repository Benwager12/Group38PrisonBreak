/**
 *  A class that implements an Item
 * @author Daniel Banks (2107922)
 */

package com.group38.prisonbreak.utilities;

import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.Level;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Implements an Item
 * @author Daniel Banks (2107922)
 */
public abstract class Item implements Drawable {

    /** The path that you're getting the images from. */
    protected static final String GAME_IMAGE_PATH = "images/GameImages/";

    /** The index of the specific image we are displaying. */
    protected int imageIndex;

    /** Defines what happens when a non-player interacts with an Item. */
    public boolean interact() {
        return interact(false);
    }

    /**
     * Called when an entity interacts with an item.
     * Defines how an item is intractable
     * @param isPlayer if the entity interacting with the item is the player
     * @return if the item should be destroyed upon interaction
     */
    public abstract boolean interact(boolean isPlayer);

    /** Get the path of the image for the item.
     * @return file path of the image
     */
    public abstract String getImagePath();

    /**
     * Gets the image of the item.
     * @return Image of the item
     */
    public abstract Image getImage();

    /**
     * Draw the item onto the screen at point 0.
     * @param g The graphics context
     */
    @Override
    public void draw(GraphicsContext g) {
        Level lvl = GameManager.getLevel();
        if (lvl == null) {
            return;
        }
        int sideLength = lvl.getSideLength(g);
        g.drawImage(getImage(), 0, 0, sideLength, sideLength);
    }

    /**
     * Draw the item onto the tile.
     * @param g The graphics context
     * @param tileX X position of the tile
     * @param tileY Y position of the tile
     * @param sideLength Length of the tile
     */
    public void draw(GraphicsContext g, int tileX, int tileY, int sideLength) {
        g.drawImage(
                getImage(),
                tileX * sideLength, tileY * sideLength,
                sideLength, sideLength
        );
    }
}

