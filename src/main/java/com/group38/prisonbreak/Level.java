package com.group38.prisonbreak;

import com.group38.prisonbreak.items.Bomb;
import com.group38.prisonbreak.utilities.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A Class that stores the data about a level
 *
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
    private final ArrayList<Entity> entities;

    /**
     * Creates a Level instance
     *
     * @param levelNumber Level number
     * @param tiles       2D array of Tiles that make up the level
     * @param entities    All the entities that are on the level
     */
    public Level(int levelNumber, Tile[][] tiles, ArrayList<Entity> entities) {
        this.levelNumber = levelNumber;
        this.tiles = tiles;
        this.entities = entities;
    }

    /**
     * Gets the ArrayList of all the Entities on the level
     *
     * @return ArrayList of Entities
     */
    public ArrayList<Entity> getEntities() {
        return entities;
    }

    /**
     * Gets a tile from the X and Y position
     *
     * @param x X position
     * @param y Y position
     * @return Tile
     */
    public Tile getTile(int x, int y) {
        return tiles[y][x];
    }

    /**
     * gets the player entity
     *
     * @return Entity
     */
    public Entity getPlayer() {
        return entities.get(entities.size() - 1);
    }

    /**
     * Checks to see if there are any items left to be collected on the level
     *
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
    public void drawEntities(GraphicsContext g) {
        int sideLength = getSideLength(g);
        for (Entity entity : entities) {
            boolean isNegative = entity.getDirection() == 0 || entity.getDirection() == 3;
            g.drawImage(entity.getEntityImage(),
                    isNegative ? (entity.getX() + 1) * sideLength : entity.getX() * sideLength,
                    entity.getY() * sideLength,
                    isNegative ? -sideLength : sideLength, sideLength);
        }
    }

    /**
     * gets the size of the tiles
     *
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
     *
     * @param direction      The direction the entity wishes to move
     * @param posX           X position of the entity
     * @param posY           Y position of the entity
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

    /**
     * Finds the position of the next tile an entity should go if they follow colours
     * @param posX Current X Position
     * @param posY Current Y Position
     * @param direction Direction of the Entity
     * @return int array with X and Y position [X Position, Y Position]
     */
    public int[] moveTo(int posX, int posY, int direction) {
        // Checks if direction is Up/Down (X)
        boolean isX = direction == 1 || direction == 3;

        // Checks if direcltion is negative (Up/Left)
        boolean isNegative = direction == 0 || direction == 3;

        // Iterates through all the Tiles; from the Current Tile to the edge

        //(newY + 1 < tiles.length || newY > 0) || (newX + 1 < tiles[0].length || newX > 0) &&

        // X Position of the next Tile (Based on Direction)
        int newX = isX ? posX + (isNegative ? -1 : 1) : posX;
        // Y Position of the next Tile (Based on Direction)
        int newY = !isX ? posY + (isNegative ? -1 : 1) : posY;

        while (newY >= 0 && newY - 1 <= tiles.length && newX >= 0 && newX - 1 <= tiles[0].length) {
            if (newY >= tiles.length || newX >= tiles[0].length) {
                return new int [] {
                        isX ? (isNegative ? tiles[0].length -1 : 0) : posX,
                        !isX ? (isNegative ? tiles.length -1 : 0) : posY};
            }

            if (tiles[newY][newX].hasColours(tiles[posY][posX].getColours())) {
                return new int[] {newX, newY};
            }

            newX = isX ? newX + (isNegative ? -1 : 1) : newX;
            newY = !isX ? newY + (isNegative ? -1 : 1) : newY;
        }
        return new int [] {
                isX ? (isNegative ? tiles[0].length -1 : 0) : posX,
                !isX ? (isNegative ? tiles.length -1 : 0) : posY};
    }


    /**
     * Finds the next tile an entity can move to if they follow colours
     * @param posX Current X Position
     * @param posY Current Y Position
     * @param direction Direction of the Entity
     * @return Tile or Null if no Tile found
     */
    private Tile nextTile(int posX, int posY, int direction) {
        // Checks if direction is Up/Down (X)
        boolean isX = direction == 1 || direction == 3;

        // Checks if direcltion is negative (Up/Left)
        boolean isNegative = direction == 0 || direction == 3;

        // Iterates through all the Tiles; from the Current Tile to the edge

        //(newY + 1 < tiles.length || newY > 0) || (newX + 1 < tiles[0].length || newX > 0) &&

        // X Position of the next Tile (Based on Direction)
        int newX = isX ? posX + (isNegative ? -1 : 1) : posX;
        // Y Position of the next Tile (Based on Direction)
        int newY = !isX ? posY + (isNegative ? -1 : 1) : posY;

        while (newY >= 0 && newY - 1 <= tiles.length && newX >= 0 && newX - 1 <= tiles[0].length) {
            if (newY >= tiles.length || newX >= tiles[0].length) {
                return null;
            }

            if (tiles[newY][newX].hasColours(tiles[posY][posX].getColours())) {
                return tiles[newY][newX];
            }

            newX = isX ? newX + (isNegative ? -1 : 1) : newX;
            newY = !isX ? newY + (isNegative ? -1 : 1) : newY;
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
        int tileXAmt = level.tiles[0].length;

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
