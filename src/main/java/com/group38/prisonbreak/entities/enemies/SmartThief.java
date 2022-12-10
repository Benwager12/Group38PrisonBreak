package com.group38.prisonbreak.entities.enemies;

import com.group38.prisonbreak.Constants;
import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.utilities.Enemy;
import com.group38.prisonbreak.utilities.pathfinding.DanielsPathFinding;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Random;

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

    public int[] getRandomMove() {
        ArrayList<Integer> triedDirections = new ArrayList<>();
        int[] newPosition = new int[] { super.getX(), super.getY()};

        // Loop until it finds a position it can move to
        while (newPosition[0] == super.getX() && newPosition[1] == super.getY()) {

            // Gets a random direction id
            Random random = new Random();
            int randomUpperBound = Constants.LEFT_ID + 1;
            int randomDirection = random.nextInt(randomUpperBound);

            // If it hasn't already tried the direction then get a position
            if (!triedDirections.contains(randomDirection)) {
                newPosition = GameManager.getLevel().moveTo(super.getX(), super.getY(), randomDirection);
                triedDirections.add(randomDirection);
            }
        }
        return newPosition;
    }

    /**
     * updates and sets the new position of the Smart Thief
     * @param position int array [X, Y] of the new position
     */
    public void updatePosition(int[] position) {
        super.setX(position[0]);
        super.setY(position[1]);
    }

    /**
     * Checks if the item it's heading towards still exists
     * @return boolean if item exists
     */
   private boolean itemExist() {
        if (positionsToItem.size() == 0) return false;
        int itemXPosition = positionsToItem.get(positionsToItem.size() - 1)[0];
        int itemYPosition = positionsToItem.get(positionsToItem.size() - 1)[1];
        return GameManager.getLevel().getTile(itemXPosition, itemYPosition).getItem() != null;
   }

    @Override
    public void move() {
        // If there's positions left to move to then change to the first position in the list of moves
        if (positionsToItem.size() > 0 && itemExist()) {
            if (GameManager.getLevel().wontCollide(this, positionsToItem.get(0)[0], positionsToItem.get(0)[1])) {
                updatePosition(positionsToItem.get(0));
                positionsToItem.remove(0);
            }
        } else {
            positionsToItem.clear();
            // Gets the shortest path to an item and adds the list of moves/positions to the Arraylist
            ArrayList<int[]> path = searchFinding.searchForItems(super.getX(), super.getY());

            if (path.size() == 0) {

                int[] newPosition = getRandomMove();

                if (GameManager.getLevel().wontCollide(this, newPosition[0], newPosition[1])) {
                    updatePosition(newPosition);
                }
            } else {
                positionsToItem.addAll(path);
                move();
            }
        }

        itemInteract();
        CheckCollision();
    }

    @Override
    public Image getEntityImage() {
        return super.getEntityImage();
    }
}
