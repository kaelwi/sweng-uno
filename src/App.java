/**
 * Class App
 * This class implements a game loop. For more detail see comments on the methods below.
 *
 * @author: first draft from FH Campus02 (game loop)
 * @contributors: Paulina Safar
 *                Julia Summer
 *                Karoline E. Wild
 * @modified: 13.4.2021
 */

import java.io.PrintStream;
import java.util.Scanner;

public class App {

    /**
     * Attributes 
     */
    private final Scanner input;
    private final PrintStream output;
    private boolean exit = false;
    private boolean cont = true;
    private Game game;
    private String userInput;


    /**
     * Constructor
     *
     * @param input (Scanner for user input)
     *        output (PrintStream)
     */
    public App(Scanner input, PrintStream output) {
        this.input = input;
        this.output = output;
    }

    /**
     * This is the main method of the class. The method Run() implements the game loop. In this method, the game is
     * set up (initialize()), the initial state is printed (printState()). Afterwards the game goes into a seemingly
     * endless loop, where the actual game takes place. The variable exit can be changed via user input (typing "exit")
     * or if the game ends (there is a player with no more cards on hand).
     *
     * The method needs no parameters and it has no return value.
     */
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

    private void whoBegins() {
        output.println("Rolling dice... tossing coins...");
        output.println(game.getPlayer(game.getTurn()) + " may begin.");
        output.println();
        output.println();
    }

    private void readUserInput(Player player) {
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

            if (cardToBeChecked == null) {
                if (!input.hasNextInt()) {
                    output.println("It seems there is no such card on your hand...");
                    output.println();
                }
                output.println("Which card do you want to play?");
                userInput = input.nextLine();
            } else {
                if (moveValidation(cardToBeChecked)) {
                    game.getDiscardDeck().addCardToDiscardDeck(cardToBeChecked);
                    valid = true;
                } else {
                    output.println("Which card do you want to play?");
                    userInput = input.nextLine();
                }
            }
        } while(!valid);
    }

    private boolean moveValidation(Card card) {
        if (game.colorValidation(card) || game.valueValidation(card)) {
            return true;
        } else {
            output.println("Illegal move!");
            return false;
        }
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
