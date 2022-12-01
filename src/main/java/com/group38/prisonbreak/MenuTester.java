package com.group38.prisonbreak;
import com.group38.prisonbreak.utilities.FileUtilities;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;

import com.group38.prisonbreak.utilities.FileUtilities;

import java.io.IOException;

public class MenuTester extends Application {
    // scene dimensions
    private static final int WIDTH = 800;
    private static final int HEIGHT = 446;

    public void start(Stage primaryStage) {
        FileUtilities.setMenuInstance(this);
        FXMLLoader loader = new FXMLLoader(FileUtilities.getMenuResource("fxml/start-menu.fxml"));

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
        primaryStage.setTitle("Menu Test");
    }

    public static void main(String[] args) { launch (args); }
}
