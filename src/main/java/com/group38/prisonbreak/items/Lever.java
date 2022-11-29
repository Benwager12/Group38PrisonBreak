package com.group38.prisonbreak.items;

import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.utilities.Item;

/**
 * Implements a Lever
 * @author Daniel Banks (2107922)
 */
public class Lever extends Item {

    // Colour that the tile is on
    private final int colour;

    /**
     * Creates an instance of a Lever
     * @param metadata Colour of tile (int)
     */
    public Lever(String metadata) {
        colour = Integer.parseInt(metadata);
    }

    @Override
    public void interact(boolean isPlayer) {
        if (isPlayer) {
            GameManager.openLever(colour);
        }
    }

    @Override
    public String getImagePath() {
        return "images/items/lever.png";
    }

}
