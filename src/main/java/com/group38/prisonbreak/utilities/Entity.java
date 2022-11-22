package com.group38.prisonbreak.utilities;

import com.group38.prisonbreak.GameManager;

/**
 * The abstract class for the entity.
 * @author Ben Wager (2108500)
 */
public abstract class Entity {

    private int tileX;
    private int tileY;
    private int direction;

    public int getDirection() {
        return direction;
    }

    public int getTileX() {
        return tileX;
    }

    public int getTileY() {
        return tileY;
    }

    public void onItemInteraction(Item it) {
        it.interact();
        GameManager.tiles[tileY][tileX].removeItem();;
    }
}
