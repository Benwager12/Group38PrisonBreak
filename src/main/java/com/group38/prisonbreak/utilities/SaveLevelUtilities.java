package com.group38.prisonbreak.utilities;

import com.group38.prisonbreak.Constants;
import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.Level;
import com.group38.prisonbreak.enemies.FloorThief;
import com.group38.prisonbreak.enemies.SmartThief;
import com.group38.prisonbreak.items.*;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Utility that saves a current level to a .level file
 *
 * @author Daniel Banks (2107922)
 */
public class SaveLevelUtilities {

    // Hash map of Colors to Ints (Reverse of Hashmap in Tiles)
    private static final HashMap<Color, Integer> colourMap = new HashMap<>() {{
        put(Color.rgb(253, 101, 105, .25), 0); // Red
        put(Color.rgb(107, 255, 109, .25), 1); // Green
        put(Color.rgb(104, 104, 252, .25), 2); // Blue
        put(Color.rgb(255, 245, 138, .25), 3); // Yellow
        put(Color.rgb(41, 255, 254, .25), 4);  // Cyan
        put(Color.rgb(253, 5, 253, .25), 5);  // Magenta
    }};

    // File name formula for the levels
    public static final String LEVEL_SAVE_LOCATION = "data/saves/%d_%d.level";

    // New line string
    private static final String NEW_LINE = "\n";

    /* String Format for saving time and score
     * <Time> <Score>
     */
    private static final String TIME_SCORE_STRING_FORMAT = "%d %d%n";

    /* String Format for saving the Height and Width of a level
     * <Height> <Width>
     */
    private static final String LEVEL_SIZE_STRING_FORMAT = "%d %d%n";

    /* String Format for saving Tiles (Tile colours)
     * <ColourID><ColourID><ColourID><ColourID>
     */
    private static final String TILE_STRING_FORMAT = "%d%d%d%d ";

    /* String Format for saving a Player
     * <XPos> <YPos> <(Int) Direction>
     */
    private static final String PLAYER_STRING_FORMAT = "%d %d %d%n";

    /* String Format for saving an item with metadata
     * <itemChar> <XPos> <YPos> <(Int) Metadata>
     */
    private static final String ITEM_STRING_FORMAT_METADATA = "%c %d %d %d%n";

    /* String Format for saving an item with no metadata
     * <itemChar> <XPos> <YPos>
     */
    private static final String ITEM_STRING_FORMAT = "%c %d %d%n";

    // Number of players in the game
    private static final int NO_PLAYERS = 1;

    /* String Format for saving an Entity
     * <entityChar> <XPos> <YPos> <(Char) Direction>
     */
    private static final String ENEMY_STRING_FORMAT = "%c %d %d %c%n";

    /* String Format for saving a Floor Thief
     * <entityChar> <XPos> <YPos> <(Char) Direction> <ColourId>
     */
    private static final String FLOOR_THIEF_STRING = "%c %d %d %c %d%n";

