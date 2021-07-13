package test;

import main.Bot;
import main.Card;
import org.junit.Test;

import java.util.*;
import static org.junit.Assert.assertEquals;

public class BotTest {

    // @Before (annotation for setup if needed)
    // assertEquals (both in parenthesis have to be same)
    // assertTrue (return value or value in parenthesis has to be true)
    // assertFalse (same as above, but it has to be false)

        List<Card> playerCards = new ArrayList<>();
        Bot bot = new Bot("name", playerCards);
        Card card1 = new Card("R", "0", 0, "0");
        Card card2 = new Card("G", "7", 0, "0");
        Card card3 = new Card("B", "6", 0, "0");
        Card cardDiscardDeck = new Card("G", "9", 0, "0");

    @Test
    public void testGetCardToPlay() {
        playerCards.add(card1);
        playerCards.add(card2);
        playerCards.add(card3);
        assertEquals(card2, bot.getCardToPlay(cardDiscardDeck));
    }

        @Test
    public void testTurn() {
        playerCards.add(card1);
        playerCards.add(card2);
        playerCards.add(card3);
        assertEquals(card2, bot.getCardToPlay(cardDiscardDeck));
    }

    @Test
    public void testCheckDrawnCard() {
        Card drawnCard = new Card("", "W+4", 50, "0");
        playerCards.add(drawnCard);
        assertEquals(drawnCard, bot.checkDrawnCard(cardDiscardDeck));
    }

    @Test
    public void testColorWish() {
        playerCards.add(new Card("", "W+4", 50, "0"));
        playerCards.add(new Card("", "W+4", 50, "0"));
        playerCards.add(card2);
        assertEquals("G", bot.colorWish());
    }

//    @Test
//    public void testChallengeWish() {
//        TODO - testing answer to random number generator
//        assertEquals("", bot.colorWish());
//    }
}
