package com.group38.prisonbreak.items;

import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.utilities.FileUtilities;
import com.group38.prisonbreak.utilities.Item;
import javafx.scene.image.Image;

import java.util.HashMap;

/**
 * Implements a Gate in the game
 * @author Daniel Banks (2107922)
 */
public class Gate extends Item {
    private final int gateColour;

    private static final HashMap<Integer, String> imagePathCache = new HashMap<>() {{
        put(0, gameImagesStart + "gate_rusted_locked.png");
        put(1, gameImagesStart + "gate_bronze_locked.png");
        put(2, gameImagesStart + "gate_silver_locked.png");
        put(3, gameImagesStart + "gate_gold_locked.png");

    }};

    private static final HashMap<Integer, Image> imageCache = new HashMap<>();

    public Gate(String metadata) {
        gateColour = Integer.parseInt(metadata);
        if (imageCache.isEmpty()) {
            imagePathCache.keySet().forEach(index -> {
                imageCache.put(index, FileUtilities.loadImageFromResource(imagePathCache.get(index)));
            });
        }
    }

    /**
     * @param isPlayer
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
     * @return An imasge that represents whether a gate is open or closed.
     */
    @Override
    public Image getImage() {
        boolean isGateOpen = GameManager.getLevel().isGateOpen(this);
        return imageCache.get(isGateOpen ? null : gateColour);
    }

    public int getGateColour() {
        return gateColour;
    }
}
