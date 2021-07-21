package main;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 *  This class implements most of the game logic. It works mostly as a dealer and rule checker.
 *
 *  Last Modified: 01.07.2021
 *  @author  Julia Summer
 *           Paulina Safar
 *           Karoline E. Wild
 */

public class Game {

    private static final List<Player> player = new ArrayList<>(4);
    private static Deck deck = new Deck(108);
    private static Deck discardDeck = new Deck(0);
    private static Scanner input;
    private static final PrintStream output = System.out;
    private static int turn = setFirst();
    private static int reverse = 1;
    private static boolean alreadyChallenged = false;

    /**
     * This Method fills the Deck with all the cards and shuffles them.
     * It allows us to choose the number of human/bot players and to open the first card on the discard deck.
     * @param in - reads User's input
     */
    public static void startGame(Scanner in) {
        input = in;
        deck.fillDeck();
        deck.shuffleCards();
        setPlayers();
        do {
            discardDeck.addCardToDiscardDeck(deck.getCards().get(0));
            deck.removeCardFromDeck();
        } while (discardDeck.getDiscardDeckCard().getValue().equals("W+4"));
    }

    public static void setReverse() {
        reverse *= -1;
    }

    public static int getReverse() {
        return reverse;
    }

    public static void newRound() {
        deck = new Deck(108);
        discardDeck = new Deck(0);
        turn = setFirst();
        reverse = 1;
        deck.fillDeck();
        deck.shuffleCards();
        do {
            discardDeck.addCardToDiscardDeck(deck.getCards().get(0));
            deck.removeCardFromDeck();
        } while (discardDeck.getDiscardDeckCard().getValue().equals("W+4"));

        for (Player p : player) {
            p.setPoints(0);
            p.setPlayerCards(deck.giveCards());
        }
    }

    /**
     * This Method checks the color of the first card on the discard deck.
     * If the card is a "Wild Card", last player must choose the color to be played onwards.
     */
    public static void checkStartingColor() {
        if (discardDeck.getDiscardDeckCard().getValue().equals("W")) {
            output.println("The last player is allowed to choose the color at the beginning of the game: ");
            // String color = getPlayer(getPredecessor()).colorWish();
            String color = getPlayer(getTurn()).colorWish();
            getDiscardDeck().addCardToDiscardDeck(new Card(color, getDiscardDeck().getDiscardDeckCard().getValue(), -1, Card.checkColorCode(color)));
        }
    }

    private static void setPlayers() {
        output.println("Tell me how many bots would you like to involve: ");
        int bots = checkNrBots();
        createBots(bots);

        output.println("Now let's settle the rest!");
        output.println("You may create " + (4 - bots) + " players.");
        createPlayers(4 - bots);
    }

    private static void createPlayers(int humans) {
        for (int i = 0; i < humans; i++) {
            output.println("Please enter a name for your player (player nr. " + (i + 1) + ")");
            String name = input.nextLine();

            while (name.isEmpty() || playerNames().contains(name)) {
                output.println("I'm sorry, you either did not enter a name, or the name is already taken. Please try again.");
                name = input.nextLine();
            }
            player.add(new Human(name, deck.giveCards()));
        }
    }

    private static void createBots(int bots) {
        for (int i = 0; i < bots; i++) {
            output.println("Please enter a name for bot nr. " + (i + 1));
            String name = input.nextLine();

            while (name.isEmpty() || playerNames().contains(name)) {
                output.println("I'm sorry, you either did not enter a name, or the entered name is already taken. Please try again.");
                name = input.nextLine();
            }
            player.add(new Bot(name, deck.giveCards()));
        }
    }

    private static List<String> playerNames() {
        List<String> names = new ArrayList<>();
        for (Player player : player) {
            names.add(player.getName());
        }
        return names;
    }

    private static int checkNrBots() {
        int bots = Integer.parseInt(input.nextLine());
        while (bots < 0 || bots > 4) {
            if (bots > 4) {
                output.println("The maximum number of allowed players is 4!");
            } else {
                output.println("You know you need at least 1 player...");
            }
            bots = Integer.parseInt(input.nextLine());
        }
        return bots;
    }

    public static Deck getDeck() {
        return deck;
    }

    public static Deck getDiscardDeck() {
        return discardDeck;
    }

    public static Player getPlayer(int position) {
        return player.get(position);
    }

    public static List<Player> getPlayers() {
        return player;
    }

    public static int getTurn() {
        return turn;
    }

    private static int setFirst() {
        Random random = new Random();
        return random.nextInt(3);
    }

    public static void setTurn(int i) {
        turn = i;
    }

    /**
     * This Method deals the cards to the players.
     * The Method also checks if the Deck is empty.
     * @param player - to which player are the cards dealt.
     * @param number - number of cards to be dealt.
     */
    public static void givePlayerDrawCards(Player player, int number) {
        for (int i = 0; i < number; i++) {
            player.takeCards(getDeck().takeCards(1));
            checkEmptyDeck();
        }
    }

    /**
     * This Method gives ONE penalty card to the Player who forgot to say "UNO"
     * with only one card left in the hand in the situation when the next Player did not draw a new card.
     * @param player - player who gets a penalty card
     */
    public static void giveOnePenaltyCard(Player player) {
        player.takeCards(getDeck().takeCards(1));
        checkEmptyDeck();
    }

    /**
     * This Method checks if the card we want to play is appropriate to play.
     * It checks if the color or the value of this card matches the color or the value of the top card on the discard Deck.
     * @param card            - we want to play
     * @param discardDeckCard - top card on the discard Deck
     * @returns               - if the card should be played
     */
    public static boolean cardValidation(Card card, Card discardDeckCard) {
        return card.getValue().equals(discardDeckCard.getValue())
                || card.getColor().equals(discardDeckCard.getColor())
                || card.getColor().equals("");
    }

