
package com.group38.prisonbreak.enemies;

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

    private int[] findMove() {
        int[] downPos = GameManager.level.potentialMove(getX(),getY(),2,getChosenColour());
        int[] rightPos = GameManager.level.potentialMove(getX(),getY(),1,getChosenColour());
        int[] leftPos = GameManager.level.potentialMove(getX(),getY(),3,getChosenColour());
        int[] upPos = GameManager.level.potentialMove(getX(),getY(),0,getChosenColour());

        if (getDirection() == 0 && (leftPos[0] != getX() || leftPos[1] != getY())) {
            setDirection(3);
            return leftPos;
        }

        if (getDirection() == 0 && (upPos[0] != getX() || upPos[1] != getY())) {
            return upPos;
        }

        if (getDirection() == 0 && (rightPos[0] != getX() || rightPos[1] != getY())) {
            setDirection(1);
            return rightPos;
        }

        if (getDirection() == 1 && (upPos[0] != getX() || upPos[1] != getY())) {
            setDirection(0);
            return upPos;
        }

        if (getDirection() == 1 && (rightPos[0] != getX() || rightPos[1] != getY())) {
            setDirection(1);
            return rightPos;
        }

        if (getDirection() == 1 && (downPos[0] != getX() || downPos[1] != getY())) {
            setDirection(2);
            return downPos;
        }

        if (getDirection() == 3 && (downPos[0] != getX() || downPos[1] != getY())) {
            setDirection(2);
            return downPos;
        }

        if (getDirection() == 3 && (leftPos[0] != getX() || leftPos[1] != getY())) {
            setDirection(3);
            return leftPos;
        }

        if (getDirection() == 3 && (upPos[0] != getX() || upPos[1] != getY())) {
            setDirection(0);
            return upPos;
        }



        if (getDirection() == 3 && (rightPos[0] != getX() || rightPos[1] != getY())) {
            setDirection(1);
            return rightPos;
        }

        if (getDirection() == 2 && (rightPos[0] != getX() || rightPos[1] != getY())) {
            setDirection(1);
            return rightPos;
        }

        if (getDirection() == 2 && (downPos[0] != getX() || downPos[1] != getY())) {
            return downPos;
        }

        if (getDirection() == 2 && (leftPos[0] != getX() || leftPos[1] != getY())) {
            setDirection(3);
            return leftPos;
        }

        if (getDirection() == 2 && (upPos[0] != getX() || upPos[1] != getY())) {
            setDirection(0);
            return upPos;
        }

        if (getDirection() == 1 && (leftPos[0] != getX() || leftPos[1] != getY())) {
            setDirection(3);
            return leftPos;
        }

        return new int[]{getX(), getY()};
    }
}
