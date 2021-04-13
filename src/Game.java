import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

public class Game {

    private static Player[] player = new Player[4];
    private static Deck deck;
    private static Deck discardDeck;
    private final Scanner input;
    private final PrintStream output;
    private int turn = setFirst();

    // Constructor
    public Game(Scanner input, PrintStream output) {
        this.input = input;
        this.output = output;
        deck = new Deck(108);
        discardDeck = new Deck(0);
        for (int i = 0; i < 4; i++) {
            player[i] = new Player();
        }
    }

    // start game - fill the deck, shuffle it, remove first card to discarddeck
    public void startGame() {
        deck.fillDeck();
        deck.shuffleCards();
        for (int i = 0; i < 4; i++) {
            // setPlayers in class game, because of missing input and output stream in other classes
            setPlayers(i);
        }
        discardDeck.addCardToDiscardDeck(deck.getCards().get(0));
        deck.removeCardFromDeck();
    }

    // set the players - get their names and give them 7 cards each
    private void setPlayers(int i) {
        output.println("Please enter your name (Player " + (i+1) + "): ");
        player[i].setName(input.nextLine());
        player[i].setPlayerCards(deck.giveCards());
    }

    // getter for deck
    public Deck getDeck() {
        return deck;
    }

    // getter for discarddeck
    public Deck getDiscardDeck() {
        return discardDeck;
    }

    ///////////////////////////////////////////////////////////////
    // getPlayer
    // parameter: int Position
    // return: Player
    // A method to get one player at a certain position in the player-array
    ///////////////////////////////////////////////////////////////
    public Player getPlayer(int position) {
        return player[position];
    }

    public Player[] getPlayers() {
        return player;
    }

    public int getTurn() {
        return turn;
    }

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
}
