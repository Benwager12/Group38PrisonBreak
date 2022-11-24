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

    public Level(int levelNumber,Tile[][] tiles, Entity[] entities) {
        this.levelNumber = levelNumber;
        this.tiles = tiles;
        this.entities = entities;
    }



}
