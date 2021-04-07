import java.io.PrintStream;
import java.util.Scanner;

public class Game {

    private static Player[] player = new Player[4];
    private static Deck deck;
    private final Scanner input;
    private final PrintStream output;

    public Game(Scanner input, PrintStream output) {
        this.input = input;
        this.output = output;
        deck = new Deck();
        for (int i = 0; i < 4; i++) {
            player[i] = new Player();
        }
    }

    public void startGame() {
        deck.fillDeck();
        deck.shuffleCards();
        for (int i = 0; i < 4; i++) {
            setPlayers(i);
        }
    }

    private void setPlayers(int i) {
        output.println("Please enter your name (Player " + (i+1) + "): ");
        // String name = input.nextLine();
        player[i].setName(input.nextLine());
        player[i].setPlayerCards(deck.giveCards());
    }

    public static Deck getDeck() {
        return deck;
    }
}
