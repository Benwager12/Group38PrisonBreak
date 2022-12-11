package com.group38.prisonbreak.items;

import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.utilities.FileUtilities;
import com.group38.prisonbreak.utilities.Item;
import javafx.scene.image.Image;

import java.util.HashMap;

/**
 * Implements a Gate in the game.
 * @author Daniel Banks (2107922)
 */
public class Gate extends Item {

    /** A list of all the paths that gate would use. */
    private static final HashMap<Integer, String> IMAGE_PATH_CACHE =
            new HashMap<>() {{
                put(0, GAME_IMAGE_PATH + "gate_rusted_locked.png");
                put(1, GAME_IMAGE_PATH + "gate_bronze_locked.png");
                put(2, GAME_IMAGE_PATH + "gate_silver_locked.png");
                put(3, GAME_IMAGE_PATH + "gate_gold_locked.png");

            }};

    /** The image cache that will store all images from IMAGE_PATH_CACHE. */
    private static final HashMap<Integer, Image> IMAGE_CACHE = new HashMap<>();

    /** The integer representing the gate colour. */
    private final int gateColour;

    /**
     * The initialization of Gate and the loading of the images.
     * @param metadata The gate colour stored in the form of metadata.
     */
    public Gate(String metadata) {
        gateColour = Integer.parseInt(metadata);
        if (IMAGE_CACHE.isEmpty()) {
            IMAGE_PATH_CACHE.keySet().forEach(index -> {
                IMAGE_CACHE.put(index, FileUtilities.loadImageFromResource(
                                IMAGE_PATH_CACHE.get(index)
                        )
                );
            });
        }
    }

    /**
     * @param isPlayer Whether the interacted person is a player.
     * @return Always returns true.
     */
    @Override
    public boolean interact(boolean isPlayer) {
        return true;
    }

    /**
     * Gets the image path of this specific image.
     * @return The image path of this specific image as a string.
     */
    @Override
    public String getImagePath() {
        return "images/items/gate.png";
    }

    /**
     * Specify which gate is being used and whether the gate is open or closed.
     *
     * @return An image that represents whether a gate is open or closed.
     */
    @Override
    public Image getImage() {
        boolean isGateOpen = GameManager.getLevel().isGateOpen(this);
        return IMAGE_CACHE.get(isGateOpen ? null : gateColour);
    }

    /**
     * Get the gate colour.
     *
     * @return The gate colour in the form of an integer.
     */
    public int getGateColour() {
        return gateColour;
    }
}
