package com.group38.prisonbreak.controllers;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/**
 * MenuController handles the...[add]
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

    /**
     * [add]
     */
    @FXML
    private void initialize() {
        homeImage.hoverProperty().addListener(createHoverListener(homeImage));
        crossImage.hoverProperty().addListener(createHoverListener(crossImage));
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
