package com.group38.prisonbreak.entities;

import com.group38.prisonbreak.Constants;
import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.utilities.Entity;
import com.group38.prisonbreak.utilities.Item;
import javafx.scene.input.KeyCode;

import java.util.HashMap;

/**
 * Implements a Player in the game.
 * @author Daniel Banks (2107922), Matthew Salter (986488)
 */
public class Player extends Entity {

    /** Image cache for the player outfits in the game. */
    private static final HashMap<Integer, String> IMAGE_PATH_CACHE =
            new HashMap<>() {{
                put(1, "outfit_jumpsuit");
                put(2, "outfit_38_jumper");
                put(3, "outfit_tuxedo");
                put(4, "outfit_tank_top");
                put(5, "outfit_suit");
                put(6, "outfit_stripey_fit");
                put(7, "outfit_guard");
                put(8, "outfit_xmas");
            }};

    /**
     * Creates and instance of player.
     *
     * @param xPos X position of the player
     * @param yPos Y position of the player
     * @param direction starting direction of the player
     * @param outfitIndex index of the player outfit to be worn
     */
    public Player(int xPos, int yPos, int direction, int outfitIndex) {
        super(xPos, yPos, direction);
        setEntityImage(IMAGE_PATH_CACHE.get(outfitIndex));
    }

    /**
     * Moves the player based on the keys pressed is called every player tick.
     */
    @Override
    public void move() {
        for (KeyCode c : GameManager.getCurrentlyPressed()) {
            if (c == KeyCode.UP || c == KeyCode.W) {
                setDirection(Constants.UP_ID);
                break;
            } else if (c == KeyCode.RIGHT || c == KeyCode.D) {
                setDirection(Constants.RIGHT_ID);
                break;
            } else if (c == KeyCode.DOWN || c == KeyCode.S) {
                setDirection(Constants.DOWN_ID);
                break;
            } else if (c == KeyCode.LEFT || c == KeyCode.A) {
                setDirection(Constants.LEFT_ID);
                break;
            }
        }

        int[] newPos =
                GameManager.getLevel()
                        .moveTo(
                                super.getX(),
                                super.getY(),
                                super.getDirection()
                        );
        super.setX(newPos[0]);
        super.setY(newPos[1]);

        itemInteract();

        // Checks if player has collided with flying assassin
        if (checkCollision()) {
            GameManager.endGame(false);
        }
    }

    /**
     * Calls the interaction of the item when an entity collides with it.
     */
    @Override
    protected void itemInteract() {
        Item item = getCurrentTile().getItem();
        if (item != null) {
            if (item.interact(true)) {
                getCurrentTile().removeItem();
            }
        }
    }
}
