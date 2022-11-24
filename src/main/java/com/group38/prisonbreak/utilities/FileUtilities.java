

package com.group38.prisonbreak.utilities;

import com.group38.prisonbreak.Game;
import com.group38.prisonbreak.enemies.FloorThief;
import com.group38.prisonbreak.enemies.FlyingAssassin;
import com.group38.prisonbreak.enemies.SmartThief;
import com.group38.prisonbreak.items.*;
import javafx.scene.image.Image;


import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

/**
 * Generalised utilities for files.
 *
 * @author Daniel Banks (2107922), Ben Wager (2108500), Matthew Salter (986488)
 */
public class FileUtilities {
    private static Game gameInstance;

   /* public static void main(String[] args) {
        testReadFile("C:\\Users\\danie\\OneDrive - Swansea University\\CS-230\\Code\\src\\main\\resources\\com\\group38\\prisonbreak\\testFiles\\Test.txt");
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

    public static void readLevel(String levelName) {
        String levelPath = getResourceURI(String.format("level/%s.level", levelName));

        if (levelPath == null) {
            System.out.println("Level does not exist.");
            System.exit(-1);
        }

        File file = new File(levelPath);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("Can't find the level file.");
            System.exit(-1);
        }

        // This is the level data
        readInfo(scanner);

    }


    public static URL getResource(String path) {
        return gameInstance.getClass().getResource(path);
    }

    public static String getResourceURI(String path) {
        URL resource = getResource(path);

        try {
            return resource.toURI().toString();
        } catch (URISyntaxException e) {
            return null;
        }
    }

    public static Image loadImageFromResource(String resourcePath) {
        String path = getResourceURI(resourcePath);
        if (path == null) {
            return null;
        }
        return new Image(path);
    }

    private static void readInfo(Scanner in) {
        int levelWidth = in.nextInt();
        int levelHeight = in.nextInt();

        Tile[][] tiles = readTiles(in, levelWidth, levelHeight);
        int[] playerLocation = readPlayerLocation(in);
        int numOfItems = in.nextInt();
        Item[] items = readItems(in, numOfItems);
        int numOfEnemies = in.nextInt();
        Enemy[] enemies = readEnemies(in, numOfEnemies);
        in.close();
    }

    private static Tile[][] readTiles(Scanner in, int width, int height) {
        Tile[][] tiles = new Tile[height][width];
        System.out.println(width + " " + height);

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

    private static Item[] readItems(Scanner in, int numOfItems) {
        Item[] items = new Item[numOfItems];

        for(int i = 0; i < numOfItems; i++) {
            String itemType = in.next();

            int itemXPos = in.nextInt();
            int itemYPos = in.nextInt();

            String metadata = in.next();

            Item nextItem = switch (itemType) {
                case "M" -> new Loot(itemXPos, itemYPos, metadata);
                case "C" -> new Clock(itemXPos, itemYPos, metadata);
                case "G" -> new Gate(itemXPos, itemYPos, metadata);
                case "L" -> new Lever(itemXPos, itemYPos, metadata);
                case "B" -> new Bomb(itemXPos, itemYPos, metadata);
                case "D" -> new Door(itemXPos, itemYPos, metadata);
                default -> null;
            };
            items[i] = nextItem;
        }
        return items;
    }

    private static Enemy[] readEnemies(Scanner in, int numOfEnemies) {
        Enemy[] enemies = new Enemy[numOfEnemies];

        for(int i = 0; i < numOfEnemies; i++) {
            String itemType = in.next();

            int enemyXPos = in.nextInt();
            int enemyYPos = in.nextInt();
            int direction = in.nextInt();

            Enemy nextEnemy = switch (itemType) {
                case "H" -> new FlyingAssassin(enemyXPos, enemyYPos, direction);
                case "F" -> new FloorThief(enemyXPos, enemyYPos, direction);
                case "S" -> new SmartThief(enemyXPos, enemyYPos, direction);
                default -> null;
            };
            enemies[i] = nextEnemy;
        }
        return enemies;
    }
}
