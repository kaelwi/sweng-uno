import java.util.ArrayList;
import java.util.Iterator;

public class Bot extends Player {

    public Bot(String name, ArrayList<Card> playerCards) {
        super(name, playerCards);
    }


    public Card getCardToPlay(Card dicardDeckCard) {

        for (Card card : getPlayerCards()) {
            if (Game.cardValidation(card, dicardDeckCard)) {
                return card;
            }
        }

        return null;

    } 






}
