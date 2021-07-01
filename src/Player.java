import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 *  Abstract class Player as a template or common ground for bots and human players.
 *
 *  Last Modified: 01.07.2021
 *  Authors: Paulina Safar
 *           Julia Summer
 *           Karoline E. Wild
 */

public abstract class Player {
    private final String name;
    private List<Card> playerCards;
    private int points;
    protected Scanner input = new Scanner(System.in);
    protected final PrintStream output = System.out;

    public Player(String name, List<Card> playerCards) {
        this.name = name;
        this.playerCards = playerCards;
        this.points = 0;
    }

    public void setPlayerCards(List<Card> playerCards) {
        this.playerCards = playerCards;
    }


    public List<Card> getPlayerCards() {
        return playerCards;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * This Method checks if the wanted card is in the player's hand,
     * and if it is, the player plays it
     *
     * @param cardToPlay The card a player wants to play
     * @returns the card to play
     */
    public Card isCardOnHand(String cardToPlay) {
        Card foundCard = new Card();
        Iterator<Card> it = this.getPlayerCards().iterator();
        while (it.hasNext()) {
            Card cardOnHand = it.next();
            if (cardOnHand.toString().equals(cardToPlay)) {
                foundCard = cardOnHand;
                it.remove();
                break;
            } else {
                foundCard = null;
            }
        }
        return foundCard;
    }

    public void takeCards(List<Card> cardsToBeTaken) {
        playerCards.addAll(cardsToBeTaken);
    }

    public void takeCardBack(Card card) {
        playerCards.add(card);
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }


    /**
     * This Method removes a specific card from the Player's hand
     *
     * @param card Card to be removed from hand
     */
    public void removeCardFromHand(Card card) {
        Iterator<Card> it = this.getPlayerCards().iterator();
        while (it.hasNext()) {
            Card cardOnHand = it.next();
            if (cardOnHand.equals(card)) {
                it.remove();
                break;
            }
        }
    }

    public boolean checkUno() {
        return playerCards.size() == 1;
    }

    public boolean challengeColorCheck(String color) {
        for (Card cardOnHand : this.getPlayerCards()) {
            if (cardOnHand.getColor().equals(color) && (cardOnHand.getPoints() < 20)) {
                return true;
            }
        }
        return false;
    }

    public boolean isRightInput(String color) {
        for (int i = 0; i < Card.getAllColors().length-1; i++) {
            if (color.equals(Card.getAllColors()[i])) {
                return true;
            }
        }
        output.println("Invalid choice of color!");
        return false;
    }

    public abstract Card getCardToPlay(Card discardDeckCard);

    public abstract Card turn(Card card);

    public abstract String colorWish();

    public abstract String challengeWish();

    public abstract void printCards();


}

