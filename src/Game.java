import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 *  This class implements most of the game logic. It works mostly as a dealer and rule checker.
 *
 *  Last Modified: 01.07.2021
 *  @author  Paulina Safar
 *           Julia Summer
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

    public static void checkStartingColor() {
        if (discardDeck.getDiscardDeckCard().getValue().equals("W")) {
            output.println("The last player is allowed to choose the color at the beginning of the game: ");
            String color = getPlayer(checkTurn()).colorWish();
            getDiscardDeck().addCardToDiscardDeck(new Card(color, getDiscardDeck().getDiscardDeckCard().getValue(), -1));
        }
    }

    /**
     * This method determines the number of human and bot players
     */
    private static void setPlayers() {
        output.println("Tell me how many bots would you like to involve: ");
        int bots = checkNrBots();
        createBots(bots);

        output.println("Now let's settle the rest!");
        output.println("You may create " + (4 - bots) + " players.");
        createPlayers(4 - bots);
    }

    /**
     * This Method creates human players (number of human players needs to be entered as parameter)
     * and adds them to the Arraylist of Players
     *
     * @param humans int
     */
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

    /**
     * This Method creates bot players (takes number of bots as parameter)
     * and adds them to the Arraylist of Players
     *
     * @param bots Indicates the number of bots to create
     */
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

    /**
     * This Method creates a list of Player's Names
     *
     * @returns names, a List of already used names
     */
    private static List<String> playerNames() {
        List<String> names = new ArrayList<>();
        for (Player player : player) {
            names.add(player.getName());
        }
        return names;
    }

    /**
     * This Method checks the number of bot players
     *
     * @returns the number of bot players
     */
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

    /**
     * This Method determines the first Players
     *
     * @returns the random first Player
     */
    private static int setFirst() {
        Random random = new Random();
        return random.nextInt(3);
    }

    public static void setTurn(int i) {
        turn = i;
    }

    /**
     * This Method deals the cards to the players
     *
     * @param player (to which player are the cards dealt)
     * @param number (number of cards to be dealt)
     *               The Method also checks if the Deck is empty
     */
    public static void givePlayerDrawCards(Player player, int number) {
        for (int i = 0; i < number; i++) {
            player.takeCards(getDeck().takeCards(1));
            checkEmptyDeck();
        }
    }

    /**
     * TODO: mit Schleife machen (check empty deck) + random?
     * This Method gives TWO penalty card to the Player who forgot to say "UNO" with only one card left in the hand
     * and the next Player already drew a new card
     *
     * @param player who needs to get penalty cards
     */
    public static void missingUnoPenalty(Player player) {
        player.takeCards(getDeck().takeCards(2));
    }

    /**
     * This Method gives ONE penalty card to the Player who forgot to say "UNO" with only one card left in the hand
     * and the next Player did not draw a new card
     *
     * @param player who needs to get penalty card
     */
    public static void giveOnePenaltyCard(Player player) {
        player.takeCards(getDeck().takeCards(1));
    }

    /**
     * This Method checks if the card we want to play is appropriate to play
     * (if the color or the value of this card matches the color or the value of the top card on the discard Deck)
     *
     * @param card            we want to play
     * @param discardDeckCard top card on the discard Deck
     * @returns if the card should be played
     */
    public static boolean cardValidation(Card card, Card discardDeckCard) {
        // TODO: check if last ORs needed
        return card.getValue().equals(discardDeckCard.getValue()) || card.getColor().equals(discardDeckCard.getColor()) || card.getColor().equals("");
        // return card.getValue().equals(discardDeckCard.getValue()) || card.getColor().equals(discardDeckCard.getColor()) || card.getColor().equals("") || discardDeckCard.getColor().equals("");
    }

    // TODO: Get rid of hardcoded values (to keep nr of players flexible)
    public static void turnOverflow() {
        if (getTurn() < 0) {
            setTurn(3);
        } else if (getTurn() > 3) {
            setTurn(0);
        }
    }

    /**
     * This Method checks if the main Deck is empty of cards.
     * If it is, than the discard Deck full with cards becomes the main Deck
     * and the new discard Deck starts afresh
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

    public static boolean moveValidation(Card card, Player player) {
        if (cardValidation(card, getDiscardDeck().getDiscardDeckCard())) {
            return true;
        } else {
            output.println("Invalid move!");
            player.takeCardBack(card);
            giveOnePenaltyCard(player);
            return false;
        }
    }

    public static void doChecks() {
        checkReverse();
        checkColorChange();
        setTurn(getTurn() + getReverse());
        doOtherChecks();
    }


    private static void checkReverse() {
        if (getDiscardDeck().checkReverse()) {
            setReverse();
        }
    }

    private static void checkColorChange() {
        if (getDiscardDeck().getDiscardDeckCard().getColor().equals("")) {
            alreadyChallenged = false;
            String color;
            output.println("You can choose the color.");
            color = getPlayer(getTurn()).colorWish();
            getDiscardDeck().addCardToDiscardDeck(new Card(color, getDiscardDeck().getDiscardDeckCard().getValue(), -1));
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
        if (getDiscardDeck().getDiscardDeckCard().getValue().equals("X")) {
            Printer.printState(App.exit, getDiscardDeck().checkReverse(), getDiscardDeck().getDiscardDeckCard());
            Printer.printPlayerCards(getPlayer(getTurn()));
            output.println("Stop! You are not allowed to play!");
            setTurn(getTurn() + getReverse());
        }
    }

    private static void checkTakeTwo() {
        if (getDiscardDeck().getDiscardDeckCard().getValue().equals("+2")) {
            Printer.printState(App.exit, getDiscardDeck().checkReverse(), getDiscardDeck().getDiscardDeckCard());
            Printer.printPlayerCards(getPlayer(getTurn()));
            output.println("You have to take 2 cards!");
            for (int i = 0; i < 2; i++) {
                givePlayerDrawCards(getPlayer(getTurn()), 1);
                checkEmptyDeck();
            }
            setTurn(getTurn() + getReverse());
        }
    }

    private static void checkTakeFour() {
        if (getDiscardDeck().getDiscardDeckCard().getValue().equals("W+4") && !alreadyChallenged) {
            alreadyChallenged = true;
            Printer.printState(App.exit, getDiscardDeck().checkReverse(), getDiscardDeck().getDiscardDeckCard());
            Printer.printPlayerCards(getPlayer(getTurn()));
            output.println("You have to take 4 cards!");
            output.println("Dou you want to challenge the last player? (Y/N)");
            String challenge = getPlayer(getTurn()).challengeWish();
            // TODO: Ausgabe, ob challenge gerechtfertigt!
            if (challenge.equals("Y")) {
                if (challenge()) {
                    for (int i = 0; i < 6; i++) {
                        int turn = checkTurn();
                        givePlayerDrawCards(getPlayer(turn), 1);
                        checkEmptyDeck();
                    }
                }
            } else {
                for (int i = 0; i < 4; i++) {
                    givePlayerDrawCards(getPlayer(getTurn()), 1);
                    checkEmptyDeck();
                }
                setTurn(getTurn() + getReverse());
            }
        }
    }

    // TODO: Get rid of hardcoded values (to keep nr of players flexible), 2 almost same methods - work with parameter?
    private static int checkTurn() {
        int turn = getTurn() - reverse;
        if (turn < 0) {
            turn = 3;
        }
        if (turn > 3) {
            turn = 0;
        }
        return turn;
    }

    public static boolean challenge() {
        System.out.println(discardDeck.getCardBeforeWild().getColor());
        return getPlayer(checkTurn()).challengeColorCheck(discardDeck.getCardBeforeWild().getColor());
    }
}

