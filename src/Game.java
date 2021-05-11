import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

/**
 * This Class works the hardest in the UNO Program.
 * It creates Players and Bots, has instructions for starting of the Game,
 */
public class Game {

    private ArrayList<Player> player;
    private Deck deck;
    private Deck discardDeck;
    private final Scanner input;
    private final PrintStream output;
    private int turn = setFirst();

    public Game(Scanner input, PrintStream output) {
        this.input = input;
        this.output = output;
        deck = new Deck(108);
        discardDeck = new Deck(0);
        player = new ArrayList<Player>(4);
    }

    public void startGame() {
        deck.fillDeck();
        deck.shuffleCards();
        setPlayers();
        // Collections.shuffle(player);                             // OPTIONAL
        discardDeck.addCardToDiscardDeck(deck.getCards().get(0));
        deck.removeCardFromDeck();
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public void setDiscardDeck(Deck discardDeck) {
        this.discardDeck = discardDeck;
    }

    /**
     * This method determines the number of human and bot players
     */
    private void setPlayers() {
        output.println("Tell me how many bots would you like to involve: ");
        int bots = checkNrBots();
        createBots(bots);

        output.println("Now let's settle the rest!");
        output.println("You may create " + (4-bots) + " players.");
        createPlayers(4-bots);
    }

    /**
     * This Method creates human players (number of human players needs to be entered as parameter)
     * and adds them to the Arraylist of Players
     * @param humans int
     */
    private void createPlayers(int humans) {
        for (int i = 0; i < humans; i++) {
            output.println("Please enter a name for your player (player nr. " + (i+1) + ")");
            String name = input.nextLine();

            while(name.isEmpty() || playerNames().contains(name)) {
                output.println("I'm sorry, you either did not enter a name, or the name is already taken. Please try again.");
                name = input.nextLine();
            }
            player.add(new Player(name, deck.giveCards()));
        }
    }

    /**
     * This Method creates bot players (takes number of bots as parameter)
     * and adds them to the Arraylist of Players
     * @param bots
     */
    private void createBots(int bots) {
        for (int i = 0; i < bots; i++) {
            output.println("Please enter a name for bot nr. " + (i+1));
            String name = input.nextLine();

            while(name.isEmpty() || playerNames().contains(name)) {
                output.println("I'm sorry, you either did not enter a name, or the entered name is already taken. Please try again.");
                name = input.nextLine();
            }
            player.add(new Bot(name, deck.giveCards()));
        }
    }

    /**
     * This Method creates a list of Player's Names
     * @returns the Arraylist of names
     */
    private ArrayList<String> playerNames() {
        ArrayList<String> names = new ArrayList<>();
        for (Player player : player) {
            names.add(player.getName());
        }
        return names;
    }

    /**
     * This Method checks the number of bot players
     * @returns the number of bot players
     */
    private int checkNrBots() {
        int bots = Integer.parseInt(input.nextLine());
        while(bots < 0 || bots > 4) {
            if (bots > 4) {
                output.println("The maximum number of allowed players is 4!");
            } else {
                output.println("You know you need at least 1 player...");
            }
            bots = Integer.parseInt(input.nextLine());
        }
        return bots;
    }

    public Deck getDeck() {
        return deck;
    }

    public Deck getDiscardDeck() {
        return discardDeck;
    }

    public Player getPlayer(int position) {
        return player.get(position);
    }

    public ArrayList<Player> getPlayers() {
        return player;
    }

    public int getTurn() {
        return turn;
    }

    /**
     * This Method determines the first Players
     * @returns the random first Player
     */
    private int setFirst() {
        Random random = new Random();
        return random.nextInt(3);
    }

    public void setTurn(int i) {
        turn = i;
    }

    public void printHelp() {
        output.println("Print help - needs to be done.");
        output.println();
    }

    /**
     * This Method deals the cards to the players
     * @param player (to which player are the cards dealt)
     * @param number (number of cards to be dealt)
     * The Method also checks if the Deck is empty
     */
    public void givePlayerDrawCards(Player player, int number) {
        for (int i = 0; i < number; i++) {
            player.takeCards(this.getDeck().takeCards(1));
            checkEmptyDeck();
        }
        // player.takeCards(this.getDeck().takeCards(number));
    }
    /**
     * This Method gives TWO penalty card to the Player who forgot to say "UNO" with only one card left in the hand
     * and the next Player already drew a new card
     * @param player who needs to get penalty cards
     */
    public void missingUnoPenalty(Player player) {
        player.takeCards(this.getDeck().takeCards(2));
    }

    /**
     * This Method gives ONE penalty card to the Player who forgot to say "UNO" with only one card left in the hand
     * and the next Player did not draw a new card
     * @param player who needs to get penalty card
     */
    public void giveOnePenaltyCard(Player player) {
        player.takeCards(this.getDeck().takeCards(1));
    }

    /**
     * This Method checks if the card we want to play is appropriate to play
     * (if the color or the value of this card matches the color or the value of the top card on the discard Deck)
     * @param card we want to play
     * @param discardDeckCard top card on the discard Deck
     * @returns if the card should be played
     */
    public static boolean cardValidation(Card card, Card discardDeckCard) {
        if (card.getValue().equals(discardDeckCard.getValue()) || card.getColor().equals(discardDeckCard.getColor()) || card.getColor().equals("") || discardDeckCard.getColor().equals("")) {
            return true;
        }
        return false;
    }

    /**
     * This Method ?
     */
    public void turnOverflow() {
        if (this.getTurn() < 0) {
            this.setTurn(3);
        } else if (this.getTurn() > 3) {
            this.setTurn(0);
        }
    }

    /**
     * This Method shuffles the discard Deck, when it is full
     * @param discardDeck
     * @returns shuffled discard Deck
     */
    public Deck shuffleDiscardDeck(Deck discardDeck) {
        discardDeck.shuffleCards();
        return discardDeck;
    }

    /**
     * This Method checks if the main Deck is empty of cards.
     * If it is, than the discard Deck full with cards becomes the main Deck
     * and the new discard Deck starts afresh
     */
    public void checkEmptyDeck() {
        if (deck.isEmpty()) {
            output.println("The deck is empty! Let me shuffle a new one...");
            Deck newDeck = new Deck(1);
            newDeck.addCardToDiscardDeck(discardDeck.getDiscardDeckCard());
            discardDeck.removeCardFromDeck();
            deck = discardDeck;
            discardDeck = newDeck;
        }
    }
}
