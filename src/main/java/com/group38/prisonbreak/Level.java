package com.group38.prisonbreak;

import com.group38.prisonbreak.entities.enemies.FlyingAssassin;
import com.group38.prisonbreak.items.Bomb;
import com.group38.prisonbreak.items.Door;
import com.group38.prisonbreak.items.Gate;
import com.group38.prisonbreak.utilities.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A Class that stores the data about a level.
 * @author Daniel Banks (2107922), Matthew Salter (986488), Ben Wager (2108500)
 */

public class Level implements Drawable {

    /** Background image for Tiles. */
    private static final Image TILE_IMAGE =
            FileUtilities.loadImageFromResource("images/GameImages/tile.png");

    /** The level number. */
    private final int levelNumber;

    /** Tiles that build up the level. */
    private final Tile[][] tiles;

    /** All the entities in the Level. */
    private final ArrayList<Entity> entities;

    /** Stores the colours of the gates that are open. */
    private final HashMap<Integer, Boolean> gatesOpen = new HashMap<>();

    /**
     * Creates a Level instance.
     * @param levelNumber Level number
     * @param tiles 2D array of Tiles that make up the level
     * @param entities All the entities that are on the level
     */
    public Level(int levelNumber, Tile[][] tiles, ArrayList<Entity> entities) {
        this.levelNumber = levelNumber;
        this.tiles = tiles;
        this.entities = entities;

        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                if (tile.getItem() == null
                        || !(tile.getItem() instanceof Gate g)) {
                    continue;
                }

                gatesOpen.put(g.getGateColour(), false);
            }
        }
    }

    /**
     * Acts out a single move onto the tile board.
     *
     * @param posX The initial X position of the tile.
     * @param posY The initial Y position of the tile.
     * @param direction The direction that you want to travel in.
     * @return The returned position after moving.
     */
    public static int[] singleMove(int posX, int posY, int direction) {
        boolean isX = direction == Constants.RIGHT_ID
                || direction == Constants.LEFT_ID;

        // Checks if direction is negative (Up/Left)
        boolean isNegative = direction == Constants.UP_ID
                || direction == Constants.LEFT_ID;

        // Sets newX and newY
        int newX = isX ? posX + (isNegative ? -1 : 1) : posX;
        int newY = !isX ? posY + (isNegative ? -1 : 1) : posY;

        return new int[] {newX, newY};
    }

    /**
     * Math function to calculate length of any tile.
     * @param level Level of which tiles we need
     * @param width Width of the canvas
     * @param height Height of the canvas
     * @return An integer representing the calculated side length
     */
    public static int getTileSideLength(Level level, int width, int height) {
        int tileYAmt = level.tiles.length;
        int tileXAmt = level.tiles[0].length;

        int tileXWidth = width / tileXAmt;
        int tileYWidth = height / tileYAmt;

        return Math.min(tileXWidth, tileYWidth);
    }

    /**
     * Gets the ArrayList of all the Entities on the level.
     * @return An ArrayList of Entities
     */
    public ArrayList<Entity> getEntities() {
        return entities;
    }

    /**
     * Gets a tile from the X and Y position.
     * @param x X position
     * @param y Y position
     * @return A Tile
     */
    public Tile getTile(int x, int y) {
        return tiles[y][x];
    }

    /**
     * Gets the current Level number.
     * @return The level Number
     */
    public int getLevelNumber() {
        return levelNumber;
    }

    /**
     * Gets the player entity.
     * @return An Entity
     */
    public Entity getPlayer() {
        return entities.get(entities.size() - 1);
    }

    /**
     * Checks to see if there are any items left to be collected on the level.
     * @return If there are items left
     */
    public boolean hasItemsLeft() {
        for (Tile[] tileX : tiles) {
            for (Tile tile : tileX) {
                Item item = tile.getItem();

                // Return if item isn't a bomb, door or an open gate
                if (item != null
                        && !(item instanceof Bomb
                        || item instanceof Door
                        || item instanceof Gate)) {
                    return true;
                }
            }
        }
        return false;
    }

    /** Removes all items from tiles apart from Doors and Gates. */
    public void removeAllItemsExplosion() {
        for (Tile[] tileX : tiles) {
            for (Tile tile : tileX) {
                Item item = tile.getItem();
                // Removes item if it's not a gate, door or bomb
                if (!(item instanceof Gate
                        || item instanceof Door
                        || item instanceof Bomb)) {
                    tile.removeItem();
                }
                // If it's a bomb then set it off
                if (item instanceof Bomb b) {
                    b.immediateExplosion();
                }
            }
        }
    }

    /**
     * Draws all Entities onto the level.
     * @param g The graphics context we draw the entities onto.
     */
    public void drawEntities(GraphicsContext g) {
        int sideLength = getSideLength(g);
        for (Entity entity : entities) {
            boolean isNegative = entity.getDirection() == Constants.UP_ID
                    || entity.getDirection() == Constants.LEFT_ID;
            g.drawImage(entity.getEntityImage(),
                    isNegative ? (entity.getX() + 1) * sideLength
                            : entity.getX() * sideLength,
                    entity.getY() * sideLength,
                    isNegative ? -sideLength : sideLength, sideLength);
        }
    }

    /**
     * Draws items onto the Level.
     * @param g The graphics context we draw items onto.
     */
    public void drawItems(GraphicsContext g) {
        int sideLength = getSideLength(g);

        for (int tileY = 0; tileY < tiles.length; tileY++) {
            for (int tileX = 0; tileX < tiles[tileY].length; tileX++) {
                Tile t = tiles[tileY][tileX];
                if (t.getItem() != null) {
                    Item it = t.getItem();
                    it.draw(g, tileX, tileY, sideLength);
                }
            }
        }
    }

    /**
     * Gets the size of the tiles.
     * @param g The graphics context that we would use to find the canvas size.
     * @return The side length
     */
    public int getSideLength(GraphicsContext g) {
        int canvasWidth = (int) g.getCanvas().getWidth();
        int canvasHeight = (int) g.getCanvas().getHeight();

        return getTileSideLength(this, canvasWidth, canvasHeight);
    }

    /**
     * Checks if a move is valid.
     * @param direction The direction the entity wishes to move
     * @param posX X position of the entity
     * @param posY Y position of the entity
     * @param requiresColour If the Entity needs to stay on the same colour
     * @return boolean - If the move is valid
     */
    public boolean canMove(int posX, int posY, int direction, boolean requiresColour) {
        if (!requiresColour) {
            // Checks if direction is Up/Down (X)
            boolean isX = direction == Constants.RIGHT_ID
                    || direction == Constants.LEFT_ID;
            return isX ? posX < tiles[0].length && posX >= 0
                    : posY < tiles.length && posY >= 0;
        }
        return nextTile(posX, posY, direction) != null;
    }

    /**
     * See if a move would be a potentially good idea
     * @param posX Current position X
     * @param posY Current position Y
     * @param direction Direction that is planned on going
     * @param colourID Colour ID that you have to follow
     * @return An int[] of the new position in the form of {X, Y}.
     */
    public int[] potentialMove(int posX, int posY, int direction, int colourID) {
        int[] move = singleMove(posX, posY, direction);

        Tile nTile;
        try {
            nTile = tiles[move[1]][move[0]];
        } catch (IndexOutOfBoundsException e) {
            return new int[]{posX, posY};
        }
        if (!isGateOpen(nTile.getItem())) {
            return new int[]{posX, posY};
        }

        if (tiles[move[1]][move[0]].hasColour(colourID)) {
            return move;
        }


        return new int[]{posX, posY};
    }

    /**
     * Finds the position of the next tile an entity
     * should go if they follow colours.
     * @param posX Current X Position
     * @param posY Current Y Position
     * @param direction Direction of the Entity
     * @param coloursOptional (Optional) Colours of tile to move to
     * @return int array with X and Y position [X Position, Y Position]
     */
    public int[] moveTo(int posX, int posY, int direction, int... coloursOptional) {
        int[] colours = coloursOptional.length == 0
                ? tiles[posY][posX].getColourIDs() : coloursOptional;

        // Checks if direction is Up/Down (X)
        int[] singleMove = singleMove(posX, posY, direction);
        int newX = singleMove[0];
        int newY = singleMove[1];

        boolean isNegative = direction == Constants.UP_ID
                || direction == Constants.LEFT_ID;
        boolean isX = direction == Constants.RIGHT_ID
                || direction == Constants.LEFT_ID;

        while (newY >= 0 && newY - 1 <= tiles.length
                && newX >= 0 && newX - 1 <= tiles[0].length) {

            // If the new position if of the board remain where it is
            if (newY >= tiles.length || newX >= tiles[0].length) {
                return new int[] {posX, posY};
            }

            Tile newTile = tiles[newY][newX];
            Item tileItem = newTile.getItem();
            boolean bombSkip = tileItem instanceof Bomb
                    && ((Bomb) tileItem).isExplodable();

            boolean sharedColours = tiles[newY][newX].hasColours(colours);
            if (sharedColours && isGateOpen(tileItem)
                    && isDoorOpen(tileItem) && !bombSkip) {
                return new int[] {newX, newY};
            }

            newX = isX ? newX + (isNegative ? -1 : 1) : newX;
            newY = !isX ? newY + (isNegative ? -1 : 1) : newY;
        }

        // If it can't find a tile stay where it is
        return new int[] {posX, posY};
    }

    /**
     * Gets number of items left on the level.
     * @return The number of items left
     */
    public int getNoItems() {
        int items = 0;
        for (Tile[] tileX : tiles) {
            for (Tile tile : tileX) {
                Item item = tile.getItem();
                if (item != null && isMainBombOrNotBomb(item)) {
                    items++;
                }
            }
        }
        return items;
    }

    /**
     * Checks if a bomb is the 'main bomb' or a fake bomb.
     * (Fake bombs are used to set the main bomb off if stepped on)
     * @param item item to check
     * @return if it's the main bomb
     */
    public boolean isMainBombOrNotBomb(Item item) {
        if (item instanceof Bomb b) {
            return b.isExplodable();
        }
        return true;
    }

    /**
     * Checks if the item is a gate and if it's open.
     * @param item Item to check
     * @return if the gate is open
     */
    public boolean isGateOpen(Item item) {
        if (item instanceof  Gate g) {
            return gatesOpen.get(g.getGateColour());
        }
        return true;
    }

    /**
     * Checks if an entity won't collide with another entity
     * @param baseEntity instance of the entity
     * @param posX X position to check
     * @param posY Y position to check
     * @return Boolean for SmartThief for whether it wont collide to another entity.
     */
    public boolean wontCollide(Entity baseEntity, int posX, int posY) {
        for (Entity entity : entities) {
            if (entity != baseEntity && entity.getX() == posX
                    && entity.getY() == posY) {
                return false;
            }
        }
        return true;
    }

    /**
     * Draws a level.
     * @param g The graphics context
     */
    @Override
    public void draw(GraphicsContext g) {
        g.setFill(Color.BLACK);
        g.fillRect(0, 0,
                g.getCanvas().getWidth(),
                g.getCanvas().getHeight()
        );

        drawTiles(g);
        drawEntities(g);
        drawItems(g);
    }

    /**
     * Gets the width of the tiles.
     * @return int width
     */
    public int getWidth() {
        return tiles[0].length;
    }

    /**
     * Gets the Height of the tiles.
     * @return int height
     */
    public int getHeight() {
        return tiles.length;
    }

    /**
     * Open the gate for a given colour.
     * @param gateColour An integer representing our colours
     */
    public void openGate(int gateColour) {
        gatesOpen.put(gateColour, true);
    }

    /**
     * Checks if a particular won't set off a bomb.
     * @param posX The X position of the tile
     * @param posY The Y position of the tile
     * @return Returns true of the bomb will not detonate, false otherwise
     */
    public boolean wontSetOffBomb(int posX, int posY) {
        return !(getTile(posX, posY).getItem() instanceof Bomb);
    }

    /**
     * Checks if an entity has collided with a flying assassin.
     * @param xPos X Position of the entity
     * @param yPos Y Position of the entity
     * @return boolean has collided with flying assassin
     */
    public boolean hasCollidedWithFlyingAssassin(int xPos, int yPos) {
        for (Entity entity : entities) {
            if (entity instanceof FlyingAssassin) {
                if (entity.getX() == xPos && entity.getY() == yPos) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Draws all the Tiles onto the level.
     * @param g The graphics context to draw the tiles onto.
     */
    private void drawTiles(GraphicsContext g) {
        int sideLength = getSideLength(g);

        int tileXDraw = 0;
        int tileYDraw = 0;

        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                g.drawImage(
                        Level.TILE_IMAGE,
                        tileXDraw,
                        tileYDraw,
                        sideLength,
                        sideLength
                );
                Tile t = getTile(x, y);

                // Tile colour side length
                int tileColourSL = sideLength / 2;

                // Loop through every colour in tile
                colourTiles(g, tileXDraw, tileYDraw, t, tileColourSL);

                tileXDraw += sideLength;
            }
            tileXDraw = 0;
            tileYDraw += sideLength;
        }
    }

    /**
     * With a given tile coordinate, apply the colours.
     * @param g The graphics context to apply the colours
     * @param tileXDraw Tile on the X coordinate to draw.
     * @param tileYDraw Tile on the Y coordinate to draw.
     * @param tileColourSL The side length of any individual colour.
     */
    private void colourTiles(GraphicsContext g, int tileXDraw, int tileYDraw, Tile t, int tileColourSL) {

        for (int col = 0; col < t.getColours().length; col++) {
            boolean isRight = col == 1 || col == 3;
            boolean isBottom = col == 2 || col == 3;

            g.setFill(t.getColours()[col]);

            // Ternary checks to fill the correct part of the square
            g.fillRect(
                    tileXDraw + (isRight ? tileColourSL : 0),
                    tileYDraw + (isBottom ? tileColourSL : 0),
                    tileColourSL,
                    tileColourSL);
        }
    }

    /**
     * Gets if item is a door and is open.
     * @param item item to check
     * @return If the door is open
     */
    private boolean isDoorOpen(Item item) {
        if (item instanceof Door) {
            return ((Door) item).isOpen();
        }
        return true;
    }

    /**
     * Finds the next tile an entity can move to if they follow colours.
     * @param posX Current X Position
     * @param posY Current Y Position
     * @param direction Direction of the Entity
     * @return Tile or Null if no Tile found
     */
    private Tile nextTile(int posX, int posY, int direction) {
        int[] newLocation = singleMove(posX, posY, direction);
        int newX = newLocation[0];
        int newY = newLocation[1];

        while (newY >= 0 && newY - 1 <= tiles.length && newX >= 0
                && newX - 1 <= tiles[0].length) {
            boolean isNegative = direction == Constants.UP_ID
                    || direction == Constants.LEFT_ID;
            boolean isX = direction == Constants.RIGHT_ID
                    || direction == Constants.LEFT_ID;

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
}
