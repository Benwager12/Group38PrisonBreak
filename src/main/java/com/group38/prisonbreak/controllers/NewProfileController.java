package com.group38.prisonbreak.controllers;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

/**
 * NewProfileController handles the...[add]
 */
public class NewProfileController {
    private static final double ORIGINAL_BUTTON_ROTATION = 0;
    private static final double MODIFIED_BUTTON_ROTATION = 1.7;

    @FXML
    private Button home;

    @FXML
    private ImageView homeImage;

    @FXML
    private Button exitButton;

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
    public void onEnter(KeyEvent submit) {
        if (submit.getCode().equals(KeyCode.ENTER)) {
            profileName = enterName.getText();
            System.out.println(profileName); //Testing
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
