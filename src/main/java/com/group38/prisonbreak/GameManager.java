package com.group38.prisonbreak;

import com.group38.prisonbreak.enemies.SmartThief;
import com.group38.prisonbreak.utilities.Entity;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * A singleton class that holds everything in the level.
 *
 * @author Ben Wager (2108500), Daniel Banks (2107922)
 */
public class GameManager {

    // Instance of the level that's currently open
    public static Level level;

    // Amount of money the player has picked up
    public static int money;

    // Time left in the game
    public static int time;

    // Checks if the lever has been pulled
    public static boolean hasLeverBeenPulled = false;

    // Tick timeline for the entities to move every 500 milliseconds
    private static final Timeline entityTimeLine =
            new Timeline(new KeyFrame(Duration.millis(500), event -> moveEntities()));

    // Tick timeline for the smartThief to move every 12500 milliseconds
    private static final Timeline smartThiefTimeLine =
            new Timeline(new KeyFrame(Duration.millis(1250), event -> moveSmartThief()));

    // Tick timeline for the main game clock, updates every 1000 milliseconds (1 second)
    private static final Timeline timeTimeLine =
            new Timeline(new KeyFrame(Duration.millis(1000), event -> changeTime()));

    /**
     * Sets all the cycles for the tick timelines
     */
    public static void startTimelines() {
        entityTimeLine.setCycleCount(Animation.INDEFINITE);
        smartThiefTimeLine.setCycleCount(Animation.INDEFINITE);
        timeTimeLine.setCycleCount(Animation.INDEFINITE);

    }

    /**
     * moves all the entities apart from smart thief
     */
    private static void moveEntities() {
        for (Entity entity : level.getEntities()) {
            if (!(entity instanceof SmartThief)) {
                entity.move();
            }
        }
    }

    /**
     *  moves the smart thief
     */
    private static void moveSmartThief() {
        for (Entity entity : level.getEntities()) {
            if (entity instanceof SmartThief) {
                entity.move();
            }
        }
    }

    /**
     * changes the main game timer
     */
    private static void changeTime() {
        // TODO: add timer
        time--;

        if (time <= 0) {
            // GAME OVER
        }
    }

    public static void addTime(int amount) {
        time += amount;
    }
    public static void removeTime(int amount) {
        time += amount;
    }

    /**
     * Adds money to the total money collected
     * @param moneyAmount The amount of money collected by the player
     */
    public static void collectMoney(int moneyAmount) {
        money += moneyAmount;
    }

    /**
     * Opens the gates that are on the given colour
     * @param colour the colour that the lever is on
     */
    public static void openLever(int colour) {
        // TODO: Get the hash map of gates in level and set the has lever been pulled to true
    }
}
