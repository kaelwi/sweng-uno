import java.util.List;
import java.util.Random;

/**
 *  This class creates bot players, as an extension of the Player class.
 *
 *  Last Modified: 01.07.2021
 *  @author  Paulina Safar
 *           Julia Summer
 *           Karoline E. Wild
 */

public class Bot extends Player {

    public Bot(String name, List<Card> playerCards) {
        super(name, playerCards);
    }

    /**
     * This Method checks the Player's Cards for a appropriate card to play.
     * If there is such a card, it returns it, if not, it returns null
     *
     * @param discardDeckCard The card being on the discard deck right now
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

    /**
     * If there is any card to play, remove it from bots hand and return it. Also check for UNO.
     * If there is no card to play, draw 1.
     *
     * @param discardDeckCard to compare bots cards on hand with
     * @return card that is being played
     */
    @Override
    public Card turn(Card discardDeckCard) {
        Card card = getCardToPlay(discardDeckCard);
        if (card != null) {
            removeCardFromHand(card);
            output.println(card);
            if (checkUno()) {
                output.println("UNO!");
            }
        } else {
            output.println("Unfortunately, I don't have anything to play.");
            Game.givePlayerDrawCards(this, 1);
        }
        return card;
    }

    /**
     * If the bot gets the possibility to change the color, choose first color on hand.
     * In the very unlikely case of having only wild cars (with no color), return a random color.
     *
     * @return color which shall be played onwards
     */
    @Override
    public String colorWish() {
        for (Card card : this.getPlayerCards()) {
            if (!card.getColor().equals("")) {
                return card.getColor();
            }
        }
        return Card.getAllColors()[new Random().nextInt(4)];
    }

    /**
     * The will to challenge a player because of the wild-take-four card is determined with a random number.
     *
     * @return challenge or not
     */
    @Override
    public String challengeWish() {
        int random = new Random().nextInt(11);
        if (random < 5) {
            return "N";
        } else {
            return "Y";
        }
    }

    /**
     * Don't show bots cards.
     */
    @Override
    public void printCards() {
        output.println("Don't be so curious....");
    }
}
