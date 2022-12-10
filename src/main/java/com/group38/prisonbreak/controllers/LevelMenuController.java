package com.group38.prisonbreak.controllers;

import com.group38.prisonbreak.utilities.FileUtilities;
import com.group38.prisonbreak.utilities.ProfileUtilities;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import static com.group38.prisonbreak.GameManager.getCurrentProfileId;

/**
 * LevelMenuController handles...[add]
 */
public class LevelMenuController {
	private static final double ORIGINAL_BUTTON_HEIGHT = 41;
	private static final double ORIGINAL_BUTTON_WIDTH = 36;
	private static final double MODIFIED_BUTTON_HEIGHT = 54;
	private static final double MODIFIED_BUTTON_WIDTH = 54;
	private static final double ORIGINAL_BUTTON_ROTATION = 0;
	private static final double MODIFIED_BUTTON_ROTATION = 1.7;

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
		// button animation
		ImageView[] buttons = new ImageView[]{level1Button, level2Button, level3Button,
				level4Button, level5Button, level6Button, level7Button, level8Button};

		for (ImageView b : buttons) {
			b.hoverProperty().addListener(enlargeButton(b));
		}

		homeButton.hoverProperty().addListener(rotateButton(homeButton));
		exitButton.hoverProperty().addListener(rotateButton(exitButton));

		// overlay levels that have not been unlocked [CHANGE]
		StackPane[] levelPanes = {levelStack2, levelStack3, levelStack4, levelStack5,
				levelStack6, levelStack7, levelStack8};
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
		FileUtilities.getGameInstance().setRoot("loadLevel" + levelNumber);
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
				img.setFitHeight(MODIFIED_BUTTON_HEIGHT);
				img.setFitWidth(MODIFIED_BUTTON_WIDTH);
			} else {
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
				img.setRotate(MODIFIED_BUTTON_ROTATION);
			} else {
				img.setRotate(ORIGINAL_BUTTON_ROTATION);
			}
		};
	}

	/**
	 * [FIX]
	 * Adds an overlay to locked levels to disable its selection.
	 *
	 * @param levelPanes the set of level overlays
	 */
	private static void setOverlay(StackPane[] levelPanes) {
		int highestLevel = ProfileUtilities.getLevelFromProfile(getCurrentProfileId());

		for (StackPane lp : levelPanes) {
			String levelPaneID = lp.getId();
			int levelStackNumber = Integer.parseInt(levelPaneID.substring(10));

			// remove overlay child
			if (levelStackNumber <= highestLevel + 1) {
				lp.getChildren().remove(2);
			}
		}
	}
}
