package com.group38.prisonbreak.items;

import com.group38.prisonbreak.utilities.Item;

public class Door extends Item {

    public Door() {
        //Implement Constructor
    }

    @Override
    public boolean interact(boolean isPlayer) {
        // TODO
        return true;
    }

    @Override
    public String getImagePath() {
        return "images/items/door.png";
    }

}
