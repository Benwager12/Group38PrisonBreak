package com.group38.prisonbreak;

import com.group38.prisonbreak.enemies.SmartThief;
import com.group38.prisonbreak.utilities.Entity;
import com.group38.prisonbreak.utilities.Tile;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * A singleton class that holds everything in the level.
 *
 * @author Ben Wager (2108500)
 */
public class GameManager {

    public static Level level;

    public static int money;

    public static int time;

    private static final Timeline entityTimeLine = new Timeline(new KeyFrame(Duration.millis(500), event -> moveEntities()));
    private static final Timeline smartThiefTimeLine = new Timeline(new KeyFrame(Duration.millis(1250), event -> moveSmartThief()));

    public static void startTimelines() {
        entityTimeLine.setCycleCount(Animation.INDEFINITE);
        smartThiefTimeLine.setCycleCount(Animation.INDEFINITE);
    }

    private static void moveEntities() {
        for (Entity entity : level.getEntities()) {
            if (!(entity instanceof SmartThief)) {
                entity.move();
            }
        }
    }

    private static void moveSmartThief() {
        for (Entity entity : level.getEntities()) {
            if (entity instanceof SmartThief) {
                entity.move();
            }
        }
    }
}
