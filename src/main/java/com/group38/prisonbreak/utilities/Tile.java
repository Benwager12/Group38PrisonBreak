package com.group38.prisonbreak.utilities;

import com.group38.prisonbreak.Constants;
import javafx.scene.paint.Color;

/**
 * A Class that implements a tile.
 * @author Daniel Banks (2107922), Ben Wager (2108500)
 */

public class Tile {
    /** colourIds that make up the tile. */
    private final int[] colourIDs;

    /** Array of colours that make up a tile. */
    private final Color[] colours = new Color[4];

    /** the Item that's on the tile. */
    private Item item;
    /**
     * creates a tile instance with set colours.
     * @param colours array of ints (0-5) that represent the colours
     */
    public Tile(int[] colours) {
        this.colourIDs = colours;
        for (int i = 0; i < colours.length; i++) {
            this.colours[i] = Constants.COLOUR_MAP.get(colours[i]);
        }
    }

    /**
     * Gets all the colours of the tile.
     * @return Color[]
     */
    public Color[] getColours() {
        return colours;
    }

    /**
     * Checks if any of the colours in a given array is on the tile.
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
     * Checks if the tile has matching colourIds.
     * @param checkColours int[] of size 4, with colourIds to check
     * @return Boolean if one colour matches
     */
    public boolean hasColours(int[] checkColours) {
        for (int id1 : colourIDs) {
            for (int id2 : checkColours) {
                if (id1 == id2) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Sets the item on the tile.
     * @param item The item to set
     */
    public void setItem(Item item) {
        this.item = item;
    }

    /** Removes the item from the tile. */
    public void removeItem() {
        item = null;
    }

    /**
     * gets the item on the tile.
     * @return item
     */
    public Item getItem() {
        return item;
    }

    /**
     * Gets the all the colourIds that make up the tile.
     * @return int[] of size 4 with the colour Ids
     */
    public int[] getColourIDs() {
        return colourIDs;
    }

    /**
     * Checks if the tile has a specific given colour.
     * @param checkColour colourId of the tile
     * @return boolean, if it has the colour
     */
    public boolean hasColour(int checkColour) {
        for (int id : colourIDs) {
            if (id == checkColour) {
                return true;
            }
        }
        return false;
    }
}
