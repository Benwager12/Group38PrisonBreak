package com.group38.prisonbreak.controllers;

import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.utilities.FileUtilities;
import com.group38.prisonbreak.utilities.LeaderboardUtilities;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * A controller class for the leaderboard, contains button functions and
 * on initialisation updates the leaderboard to the current high scores.
 * @author Matthew Salter (986488), Jennalee Llewellyn (967558)
 */

public class LeaderboardController {

    /** The original position of the button. */
    private static final double ORIGINAL_BUTTON_ROTATION = 0;

    /** The modified position of the button. */
    private static final double MODIFIED_BUTTON_ROTATION = 1.7;

    /** Fx:id for the menu's exit button image. */
    @FXML
    private ImageView crossImage;

    /** Fx:id for the text box used for the leaderboard information. */
    @FXML
    private Text leaderboardText;

    /**
     * Triggers at the opening of the FXML file and creates listeners
     * along with setting the leaderboard information.
     */
    @FXML
    public void initialize() {
        /* Animate button on hover detection */
        crossImage.hoverProperty().addListener(rotateButton(crossImage));

        /* Collect the scores and assign them to the fx:id to display */
        leaderboardText.setText(
                LeaderboardUtilities.showScores(
                        GameManager.getLevel().getLevelNumber()
                )
        );
    }

    /**
     * On home image clicked redirect to the main menu.
     * @param click trigger on mouse clicked.
     */
    @FXML
    private void homeClicked(MouseEvent click) {
        FileUtilities.getGameInstance().setRoot("mainMenu");
    }

    /**
     * On cross image clicked redirect the level menu.
     * @param click trigger on mouse clicked.
     */
    @FXML
    private void crossClicked(MouseEvent click) {
        FileUtilities.getGameInstance().setRoot("levelMenu");
    }


    /**
     * Rotates button when applicable.
     * @param img the button to be rotated.
     * @return rotated/non-rotated button depending on situation.
     */
    private ChangeListener<Boolean> rotateButton(ImageView img) {
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
}
