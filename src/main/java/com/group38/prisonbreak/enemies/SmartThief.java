package com.group38.prisonbreak.enemies;

import com.group38.prisonbreak.utilities.Enemy;
import com.group38.prisonbreak.utilities.pathfinding.TileTree;
import javafx.scene.image.Image;

/**
 * Implements a Smart Thief in the game
 * @author Daniel Banks
 */
public class SmartThief extends Enemy {

    private boolean hasMoved = false;

    private final TileTree tileTree = new TileTree();

    public SmartThief(int xPos, int yPos, int direction) {
        super(xPos, yPos, direction);
        setEntityImage("Guard 1");
    }

    @Override
    public void move() {
        if (!hasMoved) {
            hasMoved = true;
            tileTree.searchPaths(super.getX(), super.getY());
           // tileTree.searchShortestRoute();
        }
    }

    @Override
    public Image getEntityImage() {
        return super.getEntityImage();
    }
}
