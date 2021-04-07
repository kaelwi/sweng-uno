import java.util.ArrayList;

public class Deck {

    // Attributes: deck of cards, number of cards in deck?
    private ArrayList<Card> cards;
    private int cardsInDeck;

    // Constructor: 108 cards in deck
    public Deck() {
        cards = new ArrayList<Card>(108);
    }

    // Fill deck with right cards
    // one 0, two 2-9, skip, reverse, +2 of each color
    // 4 wild and 4 +4 wild
    public void createDeck() {
        Card newCard = new Card();
        String[] colors = newCard.getAllColors();
        String[] values = newCard.getAllValues();
        cardsInDeck = 0;

        for (int i = 0; i < colors.length-1; i++) {

        }
    }
}
