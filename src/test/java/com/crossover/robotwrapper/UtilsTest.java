package com.crossover.robotwrapper;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by markmicallef on 24/05/2016.
 */
public class UtilsTest {

    @Test
    public void testTwoSecondsWait()
    {
        long start = System.currentTimeMillis();
        Utils.sleep(2000);
        assertTrue(System.currentTimeMillis() > start + 2000);
    }


}
