package com.group38.prisonbreak.items;

import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.utilities.Item;

public class Clock extends Item {

    // Amount of time to be added/deducted to the timer when the clock is picked up
    private static final int CLOCK_AMOUNT = 10;

    public Clock() {
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
}
