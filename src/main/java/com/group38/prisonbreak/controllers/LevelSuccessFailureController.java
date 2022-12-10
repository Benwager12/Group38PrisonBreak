package com.group38.prisonbreak.controllers;

import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.utilities.FileUtilities;
import com.group38.prisonbreak.utilities.LeaderboardUtilities;
import com.group38.prisonbreak.utilities.ProfileUtilities;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class LevelSuccessFailureController {
    private static final double ORIGINAL_BUTTON_ROTATION = 0;
    private static final double MODIFIED_BUTTON_ROTATION = 1.7;

    @FXML
    private BorderPane LevelFailWindow;

    @FXML
    private BorderPane LevelSuccessWindow;

    @FXML
    private ImageView homeImage;

    @FXML
    private ImageView crossImage;

    @FXML
    private ImageView LevelClearedImage;

    @FXML
    private ImageView nextLevelButton;

    @FXML
    private ImageView LevelFailedImage;

    @FXML
    private VBox retryLevelButton;

    @FXML
    private ImageView retryArrow;

    @FXML
    private ImageView leaderboardButton;

    @FXML
    private Label profileNameText;

    @FXML
    private Label playerScoreText;

    @FXML
    public void initialize() {
        profileNameText.setText(ProfileUtilities.getName(GameManager.getCurrentProfileId()));
        playerScoreText.setText(Integer.toString(GameManager.calculateScore(GameManager.getMoney(),GameManager.getTime())));
        if (GameManager.getLevel().getLevelNumber() == 8) {
            //TO DO Remove next level button when all levels complete
        }
        // button animation
        homeImage.hoverProperty().addListener(rotateButton(homeImage));
        crossImage.hoverProperty().addListener(rotateButton(crossImage));
        retryArrow.hoverProperty().addListener(rotateButton(retryArrow));
    }

    @FXML
    private void homeClicked(MouseEvent actionEvent) {
        FileUtilities.getGameInstance().setRoot("mainMenu");
    }

    @FXML
    private void crossClicked(MouseEvent click) {
        click.consume();
        GameManager.exitGame();
    }

    @FXML
    private void retryLevelClicked(MouseEvent mouseEvent) {
        String currentLevel = Integer.toString((GameManager.getLevel().getLevelNumber()));
        FileUtilities.getGameInstance().setRoot("loadLevel" + currentLevel);
    }

    @FXML
    private void goToNextLevel(MouseEvent mouseEvent) {
        String nextLevel = Integer.toString((GameManager.getLevel().getLevelNumber() + 1));
        FileUtilities.getGameInstance().setRoot("loadLevel" + nextLevel);
    }

    @FXML
    private void leaderboardClicked(MouseEvent mouseEvent) {
        FileUtilities.getGameInstance().setRoot("leaderboard");
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
}
