package com.group38.prisonbreak.items;

import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.utilities.FileUtilities;
import com.group38.prisonbreak.utilities.Item;
import javafx.scene.image.Image;

public class Clock extends Item {

    // Amount of time to be added/deducted to the timer when the clock is picked up
    private static final int CLOCK_AMOUNT = 10;

    private static Image clockImage = null;

    public Clock() {

        if (clockImage == null) {
            clockImage =
                    FileUtilities.loadImageFromResource(
                            GAME_IMAGE_PATH + "clock.png"
                    );
        }
    }

    @Override
    public boolean interact(boolean isPlayer) {
        if (isPlayer) {
            GameManager.addTime(CLOCK_AMOUNT);
        } else {
            GameManager.removeTime(CLOCK_AMOUNT);
        }
        return true;
    }

    @Override
    public String getImagePath() {
        return "images/items/clock.png";
    }

    /**
     * Image that represents a clock
     * @return Returns the clock image used
     */
    @Override
    public Image getImage() {
        return clockImage;
    }
}