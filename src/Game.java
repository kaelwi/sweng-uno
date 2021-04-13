import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

public class Game {

    private Player[] player = new Player[4];
    private Deck deck;
    private Deck discardDeck;
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
        String name = input.nextLine();

        while(name.isEmpty()) {
            output.println("Unfortunately, you did not enter any name. Please, try it again.");
            name = input.nextLine();
        }

        player[i].setName(name);
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

    public void givePlayerDrawCards(Player player, int number) {
        // output.println("How many cards do you want to/have to draw?");
        // nextInt problematic, \n stays in buffer -> parse whole input line into an integer
        // int number = Integer.parseInt(input.nextLine());
        player.takeCards(this.getDeck().takeCards(number));
    }

    public void giveCardForMissingUno(Player player) {
        player.takeCards(this.getDeck().takeCards(1));
    }

    // not taking care of wild cards yet
    public boolean colorValidation(Card card) {
        boolean equalColor = this.getDiscardDeck().getCards().get(this.getDiscardDeck().getCards().size()-1).getColor().equals(card.getColor());
        boolean wildCardOnHand = card.getColor().equals("");
        boolean wildCardOnDiscardDeck = this.getDiscardDeck().getCards().get(this.getDiscardDeck().getCards().size()-1).getColor().equals("");
        return equalColor || wildCardOnDiscardDeck || wildCardOnHand;
    }

    // not taking care of wild cards yet
    public boolean valueValidation(Card card) {
        boolean equalColor = this.getDiscardDeck().getCards().get(this.getDiscardDeck().getCards().size()-1).getValue().equals(card.getValue());
        boolean wildCardOnHand = card.getValue().equals("W") || card.getValue().equals("W+4");
        boolean wildCardOnDiscardDeck = this.getDiscardDeck().getCards().get(this.getDiscardDeck().getCards().size()-1).getValue().equals("W") ||
                this.getDiscardDeck().getCards().get(this.getDiscardDeck().getCards().size()-1).getValue().equals("W+4");
        return equalColor || wildCardOnDiscardDeck || wildCardOnHand;
    }
}
