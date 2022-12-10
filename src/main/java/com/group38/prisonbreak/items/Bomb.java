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

    private static final HashMap<Integer, String> imagePathCache = new HashMap<>() {{
        put(-1, gameImagesStart + "cracked_tile.png");
        put(0, gameImagesStart + "alarm.png");
        put(1, gameImagesStart + "alarm_1.png");
        put(2, gameImagesStart + "alarm_2.png");
        put(3, gameImagesStart + "alarm_3.png");
    }};

    private static final HashMap<Integer, Image> imageCache = new HashMap<>();
    private boolean bombActivated;
    private boolean explodable = true;
    private boolean hasExploded = false;

    public Bomb() {
        //Implement Constructor
        imageIndex = 0;

        if (imageCache.isEmpty()) {
            imagePathCache.keySet().forEach(index -> {
                imageCache.put(index, FileUtilities.loadImageFromResource(imagePathCache.get(index)));
            });
        }
    }

    private Bomb mainBomb;

    // If the bomb isn't explodable, then it must have a main bomb
    public Bomb(Bomb mainBomb) {
        explodable = false;
        this.mainBomb = mainBomb;
    }

    public boolean isExplodable() {
        return explodable;
    }


    /* The time left when the player activates the bomb */
    private final int BOMB_ACTIVATE_TIME = 3;
    private Timeline bombTimeLine = new Timeline(new KeyFrame(Duration.millis(1000), event -> countdownBomb()));

    @Override
    public boolean interact(boolean isPlayer) {
        if (!explodable) {
            return mainBomb.interact(isPlayer);
        }

        if (imageIndex == 0) {
            imageIndex = BOMB_ACTIVATE_TIME;
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
        return imagePathCache.get(imageIndex);
    }

    public int getImageIndex() {
        return imageIndex;
    }

    /**
     * @return Ch
     */
    @Override
    public Image getImage() {
        return imageCache.get(explodable ? imageIndex : null);
    }
}
