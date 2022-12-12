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

/**
 * SelectProfileController is the controller for the
 * Select-profile.fxml.
 * @author Tiffany Oamen (2141570), Ben Wager (2108500)
 */
public class SelectProfileController {

    /** The original rotation of the button. */
    private static final double ORIGINAL_BUTTON_ROTATION = 0;

    /** The modified rotation of the button. */
    private static final double MODIFIED_BUTTON_ROTATION = 1.7;

    /** The amount of profiles to show at a given time. */
    private static final int PROFILES_SHOW = 3;

    /** Fx:id for the menu's home button image. */
    @FXML
    private ImageView homeImage;

    /** Fx:id for the menu's exit button image. */
    @FXML
    private ImageView crossImage;

    /** Fx:id for the menu's left navigating button image. */
    @FXML
    private ImageView leftArrowButton;

    /** Fx:id for the menu's right navigating button image. */
    @FXML
    private ImageView rightArrowButton;

    /** Stack pane for the first profile. */
    @FXML
    private StackPane selectPane1;

    /** Stack pane for the second profile. */
    @FXML
    private StackPane selectPane2;

    /** Stack pane for the third profile. */
    @FXML
    private StackPane selectPane3;

    /** Fx:id for the delete button on displayed profile 1. */
    @FXML
    private ImageView profileCross1;

    /** Fx:id for the delete button on displayed profile 2. */
    @FXML
    private ImageView profileCross2;

    /** Fx:id for the delete button on displayed profile 3. */
    @FXML
    private ImageView profileCross3;

    /** Offset of the profiles. */
    private int profileOffset = 0;

    /**
     * Triggers at the opening of the FXML file and creates listeners
     * for hover properties and displays the profiles.
     */
    @FXML
    public void initialize() {
        displayProfiles();

        /* Button animation */
        ImageView[] rotateButtons = new ImageView[] {
                homeImage,
                crossImage,
                profileCross1,
                profileCross2,
                profileCross3 };
        for (ImageView rb : rotateButtons) {
            rb.hoverProperty().addListener(rotateButton(rb));
        }

        rightArrowButton
                .setVisible(ProfileUtilities.getNoProfiles() > PROFILES_SHOW);
        rightArrowButton
                .setDisable(ProfileUtilities.getNoProfiles() <= PROFILES_SHOW);
    }

    /**
     * Navigate to the right through the stored profiles.
     * @param ignoredClick Triggered on mouse click.
     */
    public void rightArrowClicked(MouseEvent ignoredClick) {
        profileOffset =
                Math.min(ProfileUtilities.getNoProfiles()
                        - PROFILES_SHOW, profileOffset + 1);
        displayButtons();
        displayProfiles();
    }

    /**
     * Navigate to the left through the stored profiles.
     * @param ignoredClick Triggered on mouse click.
     */
    public void leftArrowClicked(MouseEvent ignoredClick) {
        profileOffset = Math.max(profileOffset - 1, 0);
        displayButtons();
        displayProfiles();
    }

    /**
     * Selection of the profile and re-direct the root to
     * the level selection pane.
     * @param click Triggered on the click of the mouse.
     */
    public void profileSelect(MouseEvent click) {
        if (!(click.getSource() instanceof ImageView iv)) {
            return;
        }

        String starter = "faceImage";

        int selectedItem = Integer.parseInt(iv.getId().substring(starter.length()));
        Profile profile =
                ProfileUtilities.
                        getProfiles()[getProfileFromButtonNumber(selectedItem)];

        GameManager.setCurrentProfileId(profile.getId());
        FileUtilities.getGameInstance().setRoot("levelMenu");
    }

    /**
     * Delete the profile from the list of profiles and the
     * corresponding stored data.
     * @param click Triggered on mouse click.
     */
    public void profileDeleteClick(MouseEvent click) {

        if (click.getEventType() != MouseEvent.MOUSE_CLICKED) {
            return;
        }

        if (!(click.getSource() instanceof ImageView iv)) {
            return;
        }

        String starter = "profileCross";

        int crossNumber = Integer.parseInt(iv.getId().substring(starter.length()));
        int profileNumber = getProfileFromButtonNumber(crossNumber);

        Profile selectedProfile = ProfileUtilities.getProfiles()[profileNumber];

        ProfileUtilities.removeProfile(selectedProfile.getId());
        ProfileUtilities.saveProfiles();

        if (profileOffset + PROFILES_SHOW > ProfileUtilities.getNoProfiles()
                && profileOffset > 1) {
            profileOffset--;
        }

        if (ProfileUtilities.getNoProfiles() == 0) {
            profileOffset = 0;
        }

        displayProfiles();
        displayButtons();
    }

    /**
     * Rotates button when applicable.
     * @param img The button to be rotated.
     * @return Rotated/non-rotated button depending on situation.
     */
    private static ChangeListener<Boolean> rotateButton(ImageView img) {
        return (observable, oldValue, newValue) -> {
            if (observable.getValue()) {
                /* Modify button position. */
                img.setRotate(MODIFIED_BUTTON_ROTATION);

            } else {
                /* Maintain original button position. */
                img.setRotate(ORIGINAL_BUTTON_ROTATION);
            }
        };
    }

    /**
     * Displays the profile images on the window.
     */
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

    /**
     * Display left/right arrows if there are more profiles to
     * the left or right that do not fit on the current screen.
     */
    private void displayButtons() {
        leftArrowButton.setVisible(profileOffset != 0);
        leftArrowButton.setDisable(profileOffset == 0);

        rightArrowButton
                .setVisible(profileOffset + PROFILES_SHOW
                        != ProfileUtilities.getNoProfiles()
                && ProfileUtilities.getNoProfiles() > PROFILES_SHOW);
        rightArrowButton
                .setDisable(profileOffset + PROFILES_SHOW
                        == ProfileUtilities.getNoProfiles()
                        && ProfileUtilities.getNoProfiles() <= PROFILES_SHOW);
    }

    /**
     * Returns profile id from selected button press number.
     * @param selectedNumber The number of the selected button that was pressed
     * @return The profile ID
     */
    private int getProfileFromButtonNumber(int selectedNumber) {
        return profileOffset + (selectedNumber - 1);
    }

    /**
     * On home image clicked, redirect to the main menu.
     * @param ignoredClick Trigger on mouse clicked.
     */
    @FXML
    private void homeClicked(MouseEvent ignoredClick) {
        FileUtilities.getGameInstance().setRoot("mainMenu");
    }

    /**
     * On cross image clicked, exit the game window.
     * @param click Trigger on mouse clicked.
     */
    @FXML
    private void crossClicked(MouseEvent click) {
        click.consume();
        GameManager.exitGame();
    }

}
