import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class App {

    // Attributes
    private final Scanner input;
    private final PrintStream output;
    private boolean exit = false;
    private boolean cont = true;
    private Game game;
    String userInput;


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
            readUserInput(game.getPlayer(game.getTurn()));
            updateState();
            printState();

            exit = !askContinue();
        }

    }

    // Initial setup
    private void initialize() {
        game = new Game(input, output);
        game.startGame();
        output.println();
        printBegin();
        whoBegins();
    }

    // Print players and commands.
    private void printBegin() {
        output.println();
        output.print("Welcome ");
        for (int i = 0; i < game.getPlayers().length - 1; i++) {
            output.print(game.getPlayer(i) + ", ");
        }
        output.print(game.getPlayer(game.getPlayers().length-1) + "!");
        output.println();

        output.println("In case you need help: type \"help\".");
        output.println("If you wish to end the game: type \"exit\".");
        output.println();

        output.println("Let the game begin!");
        output.println();
    }

    // print first player
    private void whoBegins() {
        output.println("Rolling dice... tossing coins...");
        output.println(game.getPlayer(game.getTurn()) + " may begin.");
        output.println();
        output.println();
    }

    // read user input
    private void readUserInput(Player player) {
        // TODO: distinguish different cases of input (card, help, exit)
        boolean valid = false;
        do {
            output.println("Which card do you want to play?");
            userInput = input.nextLine();

            Iterator<Card> it = player.getPlayerCards().iterator();
            while(it.hasNext()) {
                Card cardOnHand = it.next();
                if (cardOnHand.toString().equals(userInput)) {
                    game.getDiscardDeck().addCardToDiscardDeck(cardOnHand);
                    it.remove();
                    valid = true;
                }
            }

            if (!valid) {
                System.out.println("It seems there is no such card on your hand...");
            }
        } while(!valid);

    }

    ///////////////////// not finished, just an idea
    private Card cardValidation(Player player) {
        Iterator<Card> it = player.getPlayerCards().iterator();
        while(it.hasNext()) {
            Card cardOnHand = it.next();
        }
        return null;
    }

    private void updateState() {
        if (game.getTurn() >= 3) {
            game.setTurn(0);
        }
        else {
            game.setTurn(game.getTurn()+1);
        }
    }

    /////////////////////
    // printstate
    // parameter: void
    // return: void
    // Was macht die Methode
    /////////////////////
    private void printState() {
        System.out.println("Ablagestapel: " + game.getDiscardDeck().getCards().get(game.getDiscardDeck().getCards().size()-1));
        System.out.println();
        System.out.print("Player " + game.getPlayer(game.getTurn()) + " cards: ");
        for (int i = 0; i < game.getPlayer(game.getTurn()).getPlayerCards().size(); i++) {
            System.out.print(game.getPlayer(game.getTurn()).getPlayerCards().get(i) + ", ");
        }
        System.out.println();
    }


    private boolean askContinue() {
        return cont;
    }
}
