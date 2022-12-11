package com.group38.prisonbreak.items;

import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.utilities.FileUtilities;
import com.group38.prisonbreak.utilities.Item;
import javafx.scene.image.Image;

import java.util.HashMap;

/**
 * Implements a Lever.
 * @author Daniel Banks (2107922)
 */
public class Lever extends Item {

    /** A list of all the paths that lever would use. */
    private static final HashMap<Integer, String> IMAGE_PATH_CACHE =
            new HashMap<>() {{
            put(0, GAME_IMAGE_PATH + "key_rusted.png");
            put(1, GAME_IMAGE_PATH + "key_bronze.png");
            put(2, GAME_IMAGE_PATH + "key_silver.png");
            put(3, GAME_IMAGE_PATH + "key_gold.png");
    }};

    /** The image cache that will store all images from IMAGE_PATH_CACHE. */
    private static final HashMap<Integer, Image> IMAGE_CACHE = new HashMap<>();

    /** The colour of the lever, and thus what gate to open. */
    private final int itemColour;

    /**
     * Creates an instance of a Lever and initializes the cache.
     * @param metadata Colour of the lever (int)
     */
    public Lever(String metadata) {
        itemColour = Integer.parseInt(metadata);
        imageIndex = itemColour;

        if (IMAGE_CACHE.isEmpty()) {
            IMAGE_PATH_CACHE.keySet().forEach(index ->
                    IMAGE_CACHE.put(index, FileUtilities.loadImageFromResource(
                            IMAGE_PATH_CACHE.get(index))
                    )
            );
        }
    }

    /**
     *
     * @param isPlayer if the entity interacting with the item is the player
     * @return Always returns true
     */
    @Override
    public boolean interact(boolean isPlayer) {
        GameManager.getLevel().openGate(imageIndex);
        return true;
    }

    /**
     * Get the image path using the current index.
     * @return The image path as a string.
     */
    @Override
    public String getImagePath() {
        return IMAGE_PATH_CACHE.get(imageIndex);
    }

    /**
     * The current image used.
     * @return The current image used in the form of an image.
     */
    @Override
    public Image getImage() {
        return IMAGE_CACHE.get(imageIndex);
    }

    /**
     * Get the lever colour.
     * @return The lever colour in the form of an integer.
     */
    public int getItemColour() {
        return itemColour;
    }
}
