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
    private final int gateColour;

    private static final HashMap<Integer, String> IMAGE_PATH_CACHE =
            new HashMap<>() {{
            put(0, GAME_IMAGE_PATH + "gate_rusted_locked.png");
            put(1, GAME_IMAGE_PATH + "gate_bronze_locked.png");
            put(2, GAME_IMAGE_PATH + "gate_silver_locked.png");
            put(3, GAME_IMAGE_PATH + "gate_gold_locked.png");

    }};

    private static final HashMap<Integer, Image> IMAGE_CACHE = new HashMap<>();

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
     * @return
     */
    @Override
    public boolean interact(boolean isPlayer) {
        return true;
    }

    @Override
    public String getImagePath() {
        return "images/items/gate.png";
    }

    /**
     * @return An image that represents whether a gate is open or closed.
     */
    @Override
    public Image getImage() {
        boolean isGateOpen = GameManager.getLevel().isGateOpen(this);
        return IMAGE_CACHE.get(isGateOpen ? null : gateColour);
    }

    public int getGateColour() {
        return gateColour;
    }
}
