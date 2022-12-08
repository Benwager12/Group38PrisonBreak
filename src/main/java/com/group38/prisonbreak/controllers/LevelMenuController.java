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
    private static final double ORIGINAL_BUTTON_HEIGHT = 41;
    private static final double ORIGINAL_BUTTON_WIDTH = 36;
    private static final double MODIFIED_BUTTON_HEIGHT = 54;
    private static final double MODIFIED_BUTTON_WIDTH = 54;

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

    @FXML
    private void initialize() {
        level1Button.hoverProperty().addListener(animateButton(level1Button));
        level2Button.hoverProperty().addListener(animateButton(level2Button));
        level3Button.hoverProperty().addListener(animateButton(level3Button));
        level4Button.hoverProperty().addListener(animateButton(level4Button));
        level5Button.hoverProperty().addListener(animateButton(level5Button));
        level6Button.hoverProperty().addListener(animateButton(level6Button));
        level7Button.hoverProperty().addListener(animateButton(level7Button));
        level8Button.hoverProperty().addListener(animateButton(level8Button));
    }

    /**
     * [add]
     * @param e
     */
    @FXML
    private void levelSelected(MouseEvent e) {
            GameManager.level = FileUtilities.readLevel("6");
    }

    private static ChangeListener<Boolean> animateButton(ImageView img) {
        return (observable, oldValue, newValue) -> {
            if (observable.getValue()) {
                img.setFitHeight(MODIFIED_BUTTON_HEIGHT);
                img.setFitWidth(MODIFIED_BUTTON_WIDTH);
            } else {
                img.setFitHeight(ORIGINAL_BUTTON_HEIGHT);
                img.setFitWidth(ORIGINAL_BUTTON_WIDTH);
            }
        };
    }
}
