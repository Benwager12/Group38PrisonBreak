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


    private static final HashMap<Integer, String> imagePathCache = new HashMap<>() {{
        put(0, gameImagesStart + "key_rusted.png");
        put(1, gameImagesStart + "key_bronze.png");
        put(2, gameImagesStart + "key_silver.png");
        put(3, gameImagesStart + "key_gold.png");
    }};

    private static final HashMap<Integer, Image> imageCache = new HashMap<>();


    /**
     * Creates an instance of a Lever
     * @param metadata Colour of tile (int)
     */
    public Lever(String metadata) {
        imageIndex = Integer.parseInt(metadata);

        if (imageCache.isEmpty()) {
            imagePathCache.keySet().forEach(index -> {
                imageCache.put(index, FileUtilities.loadImageFromResource(imagePathCache.get(index)));
            });
        }
    }

    @Override
    public boolean interact(boolean isPlayer) {
        if (isPlayer) {
            GameManager.level.openGate(imageIndex);
        }
        return true;
    }

    @Override
    public String getImagePath() {
        return imagePathCache.get(imageIndex);
    }

    @Override
    public Image getImage() {
        return imageCache.get(imageIndex);
    }
}
