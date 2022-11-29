package com.group38.prisonbreak.enemies;

import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.utilities.Enemy;

/**
 * Implements an Enemy in the game
 * @author Daniel Banks (2107922)
 */
public class FlyingAssassin extends Enemy {

    public FlyingAssassin(int xPos, int yPos, int direction) {
        super(xPos, yPos, direction);
    }

    @Override
    public void move() {
        int direction = super.getDirection();
        // Checks if direction is Up/Down (X)
        boolean isX = direction == 1 || direction == 3;

        // Checks if direction is negative (Up/Left)
        boolean isNegative = direction == 0 || direction == 3;

        // X Position of the next Tile (Based on Direction)
        int newX = isX ?  super.getX() + (isNegative ? -1 : 1) : super.getY();

        // Y Position of the next Tile (Based on Direction)
        int newY = !isX ? super.getY() + (isNegative ? -1 : 1) : super.getY();

        if (GameManager.level.canMove(newX, newY, direction, false)) {
            // sets the new X and Y positions
            super.setX(newX);
            super.setY(newY);
        } else {
            // Switches direction
            int newDir;
            if (isX) {
                newDir = direction == 1 ? 3 : 1;
                super.setDirection(newDir);
            } else {
                newDir = direction == 0 ? 2 : 0;
                super.setDirection(newDir);
            }
            move();
        }
    }
}
