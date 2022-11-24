package com.group38.prisonbreak;

import com.group38.prisonbreak.utilities.Entity;
import com.group38.prisonbreak.utilities.Tile;

/**
 * A Class that stores the data about a level
 * @author Daniel Banks (2107922)
 */

public class Level {

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

    /**
     * Draws all the Tiles onto the level
     */
    private void drawTiles() {

    }

    /**
     * Draws all Entities onto the level
     */
    private void drawEntities(){

    }

    /**
     * Checks if a move is valid
     * @param direction The direction the entity wishes to move
     * @param posX X position of the entity
     * @param posY Y position of the entity
     * @return boolean - If the move is valid
     */
    public boolean canMove(int direction, int posX, int posY) {
        // TODO: implement can move
        return true;
    }

}
