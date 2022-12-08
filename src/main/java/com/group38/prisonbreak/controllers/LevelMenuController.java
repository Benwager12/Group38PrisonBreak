package com.group38.prisonbreak.controllers;

import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.utilities.FileUtilities;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * LevelMenuController handles...[add]
 */
public class LevelMenuController {

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
        if(){
            GameManager.level = FileUtilities.readLevel();
        } else {

        }

    }
}
