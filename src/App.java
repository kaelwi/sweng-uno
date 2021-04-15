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
    private String[] userInput;
    private int reverse;


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
        reverse = 1;
        game = new Game(input, output);
        game.startGame();
        output.println();
        printBegin();
        whoBegins();
    }

    private void printBegin() {
        output.println();
        output.print("Welcome ");
        for (int i = 0; i < game.getPlayers().size() - 1; i++) {
            output.print(game.getPlayer(i) + ", ");
        }
        output.print(game.getPlayer(game.getPlayers().size()-1) + "!");
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

        if (player.checkForBot()) {
            botTurn(player);
        } else {
            playerTurn(player);
        }

    }

    private void botTurn(Player player) {
        // Bot bot = (Bot) player;
        Card card = player.getCardToPlay(game.getDiscardDeck().getDiscardDeckCard());
        String shout = "";
        if(card != null) {
            shout = card.toString();
            player.removeCardFromHand(card);
            if (checkUno(player)) {
                shout += " uno";
            }
            playCard(player, card);
            output.println(shout);
        } else {
            game.giveOnePenaltyCard(player);
            output.println("Unfortunately, I don't have anything to play.");
        }
    }

    private void playerTurn(Player player) {
        userInput = input.nextLine().split(" ");

        switch (userInput[0]) {
            case "exit":
                exit = true;
                break;
            case "help":
                game.printHelp();
                break;
            case "ziehen":
                game.givePlayerDrawCards(player, Integer.parseInt(userInput[1]));
                break;
            default:
                cardValidation(player);
        }
    }

    private void playCard(Player player, Card card) {
        game.getDiscardDeck().addCardToDiscardDeck(card);
        if (!player.checkForBot() && checkUno(player) && (userInput.length < 2 || !userInput[1].equals("uno"))) {
            output.println("Oh no, you forgot to shout UNO!");
            output.println("Take 1 card.");
            game.missingUnoPenalty(player);
        }
    }

    private void invalidMove(Player player, Card card) {
        player.takeCardBack(card);
        game.giveOnePenaltyCard(player);
    }

    private void cardValidation(Player player) {
        boolean valid = false;
        do {
            Card cardToBeChecked = player.isCardOnHand(userInput[0]);

            if (cardToBeChecked == null) {
                if (!input.hasNextInt()) {
                    output.println("It seems there is no such card on your hand...");
                    output.println();
                }
                output.println("Which card do you want to play?");
                userInput = input.nextLine().split(" ");
            } else {
                if (moveValidation(cardToBeChecked)) {
                    playCard(player, cardToBeChecked);
                    valid = true;
                } else {
                    invalidMove(player, cardToBeChecked);
                    valid = true;
                    // output.println("Which card do you want to play?");
                    // userInput = input.nextLine().split(" ");
                }
            }
        } while(!valid);
    }

    private boolean checkUno(Player player) {
        if (player.getPlayerCards().size() == 1) {
            return true;
        }
        return false;
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
        if (checkWinner()) {
            printEndRound();
            exit = true;
        } else if (game.getPlayer(game.getTurn()).checkForBot() || !userInput[0].equals("help")) {
            if (game.getDiscardDeck().checkReverse()) {
                reverse *= -1;
            }

            game.setTurn(game.getTurn() + reverse);

            if (game.getTurn() < 0) {
                game.setTurn(3);
            } else if (game.getTurn() > 3) {
                game.setTurn(0);
            }
        }
    }

    private void printEndRound() {
        output.println("Points for this round: ");
        for (Player player : game.getPlayers()) {
            output.println("Player " + player + ": " + player.getPoints());
        }
    }

    private void printState() {
        if(!exit) {
            output.println("Ablagestapel: " + game.getDiscardDeck().getDiscardDeckCard());
            output.println();
            if (game.getDiscardDeck().checkReverse()) {
                output.println("Reverse-Card has been played! Reverse direction...");
            }
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

    private boolean checkWinner() {
        boolean win = false;
        Player winner = new Player();

        for (Player player : game.getPlayers()) {
            if (player.getPlayerCards().isEmpty()) {
                output.println("Congratulations! Player " + player + " wins this round.");
                for (Card cardsLeft : player.getPlayerCards()) {
                    player.setPoints(player.getPoints()+cardsLeft.getPoints());
                }
                winner = player;
                win = true;
            }
        }

        if (win) {
            for (Player player : game.getPlayers()) {
                for (Card cardsLeft : player.getPlayerCards()) {
                    winner.setPoints(winner.getPoints()+cardsLeft.getPoints());
                }
            }
        }

        return win;
    }
}
