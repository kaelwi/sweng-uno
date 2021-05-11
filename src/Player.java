import java.util.ArrayList;
import java.util.Iterator;

/**
 * This Class creates human players
 */
public class Player {
    // private final static String[] botNames = {"Bot1", "Bot2", "Bot3", "Bot4"};
    // private int botNr = 0;
    private String name;
    private ArrayList<Card> playerCards;
    private int points;

    public Player() {
    }


    public Player(String name, ArrayList<Card> playerCards) {
        this.name = name;
        this.playerCards = playerCards;
        this.points = 0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlayerCards(ArrayList<Card> playerCards) {
        this.playerCards = playerCards;
    }

//    // getter for bot names
//    private String getBotName() {
//        String botName = botNames[botNr];
//        botNr++;
//        return botName;
//    }


    public ArrayList<Card> getPlayerCards() {
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
     * @param cardToPlay
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

    public void takeCards(ArrayList<Card> cardsToBeTaken) {
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

    public boolean checkForBot() {
        return this instanceof Bot;
    }

    /**
     * This Method removes a specific card from the Player's hand
     * @param card
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

    /**
     * This Method checks the Player's Cards for a appropriate card to play.
     * If there is such a card, it returns it, if not, it returns null
     * @param discardDeckCard
     * @returns the appropriate card to play or null
     */
    public Card getCardToPlay(Card discardDeckCard) {

        for (Card card : this.getPlayerCards()) {
            if (Game.cardValidation(card, discardDeckCard)) {
                return card;
            }
        }

        return null;

    }
}
