package com.group38.prisonbreak.controllers;

import com.group38.prisonbreak.Constants;
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

/**
 * LevelController is the controller that handles the Level-view.fxml.
 * @author Matthew Salter (986488), Daniel Banks (2107922), Ben Wager (2108500)
 */
public class LevelController {

    /* Rotation of buttons that aren't moused over. */
    private static final double ORIGINAL_BUTTON_ROTATION = 0;

    /*  Rotation of buttons that are moused over. */
    private static final double MODIFIED_BUTTON_ROTATION = 1.7;

    /*  Size of the Top Pane with the level information. */
    private static final int LEVEL_TEXT_PANE_SIZE = 46;

    /* Number of seconds in minutes. */
    private static final int SECONDS_IN_MINUTES = 60;

    /* Max size of a number before it requires 2 digits. */
    private static final int MAX_INT = 9;

    /* String Format of the clock to be displayed when playing the game */
    private static final String CLOCK_TEXT_FORMAT = "0%d";

    /*     */
    private GraphicsContext g;

    /* Fx:id for the text for the level number within FXML file. */
    @FXML
    private Text levelNumberLabel;

    /* Fx:id for the text for the time label within FXML file. */
    @FXML
    private Text timeLabel;

    /* Fx:id for the text for the score number within FXML file. */
    @FXML
    private Text scoreNumberLabel;

    /* Fx:id for the home image within FXML file. */
    @FXML
    private ImageView homeImage;

    /* Fx:id for the cross image within FXML file. */
    @FXML
    private ImageView crossImage;

    /* Fx:id for the save image within FXML file. */
    @FXML
    private ImageView saveImage;

    /* Fx:id for the game canvas within FXML file. */
    @FXML
    private Canvas gameCanvas;

    /* Fx:id for the main borderpane within FXML file. */
    @FXML
    private BorderPane mainPane;

    /**
     * Triggers at the opening of the FXML file and creates the
     * timeline, pane sizing, hover property listeners, sets level/score
     * information to text and draws the canvas
     */
    @FXML
    public void initialize() {
        ChangeListener<Number> paneSizeChange =
                (observable, oldValue, newValue) -> {
                    recalculateCanvasSize();
                    if (GameManager.getLevel() != null) {
                        GameManager.getLevel().draw(g);
                    }

        };
        GameManager.enemyTimeLine =
                new Timeline(new KeyFrame(
                        Duration.millis(Constants.ENEMY_TIMELINE_DURATION),
                        event -> moveEnemies()));
        GameManager.smartThiefTimeLine =
                new Timeline(new KeyFrame(
                        Duration.millis(Constants.SMART_THIEF_TIMELINE_DURATION),
                        event -> moveSmartThief()));
        GameManager.playerTimeLine =
                new Timeline(new KeyFrame(
                        Duration.millis(Constants.PLAYER_TIMELINE_DURATION),
                        event -> movePlayerTick()));
        GameManager.timeTimeLine =
                new Timeline(new KeyFrame(
                        Duration.millis(Constants.CLOCK_TIMELINE_DURATION),
                        event -> changeTime()));
        mainPane.heightProperty().addListener(paneSizeChange);
        mainPane.widthProperty().addListener(paneSizeChange);

        /* Animate buttons on hover detection. */
        homeImage.hoverProperty().addListener(rotateButton(homeImage));
        crossImage.hoverProperty().addListener(rotateButton(crossImage));
        saveImage.hoverProperty().addListener(rotateButton(saveImage));

        /* Initializes and starts timelines */
        GameManager.initTimelines();
        drawCanvas();
        recalculateCanvasSize();
        levelNumberLabel.setText(
                String.valueOf(GameManager
                .getLevel()
                .getLevelNumber())
        );
        GameManager.playTimeLines();
        scoreNumberLabel.setText(String.valueOf(GameManager.getMoney()));
    }

