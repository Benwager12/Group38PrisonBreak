package com.group38.prisonbreak.controllers;

import com.group38.prisonbreak.GameManager;
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

    // The number of Profiles Shown
    private static final int PROFILES_SHOW = 3;

    @FXML
    private ImageView homeImage;

    @FXML
    private ImageView crossImage;

    @FXML
    private ImageView leftArrowButton;

    @FXML
    private ImageView rightArrowButton;

    @FXML
    private Label profileName1;

    @FXML
    private Label profileName2;

    @FXML
    private Label profileName3;

    @FXML
    private StackPane selectPane1;

    @FXML
    private StackPane selectPane2;

    @FXML
    private StackPane selectPane3;

    @FXML
    private ImageView profileCross1;

    @FXML
    private ImageView profileCross2;

    @FXML
    private ImageView profileCross3;


    private int profileOffset = 0;

    @FXML
    private ImageView faceImage1;

    @FXML
    private ImageView faceImage2;

    @FXML
    private ImageView faceImage3;

    @FXML
    public void initialize() {
        displayProfiles();

        // button animation
        ImageView[] rotateButtons = new ImageView[] {
                homeImage,
                crossImage,
                profileCross1,
                profileCross2,
                profileCross3 };
        for (ImageView rb : rotateButtons) {
            rb.hoverProperty().addListener(rotateButton(rb));
        }

        //
        rightArrowButton
                .setVisible(ProfileUtilities.getNoProfiles() > PROFILES_SHOW);
        rightArrowButton
                .setDisable(ProfileUtilities.getNoProfiles() <= PROFILES_SHOW);
    }



    private void displayProfiles() {
        Profile[] profiles = ProfileUtilities.getProfiles();
        StackPane[] selectPanes = new StackPane[] {
                selectPane1,
                selectPane2,
                selectPane3 };

        for (int item = 0; item < selectPanes.length; item++) {
            selectPanes[item]
                    .setVisible(profiles.length > profileOffset + item);
            selectPanes[item]
                    .setDisable(profiles.length <= profileOffset + item);

            if (profiles.length > profileOffset + item) {
                Label profileLabel =
                        (Label) selectPanes[item].getChildren().get(1);
                String profileName = profiles[profileOffset + item].getName();
                profileLabel.setText(profileName);
            }
        }
    }

    public void rightArrowClicked(MouseEvent ignoredMouseEvent) {
        profileOffset =
                Math.min(ProfileUtilities.getNoProfiles()
                        - PROFILES_SHOW, profileOffset + 1);

        displayButtons();
        displayProfiles();
    }

    private void displayButtons() {
        leftArrowButton.setVisible(profileOffset != 0);
        leftArrowButton.setDisable(profileOffset == 0);

        rightArrowButton
                .setVisible(profileOffset + PROFILES_SHOW
                        != ProfileUtilities.getNoProfiles());
        rightArrowButton
                .setDisable(profileOffset + PROFILES_SHOW
                        == ProfileUtilities.getNoProfiles());
    }

    public void leftArrowClicked(MouseEvent ignoredMouseEvent) {
        profileOffset = Math.max(profileOffset - 1, 0);
        displayButtons();
        displayProfiles();
    }

    public void profileSelect(MouseEvent mouseEvent) {
        if (mouseEvent.getEventType() != MouseEvent.MOUSE_CLICKED) {
            System.out.println("not gonna happen chief");
            return;
        }

        if (!(mouseEvent.getSource() instanceof ImageView iv)) {
            return;
        }

        int selectedItem = Integer.parseInt(iv.getId().substring(9));
        Profile profile =
                ProfileUtilities.
                        getProfiles()[getProfileFromButtonNumber(selectedItem)];

        GameManager.setCurrentProfileId(profile.getId());
        FileUtilities.getGameInstance().setRoot("levelMenu");
    }

    private int getProfileFromButtonNumber(int selectedNumber) {
        return profileOffset + (selectedNumber - 1);
    }

    @FXML
    private void homeClicked(MouseEvent ignoredActionEvent) {
        FileUtilities.getGameInstance().setRoot("mainMenu");
    }

    @FXML
    private void crossClicked(MouseEvent click) {
        click.consume();
        GameManager.exitGame();
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

    public void profileDeleteClick(MouseEvent mouseEvent) {
        if (!(mouseEvent.getSource() instanceof ImageView iv)) {
            return;
        }

        int crossNumber = Integer.parseInt(iv.getId().substring(12));
        int profileNumber = getProfileFromButtonNumber(crossNumber);

        Profile selectedProfile = ProfileUtilities.getProfiles()[profileNumber];

        ProfileUtilities.removeProfile(selectedProfile.getId());
        ProfileUtilities.saveProfiles();

        displayProfiles();
        displayButtons();
    }

    public void mouseClickName(MouseEvent mouseEvent) {
        if (!(mouseEvent.getSource() instanceof ImageView iv)) {
            return;
        }
        String clickedItem = iv.getId();
        int id = Integer.parseInt(clickedItem.substring(7));
        int profileId = getProfileFromButtonNumber(id);

        String name = ProfileUtilities.getName(profileId);
        int highestLevel =
                ProfileUtilities.getLevelFromProfile(profileId + 1);
    }
}
