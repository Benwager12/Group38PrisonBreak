package com.group38.prisonbreak.controllers;

import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.utilities.FileUtilities;
import com.group38.prisonbreak.utilities.LeaderboardUtilities;
import com.group38.prisonbreak.utilities.ProfileUtilities;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class LevelSuccessFailureController {

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
    private ImageView leaderboardButton;

    @FXML
    private Label profileNameText;

    @FXML
    private Label playerScoreText;

    @FXML
    public void initialize() {
        profileNameText.setText(ProfileUtilities.getName(GameManager.currentProfileId));
        playerScoreText.setText(Integer.toString(GameManager.calculateScore(GameManager.money,GameManager.time)));
        if (GameManager.level.getLevelNumber() == 8) {
            //TO DO Remove next level button when all levels complete
        }
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
        String currentLevel = Integer.toString((GameManager.level.getLevelNumber()));
        FileUtilities.getGameInstance().setRoot("loadLevel" + currentLevel);
    }

    @FXML
    private void goToNextLevel(MouseEvent mouseEvent) {
        String nextLevel = Integer.toString((GameManager.level.getLevelNumber() + 1));
        FileUtilities.getGameInstance().setRoot("loadLevel" + nextLevel);
    }

    @FXML
    private void leaderboardClicked(MouseEvent mouseEvent) {
        FileUtilities.getGameInstance().setRoot("leaderboard");
    }

}
