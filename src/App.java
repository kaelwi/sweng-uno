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
        Game game = new Game(input, output);
        game.startGame();

//        System.out.println("game = " + game.getDeck().getCards().get(0));
//        System.out.println("game.getPlayer()[0].getPlayerCards().size() = " + game.getPlayer()[0].getPlayerCards().size());
//        System.out.println("game.getPlayer()[0].getPlayerCards().get(0) = " + game.getPlayer()[0].getPlayerCards().get(0));
//        System.out.println("game.getDiscardDeck().getCards().get(0) = " + game.getDiscardDeck().getCards().get(0));

        System.out.println("game = " + game.getDeck().getCards().size());
        System.out.println("game discard deck = " + game.getDiscardDeck().getCards().size());

        for (int i = 0; i < 10; i++) {
            System.out.println("deck = " + game.getDeck().getCards().get(i));
        }

        printState(game);
    }

    private void readUserInput() {

    }

    private void updateState() {

    }

    private void printState() {

    }

    private void printState(Game game) {

    }

    private boolean askContinue() {
        return cont;
    }
}
