import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    // Attributes: deck of cards, number of cards in deck?
    private static ArrayList<Card> cards;
    private int cardsInDeck;

    // Constructor: 108 cards in deck
    public Deck() {
        cards = new ArrayList<Card>(108);
    }

    // Fill deck with right cards
    // one 0, two 2-9, skip, reverse, +2 of each color
    // 4 wild and 4 +4 wild
    public void fillDeck() {
        Card newCard = new Card();
        String[] colors = newCard.getAllColors();
        String[] values = newCard.getAllValues();
        cardsInDeck = 0;

        for (int i = 0; i < colors.length-1; i++) {
            // add 4 zeros, one of each color
            cards.add(new Card(colors[i], values[0], 0));

            // add all other cards (except for wild card and +4 wild card) twice from each color
            for (int j = 1; j < values.length-5; j++) {
                cards.add(new Card(colors[i], values[j], j));
                cards.add(new Card(colors[i], values[j], j));
            }

            for (int j = values.length-5; j < values.length-2; j++) {
                cards.add(new Card(colors[i], values[j], 20));
                cards.add(new Card(colors[i], values[j], 20));
            }
        }

        // add 4 wild cards and 4 +4 wild cards
        for (int i = 0; i < 4; i++) {
            cards.add(new Card(colors[colors.length-1], values[values.length-2], 50));
            cards.add(new Card(colors[colors.length-1], values[values.length-1], 50));
        }
    }

    public static ArrayList<Card> shuffleCards() {
        Collections.shuffle(cards);
        return cards;
    }

    public static ArrayList<Card> giveCards() {
        ArrayList<Card> playerCards = new ArrayList<>(7);

        for (int i = 0; i < 7; i++) {
            playerCards.add(cards.get(0));
            cards.remove(0);
        }

        return playerCards;
    }

    public static ArrayList<Card> getCards() {
        return cards;
    }
}
