package com.group38.prisonbreak.utilities;

import com.group38.prisonbreak.enemies.FlyingAssassin;

public class Enemy extends Entity {

    public Enemy(int xPos, int yPos, int direction) {
        super(xPos, yPos, direction);
    }

    @Override
    public void move() {
        if (!(this instanceof FlyingAssassin)) {
            itemInteract();
        }
    }

    @Override
    protected void itemInteract() {
        Item item = getCurrentTile().getItem();
        if (item != null) {
            if (item.interact(false)) {
                getCurrentTile().removeItem();
            }
        }
    }
}
