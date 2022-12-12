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
 * @author Maisha Begum Chowdhury (2114962)
 */
public class LevelMenuController {

	/** The original height of the button. */
	private static final double ORIGINAL_BUTTON_HEIGHT = 41;

	/** The original width of the button. */
	private static final double ORIGINAL_BUTTON_WIDTH = 36;

	/** The modified height of the button. */
	private static final double MODIFIED_BUTTON_HEIGHT = 54;

	/** The modified width of the button. */
	private static final double MODIFIED_BUTTON_WIDTH = 54;

	/** The original position of the button. */
	private static final double ORIGINAL_BUTTON_ROTATION = 0;

	/** The modified position of the button. */
	private static final double MODIFIED_BUTTON_ROTATION = 1.7;

	/** Level number position in the level string e.g. Level1. */
	private static final int LEVEL_NUMBER_START = 5;

	/** End position of the level number in the level string. */
	private static final int LEVEL_NUMBER_END = 6;

	/** Position of the level number in the fxml */
	private static final int LEVEL_NUMBER_FXML = 10;

	/** level 1's button's image. */
	@FXML
	private ImageView level1Button;

	/**
	 * level 3's fxml StackPane
	 * containing its preview graphic, button and overlay.
	 * */
	@FXML
	private StackPane levelStack2;

	/** level 2's button's image. */
	@FXML
	private ImageView level2Button;

	/**
	 * level 3's fxml StackPane
	 * containing its preview graphic, button and overlay.
	 * */
	@FXML
	private StackPane levelStack3;

	/** level 3's button's image. */
	@FXML
	private ImageView level3Button;

	/**
	 * level 4's fxml StackPane
	 * containing its preview graphic, button and overlay.
	 * */
	@FXML
	private StackPane levelStack4;

	/** level 4's button's image. */
	@FXML
	private ImageView level4Button;

	/**
	 * level 5's fxml StackPane
	 * containing its preview graphic, button and overlay.
	 * */
	@FXML
	private StackPane levelStack5;

	/** level 5's button's image. */
	@FXML
	private ImageView level5Button;

	/**
	 * level 6's fxml StackPane
	 * containing its preview graphic, button and overlay.
	 * */
	@FXML
	private StackPane levelStack6;

	/** level 6's button's image. */
	@FXML
	private ImageView level6Button;

	/**
	 * level 7's fxml StackPane
	 * containing its preview graphic, button and overlay.
	 * */
	@FXML
	private StackPane levelStack7;

	/** level 7's button's image. */
	@FXML
	private ImageView level7Button;

	/**
	 * level 7's fxml StackPane
	 * containing its preview graphic, button and overlay.
	 * */
	@FXML
	private StackPane levelStack8;

	/** level 8's button's image. */
	@FXML
	private ImageView level8Button;

	/** Fx:id for the menu's home button image. */
	@FXML
	private ImageView homeButton;

	/** Fx:id for the menu's exit button image. */
	@FXML
	private ImageView exitButton;

	/**
	 * Enlarges button when applicable.
	 * @param img the button to be enlarged.
	 * @return enlarged/usual button depending on situation.
	 */
	private static ChangeListener<Boolean> enlargeButton(ImageView img) {
		return (observable, oldValue, newValue) -> {
			if (observable.getValue()) {
				/* Scale up button */
				img.setFitHeight(MODIFIED_BUTTON_HEIGHT);
				img.setFitWidth(MODIFIED_BUTTON_WIDTH);

			} else {
				/* Maintain original dimensions of button */
				img.setFitHeight(ORIGINAL_BUTTON_HEIGHT);
				img.setFitWidth(ORIGINAL_BUTTON_WIDTH);
			}
		};
	}

	/**
	 * Rotates button when applicable.
	 * @param img the button to be rotated.
	 * @return rotated/non-rotated button depending on situation.
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
	 * Sets overlays such that levels not yet unlocked are disabled for selection.
	 * @param levelPanes the levels to be displayed in the menu.
	 */
	private static void setOverlay(StackPane[] levelPanes) {
		int highestLevel =
				ProfileUtilities.getLevelFromProfile(getCurrentProfileId());

		for (StackPane lp : levelPanes) {
			String levelPaneID = lp.getId();

			/* Extract corresponding level number from fx:id */
			int levelStackNumber =
					Integer.parseInt(levelPaneID.substring(LEVEL_NUMBER_FXML));

			/* Remove overlay when player has already completed level */
			if (levelStackNumber <= highestLevel + 1) {
				lp.getChildren().remove(2);
			}
		}
	}

	/**
	 * Triggers at the opening of the FXML file and creates listeners
	 * and animations for buttons along with creating the overlays for
	 * locked levels.
	 */
	@FXML
	private void initialize() {
		/* Animate buttons (as specified) on hover detection */
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

		/* Overlay setting */
		StackPane[] levelPanes = {
				levelStack2, levelStack3,
				levelStack4, levelStack5,
				levelStack6, levelStack7,
				levelStack8 };
		setOverlay(levelPanes);

	}

	/**
	 * On home image clicked, redirect the root window.
	 * @param ignoredMouseEvent trigger on mouse clicked.
	 */
	@FXML
	private void homeClicked(MouseEvent ignoredMouseEvent) {
		FileUtilities.getGameInstance().setRoot("mainMenu");
	}

	/**
	 * On cross image clicked, exit the game window.
	 * @param click trigger on mouse clicked.
	 */
	@FXML
	private void exitClicked(MouseEvent click) {
		click.consume();
		GameManager.exitGame();
	}

	/**
	 * On level selected, redirects to appropriate level.
	 * @param mouseEvent Mouse event when clicked, is used to get source.
	 */
	@FXML
	private void levelSelected(MouseEvent mouseEvent) {
		if (!(mouseEvent.getSource() instanceof ImageView iv)) {
			return;
		}
		/* Identify level chosen */
		String buttonId = iv.getId();
		String levelNumber = buttonId.substring(LEVEL_NUMBER_START,
				buttonId.length() - LEVEL_NUMBER_END);

		int intLevelNumber = Integer.parseInt(levelNumber);
		int profileId = GameManager.getCurrentProfileId();
		GameManager.resetMoney();

		/* Start game on chosen level after checking for an existing save */
		if (SaveLevelUtilities.doesSaveFileExist(profileId, intLevelNumber)) {
			GameManager.setLevel(FileUtilities.readLevel(intLevelNumber));
			GameManager.stopTimeLines();
			FileUtilities.getGameInstance().setRoot("overwriteMenu");
		} else {
			FileUtilities.getGameInstance().setRoot("loadLevel" + levelNumber);
		}
	}
}
