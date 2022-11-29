package com.group38.prisonbreak.items;

import com.group38.prisonbreak.utilities.Item;

/**
 * Implements a Gate in the game
 * @author Daniel Banks (2107922)
 */
public class Gate extends Item {

    // If the level of the corresponding colour has been collected
    private boolean isOpen = false;

    public Gate(String metadata) {
        //Implement Constructor
    }

    /**
     * "unlocks" the gate
     */
    public void openGate() {
        isOpen = true;
    }

    @Override
    public boolean interact(boolean isPlayer) {
        return isOpen;
    }

    public boolean isUnlocked() {
        return isOpen;
    }

    @Override
    public String getImagePath() {
        return "images/items/gate.png";
    }

}
