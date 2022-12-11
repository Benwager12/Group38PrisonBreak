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
 * and on initialisation updates the leaderboard to the current high scores.
 * @author         ??             and Jennalee Llewellyn (967558)
 */

public class LeaderboardController {

    private final double ORIGINAL_BUTTON_ROTATION = 0;
    private final double MODIFIED_BUTTON_ROTATION = 1.7;

	@FXML
	private ImageView crossImage;

	@FXML
	private Text leaderboardText;

	@FXML
	public void initialize() {
        // animate button on hover detection
		crossImage.hoverProperty().addListener(rotateButton(crossImage));

        //
		leaderboardText.setText(LeaderboardUtilities.showScores(GameManager.getLevel().getLevelNumber()));
	}

    @FXML
    private void homeClicked(MouseEvent actionEvent) {
        FileUtilities.getGameInstance().setRoot("mainMenu");
    }

	@FXML
	private void crossClicked(MouseEvent ignoredClick) {
		FileUtilities.getGameInstance().setRoot("levelMenu");
	}


    /**
     * Rotates button when applicable.
     *
     * @param img the button to be rotated
     * @return rotated/unrotated button depending on situation
     */
    private ChangeListener<Boolean> rotateButton(ImageView img) {
        return (observable, oldValue, newValue) -> {
            if (observable.getValue()) {
                // modify button position
                img.setRotate(MODIFIED_BUTTON_ROTATION);

            } else {
                // maintain original button position
                img.setRotate(ORIGINAL_BUTTON_ROTATION);
            }
        };
    }
}
