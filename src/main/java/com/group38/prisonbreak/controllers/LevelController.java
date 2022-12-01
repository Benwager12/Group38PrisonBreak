package com.group38.prisonbreak.controllers;

import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.Level;
import com.group38.prisonbreak.enemies.SmartThief;
import com.group38.prisonbreak.utilities.Entity;
import com.group38.prisonbreak.utilities.FileUtilities;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class LevelController {

    @FXML
    private Text levelNumberLabel;

    @FXML
    private Text timeLabel;

    @FXML
    private Text scoreNumberLabel;

    @FXML private Canvas gameCanvas;

    private static GraphicsContext g;

    @FXML
    private BorderPane mainPane;
    @FXML
    public void initialize() {
        ChangeListener<Number> paneSizeChange = (observable, oldValue, newValue) -> {
            recalculateCanvasSize();
            if (GameManager.level != null) {
                GameManager.level.draw(g);
            }


            GameManager.entityTimeLine = new Timeline(new KeyFrame(Duration.millis(500), event -> moveEntities()));
            GameManager.smartThiefTimeLine = new Timeline(new KeyFrame(Duration.millis(1250), event -> moveSmartThief()));
        };
        mainPane.heightProperty().addListener(paneSizeChange);
        mainPane.widthProperty().addListener(paneSizeChange);
    }

    public void recalculateCanvasSize() {
        if (GameManager.level == null) {
            return;
        }

        int w = (int) mainPane.getWidth();
        int h = (int) (mainPane.getHeight() - 46);

        int newWidth = Level.getTileSideLength(GameManager.level, w, h) * GameManager.level.getWidth();
        int newHeight = Level.getTileSideLength(GameManager.level, w, h) * GameManager.level.getHeight();

        gameCanvas.setWidth(newWidth);
        gameCanvas.setHeight(newHeight);
    }

    public void drawCanvas() {
        if (g == null) {
            g = gameCanvas.getGraphicsContext2D();
        }

        GameManager.level.draw(g);
    }

    public void onMouseClickCanvas(MouseEvent mouseEvent) {
        GameManager.level = FileUtilities.readLevel("0");
        drawCanvas();
        recalculateCanvasSize();
    }

    /**
     * moves all the entities apart from smart thief
     */
    private static void moveEntities() {
        if (GameManager.level != null) {
            for (Entity entity : GameManager.level.getEntities()) {
                if (!(entity instanceof SmartThief)) {
                    entity.move();
                }
            }
        }

        if (GameManager.level != null) {
            GameManager.level.draw(g);
        }
    }

    /**
     *  moves the smart thief
     */
    private static void moveSmartThief() {
        if (GameManager.level != null) {
            for (Entity entity : GameManager.level.getEntities()) {
                if (entity instanceof SmartThief) {
                    entity.move();
                }
            }
        }
    }

}
