package com.group38.prisonbreak.utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


/**
 * Utility that stores and deals with unlockable items
 * @author Daniel Banks (2107922)
 */
public class UnlockableUtilities {

    // File locations of all the different unlockables/outfits
    private static final String IMAGE_LOCATIONS = "images/PlayerOutfits/outfit_%s.png";

    // Hashmap of Arraylist of unlockables where the key is the level the item is unlocked at
    private static final HashMap<Integer, ArrayList<Unlockable>> UNLOCKABLES = new HashMap<>() {{
        put(1, new ArrayList<Unlockable>(Arrays.asList(
                new Unlockable("Stripped Prisoner", String.format(IMAGE_LOCATIONS, "stripey_fit")))));
        put(2, new ArrayList<Unlockable>(Arrays.asList(
                new Unlockable("Tank Top", String.format(IMAGE_LOCATIONS, "tank_top")))));
        put(3, new ArrayList<Unlockable>(Arrays.asList(
                new Unlockable("Suit", String.format(IMAGE_LOCATIONS, "suit")))));
        put(7, new ArrayList<Unlockable>(Arrays.asList(
                new Unlockable("Guard", String.format(IMAGE_LOCATIONS, "guard")))));
        put(8, new ArrayList<Unlockable>(Arrays.asList(
                new Unlockable("Xmas", String.format(IMAGE_LOCATIONS, "xmas")))));
    }};

    /**
     * Gets all the items unlocked at a certain level
     * @param levelNumber level number
     * @return Array of Unlockable
     */
    public static Unlockable[] getUnlocked(int levelNumber) {
        ArrayList<Unlockable> unlocked = new ArrayList<>();
        for (int i = 1; i < levelNumber + 1; i++) {
            if (UNLOCKABLES.containsKey(i)) {
                unlocked.addAll(UNLOCKABLES.get(i));
            }
        }
        return unlocked.toArray(new Unlockable[0]);
    }


    /**
     * Gets all the items that can be unlocked
     * @return Array of unlockable
     */
    public static Unlockable[] getAllUnlockables() {
        ArrayList<Unlockable> unlocked = new ArrayList<>();

        for (ArrayList<Unlockable> items : UNLOCKABLES.values()) {
            unlocked.addAll(items);
        }

        return unlocked.toArray(new Unlockable[0]);
    }
}
