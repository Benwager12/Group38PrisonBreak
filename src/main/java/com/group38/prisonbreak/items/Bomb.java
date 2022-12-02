package com.group38.prisonbreak.items;

import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.utilities.FileUtilities;
import com.group38.prisonbreak.utilities.Item;
import javafx.scene.image.Image;

import java.util.HashMap;

public class Bomb extends Item {

    private static final HashMap<Integer, String> imagePathCache = new HashMap<>() {{
        put(0, gameImagesStart + "alarm.png");
        put(1, gameImagesStart + "alarm_1.png");
        put(2, gameImagesStart + "alarm_2.png");
        put(3, gameImagesStart + "alarm_3.png");
    }};

    private static final HashMap<Integer, Image> imageCache = new HashMap<>();
    private boolean bombActivated;

    public Bomb() {
        //Implement Constructor
        imageIndex = 0;

        if (imageCache.isEmpty()) {
            imagePathCache.keySet().forEach(index -> {
                imageCache.put(index, FileUtilities.loadImageFromResource(imagePathCache.get(index)));
            });
        }
    }

    /* The time left when the player activates the bomb */
    private final int BOMB_ACTIVATE_TIME = 3;

    @Override
    public boolean interact(boolean isPlayer) {
        if (GameManager.time > 3) {
            GameManager.time = BOMB_ACTIVATE_TIME;
            imageIndex = BOMB_ACTIVATE_TIME;
        }
        return false;
    }

    @Override
    public String getImagePath() {
        return imagePathCache.get(imageIndex);
    }

    /**
     * @return Ch
     */
    @Override
    public Image getImage() {
        return imageCache.get(imageIndex);
    }
}
