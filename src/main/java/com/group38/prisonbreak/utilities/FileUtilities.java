

package com.group38.prisonbreak.utilities;

import com.group38.prisonbreak.*;
import com.group38.prisonbreak.entities.enemies.FloorThief;
import com.group38.prisonbreak.entities.enemies.FlyingAssassin;
import com.group38.prisonbreak.entities.enemies.SmartThief;
import com.group38.prisonbreak.entities.Player;
import com.group38.prisonbreak.items.*;
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
    private static Game gameInstance;

    private static final int MAX_LEVEL_TIME = 300;

    private static final String LEVELS_PATH = "levels/%d.level";


    /**
     * Set the instance of a Game
     * @param gameInstance The instance of a Game
     */
    public static void setGameInstance(Game gameInstance) {
        FileUtilities.gameInstance = gameInstance;
    }

    /**
     * Returns the current Game
     * @return The current Game instance
     */
    public static Game getGameInstance() {
        return FileUtilities.gameInstance;
    }


    /**
     * Returns a Level instance using the level name and id of a profile
     * @param levelNumber The chosen level number
     * @param profileId The ID of the chosen profile
     * @return An instance a Level
     */
    public static Level readLevel(int levelNumber, int profileId) {
        return readLevel(getResourcePathUnsafe(
                String.format(SaveLevelUtilities.LEVEL_SAVE_LOCATION, levelNumber, profileId))
        );
    }

    /**
     * Returns a Level instance using the level name
     * @param levelNumber The chosen level number
     * @return An instance a Level
     */
    public static Level readLevel(int levelNumber) {
        return readLevel(getResourcePath(String.format(LEVELS_PATH, levelNumber)));
    }

    /**
     * Returns a Level instance using the path of the level file
     * @param levelPath The path to the chosen level file
     * @return An instance a Level
     */
    public static Level readLevel(String levelPath) {
        File file = new File(levelPath);

        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("Can't find the level file.");
            e.printStackTrace();
            System.exit(-1);
        }

        System.out.println(levelPath);
        // This is the level data
        int levelNumber;
        if (levelPath.contains("saves")) {
            levelNumber = Integer.parseInt(levelPath.split("_")[0]);
        } else {
            String[] splitBySlash = levelPath.split("/");
            String levelFileName = splitBySlash[splitBySlash.length - 1];
            levelNumber = Integer.parseInt(levelFileName.substring(0, levelFileName.length() - 6));
        }
        return readInfo(scanner, levelNumber);
    }


    public static URL getResource(String path) {
        return gameInstance.getClass().getResource(path);
    }


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

    public static String getResourcePathUnsafe(String path) {
        String lvl = getResourcePath("levels/1.level");
        return lvl.substring(0, lvl.length() - 14) + path;
    }

    public static String getResourcePath(String path) {
        String uri = getResourceURI(path);
        if (System.getProperty("os.name").equals("Mac OS X")) {
            uri = "/" + uri;
        }
        assert uri != null;
        return uri.substring(6).replaceAll("%20", " ");
    }

    /**
     * Returns an Image based on its file path
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
     * Returns a Level using the contents of a level file that follows the level file format
     * @param in The scanner of the level file
     * @param levelNumber The chosen level number
     * @return An instance of the chosen Level
     */
    private static Level readInfo(Scanner in, int levelNumber) {
        int levelWidth = in.nextInt();
        int levelHeight = in.nextInt();

        Tile[][] tiles = readTiles(in, levelWidth, levelHeight);
        int[] playerLocation = readPlayerLocation(in);
        Player p = new Player(playerLocation[0], playerLocation[1], playerLocation[2],levelNumber);

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
                if (!(t.getItem() == null) && (t.getItem() instanceof Bomb b) && b.isExplodable()) {
                    if (tileY > 0) {
                        tiles[tileY - 1][tileX].setItem(new Bomb((Bomb) t.getItem()));
                    }

                    if (!(tileY == tiles.length - 1)) {
                        tiles[tileY + 1][tileX].setItem(new Bomb((Bomb) t.getItem()));
                    }

                    if (tileX > 0) {
                        tiles[tileY][tileX - 1].setItem(new Bomb((Bomb) t.getItem()));
                    }

                    if (!(tileX == tiles[tileY].length - 1)) {
                        tiles[tileY][tileX + 1].setItem(new Bomb((Bomb) t.getItem()));
                    }
                }
            }
        }

        int numOfEnemies = in.nextInt();
        ArrayList<Entity> enemies = readEnemies(in, numOfEnemies);
        enemies.add(p);

        enemies.forEach(e -> {
            if (e instanceof FloorThief ft) {
                if (!tiles[e.getY()][e.getX()].hasColours(new int[]{ft.getChosenColour()})) {
                    System.out.printf("A floor thief at (X:%d,Y:%d) has a chosen colour of %d, but"
                            + " that is not its current tile.%n", e.getX(), e.getY(), ft.getChosenColour());
                }
            }
        });

        GameManager.setTime(readLevelTime(in));

        if(in.hasNextInt()) {
            GameManager.setMoney(readScore(in));
        }

        in.close();

        return new Level(levelNumber, tiles, enemies);
    }

    private static Tile[][] readTiles(Scanner in, int width, int height) {
        Tile[][] tiles = new Tile[height][width];

        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                String[] tileColoursString = in.next().split("");
                int[] tileColours = new int[4];

                for (int i = 0; i < tileColoursString.length; i++) {
                    tileColours[i] = Integer.parseInt(tileColoursString[i]);
                }
                tiles[h][w] = new Tile(tileColours);
            }
        }
        return tiles;
    }

    private static int[] readPlayerLocation(Scanner in) {
        return new int[]{
                in.nextInt(),
                in.nextInt(),
                in.nextInt()
        };
    }

    private static HashMap<int[], Item> readItems(Scanner in, int numOfItems) {
        HashMap<int[], Item> itemMap = new HashMap<>();

        for(int i = 0; i < numOfItems; i++) {
            char itemType = (in.next()).charAt(0);

            int[] itemPosition = new int[]{in.nextInt(), in.nextInt()};

            switch (itemType) {
                case Constants.LOOT_CHAR -> itemMap.put(itemPosition, new Loot(in.next()));
                case Constants.CLOCK_CHAR -> itemMap.put(itemPosition, new Clock());
                case Constants.GATE_CHAR -> itemMap.put(itemPosition, new Gate(in.next()));
                case Constants.LEVER_CHAR -> itemMap.put(itemPosition, new Lever(in.next()));
                case Constants.BOMB_CHAR -> itemMap.put(itemPosition, new Bomb());
                case Constants.DOOR_CHAR -> itemMap.put(itemPosition, new Door());
            }
        }
        return itemMap;
    }

    private static int convertDirection(String directionString) {
        return switch (directionString) {
            case "L" -> 3;
            case "R" -> 1;
            case "D" -> 2;
            default -> 0;
        };
    }

    private static ArrayList<Entity> readEnemies(Scanner in, int numOfEnemies) {
        ArrayList<Entity> enemies = new ArrayList<>();
        Enemy nextEnemy = null;

        for(int i = 0; i < numOfEnemies; i++) {
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
                    nextEnemy = new FlyingAssassin(enemyXPos, enemyYPos, direction);
                }
                case Constants.FLOOR_THIEF_CHAR -> {
                    enemyXPos = in.nextInt();
                    enemyYPos = in.nextInt();
                    direction = convertDirection(in.next());
                    chosenColour = in.nextInt();
                    nextEnemy = new FloorThief(enemyXPos, enemyYPos, direction, chosenColour);
                }
                case Constants.SMART_THIEF_CHAR -> {
                    enemyXPos = in.nextInt();
                    enemyYPos = in.nextInt();
                    direction = convertDirection(in.next());
                    nextEnemy = new SmartThief(enemyXPos, enemyYPos, direction);
                }
            }

            enemies.add(nextEnemy);
        }
        return enemies;
    }

    private static int readLevelTime(Scanner in) {
        int levelTime = in.nextInt();

        //Ensures level is not long than max level time.
        if (levelTime > MAX_LEVEL_TIME) {
            levelTime = MAX_LEVEL_TIME;
        }
        return levelTime;
    }

    private static int readScore(Scanner in) {
        return in.nextInt();
    }
}
