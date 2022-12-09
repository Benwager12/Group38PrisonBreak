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

    @FXML
    private ImageView homeButton;

    @FXML
    private ImageView exitButton;

    @FXML
    private void initialize() {
        // button animation
        ImageView[] buttons = new ImageView[]{level1Button, level2Button, level3Button,
                level4Button, level5Button, level6Button, level7Button, level8Button};

        for (ImageView b : buttons) {
            b.hoverProperty().addListener(enlargeButton(b));
        }

        //
        homeButton.hoverProperty().addListener(rotateButton(homeButton));
        exitButton.hoverProperty().addListener(rotateButton(exitButton));
    }

    /**
     * [add]
     *
     * @param e
     */
    @FXML
    private void levelSelected(MouseEvent e) {
        if (!(e.getSource() instanceof ImageView iv)) {
            return;
        }
        String buttonId = iv.getId();
        String levelNumber = buttonId.substring(5, buttonId.length() - 6);
        FileUtilities.getGameInstance().setRoot("load" + levelNumber);
    }

    /**
     * Enlarges button when applicable.
     * @param img the button to be enlarged
     * @return enlarged/usual button depending on situation
     */
    private static ChangeListener<Boolean> enlargeButton(ImageView img) {
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

    /**
     * Rotates button when applicable.
     * @param img the button to be rotated
     * @return rotated/unrotated button depending on situation
     */
    private static ChangeListener<Boolean> rotateButton(ImageView img) {
        return (observable, oldValue, newValue) -> {
            if (observable.getValue()) {
                img.setRotate(MODIFIED_BUTTON_ROTATION);
            } else {
                img.setRotate(ORIGINAL_BUTTON_ROTATION);
            }
        };
    }

    @FXML
    private void homeClicked(MouseEvent e) {
        FileUtilities.getGameInstance().setRoot("mainMenu");
    }

    @FXML
    private void exitClicked(MouseEvent e) {
        e.consume();
        GameManager.exitGame();
    }



}
