package com.group38.prisonbreak.items;

import com.group38.prisonbreak.utilities.Item;

public class Gate extends Item {

    private boolean isOpen = false;

    public Gate(String metadata) {
        //Implement Constructor
    }

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
