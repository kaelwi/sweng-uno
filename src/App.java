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
            printPlayerCards();
            readUserInput(game.getPlayer(game.getTurn()));
            updateState();
            printState();

            // exit = !askContinue();
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
        output.println("Tell me what your next step shall be.");
        userInput = input.nextLine();

        switch (userInput) {
            case "exit":
                exit = true;
                break;
            case "help":
                game.printHelp();
                break;
            case "ziehen":
                game.givePlayerDrawCards(player);
                break;
            default:
                cardValidation(player);
        }

    }

    private void cardValidation(Player player) {
        boolean valid = false;
        do {
            Card cardToBeChecked = player.isCardOnHand(userInput);

            if(cardToBeChecked != null) {
                game.getDiscardDeck().addCardToDiscardDeck(cardToBeChecked);
                valid = true;
            }

            if (!valid) {
                if (!input.hasNextInt()) {
                    System.out.println("It seems there is no such card on your hand...");
                    output.println();
                }
                output.println("Which card do you want to play?");
                userInput = input.nextLine();
            } else {

            }
        } while(!valid);
    }


    private void updateState() {
        if(!userInput.equals("help")) {
            if (game.getTurn() >= 3) {
                game.setTurn(0);
            } else {
                game.setTurn(game.getTurn() + 1);
            }
        }
    }

    /////////////////////
    // printstate
    // parameter: void
    // return: void
    // Was macht die Methode
    /////////////////////
    private void printState() {
        if(!exit) {
            output.println("Ablagestapel: " + game.getDiscardDeck().getCards().get(game.getDiscardDeck().getCards().size()-1));
            output.println();
        }

    }

    private void printPlayerCards() {
        output.print("Player " + game.getPlayer(game.getTurn()) + " cards: ");
        for (int i = 0; i < game.getPlayer(game.getTurn()).getPlayerCards().size(); i++) {
            output.print(game.getPlayer(game.getTurn()).getPlayerCards().get(i) + ", ");
        }
        output.println();
    }


    private boolean askContinue() {
        return cont;
    }
}
