
package com.group38.prisonbreak.enemies;

import com.group38.prisonbreak.utilities.Enemy;

import javafx.scene.paint.Color;

import java.util.HashMap;
/**
 * Implements an FloorThief Enemy type in the game
 * @author ISSA (853846)
 */
public class FloorThief extends Enemy {

    //created rgb colours
    private static final HashMap<Integer, Color> colourMap = new HashMap<>() {{
        put(0, Color.rgb(253, 101, 105, .25)); // Red
        put(1, Color.rgb(107, 255, 109, .25)); // Green
        put(2, Color.rgb(104, 104, 252, .25)); // Blue
        put(3, Color.rgb(255, 245, 138, .25)); // Yellow
        put(4, Color.rgb(41, 255, 254, .25));  // Cyan
        put(5, Color.rgb(253, 5, 253, .25));  // Magenta
    }};

    /** Represents the color in rgb format, FloorThief follows.
     */
    private int colour;

    /** Represents the coordinates returned by moveTo function.
     */
    int[] cord;
    public FloorThief(int xPos, int yPos, int direction, int chosenColour ) {
        super(xPos, yPos, direction);
        setEntityImage("thief_floor");
        //this.chosenColourRgb[0] = colourMap.get(chosenColour);
    }

    public int getColour() {
        return colour;
    }
}
