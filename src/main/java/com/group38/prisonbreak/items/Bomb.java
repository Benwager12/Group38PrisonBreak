package com.group38.prisonbreak.items;

import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.utilities.FileUtilities;
import com.group38.prisonbreak.utilities.Item;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.util.HashMap;

public class Bomb extends Item {

    private static final HashMap<Integer, String> IMAGE_PATH_CACHE =
            new HashMap<>() {{
        put(-1, GAME_IMAGE_PATH + "cracked_tile.png");
        put(0, GAME_IMAGE_PATH + "alarm.png");
        put(1, GAME_IMAGE_PATH + "alarm_1.png");
        put(2, GAME_IMAGE_PATH + "alarm_2.png");
        put(3, GAME_IMAGE_PATH + "alarm_3.png");
    }};

    private static final int BOMB_START_TIME = 3;

    private static final HashMap<Integer, Image> IMAGE_CACHE = new HashMap<>();
    private boolean explodable = true;
    private boolean hasExploded = false;

    private Timeline bombTimeLine =
            new Timeline(new KeyFrame(
                    Duration.millis(1000), event -> countdownBomb()));

    private Bomb mainBomb;

    public Bomb() {
        //Implement Constructor
        imageIndex = 0;

        if (IMAGE_CACHE.isEmpty()) {
            IMAGE_PATH_CACHE.keySet().forEach(index -> {
                IMAGE_CACHE.put(
                        index, FileUtilities.loadImageFromResource(
                                IMAGE_PATH_CACHE.get(index)));
            });
        }
    }


    // If the bomb isn't explodable, then it must have a main bomb
    public Bomb(Bomb mainBomb) {
        explodable = false;
        this.mainBomb = mainBomb;
    }

    public boolean isExplodable() {
        return explodable;
    }

    @Override
    public boolean interact(boolean isPlayer) {
        if (!explodable) {
            return mainBomb.interact(isPlayer);
        }

        if (imageIndex == 0) {
            /* The time left when the player activates the bomb */
            imageIndex = BOMB_START_TIME;
            bombTimeLine.setCycleCount(Animation.INDEFINITE);
            bombTimeLine.play();
        }

        return true;
    }

    private void countdownBomb() {
        if (imageIndex > 1) {
            imageIndex--;
        } else {
            GameManager.getLevel().removeAllItemsExplosion();
            bombTimeLine.stop();
            imageIndex = -1;
            hasExploded = true;
        }
    }

    public void immediateExplosion() {
        imageIndex = -1;
    }

    @Override
    public String getImagePath() {
        return IMAGE_PATH_CACHE.get(imageIndex);
    }

    public int getImageIndex() {
        return imageIndex;
    }

    /**
     * @return Ch
     */
    @Override
    public Image getImage() {
        return IMAGE_CACHE.get(explodable ? imageIndex : null);
    }
}
