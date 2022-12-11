package com.group38.prisonbreak;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;


/**
 * This class fetches the Message of the day.
 * From CSWEBCAT.SWANSEA.AC.UK using HTTP GET Requests
 * @author Issa (853846)
 * @since 29/11/2022
 */


public class MOTD {

    /** URL of the web request to get message of the day. */
    private static final String URL
            = "http://cswebcat.swansea.ac.uk/message?solution=";

    /** Module code to be added to the end of the message of the day. */
    private static final String MODULE_CODE = "CS-230";

    /** Ascii value that represents that last ascii letter (Z). */
    private static final int LAST_ASCII_VALUE = 90;

    /** Ascii value that represents the first ascii letter (A). */
    private static final int FIRST_ASCII_VALUE = 65;

    /**
     * Publicly accessible get method for the message of the day.
     * @return A string with the message of the day and its time stamp.
     */
    public String getMessageOfTheDay() throws IOException, InterruptedException {
        String puzzle = getPuzzle();
        return getFinalMessage(solvePuzzle(puzzle));
    }


    /**
     * Private method to fetch the puzzle from the server.
     * Updates the puzzle variable with the string received from the server
     * @return
     */
    private static String getPuzzle() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://cswebcat.swansea.ac.uk/puzzle"))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();

    }


    /**
     * Private method to solve the puzzle received from the server.
     * Solves the puzzle using the Ceaser Cipher used.
     * @param puzzle The Puzzle received from the server.
     * @return A string of the solution.
     */
    private static String solvePuzzle(String puzzle) {
        StringBuilder solution =
                new StringBuilder(
                        String.valueOf(puzzle.length() + MODULE_CODE.length())
                );

        for (int i = 0; i < puzzle.length(); i++) {

            int asciiTemp = puzzle.charAt(i);

            // if the index is odd, starting index counting at 1
            if ((i % 2) != 0) {
            // This circles back Z back to A
                //check if shifting the character forward needs to start from A again
                if ((asciiTemp + (i + 1)) > LAST_ASCII_VALUE) {
                    //shifts the character forward till Z, and remaining number of shifts start from A
                    asciiTemp = FIRST_ASCII_VALUE - 1
                            + ((asciiTemp + (i + 1)) - LAST_ASCII_VALUE);
                } else {
                    //shift the character forward by the number of index
                    asciiTemp += (i + 1);
                }

            } else {

                //check if shifting the character backwards needs to start from Z again
                if ((asciiTemp - (i + 1)) < FIRST_ASCII_VALUE) {
                    //shifts the character backwards till A, and remaining number of shifts start from Z
                    asciiTemp = LAST_ASCII_VALUE + 1
                            - (FIRST_ASCII_VALUE - (asciiTemp - (i + 1)));
                } else {
                    //shift the character backwards by the number of index
                    asciiTemp -= (i + 1);
                }
            }
            //add shifted character to solution string
            solution.append((char) asciiTemp);
        }

        //append CS-230 at end of solution string
        solution.append(MODULE_CODE);
        return solution.toString();
    }



    /**
     * Private method to fetch the message of the day from server using the solution to the puzzle
     * Sends get request to the server with solution passed in the URL.
     * @param solution The String solution to be used in the URL
     * @return A string containing the message of the day from server and the time stamp.
     * @exception IOException Throws if send request to Http errors out
     * @exception InterruptedException If the send request is interrupted
     */
    private static String getFinalMessage(String solution)
            throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL + solution))
                .build();

        HttpResponse<String> response
                = client.send(request, HttpResponse.BodyHandlers.ofString());

        String finalMessage = response.body();
        return removeDateTime(finalMessage);
    }

    /**
     * Removes date and time from MoTD
     * @param finalMessage message of the day
     * @return message without date
     */
    public static String removeDateTime(String finalMessage) {
        Scanner in = new Scanner(finalMessage);
        //stop by braces as date documentation starts with brace
        in.useDelimiter("\\(");
        return in.next();
    }

}




