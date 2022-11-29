package com.group38.prisonbreak.controllers;

import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.Level;
import com.group38.prisonbreak.utilities.FileUtilities;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

public class LevelController {

    @FXML
    private Text levelNumberLabel;

    @FXML
    private Text timeLabel;

    @FXML
    private Text scoreNumberLabel;

    @FXML private Canvas gameCanvas;

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
}
