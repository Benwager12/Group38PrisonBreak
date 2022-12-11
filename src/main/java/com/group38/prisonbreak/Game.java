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

    /** Location of the new profile fxml file. */
    private static final String NEW_PROFILE_FXML = "fxml/New-Profile.fxml";

    /** Location of the level view fxml file. */
    private static final String LEVEL_VIEW_FXML = "fxml/level-view.fxml";

    /** Location of the overwrite menu fxml. file */
    private static final String OVERWRITE_MENU_FXML =
            "fxml/ask-overwrite-menu.fxml";

    /** Location of the start menu fxml file. */
    private static final String START_MENU_FXML = "fxml/start-menu.fxml";

    /** Location of the level menu fxml file. */
    private static final String LEVEL_MENU_FXML = "fxml/level-menu.fxml";

    /** Location of the leaderboard fxml file. */
    private static final String LEADERBOARD_FXML = "fxml/leaderboard.fxml";

    /** Location of the select profile fxml file. */
    private static final String SELECT_PROFILE_FXML =
            "fxml/Select-Profile.fxml";

    /** Location of the game intro fxml file. */
    private static final String GAME_INTRO_FXML = "fxml/Game-intro.fxml";

    /** Location of the level passed fxml file. */
    private static final String LEVEL_PASSED_FXML = "fxml/level-Passed.fxml";

    /** Location of the level failed fxml file. */
    private static final String LEVEL_FAILED_FXML = "fxml/level-Failed.fxml";

    /** Input that represents opening the profile window. */
    private static final String PROFILE = "profile";

    /** Input that represents opening the load level window. */
    private static final String LOADLEVEL = "loadLevel";

    /** Input that represents opening the load overwrite level window. */
    private static final String LOAD_OVERWRITE_LEVEL =  "loadOverwriteLevel";

    /** Input that represents opening the main menu window. */
    private static final String MAIN_MENU = "mainMenu";

    /** Input that represents opening the overwrite window. */
    private static final String OVERWRITE_MENU = "overwriteMenu";

    /** Input that represents opening the level menu window. */
    private static final String LEVEL_MENU = "levelMenu";

    /** Input that represents opening the leaderboard window. */
    private static final String LEADERBOARD = "leaderboard";

    /** Input that represents opening the select profile window. */
    private static final String SELECT_PROFILE = "selectProfile";

    /** Input that represents opening the level won window. */
    private static final String LEVEL_WON = "levelWon";

    /** Input that represents opening the level lost window. */
    private static final String LEVEL_LOST = "levelLost";

    /** Input that represents opening the game intro window. */
    private static final String GAME_INTRO = "gameIntro";

    /** Input that represents opening the loading a level. */
    private static final String LOAD = "load";

    /** Size of the scene. */
    private static final int WIDTH = 800;
    private static final int HEIGHT = 446;

    /** Key components of the scene. */
    private static Pane root = null;
    private static Scene scene = null;
    private static Stage primaryStage = null;

    public static void main(String[] args) {
        launch(args);
    }

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
            FXMLLoader mainMenuLoader =
                    new FXMLLoader(FileUtilities.getResource(
                            "fxml/start-menu.fxml")
                    );
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

        String iconLocation =
                FileUtilities.getResourceURI("images/Menus/38Icon.png");
        if (iconLocation != null) {
            primaryStage.getIcons().add(new Image(iconLocation));
        }

        Game.primaryStage.addEventFilter(
                KeyEvent.KEY_PRESSED, GameManager::processKeyEvent);
        Game.primaryStage.addEventFilter(
                KeyEvent.KEY_RELEASED, GameManager::processKeyEvent);
    }

    /**
     * Setting the root to a specific scene.
     * @param paneType The specific window to load
     */
    public void setRoot(String paneType) {

        if (paneType.startsWith(LOADLEVEL)) {
            GameManager.setLevel(
                    FileUtilities.readLevel(
                            Integer.parseInt(paneType.substring(9))));
            paneType = paneType.substring(0, paneType.length() - 1);
        }
        FXMLLoader loader = switch (paneType) {
            case PROFILE ->
                    new FXMLLoader(FileUtilities.getResource(
                            NEW_PROFILE_FXML));
            case LOADLEVEL, LOAD_OVERWRITE_LEVEL ->
                    new FXMLLoader(FileUtilities.getResource(
                            LEVEL_VIEW_FXML));
            case OVERWRITE_MENU ->
                    new FXMLLoader(FileUtilities.getResource(
                            OVERWRITE_MENU_FXML));
            case MAIN_MENU ->
                    new FXMLLoader(FileUtilities.getResource(
                            START_MENU_FXML));
            case LEVEL_MENU ->
                    new FXMLLoader(FileUtilities.getResource(
                            LEVEL_MENU_FXML));
            case LEADERBOARD ->
                    new FXMLLoader(FileUtilities.getResource(
                            LEADERBOARD_FXML));
            case SELECT_PROFILE ->
                    new FXMLLoader(FileUtilities.getResource(
                           SELECT_PROFILE_FXML));
            case LEVEL_WON ->
                    new FXMLLoader(FileUtilities.getResource(
                           LEVEL_PASSED_FXML));
            case LEVEL_LOST ->
                    new FXMLLoader(FileUtilities.getResource(
                            LEVEL_FAILED_FXML));
            case GAME_INTRO ->
                    new FXMLLoader(FileUtilities.getResource(
                            GAME_INTRO_FXML));
            default -> null;
        };
        assert loader != null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setStage();

        primaryStage.setResizable(paneType.startsWith(LOAD));
    }

    /**
     * Setting up the stage.
     */
    private static void setStage() {
        scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

