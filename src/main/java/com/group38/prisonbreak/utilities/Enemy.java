package com.group38.prisonbreak.utilities;

import com.group38.prisonbreak.entities.enemies.FlyingAssassin;
import com.group38.prisonbreak.items.Door;

/**
 * Implements an Enemy in the game.
 * @author Daniel Banks (2107922)
 */
public class Enemy extends Entity {

    /**
     * Creates an instance of an Enemy.
     * @param xPos X position
     * @param yPos Y position
     * @param direction Direction the entity will move/face
     */
    public Enemy(int xPos, int yPos, int direction) {
        super(xPos, yPos, direction);
    }

    /**
     * if the enemy isn't a flyingAssassin; it calls item interaction.
     * (Happens everytime an entity moves)
     */
    @Override
    public void move() {
        if (!(this instanceof FlyingAssassin)) {
            itemInteract();
        }
    }

    /**
     * Handles interaction between Enemy's and items.
     * Removes an item if it's not an instance of a door.
     */
    @Override
    protected void itemInteract() {
        Item item = getCurrentTile().getItem();
        if (item != null) {
            if (item.interact(false) && !(item instanceof Door)) {
                getCurrentTile().removeItem();
            }
        }
    }
}
