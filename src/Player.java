import java.util.ArrayList;

public class Player {
    private final static String[] botNames = {"Bot1", "Bot2", "Bot3", "Bot4"};
    private int botNr = 0;
    private String name;
    private ArrayList<Card> playerCards;

    public Player() {
        this.name = this.getBotName();
    };

    public Player(String name, ArrayList<Card> playerCards) {
        this.name = name;
        this.playerCards = playerCards;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlayerCards(ArrayList<Card> playerCards) {
        this.playerCards = playerCards;
    }

    private String getBotName() {
        String botName = botNames[botNr];
        botNr++;
        return botName;
    }

    public ArrayList<Card> getPlayerCards() {
        return playerCards;
    }

}
