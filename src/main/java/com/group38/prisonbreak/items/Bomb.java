package com.group38.prisonbreak.items;

import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.utilities.Item;
import javafx.scene.canvas.GraphicsContext;

public class Bomb extends Item {

    public Bomb() {
        //Implement Constructor
    }

    /* The time left when the player activates the bomb */
    private final int BOMB_ACTIVATE_TIME = 3;

    @Override
    public boolean interact(boolean isPlayer) {
        if (GameManager.time > 3) {
            GameManager.time = BOMB_ACTIVATE_TIME;
        }
        return false;
    }

    @Override
    public String getImagePath() {
        return "images/items/bomb.png";
    }

    @Override
    public void draw(GraphicsContext g) {
        // Draw the bomb to screen
    }
}
