package com.group38.prisonbreak.entities.enemies;

import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.utilities.Enemy;
import javafx.scene.image.Image;

/**
 * Implements an Enemy in the game
 * @author Daniel Banks (2107922)
 */
public class FlyingAssassin extends Enemy {

    public FlyingAssassin(int xPos, int yPos, int direction) {
        super(xPos, yPos, direction);
        setEntityImage("assassin_flying");
    }

    @Override
    public void move() {
        boolean canMove = false;
        while (!canMove) {
            int direction = super.getDirection();

            boolean isX = direction == 1 || direction == 3;
            boolean isNegative = direction == 0 || direction == 3;

            // X Position of the next Tile (Based on Direction)
            int newX = isX ?  super.getX() + (isNegative ? -1 : 1) : super.getX();

            // Y Position of the next Tile (Based on Direction)
            int newY = !isX ? super.getY() + (isNegative ? -1 : 1) : super.getY();

            if (GameManager.level.canMove(newX, newY, direction, false)) {
                // sets the new X and Y positions
                super.setX(newX);
                super.setY(newY);
                canMove = true;
            } else {
                // Switches direction
                int newDir = isX ? (direction == 1 ? 3 : 1) : (direction == 0 ? 2 : 0);
                super.setDirection(newDir);
            }
        }
    }

    @Override
    public Image getEntityImage() {
        return super.getEntityImage();
    }
}
