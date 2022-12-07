package com.group38.prisonbreak;
import com.group38.prisonbreak.controllers.NewProfileController;
import com.group38.prisonbreak.utilities.FileUtilities;
import javafx.application.Application;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyEvent.KEY_PRESSED;

public class NewProfileTester extends Application {
    // scene dimensions
    private static final int WIDTH = 800;
    private static final int HEIGHT = 446;

    public void start(Stage primaryStage) {
        FileUtilities.setProfileInstance(this);
        FXMLLoader loader = new FXMLLoader(FileUtilities.getNewProfileResource("fxml/New-Profile.fxml"));

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
        primaryStage.setTitle("New Profile Test");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
