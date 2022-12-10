package com.group38.prisonbreak.entities;

import com.group38.prisonbreak.GameManager;
import com.group38.prisonbreak.utilities.Entity;
import com.group38.prisonbreak.utilities.Item;
import javafx.scene.input.KeyCode;

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
        for (KeyCode c : GameManager.getCurrentlyPressed()) {
            if (c == KeyCode.UP || c == KeyCode.W) {
                setDirection(0);
                break;
            } else if (c == KeyCode.RIGHT || c == KeyCode.D) {
                setDirection(1);
                break;
            } else if (c == KeyCode.DOWN || c == KeyCode.S) {
                setDirection(2);
                break;
            } else if (c == KeyCode.LEFT || c == KeyCode.A) {
                setDirection(3);
                break;
            } else if (c == KeyCode.K) {
                System.out.println(GameManager.getLevel().isGateOpen(4));
                break;
            } else if (c == KeyCode.F) {
                GameManager.saveGame();
            }
        }

        int[] newPos = GameManager.getLevel().moveTo(super.getX(), super.getY(), super.getDirection());
        super.setX(newPos[0]);
        super.setY(newPos[1]);

        itemInteract();

        // Checks if player has collided with flying assassin
        if (CheckCollision()) {
            GameManager.endGame(false);
        }
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
