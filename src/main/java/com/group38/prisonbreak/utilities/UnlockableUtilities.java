package com.group38.prisonbreak.utilities;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Utility that stores and deals with unlockable items
 * @author Daniel Banks (2017922)
 */
public class UnlockableUtilities {

    // Hashmap of Arraylist of unlockables where the key is the level the item is unlocked at
    private static final HashMap<Integer, ArrayList<Unlockable>> UNLOCKABLES = new HashMap<>();

    /**
     * Gets all the items unlocked at a certain level
     * @param levelNumber level number
     * @return Array of Unlockable
     */
    public static Unlockable[] getUnlocked(int levelNumber) {
        ArrayList<Unlockable> unlocked = new ArrayList<>();
        //Unlockable[] unlocked = new Unlockable[levelNumber];
        for (int i = 1; i < levelNumber + 1; i++) {
            unlocked.addAll(UNLOCKABLES.get(i - 1));
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
