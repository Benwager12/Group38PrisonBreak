package com.group38.prisonbreak;

/**
 * Implementation of a Player Profile
 * @author Daniel Banks (2107922)
 * @since 02/12/2022
 */
public class Profile {

    /** Id number of the player */
    private final int id;

    /** Name of the player */
    private String name;

    /** Highest level they've completed */
    private int highestLevel;

    /**
     * Creates an instance of Profile
     * @param id Id number
     * @param name Name of the player
     *
     * @author Daniel Banks
     * @since 02/12/2022
     */
    public Profile(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Creates an instance of Profile
     * @param id Id number
     * @param name Name of the player
     * @param highestLevel Highest Completed Level
     *
     * @author Daniel Banks
     * @since 02/12/2022
     */
    public Profile(int id, String name, int highestLevel) {
        this.id = id;
        this.name = name;
        this.highestLevel = highestLevel;
    }

    /**
     * Gets id
     * @return int Id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets player's Name
     * @return String representation name
     * @author Daniel Banks
     * @since 02/12/2022
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the highest Level Completed
     * @return The highest level in the form as an integer
     * @author Daniel Banks
     * @since 02/12/2022
     */
    public int getHighestLevel() {
        return highestLevel;
    }

    /**
     * Sets the highest level completed
     * @param highestLevel Highest Level completed
     * @author Daniel Banks
     * @since 02/12/2022
     */
    public void setHighestLevel(int highestLevel) {
        this.highestLevel = highestLevel;
    }

    /**
     * Sets the players name
     * @param name Name of the player
     * @author Daniel Banks
     * @since 02/12/2022
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get a string representation of the profile
     * @return String representation of profile
     *
     * @author Ben Wager
     * @since 10/12/2022
     */
    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", highestLevel=" + highestLevel +
                '}';
    }
}
