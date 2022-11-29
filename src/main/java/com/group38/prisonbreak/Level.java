package com.group38.prisonbreak;

import com.group38.prisonbreak.utilities.Drawable;
import com.group38.prisonbreak.utilities.Entity;
import com.group38.prisonbreak.utilities.FileUtilities;
import com.group38.prisonbreak.utilities.Tile;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * A Class that stores the data about a level
 * @author Daniel Banks (2107922)
 */

public class Level implements Drawable {

    // Background image for Tiles
    private static final Image tileImage = FileUtilities.loadImageFromResource("images/GameImages/tile.png");

    // The level Number
    private final int levelNumber;

    // Tiles that build up the level
    private final Tile[][] tiles;

    // All the entities in the Level
    private final Entity[] entities;

    /**
     * Creates a Level instance
     * @param levelNumber Level number
     * @param tiles 2D array of Tiles that make up the level
     * @param entities All the entities that are on the level
     */
    public Level(int levelNumber,Tile[][] tiles, Entity[] entities) {
        this.levelNumber = levelNumber;
        this.tiles = tiles;
        this.entities = entities;
    }


    public Entity[] getEntities() {
        return entities;
    }

    /**
     * Draws all the Tiles onto the level
     */
    private void drawTiles(GraphicsContext g) {
        int tileYAmt = tiles.length;
        int tileXAmt = tiles[0].length;

        int canvasWidth = (int) g.getCanvas().getWidth();
        int canvasHeight = (int) g.getCanvas().getHeight();

        int sideLength = getTileSideLength(this, canvasWidth, canvasHeight);

        int tileXDraw = 0;
        int tileYDraw = 0;

        for (int y = 0; y < tileYAmt; y++) {
            for (int x = 0; x < tileXAmt; x++) {
                g.drawImage(Level.tileImage, tileXDraw, tileYDraw, sideLength, sideLength);
                tileXDraw += sideLength;
            }
            tileXDraw = 0;
            tileYDraw += sideLength;
        }
    }

    /**
     * Draws all Entities onto the level
     */
    private void drawEntities(GraphicsContext g){

    }

    /**
     * Checks if a move is valid
     * @param direction The direction the entity wishes to move
     * @param posX X position of the entity
     * @param posY Y position of the entity
     * @return boolean - If the move is valid
     */
    public boolean canMove(int posX, int posY, int direction) {
        return nextTile(posX, posY, direction) != null;
    }

    private Tile nextTile(int posX, int posY, int direction) {
        // Checks if direction is Up/Down (X)
        boolean isX = direction == 1 || direction == 3;

        // Checks if direction is negative (Up/Left)
        boolean isNegative = direction == 0 || direction == 3;

        // X Position of the next Tile (Based on Direction)
        int newX = isX ?  posX + (isNegative ? -1 : 1) : posX;

        // Y Position of the next Tile (Based on Direction)
        int newY = !isX ? posY + (isNegative ? -1 : 1) : posY;

        Tile nextTile = tiles[newY][newX];

        // Iterates through all the Tiles; from the Current Tile to the edge
        while (tiles[newY] != null && tiles[newY][newX] != null) {
            System.out.printf("%d %d%n", newY, newX);
            if (nextTile.hasColours(tiles[posY][posX].getColours())) {
                return nextTile;
            }
            // Gets the next Tile
            nextTile = tiles[isX ? (isNegative ? --newX : ++newX) : newX][!isX ? (isNegative ? --newY : ++newY) : newY];
        }
        return null;
    }

    /**
     * @param g The graphics context
     */
    @Override
    public void draw(GraphicsContext g) {
        g.setFill(Color.BLACK);
        g.fillRect(0, 0, g.getCanvas().getWidth(), g.getCanvas().getHeight());

        drawTiles(g);
        drawEntities(g);
    }

    private static int getTileSideLength(Level level, int width, int height) {
        int tileYAmt = level.tiles.length;
        int tileXAmt  = level.tiles[0].length;

        int tileXWidth = width / tileXAmt;
        int tileYWidth = height / tileYAmt;

        return Math.min(tileXWidth, tileYWidth);
    }
}
