import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

    private List<Card> cards;

    public Deck(int capacity) {
        cards = new ArrayList<>(capacity);
    }

    // Fill deck with right cards
    // one 0, two 2-9, skip, reverse, +2 of each color
    // 4 wild and 4 +4 wild
    public void fillDeck() {
        Card newCard = new Card();
        String[] colors = newCard.getAllColors();
        String[] values = newCard.getAllValues();
        int[] points = newCard.getAllPoints();

        for (int i = 0; i < colors.length-1; i++) {
            // add 4 zeros, one of each color
            cards.add(new Card(colors[i], values[0], points[0]));

            // add all other cards with numbers, twice from each color
            for (int j = 1; j < values.length-5; j++) {
                cards.add(new Card(colors[i], values[j], points[j]));
                cards.add(new Card(colors[i], values[j], points[j]));
            }

            // add all special cards except for wild card and +4 wild card
            for (int j = values.length-5; j < values.length-3; j++) {
                cards.add(new Card(colors[i], values[j], points[points.length-2]));
                cards.add(new Card(colors[i], values[j], points[points.length-2]));
            }
        }

        // add 4 wild cards and 4 +4 wild cards
        for (int i = 0; i < 4; i++) {
            cards.add(new Card(colors[colors.length-1], values[values.length-3], points[points.length-1]));
            cards.add(new Card(colors[colors.length-1], values[values.length-2], points[points.length-1]));
        }
    }

    // shuffle cards - method from collections package to shuffle elements of an arraylist
    public void shuffleCards() {
        Collections.shuffle(cards);
    }

    // to take and give 7 cards from deck to player
    public List<Card> giveCards() {
        List<Card> playerCards = new ArrayList<>(7);
        for (int i = 0; i < 7; i++) {
            playerCards.add(cards.get(cards.size()-1));
            cards.remove(cards.size()-1);
        }
        return playerCards;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void addCardToDiscardDeck(Card card) {
        cards.add(0, card);
    }

    // remove first card from deck (needed for the beginning of the game and later on for players to take cards)
    public void removeCardFromDeck() {
        cards.remove(0);
    }

    public List<Card> takeCards(int number) {
        List<Card> playerCards = new ArrayList<>(number);
        for (int i = 0; i < number; i++) {
            playerCards.add(cards.get(cards.size()-1));
            cards.remove(cards.size()-1);
        }
        return playerCards;
    }

    public Card getDiscardDeckCard() {
        return cards.get(0);
    }

    public Card getCardBeforeWild() {
        return this.cards.get(this.cards.size()-3);
    }

    public boolean checkReverse() {
        return getDiscardDeckCard().getValue().equals("<->");
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public void removeFakeWildCards() {
        for (Card card : cards) {
            if (card.getPoints() == -1) {
                cards.remove(card);
            }
        }
    }
}

