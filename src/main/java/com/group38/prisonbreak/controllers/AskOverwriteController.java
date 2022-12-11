package com.group38.prisonbreak.controllers;

import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.utilities.FileUtilities;
import com.group38.prisonbreak.utilities.ProfileUtilities;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * NewProfileController handles the...[add]
 */
public class AskOverwriteController {
    private static final double ORIGINAL_BUTTON_ROTATION = 0;
    private static final double MODIFIED_BUTTON_ROTATION = 1.7;

    @FXML
    private ImageView homeImage;

    @FXML
    private ImageView crossImage;

    @FXML
    private ImageView logoImage;

    @FXML
    private ImageView yesButton;

    @FXML
    private ImageView noButton;


    /**
     * [add]
     */
    @FXML
    private void initialize() {
        homeImage.hoverProperty().addListener(rotateButton(homeImage));
        crossImage.hoverProperty().addListener(rotateButton(crossImage));
    }

    @FXML
    private void homeClicked(MouseEvent actionEvent) {
        FileUtilities.getGameInstance().setRoot("mainMenu");
    }

    @FXML
    private void crossClicked(MouseEvent click) {
        click.consume();
        GameManager.exitGame();
    }

    @FXML
    private void yesClicked(MouseEvent actionEvent) {
        //Maybe add functionality to delete a saved level file???

        FileUtilities.getGameInstance().setRoot("loadOverwriteLevel");
    }

    @FXML
    private void noClicked(MouseEvent actionEvent) {
        //Needs to be able to set the level to run from a "saved progress level file"

        FileUtilities.readLevel(GameManager.getCurrentProfileId(), GameManager.getLevel().getLevelNumber());

        //Then runs this which just loads the level FXML
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
                img.setRotate(MODIFIED_BUTTON_ROTATION);
            } else {
                img.setRotate(ORIGINAL_BUTTON_ROTATION);
            }
        };
    }
    

    

}
