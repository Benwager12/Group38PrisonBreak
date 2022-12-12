package com.group38.prisonbreak.controllers;

import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.Level;
import com.group38.prisonbreak.utilities.FileUtilities;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * AskOverwriteController is the controller that handles the
 * Ask-overwrite-menu.fxml.
 * @author Matthew Salter (986488), Daniel Banks (2107922), Ben Wager (2108500)
 */
public class AskOverwriteController {

    /* The original position of the button */
    private static final double ORIGINAL_BUTTON_ROTATION = 0;

    /* The modified position of the button */
    private static final double MODIFIED_BUTTON_ROTATION = 1.7;

    /* Fx:id for the menu's home button image */
    @FXML
    private ImageView homeImage;

    /* Fx:id for the menu's exit button image */
    @FXML
    private ImageView crossImage;

    /**
     * Triggers at the opening of the FXML file and creates listeners
     * for hover properties.
     */
    @FXML
    private void initialize() {
        /* Animate buttons on hover detection */
        homeImage.hoverProperty().addListener(rotateButton(homeImage));
        crossImage.hoverProperty().addListener(rotateButton(crossImage));
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
     * On cross image clicked, exit the game window.
     * @param click trigger on mouse clicked.
     */
    @FXML
    private void crossClicked(MouseEvent click) {
        click.consume();
        GameManager.exitGame();
    }

    /**
     * On 'yes' button clicked, loads the level with saved progress.
     * @param click trigger on mouse clicked.
     */
    @FXML
    private void yesClicked(MouseEvent click) {

        /* Sets the level to run from a "saved progress level file" */
        Level savedLevel = FileUtilities.readLevel(
                GameManager.getCurrentProfileId(),
                GameManager.getLevel().getLevelNumber()
        );

        GameManager.setLevel(savedLevel);
        FileUtilities.getGameInstance().setRoot("loadOverwriteLevel");
    }

    /**
     * On 'no' button clicked, loads the level from scratch.
     * @param click trigger on mouse clicked.
     */
    @FXML
    private void noClicked(MouseEvent click) {
        FileUtilities.getGameInstance().setRoot("loadOverwriteLevel");
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
}
