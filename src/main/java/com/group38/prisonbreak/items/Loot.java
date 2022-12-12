package com.group38.prisonbreak.items;

import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.utilities.FileUtilities;
import com.group38.prisonbreak.utilities.Item;
import javafx.scene.image.Image;

import java.util.HashMap;

/**
 * A class that represents loot in the game.
 * @author Daniel Banks (2107922), Ben Wager (2108500)
 */

public class Loot extends Item {

    /** The pricing for the fourth type of loot. */
    public static final int LOOT_AMOUNT_FOUR = 250;
    /** The pricing for the first type of loot. */
    private static final int LOOT_AMOUNT_ONE = 50;
    /** The pricing for the second type of loot. */
    private static final int LOOT_AMOUNT_TWO = 100;
    /** The pricing for the third type of loot. */
    private static final int LOOT_AMOUNT_THREE = 150;
    /** A list of all the paths that loot would use. */
    private static final HashMap<Integer, String> IMAGE_PATH_CACHE =
            new HashMap<>() {{
            put(LOOT_AMOUNT_ONE, GAME_IMAGE_PATH + "loot_1.png");
            put(LOOT_AMOUNT_TWO, GAME_IMAGE_PATH + "loot_2.png");
            put(LOOT_AMOUNT_THREE, GAME_IMAGE_PATH + "loot_3.png");
            put(LOOT_AMOUNT_FOUR, GAME_IMAGE_PATH + "loot_4.png");
    }};

    /** The image cache that will store all images from IMAGE_PATH_CACHE. */
    private static final HashMap<Integer, Image> IMAGE_CACHE = new HashMap<>();

    /** The type of loot that this particular item is. */
    private final int lootType;

    /**
     * Constructor for Loot class.
     * @param metadata Type of loot (each type is an icon)
     */
    public Loot(String metadata) {
        this.lootType = Integer.parseInt(metadata);
        this.imageIndex = lootType;

        if (IMAGE_CACHE.isEmpty()) {
            IMAGE_PATH_CACHE.keySet().forEach(index ->
                    IMAGE_CACHE.put(index, FileUtilities.loadImageFromResource(
                            IMAGE_PATH_CACHE.get(index))
                    )
            );
        }
    }

    /**
     * Adds money to the game iff the entity is a player.
     * @param isPlayer Is the entity a player
     */
    @Override
    public boolean interact(boolean isPlayer) {
        if (isPlayer) {
            GameManager.collectMoney(imageIndex);
        }
        return true;
    }

    /**
     * The path for the image for the particular loot index.
     * @return The current used image for the loot.
     */
    @Override
    public String getImagePath() {
        return IMAGE_PATH_CACHE.get(imageIndex);
    }

    /**
     * Get the image for the particular type of loot being used.
     * @return The image for the loot in the form of an image.
     */
    @Override
    public Image getImage() {
        return IMAGE_CACHE.get(imageIndex);
    }

    /**
     * Get the type of loot that this is.
     * @return Return the pricing of the loot as an integer.
     */
    public int getLootType() {
        return lootType;
    }
}
