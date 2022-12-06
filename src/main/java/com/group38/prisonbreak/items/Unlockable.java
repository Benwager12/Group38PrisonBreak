package com.group38.prisonbreak.items;

import com.group38.prisonbreak.utilities.FileUtilities;
import javafx.scene.image.Image;

/**
 * Implements an item that can be unlocked in the game
 * @author Daniel Banks
 */
public class Unlockable {

    // Name of the unlockable
    private final String itemName;

    // Image of the item
    private final Image image;

    /**
     * Creates an instance of an unlockable
     * @param itemName Name of the unlockable
     * @param imageFile Path of the image of the unlockable
     */
    Unlockable (String itemName, String imageFile) {
        this.itemName = itemName;
        this.image = FileUtilities.loadImageFromResource(imageFile);
    }

    /**
     * Gets the name of the unlockable
     * @return String item name
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Gets the image of the unlockable
     * @return JavaFX Image
     */
    public Image getImage() {
        return image;
    }
}
