package com.group38.prisonbreak.items;

import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.utilities.FileUtilities;
import com.group38.prisonbreak.utilities.Item;
import javafx.scene.image.Image;

import java.util.HashMap;

/**
 * A class that represents loot in the game
 * @author Daniel Banks (2107922)
 */

public class Loot extends Item {

    private static final HashMap<Integer, String> imagePathCache = new HashMap<>() {{
        put(50, gameImagesStart + "loot_1.png");
        put(100, gameImagesStart + "loot_2.png");
        put(150, gameImagesStart + "loot_3.png");
        put(250, gameImagesStart + "loot_4.png");
    }};

    private static final HashMap<Integer, Image> imageCache = new HashMap<>();

    /**
     * Constructor for Loot class
     * @param metadata
     */
    public Loot(String metadata) {
        this.imageIndex = Integer.parseInt(metadata);

        if (imageCache.isEmpty()) {
            imagePathCache.keySet().forEach(index -> {
                imageCache.put(index, FileUtilities.loadImageFromResource(imagePathCache.get(index)));
            });
        }
    }

    /**
     * adds money to the game iff the entity is a player
     * @param isPlayer Is the entity a player
     */
    @Override
    public boolean interact(boolean isPlayer) {
        if (isPlayer) {
            GameManager.collectMoney(imageIndex);
        }
        return true;
    }

    @Override
    public String getImagePath() {
        return "images/items/loot.png";
    }

    /**
     * @return
     */
    @Override
    public Image getImage() {
        return imageCache.get(imageIndex);
    }

}
