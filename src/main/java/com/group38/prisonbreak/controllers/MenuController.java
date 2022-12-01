package com.group38.prisonbreak.controllers;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * MenuController handles the...[add]
 */
public class MenuController {

    @FXML
    private Button newGameButton;

    @FXML
    private ImageView newGameImage;

    @FXML
    private Button loadGameButton;

    @FXML
    private ImageView loadGameImage;

    @FXML
    private Button exitButton;

    @FXML
    private ImageView exitImage;

    /**
     * Adds movement to a button when it is hovered over e.g. during menu selection
     * @param e The interaction of the mouse with the button.
     */
    public void animateButton(MouseEvent e) {
    ChangeListener<Boolean> mousePresent = (observable, oldValue, newValue) -> {
    if (e.getTarget() == newGameImage) {
        double originalPos = newGameImage.getRotate();

        // tilt button whilst it is the target
        while(e.getTarget() == newGameImage) {
        newGameImage.setRotate(1);
        newGameButton.setRotate(1);
        }
        // return button to original state
        newGameImage.setRotate(originalPos);
        newGameButton.setRotate(originalPos);
     }
    };
    newGameImage.hoverProperty().addListener(mousePresent);
    loadGameImage.hoverProperty().addListener(mousePresent);
    exitImage.hoverProperty().addListener(mousePresent);
    }
}
