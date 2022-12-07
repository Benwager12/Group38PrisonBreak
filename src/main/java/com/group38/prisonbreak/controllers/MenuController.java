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

    public static Timeline motdTimeline;
    private static String message;

    /**
     * [add]
     */
    @FXML
    private void initialize() {
        newGameImage.hoverProperty().addListener(createHoverListener(newGameImage));
        loadGameImage.hoverProperty().addListener(createHoverListener(loadGameImage));
        exitImage.hoverProperty().addListener(createHoverListener(exitImage));
        gateImage.hoverProperty().addListener(startMOTDListener(gateImage));


    }

    @FXML
    private void newGameClicked(MouseEvent actionEvent){
        FileUtilities.getGameInstance().setRoot("profile");
    }

    @FXML
    private void loadGameClicked(MouseEvent actionEvent){
        FileUtilities.getGameInstance().setRoot("load");
    }

    @FXML
    private void exitLevel(MouseEvent actionEvent) {
        actionEvent.consume();
        System.exit(0);
    }

    /**
     * Sets all the cycles for the tick timelines
     */
    public void initTimelines() {
        motdTimeline.setCycleCount(Animation.INDEFINITE);
        playTimeLines();
        motdTimeline = new Timeline(new KeyFrame(Duration.millis(10), event -> printMOTD()));
    }

    /**
     * starts and plays the timelines
     */
    public static void playTimeLines(){
        motdTimeline.play();
    }

    @FXML
    private void printMOTD(){
        MOTD motd = new MOTD();
        try {
            message = motd.getMessageOfTheDay();
            motdTextBox.setText(message);
            //initTimelines();
            Scanner scan = new Scanner(message);
            for(int i = 0; i <message.length(); i++){

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) { //This was suggested by intellj ???
            throw new RuntimeException(e);
        }
    }
    /**
     * [add]
     * @param img
     * @return
     */
    private static ChangeListener<Boolean> createHoverListener(ImageView img) {
        return (observable, oldValue, newValue) -> {
            if (observable.getValue()) {
                img.setRotate(MODIFIED_BUTTON_ROTATION);
            } else {
                img.setRotate(ORIGINAL_BUTTON_ROTATION);
            }
        };
    }
    private ChangeListener<Boolean> startMOTDListener(ImageView img) {
        return (observable, oldValue, newValue) -> printMOTD();
        };
    }

