package com.group38.prisonbreak.items;

import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.utilities.Item;

/**
 * A class that represents loot in the game
 * @author Daniel Banks
 */

public class Loot extends Item {

    // Amount of money that's on the tile
    private final int moneyAmount;

    /**
     * Constructor for Loot class
     * @param metadata
     */
    public Loot(String metadata) {
        this.moneyAmount = Integer.parseInt(metadata);
    }

    /**
     * adds money to the game iff the entity is a player
     * @param isPlayer Is the entity a player
     */
    @Override
    public boolean interact(boolean isPlayer) {
        if (isPlayer) {
            GameManager.collectMoney(moneyAmount);
        }
        return true;
    }

    @Override
    public String getImagePath() {
        return "images/items/loot.png";
    }

}
