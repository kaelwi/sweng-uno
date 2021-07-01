import java.util.List;

/**
 * This Class creates Bot players, as an extension of the Player class
 */
public class Bot extends Player {

    public Bot(String name, List<Card> playerCards) {
        super(name, playerCards);
    }

    /**
     * This Method checks the Player's Cards for a appropriate card to play.
     * If there is such a card, it returns it, if not, it returns null
     * @param discardDeckCard
     * @returns the appropriate card to play or null
     */
    public Card getCardToPlay(Card discardDeckCard) {
        for (Card card : this.getPlayerCards()) {
            if (Game.cardValidation(card, discardDeckCard)) {
                return card;
            }
        }
        return null;
    }

    @Override
    public Card turn(Card discardDeckCard) {
        Card card = getCardToPlay(discardDeckCard);
        if(card != null) {
            removeCardFromHand(card);
            output.println(card.toString());
            if (checkUno()) {
                output.println(" UNO!");
            }
        } else {
            output.println("Unfortunately, I don't have anything to play.");
            Game.givePlayerDrawCards(this, 1);
        }
        return card;
    }
}
