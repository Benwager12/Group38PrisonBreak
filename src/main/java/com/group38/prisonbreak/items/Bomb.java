package com.group38.prisonbreak.items;

import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.utilities.Item;

public class Bomb extends Item {

    @Override
    public void interact() {
        GameManager.time = 3;
    }

}
