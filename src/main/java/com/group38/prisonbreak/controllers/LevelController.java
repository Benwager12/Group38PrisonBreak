package com.group38.prisonbreak.controllers;

import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.Level;
import com.group38.prisonbreak.enemies.SmartThief;
import com.group38.prisonbreak.utilities.Entity;
import com.group38.prisonbreak.utilities.FileUtilities;
import com.group38.prisonbreak.utilities.Player;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

    @FXML
    private Canvas gameCanvas;

    private GraphicsContext g;

    @FXML
    private BorderPane mainPane;

    @FXML
    public void initialize() {
        ChangeListener<Number> paneSizeChange = (observable, oldValue, newValue) -> {
            recalculateCanvasSize();
            if (GameManager.level != null) {
                GameManager.level.draw(g);
            }

        };
        GameManager.entityTimeLine = new Timeline(new KeyFrame(Duration.millis(500), event -> moveEntities()));
        GameManager.smartThiefTimeLine = new Timeline(new KeyFrame(Duration.millis(1250), event -> moveSmartThief()));
        GameManager.playerTimeLine = new Timeline(new KeyFrame(Duration.millis(350), event -> movePlayer()));
        GameManager.timeTimeLine = new Timeline(new KeyFrame(Duration.millis(1000), event -> changeTime()));
        mainPane.heightProperty().addListener(paneSizeChange);
        mainPane.widthProperty().addListener(paneSizeChange);

        // Initializes and starts timelines
        GameManager.initTimelines();
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

        levelNumberLabel.setText(String.valueOf(GameManager.level.getLevelNumber()));
    }

    public void drawCanvas() {
        if (g == null) {
            g = gameCanvas.getGraphicsContext2D();
        }

        GameManager.level.draw(g);
    }

    public void onMouseClickCanvas(MouseEvent mouseEvent) {
        GameManager.level = FileUtilities.readLevel("1");
        drawCanvas();
        recalculateCanvasSize();
        levelNumberLabel.setText(String.valueOf(GameManager.level.getLevelNumber()));
        GameManager.playTimeLines();
    }

    /**
     * moves all the entities apart from smart thief
     */
    private void moveEntities() {
        if (GameManager.level != null) {
            GameManager.level.getEntities().forEach(entity -> {
                if (!(entity instanceof SmartThief) && !(entity instanceof Player)) {
                    entity.move();
                }
            });
            if (GameManager.level != null) {
                GameManager.level.draw(g);
            }
        }
    }

    /**
     * moves the smart thief
     */
    private void moveSmartThief() {
        if (GameManager.level != null) {
            for (Entity entity : GameManager.level.getEntities()) {
                if (entity instanceof SmartThief) {
                    entity.move();
                }
            }
            if (GameManager.level != null) {
                GameManager.level.draw(g);
            }
        }
    }

    private void movePlayer() {
        if (GameManager.level != null) {
            Entity player = GameManager.level.getPlayer();

            for (KeyCode c : GameManager.currentlyPressed) {
                if (c == KeyCode.UP || c == KeyCode.W) {
                    player.move(0);
                    break;
                } else if (c == KeyCode.RIGHT || c == KeyCode.D) {
                    player.move(1);
                    break;
                } else if (c == KeyCode.DOWN || c == KeyCode.S) {
                    player.move(2);
                    break;
                } else if (c == KeyCode.LEFT || c == KeyCode.A) {
                    player.move(3);
                    break;
                } else if (c == KeyCode.K) {
                    System.out.println(GameManager.level.isGateOpen(4));
                }
            }

            GameManager.level.draw(g);
        }
        scoreNumberLabel.setText(String.valueOf(GameManager.money));


    }

    /**
     * changes the main game timer
     */
    private void changeTime() {
        GameManager.time--;
        timeLabel.setText(String.valueOf(GameManager.time));
        if (GameManager.time <= 0) {
            GameManager.stopTimeLines();
            timeLabel.setText("GAME OVER");
        }
    }
}
