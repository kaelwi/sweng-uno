package test;

import main.Card;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CardTest {

    // @Before (annotation for setup if needed)
    // assertEquals (both in parenthesis have to be same)
    // assertTrue (return value or value in parenthesis has to be true)
    // assertFalse (same as above, but it has to be false

    @Test
    public void testGetColor() {
        Card card = new Card("R", "0", 0, "0");
        assertEquals("R", card.getColor());
    }
}
