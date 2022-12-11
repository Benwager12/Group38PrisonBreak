package com.group38.prisonbreak.items;

import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.utilities.FileUtilities;
import com.group38.prisonbreak.utilities.Item;
import javafx.scene.image.Image;

import java.util.HashMap;

/**
 * Implements a Lever
 * @author Daniel Banks (2107922)
 */
public class Lever extends Item {

    private static final HashMap<Integer, String> IMAGE_PATH_CACHE =
            new HashMap<>() {{
            put(0, GAME_IMAGE_PATH + "key_rusted.png");
            put(1, GAME_IMAGE_PATH + "key_bronze.png");
            put(2, GAME_IMAGE_PATH + "key_silver.png");
            put(3, GAME_IMAGE_PATH + "key_gold.png");
    }};

    private static final HashMap<Integer, Image> IMAGE_CACHE = new HashMap<>();

    private final int itemColour;

    /**
     * Creates an instance of a Lever
     * @param metadata Colour of tile (int)
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

    @Override
    public boolean interact(boolean isPlayer) {
            GameManager.getLevel().openGate(imageIndex);
        return true;
    }

    @Override
    public String getImagePath() {
        return IMAGE_PATH_CACHE.get(imageIndex);
    }

    @Override
    public Image getImage() {
        return IMAGE_CACHE.get(imageIndex);
    }

    public int getItemColour() {
        return itemColour;
    }
}
