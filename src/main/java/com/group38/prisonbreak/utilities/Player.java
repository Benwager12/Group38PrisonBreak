package com.group38.prisonbreak.utilities;

import com.group38.prisonbreak.GameManager;

/**
 * Implements a Player in the game
 * @author Daniel Banks (2107922)
 */
public class Player extends Entity {

    /**
     * Creates and instance of player
     * @param xPos X position of the player
     * @param yPos Y position of the player
     * @param direction starting direction of the player
     */
    public Player(int xPos, int yPos, int direction) {
        super(xPos, yPos, direction);
        setEntityImage("playerDog");
        System.out.printf("%d, %d%n", xPos, yPos);
    }

    @Override
    public void move() {
        boolean canMove = false;
            int direction = super.getDirection();

            boolean isX = direction == 1 || direction == 3;
            boolean isNegative = direction == 0 || direction == 3;

            // X Position of the next Tile (Based on Direction)
            int newX = isX ?  super.getX() + (isNegative ? -1 : 1) : super.getX();

            // Y Position of the next Tile (Based on Direction)
            int newY = !isX ? super.getY() + (isNegative ? -1 : 1) : super.getY();

            if (GameManager.level.canMove(newX, newY, direction, true)) {
                // sets the new X and Y positions
                super.setX(newX);
                super.setY(newY);
        }
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
