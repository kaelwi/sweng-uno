import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Game {

    private ArrayList<Player> player;
    private Deck deck;
    private Deck discardDeck;
    private final Scanner input;
    private final PrintStream output;
    private int turn = setFirst();

    public Game(Scanner input, PrintStream output) {
        this.input = input;
        this.output = output;
        deck = new Deck(108);
        discardDeck = new Deck(0);
        player = new ArrayList<Player>(4);
    }

    public void startGame() {
        deck.fillDeck();
        deck.shuffleCards();
        setPlayers();
        // Collections.shuffle(player);                             // OPTIONAL
        discardDeck.addCardToDiscardDeck(deck.getCards().get(0));
        deck.removeCardFromDeck();
    }

    private void setPlayers() {
        output.println("Tell me how many bots would you like to involve: ");
        int bots = checkNrBots();
        createBots(bots);

        output.println("Now let's settle the rest!");
        output.println("You may create " + (4-bots) + " players.");
        createPlayers(4-bots);
    }

    private void createPlayers(int humans) {
        for (int i = 0; i < humans; i++) {
            output.println("Please enter a name for your player (player nr. " + (i+1) + ")");
            String name = input.nextLine();

            while(name.isEmpty() || playerNames().contains(name)) {
                output.println("I'm sorry, you either did not enter a name, or the entered name is already taken. Please try again.");
                name = input.nextLine();
            }
            player.add(new Player(name, deck.giveCards()));
        }
    }


    private void createBots(int bots) {
        for (int i = 0; i < bots; i++) {
            output.println("Please enter a name for bot nr. " + (i+1));
            String name = input.nextLine();

            while(name.isEmpty() || playerNames().contains(name)) {
                output.println("I'm sorry, you either did not enter a name, or the entered name is already taken. Please try again.");
                name = input.nextLine();
            }
            player.add(new Bot(name, deck.giveCards()));
        }
    }

    private ArrayList<String> playerNames() {
        ArrayList<String> names = new ArrayList<>();
        for (Player player : player) {
            names.add(player.getName());
        }
        return names;
    }

    private int checkNrBots() {
        int bots = Integer.parseInt(input.nextLine());
        while(bots < 0 || bots > 4) {
            if (bots > 4) {
                output.println("The maximum number of allowed players is 4!");
            } else {
                output.println("You know you need at least 1 player...");
            }
            bots = Integer.parseInt(input.nextLine());
        }
        return bots;
    }

    public Deck getDeck() {
        return deck;
    }

    public Deck getDiscardDeck() {
        return discardDeck;
    }

    public Player getPlayer(int position) {
        return player.get(position);
    }

    public ArrayList<Player> getPlayers() {
        return player;
    }

    public int getTurn() {
        return turn;
    }

    private int setFirst() {
        Random random = new Random();
        return random.nextInt(3);
    }

    public void setTurn(int i) {
        turn = i;
    }

    public void printHelp() {
        output.println("Print help - needs to be done.");
        output.println();
    }

    public void givePlayerDrawCards(Player player, int number) {
        player.takeCards(this.getDeck().takeCards(number));
    }

    public void missingUnoPenalty(Player player) {
        player.takeCards(this.getDeck().takeCards(2));
    }

    public void giveOnePenaltyCard(Player player) {
        player.takeCards(this.getDeck().takeCards(1));
    }

    public static boolean cardValidation(Card card, Card discardDeckCard) {
        if (card.getValue().equals(discardDeckCard.getValue()) || card.getColor().equals(discardDeckCard.getColor()) || card.getColor().equals("") || discardDeckCard.getColor().equals("")) {
            return true;
        }

        return false;
    }
}
