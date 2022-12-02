package com.group38.prisonbreak.utilities;

import javafx.scene.paint.Color;

import java.util.HashMap;

/**
 * A Class that implements a tile
 * @author Daniel Banks (2107922), Ben Wager (2108500)
 */

public class Tile {

    // map of ints to Javafx Colors
    private static final HashMap<Integer, Color> colourMap = new HashMap<>() {{
        put(0, Color.rgb(253, 101, 105, .25)); // Red
        put(1, Color.rgb(107, 255, 109, .25)); // Green
        put(2, Color.rgb(104, 104, 252, .25)); // Blue
        put(3, Color.rgb(255, 245, 138, .25)); // Yellow
        put(4, Color.rgb(41, 255, 254, .25));  // Cyan
        put(5, Color.rgb(253, 5, 253, .25));  // Magenta
    }};

    // colours that make up the tile
    private final Color[] colours = new Color[4];

    // the Item that's on the tile
    private Item item;
    /**
     * creates a tile instance with set colours
     * @param colours array of ints (0-5) that represent the colours
     */
    public Tile(int[] colours) {
        for (int i = 0; i < 4; i++) {
            this.colours[i] = colourMap.get(colours[i]);
        }
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
        for (Color c1 : colours) {
            for (Color c2 : checkColours) {
                if (c1.equals(c2)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Sets the item on the tile
     * @param item The item to set
     */
    public void setItem(Item item) {
        this.item = item;
    }

    /** Removes the item from the tile */
    public void removeItem() {
        item = null;
    }

    /**
     * gets the item on the tile
     * @return item
     */
    public Item getItem() {
        return item;
    }
}
