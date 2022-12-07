package com.group38.prisonbreak.utilities;

import com.group38.prisonbreak.GameManager;

import java.io.File;
import java.util.HashMap;

/**
 * Implements a Player in the game
 * @author Daniel Banks (2107922), Matthew Salter (986488)
 */
public class Player extends Entity {

    private static final HashMap<Integer, String> imagePathCache = new HashMap<>() {{
        put(1, "outfit_jumpsuit");
        put(2, "outfit_38_jumper");
        put(3, "outfit_tuxedo");
        put(4, "outfit_tank_top");
        put(5, "outfit_suit");
        put(6, "outfit_stripey_fit");
        put(7, "outfit_guard");
        put(8, "outfit_xmas");
    }};

    /**
     * Creates and instance of player
     * @param xPos X position of the player
     * @param yPos Y position of the player
     * @param direction starting direction of the player
     */
    public Player(int xPos, int yPos, int direction, int outfitIndex) {
        super(xPos, yPos, direction);
        setEntityImage(imagePathCache.get(outfitIndex));
    }

    @Override
    public void move() {
        int[] newPos = GameManager.level.moveTo(super.getX(), super.getY(), super.getDirection());
        super.setX(newPos[0]);
        super.setY(newPos[1]);
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
