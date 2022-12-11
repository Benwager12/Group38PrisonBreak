package com.group38.prisonbreak.items;

import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.utilities.FileUtilities;
import com.group38.prisonbreak.utilities.Item;
import javafx.scene.image.Image;

import java.util.HashMap;

/**
 * Implements a Door  in the game.
 * @author Daniel Banks (2107922)
 */
public class Door extends Item {

    /** Hashmap of id's to image to show the door locked and unlocked. */
    private static final HashMap<Integer, String> IMAGE_PATH_CACHE =
            new HashMap<>() {{
            put(0, GAME_IMAGE_PATH + "door_close.png");
            put(1, GAME_IMAGE_PATH + "door_open.png");
    }};

    /** An image cache, to be filled with images that are in IMAGE_PATH_CACHE. */
    private static final HashMap<Integer, Image> IMAGE_CACHE = new HashMap<>();

    /** Initializes the door, fill the door cache if hasn't been. */
    public Door() {
        if (IMAGE_CACHE.isEmpty()) {
            IMAGE_PATH_CACHE.keySet().forEach(index -> {
                IMAGE_CACHE.put(
                        index, FileUtilities.loadImageFromResource(
                                IMAGE_PATH_CACHE.get(index)
                        )
                );
            });
        }
    }

    /**
     * Check if the game is finished by seeing if there are any items left.
     * @return A boolean representing whether the game is finished.
     */
    public boolean isOpen() {
        return !GameManager.getLevel().hasItemsLeft();
    }

    /**
     * Checks if the game is finished and then end the game.
     * @param isPlayer if the entity interacting with the item is the player
     * @return Returns true if the game is able to be finished, otherwise false.
     */
    @Override
    public boolean interact(boolean isPlayer) {
        if (!(GameManager.getLevel().hasItemsLeft())) {
            GameManager.endGame(isPlayer);
            return true;
        }
        return false;
    }

    /**
     * Get the image path of the item.
     * @return Returns a string representing the image path.
     */
    @Override
    public String getImagePath() {
        return IMAGE_PATH_CACHE.get(imageIndex);
    }

    /**
     * Gets the image of the door.
     * @return The image that represents an open or closed door.
     */
    @Override
    public Image getImage() {
        return IMAGE_CACHE.get(isOpen() ? 0 : 1);
    }

}
