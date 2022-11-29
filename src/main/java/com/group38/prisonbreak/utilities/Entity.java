/**
 * A Class that implements an Entity
 * @author Matthew Salter (986488)
 */

package com.group38.prisonbreak.utilities;

import com.group38.prisonbreak.GameManager;

public abstract class Entity {

    private int x;
    private int y;
    private int direction;

    public Entity(int xPos, int yPos, int direction) {
        x = xPos;
        y = yPos;
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public Tile getCurrentTile() {
        return GameManager.level.getTile(x, y);
    }

    public abstract void move();

    public abstract void itemInteract();

}
