/**
 * A Class that implements an Entity
 * @author Matthew Salter (986488), Daniel Banks (2107922)
 */

package com.group38.prisonbreak.utilities;

import com.group38.prisonbreak.Constants;
import com.group38.prisonbreak.GameManager;
import javafx.scene.image.Image;

public abstract class Entity {

    // Path of the entity images
    private static final String IMAGE_URL = "images/EntityImages/%s.png";

    // X position of the entity
    private int x;

    // Y position of the entity
    private int y;

    // Direction the entity is facing/moving
    private int direction;

    private boolean isAlive;

    // Image of the entity
    private Image entityImage;

    /**
     * Creates an instance of an Entity
     * @param xPos Starting X Position
     * @param yPos Starting Y Position
     * @param direction Starting Direction
     */
    public Entity(int xPos, int yPos, int direction) {
        x = xPos;
        y = yPos;
        this.direction = direction;
        isAlive = true;
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

    /**
     * Gets the Entity's Image
     * @return Image
     */
    public Image getEntityImage() {
        return entityImage;
    }

    /**
     * Gets if the entity is alive
     * @return boolean - isAlive
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * "Kills" the entity
     * Moves the entity off-screen (Rather than deletes it to prevent Concurrency Error)
     */
    public void killEntity() {
        isAlive = false;
        x = Constants.KILLED_ENTITY_LOCATION;
        y = Constants.KILLED_ENTITY_LOCATION;
    }

    /**
     * Checks if entity has Collided with a flying Assassin
     * If so; kills it
     * @return boolean has Collided with Flying Assassin
     */
    protected boolean CheckCollision() {
        if (GameManager.getLevel().hasCollidedWithFlyingAssassin(x, y)) {
            killEntity();
            return true;
        }
        return false;
    }

    /**
     * Sets the X position
     * @param newX X position
     */
    protected void setX(int newX) {
        x = newX;
    }

    /**
     * Sets the Y position
     * @param newY Y position
     */
    protected void setY(int newY) {
        y = newY;
    }

    /**
     * Gets the current tile that the entity is on
     * @return tile
     */
    public Tile getCurrentTile() {
        return GameManager.getLevel().getTile(x, y);
    }

    public abstract void move();

    /**
     * Interacts with an item that's on the current tile
     */
    protected abstract void itemInteract();

    /**
     * Sets EntitySpriteURL
     * @param entityName Name of the png to be loaded
     */
    protected void setEntityImage(String entityName) {
        entityImage = FileUtilities.loadImageFromResource(String.format(IMAGE_URL, entityName));
    }
}
