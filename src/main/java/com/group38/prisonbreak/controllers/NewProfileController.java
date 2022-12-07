package com.group38.prisonbreak.controllers;

import com.group38.prisonbreak.utilities.FileUtilities;
import com.group38.prisonbreak.utilities.ProfileUtilities;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * NewProfileController handles the...[add]
 */
public class NewProfileController {
    private static final double ORIGINAL_BUTTON_ROTATION = 0;
    private static final double MODIFIED_BUTTON_ROTATION = 1.7;

    @FXML
    private ImageView homeImage;


    @FXML
    private ImageView crossImage;

    @FXML
    private ImageView logoImage;

    @FXML
    private TextField enterName;

    private String profileName = null;

    /**
     * [add]
     */
    @FXML
    private void initialize() {
        homeImage.hoverProperty().addListener(createHoverListener(homeImage));
        crossImage.hoverProperty().addListener(createHoverListener(crossImage));
    }

    @FXML
    private void homeClicked(MouseEvent actionEvent) {
        FileUtilities.getGameInstance().setRoot("mainMenu");
    }

    @FXML
    private void crossClicked(MouseEvent click) {
        click.consume();
        System.exit(0);
    }

    @FXML
    public void onEnter(KeyEvent submit) {
        if (submit.getCode().equals(KeyCode.ENTER)) {
            profileName = enterName.getText();
            System.out.println(profileName); //Testing
            ProfileUtilities.addProfile(profileName);
            ProfileUtilities.getProfiles(); //Doesn't return anything?
        }
    }

    public String getProfileName(){
        return profileName;
    }

    /**
     * [add]
     * @param img
     * @return
     * @noinspection checkstyle:FinalParameters
     */
    private static ChangeListener<Boolean> createHoverListener(ImageView img) {
        return (observable, oldValue, newValue) -> {
            if (observable.getValue()) {
                img.setRotate(MODIFIED_BUTTON_ROTATION);
            } else {
                img.setRotate(ORIGINAL_BUTTON_ROTATION);
            }
        };
    }

}
