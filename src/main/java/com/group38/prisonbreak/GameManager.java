package com.group38.prisonbreak;

import com.group38.prisonbreak.utilities.Entity;
import com.group38.prisonbreak.utilities.ProfileUtilities;
import com.group38.prisonbreak.utilities.SaveLevelUtilities;
import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

/**
 * A singleton class that holds everything in the level.
 *
 * @author Ben Wager (2108500), Daniel Banks (2107922)
 */
public class GameManager {

    //POTENTIALLY MAKE THESE PRIVATE?

    // Instance of the level that's currently open
    public static Level level;

    // Amount of money the player has picked up
    public static int money;

    // Time left in the game
    public static int time;

    // Tick timeline for the entities
    public static Timeline entityTimeLine;

    // Tick timeline for the smartThief
    public static Timeline smartThiefTimeLine;

    // Tick timeline for the player
    public static Timeline playerTimeLine;

    // Tick timeline for the main game clock, updates every 1000 milliseconds (1 second)
    public static Timeline timeTimeLine;

    public static ArrayList<KeyCode> currentlyPressed = new ArrayList<>();

    private static int currentProfileId;

    /**
     * Sets all the cycles for the tick timelines
     */
    public static void initTimelines() {
        entityTimeLine.setCycleCount(Animation.INDEFINITE);
        smartThiefTimeLine.setCycleCount(Animation.INDEFINITE);
        timeTimeLine.setCycleCount(Animation.INDEFINITE);
        playerTimeLine.setCycleCount(Animation.INDEFINITE);
        playTimeLines();
    }

    /**
     * starts and plays the timelines
     */
    public static void playTimeLines(){
        entityTimeLine.play();
        smartThiefTimeLine.play();
        timeTimeLine.play();
        playerTimeLine.play();
    }

    /**
     * stops the timelines
     */
    public static void stopTimeLines(){
        entityTimeLine.stop();
        smartThiefTimeLine.stop();
        playerTimeLine.stop();
        timeTimeLine.stop();
    }

    /**
     * Adds a specific amount of time to the game clock
     * @param amount The amount of time to be added
     */
    public static void addTime(int amount) {
        time += amount;
    }

    /**
     * removes a specific amount of time to the game clock
     * @param amount The amount of time to be removed
     */
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
     * Ends the game/level
     */
    public static void endGame () {
        ProfileUtilities.saveProfiles();
        // TODO: End the game when the player goes through the door
    }

    public static void exitGame() {
        ProfileUtilities.saveProfiles();
        SaveLevelUtilities.saveLevel(currentProfileId, level);
    }

    public static void processKeyEvent(KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            if (currentlyPressed.contains(event.getCode())) {
                return;
            }

            currentlyPressed.add(event.getCode());
        }
        if (event.getEventType() == KeyEvent.KEY_RELEASED) {
            currentlyPressed.remove(event.getCode());
        }
    }

    public static void saveGame() {
        SaveLevelUtilities.saveLevel(0, level);
    }
}
