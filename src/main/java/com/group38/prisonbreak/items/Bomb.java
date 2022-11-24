package com.group38.prisonbreak.items;

import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.utilities.Item;

public class Bomb extends Item {

    /* The time left when the player activates the bomb */
    private final int BOMB_ACTIVATE_TIME = 3;

    @Override
    public void interact() {
        GameManager.time = BOMB_ACTIVATE_TIME;
    }

}
