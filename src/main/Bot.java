package main;

import java.util.List;
import java.util.Random;

/**
 *  This class creates bot players, as an extension of the main.Player class.
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
     * This Method checks the main.Player's Cards for a appropriate card to play.
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
     * This Methods checks if bot has a card to play. If so, the card is removed from the bot hand and returned.
     * If there is no card to play, 1 card is drawn.
     * The Method also checks for "uno" situation.
     * @param discardDeckCard to compare bots' cards in hand with
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
            card = checkDrawnCard(discardDeckCard);
        }
        return card;
    }

    /**
     * This Method checks if the drawn card can be played. It also allows calling "uno".
     * @param discardDeckCard to compare the card with the discard deck card
     * @return card to be played (if possible)
     */
    public Card checkDrawnCard(Card discardDeckCard) {
        Card card = getCardToPlay(discardDeckCard);
        if (card != null) {
            removeCardFromHand(card);
            output.println("I was happy, I can play that card.");
            output.println(card);
            if (checkUno()) {
                output.println("UNO!");
            }
        } else {
            output.println("Still nothing...");
        }
        return card;
    }

    /**
     * With this Method the bot has a possibility to change the color by choosing first color in hand.
     * In a very unlikely case of having only wild cards (without color), a random color is returned.
     * @return - color which shall be played onwards
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
     * This Method randomly decides to challenge a player that used the "Wild Draw 4" card.
     * @return - is player challenged or not
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
     * This Method does not allow bots' cards to be seen.
     */
    @Override
    public void printCards() {
        output.println("Don't be so curious....");
    }
}