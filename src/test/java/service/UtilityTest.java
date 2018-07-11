package service;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class UtilityTest {

    @Test
    public void checkId() {
        boolean result = Utility.checkId("12");
        assertTrue(result);
    }

    @Test
    public void stringParser() {
        String[] result = Utility.stringParser("ololo,,,    haha456$ha Boom!Boom!@#$%^&");
        String[] expected = {"ololo", "hahaha", "BoomBoom"};
        assertArrayEquals(expected, result);
    }

    @Ignore
    public void printList() {

    }
}