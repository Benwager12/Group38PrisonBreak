package com.group38.prisonbreak.controllers;

import com.group38.prisonbreak.utilities.FileUtilities;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * MenuController handles the...[add]
 */
public class MenuController {
    private static final double ORIGINAL_BUTTON_ROTATION = 0;
    private static final double MODIFIED_BUTTON_ROTATION = 1.7;

    @FXML
    private ImageView newGameImage;

    @FXML
    private ImageView loadGameImage;

    @FXML
    private ImageView exitImage;

    /**
     * [add]
     */
    @FXML
    private void initialize() {
        newGameImage.hoverProperty().addListener(createHoverListener(newGameImage));
        loadGameImage.hoverProperty().addListener(createHoverListener(loadGameImage));
        exitImage.hoverProperty().addListener(createHoverListener(exitImage));
    }

    @FXML
    private void newGameClicked(MouseEvent actionEvent){
        FileUtilities.getGameInstance().setRoot("profile");
    }

    @FXML
    private void loadGameClicked(MouseEvent actionEvent){
        FileUtilities.getGameInstance().setRoot("load");
    }
    @FXML
    private void exitLevel(MouseEvent actionEvent) {
        actionEvent.consume();
        System.exit(0);
    }

    /**
     * [add]
     * @param img
     * @return
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
