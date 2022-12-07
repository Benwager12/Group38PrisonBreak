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
    private static Pane root = null;
    private static Scene scene = null;
    private static Stage primaryStage = null;

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        FileUtilities.setGameInstance(this);

        try {
            FXMLLoader mainMenuLoader = new FXMLLoader(FileUtilities.getResource("fxml/start-menu.fxml"));
            root = mainMenuLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        scene = new Scene(root, WIDTH, HEIGHT);

        primaryStage.setMinWidth(WIDTH);
        primaryStage.setMinHeight(HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setTitle("Cell Block 38");

        String iconLocation = FileUtilities.getResourceURI("images/mainMenu/38Icon.png");
        if (iconLocation != null) {
            primaryStage.getIcons().add(new Image(iconLocation));
        }

        // Initialize Profiles
        ProfileUtilities.initialise();

        // Initialize Leaderboard
        LeaderboardUtilities.initialise();
    }

    public static void setRoot(String paneType) {
        if (paneType.equals("profile")) {
            try {
                FXMLLoader newProfileLoader = new FXMLLoader(FileUtilities.getResource("fxml/New-Profile.fxml"));
                root = newProfileLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (paneType.equals("load")) {
            try {
                FXMLLoader levelLoader = new FXMLLoader(FileUtilities.getResource("fxml/level-view.fxml"));
                root = levelLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            scene.addEventFilter(KeyEvent.KEY_PRESSED, GameManager::processKeyEvent);
            scene.addEventFilter(KeyEvent.KEY_RELEASED, GameManager::processKeyEvent);
        } else if (paneType.equals("mainMenu")) {
            try {
                FXMLLoader mainMenuLoader = new FXMLLoader(FileUtilities.getResource("fxml/start-menu.fxml"));
                root = mainMenuLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        setStage();
    }

    private static void setStage() {
        scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}

