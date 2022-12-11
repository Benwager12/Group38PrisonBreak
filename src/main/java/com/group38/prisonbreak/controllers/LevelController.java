package com.group38.prisonbreak.controllers;

import com.group38.prisonbreak.Constants;
import com.group38.prisonbreak.Game;
import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.Level;
import com.group38.prisonbreak.entities.enemies.FlyingAssassin;
import com.group38.prisonbreak.entities.enemies.SmartThief;
import com.group38.prisonbreak.utilities.*;
import com.group38.prisonbreak.entities.Player;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class LevelController {

    private static final double ORIGINAL_BUTTON_ROTATION = 0;
    private static final double MODIFIED_BUTTON_ROTATION = 1.7;

    @FXML
    private Text levelNumberLabel;

    @FXML
    private Text timeLabel;

    @FXML
    private Text scoreNumberLabel;

    @FXML
    private ImageView homeImage;

    @FXML
    private ImageView crossImage;

    @FXML
    private ImageView saveImage;

    @FXML
    private Canvas gameCanvas;

    private GraphicsContext g;

    @FXML
    private BorderPane mainPane;

    @FXML
    public void initialize() {
        ChangeListener<Number> paneSizeChange = (observable, oldValue, newValue) -> {
            recalculateCanvasSize();
            if (GameManager.getLevel() != null) {
                GameManager.getLevel().draw(g);
            }

        };
        GameManager.enemyTimeLine = new Timeline(new KeyFrame(Duration.millis(Constants.ENEMY_TIMELINE_DURATION),
                event -> moveEnemies()));
        GameManager.smartThiefTimeLine = new Timeline(new KeyFrame(Duration.millis(Constants.SMART_THIEF_TIMELINE_DURATION),
                event -> moveSmartThief()));
        GameManager.playerTimeLine = new Timeline(new KeyFrame(Duration.millis(Constants.PLAYER_TIMELINE_DURATION),
                event -> movePlayerTick()));
        GameManager.timeTimeLine = new Timeline(new KeyFrame(Duration.millis(Constants.CLOCK_TIMELINE_DURATION),
                event -> changeTime()));
        mainPane.heightProperty().addListener(paneSizeChange);
        mainPane.widthProperty().addListener(paneSizeChange);

        // button animation
        homeImage.hoverProperty().addListener(rotateButton(homeImage));
        crossImage.hoverProperty().addListener(rotateButton(crossImage));
        saveImage.hoverProperty().addListener(rotateButton(saveImage));

        // Initializes and starts timelines
        GameManager.initTimelines();
        drawCanvas();
        recalculateCanvasSize();
        levelNumberLabel.setText(String.valueOf(GameManager.getLevel().getLevelNumber()));
        GameManager.resetMoney();
        GameManager.playTimeLines();
    }

    public void recalculateCanvasSize() {
        if (GameManager.getLevel() == null) {
            return;
        }

        int w = (int) mainPane.getWidth();
        int h = (int) mainPane.getHeight() - 46;

        int newWidth = Level.getTileSideLength(GameManager.getLevel(), w, h) * GameManager.getLevel().getWidth();
        int newHeight = Level.getTileSideLength(GameManager.getLevel(), w, h) * GameManager.getLevel().getHeight();

        gameCanvas.setWidth(newWidth);
        gameCanvas.setHeight(newHeight);

        levelNumberLabel.setText(String.valueOf(GameManager.getLevel().getLevelNumber()));
    }

    public void drawCanvas() {
        if (g == null) {
            g = gameCanvas.getGraphicsContext2D();
        }

        GameManager.getLevel().draw(g);
    }

    public void onMouseClickCanvas(MouseEvent ignoredMouseEvent) {
        GameManager.setLevel(FileUtilities.readLevel(GameManager.getLevel().getLevelNumber()));
        drawCanvas();
        recalculateCanvasSize();
        levelNumberLabel.setText(String.valueOf(GameManager.getLevel().getLevelNumber()));
        GameManager.playTimeLines();
    }

    /**
     * moves all the entities apart from smart thief
     */
    private void moveEnemies() {
        // Try catch error of moving an entity while the entity is being deleted
            for (Entity entity : GameManager.getLevel().getEntities()) {
                if (entity.isAlive() && !(entity instanceof SmartThief) && !(entity instanceof Player)) {
                    entity.move();

                    // Draws again if the flying assassin has collided with the player so it's visible before games ends
                    if (entity instanceof FlyingAssassin && ((FlyingAssassin) entity).getHasColliedWithPlayer()) {
                        GameManager.getLevel().draw(g);
                    }
                }
            }
            if (GameManager.getLevel() != null) {
                GameManager.getLevel().draw(g);
            }
    }

    /**
     * moves the smart thief
     */
    private void moveSmartThief() {
        if (GameManager.getLevel() != null) {
            for (Entity entity : GameManager.getLevel().getEntities()) {
                if (entity.isAlive() && entity instanceof SmartThief) {
                    entity.move();
                }
            }
            if (GameManager.getLevel() != null) {
                GameManager.getLevel().draw(g);
            }
        }
    }

    /**
     * Moves the player every tick
     */
    private void movePlayerTick() {
        if (GameManager.getLevel() != null) {
            GameManager.getLevel().getPlayer().move();
            GameManager.getLevel().draw(g);
        }
        scoreNumberLabel.setText(String.valueOf(GameManager.getMoney()));


    }

    /**
     * Changes the main game timer
     * @author Daniel Banks, Ben Wager
     * @since 02/12/2022
     */
    private void changeTime() {
        GameManager.setTime(GameManager.getTime() - 1);

        // Converting to minutes and seconds
        int minutes = GameManager.getTime() / 60;
        int seconds = GameManager.getTime() % 60;

        // Converting to 0 left padded
        String minutesStr = minutes < 9 ? String.format("0%d", minutes) : String.valueOf(minutes);
        String secondsStr = seconds < 9 ? String.format("0%d", seconds) : String.valueOf(seconds);

        // Displaying to the screen
        timeLabel.setText(minutesStr + ":" + secondsStr);
        if (GameManager.getTime() <= 0) {
            GameManager.stopTimeLines();
            timeLabel.setText("GAME OVER");
            GameManager.endGame(false);
        }
    }

    @FXML
    private void homeClicked(MouseEvent ignoredActionEvent) {
        FileUtilities.getGameInstance().setRoot("mainMenu");
    }

    @FXML
    private void crossClicked(MouseEvent click) {
        click.consume();
        GameManager.exitGame();
    }

    @FXML
    private void saveClicked(MouseEvent actionEvent) {
        GameManager.saveLevel();
        FileUtilities.getGameInstance().setRoot("levelMenu");
    }

    /**
     * Rotates button when applicable.
     *
     * @param img the button to be rotated
     * @return rotated/unrotated button depending on situation
     */
    private static ChangeListener<Boolean> rotateButton(ImageView img) {
        return (observable, oldValue, newValue) -> {
            if (observable.getValue()) {
                // modify button position
                img.setRotate(MODIFIED_BUTTON_ROTATION);

            } else {
                // maintain original button position
                img.setRotate(ORIGINAL_BUTTON_ROTATION);
            }
        };
    }
}