    /**
     * This Method checks out of bound access to the player list.
     */
    public static void turnOverflow() {
        if (getTurn() < 0) {
            setTurn(player.size()-1);
        } else if (getTurn() > player.size()-1) {
            setTurn(0);
        }
    }

    /**
     * This Method finds the predecessor for the "Wild Draw 4 Card" challenge.
     * @returns turn - the position of the predecessor player in the list of players.
     */
    private static int getPredecessor() {
        int turn = getTurn() - reverse;
        if (turn < 0) {
            turn = player.size()-1;
        }
        if (turn > player.size()-1) {
            turn = 0;
        }
        return turn;
    }

    /**
     * This Method checks if the main Deck is empty of cards.
     * If so, the discard Deck (which is full with cards) becomes the main Deck
     * The new discard Deck starts afresh.
     */
    public static void checkEmptyDeck() {
        if (deck.isEmpty()) {
            output.println("The deck is empty! Let me shuffle a new one...");
            Deck newDeck = new Deck(1);
            newDeck.addCardToDiscardDeck(discardDeck.getDiscardDeckCard());
            discardDeck.removeCardFromDeck();
            discardDeck.removeFakeWildCards();
            deck = discardDeck;
            deck.shuffleCards();
            discardDeck = newDeck;
        }
    }

    /**
     * This Method checks if the move is valid. Give one penalty card in case of an invalid move.
     * @param card - currently being played.
     * @param player - player that wants to play this card.
     * @returns boolean - information whether or not the move is valid.
     */
    public static boolean moveValidation(Card card, Player player) {
        if (cardValidation(card, getDiscardDeck().getDiscardDeckCard())) {
            return true;
        } else {
            output.println("Invalid move! Player " + player.getName() + " draws one penalty card.");
            player.takeCardBack(card);
            giveOnePenaltyCard(player);
            return false;
        }
    }

    /**
     * This Method checks if any of the special cards is currently on the discard deck.
     * @param beginning - turn reversed if a special card gets on the discard deck on the very beginning.
     */
    public static void doChecks(boolean beginning) {
        checkReverse(beginning);
        checkColorChange();
        if (!beginning) {
            setTurn(getTurn() + getReverse());
        }
        doOtherChecks();
    }

    private static void checkReverse(boolean beginning) {
        if (getDiscardDeck().checkReverse() && getDiscardDeck().getDiscardDeckCard().getPoints() != -1) {
            setReverse();
            if (beginning) {
                setTurn(getTurn()+getReverse());
            }
        }
    }

    private static void checkColorChange() {
        if (getDiscardDeck().getDiscardDeckCard().getColor().equals("")) {
            alreadyChallenged = false;
            String color;
            output.println("You can choose the color.");
            color = getPlayer(getTurn()).colorWish();
            getDiscardDeck().addCardToDiscardDeck(new Card(color, getDiscardDeck().getDiscardDeckCard().getValue(), -1, Card.checkColorCode(color)));
        }
    }

    private static void doOtherChecks() {
        turnOverflow();
        checkStop();
        checkTakeTwo();
        checkTakeFour();
        turnOverflow();
    }

    private static void checkStop() {
        if (getDiscardDeck().getDiscardDeckCard().getValue().equals("X") && getDiscardDeck().getDiscardDeckCard().getPoints() != -1) {
            Printer.printState(App.exit, getDiscardDeck().getDiscardDeckCard(), false);
            Printer.printPlayerCards(getPlayer(getTurn()));
            output.println("Stop! You are not allowed to play!");
            setTurn(getTurn() + getReverse());
        }
    }

    private static void checkTakeTwo() {
        if (getDiscardDeck().getDiscardDeckCard().getValue().equals("+2") && getDiscardDeck().getDiscardDeckCard().getPoints() != -1) {
            Printer.printState(App.exit, getDiscardDeck().getDiscardDeckCard(), false);
            Printer.printPlayerCards(getPlayer(getTurn()));
            output.println("You have to take 2 cards!");
            givePlayerDrawCards(getPlayer(getTurn()), 2);
            setTurn(getTurn() + getReverse());
        }
    }

    private static void checkTakeFour() {
        if (getDiscardDeck().getDiscardDeckCard().getValue().equals("W+4") && !alreadyChallenged) {
            alreadyChallenged = true;
            Printer.printState(App.exit, getDiscardDeck().getDiscardDeckCard(), false);
            Printer.printPlayerCards(getPlayer(getTurn()));
            output.println("You have to take 4 cards!");
            output.println("Dou you want to challenge the last player? (Y/N)");
            String challenge = getPlayer(getTurn()).challengeWish();
            if (challenge.equalsIgnoreCase("Y")) {
                if (challenge()) {
                    output.println("You were right, the last player tried to cheat!");
                    int turn = getPredecessor();
                    givePlayerDrawCards(getPlayer(turn), 6);
                } else {
                    output.println("You still have to take 4 cards...");
                    givePlayerDrawCards(getPlayer(getTurn()), 4);
                    setTurn(getTurn() + getReverse());
                }
            } else {
                givePlayerDrawCards(getPlayer(getTurn()), 4);
                setTurn(getTurn() + getReverse());
            }
        }
    }

    /**
     * This Method checks if a player was allowed to play the "Wild Draw 4" card.
     * @returns boolean - true if the challenge is justified and the player had another card that they could play.
     */
    public static boolean challenge() {
        return getPlayer(getPredecessor()).challengeColorCheck(discardDeck.getCardBeforeWild().getColor());
    }
}
