import java.util.List;

public class Human extends Player {
    private String uno;
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
        Card validateMove = moveValidation(userInput);
        return validateMove;
    }

    private String[] cleanUserInput() {
        String inputLine = input.nextLine();
        inputLine = inputLine.replaceFirst("^\\s*", "");
        return inputLine.split(" ");
    }

    private Card moveValidation(String[] input) {
//        if (input[0].equals("help")) {
//            Printer.printHelp();
//            input = cleanUserInput();
//        }

        switch (input[0]) {
            case "help":
                Printer.printHelp();
                return moveValidation(cleanUserInput());
            case "exit":
                App.exit = true;
                return null;
            case "draw":
                Game.givePlayerDrawCards(this, Integer.parseInt(input[1]));
                return null;
            case "challenge":
                System.out.println(Game.challenge());
                return null;
            default:
                return cardValidation(input);
        }
    }

    private int commandValidation(String[] input) {
        if (input[0].equals("help")) {
            Printer.printHelp();
            input = cleanUserInput();
        }

        switch (input[0]) {
            case "exit":
                App.exit = true;
                return 0;
            case "draw":
                Game.givePlayerDrawCards(this, Integer.parseInt(input[1]));
                return 0;
            default:
                return -1;
        }
    }

    private Card cardValidation(String[] input) {
        boolean valid = false;
        Card cardToBeChecked = null;
        do {
            cardToBeChecked = isCardOnHand(input[0]);

            if (cardToBeChecked == null) {
                Printer.noSuchCardOnHand();
                input = cleanUserInput();
                if (commandValidation(input) == 0) {
                    valid = true;
                }
            } else {
                valid = true;
                printMove(input);
                removeCardFromHand(cardToBeChecked);
            }
        } while(!valid);
        return cardToBeChecked;
    }

    private void printMove(String[] input) {
        if (checkUno() && (input.length < 2 || !input[1].equals("UNO"))) {
            output.println("Oh no, you forgot to shout uno...");
            Game.missingUnoPenalty(this);
        } else if (checkUno() && input[1].equals("UNO")) {
            output.println(input[0] + " " + input[1]);
        } else {
            output.println(input[0]);
        }
    }
}
