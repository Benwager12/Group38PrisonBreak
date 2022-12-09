package com.group38.prisonbreak.controllers;

import com.group38.prisonbreak.Profile;
import com.group38.prisonbreak.utilities.FileUtilities;
import com.group38.prisonbreak.utilities.ProfileUtilities;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class SelectProfileController {
    private static final double ORIGINAL_BUTTON_ROTATION = 0;
    private static final double MODIFIED_BUTTON_ROTATION = 1.7;

    @FXML
    private ImageView homeImage;

    @FXML
    private ImageView crossImage;

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
    public StackPane selectPane1;

    @FXML
    public StackPane selectPane2;

    @FXML
    public StackPane selectPane3;

    @FXML
    private ImageView profileCross1;

    @FXML
    private ImageView profileCross2;

    @FXML
    private ImageView profileCross3;


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
        homeImage.hoverProperty().addListener(rotateButton(homeImage));
        crossImage.hoverProperty().addListener(rotateButton(crossImage));
        profileCross1.hoverProperty().addListener(rotateButton(profileCross1));
        profileCross2.hoverProperty().addListener(rotateButton(profileCross2));
        profileCross3.hoverProperty().addListener(rotateButton(profileCross3));
    }



    private void displayProfiles() {
        Profile[] profiles = ProfileUtilities.getProfiles();

        if (profiles.length > profileOffset) {
            profileName1.setText(profiles[profileOffset].getName());

        } else {
            selectPane1.setOpacity(0);
            selectPane1.setDisable(true);
        }

        if (profiles.length > profileOffset + 1) {
            profileName2.setText(profiles[profileOffset + 1].getName());
        } else {
            selectPane2.setOpacity(0);
            selectPane2.setDisable(true);
        }

        if (profiles.length > profileOffset + 2) {
            profileName3.setText(profiles[profileOffset + 2].getName());
        } else {
            selectPane3.setOpacity(0);
            selectPane3.setDisable(true);
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
        FileUtilities.getGameInstance().setRoot("levelMenu");
    }

    @FXML
    private void homeClicked(MouseEvent actionEvent) {
        FileUtilities.getGameInstance().setRoot("mainMenu");
    }

    @FXML
    private void crossClicked(MouseEvent click) {
        click.consume();
        System.exit(0);
    }

    /**
     * Rotates button when applicable.
     * @param img the button to be rotated
     * @return rotated/unrotated button depending on situation
     */
    private static ChangeListener<Boolean> rotateButton(ImageView img) {
        return (observable, oldValue, newValue) -> {
            if (observable.getValue()) {
                img.setRotate(MODIFIED_BUTTON_ROTATION);
            } else {
                img.setRotate(ORIGINAL_BUTTON_ROTATION);
            }
        };
    }
}