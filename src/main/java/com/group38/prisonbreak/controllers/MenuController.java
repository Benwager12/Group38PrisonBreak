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
 * @author Maisha Begum Chowdhury (2114962), ... [Jenna i think this is ur cue]
 */
public class MenuController {
    private static final double ORIGINAL_BUTTON_ROTATION = 0;
    private static final double MODIFIED_BUTTON_ROTATION = 1.7;

    @FXML
    private ImageView newGameImage;

    @FXML
    private ImageView loadGameImage;

    @FXML
    private ImageView exitImage;

    @FXML
    private ImageView gateImage;

    @FXML
    private Text motdTextBox;

    private static String message;

    @FXML
    private void initialize() {
        // animate buttons on hover detection
        newGameImage.hoverProperty().addListener(rotateButton(newGameImage));
        loadGameImage.hoverProperty().addListener(rotateButton(loadGameImage));
        exitImage.hoverProperty().addListener(rotateButton(exitImage));
        gateImage.hoverProperty().addListener(startMOTDListener(gateImage));
    }

    @FXML
    private void newGameClicked(MouseEvent actionEvent){
        FileUtilities.getGameInstance().setRoot("profile");
    }

    @FXML
    private void loadGameClicked(MouseEvent actionEvent){
        FileUtilities.getGameInstance().setRoot("selectProfile");
    }

    @FXML
    private void exitLevel(MouseEvent actionEvent) {
        actionEvent.consume();
        GameManager.exitGame();
        //Needs to save info to file
    }

    @FXML
    private void printMOTD(){
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

    private ChangeListener<Boolean> startMOTDListener(ImageView gateImage) {
        return (observable, oldValue, newValue) -> printMOTD();
        };
    }

