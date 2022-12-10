package com.group38.prisonbreak.controllers;

import com.group38.prisonbreak.utilities.FileUtilities;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

import java.util.concurrent.TimeUnit;

/**
 * A controller class for the pre-game video, controls the duration of the
 * video and when to open the next window.
 * @author Jennalee Llewellyn (967558)
 */

public class GameIntroductionController {

    //The game 'video' variable, saved in a .gif format to act as a video but stored
    //in an image view pane.
    @FXML
    private ImageView gameIntro;

    /**
     * Initialse method to trigger at the opening of the FXML file. Delays
     * the opening of the next FXML by 31 seconds which is the duration of
     * the video. Before setting the new root pane to the level selection menu.
     */
    @FXML
    private void initialize() {
        try {
            //Duration of the video before moving automatically onto the
            TimeUnit.SECONDS.sleep(31);
            FileUtilities.getGameInstance().setRoot("levelMenu");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
