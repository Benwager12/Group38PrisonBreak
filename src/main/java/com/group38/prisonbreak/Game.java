package com.group38.prisonbreak;

import com.group38.prisonbreak.utilities.*;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class Game extends Application {

    /** Size of the scene */
    private static final int WIDTH = 800;
    private static final int HEIGHT = 446;

    /** Key components of the scene */
    private static Pane root = null;
    private static Scene scene = null;
    private static Stage primaryStage = null;

    /**
     * The initialization of the scene/stage.
     * @param primaryStage The primary stage that we start on
     */
    public void start(Stage primaryStage) {

        Game.primaryStage = primaryStage;

        FileUtilities.setGameInstance(this);

        // Initialize Profiles
        ProfileUtilities.initialise();

        // Initialize Leaderboard
        LeaderboardUtilities.initialise();

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
        primaryStage.setResizable(false);

        String iconLocation = FileUtilities.getResourceURI("images/Menus/38Icon.png");
        if (iconLocation != null) {
            primaryStage.getIcons().add(new Image(iconLocation));
        }

        Game.primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, GameManager::processKeyEvent);
        Game.primaryStage.addEventFilter(KeyEvent.KEY_RELEASED, GameManager::processKeyEvent);
    }

    /**
     * Setting the root to a specific scene.
     * @param paneType The specific window to load
     */
    public void setRoot(String paneType) {

        if(paneType.startsWith("loadLevel")) {
            GameManager.setLevel(FileUtilities.readLevel(Integer.parseInt(paneType.substring(9))));
            paneType = paneType.substring(0, paneType.length() - 1);
        }
        FXMLLoader loader = switch (paneType) {
            case "profile" -> new FXMLLoader(FileUtilities.getResource("fxml/New-Profile.fxml"));
            case "loadLevel", "loadOverwriteLevel" -> new FXMLLoader(FileUtilities.getResource("fxml/Level-view.fxml"));
            case "overwriteMenu" -> new FXMLLoader(FileUtilities.getResource("fxml/Ask-overwrite-menu.fxml"));
            case "mainMenu" -> new FXMLLoader(FileUtilities.getResource("fxml/Start-menu.fxml"));
            case "levelMenu" -> new FXMLLoader(FileUtilities.getResource("fxml/Level-menu.fxml"));
            case "leaderboard" -> new FXMLLoader(FileUtilities.getResource("fxml/Leaderboard.fxml"));
            case "selectProfile" -> new FXMLLoader(FileUtilities.getResource("fxml/Select-profile.fxml"));
            case "levelWon" -> new FXMLLoader(FileUtilities.getResource("fxml/Level-passed.fxml"));
            case "levelLost" -> new FXMLLoader(FileUtilities.getResource("fxml/Level-failed.fxml"));
            case "gameIntro" -> new FXMLLoader(FileUtilities.getResource("fxml/Game-intro.fxml"));
            default -> null;
        };
        assert loader != null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setStage();

        primaryStage.setResizable(paneType.startsWith("load"));
    }

    /**
     * Setting up the stage.
     */
    private static void setStage() {
        scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}

