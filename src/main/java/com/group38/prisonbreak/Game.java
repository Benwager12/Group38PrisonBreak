package com.group38.prisonbreak;

import com.group38.prisonbreak.utilities.FileUtilities;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class Game extends Application {

    // Size of the scene
    private static final int WIDTH = 800;
    private static final int HEIGHT = 446;

    public void start(Stage primaryStage) {
        FileUtilities.setGameInstance(this);
        Pane root = null;

        try {
            root = FXMLLoader.load(FileUtilities.getResource("level/level-view.fxml"));
        } catch (IOException e) {
            System.out.println("Couldn't find level view.");
            System.exit(-1);
        }

        Scene scene = new Scene(root, WIDTH, HEIGHT);

        primaryStage.setMinWidth(WIDTH);
        primaryStage.setMinHeight(HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setTitle("Prison Break");

        String iconLocation = FileUtilities.getResourceURI("images/Group38LogoRS.png");
        if (iconLocation != null) {
            primaryStage.getIcons().add(new Image(iconLocation));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}

