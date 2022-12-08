package com.group38.prisonbreak.controllers;

import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.utilities.FileUtilities;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * LevelMenuController handles...[add]
 */
public class LevelMenuController {

    private static final double ORIGINAL_BUTTON_ROTATION = 0;
    private static final double MODIFIED_BUTTON_ROTATION = 1.7;

    @FXML
    private ImageView level1Button;

    @FXML
    private ImageView level2Button;

    @FXML
    private ImageView level3Button;

    @FXML
    private ImageView level4Button;

    @FXML
    private ImageView level5Button;

    @FXML
    private ImageView level6Button;

    @FXML
    private ImageView level7Button;

    @FXML
    private ImageView level8Button;

    private void initialize() {

    }

    /**
     * [add]
     * @param e
     */
    private void levelSelected(MouseEvent e) {
        if(true){
            GameManager.level = FileUtilities.readLevel("6");
        } else {

        }
    }

    private static ChangeListener<Boolean> animateButton(ImageView img) {
        return (observable, oldValue, newValue) -> {
            if (observable.getValue()) {
                img.setRotate(MODIFIED_BUTTON_ROTATION);
            } else {
                img.setRotate(ORIGINAL_BUTTON_ROTATION);
            }
        };
    }
}
