package com.group38.prisonbreak.utilities;

import com.group38.prisonbreak.enemies.FlyingAssassin;

/**
 * Implements an Enemy in the game
 * @author Daniel Banks (2107922)
 */
public class Enemy extends Entity {

    /**
     * Creates an instance of an Enemy
     * @param xPos X position
     * @param yPos Y position
     * @param direction Direction the entity will move/face
     */
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
