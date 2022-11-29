package com.group38.prisonbreak.controllers;

import com.group38.prisonbreak.GameManager;
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
            double w = mainPane.getWidth() ;
            double h = mainPane.getHeight() - 46;

            gameCanvas.setWidth(Math.min(w, h));
            gameCanvas.setHeight(Math.min(w, h));

            if (GameManager.level != null) {
                GameManager.level.draw(g);
            }
        };

        mainPane.heightProperty().addListener(paneSizeChange);
        mainPane.widthProperty().addListener(paneSizeChange);
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
    }
}
