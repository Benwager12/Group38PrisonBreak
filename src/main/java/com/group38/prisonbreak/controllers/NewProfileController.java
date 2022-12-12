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

    /* The original position of the button */
    private static final double ORIGINAL_BUTTON_ROTATION = 0;

    /* The modified position of the button */
    private static final double MODIFIED_BUTTON_ROTATION = 1.7;

    /* Fx:id for the menu's home button image  */
    @FXML
    private ImageView homeImage;

    /* Fx:id for the menu's exit button image */
    @FXML
    private ImageView crossImage;

    /* Fx:id for the menu's name text field */
    @FXML
    private TextField enterName;

    /**
     * On enter pressed, create a new profile and re-direct to the game introduction.
     * @param submit The action of pressing the enter key.
     */
    @FXML
    public void onEnter(KeyEvent submit) {
        if (submit.getCode().equals(KeyCode.ENTER)) {
            /* Stores the profile name */
            String profileName = enterName.getText();
            ProfileUtilities.addProfile(profileName);
            GameManager.setCurrentProfileId(ProfileUtilities.getNoProfiles());
            FileUtilities.getGameInstance().setRoot("gameIntro");
        }
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
     * On home image clicked, redirect the root window.
     * @param ignoredClick Trigger on mouse clicked.
     */
    @FXML
    private void homeClicked(MouseEvent ignoredClick) {
        FileUtilities.getGameInstance().setRoot("mainMenu");
    }

    /**
     * On cross image clicked, exit the game window.
     * @param click Trigger on mouse clicked.
     */
    @FXML
    private void crossClicked(MouseEvent click) {
        click.consume();
        GameManager.exitGame();
    }
}
