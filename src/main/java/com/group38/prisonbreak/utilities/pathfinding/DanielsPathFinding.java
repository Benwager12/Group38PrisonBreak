package com.group38.prisonbreak.utilities.pathfinding;


import com.group38.prisonbreak.Constants;
import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.items.Bomb;
import com.group38.prisonbreak.items.Door;
import com.group38.prisonbreak.items.Gate;
import com.group38.prisonbreak.utilities.Item;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Implements a path finding algorithm for smart thief.
 * @author Daniel Banks (2107922)
 */
public class DanielsPathFinding {
    /*
     * Uses A* Algorithm
     * Each Node is given a 'weight'
     * (Distance from item + Distance from start point)
     * Nodes with the lowest weight are traversed first until the item is found
     */


    // Stores position of items
    private final ArrayList<int[]> itemPositions = new ArrayList<>();

    // Stores all the visited nodes
    private final ArrayList<TileNode> visitedNodes = new ArrayList<>();

    /* Stores all the found nodes and is ordered by each TileNode's weight
     * (How close it is to the item)
     */
    private final ArrayList<TileNode> nodesFound = new ArrayList<>();


    /**
     * Gets all the items that it has to collect.
     */
    public void getAllItems() {
        // Resets the array of items
        itemPositions.clear();
        int levelWidth = GameManager.getLevel().getWidth();
        int levelHeight = GameManager.getLevel().getHeight();

        for (int x = 0; x < levelWidth; x++) {
            for (int y = 0; y < levelHeight; y++) {
                Item item = GameManager.getLevel().getTile(x, y).getItem();

                /* Will look for items that aren't null, isn't a gate,
                 * isn't a bomb and a door if the game has no items left
                 */
                if (item != null && !(item instanceof Gate)
                        && !(item instanceof Bomb)
                        && (!(item instanceof Door)
                        || !GameManager.getLevel().hasItemsLeft())) {
                    itemPositions.add(new int[] {x, y});
                }
            }
        }
    }

    /**
     * Calculates the distance between a point and an item.
     * @param x X position of a point
     * @param y Y position of a point
     * @param itemX X position of the item
     * @param itemY Y position of the item
     * @return (Double) The distance between the points
     */
    private double calculateDistance(int x, int y, int itemX, int itemY) {
        return Math.sqrt(Math.pow(x - itemX, 2.0) + Math.pow(y - itemY, 2.0));
    }

    /**
     * Marks a node as found.
     * @param newNode node to be marked as found
     */
    private void markAsFound(TileNode newNode) {
        for (int i = 0; i < nodesFound.size(); i++) {
            // Inserts it in the correct place so nodesFound is ordered
            if (nodesFound.get(i).getTotalWeight() > newNode.getTotalWeight()) {
               nodesFound.add(i, newNode);
               return;
            }
        }
        nodesFound.add(newNode);
    }

