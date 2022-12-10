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
        put(50, GAME_IMAGE_PATH + "loot_1.png");
        put(100, GAME_IMAGE_PATH + "loot_2.png");
        put(150, GAME_IMAGE_PATH + "loot_3.png");
        put(250, GAME_IMAGE_PATH + "loot_4.png");
    }};

    private static final HashMap<Integer, Image> imageCache = new HashMap<>();

    private final int lootType;

    /**
     * Constructor for Loot class
     * @param metadata Type of loot (each type is an icon)
     */
    public Loot(String metadata) {
        this.lootType = Integer.parseInt(metadata);
        this.imageIndex = lootType;

        if (imageCache.isEmpty()) {
            imagePathCache.keySet().forEach(index ->
                    imageCache.put(index, FileUtilities.loadImageFromResource(imagePathCache.get(index))));
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

    @Override
    public Image getImage() {
        return imageCache.get(imageIndex);
    }

    public int getLootType() {
        return lootType;
    }
}
