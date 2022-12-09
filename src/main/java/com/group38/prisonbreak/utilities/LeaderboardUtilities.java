package com.group38.prisonbreak.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Utility that stores and deals with the leaderboard
 * @author Daniel Banks (2107922)
 */
public class LeaderboardUtilities {

    // Number of HighScores Shown
    public static final int MAX_NUMBER_OF_HIGH_SCORES = 10;

    // Location of the .txt file that stores the leaderboard
    private static final String FILE_LOCATION = "data/Leaderboard.txt";

    // HashMap of level numbers to [profileId, high-score]
    private static final HashMap<Integer, LevelLeaderboard> LEADERBOARD = new HashMap<>();

    // Format of the Leaderboard.txt file "<levelNumber>, "
    private static final String TXT_STRING_FORMAT = "%d  %d %d%n";

    // Format of the string used when showLevel is called
    private static final String SHOW_LEADERBOARD_STRING_FORMAT = "Level %d%n%s";

    /**
     * Initialises all the leaderboards
     */
    public static void initialise(){
        loadLeaderboard();
    }

    /**
     * Loads leaderboards from Leaderboards.txt
     */
    private static void loadLeaderboard() {
        String filePath = FileUtilities.getResourcePath(FILE_LOCATION);

        File file = new File(filePath);

        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("Can't find leaderboards file");
            e.printStackTrace();
            System.exit(-1);
        }
        readInfo(scanner);
        scanner.close();
    }

    /**
     * Reads leaderboard information from a scanner
     * @param in Scanner containing leaderboard information
     */
    private static void readInfo(Scanner in) {
        int currentLevel = -1;
        LevelLeaderboard levelLeaderboard;
        while (in.hasNextLine() && in.hasNextInt()) {
            int levelNumber = in.nextInt();
            int [] score = new int[] {
                    in.nextInt(),
                    in.nextInt()
            };
            if (levelNumber != currentLevel) {
                currentLevel = levelNumber;
                levelLeaderboard = new LevelLeaderboard(score);
                LEADERBOARD.put(levelNumber, levelLeaderboard);
            } else {
                LEADERBOARD.get(levelNumber).addToLeaderboard(score);
            }
        }
    }

    /**
     * Gets the highest scores from a levelNumber in order
     * @param levelNumber Level number
     * @return int[] of the highest scores
     */
    public static int[] getHighestScores(int levelNumber) {
        return LEADERBOARD.get(levelNumber).getLeaderboardScores();
    }

    /**
     * Gets the profile id and scores of the highest scores of a level in order
     * @param levelNumber Level number
     * @return int[][] of the profileId to highest score
     */
    public static int[][] getHighestScoreDetails(int levelNumber) {
        return LEADERBOARD.get(levelNumber).getLeaderboard();
    }

    /**
     * Shows the details of a given level in a formatted way
     * @param levelNumber Level Number
     * @return String of leaderboard values
     */
    public static String showScores(int levelNumber) {
        LevelLeaderboard levelLeaderboard = LEADERBOARD.get(levelNumber);
        return String.format(SHOW_LEADERBOARD_STRING_FORMAT,
                levelNumber,
                levelLeaderboard.showScores());
    }

    /**
     * Adds a new highscore to a level
     * @param levelNumber level number
     * @param profileId profile id of the player
     * @param highestScore high score
     */
    public static void addNewHighscore(int levelNumber, int profileId, int highestScore) {
        int[] score = new int[] {
                profileId,
                highestScore
        };
        LevelLeaderboard levelScores = LEADERBOARD.get(levelNumber);
        if (levelScores == null) {
            LEADERBOARD.put(levelNumber, new LevelLeaderboard(score));
        } else {
            levelScores.addToLeaderboard(score);
        }
    }

    /**
     * Saves the current stored high scores in Leaderboard.txt
     * NOTE: THIS WILL OVERRIDE EVERYTHING THAT'S CURRENTLY IN LEADERBOARD.TXT
     */
    public static void saveProfiles() {
        try {
            PrintWriter myWriter = new PrintWriter(FileUtilities.getResourcePath(FILE_LOCATION));
            System.out.println(FileUtilities.getResourcePath(FILE_LOCATION));

            for (Map.Entry<Integer, LevelLeaderboard> levelHScore : LEADERBOARD.entrySet()) {
                int[][] levelHScoreVaules = levelHScore.getValue().getLeaderboard();
                for (int[] score : levelHScoreVaules) {
                    String profileData = String.format(TXT_STRING_FORMAT, levelHScore.getKey(),
                            score[0],
                            score[1]
                    );
                    myWriter.write(profileData);
                }
            }
            myWriter.close();

        } catch (IOException e) {
            System.out.println("Can't find Profiles file");
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
