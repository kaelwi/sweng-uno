package main;

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
     * This Method checks the Bot's Cards for an appropriate card to play.
     * If there is such a card, it returns it. If not, it returns null.
     * @param discardDeckCard - the card being on the discard deck right now
     * @returns card - the appropriate card to play or null
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
     * This Method removes the card to play from the bot's hand and plays it.
     * If the bot has no appropriate card to play, 1 card has to be drawn.
     * The drawn card is checked against the discard deck and played, if possible.
     * The Method also checks if "uno" should be called.
     * @param discardDeckCard - to compare bots' cards in hand with
     * @return card  - that is being played
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
     * This Method checks the drawn card against the discard deck, so that it can be played.
     * It also allows calling "uno", as per the rules.
     * @param discardDeckCard - to compare the card with the discard deck card
     * @returns card - to be played, if possible
     */
    public Card checkDrawnCard(Card discardDeckCard) {
        Card card = getCardToPlay(discardDeckCard);
        if (card != null) {
            removeCardFromHand(card);
            output.println("I am happy I can play that card.");
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
     * @returns color - color which shall be played onwards
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
     * @returns String yes/no - is player challenged or not
     */
    @Override
    public String challengeWish() {
        int random = new Random().nextInt(11);
        if (random < 5) {
            output.println("N");
            return "N";
        } else {
            output.println("Y");
            return "Y";
        }
    }

    /**
     * This Method does not allow bots' cards to be seen.
     */
    @Override
    public void printCards() {
        for (int i = 0; i < getPlayerCards().size()-1; i++) {
            output.print("[], ");
        }
        output.print("[]");
    }
}
