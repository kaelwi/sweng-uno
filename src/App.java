import java.io.PrintStream;
import java.util.Scanner;

public class App {

    // Attributes
    private final Scanner input;
    private final PrintStream output;
    private boolean exit = false;
    private boolean cont = true;

    // Constructor
    public App(Scanner input, PrintStream output) {
        this.input = input;
        this.output = output;
    }

    // Game Loop
    public void Run() {
        initialize();
        printState();

        while(!exit) {
            readUserInput();
            updateState();
            printState();

            exit = !askContinue();
        }
    }

    // Initial setup
    private void initialize() {
        Deck deck = new Deck();
        deck.fillDeck();
        System.out.println("deck.getCards().size() = " + deck.getCards().size());
        System.out.println("deck.getCards().get(0).toString() = " + deck.getCards().get(0).toString());
        System.out.println("deck.getCards().get(0).toString() = " + deck.getCards().get(10).toString());
        System.out.println("deck.getCards().get(0).toString() = " + deck.getCards().get(107).toString());
        
        Game game = new Game(input, output);
        game.startGame();
        Deck testDeck = game.getDeck();
        System.out.println("testDeck.getCards().size() = " + testDeck.getCards().size());
    }

    private void readUserInput() {

    }

    private void updateState() {

    }

    private void printState() {

    }

    private boolean askContinue() {
        return cont;
    }
}
