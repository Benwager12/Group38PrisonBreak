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
 * NewProfileController is the controller for the New-profile.fxml.
 * @author Jennalee Llewellyn (967558)
 */
public class NewProfileController {

    /* The original rotation of the button. */
    private static final double ORIGINAL_BUTTON_ROTATION = 0;

    /* The modified rotation of the button. */
    private static final double MODIFIED_BUTTON_ROTATION = 1.7;

    /* Fx:id for the image of a house within FXML file. */
    @FXML
    private ImageView homeImage;

    /* Fx:id for the image of a cross within FXML file. */
    @FXML
    private ImageView crossImage;

    /* Fx:id for the text field for the name within FXML file. */
    @FXML
    private TextField enterName;

    /* String to store the profile name */
    private String profileName = null;

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
     * On home image clicked redirect the root window.
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
     * On enter pressed, create a new profile and re-direct the root pane.
     * @param submit the action of pressing the enter key.
     */
    @FXML
    public void onEnter(KeyEvent submit) {
        if (submit.getCode().equals(KeyCode.ENTER)) {
            profileName = enterName.getText();
            ProfileUtilities.addProfile(profileName);
            GameManager.setCurrentProfileId(ProfileUtilities.getNoProfiles());
            FileUtilities.getGameInstance().setRoot("gameIntro");
        }
    }

    public String getProfileName() {
        return profileName;
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
