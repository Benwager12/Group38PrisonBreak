package com.group38.prisonbreak.controllers;

import com.group38.prisonbreak.Profile;
import com.group38.prisonbreak.utilities.ProfileUtilities;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.util.Arrays;

public class SelectProfileController {

    @FXML
    public ImageView leftArrowButton;

    @FXML
    public ImageView rightArrowButton;

    @FXML
    public Label profileName1;

    @FXML
    public Label profileName2;

    @FXML
    public Label profileName3;

    @FXML
    public StackPane stackPane1;

    @FXML
    public StackPane stackPane2;

    @FXML
    public StackPane stackPane3;


    private int profileOffset = 0;

    @FXML
    public ImageView faceImage1;

    @FXML
    public ImageView faceImage2;

    @FXML
    public ImageView faceImage3;

    @FXML
    public void initialize() {
        displayProfiles();
    }



    private void displayProfiles() {
        Profile[] profiles = ProfileUtilities.getProfiles();

        if (profiles.length > profileOffset) {
            profileName1.setText(profiles[profileOffset].getName());

        } else {
            stackPane1.setOpacity(0);
            stackPane1.setDisable(true);
        }

        if (profiles.length > profileOffset + 1) {
            profileName2.setText(profiles[profileOffset + 1].getName());
        } else {
            stackPane2.setOpacity(0);
            stackPane2.setDisable(true);
        }

        if (profiles.length > profileOffset + 2) {
            profileName3.setText(profiles[profileOffset + 2].getName());
        } else {
            stackPane3.setOpacity(0);
            stackPane3.setDisable(true);
        }
    }

    public void rightArrowClicked(MouseEvent mouseEvent) {
        profileOffset++;

        if (ProfileUtilities.getNoProfiles() - 3 == profileOffset) {
            profileOffset--;
        }
        displayProfiles();
    }


    public void leftArrowClicked(MouseEvent mouseEvent) {
        leftArrowButton.setVisible(!(profileOffset == 0));
        leftArrowButton.setDisable(profileOffset == 0);

        profileOffset--;

        if (profileOffset < 0) {
            profileOffset = 0;
        }
        displayProfiles();
    }

    public void profileSelect(MouseEvent mouseEvent) {
        if (mouseEvent.getEventType() != MouseEvent.MOUSE_CLICKED) {
            System.out.println("not gonna happen chief");
            return;
        }

        if (!(mouseEvent.getSource() instanceof StackPane sp)) {
            System.out.println("kamsdkdsi");
            return;
        }

        int selectNumber = Integer.parseInt(sp.getId().substring(10)) - 1;
        selectNumber += profileOffset;

        Profile profile = ProfileUtilities.getProfiles()[selectNumber];
        System.out.printf("Name: %s%n", profile.getName());
    }
}