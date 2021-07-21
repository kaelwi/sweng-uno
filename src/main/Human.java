package main;

import java.util.List;

/**
 *  This class creates human players, as an extension of the Player class. Also move and card validation of the current
 *  turn is done here.
 *
 *  Last Modified: 01.07.2021
 *  @author  Paulina Safar
 *           Julia Summer
 *           Karoline E. Wild
 */

public class Human extends Player {
    public Human(String name, List<Card> giveCards) {
        super(name, giveCards);
    }

    /**
     * This Method is not needed for human player, who can consciously choose a card to play.
     * @param discardDeckCard - last card on the discard deck
     * @returns - null, the player will independently chose a card to play
     */
    @Override
    public Card getCardToPlay(Card discardDeckCard) {
        return null;
    }

    /**
     * This Method takes an input from player (which card the player wishes to play) and checks if the move is valid.
     * @param card - card the player wishes to play.
     * @return validation - result of the move validity check.
     */
    @Override
    public Card turn(Card card) {
        String[] userInput = cleanUserInput();
        return moveValidation(userInput);
    }

    /**
     * This Method takes a change of color by player's input
     * and checks in Player class if the color is a valid UNO card color.
     * @return color - color that the player has chosen.
     */
    @Override
    public String colorWish() {
        String color;
        boolean rightInput;
        do {
            color = input.nextLine();
            rightInput = isRightInput(color);
        } while (!rightInput);
        return color;
    }

    /**
     * This Method takes player's wish to challenge a player that used the "Wild Draw 4" card.
     * @return String - player's input regarding the challenge.
     */
    @Override
    public String challengeWish() { return input.nextLine(); }

    private String[] cleanUserInput() {
        String inputLine = input.nextLine();
        inputLine = inputLine.replaceFirst("^\\s*", "");
        return inputLine.split(" ");
    }

    private Card moveValidation(String[] userInput) {
        String input = userInput[0].toLowerCase();
        switch (input) {
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
                if (this.getCanDraw()) {
                    Game.givePlayerDrawCards(this, 1);
                    Printer.printPlayerCards(this);
                    this.setCanDraw(false);
                } else {
                    output.println("You already took a card! If there is still no card to play, type \"skip\".");
                }
                return moveValidation(cleanUserInput());
            case "skip":
                return null;
            default:
                return cardValidation(userInput);
        }
    }

    private Card cardValidation(String[] userInput) {
        boolean valid = false;
        Card cardToBeChecked;
        String[] inputToBeChecked = userInput;
        do {
            cardToBeChecked = isCardOnHand(inputToBeChecked[0]);

            if (cardToBeChecked == null) {
                output.println(inputToBeChecked[0]);
                Printer.noSuchCardOnHand();
                inputToBeChecked = cleanUserInput();
                // if (moveValidation(inputToBeChecked) == null) {
                if (inputToBeChecked[0].equalsIgnoreCase("skip") || inputToBeChecked[0].equalsIgnoreCase("exit")) {
                    valid = true;
                }
            } else {
                valid = true;
                removeCardFromHand(cardToBeChecked);
                printMove(inputToBeChecked);
                removeCardFromHand(cardToBeChecked);
            }
        } while (!valid);
        return cardToBeChecked;
    }

    private void printMove(String[] input) {
        if (!this.checkUno()) {
            output.println(input[0]);
        } else {
            if (input.length < 2) {
                output.println("Oh no, you forgot to shout uno...");
                Game.giveOnePenaltyCard(this);
            } else {
                if (input[1].equalsIgnoreCase("UNO")) {
                    output.println(input[0] + " " + input[1]);
                } else {
                    output.println(input[0] + " " + input[1]);
                    output.println("What did you say? That was no UNO!");
                    Game.giveOnePenaltyCard(this);
                }
            }
        }
    }

    @Override
    public void printCards() {
        for (int i = 0; i < getPlayerCards().size()-1; i++) {
            output.print(getPlayerCards().get(i) + ", ");
        }
        output.print(getPlayerCards().get(getPlayerCards().size()-1));
    }
}
