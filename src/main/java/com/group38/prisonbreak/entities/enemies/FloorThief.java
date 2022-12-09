
package com.group38.prisonbreak.entities.enemies;

import com.group38.prisonbreak.Constants;
import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.utilities.Enemy;

/**
 * Implements an FloorThief Enemy type in the game
 * @author ISSA (853846), Ben Wager (2108500)
 */
public class FloorThief extends Enemy {

    /** Represents the color ID that FloorThief follows */
    private final int chosenColour;

    public FloorThief(int xPos, int yPos, int direction, int chosenColour) {
        super(xPos, yPos, direction);
        setEntityImage("thief_floor");
        this.chosenColour = chosenColour;
        //this.chosenColourRgb[0] = colourMap.get(chosenColour);
    }

    public int getChosenColour() {
        return chosenColour;
    }

    @Override
    public void move() {
        int[] potentialMoveTo = findMove();

        setX(potentialMoveTo[0]);
        setY(potentialMoveTo[1]);
    }

    private boolean notAtPositiion(int[] xAndY) {
        return xAndY[0] != getX() || xAndY[1] != getY();
    }

    private int[] findMove() {
        int[] downPos = GameManager.level.potentialMove(getX(),getY(),Constants.DOWN_ID,getChosenColour());
        int[] rightPos = GameManager.level.potentialMove(getX(),getY(),Constants.RIGHT_ID,getChosenColour());
        int[] leftPos = GameManager.level.potentialMove(getX(),getY(),Constants.LEFT_ID,getChosenColour());
        int[] upPos = GameManager.level.potentialMove(getX(),getY(),Constants.UP_ID,getChosenColour());

        if (getDirection() == Constants.UP_ID) {
            if (notAtPositiion(leftPos)) {
                setDirection(Constants.LEFT_ID);
                return leftPos;
            }

            if (notAtPositiion(upPos)) {
                return upPos;
            }

            if (notAtPositiion(rightPos)) {
                setDirection(Constants.RIGHT_ID);
                return rightPos;
            }

            if (notAtPositiion(downPos)) {
                setDirection(Constants.DOWN_ID);
                return downPos;
            }
        }

        if (getDirection() == Constants.RIGHT_ID) {
            if (notAtPositiion(upPos)) {
                setDirection(Constants.UP_ID);
                return upPos;
            }

            if (notAtPositiion(rightPos)) {
                return rightPos;
            }

            if (notAtPositiion(downPos)) {
                setDirection(Constants.DOWN_ID);
                return downPos;
            }

            if (notAtPositiion(leftPos)) {
                setDirection(Constants.LEFT_ID);
                return leftPos;
            }
        }

        if (getDirection() == Constants.DOWN_ID) {
            if (notAtPositiion(rightPos)) {
                setDirection(Constants.RIGHT_ID);
                return rightPos;
            }

            if (notAtPositiion(downPos)) {
                return downPos;
            }

            if (notAtPositiion(leftPos)) {
                setDirection(Constants.LEFT_ID);
                return leftPos;
            }

            if (notAtPositiion(upPos)) {
                setDirection(Constants.UP_ID);
                return upPos;
            }
        }

        if (getDirection() == Constants.LEFT_ID) {
            if (notAtPositiion(downPos)) {
                setDirection(Constants.DOWN_ID);
                return downPos;
            }

            if (notAtPositiion(leftPos)) {
                return leftPos;
            }

            if (notAtPositiion(upPos)) {
                setDirection(Constants.UP_ID);
                return upPos;
            }

            if (notAtPositiion(rightPos)) {
                setDirection(Constants.RIGHT_ID);
                return rightPos;
            }
        }

        return new int[]{getX(), getY()};
    }
}
