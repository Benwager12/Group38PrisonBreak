package com.group38.prisonbreak.enemies;

import com.group38.prisonbreak.utilities.Enemy;
import com.group38.prisonbreak.utilities.pathfinding.DanielsPathFinding;
import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * Implements a Smart Thief in the game
 * @author Daniel Banks (2107922)
 */
public class SmartThief extends Enemy {

    // Class to find and search for items on the level
    private final DanielsPathFinding searchFinding = new DanielsPathFinding();

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

    @Override
    public void move() {

        // If there's positions left to move to then change to the first position in the list of moves
        if (positionsToItem.size() > 0) {
            super.setX(positionsToItem.get(0)[0]);
            super.setY(positionsToItem.get(0)[1]);
            positionsToItem.remove(0);
        } else {
            // Gets the shortest path to an item and adds the list of moves/positions to the Arraylist
            ArrayList<int[]> path = searchFinding.searchForItems(super.getX(), super.getY());
            if (path.size() == 0) System.out.println("/");
            positionsToItem.addAll(path);
        }
        itemInteract();

    }

    @Override
    public Image getEntityImage() {
        return super.getEntityImage();
    }
}
