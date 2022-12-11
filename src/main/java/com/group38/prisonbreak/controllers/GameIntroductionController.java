package com.group38.prisonbreak.controllers;

import com.group38.prisonbreak.utilities.FileUtilities;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

/**
 * GameIntroductionController is the controller that handles the
 * Game-intro.fxml.
 * @author Jennalee Llewellyn (967558), Matthew Salter (986488)
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
        /* Creates a new timeline for the duration of the video, redirects root window
            after video is complete. */
        timeline = new Timeline(new KeyFrame(Duration.seconds(VIDEO_DURATION), ev -> {
            timeline.stop();
            FileUtilities.getGameInstance().setRoot("levelMenu");
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * On skip button clicked, stop timeline and redirect the root window.
     * @param ignoredActionEvent Trigger on mouse clicked.
     */
    @FXML
    private void skipClicked(final MouseEvent ignoredActionEvent) {
        timeline.stop();
        FileUtilities.getGameInstance().setRoot("levelMenu");
    }
}
