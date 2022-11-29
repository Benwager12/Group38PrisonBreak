package com.group38.prisonbreak.utilities;

public class Player extends Entity {

    public Player(int xPos, int yPos, int direction) {

        super(xPos, yPos, direction);
    }

    @Override
    public void move() {
        itemInteract();
    }

    @Override
    protected void itemInteract() {
        Item item = getCurrentTile().getItem();
        if (item != null) {
            if (item.interact(true)) {
                getCurrentTile().removeItem();
            }
        }
    }
}
