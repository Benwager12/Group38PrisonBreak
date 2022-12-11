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

    /** Every image path that bomb ever uses. */
    private static final HashMap<Integer, String> IMAGE_PATH_CACHE =
            new HashMap<>() {{
        put(-1, GAME_IMAGE_PATH + "cracked_tile.png");
        put(0, GAME_IMAGE_PATH + "alarm.png");
        put(1, GAME_IMAGE_PATH + "alarm_1.png");
        put(2, GAME_IMAGE_PATH + "alarm_2.png");
        put(3, GAME_IMAGE_PATH + "alarm_3.png");
    }};

    /** The time that the game time is set to when the bomb explodes. */
    private static final int BOMB_START_TIME = 3;

    /**
     * An image cache, is loaded from everything from the
     * path cache when game is initialized.
     */
    private static final HashMap<Integer, Image> IMAGE_CACHE = new HashMap<>();

    /** A boolean that points out whether the bomb has been exploded. */
    private boolean explodable = true;

    /** A second in millis, used for the creation of the timeline. */
    private final int SECOND_IN_MILLIS = 1000;

    /** The bomb timeline is used for ticking down from 3 to explode. */
    private final Timeline bombTimeLine =
            new Timeline(new KeyFrame(
                    Duration.millis(SECOND_IN_MILLIS), event -> countdownBomb()));

    /**
     * The main bomb, this is only utilised if it is a filler
     * bomb by being next to a real bomb.
     */
    private Bomb mainBomb;

    /**
     * The initialization of the bomb, adds all items to the cache.
     */
    public Bomb() {
        //Implement Constructor
        imageIndex = 0;

        if (IMAGE_CACHE.isEmpty()) {
            IMAGE_PATH_CACHE.keySet().forEach(index -> IMAGE_CACHE.put(
                    index, FileUtilities.loadImageFromResource(
                            IMAGE_PATH_CACHE.get(index))));
        }
    }

    /**
     * This is the initialization of bomb if it is a secondary bomb.
     *
     * @param mainBomb The reference to a main bomb, which would have no "mainBomb" attribute.
     */
    public Bomb(Bomb mainBomb) {
        explodable = false;
        this.mainBomb = mainBomb;
    }

    /**
     * Whether the bomb is explodable.
     *
     * @return Returns true if the bomb is explodable, false otherwise.
     */
    public boolean isExplodable() {
        return explodable;
    }

    /**
     * Checks if the bomb is a player and explodes it if so.
     *
     * @param isPlayer if the entity interacting with the item is the player
     * @return Always true.
     */
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

    /**
     * The method used to count down from 3 to 0 on the bomb.
     */
    private void countdownBomb() {
        if (imageIndex > 1) {
            imageIndex--;
        } else {
            GameManager.getLevel().removeAllItemsExplosion();
            bombTimeLine.stop();
            imageIndex = -1;
        }
    }

    /** Utilised when another bomb explodes. */
    public void immediateExplosion() {
        imageIndex = -1;
    }

    /** Gets the image path of the bomb. */
    @Override
    public String getImagePath() {
        return IMAGE_PATH_CACHE.get(imageIndex);
    }

    /**
     * Gets the current image of the bomb.
     * @return An image of what the bomb currently is.
     */
    @Override
    public Image getImage() {
        return IMAGE_CACHE.get(explodable ? imageIndex : null);
    }
}
