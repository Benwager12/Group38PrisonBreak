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
 * @author Ben Wager (2108500), Daniel Banks (2107922)
 */
public class GameManager {

    /** ArrayList of currently pressed keys. */
    private static final ArrayList<KeyCode> CURRENTLY_PRESSED =
            new ArrayList<>();

    /** Tick timeline for the smartThief. */
    public static Timeline smartThiefTimeLine;

    /** Tick timeline for the player. */
    public static Timeline playerTimeLine;

    /**
     *  Tick timeline for the main game clock.
     *  updates every 1000 milliseconds (1 second)
     */
    public static Timeline timeTimeLine;

    /** Tick timeline for the entities. */
    public static Timeline enemyTimeLine;

    /** Instance of the level that's currently open. */
    private static Level level;

    /** Amount of money the player has picked up. */
    private static int money;

    /** Time left in the game. */
    private static int time;

    /** Id of the profile that's current playing. */
    private static int currentProfileId;

    /**
     * Sets all the cycles for the tick timelines.
     */
    public static void initTimelines() {
        enemyTimeLine.setCycleCount(Animation.INDEFINITE);
        smartThiefTimeLine.setCycleCount(Animation.INDEFINITE);
        timeTimeLine.setCycleCount(Animation.INDEFINITE);
        playerTimeLine.setCycleCount(Animation.INDEFINITE);
        playTimeLines();
    }

    /**
     * Starts and plays the timelines.
     */
    public static void playTimeLines() {
        enemyTimeLine.play();
        smartThiefTimeLine.play();
        timeTimeLine.play();
    }

    /**
     * Stops the timelines.
     */
    public static void stopTimeLines() {
        if (timeTimeLine != null) {
            enemyTimeLine.stop();
            smartThiefTimeLine.stop();
            playerTimeLine.stop();
            timeTimeLine.stop();
        }

    }

    /**
     * Adds a specific amount of time to the game clock.
     * @param amount The amount of time to be added
     */
    public static void addTime(int amount) {
        time += amount;
    }

    /**
     * removes a specific amount of time to the game clock.
     * @param amount The amount of time to be removed
     */
    public static void removeTime(int amount) {
        time -= amount;
    }

    /**
     * Adds money to the total money collected.
     * @param moneyAmount The amount of money collected by the player
     */
    public static void collectMoney(int moneyAmount) {
        money += moneyAmount;
    }

    /**
     * Calculates the players score in the level.
     * @param money The amount of money collected by the player
     * @param timeRemaining The time remaining in the level
     * @return the players score in the level
     */
    public static int calculateScore(int money, int timeRemaining) {
        return money + timeRemaining;
    }

    /**
     * Ends the level when a level is finished.
     * @param hasWon If the player has won/beaten the level
     */
    public static void endGame(boolean hasWon) {
        stopTimeLines();
        if (hasWon) {
            ProfileUtilities.updateProfile(
                    currentProfileId,
                    level.getLevelNumber()
            );
            LeaderboardUtilities.addNewHighscore(
                    level.getLevelNumber(),
                    currentProfileId,
                    calculateScore(GameManager.money, GameManager.time)
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
    }

    /**
     * Saves the current level and profile data.
     */
    public static void saveLevel() {
        ProfileUtilities.saveProfiles();
        LeaderboardUtilities.saveProfiles();

        if (level != null && level.getPlayer() instanceof Player) {
            SaveLevelUtilities.saveLevel(currentProfileId, level);
        }
    }

    /**
     * Exits the game and saves profile and level data.
     */
    public static void exitGame() {
        saveLevel();
        System.exit(0);
    }

    /**
     * Proccess Key events.
     * @param event key event
     */
    public static void processKeyEvent(KeyEvent event) {
        if (level != null) {
            if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                if (CURRENTLY_PRESSED.contains(event.getCode())) {
                    return;
                }

                CURRENTLY_PRESSED.add(event.getCode());
                if (playerTimeLine.getStatus() != Animation.Status.RUNNING
                        && Constants.MOVEMENT_KEYS.contains(event.getCode())) {

                    playerTimeLine.play();
                }
            }
            if (event.getEventType() == KeyEvent.KEY_RELEASED
                    && Constants.MOVEMENT_KEYS.contains(event.getCode())) {
                CURRENTLY_PRESSED.remove(event.getCode());
                playerTimeLine.pause();

                for (KeyCode kc : CURRENTLY_PRESSED) {
                    if (Constants.MOVEMENT_KEYS.contains(kc)) {
                        playerTimeLine.play();
                    }
                }
            }
        }
    }

    /**
     * Gets the current level.
     * @return level
     */
    public static Level getLevel() {
        return level;
    }

    /**
     * Sets the current level.
     * @param level level
     */
    public static void setLevel(Level level) {
        GameManager.level = level;
    }

    /**
     * Gets the time remaining on the level.
     * @return int Time Remaining
     */
    public static int getTime() {
        return time;
    }

    /**
     * Sets the time on the level to a given amount.
     * @param amount The time the level time should be set to
     */
    public static void setTime(int amount) {
        time = amount;
    }

    /**
     * Gets the current money collected/score.
     * @return int Money
     */
    public static int getMoney() {
        return money;
    }

    /**
     * Sets the money in the level.
     * @param money int money to be added
     */
    public static void setMoney(int money) {
        GameManager.money = money;
    }

    /**
     * Resets the money collected/score.
     */
    public static void resetMoney() {
        GameManager.money = 0;
    }

    /**
     * Gets the profile id of the profile that's currently playing.
     * @return int Id
     */
    public static int getCurrentProfileId() {
        return currentProfileId;
    }

    /**
     * Sets the profile id of the current profile playing.
     * @param currentProfileId int profile id
     */
    public static void setCurrentProfileId(int currentProfileId) {
        GameManager.currentProfileId = currentProfileId;
    }

    /**
     * Gets the currently Pressed keys.
     * @return ArrayList of KeyCodes
     */
    public static ArrayList<KeyCode> getCurrentlyPressed() {
        return CURRENTLY_PRESSED;
    }
}
