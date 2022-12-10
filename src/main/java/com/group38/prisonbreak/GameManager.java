package com.group38.prisonbreak;

import com.group38.prisonbreak.entities.Player;
import com.group38.prisonbreak.utilities.*;
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
    public static Timeline enemyTimeLine;

    // Tick timeline for the smartThief
    public static Timeline smartThiefTimeLine;

    // Tick timeline for the player
    public static Timeline playerTimeLine;

    // Tick timeline for the main game clock, updates every 1000 milliseconds (1 second)
    public static Timeline timeTimeLine;

    public static ArrayList<KeyCode> currentlyPressed = new ArrayList<>();

    public static int currentProfileId;

    /**
     * Sets all the cycles for the tick timelines
     */
    public static void initTimelines() {
        enemyTimeLine.setCycleCount(Animation.INDEFINITE);
        smartThiefTimeLine.setCycleCount(Animation.INDEFINITE);
        timeTimeLine.setCycleCount(Animation.INDEFINITE);
        playerTimeLine.setCycleCount(Animation.INDEFINITE);
        playTimeLines();
    }

    /**
     * starts and plays the timelines
     */
    public static void playTimeLines(){
        enemyTimeLine.play();
        smartThiefTimeLine.play();
        timeTimeLine.play();
    }

    /**
     * stops the timelines
     */
    public static void stopTimeLines(){
        enemyTimeLine.stop();
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
     * Sets the time on the level to a given amount
     * @param amount The time the level time should be set to
     */
    public static void setTime(int amount) {
        time = amount;
    }

    /**
     * removes a specific amount of time to the game clock
     * @param amount The amount of time to be removed
     */
    public static void removeTime(int amount) {
        time -= amount;
    }

    /**
     * Adds money to the total money collected
     * @param moneyAmount The amount of money collected by the player
     */
    public static void collectMoney(int moneyAmount) {
        money += moneyAmount;
    }

    /**
     * Calculates the players score in the level
     * @param money The amount of money collected by the player
     * @param timeRemaining The time remaining in the level
     * @return the players score in the level
     */
    public static int calculateScore(int money, int timeRemaining) {
        return money + timeRemaining;
    }

    /**
     * Ends the level when a level is finished
     * @param hasWon If the player has won/beaten the level
     */
    public static void endGame (boolean hasWon) {
        stopTimeLines();
        if (hasWon) {
            ProfileUtilities.updateProfile(currentProfileId, level.getLevelNumber());
            LeaderboardUtilities.addNewHighscore(level.getLevelNumber(), currentProfileId,
                    calculateScore(GameManager.money,GameManager.time)
            );
        }
        System.out.println(hasWon);
        ProfileUtilities.saveProfiles();
        LeaderboardUtilities.saveProfiles();
       // SaveLevelUtilities.saveLevel(currentProfileId, level);

        if (hasWon) {
            FileUtilities.getGameInstance().setRoot("levelWon");
        } else {
            FileUtilities.getGameInstance().setRoot("levelLost");
        }
        // TODO: End the game when the player goes through the door
    }

    /**
     * Exists the game and saves profile and level data
     */
    public static void exitGame() {
        ProfileUtilities.saveProfiles();
        LeaderboardUtilities.saveProfiles();

        if (level != null && level.getPlayer() instanceof Player) {
            SaveLevelUtilities.saveLevel(currentProfileId, level);
        }
        System.exit(0);
    }

    public static void processKeyEvent(KeyEvent event) {
        if (level != null) {
            if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                if (currentlyPressed.contains(event.getCode())) {
                    return;
                }

                currentlyPressed.add(event.getCode());
                if (playerTimeLine.getStatus() != Animation.Status.RUNNING &&
                        Constants.MOVEMENT_KEYS.contains(event.getCode())) {

                    playerTimeLine.play();
                }
            }
            if (event.getEventType() == KeyEvent.KEY_RELEASED &&
                    Constants.MOVEMENT_KEYS.contains(event.getCode())) {
                currentlyPressed.remove(event.getCode());
                playerTimeLine.pause();

                for (KeyCode kc : currentlyPressed) {
                    if (Constants.MOVEMENT_KEYS.contains(kc)) {
                        playerTimeLine.play();
                        break;
                    }
                }
            }
        }
    }

    public static void saveGame() {
        SaveLevelUtilities.saveLevel(0, level);
    }
}
