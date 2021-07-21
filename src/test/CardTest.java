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

    Card card = new Card("R", "0", 0, "0");
    Card card1 = new Card("G", "9", 9, "0");
    Card card2 = new Card("B", "2", 2, "0");

    @Test
    public void testGetAllColors() {
        String[] expected = {"R", "G", "B", "Y", ""};
        assertArrayEquals(expected, card.getAllColors());
    }

    @Test
    public void testGetAllValues() {
        String[] expected = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "+2", "<->", "X", "W", "W+4"};
        assertArrayEquals(expected, card.getAllValues());
    }

    @Test
    public void testGetAllPoints() {
        int[] expected = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 20, 50};
        assertArrayEquals(expected, card.getAllPoints());
    }

    @Test
    public void testGetColorCodeCollection() {
        String[] expected = {"\033[0;31m", "\033[0;32m", "\033[0;34m", "\033[0;33m", "\033[0m"};
        assertArrayEquals(expected, card.getColorCodeCollection());
    }

    @Test
    public void testGetColorValue() {
        assertEquals("R0", card.getColorValue());
        assertEquals("G9", card1.getColorValue());
        assertEquals("B2", card2.getColorValue());
    }

    @Test
    public void testGetColor() {
        assertEquals("R", card.getColor());
        assertEquals("G", card1.getColor());
        assertEquals("B", card2.getColor());
    }

    @Test
    public void testGetValue() {
        assertEquals("0", card.getValue());
    }

    @Test
    public void testGetPoints() {
        assertEquals(0, card.getPoints());
        assertEquals(9, card1.getPoints());
        assertEquals(2, card2.getPoints());
    }

    @Test
    public void testCheckColorCode() {
        assertEquals("\033[0;31m", card.checkColorCode("R"));
        assertEquals("\033[0;32m", card.checkColorCode("G"));
        assertEquals("\033[0;34m", card.checkColorCode("B"));
    }
}
