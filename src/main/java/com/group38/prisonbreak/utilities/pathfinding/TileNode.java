package com.group38.prisonbreak.utilities.pathfinding;

/**
 * Implements a Node for the Tile Tree
 * @author Daniel Banks
 */
public class TileNode {

    // X position of the tile
    private final int xPos;

    // Y position of the tile
    private final int yPos;

    // If the Tile has an item
    private final boolean hasItem;

    // TileNode for the left direction
    private TileNode leftTile;

    // TileNode for the right direction
    private TileNode rightTile;

    // TileNode for the up direction
    private TileNode upTile;

    // Tile for the down direction
    private TileNode downTile;

    /**
     * Creates an instance of a TileNode
     * @param xPos X position of the Tile it represents
     * @param yPos Y position of the Tile it represents
     * @param hasItem If the Tile it represents has and item
     */
    public TileNode(int xPos, int yPos, boolean hasItem) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.hasItem = hasItem;
    }

    /**
     * Sets left Tile
     * @param leftTile TileNode that's on the left direction of the tile
     */
    public void setLeftTile(TileNode leftTile) {
        this.leftTile = leftTile;
    }

    /**
     * Sets the right Tile
     * @param rightTile TileNode that's on the right direction of the tile
     */
    public void setRightTile(TileNode rightTile) {
        this.rightTile = rightTile;
    }

    /**
     * Sets the up Tile
     * @param upTile TileNode that's on the up direction of the tile
     */
    public void setUpTile(TileNode upTile) {
        this.upTile = upTile;
    }

    /**
     * Sets the down Tile
     * @param downTile TileNode that's on the up direction of the tile
     */
    public void setDownTile(TileNode downTile) {
        this.downTile = downTile;
    }

    public TileNode getUpTile() {
        return upTile;
    }

    public TileNode getDownTile() {
        return downTile;
    }

    public TileNode getLeftTile() {
        return leftTile;
    }

    public TileNode getRightTile() {
        return rightTile;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public boolean hasItem() {
        return hasItem;
    }
}
