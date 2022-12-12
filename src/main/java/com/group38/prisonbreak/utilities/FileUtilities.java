

package com.group38.prisonbreak.utilities;

import com.group38.prisonbreak.Constants;
import com.group38.prisonbreak.Game;
import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.Level;
import com.group38.prisonbreak.entities.Player;
import com.group38.prisonbreak.entities.enemies.FloorThief;
import com.group38.prisonbreak.entities.enemies.FlyingAssassin;
import com.group38.prisonbreak.entities.enemies.SmartThief;
import com.group38.prisonbreak.items.Bomb;
import com.group38.prisonbreak.items.Loot;
import com.group38.prisonbreak.items.Clock;
import com.group38.prisonbreak.items.Gate;
import com.group38.prisonbreak.items.Lever;
import com.group38.prisonbreak.items.Door;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * A class containing the utilities used for file processing.
 * This includes the reading a level file and parsing it into a Level.
 * The class also contains key methods to get the paths to resources.
 * @author Daniel Banks (2107922), Ben Wager (2108500), Matthew Salter (986488)
 */
public class FileUtilities {

    /** An instance of the game. */
    private static Game gameInstance;

    /** Maximum time a level can have. */
    private static final int MAX_LEVEL_TIME = 300;

    /** String Format of the plath to a level file. */
    private static final String LEVELS_PATH = "levels/%d.level";

    /** String formatting for when a file couldn't be found. */
    private static final String NO_FILE_ERROR = "Can't find file";

    /**
     * Set the instance of a Game.
     * @param gameInstance The instance of a Game
     */
    public static void setGameInstance(Game gameInstance) {
        FileUtilities.gameInstance = gameInstance;
    }

    /**
     * Returns the current Game.
     * @return The current Game instance
     */
    public static Game getGameInstance() {
        return FileUtilities.gameInstance;
    }


    /**
     * Returns a Level instance using the level name and id of a profile.
     * @param levelNumber The chosen level number
     * @param profileId The ID of the chosen profile
     * @return An instance a Level
     */
    public static Level readLevel(int levelNumber, int profileId) {
        return readLevel(getResourcePathUnsafe(
                String.format(
                        SaveLevelUtilities.LEVEL_SAVE_LOCATION,
                        profileId,
                        levelNumber)
                )
        );
    }

    /**
     * Returns a Level instance using the level name.
     * @param levelNumber The chosen level number
     * @return An instance a Level
     */
    public static Level readLevel(int levelNumber) {
        return readLevel(getResourcePath(
                String.format(LEVELS_PATH, levelNumber))
        );
    }

    /**
     * Returns a Level instance using the path of the level file.
     * @param levelPath The path to the chosen level file
     * @return An instance a Level
     */
    public static Level readLevel(String levelPath) {
        File file = new File(levelPath);

        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println(NO_FILE_ERROR);
            e.printStackTrace();
            System.exit(-1);
        }

        System.out.println(levelPath);
        // This is the level data
        int levelNumber;
        String[] splitBySlash = levelPath.split("/");
        String levelFileName = splitBySlash[splitBySlash.length - 1];

