package com.group38.prisonbreak.items;

import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.utilities.Item;

public class Door extends Item {

    public Door() {
        // TODO: Implement Constructor
    }

    @Override
    public boolean interact(boolean isPlayer) {
        if (GameManager.level.hasItemsLeft()) {
            GameManager.endGame();
            return true;
        }
        return false;
    }

    @Override
    public String getImagePath() {
        return "images/items/door.png";
    }

}
