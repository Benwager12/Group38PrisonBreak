package com.group38.prisonbreak.controllers;

import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.utilities.FileUtilities;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
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

    public void drawCanvas() {
        if (g == null) {
            g = gameCanvas.getGraphicsContext2D();
        }

        GameManager.level.draw(g);
    }

    public void onMouseClickCanvas(MouseEvent mouseEvent) {
        GameManager.level = FileUtilities.readLevel("0");
        drawCanvas();
    }
}
