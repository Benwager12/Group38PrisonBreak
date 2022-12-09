package com.group38.prisonbreak.controllers;

import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.utilities.FileUtilities;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class LevelSuccessFailureController {

    @FXML
    private BorderPane LevelFailWindow;

    @FXML
    private BorderPane LevelSuccessWindow;

    @FXML
    private ImageView homeImage;

    @FXML
    private ImageView retryLevelImage;

    @FXML
    private ImageView crossImage;

    @FXML
    private ImageView LevelClearedImage;

    @FXML
    private ImageView nextLevelButton;

    @FXML
    private ImageView LevelFailedImage;

    @FXML
    private ImageView retryLevelButton;


    @FXML
    private void retryLevelClicked(MouseEvent mouseEvent) {
        FileUtilities.getGameInstance().setRoot("load" + GameManager.level.getLevelNumber());
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
    private void goToNextLevel(MouseEvent mouseEvent) {
        String nextLevel = Integer.toString((GameManager.level.getLevelNumber() + 1));
        FileUtilities.getGameInstance().setRoot("load" + nextLevel);
    }
}
