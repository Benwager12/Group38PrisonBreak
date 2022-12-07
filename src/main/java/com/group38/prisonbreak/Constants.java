package com.group38.prisonbreak;

import javafx.scene.paint.Color;

import java.util.HashMap;

/**
 * Static Class that stores char/string constants
 * @author Daniel Banks (2107922)
 */
public class Constants {
    /**
     * Character that represents a Bomb in .Level Files
     */
    public static final char BOMB_CHAR = 'B';

    /**
     * Character that represents a Clock in .Level Files
     */
    public static final char CLOCK_CHAR = 'C';

    /**
     * Character that represents Loot in .Level Files
     */
    public static final char LOOT_CHAR = 'M';

    /**
     * Character that represents a Lever in .Level Files
     */
    public static final char LEVER_CHAR = 'L';

    /**
     * Character that represents a Gate in .Level Files
     */
    public static final char GATE_CHAR = 'G';

    /**
     * Character that represents a Door in .Level Files
     */
    public static final char DOOR_CHAR = 'D';

    /**
     * Character that represents a SmartThief in .Level Files
     */
    public static final char SMART_THIEF_CHAR = 'S';

    /**
     * Character that represents a FloorThief in .Level Files
     */
    public static final char FLOOR_THIEF_CHAR = 'F';

    /**
     * Character that represents a FlyingAssassin in .Level Files
     */
    public static final char FLYING_ASSASSIN_CHAR = 'H';

    /**
     * Map of ints to colors used in tiles
     */
    public static final HashMap<Integer, Color> COLOUR_MAP = new HashMap<>() {{
        put(0, Color.rgb(253, 101, 105, .25)); // Red
        put(1, Color.rgb(107, 255, 109, .25)); // Green
        put(2, Color.rgb(104, 104, 252, .25)); // Blue
        put(3, Color.rgb(255, 245, 138, .25)); // Yellow
        put(4, Color.rgb(41, 255, 254, .25));  // Cyan
        put(5, Color.rgb(253, 5, 253, .25));  // Magenta
    }};
}