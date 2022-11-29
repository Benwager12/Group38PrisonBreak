package com.group38.prisonbreak.items;

import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.utilities.Item;

/**
 * Implements a Door  in the game
 * @author Daniel Banks (2107922)
 */
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
