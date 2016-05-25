package com.crossover.robotwrapper;

/**
 * Created by markmicallef on 24/05/2016.
 */
public class Utils {

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
        }
    }
}
