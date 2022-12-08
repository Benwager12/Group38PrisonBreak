package com.group38.prisonbreak;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;


/** This class fetches the Message of the day from CSWEBCAT.SWANSEA.AC.UK using HTTP GET Requests.
 * @author Issa (853846)
 */


public class MOTD {

    /** Represents the message of the day.
     */
    private String messageOfTheDay;
    /** Represents the puzzle received from the server.
     */
    public static String puzzle;


    /** Publicly accessible get method for the message of the day.
     * @return A string with the message of the day and its time stamp.
     */
    public String getMessageOfTheDay() throws IOException, InterruptedException {
        getPuzzle();
        messageOfTheDay = getFinalMessage(solvePuzzle(puzzle));
        return messageOfTheDay;
    }


    /** Private method to fetch the puzzle from the server
     * Updates the puzzle variable with the string received from the server
     */
    private static void getPuzzle() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://cswebcat.swansea.ac.uk/puzzle"))
                .build();

        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

        puzzle = response.body();

    }


    /** Private method to solve the puzzle received from the server
     * @param puzzle The Puzzle received from the server.
     * Solves the puzzle using the Ceaser Cipher used.
     * @return A string of the solution.
     */
    private static String solvePuzzle(String puzzle) {
        String solution = String.valueOf(puzzle.length()+6);

        for (int i = 0; i < puzzle.length(); i++) {

            char charTemp = puzzle.charAt(i);
            int asciiTemp = (int) charTemp;
            //System.out.println(asciiTemp);

            if ((i % 2) != 0) { // if the index is odd, starting index counting at 1
            // This circles back Z back to A
                if ((asciiTemp + (i+1))> 90) { //check if shifting the character forward needs to start from A again
                    asciiTemp = 64 + ((asciiTemp + (i+1)) - 90); //shifts the character forward till Z, and remaining number of shifts start from A
                }
                else {
                    asciiTemp += (i+1); //shift the character forward by the number of index
                }

            }
            else { //if the index is even, starting index counting at 1

                if ((asciiTemp - (i+1))< 65) { //check if shifting the character backwards needs to start from Z again
                    asciiTemp = 91 - (65 - (asciiTemp - (i+1))); //shifts the character backwards till A, and remaining number of shifts start from Z
                }
                else {
                    asciiTemp -= (i+1); //shift the character backwards by the number of index
                }
            }
            solution = solution + (char) asciiTemp; //add shifted character to solution string
        }

        solution = solution + "CS-230"; //append CS-230 at end of solution string
        return solution;
    }



    /** Private method to fetch the message of the day from server using the solution to the puzzle
     * @param solution The String solution to be used in the URL.
     * Sends get request to the server with solution passed in the URL.
     * @return A string containing the message of the day from server and the time stamp.
     */
    private static String getFinalMessage(String solution) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://cswebcat.swansea.ac.uk/message?solution=" + solution))
                .build();

        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

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




