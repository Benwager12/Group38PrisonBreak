

package com.group38.prisonbreak.utilities;

import com.group38.prisonbreak.Game;
import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.Level;
import com.group38.prisonbreak.MenuTester;
import com.group38.prisonbreak.enemies.FloorThief;
import com.group38.prisonbreak.enemies.FlyingAssassin;
import com.group38.prisonbreak.enemies.SmartThief;
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
 * Generalised utilities for files.
 *
 * @author Daniel Banks (2107922), Ben Wager (2108500), Matthew Salter (986488)
 */
public class FileUtilities {
    private static Game gameInstance;

    private static final int MAX_LEVEL_TIME = 300;

    //ADDED FOR MENU TESTER
    private static MenuTester menuInstance;

   /* public static void main(String[] args) {
        testReadFile("C:\\Users\\danie\\OneDrive - Swansea University\\CS-230\\Code\\src\\main\\resources\\com\\group38\\prisonbreak\\testFiles\\1.level");
    }

    public static void testReadFile(String fileName) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            readInfo(scanner);
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occured");
            e.printStackTrace();
        }
    } */

    public static void setGameInstance(Game gameInstance) {
        FileUtilities.gameInstance = gameInstance;
    }

    //ADDED FOR MENU TESTER
    public static void setMenuInstance(MenuTester menuInstance) {
        FileUtilities.menuInstance = menuInstance;
    }

    public static Level readLevel(String levelName) {
        String levelPath = getResourcePath(String.format("levels/%s.level", levelName));

        if (System.getProperty("os.name").equals("Mac OS X")) {
            levelPath = "/" + levelPath;
        }
        File file = new File(levelPath);

        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("Can't find the level file.");
            e.printStackTrace();
            System.exit(-1);
        }

        // This is the level data
        return readInfo(scanner);
    }


    public static URL getResource(String path) {
        return gameInstance.getClass().getResource(path);
    }

    //ADDED FOR MENU TESTER
    public static URL getMenuResource(String path) {
        return menuInstance.getClass().getResource(path);
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

    public static String getResourcePath(String path) {
        String uri = getResourceURI(path);
        assert uri != null;
        return uri.substring(6).replaceAll("%20", " ");
    }

    public static Image loadImageFromResource(String resourcePath) {
        String path = getResourceURI(resourcePath);
        if (path == null) {
            return null;
        }
        return new Image(path);
    }

    private static Level readInfo(Scanner in) {
        int levelWidth = in.nextInt();
        int levelHeight = in.nextInt();

        Tile[][] tiles = readTiles(in, levelWidth, levelHeight);
        int[] playerLocation = readPlayerLocation(in);
        Player p = new Player(playerLocation[0], playerLocation[1], playerLocation[2]);

        int numOfItems = in.nextInt();
        HashMap<int[], Item> items = readItems(in, numOfItems);

        for (int[] itemPosition : items.keySet()) {
            Tile currentTile = tiles[itemPosition[1]][itemPosition[0]];
            currentTile.setItem(items.get(itemPosition));
            tiles[itemPosition[1]][itemPosition[0]] = currentTile;
        }
        int numOfEnemies = in.nextInt();
        ArrayList<Entity> enemies = readEnemies(in, numOfEnemies);
        enemies.add(p);
        GameManager.addTime(readLevelTime(in));

        in.close();

        return new Level(0, tiles, enemies);
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
            String itemType = in.next();

            int[] itemPosition = new int[]{in.nextInt(), in.nextInt()};

            switch (itemType) {
                case "M" -> itemMap.put(itemPosition, new Loot(in.next()));
                case "C" -> itemMap.put(itemPosition, new Clock());
                case "G" -> itemMap.put(itemPosition, new Gate(in.next()));
                case "L" -> itemMap.put(itemPosition, new Lever(in.next()));
                case "B" -> itemMap.put(itemPosition, new Bomb());
                case "D" -> itemMap.put(itemPosition, new Door());
            }
        }
        return itemMap;
    }

    private static int convertDirection(String directionString) {
        return switch (directionString) {
            case "L" -> 3;
            case "R" -> 1;
            case "U" -> 0;
            case "D" -> 2;
            default -> 0;
        };
    }

    private static ArrayList<Entity> readEnemies(Scanner in, int numOfEnemies) {
        ArrayList<Entity> enemies = new ArrayList<>();
        Enemy nextEnemy = null;

        for(int i = 0; i < numOfEnemies; i++) {
            String enemyType = in.next();
            int enemyXPos;
            int enemyYPos;
            int direction;
            int chosenColour;

            switch (enemyType) {
                case "H" -> {
                    enemyXPos = in.nextInt();
                    enemyYPos = in.nextInt();
                    direction = convertDirection(in.next());
                    nextEnemy = new FlyingAssassin(enemyXPos, enemyYPos, direction);
                }
                case "F" -> {
                    enemyXPos = in.nextInt();
                    enemyYPos = in.nextInt();
                    direction = convertDirection(in.next());
                    chosenColour = in.nextInt();
                    nextEnemy = new FloorThief(enemyXPos, enemyYPos, direction, chosenColour);
                }
                case "S" -> {
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
}
