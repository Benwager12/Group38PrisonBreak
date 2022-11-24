

package com.group38.prisonbreak.utilities;

import com.group38.prisonbreak.Game;
import com.group38.prisonbreak.items.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

/**
 * Generalised utilities for files.
 *
 * @author Daniel Banks (2107922), Ben Wager (2108500)
 */
public class FileUtilities {
    private static Game gameInstance;

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

    private static void readInfo(Scanner in) {
        int levelWidth = in.nextInt();
        int levelHeight = in.nextInt();

        Tile[][] tiles = readTiles(in, levelWidth, levelHeight);
        int[] playerLocation = readPlayerLocation(in);
        int numOfItems = in.nextInt();
        Item[] items = readItems(in, numOfItems);
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
            Item nextItem;
            switch (itemType) {
                case "M":
                    nextItem = new Loot();
                    break;
                case "C":
                    nextItem = new Clock();
                    break;
                case "G":
                    nextItem = new Gate();
                    break;
                case "L":
                    nextItem = new Lever();
                    break;
                case "B":
                    nextItem = new Bomb();
                    break;
                case "D":
                    nextItem = new Door();
                    break;
            }
        }

        return null;
    }
}
