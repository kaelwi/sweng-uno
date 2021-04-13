import java.util.ArrayList;

public class Player {
    private final static String[] botNames = {"Bot1", "Bot2", "Bot3", "Bot4"};
    private int botNr = 0;
    private String name;
    private ArrayList<Card> playerCards;

    public Player() {
        this.name = this.getBotName();
    };

    // constructor
    public Player(String name, ArrayList<Card> playerCards) {
        this.name = name;
        this.playerCards = playerCards;
    }

    // setter for name
    public void setName(String name) {
        this.name = name;
    }

    // setter for player cards
    public void setPlayerCards(ArrayList<Card> playerCards) {
        this.playerCards = playerCards;
    }

    // getter for bot names
    private String getBotName() {
        String botName = botNames[botNr];
        botNr++;
        return botName;
    }

    // getter for player cards
    public ArrayList<Card> getPlayerCards() {
        return playerCards;
    }

    @Override
    public String toString() {
        return name;
    }

}
