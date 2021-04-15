import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Game {

    private ArrayList<Player> player;
    // private Player[] player = new Player[4];
    private Deck deck;
    private Deck discardDeck;
    private final Scanner input;
    private final PrintStream output;
    private int turn = setFirst();

    // Constructor
    public Game(Scanner input, PrintStream output) {
        this.input = input;
        this.output = output;
        deck = new Deck(108);
        discardDeck = new Deck(0);
        player = new ArrayList<Player>(4);
//        for (int i = 0; i < 4; i++) {
//            player[i] = new Player();
//        }
    }

    // start game - fill the deck, shuffle it, remove first card to discarddeck
    public void startGame() {
        deck.fillDeck();
        deck.shuffleCards();
        setPlayers();
        Collections.shuffle(player);
//        for (int i = 0; i < 4; i++) {
//            // setPlayers in class game, because of missing input and output stream in other classes
//            setPlayers(i);
//        }
        discardDeck.addCardToDiscardDeck(deck.getCards().get(0));
        deck.removeCardFromDeck();
    }

    // set the players - get their names and give them 7 cards each
    private void setPlayers() {
        output.println("Tell me how many bots you would like to involve: ");
        int bots = checkNrBots();
        createBots(bots);

        output.println("Now let's settle the rest!");
        output.println("You may create " + (4-bots) + " players.");
        createPlayers(4-bots);

//        output.println("Please enter your name (Player " + (i+1) + "): ");
//        String name = input.nextLine();
//
//        while(name.isEmpty()) {
//            output.println("Unfortunately, you did not enter any name. Please, try it again.");
//            name = input.nextLine();
//        }
//
//        player.add(new Player());
//
//        player.get(i).setName(name);
//        player.get(i).setPlayerCards(deck.giveCards());

    }

    private void createPlayers(int humans) {
        for (int i = 0; i < humans; i++) {
            output.println("Please enter a name for your player (player nr. " + (i+1) + ")");
            String name = input.nextLine();

            while(name.isEmpty() || playerNames().contains(name)) {
                output.println("I'm sorry, this is not working. Either you did not enter any name, or the name is already taken. Please try again.");
                name = input.nextLine();
            }
            player.add(new Player(name, deck.giveCards()));
            System.out.println(player.get(player.size()-1) instanceof Player);
            System.out.println(player.get(player.size()-1) instanceof Bot);
        }
    }


    private void createBots(int bots) {
        for (int i = 0; i < bots; i++) {
            output.println("Please enter a name for bot nr. " + (i+1));
            String name = input.nextLine();

            while(name.isEmpty() || playerNames().contains(name)) {
                output.println("I'm sorry, this is not working. Either you did not enter any name, or the name is already taken. Please try again.");
                name = input.nextLine();
            }
            player.add(new Bot(name, deck.giveCards()));
            System.out.println(player.get(player.size()-1) instanceof Player);
            System.out.println(player.get(player.size()-1) instanceof Bot);
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
                output.println("The maximum of allowed players is 4!");
            } else {
                output.println("You know you need at least 1 player...");
            }
            bots = Integer.parseInt(input.nextLine());
        }
        return bots;
    }

    // getter for deck
    public Deck getDeck() {
        return deck;
    }

    // getter for discarddeck
    public Deck getDiscardDeck() {
        return discardDeck;
    }

    ///////////////////////////////////////////////////////////////
    // getPlayer
    // parameter: int Position
    // return: Player
    // A method to get one player at a certain position in the player-array
    ///////////////////////////////////////////////////////////////
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
        // output.println("How many cards do you want to/have to draw?");
        // nextInt problematic, \n stays in buffer -> parse whole input line into an integer
        // int number = Integer.parseInt(input.nextLine());
        player.takeCards(this.getDeck().takeCards(number));
    }

    public void missingUnoPenalty(Player player) {
        player.takeCards(this.getDeck().takeCards(2));
    }

    public void giveOnePenaltyCard(Player player) {
        player.takeCards(this.getDeck().takeCards(1));
    }

    // not taking care of wild cards yet
    public boolean colorValidation(Card card) {
        boolean equalColor = this.getDiscardDeck().getCards().get(this.getDiscardDeck().getCards().size()-1).getColor().equals(card.getColor());
        boolean wildCardOnHand = card.getColor().equals("");
        boolean wildCardOnDiscardDeck = this.getDiscardDeck().getCards().get(this.getDiscardDeck().getCards().size()-1).getColor().equals("");
        return equalColor || wildCardOnDiscardDeck || wildCardOnHand;
    }

    // not taking care of wild cards yet
    public boolean valueValidation(Card card) {
        boolean equalColor = this.getDiscardDeck().getCards().get(this.getDiscardDeck().getCards().size()-1).getValue().equals(card.getValue());
        boolean wildCardOnHand = card.getValue().equals("W") || card.getValue().equals("W+4");
        boolean wildCardOnDiscardDeck = this.getDiscardDeck().getCards().get(this.getDiscardDeck().getCards().size()-1).getValue().equals("W") ||
                this.getDiscardDeck().getCards().get(this.getDiscardDeck().getCards().size()-1).getValue().equals("W+4");
        return equalColor || wildCardOnDiscardDeck || wildCardOnHand;
    }

    public void reversePlayers() {
        Collections.reverse(player);
    }

    public void setDirectionTurn(int i, int direction) {
        turn = i + 1*direction;
    }
}
