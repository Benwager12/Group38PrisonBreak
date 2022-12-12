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
 * @author Maisha Begum Chowdhury (2114962), Jennalee Llewellyn (967558)
 */
public class MenuController {

    /** The original position of the button. */
    private static final double ORIGINAL_BUTTON_ROTATION = 0;

    /** The modified position of the button. */
    private static final double MODIFIED_BUTTON_ROTATION = 1.7;

    /** Fx:id for the menu's new game button image. */
    @FXML
    private ImageView newGameImage;
    /** Fx:id for the menu's load game button image. */
    @FXML
    private ImageView loadGameImage;
    /** Fx:id for the menu's exit button image. */
    @FXML
    private ImageView exitImage;

    /** Fx:id for the menu's gate image. */
    @FXML
    private ImageView gateImage;
    /** Fx:id for the menu's message of the day text .*/
    @FXML
    private Text motdTextBox;

    /**
     * Rotates button when applicable.
     * @param img The button to be rotated.
     * @return Rotated/non-rotated button depending on situation
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
     * Triggers the initialisation of the listeners for hover properties.
     */
    @FXML
    private void initialize() {
        /* Animate buttons on hover detection */
        newGameImage.hoverProperty().addListener(rotateButton(newGameImage));
        loadGameImage.hoverProperty().addListener(rotateButton(loadGameImage));
        exitImage.hoverProperty().addListener(rotateButton(exitImage));
        gateImage.hoverProperty().addListener(startMOTDListener());
    }

    /**
     * When new game is clicked, re-direct the root pane.
     * @param ignoredClick Triggered on mouse click.
     */
    @FXML
    private void newGameClicked(MouseEvent ignoredClick) {
        FileUtilities.getGameInstance().setRoot("profile");
    }

    /**
     * When load game is clicked, re-direct the root pane.
     * @param ignoredClick Triggered on mouse click.
     */
    @FXML
    private void loadGameClicked(MouseEvent ignoredClick) {
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
    }

    /**
     * Uses the MOTD class to set the message of the day to appropriate
     * text box.
     */
    @FXML
    private void printMOTD() {
        MOTD motd = new MOTD();
        try {
            /* Stores the message of the day */
            String message = motd.getMessageOfTheDay();
            motdTextBox.setText(message);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * When triggered directs to the printMOTD method.
     * @return printMOTD() method.
     */
    private ChangeListener<Boolean> startMOTDListener() {
        return (observable, oldValue, newValue) -> printMOTD();
        }
    }

