package com.group38.prisonbreak.controllers;

import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.MOTD;
import com.group38.prisonbreak.utilities.FileUtilities;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import java.io.IOException;

/**
 * MenuController is responsible for animating menu buttons,
 * directing button clicks, and presenting the message of the day
 * on the game's starting menu.
 *
 * @author Maisha Begum Chowdhury (2114962), Jennalee Llewellyn (967558)
 */
public class MenuController {

    /* The original rotation of the button. */
    private static final double ORIGINAL_BUTTON_ROTATION = 0;

    /* The modified rotation of the button. */
    private static final double MODIFIED_BUTTON_ROTATION = 1.7;

    /* Fx:id for the image of the new game button within FXML file. */
    @FXML
    private ImageView newGameImage;

    /* Fx:id for the image of the load game button within FXML file. */
    @FXML
    private ImageView loadGameImage;

    /* Fx:id for the image of the exit button within FXML file. */
    @FXML
    private ImageView exitImage;

    /* Fx:id for the main image of the gate within FXML file. */
    @FXML
    private ImageView gateImage;

    /* Fx:id for the text for the message of the day within FXML file. */
    @FXML
    private Text motdTextBox;

    /* String to store the message of the day. */
    private static String message;

    /**
     * Triggers the initialisation of the listeners for hover properties.
     */
    @FXML
    private void initialize() {
        // animate buttons on hover detection
        newGameImage.hoverProperty().addListener(rotateButton(newGameImage));
        loadGameImage.hoverProperty().addListener(rotateButton(loadGameImage));
        exitImage.hoverProperty().addListener(rotateButton(exitImage));
        gateImage.hoverProperty().addListener(startMOTDListener());
    }

    /**
     * When new game is clicked, re-direct the root pane.
     * @param click Triggered on mouse click.
     */
    @FXML
    private void newGameClicked(MouseEvent click) {
        FileUtilities.getGameInstance().setRoot("profile");
    }

    /**
     * When load game is clicked, re-direct the root pane.
     * @param click Triggered on mouse click.
     */
    @FXML
    private void loadGameClicked(MouseEvent click) {
        FileUtilities.getGameInstance().setRoot("selectProfile");
    }

    /**
     * When the exit button is clicked, close the game.
     * @param click Triggered on the mouse click.
     */
    @FXML
    private void exitLevel(MouseEvent click) {
        click.consume();
        GameManager.exitGame();
        //Needs to save info to file
    }

    /**
     * Use the MOTD class to set the message of the day to appropriate
     * text box.
     */
    @FXML
    private void printMOTD() {
        MOTD motd = new MOTD();
        try {
            message = motd.getMessageOfTheDay();
            motdTextBox.setText(message);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Rotates button when applicable.
     * @param img the button to be rotated.
     * @return rotated/non-rotated button depending on situation
     */
    private static ChangeListener<Boolean> rotateButton(ImageView img) {
        return (observable, oldValue, newValue) -> {
            if (observable.getValue()) {
                /* Modify button position */
                img.setRotate(MODIFIED_BUTTON_ROTATION);

            } else {
                /* Maintain original button position */
                img.setRotate(ORIGINAL_BUTTON_ROTATION);
            }
        };
    }

    /**
     * When triggered directs to the printMOTD method.
     * @return printMOTD() method.
     */
    private ChangeListener<Boolean> startMOTDListener() {
        return (observable, oldValue, newValue) -> printMOTD();
        };
    }

