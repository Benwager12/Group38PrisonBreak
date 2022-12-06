package com.group38.prisonbreak;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


/** This class fetches the Message of the day from CSWEBCAT.SAWANSEA.AC.UK using HTTP GET Requests.
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

            if ((i % 2) != 0) {
            // This circles back Z back to A
                if ((asciiTemp + (i+1))> 90) { // 90 is Z
                    asciiTemp = 64 + ((asciiTemp + (i+1)) - 90); // 64 is A
                }
                else {
                    asciiTemp += (i+1);
                }

            }
            else {

                if ((asciiTemp - (i+1))< 65) {
                    asciiTemp = 91 - (65 - (asciiTemp - (i+1)));
                }
                else {
                    asciiTemp -= (i+1);
                }
            }
            solution = solution + (char) asciiTemp;
        }

        solution = solution + "CS-230";
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

        //System.out.println(finalMessage);
        return finalMessage;
    }


}




