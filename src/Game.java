import java.io.PrintStream;
import java.util.Scanner;

public class Game {

    private static Player[] player = new Player[4];
    private static Deck deck;
    private static Deck discardDeck = new Deck(0);
    private final Scanner input;
    private final PrintStream output;

    // Constructor
    public Game(Scanner input, PrintStream output) {
        this.input = input;
        this.output = output;
        deck = new Deck(108);
        // discardDeck = new Deck(0);
        for (int i = 0; i < 4; i++) {
            player[i] = new Player();
        }
    }

    // start game - fill the deck, shuffle it, remove first card to discarddeck
    public void startGame() {
        deck.fillDeck();
        deck.shuffleCards();
        for (int i = 0; i < 4; i++) {
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

    // getter for players
    public Player[] getPlayer() {
        return player;
    }
}
