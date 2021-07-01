import java.io.PrintStream;
import java.util.Scanner;

public class App {
    private final Scanner input;
    private final PrintStream output;
    public static boolean exit = false;

    public App(Scanner input, PrintStream output){
        this.input = input;
        this.output = output;
    }

    public void Run() {
        initialize();
        Printer.printState(exit, Game.getDiscardDeck().checkReverse(), Game.getDiscardDeck().getDiscardDeckCard());

        while(!exit) {
            Printer.printPlayerCards(Game.getPlayer(Game.getTurn()));
            readUserInput(Game.getPlayer(Game.getTurn()));
            Printer.printState(exit, Game.getDiscardDeck().checkReverse(), Game.getDiscardDeck().getDiscardDeckCard());
        }
    }

    private void initialize() {
        //TODO: Initialisierungen hier durchführen
        Game.startGame();
        Printer.printBegin(Game.getPlayers());
        Printer.whoBegins(Game.getPlayer(Game.getTurn()));
    }

    private void readUserInput(Player player) {
        //TODO: Alle Eingaben der Benutzer einlesen
        output.println("Tell me your next step.");
        Card cardToBePlayed = player.turn(Game.getDiscardDeck().getDiscardDeckCard());
        updateState(cardToBePlayed, player);
    }

    private void updateState(Card card, Player player) {
        //TODO: Benutzereingaben verarbeiten
        if (card != null) {
            if (Game.moveValidation(card, player)) {
                Game.getDiscardDeck().addCardToDiscardDeck(card);
            }
        }

        Game.doChecks();
        checkWinner(player);

//        if (Game.getDiscardDeck().checkReverse()) {
//            Game.setReverse();
//        }
//
//        checkColorChange();
//
//        Game.setTurn(Game.getTurn() + Game.getReverse());
//        doChecks();
//        Game.turnOverflow();
    }

    private void printState() {
        //TODO: Ausgabe des aktuellen Zustands
        Printer.printState(exit, Game.getDiscardDeck().checkReverse(), Game.getDiscardDeck().getDiscardDeckCard());
    }

    private void checkColorChange() {
        if (Game.getDiscardDeck().getDiscardDeckCard().getColor().equals("")) {
            boolean rightInput = false;
            String color = "";
            while(!rightInput) {
                output.println("You can choose the color.");
                color = input.nextLine();
                for (int i = 0; i < Card.getAllColors().length-1; i++) {
                    if (color.equals(Card.getAllColors()[i])) {
                        rightInput = true;
                    }
                }
                if (!rightInput) {
                    output.println("Invalid choice of color!");
                }
            }
            Game.getDiscardDeck().addCardToDiscardDeck(new Card(color, Game.getDiscardDeck().getDiscardDeckCard().getValue(), -1));
        }
    }

    private void doChecks() {
        Game.turnOverflow();
        checkStop();
        checkTakeTwo();
        checkTakeFour();
        Game.checkEmptyDeck();
    }

    private void checkStop() {
        if (Game.getDiscardDeck().getDiscardDeckCard().getValue().equals("X")) {
            printState();
            Printer.printPlayerCards(Game.getPlayer(Game.getTurn()));
            output.println("Stop! You are not allowed to play!");
            Game.setTurn(Game.getTurn() + Game.getReverse());
        }
    }

    private void checkTakeTwo() {
        if (Game.getDiscardDeck().getDiscardDeckCard().getValue().equals("+2")) {
            printState();
            Printer.printPlayerCards(Game.getPlayer(Game.getTurn()));
            output.println("You have to take 2 cards!");
            for (int i = 0; i < 2; i++) {
                Game.givePlayerDrawCards(Game.getPlayer(Game.getTurn()), 1);
                Game.checkEmptyDeck();
            }
            Game.setTurn(Game.getTurn() + Game.getReverse());
        }
    }

    private void checkTakeFour() {
        if(Game.getDiscardDeck().getDiscardDeckCard().getValue().equals("W+4")) {
            printState();
            Printer.printPlayerCards(Game.getPlayer(Game.getTurn()));
            System.out.println("You have to take 4 cards!");
            for (int i = 0; i < 4; i++) {
                Game.givePlayerDrawCards(Game.getPlayer(Game.getTurn()), 1);
                Game.checkEmptyDeck();
            }

            Game.setTurn(Game.getTurn() + Game.getReverse());
        }
    }

    private void checkWinner(Player player) {
        if (player.getPlayerCards().isEmpty()) {
            // Printer print congrats?
            output.println();
            output.println("Congratulations! Player " + player + " wins this round.");
            output.println();
            for (Player p : Game.getPlayers()) {
                if (!p.equals(player)) {
                    for (Card cardsLeft : p.getPlayerCards()) {
                        player.setPoints(player.getPoints()+cardsLeft.getPoints());
                    }
                }
            }
            // Printer print end of round and points
            exit = true;
        }

    }
}
