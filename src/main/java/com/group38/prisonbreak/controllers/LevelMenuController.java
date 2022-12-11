package com.group38.prisonbreak.controllers;

import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.utilities.FileUtilities;
import com.group38.prisonbreak.utilities.ProfileUtilities;
import com.group38.prisonbreak.utilities.SaveLevelUtilities;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import static com.group38.prisonbreak.GameManager.getCurrentProfileId;

/**
 * LevelMenuController is responsible for animating level menu buttons,
 * directing button clicks, and displaying levels correctly i.e.
 * locked or unlocked during level selection.
 *
 * @author Maisha Begum Chowdhury (2114962)
 */
public class LevelMenuController {
	private static final double ORIGINAL_BUTTON_HEIGHT = 41;
	private static final double ORIGINAL_BUTTON_WIDTH = 36;
	private static final double MODIFIED_BUTTON_HEIGHT = 54;
	private static final double MODIFIED_BUTTON_WIDTH = 54;
	private static final double ORIGINAL_BUTTON_ROTATION = 0;
	private static final double MODIFIED_BUTTON_ROTATION = 1.7;

	/* levelStack corresponds to the respective level's fxml StackPane
	 * which contains its preview graphic, button and overlay
	 */

	@FXML
	private ImageView level1Button;

	@FXML
	private StackPane levelStack2;

	@FXML
	private ImageView level2Button;

	@FXML
	private StackPane levelStack3;

	@FXML
	private ImageView level3Button;

	@FXML
	private StackPane levelStack4;

	@FXML
	private ImageView level4Button;

	@FXML
	private StackPane levelStack5;

	@FXML
	private ImageView level5Button;

	@FXML
	private StackPane levelStack6;

	@FXML
	private ImageView level6Button;

	@FXML
	private StackPane levelStack7;

	@FXML
	private ImageView level7Button;

	@FXML
	private StackPane levelStack8;

	@FXML
	private ImageView level8Button;

	@FXML
	private ImageView homeButton;

	@FXML
	private ImageView exitButton;

	@FXML
	private void initialize() {
		// animate buttons (as specified) on hover detection
		ImageView[] buttons = new ImageView[] {
				level1Button, level2Button,
				level3Button, level4Button,
				level5Button, level6Button,
				level7Button, level8Button };

		for (ImageView b : buttons) {
			b.hoverProperty().addListener(enlargeButton(b));
		}

		homeButton.hoverProperty().addListener(rotateButton(homeButton));
		exitButton.hoverProperty().addListener(rotateButton(exitButton));

		// overlay setting
		StackPane[] levelPanes = {
				levelStack2, levelStack3,
				levelStack4, levelStack5,
				levelStack6, levelStack7,
				levelStack8 };
		setOverlay(levelPanes);

	}

	@FXML
	private void homeClicked(MouseEvent ignoredMouseEvent) {
		FileUtilities.getGameInstance().setRoot("mainMenu");
	}

	@FXML
	private void exitClicked(MouseEvent e) {
		e.consume();
		System.exit(0);
	}

	/**
	 * [add]
	 *
	 * @param mouseEvent Mouse event when clicked, is used to get source.
	 */
	@FXML
	private void levelSelected(MouseEvent mouseEvent) {
		if (!(mouseEvent.getSource() instanceof ImageView iv)) {
			return;
		}
		String buttonId = iv.getId();
		String levelNumber = buttonId.substring(5, buttonId.length() - 6);

		int intLevelNumber = Integer.parseInt(levelNumber);
		int profileId = GameManager.getCurrentProfileId();

		if (SaveLevelUtilities.doesSaveFileExist(profileId, intLevelNumber)) {
			GameManager.setLevel(FileUtilities.readLevel(intLevelNumber));
			System.out.println("save");
			FileUtilities.getGameInstance().setRoot("overwriteMenu");
		} else {
			FileUtilities.getGameInstance().setRoot("loadLevel" + levelNumber);
		}

		//Add functionality to check if the current profile has a save for the selected level number

		//If they don't have save needs to do this
		//FileUtilities.getGameInstance().setRoot("loadLevel" + levelNumber);

		//If they do have save needs to do this
	//	GameManager.setLevel(FileUtilities.readLevel(Integer.parseInt(levelNumber)));
	//	FileUtilities.getGameInstance().setRoot("overwriteMenu");
	}

	/**
	 * Enlarges button when applicable.
	 *
	 * @param img the button to be enlarged
	 * @return enlarged/usual button depending on situation
	 */
	private static ChangeListener<Boolean> enlargeButton(ImageView img) {
		return (observable, oldValue, newValue) -> {
			if (observable.getValue()) {
				// scale up button
				img.setFitHeight(MODIFIED_BUTTON_HEIGHT);
				img.setFitWidth(MODIFIED_BUTTON_WIDTH);

			} else {
				// maintain original dimensions of button
				img.setFitHeight(ORIGINAL_BUTTON_HEIGHT);
				img.setFitWidth(ORIGINAL_BUTTON_WIDTH);
			}
		};
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
				// modify button position
				img.setRotate(MODIFIED_BUTTON_ROTATION);

			} else {
				// maintain original button position
				img.setRotate(ORIGINAL_BUTTON_ROTATION);
			}
		};
	}

	/**
	 * Sets overlays such that levels not yet unlocked are disabled for selection.
	 *
	 * @param levelPanes the levels to be displayed in the menu
	 */
	private static void setOverlay(StackPane[] levelPanes) {
		int highestLevel =
				ProfileUtilities.getLevelFromProfile(getCurrentProfileId());

		for (StackPane lp : levelPanes) {
			String levelPaneID = lp.getId();

			// extract corresponding level number from fxid
			int levelStackNumber = Integer.parseInt(levelPaneID.substring(10));

			// remove overlay when player has already completed level
			if (levelStackNumber <= highestLevel + 1) {
				lp.getChildren().remove(2);
			}
		}
	}
}
