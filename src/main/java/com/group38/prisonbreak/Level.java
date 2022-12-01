package com.group38.prisonbreak;

import com.group38.prisonbreak.items.Bomb;
import com.group38.prisonbreak.utilities.*;
import javafx.scene.canvas.Canvas;
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

    public Tile getTile(int x, int y) {
        return tiles[y][x];
    }

    /**
     * Checks to see if there are any items left to be collected on the level
     * @return boolean
     */
    public boolean hasItemsLeft() {
        for (Tile[] tileX : tiles) {
            for (Tile tile : tileX) {
                Item item = tile.getItem();
                if (item != null && !(item instanceof Bomb)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Draws all the Tiles onto the level
     */
    private void drawTiles(GraphicsContext g) {
        int sideLength = getSideLength(g);

        int tileXDraw = 0;
        int tileYDraw = 0;

        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                g.drawImage(Level.tileImage, tileXDraw, tileYDraw, sideLength, sideLength);
                Tile t = getTile(x, y);

                int tileColourSL = sideLength / 2; // Tile colour side length

                // Loop through every colour in tile
                for (int col = 0; col < t.getColours().length; col++) {
                    g.setFill(t.getColours()[col]);

                    // Ternary checks to fill the correct part of the square
                    g.fillRect(tileXDraw + (col == 1 || col == 3 ? tileColourSL : 0),
                            tileYDraw + (col == 2 || col == 3 ? tileColourSL : 0),
                            tileColourSL, tileColourSL);
                }

                tileXDraw += sideLength;
            }
            tileXDraw = 0;
            tileYDraw += sideLength;
        }
    }

    /**
     * Draws all Entities onto the level
     */
    public void drawEntities(GraphicsContext g){
        int sideLength = getSideLength(g);
        for (Entity entity : entities) {
            g.drawImage(entity.getEntityImage(), entity.getX() * sideLength, entity.getY() * sideLength, sideLength, sideLength);
        }
    }

    /**
     * gets the size of the tiles
     * @param g GraphicsContext
     * @return sideLength
     */
    private int getSideLength(GraphicsContext g) {
        int canvasWidth = (int) g.getCanvas().getWidth();
        int canvasHeight = (int) g.getCanvas().getHeight();

        return getTileSideLength(this, canvasWidth, canvasHeight);
    }

    /**
     * Checks if a move is valid
     * @param direction The direction the entity wishes to move
     * @param posX X position of the entity
     * @param posY Y position of the entity
     * @param requiresColour If the Entity needs to stay on the same colour
     * @return boolean - If the move is valid
     */
    public boolean canMove(int posX, int posY, int direction, boolean requiresColour) {
        if (!requiresColour) {
            // Checks if direction is Up/Down (X)
            boolean isX = direction == 1 || direction == 3;
            return isX ? posX < tiles[0].length && posX >= 0 : posY < tiles.length && posY >= 0;
        }
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

    public static int getTileSideLength(Level level, int width, int height) {
        int tileYAmt = level.tiles.length;
        int tileXAmt  = level.tiles[0].length;

        int tileXWidth = width / tileXAmt;
        int tileYWidth = height / tileYAmt;

        return Math.min(tileXWidth, tileYWidth);
    }

    public int getWidth() {
        return tiles[0].length;
    }

    public int getHeight() {
        return tiles.length;
    }
}