        if (levelPath.contains("saves")) {
            levelNumber =
                    Integer.parseInt(levelFileName.split("_")[0]);
            System.out.println(levelNumber);
        } else {
            String extension = ".level";
            levelNumber =
                    Integer.parseInt(levelFileName.substring(
                            0,
                            levelFileName.length() - extension.length())
                    );
        }
        System.out.println(levelPath);
        return readInfo(scanner, levelNumber);
    }

    /**
     * Utilise the game instance to get a resource.
     * @param path The path of the file.
     * @return A URL representing the file.
     */
    public static URL getResource(String path) {
        return gameInstance.getClass().getResource(path);
    }

    /**
     * Get a resource URI in the form of a string.
     * @param path The path of the file.
     * @return A string representation of the URI of the file.
     */
    public static String getResourceURI(String path) {
        URL resource = getResource(path);
        if (resource == null) {
            return null;
        }
        try {
            return resource.toURI().toString();
        } catch (URISyntaxException e) {
            return null;
        }
    }

    /**
     * An unsafe version of getResourcePath,
     * this is when you are unsure whether the file exists.
     * @param path The path to the unsafe file.
     * @return The string representation of a file URI.
     */
    public static String getResourcePathUnsafe(String path) {
        String firstLevel = "levels/1.level";
        String lvl = getResourcePath(firstLevel);
        return lvl.substring(0, lvl.length() - firstLevel.length()) + path;
    }

    /**
     * Get a resource path that is converted to use in either Mac or Windows.
     * @param path The path of the file.
     * @return A string representation of a file URL.
     */
    public static String getResourcePath(String path) {
        String uri = getResourceURI(path);
        if (System.getProperty("os.name").equals("Mac OS X")) {
            uri = "/" + uri;
        }
        String fileStart = "file:/";
        assert uri != null;
        return uri.substring(fileStart.length()).replaceAll("%20", " ");
    }

    /**
     * Returns an Image based on its file path.
     * @param resourcePath Path to the chosen file
     * @return The chosen Image
     */
    public static Image loadImageFromResource(String resourcePath) {
        String path = getResourceURI(resourcePath);
        if (path == null) {
            return null;
        }
        return new Image(path);
    }

    /**
     * Returns a Level using the contents of a level file that follows the level file format.
     * @param in The scanner of the level file
     * @param levelNumber The chosen level number
     * @return An instance of the chosen Level
     */
    private static Level readInfo(Scanner in, int levelNumber) {
        int levelWidth = in.nextInt();
        int levelHeight = in.nextInt();

        Tile[][] tiles = readTiles(in, levelWidth, levelHeight);
        int[] playerLocation = readPlayerLocation(in);
        Player p = new Player(
                playerLocation[0],
                playerLocation[1],
                playerLocation[2],
                levelNumber
        );

        int numOfItems = in.nextInt();
        HashMap<int[], Item> items = readItems(in, numOfItems);

        // Adding all items to tiles
        for (int[] itemPosition : items.keySet()) {
            Tile currentTile = tiles[itemPosition[1]][itemPosition[0]];
            currentTile.setItem(items.get(itemPosition));
            tiles[itemPosition[1]][itemPosition[0]] = currentTile;
        }

        for (int tileY = 0; tileY < tiles.length; tileY++) {
            for (int tileX = 0; tileX < tiles[tileY].length; tileX++) {
                Tile t = tiles[tileY][tileX];
                //Checks if there is a real (explodable) bomb on the tile.
                if (!(t.getItem() == null)
                        && (t.getItem() instanceof Bomb b)
                        && b.isExplodable()) {
                    if (tileY > 0) {
                        tiles[tileY - 1][tileX].setItem(
                                new Bomb((Bomb) t.getItem())
                        );
                    }

                    if (!(tileY == tiles.length - 1)) {
                        tiles[tileY + 1][tileX].setItem(
                                new Bomb((Bomb) t.getItem())
                        );
                    }

                    if (tileX > 0) {
                        tiles[tileY][tileX - 1].setItem(
                                new Bomb((Bomb) t.getItem())
                        );
                    }

                    if (!(tileX == tiles[tileY].length - 1)) {
                        tiles[tileY][tileX + 1].setItem(
                                new Bomb((Bomb) t.getItem())
                        );
                    }
                }
            }
        }

        int numOfEnemies = in.nextInt();
        ArrayList<Entity> enemies = readEnemies(in, numOfEnemies);
        enemies.add(p);

        GameManager.setTime(readLevelTime(in));

        if (in.hasNextInt()) {
            GameManager.setMoney(readScore(in));
        }

        in.close();

        return new Level(levelNumber, tiles, enemies);
    }

    /**
     * Returns a 2D array of tiles present in a level file.
     * @param in Scanner of the file
     * @param width Width of the level
     * @param height Height of the level
     * @return A 2D array of Tiles
     */
    private static Tile[][] readTiles(Scanner in, int width, int height) {
        Tile[][] tiles = new Tile[height][width];

        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                String[] tileColoursString = in.next().split("");
                int[] tileColours = new int[Constants.NO_COLOURS];

                for (int i = 0; i < tileColoursString.length; i++) {
                    tileColours[i] = Integer.parseInt(tileColoursString[i]);
                }
                tiles[h][w] = new Tile(tileColours);
            }
        }
        return tiles;
    }

    /**
     * Returns the players location in the level as an array.
     * @param in Scanner of the file
     * @return The players location in the level as an array of integers
     */
    private static int[] readPlayerLocation(Scanner in) {
        return new int[]{
                in.nextInt(),
                in.nextInt(),
                in.nextInt()
        };
    }

    /**
     * Returns a hashmap of Items present in a level from the level file.
     * @param in Scanner of the file
     * @param numOfItems Number of Items present in a level
     * @return A hashmap of Items
     */
    private static HashMap<int[], Item> readItems(Scanner in, int numOfItems) {
        HashMap<int[], Item> itemMap = new HashMap<>();

        for (int i = 0; i < numOfItems; i++) {
            char itemType = (in.next()).charAt(0);

            int[] itemPosition = new int[]{in.nextInt(), in.nextInt()};

            switch (itemType) {
                case Constants.LOOT_CHAR ->
                        itemMap.put(itemPosition, new Loot(in.next()));
                case Constants.CLOCK_CHAR ->
                        itemMap.put(itemPosition, new Clock());
                case Constants.GATE_CHAR ->
                        itemMap.put(itemPosition, new Gate(in.next()));
                case Constants.LEVER_CHAR ->
                        itemMap.put(itemPosition, new Lever(in.next()));
                case Constants.BOMB_CHAR ->
                        itemMap.put(itemPosition, new Bomb());
                case Constants.DOOR_CHAR ->
                        itemMap.put(itemPosition, new Door());
                default -> { }
            }
        }
        return itemMap;
    }

    /**
     * Converts the direction string into integer format.
     * @param directionString The direction in string format.
     * @return The directing in integer format.
     */
    private static int convertDirection(String directionString) {
        return switch (directionString) {
            case "L" -> Constants.LEFT_ID;
            case "R" -> Constants.RIGHT_ID;
            case "D" -> Constants.DOWN_ID;
            default -> Constants.UP_ID;
        };
    }

    /**
     * Returns an ArrayList of enemies from a level file.
     * @param in Scanner of the file
     * @param numOfEnemies Number of enemies present in a level
     * @return An Arraylist of enemies
     */
    private static ArrayList<Entity> readEnemies(Scanner in, int numOfEnemies) {
        ArrayList<Entity> enemies = new ArrayList<>();
        Enemy nextEnemy = null;

        for (int i = 0; i < numOfEnemies; i++) {
            char enemyType = (in.next()).charAt(0);
            int enemyXPos;
            int enemyYPos;
            int direction;
            int chosenColour;

            switch (enemyType) {
                case Constants.FLYING_ASSASSIN_CHAR -> {
                    enemyXPos = in.nextInt();
                    enemyYPos = in.nextInt();
                    direction = convertDirection(in.next());
                    nextEnemy =
                            new FlyingAssassin(enemyXPos, enemyYPos, direction);
                }
                case Constants.FLOOR_THIEF_CHAR -> {
                    enemyXPos = in.nextInt();
                    enemyYPos = in.nextInt();
                    direction = convertDirection(in.next());
                    chosenColour = in.nextInt();
                    nextEnemy =
                            new FloorThief(
                                    enemyXPos,
                                    enemyYPos,
                                    direction,
                                    chosenColour
                            );
                }
                case Constants.SMART_THIEF_CHAR -> {
                    enemyXPos = in.nextInt();
                    enemyYPos = in.nextInt();
                    direction = convertDirection(in.next());
                    nextEnemy =
                            new SmartThief(
                                    enemyXPos,
                                    enemyYPos,
                                    direction
                            );
                }
                default -> { }
            }

            enemies.add(nextEnemy);
        }
        return enemies;
    }

    /**
     * Returns the time of a level.
     * @param in Scanner of the file.
     * @return Time remaining for a level.
     */
    private static int readLevelTime(Scanner in) {
        int levelTime = in.nextInt();
        return Math.min(MAX_LEVEL_TIME, levelTime);
    }

    /**
     * Returns the score in the saved level file.
     * @param in Scanner of the file.
     * @return The score saved in the file
     */
    private static int readScore(Scanner in) {
        return in.nextInt();
    }
}
