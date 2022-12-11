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
 * ask-overwrite-menu.fxml.
 *
 * @author Matthew Salter, Daniel Banks, Ben Wager
 */
public class AskOverwriteController {

    /** The original rotation of the button. */
    private static final double ORIGINAL_BUTTON_ROTATION = 0;

    /** The modified rotation of level. */
    private static final double MODIFIED_BUTTON_ROTATION = 1.7;

    @FXML
    private ImageView homeImage;

    @FXML
    private ImageView crossImage;

    /**
     * [add]
     */
    @FXML
    private void initialize() {
        // animate buttons on hover detection
        homeImage.hoverProperty().addListener(rotateButton(homeImage));
        crossImage.hoverProperty().addListener(rotateButton(crossImage));
    }

    @FXML
    private void homeClicked(MouseEvent ignoredActionEvent) {
        FileUtilities.getGameInstance().setRoot("mainMenu");
    }

    @FXML
    private void crossClicked(MouseEvent click) {
        click.consume();
        GameManager.exitGame();
    }

    @FXML
    private void yesClicked(MouseEvent ignoredActionEvent) {

        //Sets the level to run from a "saved progress level file"
        Level savedLevel = FileUtilities.readLevel(
                GameManager.getCurrentProfileId(),
                GameManager.getLevel().getLevelNumber()
        );

        GameManager.setLevel(savedLevel);
        FileUtilities.getGameInstance().setRoot("loadOverwriteLevel");
    }

    @FXML
    private void noClicked(MouseEvent ignoredActionEvent) {
        FileUtilities.getGameInstance().setRoot("loadOverwriteLevel");
    }



    /**
     * Rotates button when applicable.
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
}
