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

        ImageView[] rotateButtons = new ImageView[]{homeImage, crossImage, profileCross1, profileCross2, profileCross3};
        for (ImageView rb : rotateButtons) {
            rb.hoverProperty().addListener(rotateButton(rb));
        }

        rightArrowButton.setVisible(ProfileUtilities.getNoProfiles() > 3);
        rightArrowButton.setDisable(ProfileUtilities.getNoProfiles() <= 3);
    }



    private void displayProfiles() {
        Profile[] profiles = ProfileUtilities.getProfiles();
        StackPane[] selectPanes = new StackPane[] {selectPane1, selectPane2, selectPane3};

        for (int item = 0; item < selectPanes.length; item++) {
            selectPanes[item].setVisible(profiles.length > profileOffset + item);
            selectPanes[item].setDisable(profiles.length <= profileOffset + item);

            if (profiles.length > profileOffset + item) {
                Label profileLabel = (Label) selectPanes[item].getChildren().get(1);
                String profileName = profiles[profileOffset + item].getName();
                profileLabel.setText(profileName);
            }
        }
    }

    public void rightArrowClicked(MouseEvent ignoredMouseEvent) {
        profileOffset = Math.min(ProfileUtilities.getNoProfiles() - 3, profileOffset + 1);

        displayButtons();
        displayProfiles();
    }

    private void displayButtons() {
        leftArrowButton.setVisible(profileOffset != 0);
        leftArrowButton.setDisable(profileOffset == 0);

        rightArrowButton.setVisible(profileOffset + 3 != ProfileUtilities.getNoProfiles());
        rightArrowButton.setDisable(profileOffset + 3 == ProfileUtilities.getNoProfiles());
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
        Profile profile = ProfileUtilities.getProfiles()[getProfileFromButtonNumber(selectedItem)];

        GameManager.currentProfileId = profile.getId();
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
        int highestLevel = ProfileUtilities.getLevelFromProfile(profileId + 1);
        System.out.printf("You clicked on \"%s\", they have an ID of %d, their highest level is %d. %n",
                name, profileId + 1, highestLevel);
    }
}