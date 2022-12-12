package com.group38.prisonbreak;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Static Class that stores constants used in multiple classes.
 * @author Daniel Banks (2107922)
 */
public class Constants {
    /** Character that represents a Bomb in .Level Files. */
    public static final char BOMB_CHAR = 'B';

    /** Character that represents a Clock in .Level Files. */
    public static final char CLOCK_CHAR = 'C';

    /** Character that represents Loot in .Level Files. */
    public static final char LOOT_CHAR = 'M';

    /** Character that represents a Lever in .Level Files. */
    public static final char LEVER_CHAR = 'L';

    /** Character that represents a Gate in .Level Files. */
    public static final char GATE_CHAR = 'G';

    /** Character that represents a Door in .Level Files. */
    public static final char DOOR_CHAR = 'D';

    /** Character that represents a SmartThief in .Level Files. */
    public static final char SMART_THIEF_CHAR = 'S';

    /** Character that represents a FloorThief in .Level Files. */
    public static final char FLOOR_THIEF_CHAR = 'F';

    /** Character that represents a FlyingAssassin in .Level Files. */
    public static final char FLYING_ASSASSIN_CHAR = 'H';

    private Constants() { }

    /** Map of ints to colors used in tiles. */
    public static final HashMap<Integer, Color> COLOUR_MAP = new HashMap<>() {{
        put(0, Color.rgb(253, 101, 105, .25)); // Red
        put(1, Color.rgb(107, 255, 109, .25)); // Green
        put(2, Color.rgb(104, 104, 252, .25)); // Blue
        put(3, Color.rgb(255, 245, 138, .25)); // Yellow
        put(4, Color.rgb(41, 255, 254, .25));  // Cyan
        put(5, Color.rgb(253, 5, 253, .25));  // Magenta
    }};

    /** Int that represents up direction. */
    public static final int UP_ID = 0;

    /** Int that represents right direction. */
    public static final int RIGHT_ID = 1;

    /** Int that represents down direction. */
    public static final int DOWN_ID = 2;

    /** Int that represents left direction. */
    public static final int LEFT_ID = 3;

    /** ArrayList of Key Codes that represent 'Up' key bind. */
    public static final ArrayList<KeyCode> UP_KEYS = new ArrayList<>() {{
        add(KeyCode.UP);
        add(KeyCode.W);
    }};

    /** ArrayList of Key Codes that represent 'Right' key bind. */
    public static final ArrayList<KeyCode> RIGHT_KEYS = new ArrayList<>() {{
        add(KeyCode.RIGHT);
        add(KeyCode.D);
    }};

    /** ArrayList of Key Codes that represent 'Down' key bind. */
    public static final ArrayList<KeyCode> DOWN_KEYS = new ArrayList<>() {{
        add(KeyCode.DOWN);
        add(KeyCode.S);
    }};

    /** ArrayList of Key Codes that represent 'Left' key bind. */
    public static final ArrayList<KeyCode> LEFT_KEYS = new ArrayList<>() {{
        add(KeyCode.LEFT);
        add(KeyCode.A);
    }};

    /** ArrayList of Key Codes that represent movement key bind. */
    public static final ArrayList<KeyCode> MOVEMENT_KEYS = new ArrayList<>() {{
        addAll(UP_KEYS);
        addAll(RIGHT_KEYS);
        addAll(DOWN_KEYS);
        addAll(LEFT_KEYS);
    }};

    /**
     * Duration of the Player Timeline (milli Seconds).
     * (How often the player ticks/moves)
     */
    public static final int PLAYER_TIMELINE_DURATION  = 350;

    /**
     * Duration of the Enemy Timeline (milli Seconds).
     * (How often an enemy ticks/moves)
     */
    public static final int ENEMY_TIMELINE_DURATION = 500;

    /**
     * Duration of the Smart Thief Timeline (milli Seconds).
     * (How often a smart Thief ticks/moves)
     */
    public static final int SMART_THIEF_TIMELINE_DURATION = 1250;


    /**
     * Duration of the Clock Timeline (1 Second).
     * (How often the clock decreases)
     */
    public static final int CLOCK_TIMELINE_DURATION = 1000;

    /** Position of where entities go upon being 'Killed'. */
    public static final int KILLED_ENTITY_LOCATION = -1;

    /** Number of colours that make up a tile */
    public static final int NO_COLOURS = 4;
}
