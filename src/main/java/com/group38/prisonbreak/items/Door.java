package com.group38.prisonbreak.items;

import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.utilities.FileUtilities;
import com.group38.prisonbreak.utilities.Item;
import javafx.scene.image.Image;

import java.util.HashMap;

/**
 * Implements a Door  in the game
 * @author Daniel Banks (2107922)
 */
public class Door extends Item {

    private static final HashMap<Integer, String> imagePathCache = new HashMap<>() {{
        put(0, gameImagesStart + "door_close.png");
        put(1, gameImagesStart + "door_open.png");
    }};

    private static final HashMap<Integer, Image> imageCache = new HashMap<>();

    public Door() {
        // TODO: Implement Constructor

        if (imageCache.isEmpty()) {
            imagePathCache.keySet().forEach(index -> {
                imageCache.put(index, FileUtilities.loadImageFromResource(imagePathCache.get(index)));
            });
        }
    }

    public boolean isOpen() {
        return !GameManager.getLevel().hasItemsLeft();
    }

    @Override
    public boolean interact(boolean isPlayer) {
        if (!(GameManager.getLevel().hasItemsLeft())) {
            GameManager.endGame(isPlayer);
            return true;
        }
        return false;
    }

    @Override
    public String getImagePath() {
        return imagePathCache.get(imageIndex);
    }

    /**
     * @return The image that represents an open or closed door
     */
    @Override
    public Image getImage() {
        return imageCache.get(isOpen() ? 0 : 1);
    }

}
