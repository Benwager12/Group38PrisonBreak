package com.group38.prisonbreak.utilities.pathfinding;

/**
 * Implements a Node for the Tile Tree.
 * @author Daniel Banks (2107922)
 */
public class TileNode {

    /** Parent Node. */
    private final TileNode previousNode;

    /** X Position of the tile. */
    private final int xPos;

    /** Y Position of the tile. */
    private final int yPos;

    /** Distance from the root. */
    private final int distanceFromRoot;

    /** Distance to the item. */
    private final double distanceToItem;

    /**
     * Creates an instance of TileNode to represent the Tile as a "node".
     * @param x X Position of the tile
     * @param y Y Position of the tile
     * @param previousNode Parent Node
     * @param distanceFromRoot Distance from the start point to this node
     * @param distanceToItem Straight line distance from the node to the item
     */
    public TileNode(int x, int y, TileNode previousNode,
                    int distanceFromRoot, double distanceToItem) {
        this.previousNode = previousNode;
        this.distanceFromRoot = distanceFromRoot;
        this.xPos = x;
        this.yPos = y;
        this.distanceToItem = distanceToItem;
    }

    /**
     * Gets the 'weight' of a node.
     * Weight represents how close the node is to the item or the cost to start at that tile.
     * The lower, the better.
     * Higher weight means it's not worth as much to look at, and it's more of a last resort.
     *
     * Calculated by adding distance from root to distance to item.
     * @return double 'Weight' of the tile
     */
    public double getTotalWeight() {
        return  distanceFromRoot + distanceToItem;
    }

    /**
     * Gets the X position of the tile.
     * @return int X position
     */
    public int getXPos() {
        return xPos;
    }

    /**
     * Gets the Y position of the tile.
     * @return int Y position
     */
    public int getYPos() {
        return yPos;
    }

    /**
     * Gets the previous node.
     * @return TileNode previous Node
     */
    public TileNode getPreviousNode() {
        return previousNode;
    }
}
