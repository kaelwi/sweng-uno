import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *  A class for the representation of deck objects.
 *
 *  Last Modified: 01.07.2021
 *  @author  Paulina Safar
 *           Julia Summer
 *           Karoline E. Wild
 */

public class Deck {

    private final List<Card> cards;

    public Deck(int capacity) {
        cards = new ArrayList<>(capacity);
    }

    /**
     * Fill the deck with corresponding cards. 108 cards in total.
     */
    public void fillDeck() {
        String[] colors = Card.getAllColors();
        String[] values = Card.getAllValues();
        int[] points = Card.getAllPoints();

        for (int i = 0; i < colors.length - 1; i++) {
            cards.add(new Card(colors[i], values[0], points[0]));

            for (int j = 1; j < values.length - 4; j++) {
                cards.add(new Card(colors[i], values[j], points[j]));
                cards.add(new Card(colors[i], values[j], points[j]));
            }

            for (int j = values.length - 4; j < values.length - 2; j++) {
                cards.add(new Card(colors[i], values[j], points[points.length - 2]));
                cards.add(new Card(colors[i], values[j], points[points.length - 2]));
            }
        }

        for (int i = 0; i < 4; i++) {
            cards.add(new Card(colors[colors.length - 1], values[values.length - 2], points[points.length - 1]));
            cards.add(new Card(colors[colors.length - 1], values[values.length - 1], points[points.length - 1]));
        }
    }

    public void shuffleCards() {
        Collections.shuffle(cards);
    }

    /**
     * Initial dealing of cards. 7 cards for each player.
     * @return playerCards, a list of 7 cards that a player gets on hand
     */
    public List<Card> giveCards() {
        List<Card> playerCards = new ArrayList<>(7);
        for (int i = 0; i < 7; i++) {
            playerCards.add(cards.get(cards.size() - 1));
            cards.remove(cards.size() - 1);
        }
        return playerCards;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void addCardToDiscardDeck(Card card) {
        cards.add(0, card);
    }

    public void removeCardFromDeck() {
        cards.remove(cards.size()-1);
    }

    /**
     * Lets a player taky any specified number of cards.
     *
     * @param number of cards to be taken
     * @return playerCards, a list of cards enriched with new cards
     */
    public List<Card> takeCards(int number) {
        List<Card> playerCards = new ArrayList<>(number);
        for (int i = 0; i < number; i++) {
            playerCards.add(cards.get(cards.size() - 1));
            cards.remove(cards.size() - 1);
        }
        return playerCards;
    }

    public Card getDiscardDeckCard() {
        return cards.get(0);
    }

    public Card getCardBeforeWild() {
        return this.cards.get(this.cards.size() - 3);
    }

    public boolean checkReverse() {
        return getDiscardDeckCard().getValue().equals("<->");
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public void removeFakeWildCards() {
        cards.removeIf(card -> card.getPoints() == -1);
    }
}
