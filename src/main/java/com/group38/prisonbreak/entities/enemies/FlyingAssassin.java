package com.group38.prisonbreak.entities.enemies;

import com.group38.prisonbreak.Constants;
import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.entities.Player;
import com.group38.prisonbreak.utilities.Enemy;
import com.group38.prisonbreak.utilities.Entity;
import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * Implements an Enemy in the game.
 * @author Daniel Banks (2107922)
 */
public class FlyingAssassin extends Enemy {

    /**
     * Boolean to sa if this flying assassin has collied with the player.
     */
    private boolean hasColliedWithPlayer = false;

    /**
     * Creates an instance of a flying assassin.
     * @param xPos Starting X Position
     * @param yPos Starting Y Position
     * @param direction Starting direction
     */
    public FlyingAssassin(int xPos, int yPos, int direction) {
        super(xPos, yPos, direction);
        setEntityImage("assassin_flying");
    }

    /**
     * Gets if the flying assassin has collided with the player.
     * @return boolean has collied with player
     */
    public boolean getHasCollidedWithPlayer() {
        return hasColliedWithPlayer;
    }

    /**
     * Moves the FlyingAssassin.
     * Is called every tick in the enemyTimeLine (in LevelController)
     */
    @Override
    public void move() {
        boolean canMove = false;
        while (!canMove) {
            int direction = super.getDirection();

            boolean isX = direction == Constants.RIGHT_ID
                    || direction == Constants.LEFT_ID;
            boolean isNegative = direction == Constants.UP_ID
                    || direction == Constants.LEFT_ID;

            // X Position of the next Tile (Based on Direction)
            int newX =
                    isX ?  super.getX() + (isNegative ? -1 : 1) : super.getX();

            // Y Position of the next Tile (Based on Direction)
            int newY =
                    !isX ? super.getY() + (isNegative ? -1 : 1) : super.getY();

            if (GameManager.getLevel().canMove(newX, newY, direction, false)) {
                // sets the new X and Y positions
                super.setX(newX);
                super.setY(newY);
                canMove = true;
            } else {
                // Switches direction
                int newDir =
                        isX ? (direction == Constants.RIGHT_ID
                                ? Constants.LEFT_ID : Constants.RIGHT_ID)
                                : (direction == Constants.UP_ID
                                ? Constants.DOWN_ID : Constants.UP_ID);
                super.setDirection(newDir);
            }
        }
        // Checks if it has collided with any other entities
        checkCollisions();
    }

    /**
     * Gets the Image of the entity.
     * @return Image of the entity
     */
    @Override
    public Image getEntityImage() {
        return super.getEntityImage();
    }

    /**
     * Checks if the flying assassin has collided with an entity.
     * If it has then it 'Destroys that entity'
     * If it's collided with the player it ends the game
     */
    private void checkCollisions() {
        // Gets collisions
        ArrayList<Entity> collidedEntities = getCollision();
        collidedEntities.forEach(collidedEntity -> {
            collidedEntity.killEntity();
            if (collidedEntity instanceof Player) {
                hasColliedWithPlayer = true;
                GameManager.endGame(false);
            }
        });
    }

    /**
     * Gets for a collision between with an entity.
     * @return Entity object of the entity it's collided with
     */
    private ArrayList<Entity> getCollision() {
        // ArrayList of collisions with the flying assassin
        ArrayList<Entity> colliedEntities = new ArrayList<>();

        for (Entity entity : GameManager.getLevel().getEntities()) {
            if (entity != this) {
                if (entity.getX() == super.getX()
                        && entity.getY() == super.getY()) {
                    colliedEntities.add(entity);
                }
            }
        }
        return colliedEntities;
    }
}
