
package com.group38.prisonbreak.entities.enemies;

import com.group38.prisonbreak.Constants;
import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.utilities.Enemy;

/**
 * Implements an FloorThief Enemy type in the game.
 * @author ISSA (853846), Ben Wager (2108500)
 */
public class FloorThief extends Enemy {

    /** Represents the color ID that FloorThief follows. */
    private final int chosenColour;

    /**
     * The initialization of a floor thief.
     *
     * @param xPos The initial X coordinate of the enemy.
     * @param yPos The initial Y coordinate of the enemy.
     * @param direction The initial direction of the enemy.
     * @param chosenColour The chosen colour for the floor thief to floor.
     */
    public FloorThief(int xPos, int yPos, int direction, int chosenColour) {
        super(xPos, yPos, direction);
        setEntityImage("thief_floor");
        this.chosenColour = chosenColour;
    }

    /**
     * Get the chosen colour of the floor thief.
     *
     * @return An integer representing the chosen colour.
     */
    public int getChosenColour() {
        return chosenColour;
    }

    /**
     * Move to the next tile using it's path finding algorithm.
     */
    @Override
    public void move() {
        int[] potentialMoveTo = findMove();

        setX(potentialMoveTo[0]);
        setY(potentialMoveTo[1]);

        itemInteract();
        // Checks if it's collided with flying assassin
        CheckCollision();
    }

    /** Check if a position is equal to the current position.
     *
     * @param xAndY The inputted modified X and Y.
     * @return Returns true if the input is the same as the position
     */
    private boolean notAtPosition(final int[] xAndY) {
        return xAndY[0] != getX() || xAndY[1] != getY();
    }

    /**
     * Finds the Floor thief's next move.
     *
     * @return An integer array containing the X and Y of the floor thief's next move.
     */
    private int[] findMove() {
        int[] downPos = GameManager.getLevel().potentialMove(
                getX(), getY(),
                Constants.DOWN_ID,
                getChosenColour()
        );
        int[] rightPos = GameManager.getLevel().potentialMove(getX(), getY(),
                Constants.RIGHT_ID,
                getChosenColour()

        );
        int[] leftPos = GameManager.getLevel().potentialMove(
                getX(), getY(),
                Constants.LEFT_ID,
                getChosenColour()
        );
        int[] upPos = GameManager.getLevel().potentialMove(
                getX(),
                getY(),
                Constants.UP_ID, getChosenColour()
        );

        if (getDirection() == Constants.UP_ID) {
            if (notAtPosition(leftPos)) {
                setDirection(Constants.LEFT_ID);
                return leftPos;
            }

            if (notAtPosition(upPos)) {
                return upPos;
            }

            if (notAtPosition(rightPos)) {
                setDirection(Constants.RIGHT_ID);
                return rightPos;
            }

            if (notAtPosition(downPos)) {
                setDirection(Constants.DOWN_ID);
                return downPos;
            }
        }

        if (getDirection() == Constants.RIGHT_ID) {
            if (notAtPosition(upPos)) {
                setDirection(Constants.UP_ID);
                return upPos;
            }

            if (notAtPosition(rightPos)) {
                return rightPos;
            }

            if (notAtPosition(downPos)) {
                setDirection(Constants.DOWN_ID);
                return downPos;
            }

            if (notAtPosition(leftPos)) {
                setDirection(Constants.LEFT_ID);
                return leftPos;
            }
        }

        if (getDirection() == Constants.DOWN_ID) {
            if (notAtPosition(rightPos)) {
                setDirection(Constants.RIGHT_ID);
                return rightPos;
            }

            if (notAtPosition(downPos)) {
                return downPos;
            }

            if (notAtPosition(leftPos)) {
                setDirection(Constants.LEFT_ID);
                return leftPos;
            }

            if (notAtPosition(upPos)) {
                setDirection(Constants.UP_ID);
                return upPos;
            }
        }

        if (getDirection() == Constants.LEFT_ID) {
            if (notAtPosition(downPos)) {
                setDirection(Constants.DOWN_ID);
                return downPos;
            }

            if (notAtPosition(leftPos)) {
                return leftPos;
            }

            if (notAtPosition(upPos)) {
                setDirection(Constants.UP_ID);
                return upPos;
            }

            if (notAtPosition(rightPos)) {
                setDirection(Constants.RIGHT_ID);
                return rightPos;
            }
        }

        return new int[]{getX(), getY()};
    }
}
