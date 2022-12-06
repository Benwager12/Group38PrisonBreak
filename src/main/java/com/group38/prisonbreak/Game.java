package com.group38.prisonbreak;

import com.group38.prisonbreak.utilities.FileUtilities;
import com.group38.prisonbreak.utilities.LeaderboardUtilities;
import com.group38.prisonbreak.utilities.ProfileUtilities;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.Arrays;

public class Game extends Application {

    // Size of the scene
    private static final int WIDTH = 800;
    private static final int HEIGHT = 446;

    public void start(Stage primaryStage) {
        FileUtilities.setGameInstance(this);

        FXMLLoader loader = new FXMLLoader(FileUtilities.getResource("fxml/start-menu.fxml"));

        Pane root;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(root, WIDTH, HEIGHT);

        primaryStage.setMinWidth(WIDTH);
        primaryStage.setMinHeight(HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setTitle("Cell Block 38");

        String iconLocation = FileUtilities.getResourceURI("images/mainMenu/38Icon.png");
        if (iconLocation != null) {
            primaryStage.getIcons().add(new Image(iconLocation));
        }

        scene.addEventFilter(KeyEvent.KEY_PRESSED, GameManager::processKeyEvent);

        // Initialize Profiles
        ProfileUtilities.initialise();

        // Initialize Leaderboard
        LeaderboardUtilities.initialise();
    }

    public static void main(String[] args) {
        launch(args);
    }

}

