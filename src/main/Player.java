package main;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 *  Abstract class Player as a template with common attributes and operations for bots and human players.
 *
 *  Last Modified: 01.07.2021
 *  @author  Julia Summer
 *           Paulina Safar
 *           Karoline E. Wild
 */

public abstract class Player {
    private final String name;
    private List<Card> playerCards;
    private int points;
    protected Scanner input = new Scanner(System.in);
    protected final PrintStream output = System.out;
    protected boolean canDraw = true;
    protected boolean played = false;

    public Player(String name, List<Card> playerCards) {
        this.name = name;
        this.playerCards = playerCards;
        this.points = 0;
    }

    public boolean getPlayed() {
        return played;
    }

    public void setPlayed(boolean played) {
        this.played = played;
    }

    public void setPlayerCards(List<Card> playerCards) {
        this.playerCards = playerCards;
    }

    public boolean getCanDraw() {
        return canDraw;
    }

    public void setCanDraw(boolean canDraw) {
        this.canDraw = canDraw;
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
     * This Method checks if the card chosen for play is in player's hand.
     * @param cardToPlay - card chosen for play
     * @returns the card - a played card
     */
    public Card isCardOnHand(String cardToPlay) {
        Card foundCard = new Card();
        Iterator<Card> it = this.getPlayerCards().iterator();
        while (it.hasNext()) {
            Card cardOnHand = it.next();
            if (cardOnHand.getColorValue().equals(cardToPlay.toUpperCase())) {
                foundCard = cardOnHand;
                // it.remove();
                break;
            } else {
                foundCard = null;
            }
        }
        return foundCard;
    }

    /**
     * This Method adds drawn cards to players hand.
     * @param cardsToBeTaken - list of drawn cards
     */
    public void takeCards(List<Card> cardsToBeTaken) {
        playerCards.addAll(cardsToBeTaken);
    }

    /**
     * This Method allows the card to be taken back from discard deck into the player's hand.
     * @param card - card returned from the discard deck into the player's hand
     */
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
     * This Method removes a specific card from the Player's hand.
     * @param card - Card to be removed from discard deck and returned into the player's hand
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
     * This Method sets a rule in which situation should a player call "uno".
     */
    public boolean checkUno() {
        return playerCards.size() == 1;
    }

    /**
     * This Method is part of the challenge and checks if the player had a card with the right color to play.
     * If true, s/he was not allowed to play the "Wild Draw 4" card.
     * @param color - color, for which we search in the player's hand.
     */
    public boolean challengeColorCheck(String color) {
        for (Card cardOnHand : this.getPlayerCards()) {
            if (cardOnHand.getColor().equals(color) && (cardOnHand.getPoints() < 20)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This Method checks if the color, which was chosen by a player, is in the list of valid UNO card colors.
     * @param color - color that we are checking.
     * @returns boolean - information if the color is valid.
     */
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
