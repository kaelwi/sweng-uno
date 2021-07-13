package test;

import main.Card;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class CardTest {

    // @Before (annotation for setup if needed)
    // assertEquals (both in parenthesis have to be same)
    // assertTrue (return value or value in parenthesis has to be true)
    // assertFalse (same as above, but it has to be false)

    @Test
    public void testGetAllColors() {
        Card card = new Card("R", "0", 0, "0");
        String[] expected = {"R", "G", "B", "Y", ""};
        assertArrayEquals(expected, card.getAllColors());
    }

    @Test
    public void testGetAllValues() {
        Card card = new Card("R", "0", 0, "0");
        String[] expected = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "+2", "<->", "X", "W", "W+4"};
        assertArrayEquals(expected, card.getAllValues());
    }

    @Test
    public void testGetAllPoints() {
        Card card = new Card("R", "0", 0, "0");
        int[] expected = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 20, 50};
        assertEquals(expected, card.getAllValues());
    }

    @Test
    public void testgetColorCodeCollection() {
        Card card = new Card("R", "0", 0, "0");
        String[] expected = {"\033[0;31m", "\033[0;32m", "\033[0;34m", "\033[0;33m", "\033[0m"};
        assertArrayEquals(expected, card.getAllValues());
    }

    @Test
    public void testGetColorValue() {
        Card card = new Card("R", "0", 0, "0");
        assertEquals("R0", card.getColorValue());
    }

    @Test
    public void testGetColor() {
        Card card = new Card("R", "0", 0, "0");
        assertEquals("R", card.getColor());
    }

    @Test
    public void testGetValue() {
        Card card = new Card("R", "0", 0, "0");
        assertEquals("0", card.getValue());
    }

    @Test
    public void testGetPoints() {
        Card card = new Card("R", "0", 0, "0");
        assertEquals(0, card.getPoints());
    }

    @Test
    public void testCheckColorCode() {
        Card card = new Card("G", "0", 0, "0");
        assertEquals("\033[0;32m", card.checkColorCode("G"));
    }
}
