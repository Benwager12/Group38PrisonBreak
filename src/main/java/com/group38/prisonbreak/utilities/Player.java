package com.group38.prisonbreak.utilities;

import com.group38.prisonbreak.GameManager;

/**
 * Implements a Player in the game
 * @author Daniel Banks (2107922)
 */
public class Player extends Entity {

    private static final String IMAGE_NAME = "Ben";

    /**
     * Creates and instance of player
     * @param xPos X position of the player
     * @param yPos Y position of the player
     * @param direction starting direction of the player
     */
    public Player(int xPos, int yPos, int direction) {
        super(xPos, yPos, direction);
        setEntityImage(IMAGE_NAME);
    }

    @Override
    public void move() {
        int[] newPos = GameManager.level.moveTo(super.getX(), super.getY(), super.getDirection());
        super.setX(newPos[0]);
        super.setY(newPos[1]);
        itemInteract();
    }

    @Override
    protected void itemInteract() {
        Item item = getCurrentTile().getItem();
        if (item != null) {
            if (item.interact(true)) {
                getCurrentTile().removeItem();
            }
        }
    }
}
