package com.group38.prisonbreak;
// delete after - just for testing

import com.group38.prisonbreak.utilities.FileUtilities;

public class MenuTester extends Application {
    // scene dimensions
    private static final int WIDTH = 800;
    private static final int HEIGHT = 446;

    public void start(Stage primaryStage) {
        FileUtilities.setGameInstance(this);
        FXMLLoader loader = new FXMLLoader(FileUtilities.getResource("fxml/start-menu.fxml"));

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