    /**
     * Checks if a node hasn't been visited.
     * @param node TileNode to be checked
     * @return If it's been visited
     */
    private boolean hasNotBeenVisited(TileNode node) {
        // Checks for a node with the same X and Y position
        for (TileNode visitedNode : visitedNodes) {
            if (visitedNode.getXPos() == node.getXPos()
                    && visitedNode.getYPos() == node.getYPos()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Marks a mode as visited.
     * @param newNode Node to be marked
     */
    private void markAsVisited(TileNode newNode) {
        visitedNodes.add(newNode);
        nodesFound.remove(newNode);
    }

    /**
     * Marks a node to nodes found (Meaning it's been found but not visited).
     * @param newNode Node to be marked
     */
    private void addNode(TileNode newNode) {
        /* Checks if a node has been found already,
         * if so is its total weight less than it
         */
        for (TileNode node : nodesFound) {
            if (node.getXPos() == newNode.getXPos()
                    && node.getYPos() == newNode.getYPos()) {
                if (node.getTotalWeight() > newNode.getTotalWeight()) {
                    nodesFound.remove(node);
                    markAsFound(newNode);
                } else {
                    return;
                }
            }
        }
        markAsFound(newNode);
    }

    /**
     * Searches for all the items and then finds the shortest path to the closet one.
     * @param startX Start X point of the search
     * @param startY Start Y point of the search
     * @return ArrayList of positions int [X, Y]
     */
    public ArrayList<int[]> searchForItems(int startX, int startY) {
        ArrayList<ArrayList<int[]>> pathToItems = new ArrayList<>();
        getAllItems();

        // Finds path to each item using A* Algorithm
        for (int[] itemPosition : itemPositions) {
            pathToItems.add(search(startX, startY, itemPosition[0],
                    itemPosition[1]));
        }

        // Finds which path is shorter
        if (pathToItems.size() != 0) {
            ArrayList<int[]> shortestPath = pathToItems.get(0);
            for (ArrayList<int[]> paths : pathToItems) {
                if ((paths.size() > 0 && paths.size() < shortestPath.size())
                        || shortestPath.size() == 0) {
                    shortestPath = paths;
                }
            }
            return shortestPath;
        }
        // Return empty List if no paths are found
        return new ArrayList<>();
    }

    /**
     * Searches for a path between two points (Player Position and Item Position).
     * @param startX Start X point of the search
     * @param startY Start Y point of the search
     * @param itemXPos X Position of the item
     * @param itemYPos Y Position of the item
     * @return Arraylist of position int[X, Y]
     */
    public ArrayList<int[]> search(int startX, int startY,
                                   int itemXPos, int itemYPos)  {
        visitedNodes.clear();
        nodesFound.clear();

        ArrayList<int[]> foundRoute = new ArrayList<>();

        // Creates start/root Node
        addNode(new TileNode(startX, startY, null, 0,
                calculateDistance(startX, startY, itemXPos, itemYPos))
        );

        // Finds and gets the foundNode with the item on it
        TileNode foundNode = searchForItem(itemXPos, itemYPos, 1);

        // Finds the route in reverse to get to the found node
        while (foundNode != null) {
            foundRoute.add(new int[]{foundNode.getXPos(), foundNode.getYPos()});
            foundNode = foundNode.getPreviousNode();
        }
        // Returns the route in the correct way (Unreserved)
        Collections.reverse(foundRoute);
        return foundRoute;
    }

    /**
     * Searches for an item/item position.
     * @param itemXPos X Position of the item
     * @param itemYPos Y Position of the item
     * @param distance Distance from the start point
     * @return TileNode with the item
     */
    public TileNode searchForItem(int itemXPos, int itemYPos, int distance) {
        // Return if no nodes found (meaning there's no path)
        if (nodesFound.size() == 0) {
            return null;
        }

        // Gets the node that has the smallest "Weight"
        TileNode currentNode = nodesFound.get(0);
        markAsVisited(currentNode);

        // If position of the node is equal to item; returns item
        if (currentNode.getXPos() == itemXPos
                && currentNode.getYPos() == itemYPos) {
            return currentNode;
        }

        int rootXPos = currentNode.getXPos();
        int rootYPos = currentNode.getYPos();

        // Gets all possible positions/moves
        int[] newPositionUp = GameManager.getLevel()
                .moveTo(rootXPos, rootYPos, Constants.UP_ID);
        int[] newPositionDown = GameManager.getLevel()
                .moveTo(rootXPos, rootYPos, Constants.DOWN_ID);
        int[] newPositionRight = GameManager.getLevel()
                .moveTo(rootXPos, rootYPos, Constants.RIGHT_ID);
        int[] newPositionLeft = GameManager.getLevel()
                .moveTo(rootXPos, rootYPos, Constants.LEFT_ID);

        // Creates an instance of TileNode for that direction
        TileNode upNode =
                new TileNode(newPositionUp[0], newPositionUp[1],
                        currentNode, distance,
                        calculateDistance(newPositionUp[0],
                        newPositionUp[1],
                        itemXPos, itemYPos));
        // Checks if it hasn't been visited or that it won't set off the bomb
        if (newPositionUp[1] != rootYPos && hasNotBeenVisited(upNode)
                && GameManager.getLevel()
                .wontSetOffBomb(newPositionUp[0], newPositionUp[1])) {
            addNode(upNode);
        }

        TileNode downNode =
                new TileNode(newPositionDown[0], newPositionDown[1],
                        currentNode, distance,
                        calculateDistance(newPositionDown[0], newPositionDown[1],
                        itemXPos, itemYPos));
        if (newPositionDown[1] != rootYPos && hasNotBeenVisited(downNode)
                && GameManager.getLevel()
                .wontSetOffBomb(newPositionDown[0], newPositionDown[1])) {
            addNode(downNode);
        }

        TileNode rightNode =
                new TileNode(newPositionRight[0], newPositionRight[1],
                        currentNode, distance,
                        calculateDistance(newPositionRight[0], newPositionRight[1],
                        itemXPos, itemYPos));
        if (newPositionRight[0] != rootXPos && hasNotBeenVisited(rightNode)
                && GameManager.getLevel()
                .wontSetOffBomb(newPositionRight[0], newPositionRight[1])) {
           addNode(rightNode);
        }

        TileNode leftNode =
                new TileNode(newPositionLeft[0], newPositionLeft[1],
                        currentNode, distance,
                        calculateDistance(newPositionLeft[0], newPositionLeft[1],
                        itemXPos, itemYPos));
        if (newPositionLeft[0] != rootXPos && hasNotBeenVisited(leftNode)
                && GameManager.getLevel()
                .wontSetOffBomb(newPositionLeft[0], newPositionLeft[1])) {
            addNode(leftNode);
        }
        // Recursively calls function again, incrementing distance (from start node)
        return searchForItem(itemXPos, itemYPos, ++distance);
    }
}
