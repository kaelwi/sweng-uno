import java.util.List;

public class Human extends Player {
    public Human(String name, List<Card> giveCards) {
        super(name, giveCards);
    }

    @Override
    public Card getCardToPlay(Card discardDeckCard) {
        return null;
    }

    @Override
    public Card turn(Card card) {
        String[] userInput = cleanUserInput();
        return moveValidation(userInput);
    }

    @Override
    public String colorWish() {
        return input.nextLine();
    }

    @Override
    public String challengeWish() {
        return input.nextLine();
    }

    private String[] cleanUserInput() {
        String inputLine = input.nextLine();
        inputLine = inputLine.replaceFirst("^\\s*", "");
        return inputLine.split(" ");
    }

    private Card moveValidation(String[] input) {
        switch (input[0]) {
            case "help":
                Printer.printHelp();
                return moveValidation(cleanUserInput());
            case "status":
                Printer.printStatus();
                return moveValidation(cleanUserInput());
            case "exit":
                App.exit = true;
                return null;
            case "draw":
                Game.givePlayerDrawCards(this, 1);
                return null;
            default:
                return cardValidation(input);
        }
    }

    private Card cardValidation(String[] input) {
        boolean valid = false;
        Card cardToBeChecked;
        do {
            cardToBeChecked = isCardOnHand(input[0]);

            if (cardToBeChecked == null) {
                Printer.noSuchCardOnHand();
                input = cleanUserInput();
                if (moveValidation(input) == null) {
                    valid = true;
                }
            } else {
                valid = true;
                printMove(input);
                removeCardFromHand(cardToBeChecked);
            }
        } while (!valid);
        return cardToBeChecked;
    }

    private void printMove(String[] input) {
        if (checkUno() && (input.length < 2 || !input[1].equalsIgnoreCase("UNO"))) {
            output.println("Oh no, you forgot to shout uno...");
            Game.missingUnoPenalty(this);
        } else if (checkUno() && input[1].equals("UNO")) {
            output.println(input[0] + " " + input[1]);
        } else {
            output.println(input[0]);
        }
    }

    @Override
    public void printCards() {
        for (int i = 0; i < getPlayerCards().size(); i++) {
            output.print(getPlayerCards().get(i) + ", ");
        }
    }
}
