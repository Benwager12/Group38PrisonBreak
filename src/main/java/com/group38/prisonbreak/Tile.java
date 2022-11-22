/**
 * A Class that implements a tile
 * @aurhor Daniel Banks, Ben Wager
 */

package com.group38.prisonbreak;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.HashMap;

public class Tile implements Drawable {

    private static final HashMap<Integer, Color> colourMap = new HashMap<>() {{
        put(0, Color.rgb(253, 101, 105));
        put(1, Color.rgb(107, 255, 109));
        put(2, Color.rgb(104, 104, 252));
        put(3, Color.rgb(255, 245, 138));
        put(4, Color.rgb(41, 255, 254));
        put(5, Color.rgb(253, 5, 253));
    }};

    // all the colours on the tile
    private final Color[] colours = new Color[4];

    public Tile(int[] colours) {
        for (int i = 0; i < 4; i++) {
            this.colours[i] = colourMap.get(colours[i]);
        }
    }

    @Override
    public void draw(GraphicsContext g) {

    }

    /**
     * Gets all the colours of the tile
     * @return Color[]
     */
    public Color[] getColours() {
        return colours;
    }

    /**
     * Checks if any of the colours in a given array is on the tile
     * @param checkColours array of Colors
     * @return boolean
     */
    public boolean hasColours(Color[] checkColours) {
        for (Color checkColour : checkColours) {
            if (Arrays.asList(colours).contains(checkColour)) {
                return true;
            }
        }
        return false;
    }


}
