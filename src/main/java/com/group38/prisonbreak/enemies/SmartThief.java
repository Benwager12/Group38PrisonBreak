package com.group38.prisonbreak.enemies;

import com.group38.prisonbreak.Constants;
import com.group38.prisonbreak.utilities.Enemy;
import com.group38.prisonbreak.utilities.pathfinding.TileTree;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

/**
 * Implements a Smart Thief in the game
 * @author Daniel Banks (2107922)
 */
public class SmartThief extends Enemy {

    // Class to find and search for items on the level
    private final TileTree tileTree = new TileTree();

    // ArrayList of moves to get to the closest item
    private final ArrayList<int[]> positionsToItem = new ArrayList<>();

    /**
     * Creates an instance of Smart Thief
     * @param xPos Starting X position
     * @param yPos Starting Y position
     * @param direction Starting direction
     */
    public SmartThief(int xPos, int yPos, int direction) {
        super(xPos, yPos, direction);
        setEntityImage("Guard 1");
    }

    /**
     * Calculates a path from a given string of directionIds
     * @param path String of directionIds
     */
    private void calculatePath(String path) {
        int posX = super.getX();
        int posY = super.getY();

        for (char direction : path.toCharArray()) {
            int directionId = Character.getNumericValue(direction);

            // Checks if direction is Up/Down (X)
            boolean isX = directionId == Constants.LEFT_ID || directionId == Constants.RIGHT_ID;
            // Checks if direcltion is negative (Up/Left)
            boolean isNegative = directionId == Constants.UP_ID || directionId == Constants.LEFT_ID;

            // X Position of the next Tile (Based on Direction)
            int newX = isX ? posX + (isNegative ? -1 : 1) : posX;
            // Y Position of the next Tile (Based on Direction)
            int newY = !isX ? posY + (isNegative ? -1 : 1) : posY;

            positionsToItem.add(new int[] {newX, newY});


            posX = newX;
            posY = newY;
        }
    }

    @Override
    public void move() {
        /*
        if (positionsToItem.size() > 0) {
            super.setX(positionsToItem.get(0)[0]);
            super.setY(positionsToItem.get(0)[1]);
            positionsToItem.remove(0);
        } else {
            String path = tileTree.searchPaths(super.getX(), super.getY());
            calculatePath(path);
        }

         */
    }

    @Override
    public Image getEntityImage() {
        return super.getEntityImage();
    }
}