    /**
     *
     */
    public void recalculateCanvasSize() {
        if (GameManager.getLevel() == null) {
            return;
        }

        int w = (int) mainPane.getWidth();
        int h = (int) mainPane.getHeight() - LEVEL_TEXT_PANE_SIZE;

        int newWidth =
                Level.getTileSideLength(GameManager.getLevel(), w, h)
                        * GameManager.getLevel().getWidth();
        int newHeight =
                Level.getTileSideLength(GameManager.getLevel(), w, h)
                        * GameManager.getLevel().getHeight();

        gameCanvas.setWidth(newWidth);
        gameCanvas.setHeight(newHeight);

        levelNumberLabel.setText(
                String.valueOf(
                        GameManager
                                .getLevel()
                                .getLevelNumber())
        );
    }

    /**
     *
     */
    public void drawCanvas() {
        if (g == null) {
            g = gameCanvas.getGraphicsContext2D();
        }

        GameManager.getLevel().draw(g);
    }


    /**
     * Moves all the entities apart from smart thief.
     */
    private void moveEnemies() {
        /* Try catch error of moving an entity while the entity is being deleted */
            for (Entity entity : GameManager.getLevel().getEntities()) {
                if (entity.isAlive() && !(entity instanceof SmartThief)
                        && !(entity instanceof Player)) {
                    entity.move();

                    /* Draws again if the flying assassin has collided with the player
                     * So it's visible before games ends.
                     */
                    if (entity instanceof FlyingAssassin
                            && ((FlyingAssassin) entity)
                            .getHasColliedWithPlayer()) {
                        GameManager.getLevel().draw(g);
                    }
                }
            }
            if (GameManager.getLevel() != null) {
                GameManager.getLevel().draw(g);
            }
    }

    /**
     * Moves the smart thief.
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
     * Moves the player every tick.
     */
    private void movePlayerTick() {
        if (GameManager.getLevel() != null) {
            GameManager.getLevel().getPlayer().move();
            GameManager.getLevel().draw(g);
        }
        scoreNumberLabel.setText(String.valueOf(GameManager.getMoney()));


    }

    /**
     * Changes the main game timer.
     */
    private void changeTime() {
        GameManager.setTime(GameManager.getTime() - 1);

        /* Converting to minutes and seconds */
        int minutes = GameManager.getTime() / SECONDS_IN_MINUTES;
        int seconds = GameManager.getTime() % SECONDS_IN_MINUTES;

        /* Converting to 0 left padded. */
        String minutesStr =
                minutes < MAX_INT ? String.format(CLOCK_TEXT_FORMAT, minutes)
                        : String.valueOf(minutes);
        String secondsStr =
                seconds < MAX_INT ? String.format(CLOCK_TEXT_FORMAT, seconds)
                        : String.valueOf(seconds);

        /* Displaying to the screen. */
        timeLabel.setText(minutesStr + ":" + secondsStr);
        if (GameManager.getTime() <= 0) {
            GameManager.stopTimeLines();
            timeLabel.setText("GAME OVER");
            GameManager.endGame(false);
        }
    }

    /**
     * On home image clicked redirect the root window.
     * @param click trigger on mouse clicked.
     */
    @FXML
    private void homeClicked(MouseEvent click) {
        FileUtilities.getGameInstance().setRoot("mainMenu");
        GameManager.stopTimeLines();
    }

    /**
     * On cross image clicked, exit the game window.
     * @param click trigger on mouse clicked.
     */
    @FXML
    private void crossClicked(MouseEvent click) {
        click.consume();
        GameManager.exitGame();
    }

    /**
     * Save the level and re-direct the root pane.
     * @param click Trigger on mouse clicked.
     */
    @FXML
    private void saveClicked(MouseEvent click) {
        GameManager.saveLevel();
        FileUtilities.getGameInstance().setRoot("levelMenu");
        GameManager.stopTimeLines();
    }

    /**
     * Rotates button when applicable.
     * @param img the button to be rotated.
     * @return rotated/non-rotated button depending on situation.
     */
    private static ChangeListener<Boolean> rotateButton(ImageView img) {
        return (observable, oldValue, newValue) -> {
            if (observable.getValue()) {
                /* Modify button position. */
                img.setRotate(MODIFIED_BUTTON_ROTATION);

            } else {
                /* Maintain original button position. */
                img.setRotate(ORIGINAL_BUTTON_ROTATION);
            }
        };
    }
}