    /**
     * Saves the progress of the current level into a file
     * NOTE: THIS WILL REPLACE ANY SAVE MADE BY THAT PROFILE ON THE SAME LEVEL
     *
     * @param profileId id of the profile that's currently player
     * @param level     instance of the level
     */
    public static void saveLevel(int profileId, Level level) {
        String saveLocation = String.format(LEVEL_SAVE_LOCATION, level.getLevelNumber(), profileId);
        String newSaveLocation = FileUtilities.getResourcePathUnsafe(saveLocation);
        // Creates a file
        File saveFile = new File(newSaveLocation);
        boolean isFileCreated = false;
        try {
            System.out.println(newSaveLocation);
            isFileCreated = saveFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!isFileCreated) {
            boolean success = saveFile.delete();
            assert (success);
            try {
                success = saveFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert (success);
        }

        System.out.println("Saved at " + saveLocation);

        PrintWriter myWriter = null;
        try {
            myWriter = new PrintWriter(saveFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (myWriter != null) {
            saveTiles(myWriter, level);
            savePlayer(myWriter, (Player) level.getPlayer());
            saveItems(myWriter, level);
            saveEntities(myWriter, level);
            saveScoreTime(myWriter);

            myWriter.close();
        }
    }

    /**
     * Writes the time left and score of the level to the file
     *
     * @param myWriter printWriter writing to the file
     */
    private static void saveScoreTime(PrintWriter myWriter) {
        myWriter.write(String.format(TIME_SCORE_STRING_FORMAT, GameManager.time, GameManager.money));
    }

    /**
     * Writes all the tiles colours to the file
     *
     * @param myWriter printWriter writing to the file
     * @param level    instance of level that's being saved
     */
    private static void saveTiles(PrintWriter myWriter, Level level) {
        myWriter.write(String.format(LEVEL_SIZE_STRING_FORMAT, level.getWidth(), level.getHeight()));
        for (int y = 0; y < level.getHeight(); y++) {
            for (int x = 0; x < level.getWidth(); x++) {
                Color[] colours = level.getTile(x, y).getColours();
                myWriter.write(String.format(TILE_STRING_FORMAT,
                        colourMap.get(colours[0]),
                        colourMap.get(colours[1]),
                        colourMap.get(colours[2]),
                        colourMap.get(colours[3])));
            }
            myWriter.write(NEW_LINE);
        }
    }

    /**
     * Writes all the player info to the file
     *
     * @param myWriter printWriter writing to the file
     * @param player   instance of the player that's in the current level
     */
    private static void savePlayer(PrintWriter myWriter, Player player) {
        myWriter.write(String.format(PLAYER_STRING_FORMAT, player.getX(), player.getY(), player.getDirection()));
    }

    /**
     * Writes all the info for all the tiles in the level to the file
     *
     * @param myWriter printWriter writing to the file
     * @param level    instance of the level that's being saved
     */
    private static void saveItems(PrintWriter myWriter, Level level) {
        // Writes number of items
        myWriter.write(level.getNoItems() + NEW_LINE);

        Item item;
        for (int y = 0; y < level.getHeight(); y++) {
            for (int x = 0; x < level.getWidth(); x++) {
                item = level.getTile(x, y).getItem();
                if (item != null) {
                    if (!(item instanceof Gate) && !(item instanceof Bomb)) {
                        char itemChar = item instanceof Loot ? Constants.LOOT_CHAR : item instanceof Clock ?
                                Constants.CLOCK_CHAR : item instanceof Lever ?
                                Constants.LEVER_CHAR : Constants.DOOR_CHAR;
                        switch (itemChar) {
                            case Constants.LOOT_CHAR -> myWriter.write(String.format(ITEM_STRING_FORMAT_METADATA,
                                    Constants.LOOT_CHAR, x, y, ((Loot) item).getLootType()));
                            case Constants.LEVER_CHAR -> myWriter.write(String.format(ITEM_STRING_FORMAT_METADATA,
                                    Constants.LEVER_CHAR, x, y,
                                    ((Lever) item).getItemColour()));
                            default -> myWriter.write(String.format(ITEM_STRING_FORMAT, itemChar, x, y));
                        }
                    } else if (item instanceof Gate) {
                        myWriter.write(String.format(ITEM_STRING_FORMAT_METADATA, Constants.GATE_CHAR, x, y,
                                ((Gate) item).getGateColour()));
                    } else if (((Bomb) item).isExplodable()) {
                        myWriter.write(String.format(ITEM_STRING_FORMAT, Constants.BOMB_CHAR, x, y));
                    }
                }
            }
        }
    }

    /**
     * Writes all the entities' info to the file
     *
     * @param myWriter printWriter writing to the file
     * @param level    instance of the level that's being saved
     */
    private static void saveEntities(PrintWriter myWriter, Level level) {
        ArrayList<Entity> entities = level.getEntities();

        // Writing number of Enemies
        myWriter.write(String.valueOf(entities.size() - NO_PLAYERS));
        myWriter.write(NEW_LINE);

        for (Entity entity : entities) {
            int intDir = entity.getDirection();
            char direction = 0;

            switch (intDir) {
                case 0 -> direction = 'U';
                case 1 -> direction = 'R';
                case 2 -> direction = 'D';
                case 3 -> direction = 'L';
                default -> {
                }
            }

            if (!(entity instanceof Player) && !(entity instanceof FloorThief)) {
                char entityChar = entity instanceof SmartThief ?
                        Constants.SMART_THIEF_CHAR : Constants.FLYING_ASSASSIN_CHAR;
                myWriter.write(String.format(ENEMY_STRING_FORMAT, entityChar, entity.getX(), entity.getY(), direction));
            } else if (entity instanceof FloorThief floorThief) {
                char entityChar = Constants.FLOOR_THIEF_CHAR;
                myWriter.write(String.format(FLOOR_THIEF_STRING,
                        entityChar,
                        floorThief.getX(),
                        floorThief.getY(),
                        direction,
                        floorThief.getChosenColour()));
            }
        }
    }
}
