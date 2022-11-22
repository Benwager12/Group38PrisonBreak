package com.group38.prisonbreak;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;

public class Game extends Application {

    // Size of the scene
    private static final int WIDTH = 800;
    private static final int HEIGHT = 446;

    public void start(Stage primaryStage) {
        try {
            Pane root = (Pane)FXMLLoader.load(getClass().
                    getResource("fxml/level-view.fxml"));
            Scene scene = new Scene(root,WIDTH,HEIGHT);
            primaryStage.setMinWidth(WIDTH);
            primaryStage.setMinHeight(HEIGHT);
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setTitle("Prison Break");
            primaryStage.getIcons().add(new Image(getClass().getResource("images/player.png").toURI().toString()));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}

