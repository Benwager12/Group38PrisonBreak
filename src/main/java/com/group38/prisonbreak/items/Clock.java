package com.group38.prisonbreak.items;

import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.utilities.FileUtilities;
import com.group38.prisonbreak.utilities.Item;
import javafx.scene.image.Image;

public class Clock extends Item {

    /**
     * Amount of time to be added/deducted to/from the timer when
     * the clock is picked up.
     */
    private static final int CLOCK_AMOUNT = 10;

    /** The clock image for the item. */
    private static Image clockImage;

    /**
     * Initialize the clock and set the clock image.
     */
    public Clock() {
        if (clockImage == null) {
            clockImage = FileUtilities.loadImageFromResource(
                            GAME_IMAGE_PATH + "clock.png"
                        );
        }
    }

    /**
     * Called when an entity interacts with an item.
     *
     * @param isPlayer If the entity interacting with the item is the player
     * @return Always returns true.
     */
    @Override
    public boolean interact(boolean isPlayer) {
        if (isPlayer) {
            GameManager.addTime(CLOCK_AMOUNT);
        } else {
            GameManager.removeTime(CLOCK_AMOUNT);
        }
        return true;
    }

    /**
     * Gets the image path for the clock.
     * @return The image path for the clock as a string.
     */
    @Override
    public String getImagePath() {
        return "images/items/clock.png";
    }

    /**
     * Image that represents a clock.
     * @return Returns the clock image used.
     */
    @Override
    public Image getImage() {
        return clockImage;
    }
}
