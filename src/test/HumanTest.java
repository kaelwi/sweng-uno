package test;

import main.Card;
import main.Human;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;

public class HumanTest {

    // @Before (annotation for setup if needed)
    // assertEquals (both in parenthesis have to be same)
    // assertTrue (return value or value in parenthesis has to be true)
    // assertFalse (same as above, but it has to be false)

    List<Card> cardsOnHand = new ArrayList<>();
    Human human = new Human("name", cardsOnHand);
    Card card1 = new Card("R", "0", 0, "0");
    Card card2 = new Card("G", "7", 0, "0");

    @Test
    public void testIsRightInput() {
        assertTrue(human.isRightInput("G"));
        assertFalse(human.isRightInput("X"));
    }

    @Test
    public void testIsCardOnHand() {
        cardsOnHand.add(card1);
        cardsOnHand.add(card2);
        assertEquals(card1, human.isCardOnHand("R0"));
    }

    @Test
    public void testRemoveCardFromHand() {
        cardsOnHand.add(card1);
        cardsOnHand.add(card2);
        List<Card> expected = new ArrayList<>();
        expected.add(card2);
        human.removeCardFromHand(card1);
        assertEquals(expected, cardsOnHand);
    }

    @Test
    public void testCheckUno() {
        assertFalse(human.checkUno());
    }

    @Test
    public void testChallengeColorCheck() {
        cardsOnHand.add(card1);
        cardsOnHand.add(card2);
        assertTrue(human.challengeColorCheck("R"));
        assertFalse(human.challengeColorCheck("B"));
    }
}
