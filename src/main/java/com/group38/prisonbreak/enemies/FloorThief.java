package com.group38.prisonbreak.enemies;

import com.group38.prisonbreak.utilities.Enemy;

public class FloorThief extends Enemy {

    public FloorThief(int xPos, int yPos, int direction) {
        super(xPos, yPos, direction);
    }

    public void doNothing() {
        int x = 10;
        for (int i = 0; i < x; i++) {
            System.out.println("Helllllllllloooooooooo");
        }
    }
}
