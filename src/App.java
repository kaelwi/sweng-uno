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
    private String userInput;

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
            // updateState();
            printState();

            exit = !askContinue();
        }
    }

    // Initial setup
    private void initialize() {
        game = new Game(input, output);
        game.startGame();

        printBegin();
    }

    private void printBegin() {
        System.out.println();
        System.out.println("Let the game begin!");
        System.out.println();
    }

    private void readUserInput(Player player) {
        Card cardToPlay = cardValidation(player);
        updateState(cardToPlay, player);

    }

    private Card cardValidation(Player player) {
        ArrayList<Card> playersCards = player.getPlayerCards();
        boolean valid = false;

        do {
            output.println("Welche Karte möchten Sie legen?");
            userInput = input.nextLine();
            for (Card card : playersCards) {
                if(card.toString().equals(userInput)) {
                    valid = true;
                    return card;
                }
            }
            if(!valid) {
                System.out.println("Ungültige Eingabe!");
            }
        } while(!valid);

        return null;
    }

    private void updateState(Card card, Player player) {
        Iterator<Card> it = player.getPlayerCards().iterator();
        while (it.hasNext()) {
            Card cardOnHand = it.next();
            if (cardOnHand.equals(card)) {
                game.getDiscardDeck().addCardToDiscardDeck(card);
                it.remove();
            }
        }

        if (game.getTurn() >= 3) {
            game.setTurn(0);
        }
        else {
            game.setTurn(game.getTurn()+1);
        }
    }

    private void printState() {
        System.out.println();
        System.out.println("Ablagestapel: " + game.getDiscardDeck().getCards().get(game.getDiscardDeck().getCards().size()-1));
        System.out.println();
        System.out.println("Player: " + game.getPlayer(game.getTurn()).getName());
        for (int i = 0; i < game.getPlayer(game.getTurn()).getPlayerCards().size(); i++) {
            System.out.print(game.getPlayer(game.getTurn()).getPlayerCards().get(i) + ", ");
        }

    }

    private boolean askContinue() {
        return cont;
    }
}
