package com.group38.prisonbreak.utilities;

import java.util.ArrayList;

/**
 * Implements a specific level's 10 high scores.
 * @author Daniel Banks (2107922)
 */
public class LevelLeaderboard {

    /** Highest number of scores shown on the leaderboard. */
    private static final int MAX_NUMBER_SCORES = 10;

    /** Format of the string to show the current leaderboard if score is not empty. */
    private static final String SHOW_SCORES_STRING_FORMAT = "%d:%s%s%s%d%n";

    /** Format of the string to show the current leaderboard if score is empty. */
    private static final String SHOW_EMPTY_SCORE_STRING_FORMAT = "%d:%n";

    /** Maximum number of spaces before the name in showScores. */
    private static final int MAX_NO_SPACES_BEFORE_NAME = 9;

    /** Maximum number of spaces after the name in showScores. */
    private static final int MAX_NO_SPACES_AFTER_NAME = 0;

    /** Maximum number of characters in a profile name. */
    private static final int MAX_NAME_LENGTH = 14;

    /** 2D array of scores for this specific level [[profileId, score]]. */
    private final int[][] scores = new int[MAX_NUMBER_SCORES][2];

    /**
     * Creates an instance of a leaderboard with only one score, each score holds [profileId, score].
     * @param score a score of the level
     */
    public LevelLeaderboard(int[] score) {
        scores[0] = score;
    }

    /**
     * Creates an instance of a leaderboard with multiple scores.
     * @param scores 2D array of scores, each score holds [profileId, score]
     */
    public LevelLeaderboard(int[][] scores) {
        for (int[] score : scores) {
            addToLeaderboard(score);
        }
    }

    /**
     * Adds a score to the leaderboard in the correct order.
     * Disregards if it doesn't fit in the top 10
     * @param score score to be added ([profileId, score])
     */
    public void addToLeaderboard(int[] score) {
        for (int i = 0; i < scores.length; i++) {
            if (score[1] > scores[i][1]) {
                int[] temp = scores[i];
                scores[i] = score;
                if (i != LeaderboardUtilities.MAX_NUMBER_OF_HIGH_SCORES - 1) {
                    addToLeaderboard(temp);
                }
                return;
            }
        }
    }

    /**
     * Gets the current high scores of the level.
     * @return int[] high scores
     */
    public int[] getLeaderboardScores() {
        ArrayList<Integer> scores = new ArrayList<>();
        for (int[] score : this.scores) {

            // Checking score is not a blank score
            if (score[1] != 0) {
                scores.add(score[1]);
            }
        }
        return scores.stream().mapToInt(i -> i).toArray();
    }

    /**
     * Gets the profile id and their high scores of the level.
     * @return int[][] (holds [profileId, score])
     */
    public int[][] getLeaderboard() {
        ArrayList<int[]> scores = new ArrayList<>();
        for (int[] score : this.scores) {

            // Checking score is not a blank score
            if (score[0] != 0) {
                scores.add(score);
            }
        }

        // Converts Arraylist to int array
        int[][] leaderboard = new int[scores.size()][2];
        for (int i = 0; i < scores.size(); i++) {
            leaderboard[i] = scores.get(i);
        }
        return leaderboard;
    }

    /**
     * Shows the leaderboard details of this current level in a formatted way.
     * @return String of leaderboard values
     */
    public String showScores() {
        StringBuilder output = new StringBuilder();
        String name;
        int noSpaces;
        String repeatedSpacesBeforeName;
        String repeatedSpacesAfterName;
        for (int i = 0;
             i < LeaderboardUtilities.MAX_NUMBER_OF_HIGH_SCORES; i++) {
            if (scores[i][1] != 0) {
                name = ProfileUtilities.getName(scores[i][0]);
                noSpaces = MAX_NO_SPACES_BEFORE_NAME
                        - (String.valueOf(i).length() - 1);
                repeatedSpacesBeforeName =
                        new String(new char[noSpaces])
                                .replace("\0", " ");

                noSpaces = MAX_NAME_LENGTH - name.length()
                        + MAX_NO_SPACES_AFTER_NAME;
                repeatedSpacesAfterName = new String(new char[noSpaces])
                        .replace("\0", " ");

                // add to output String
                output.append(String.format(SHOW_SCORES_STRING_FORMAT,
                        i + 1,
                        repeatedSpacesBeforeName,
                        name,
                        repeatedSpacesAfterName,
                        scores[i][1]));
            } else {
                // add to output String
                output.append(
                        String.format(SHOW_EMPTY_SCORE_STRING_FORMAT, i + 1)
                );
            }
        }
        return output.toString();
    }
}
