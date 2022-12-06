package com.group38.prisonbreak.utilities;

import java.util.ArrayList;

/**
 * Implements a specific level's 10 high scores
 * @author Daniel Banks (2107922)
 */
public class LevelLeaderboard {

    // 2D array of scores for this specific level [[profileId, score]]
    private final int[][] scores = new int[10][2];

    /**
     * Creates an instance of a leaderboard with only one score, each score holds [profileId, score]
     * @param score a score of the level
     */
    public LevelLeaderboard(int[] score) {
        scores[0] = score;
    }

    /**
     * Creates an instance of a leaderboard with multiple scores
     * @param scores 2D array of scores, each score holds [profileId, score]
     */
    public LevelLeaderboard(int[][] scores) {
        for (int[] score : scores) {
            addToLeaderboard(score);
        }
    }

    /**
     * Adds a score to the leaderboard in the correct order
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
     * Gets the current high scores of the level
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
     * Gets the profile id and their high scores of the level
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
        for (int i = 0; i < scores.size(); i ++){
            leaderboard[i] = scores.get(i);
        }
        return leaderboard;
    }
}
