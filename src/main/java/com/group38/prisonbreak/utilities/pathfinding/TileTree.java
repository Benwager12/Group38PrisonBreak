package com.group38.prisonbreak.utilities.pathfinding;

import com.group38.prisonbreak.Constants;
import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.items.Bomb;
import com.group38.prisonbreak.items.Door;
import com.group38.prisonbreak.utilities.Item;
import com.group38.prisonbreak.utilities.Tile;

import java.util.ArrayList;

/**
 *
 * @author Daniel Banks
 */
public class TileTree {

    // NOTE THIS IS WIP AND DOESN'T ACTUALLY WORK YET
    // I HAVEN'T REMOVED ANY REDUNDANCIES YET (I will when it works :))

    // TODO: Remove TileNode as I don't this it's needed

    /* Since I don't think with my new implementation it's ever used
     * It was being used when I created a tree then searched through it, but now I'm searching for the shortest path
     * while going through all the possible moves?
    */


    // Int to set when a path isn't found
    private static final int NOT_PATH_ERROR_INT = -1;

    // String to show node is equal to null
    // private static final String NULL_NODE = "/";
    private static final String NULL_NODE = "";

    // char to show node is equal to null
   private static final char NULL_NODE_CHAR = '/'; //NULL_NODE.charAt(0);

    // Starting TileNode / root of the Tree
    private TileNode startingTileNode;

    // String of direction ids to show path to the item
    private String pathToItem;

    // boolean to show items have been found
    // TODO: Maybe remove this and change when this is called to a value to level?
    private boolean itemsFound = false;

    // ArrayList of previously visited positions
    private ArrayList<int[]> visitedPositions;

    /**
     * Creates an instance of tree
     */
    public TileTree() {
    }

    /**
     * Sets itemsFound to true
     */
    public void itemsFound() {
        itemsFound = true;
    }

    /**
     * Sets the root to the tree
     * @param startingTile TileNode
     */
    public void setStartingTile(TileNode startingTile) {
        this.startingTileNode = startingTile;
    }

    /**
     * Searches all possible paths and finds the shortest path to an item
     * @param xPos X position of the start point
     * @param yPos Y position of the start point
     */
    public String searchPaths(int xPos, int yPos) {
        startingTileNode = createTileNode(new int[] {xPos, yPos});
        visitedPositions = new ArrayList<>();
        String path = searchPathsRecursive(startingTileNode, "");

        // TODO: Do something with path
        System.out.println("Shortest Path: " + path);

        return path;
    }

    private String searchPathsRecursive(TileNode root, String path) {
        if (root.hasItem()) {
            System.out.println(path);
            return path;
        }

        String upPath = "";
        String downPath = "";
        String leftPath = "";
        String rightPath = "";

        int rootXPos = root.getXPos();
        int rootYPos = root.getYPos();

        int[] newPositionUp = GameManager.level.moveTo(rootXPos, rootYPos, Constants.UP_ID);
        int[] newPositionDown = GameManager.level.moveTo(rootXPos, rootYPos, Constants.DOWN_ID);
        int[] newPositionRight = GameManager.level.moveTo(rootXPos, rootYPos, Constants.RIGHT_ID);
        int[] newPositionLeft = GameManager.level.moveTo(rootXPos, rootYPos, Constants.LEFT_ID);

        if (newPositionUp[1] != rootYPos && notPreviouslyVisited(newPositionUp)) {
            TileNode upNode = createTileNode(newPositionUp);
            root.setUpTile(upNode);

            visitedPositions.add(newPositionUp);

            upPath = searchPathsRecursive(upNode, path + Constants.UP_ID);
        }

        if (newPositionDown[1] != rootYPos && notPreviouslyVisited(newPositionDown)) {
            TileNode downNode = createTileNode(newPositionDown);
            root.setDownTile(downNode);

            visitedPositions.add(newPositionDown);

            downPath = searchPathsRecursive(downNode, path + Constants.DOWN_ID);
        }

        if (newPositionLeft[0] != rootXPos && notPreviouslyVisited(newPositionLeft)) {
            TileNode leftNode = createTileNode(newPositionLeft);
            root.setLeftTile(leftNode);

            visitedPositions.add(newPositionLeft);

            leftPath = searchPathsRecursive(leftNode, path + Constants.LEFT_ID);
        }

        if (newPositionRight[0] != rootXPos && notPreviouslyVisited(newPositionRight)) {
            TileNode rightNode = createTileNode(newPositionRight);
            root.setRightTile(rightNode);

            visitedPositions.add(newPositionDown);

            rightPath = searchPathsRecursive(rightNode, path + Constants.RIGHT_ID);
        }

        return findSmallestPath(upPath, rightPath, downPath, leftPath);
    }

