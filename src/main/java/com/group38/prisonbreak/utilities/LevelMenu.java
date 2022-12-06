package com.group38.prisonbreak.utilities;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class LevelMenu extends Application {

    public void start(Stage stage) {

        // Add image background
        Image image = new Image("file:\"C:\\Users\\44736\\IdeaProjects\\Group38PrisonBreak\\src\\mainr\\resources\\com\\group38\\prisonbreak\\images\\mainMenu\\MainMenuDesignBG.png\"");
        ImageView mv = new ImageView(image);

        Group root = new Group();
        root.getChildren().addAll(mv);

        Scene scene = new Scene(root, 800, 446);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}

