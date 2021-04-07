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
