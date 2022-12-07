
package com.group38.prisonbreak.enemies;

import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.utilities.Enemy;

/**
 * Implements an FloorThief Enemy type in the game
 * @author ISSA (853846), Ben Wager (2108500)
 */
public class FloorThief extends Enemy {

    /** Represents the color ID that FloorThief follows */
    private int chosenColour;

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
        int[] potentialMoveTo = GameManager.level.moveTo(getX(), getY(), getDirection(), chosenColour);
        int[] currentPosition = new int[]{getX(), getY()};
        while (potentialMoveTo[0] == currentPosition[0] && potentialMoveTo[1] == currentPosition[1]) {
            int toTheLeft = getDirection() + 1;
            if (toTheLeft == 4) {
                toTheLeft = 0;
            }

            potentialMoveTo = GameManager.level.moveTo(getX(), getY(), toTheLeft, chosenColour);
            setDirection(toTheLeft);
        }

        setX(potentialMoveTo[0]);
        setY(potentialMoveTo[1]);
    }
}
