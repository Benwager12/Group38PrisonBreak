package com.group38.prisonbreak.utilities;

import com.group38.prisonbreak.Profile;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
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
    private static final String TXT_STRING_FORMAT = "%s, %d%n";

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
        int id = 1;
        while (in.hasNextLine()) {
            String[] profile = in.nextLine().split(", ");
            String name = profile[0];

            if (profile.length != 1) {
                int highestLevel = Integer.parseInt(profile[1]);
                PROFILES.add(new Profile(id, name, highestLevel));
            } else {
                PROFILES.add(new Profile(id, name));
            }
            id++;
        }
    }

    /**
     * Gets all the profiles
     * @return Profile array
     */
    public static Profile[] getProfiles() {
        return PROFILES.toArray(new Profile[0]);
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
     * Gets the highest level of the player from their ID
     * @param id The ID of the player
     * @return The highest level of the player
     */
    public static int getLevelFromProfile(int id) {
        for (Profile profile : PROFILES) {
            if (profile.getId() == id) {
                return profile.getHighestLevel();
            }
        }
        return 0;
    }

    /**
     * Gets a hashmap of Profile id's to profile names
     * @return Hashmap of id's to profiles
     */
    public static HashMap<Integer, String> getProfileMap() {
        HashMap<Integer, String> playerHashMap = new HashMap<>();
        for (Profile profile : PROFILES) {
            playerHashMap.put(profile.getId(), profile.getName());
        }
        return playerHashMap;
    }

    private static Profile getProfileFromId(int id) {
        for (Profile profile : PROFILES) {
            if (profile.getId() == id) {
                return profile;
            }
        }
        return null;
    }

    /**
     * Updates a player's details
     * @param id Id of the player's profile
     * @param levelNumber New highest level completed
     */
    public static void updateProfile(int id, int levelNumber) {
        Profile profile = PROFILES.get(id);
        if (profile != null) {
            if (levelNumber > profile.getHighestLevel()) {
                profile.setHighestLevel(levelNumber);
            }
        }
    }

    /**
     * Updates a player's details
     * @param id Id of the player's profile
     * @param name New name of the player
     */
    public static void updateProfile(int id, String name) {
        Profile profile = PROFILES.get(id);
        profile.setName(name); //Would this be  NewProfileController.getProfileName() in here instead of name now?
    }

    /**
     * Updates a player's details
     * @param id Id of the player's profile
     * @param name New name of the player
     * @param levelNumber New highest level completed
     */
    public static void updateProfile(int id, String name, int levelNumber) {
        Profile profile = PROFILES.get(id);
        profile.setName(name); //Would this be  NewProfileController.getProfileName() in here instead of name now?
        profile.setHighestLevel(levelNumber);
    }

    /**
     * Adds a profile to the profile list with the level they last completed
     * @param name Name of the profile
     * @param levelNumber Highest Level that profile has created
     */
    public static void addProfile(String name, int levelNumber) {
        PROFILES.add(new Profile(PROFILES.size() + 1, name, levelNumber));
    }

    /**
     * Adds a blank profile to the profile list
     * @param name Name of the profile
     */
    public static void addProfile(String name) {
        PROFILES.add(new Profile(PROFILES.size() + 1, name));
    } //Would this be  NewProfileController.getProfileName() in here instead of name now?

    /**
     * Gets the name of a profile from the id
     * @param id id of the Profile
     * @return String name
     */
    public static String getName(int id) {
        return PROFILES.get(id).getName();
    }

    /**
     * Gets the number of profiles
     * @return int amount of profiles
     */
    public static int getNoProfiles() {
        return PROFILES.size();
    }

    /**
     * Removes a profile with the given id
     * @param id int id of the profile to be removed
     */
    public static void removeProfile(int id) {
        // Removes the profile if it has the id of the given id
        PROFILES.removeIf(p -> p.getId() == id);
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
                if (!profile.getName().equals("")) {
                    String profileData = String.format(TXT_STRING_FORMAT,
                            profile.getName(),
                            profile.getHighestLevel()
                    );
                    myWriter.write(profileData);
                }
            }
            myWriter.close();

        } catch (IOException e) {
            System.out.println("Can't find Profiles file");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Increment level number
     */
    public static void incrementLevelNumber(int profileId) {
        Profile usrProfile = getProfileFromId(profileId);
        if (usrProfile == null) {
            return;
        }

        int level = usrProfile.getHighestLevel();
        updateProfile(profileId, level + 1);
    }
}
