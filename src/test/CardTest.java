package test;

import main.Card;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CardTest {

    @Test
    public void testGetColor() {
        Card card = new Card("R", "0", 0);
        assertEquals("R", card.getColor());
    }
}
