package com.group38.prisonbreak;

import java.io.IOException;

public class TestMOTD {
    public static void main(String[] args) throws IOException, InterruptedException {
        MOTD motd = new MOTD();
        System.out.println(motd.getMessageOfTheDay());
    }
}
