package com.group38.prisonbreak.controllers;

import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.utilities.FileUtilities;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class LevelSuccessFailureController {

    public BorderPane LevelFailWindow;
    public BorderPane LevelSuccessWindow;
    public ImageView homeImage;
    public ImageView retryLevelImage;
    public ImageView crossImage;
    public ImageView LevelClearedImage;
    public ImageView NextLevelButton;
    public ImageView LevelFailedImage;
    public ImageView retryLevelButton;

    public void retryLevelClicked(MouseEvent ignoredMouseEvent) {
        FileUtilities.getGameInstance().setRoot("load" + GameManager.level.getLevelNumber());
    }

    public void homeClicked(MouseEvent ignoredActionEvent) {
        FileUtilities.getGameInstance().setRoot("mainMenu");
    }

    public void crossClicked(MouseEvent click) {
        click.consume();
        System.exit(0);
    }


    public void goToNextLevel(MouseEvent ignoredMouseEvent) {
        FileUtilities.getGameInstance().setRoot("load" + GameManager.level.getLevelNumber() + 1);
    }
}
