package com.group38.prisonbreak;

import com.group38.prisonbreak.utilities.FileUtilities;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.scene.canvas.Canvas;
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

        FXMLLoader loader = new FXMLLoader(FileUtilities.getResource("fxml/level-view.fxml"));

        Pane root = null;
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

        String iconLocation = FileUtilities.getResourceURI("images/mainMenu/CellBlock38Icon.png");
        if (iconLocation != null) {
            primaryStage.getIcons().add(new Image(iconLocation));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}

