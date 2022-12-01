/**
 * A Class that implements an Entity
 * @author Matthew Salter (986488), Daniel Banks (2107922)
 */

package com.group38.prisonbreak.utilities;

import com.group38.prisonbreak.GameManager;
import javafx.scene.image.Image;

public abstract class Entity {

    // Path of the entity images
    private static final String IMAGE_URL = "images/GameImages/%s.png";

    // X position of the entity
    private int x;

    // Y position of the entity
    private int y;

    // Direction the entity is facing/moving
    private int direction;

    private Image entityImage;

    public Entity(int xPos, int yPos, int direction) {
        x = xPos;
        y = yPos;
        this.direction = direction;
    }

    /**
     * gets the x position
     * @return X
     */
    public int getX() {
        return x;
    }

    /**
     * gets the y position
     * @return Y
     */
    public int getY() {
        return y;
    }

    /**
     * gets the direction
     * @return direction
     */
    public int getDirection() {
        return direction;
    }

    /**
     * sets the direction of the entity
     * @param direction the new direction
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    public Image getEntityImage() {
        return entityImage;
    }

    /**
     * sets the X position
     * @param newX X position
     */
    protected void setX(int newX) {
        x = newX;
    }

    /**
     * sets the Y position
     * @param newY Y position
     */
    protected void setY(int newY) {
        y = newY;
    }

    /**
     * gets the current tile that the entity is on
     * @return tile
     */
    public Tile getCurrentTile() {
        return GameManager.level.getTile(x, y);
    }

    public abstract void move();

    /**
     * Interacts with an item that's on the current tile
     */
    protected abstract void itemInteract();

    /**
     * sets EntitySpriteURL
     * @param entityName Name of the png to be loaded
     */
    protected void setEntityImage(String entityName) {
        entityImage = FileUtilities.loadImageFromResource(String.format(IMAGE_URL, entityName));
    }

}
