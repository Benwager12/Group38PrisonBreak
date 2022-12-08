package com.group38.prisonbreak.controllers;

import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.MOTD;
import com.group38.prisonbreak.utilities.FileUtilities;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Scanner;

/**
 * MenuController handles the...[add]
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
    private ImageView motdSpeechBubble;

    @FXML
    private Text motdTitle;

    @FXML
    private Text motdTextBox;

    private static String message;

    @FXML
    private void initialize() {
        // button animation
        newGameImage.hoverProperty().addListener(animateButton(newGameImage));
        loadGameImage.hoverProperty().addListener(animateButton(loadGameImage));
        exitImage.hoverProperty().addListener(animateButton(exitImage));

        // [add]
        gateImage.hoverProperty().addListener(startMOTDListener(gateImage));
        motdSpeechBubble.setVisible(false);
        motdTitle.setVisible(false);
        motdTextBox.setVisible(false);
        gateImage.hoverProperty().addListener(startMOTDListener(gateImage));
    }

    @FXML
    private void newGameClicked(MouseEvent actionEvent){
        FileUtilities.getGameInstance().setRoot("profile");
    }

    @FXML
    private void loadGameClicked(MouseEvent actionEvent){
        FileUtilities.getGameInstance().setRoot("levelMenu");
    }

    @FXML
    private void tempLeaderboardClicked(MouseEvent actionEvent){
        FileUtilities.getGameInstance().setRoot("leaderboard");
    }

    @FXML
    private void exitLevel(MouseEvent actionEvent) {
        actionEvent.consume();
        System.exit(0);
        //Needs to save info to file
    }


    @FXML
    private void printMOTD(){
        MOTD motd = new MOTD();
        try {
            message = motd.getMessageOfTheDay();
            motdTextBox.setText(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) { //This was suggested by intellj ???
            throw new RuntimeException(e);
        }
    }
    /** [draft]
     * Animates button when applicable.
     * @param img the button to be animated
     * @return rotated/unrotated button depending on situation
     */
    private static ChangeListener<Boolean> animateButton(ImageView img) {
        return (observable, oldValue, newValue) -> {
            if (observable.getValue()) {
                img.setRotate(MODIFIED_BUTTON_ROTATION);
            } else {
                img.setRotate(ORIGINAL_BUTTON_ROTATION);
            }
        };
    }
    private ChangeListener<Boolean> startMOTDListener(ImageView img) {
        motdTitle.setVisible(true);
        motdTextBox.setVisible(true);
        motdSpeechBubble.setVisible(true);
        return (observable, oldValue, newValue) -> printMOTD();

        };
    }

