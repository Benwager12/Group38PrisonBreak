package com.group38.prisonbreak.utilities;

import com.group38.prisonbreak.Profile;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Utility that stores and deals with Player Profiles
 * @author Daniel Banks (2107922)
 */
public class ProfileUtilities {

    // Location of the .txt file that stores the profiles
    private static final String FILE_LOCATION = "data/Profiles.txt";

    // Arraylist of Profiles
    private static final ArrayList<Profile> PROFILES = new ArrayList<>();

    // Format of the Profiles.txt file "<id>, <name>, <highest level>"
    private static final String TXT_STRING_FORMAT = "%d, %s, %d%n";

    /**
     * Initialises all the profiles
     */
    public static void initialise() {
        loadProfiles();
    }

    /**
     * Loads player profiles from Profiles.txt
     */
    private static void loadProfiles() {
        String filePath = FileUtilities.getResourcePath(FILE_LOCATION);

        if (System.getProperty("os.name").equals("Mac OS X")) {
            filePath = "/" + filePath;
        }
        File file = new File(filePath);

        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("Can't find Profiles file");
            e.printStackTrace();
            System.exit(-1);
        }
        readInfo(scanner);
        scanner.close();
    }

    /**
     * Reads player profile information from a scanner
     * @param in Scanner containing profile information
     */
    private static void readInfo(Scanner in) {
        while (in.hasNextLine()) {
            String[] profile = in.nextLine().split(", ");
            PROFILES.add(initProfile(profile));
        }
    }

    /**
     * Creates an instance of a profile class
     * @param profileValues String array of values in the form [id, name, highest Level]
     * @return Profile
     */
    private static Profile initProfile(String[] profileValues) {
        if (profileValues.length < 3) {
            return new Profile(Integer.parseInt(profileValues[0]), profileValues[1]);
        }
        return new Profile(Integer.parseInt(profileValues[0]), profileValues[1], Integer.parseInt(profileValues[2]));
    }

    /**
     * Gets all the profiles
     * @return Profile array
     */
    public static Profile[] getProfiles() {
        return PROFILES.toArray(new Profile[PROFILES.size()]);
    }

    /**
     * Gets all the player names from all the stored profiles
     * @return String array of profile names
     */
    public static String[] getProfileNames() {
        String[] names = new String[PROFILES.size()];
        for (int i = 0; i < PROFILES.size(); i++) {
            names[i] = PROFILES.get(i).getName();
        }
        return names;
    }

    /**
     * Gets the highest level a player has played from their name
     * @param name player name of the profile
     * @return int The Highest Level completed
     */
    public static int getLevelFromProfile(String name) {
        for (Profile profile : PROFILES) {
            if (profile.getName().equals(name)) {
                return profile.getHighestLevel();
            }
        }
        return 0;
    }

    /**
     * Updates a player's details
     * @param id Id of the player's profile
     * @param levelNumber New highest level completed
     */
    public static void updateProfile(int id, int levelNumber) {
        Profile profile = PROFILES.get(id);
        if (profile != null) {
            profile.setHighestLevel(levelNumber);
        }
    }

    /**
     * Updates a player's details
     * @param id Id of the player's profile
     * @param name New name of the player
     */
    public static void updateProfile(int id, String name) {
        Profile profile = PROFILES.get(id);
        profile.setName(name);
    }

    /**
     * Updates a player's details
     * @param id Id of the player's profile
     * @param name New name of the player
     * @param levelNumber New highest level completed
     */
    public static void updateProfile(int id, String name, int levelNumber) {
        Profile profile = PROFILES.get(id);
        profile.setName(name);
        profile.setHighestLevel(levelNumber);
    }

    public static void addProfile(String name, int levelNumber) {
        PROFILES.add(new Profile(PROFILES.size(), name, levelNumber));
    }

    public static void addProfile(String name) {
        PROFILES.add(new Profile(PROFILES.size(), name));
    }

    /**
     * Saves the current stored Profile classes in Profile.txt
     * NOTE: THIS WILL OVERRIDE EVERYTHING THAT'S CURRENTLY IN PROFILE.TXT
     */
    public static void saveProfiles() {
        try {
            PrintWriter myWriter = new PrintWriter(FileUtilities.getResourcePath(FILE_LOCATION));
            System.out.println(FileUtilities.getResourcePath(FILE_LOCATION));

            for (Profile profile : PROFILES) {
                String profileData = String.format(TXT_STRING_FORMAT, profile.getId(),
                        profile.getName(),
                        profile.getHighestLevel()
                );
                myWriter.write(profileData);
            }
            myWriter.close();

        } catch (IOException e) {
            System.out.println("Can't find Profiles file");
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
