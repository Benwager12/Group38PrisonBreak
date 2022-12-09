package com.group38.prisonbreak.controllers;

import com.group38.prisonbreak.utilities.FileUtilities;
import com.group38.prisonbreak.utilities.LeaderboardUtilities;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class LeaderboardController {

	private static final double ORIGINAL_BUTTON_ROTATION = 0;
	private static final double MODIFIED_BUTTON_ROTATION = 1.7;


	@FXML
	private ImageView crossImage;

	@FXML
	private Text leaderboardText;


	@FXML
	public void initialize() {
		crossImage.hoverProperty().addListener(rotateButton(crossImage));
		leaderboardText.setText(LeaderboardUtilities.showScores(1));
	}

	@FXML
	private void homeClicked(MouseEvent ignoredActionEvent) {
		FileUtilities.getGameInstance().setRoot("mainMenu");
	}

	@FXML
	private void crossClicked(MouseEvent ignoredClick) {
		FileUtilities.getGameInstance().setRoot("mainMenu"); //Needs to go to level menu instead
	}


	/**
	 * Rotates button when applicable.
	 *
	 * @param img the button to be rotated
	 * @return rotated/unrotated button depending on situation
	 */
	private static ChangeListener<Boolean> rotateButton(ImageView img) {
		return (observable, oldValue, newValue) -> {
			if (observable.getValue()) {
				img.setRotate(MODIFIED_BUTTON_ROTATION);
			} else {
				img.setRotate(ORIGINAL_BUTTON_ROTATION);
			}
		};
	}


}