    /**
     * Creates a tileNode to be added to the tree
     * @param position int array with X and Y position of the tile
     * @return tileNode
     */
    private TileNode createTileNode(int[] position) {
        Tile tile = GameManager.level.getTile(position[0], position[1]);
        Item item = tile.getItem();
        return new TileNode(position[0], position[1],
                item != null && !(item instanceof Bomb) &&
                        (!(item instanceof Door) || (itemsFound && item instanceof Door)));
    }

    /**
     * Finds the shortest path from the 4 given directions
     * Will take into account null strings if the path isn't valid
     * @param upString Up direction String
     * @param rightString Right direction String
     * @param downString Down direction String
     * @param leftString Left direction String
     * @return String of the shortest Path
     */
    private String findSmallestPath(String upString, String rightString, String downString, String leftString) {
        if (upString.length() > 0
                && upString.length() < rightString.length() && upString.length() < downString.length() &&
                upString.length() < leftString.length()) {
            return upString;
        } else if (rightString.length() > 0
                && rightString.length() < upString.length() && rightString.length() < downString.length() &&
                rightString.length() < leftString.length()) {
            return rightString;
        } else if (downString.length() > 0 &&
                downString.length() < upString.length() && downString.length() < rightString.length() &&
                downString.length() < leftString.length()) {
            return downString;
        } else if (leftString.length() > 0) {
            return leftString;
        }
        return NULL_NODE;
    }

    /**
     * Checks if a position has been previously visited
     * @param position position to check
     * @return boolean if visited already
     */
    private boolean notPreviouslyVisited(int[] position) {
        for (int[] previousPosition : visitedPositions) {
            if (previousPosition[0] == position[0] && previousPosition[1] == position[1]) {
                return false;
            }
        }
        return true;
    }

    /*
    ******************** NOT USED ANY MORE DUE TO EFFICIENCY (NO CLUE IF IT DID WORK) ********************
     */
    public int[][] searchShortestRoute() {
        String path = binarySearchTree(startingTileNode, "");
        if (path.charAt(path.length() - 1) != NULL_NODE_CHAR) {
            pathToItem = path;
            // TODO: Convert String of direction into X, Y Positions
        }
        System.out.println(pathToItem);
        return new int[][] {{NOT_PATH_ERROR_INT, NOT_PATH_ERROR_INT}};
    }

    private String binarySearchTree(TileNode node, String path) {
        if (node == null) {
            return path + NULL_NODE;
        } else if (node.hasItem()) {
            return path;
        }

        String upPath = binarySearchTree(node.getUpTile(), path + Constants.UP_ID);
        if (upPath.charAt(upPath.length() - 1) == NULL_NODE_CHAR) {
            return upPath;
        }
        String rightPath = binarySearchTree(node.getRightTile(), path + Constants.RIGHT_ID);
        if (rightPath.charAt(rightPath.length() - 1) == NULL_NODE_CHAR) {
            return rightPath;
        }

        String downPath = binarySearchTree(node.getDownTile(), path + Constants.DOWN_ID);
        if (downPath.charAt(downPath.length() - 1) == NULL_NODE_CHAR) {
            return downPath;
        }

        String leftPath = binarySearchTree(node.getLeftTile(), path + Constants.RIGHT_ID);
        if (leftPath.charAt(leftPath.length() - 1) == NULL_NODE_CHAR) {
            return leftPath;
        }
        return findSmallestPath(upPath, rightPath, downPath, leftPath);
    }
}
