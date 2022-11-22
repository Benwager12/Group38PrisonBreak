package com.group38.prisonbreak.controllers;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;

public class LevelController {

    @FXML
    private Label levelNumberLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private Label scoreNumberLabel;

    @FXML
    private Canvas gameCanvas;

    private GraphicsContext gc;

    public void drawCanvas() {
        if (gc == null) {
            gc = gameCanvas.getGraphicsContext2D();
        }

        // TODO: Draw the scene
    }
}
