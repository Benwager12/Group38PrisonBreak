package com.group38.prisonbreak.controllers;

import com.group38.prisonbreak.utilities.FileUtilities;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

/**
 * A controller class for the pre-game video, controls the duration of the
 * video and when to open the next window.
 * @author Jennalee Llewellyn (967558)
 */

public class GameIntroductionController {

    /** Game introduction timeline which tells the game when the video has ended. */
    private Timeline timeline = null;

    /** Duration of the video played at the beginning. */
    private static final int VIDEO_DURATION = 29;

    /**
     * Triggers at the opening of the FXML file and plays video.
     */
    @FXML
    private void initialize() {
        //Duration of the video before moving automatically onto the level menu
        timeline = new Timeline(new KeyFrame(Duration.seconds(VIDEO_DURATION), ev -> {
            timeline.stop();
            FileUtilities.getGameInstance().setRoot("levelMenu");
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    @FXML
    private void skipClicked(final MouseEvent ignoredActionEvent) {
        timeline.stop();
        FileUtilities.getGameInstance().setRoot("levelMenu");
    }
}
