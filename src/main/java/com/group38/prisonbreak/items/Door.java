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
        put(0, gameImagesStart + "close_door.png");
        put(1, gameImagesStart + "open_door.png");
    }};

    private static final HashMap<Integer, Image> imageCache = new HashMap<>();

    private boolean isOpen = false;

    public Door() {
        // TODO: Implement Constructor

        if (imageCache.isEmpty()) {
            imagePathCache.keySet().forEach(index -> {
                imageCache.put(index, FileUtilities.loadImageFromResource(imagePathCache.get(index)));
            });
        }
    }

    @Override
    public boolean interact(boolean isPlayer) {
        if (!(GameManager.level.hasItemsLeft())) {
            GameManager.endGame();
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
        return imageCache.get(isOpen ? 1 : 0);
    }

}
