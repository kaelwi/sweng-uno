package test;

import main.Card;
import main.Deck;
import main.Human;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DeckTest {

    //List<Card> cards = new ArrayList<>();
    Deck deck = new Deck(5);
    Card card1 = new Card("R", "0", 0, "0");
    Card card2 = new Card("G", "7", 7, "0");
    Card card3 = new Card("B", "10", 10, "0");
    Card card4 = new Card("R", "4", 4, "0");

//    @Test
//    public void testGiveCards() {
//        assertEquals(7, deck.giveCards().size());
//    }

//    @Test
//    public void testAddCardToDiscardDeck() {
//        List<Card> cards = new ArrayList<>();
//        List<Card> expected = new ArrayList<>();
//        expected.add(card1);
//        deck.addCardToDiscardDeck(card1);
//        assertEquals(expected, cards);
//    }


}
