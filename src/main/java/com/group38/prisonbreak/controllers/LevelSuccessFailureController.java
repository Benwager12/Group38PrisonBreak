package com.group38.prisonbreak.controllers;

import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.utilities.FileUtilities;
import com.group38.prisonbreak.utilities.ProfileUtilities;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 * LevelSuccessFailure is the controller that handles both the
 * Level-passed.fxml and the Level-failed.fxml.
 * @author Tiffany Oamen (2141570), Matthew Salter (986488)
 */
public class LevelSuccessFailureController {

    /** The original position of the button. */
    private static final double ORIGINAL_BUTTON_ROTATION = 0;

    /** The modified position of the button. */
    private static final double MODIFIED_BUTTON_ROTATION = 1.7;

    /** The amount of levels. */
    private static final int NUMBER_OF_LEVELS = 8;

    /** Fx:id for the menu's home button image. */
    @FXML
    private ImageView homeImage;

    /** Fx:id for the menu's exit button image. */
    @FXML
    private ImageView crossImage;

    /** Fx:id for the menu's next level button image. */
    @FXML
    private ImageView nextLevelButton;

    /** Fx:id for the menu's retry button image. */
    @FXML
    private ImageView retryArrow;

    /** Fx:id for the menu's profile name text box. */
    @FXML
    private Label profileNameText;

    /** Fx:id for the menu's player score text box. */
    @FXML
    private Label playerScoreText;

    /** Fx:id for the Hbox that is the parent of the next level button. */
    @FXML
    private HBox nextLevelHbox;

    /**
     * Triggers at the opening of the FXML files and sets the text
     * and listeners for hover properties.
     */
    @FXML
    public void initialize() {
        profileNameText.setText(ProfileUtilities
                .getName(GameManager.getCurrentProfileId()));
        playerScoreText.setText(Integer.toString(GameManager
                .calculateScore(
                        GameManager.getMoney(),
                        GameManager.getTime()))
        );

        /* Removes the next level button if player has completed the last level */
        if (GameManager.getLevel().getLevelNumber() == NUMBER_OF_LEVELS
                && nextLevelHbox != null) {
            nextLevelButton.setVisible(false);
        }
        /* Animate buttons on hover detection */
        homeImage.hoverProperty().addListener(rotateButton(homeImage));
        crossImage.hoverProperty().addListener(rotateButton(crossImage));
        retryArrow.hoverProperty().addListener(rotateButton(retryArrow));
    }

    /**
     * Rotates button when applicable.
     * @param img The button to be rotated.
     * @return Rotated/non-rotated button depending on situation.
     */
    private static ChangeListener<Boolean> rotateButton(ImageView img) {
        return (observable, oldValue, newValue) -> {
            if (observable.getValue()) {
                /* Modify button position */
                img.setRotate(MODIFIED_BUTTON_ROTATION);

            } else {
                /* Maintain original button position */
                img.setRotate(ORIGINAL_BUTTON_ROTATION);
            }
        };
    }

    /**
     * On home image clicked, redirect the root window.
     * @param ignoredClick Trigger on mouse clicked.
     */
    @FXML
    private void homeClicked(MouseEvent ignoredClick) {
        FileUtilities.getGameInstance().setRoot("mainMenu");
    }

    /**
     * On cross image clicked, exit the game window.
     * @param click Trigger on mouse clicked.
     */
    @FXML
    private void crossClicked(MouseEvent click) {
        click.consume();
        GameManager.exitGame();
    }

    /**
     * Restart the level by re-directing the pane to the current root.
     * @param ignoredClick Triggered on mouse event.
     */
    @FXML
    private void retryLevelClicked(MouseEvent ignoredClick) {
        String currentLevel = Integer.toString(
                (GameManager.getLevel().getLevelNumber())
        );
        GameManager.resetMoney();
        FileUtilities.getGameInstance().setRoot("loadLevel" + currentLevel);
    }

    /**
     * Update the root pane to the new level.
     * @param ignoredClick Triggered on mouse event.
     */
    @FXML
    private void goToNextLevel(MouseEvent ignoredClick) {
        String nextLevel = Integer.toString(
                (GameManager.getLevel().getLevelNumber() + 1));
        GameManager.resetMoney();
        FileUtilities.getGameInstance().setRoot("loadLevel" + nextLevel);
    }

    /**
     * When the leaderboard is clicked, re-direct to the leaderboard.
     * @param ignoredClick Triggered on mouse event.
     */
    @FXML
    private void leaderboardClicked(MouseEvent ignoredClick) {
        FileUtilities.getGameInstance().setRoot("leaderboard");
    }
}
