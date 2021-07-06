package main;

import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 *  The main.App class implements a game loop (incl. initialization, entrance point of reading the user input, update of
 *  the game) and this is the place where the winner of the round and/or game is checked.
 *
 *  Last Modified: 01.07.2021
 *  @author  Julia Summer
 *           Paulina Safar
 *           Karoline E. Wild
 */

public class App {
    private final Scanner input;
    private final PrintStream output;
    public static boolean beginning = true;
    public static boolean exit = false;

    public App(Scanner input, PrintStream output) {
        this.input = input;
        this.output = output;
    }

    public void Run() {
        initialize();
        Printer.printState(exit, Game.getDiscardDeck().checkReverse(), Game.getDiscardDeck().getDiscardDeckCard());

        while (!exit) {
            Printer.printPlayerCards(Game.getPlayer(Game.getTurn()));
            readUserInput(Game.getPlayer(Game.getTurn()));
            Printer.printState(exit, Game.getDiscardDeck().checkReverse(), Game.getDiscardDeck().getDiscardDeckCard());
        }
    }
    /**
     * Initial setup
     */
    private void initialize() {
        DBManager.createTable();
        Game.startGame(input);
        Printer.printBegin(Game.getPlayers());
        Printer.whoBegins(Game.getPlayer(Game.getTurn()));
        Game.doChecks(beginning);
        beginning = false;
    }

    /**
     * This Method reads the card the User wants to play
     * @param player (whose input shall be read)
     */
    private void readUserInput(Player player) {
        output.println("Tell me your next step.");
        Card cardToBePlayed = player.turn(Game.getDiscardDeck().getDiscardDeckCard());
        updateState(cardToBePlayed, player);
    }

    /**
     * This Method performs checks necessary for playing the game according to the UNO rules, e.g.:
     * check if a player played a valid card
     * check if a player played a special card
     * check if a player played all his cards (has no more cards in hand) and is thus a winner of the round.
     * In the last case the round is finished.
     */
    private void updateState(Card card, Player player) {
        if (exit) {
            Printer.printEndGame();
        } else {
            if (card != null) {
                if (Game.moveValidation(card, player)) {
                    Game.getDiscardDeck().addCardToDiscardDeck(card);
                }
            }

            Game.doChecks(beginning);
            checkWinner(player);
        }
    }

    /**
     * This Method determines if a player has played all of his cards and has no cards left in hand.
     * Such a player is then a winner of the round, and receives points for all the cards
     * that are still in the opponents' hands.
     */
    private void checkWinner(Player player) {
        if (player.getPlayerCards().isEmpty()) {
            Printer.printWin(player);
            for (Player p : Game.getPlayers()) {
                if (!p.equals(player)) {
                    for (Card cardsLeft : p.getPlayerCards()) {
                        player.setPoints(player.getPoints() + cardsLeft.getPoints());
                    }
                }
            }

            DBManager.insertOrUpdate(Game.getPlayers());
            Printer.printEndRound();
            exit = checkOverallWinner(Game.getPlayers());
            if (!exit) {
                Game.newRound();
                Printer.startingNewRound();
                Printer.whoBegins(Game.getPlayer(Game.getTurn()));
                Game.checkStartingColor();
            }
        }
    }

    /**
     * This Method determines the winner of the game, as per the number of collected points in rounds.
     * @param players List of all players to check the overall winner within the DB
     */
    private boolean checkOverallWinner(List<Player> players) {
        try {
            for (Player player : players) {
                ResultSet resultSet = DBManager.selectPoints(player);
                if (resultSet != null) {
                    while (resultSet.next()) {
                        if (resultSet.getInt("points") >= 500) {
                            output.println();
                            output.println("main.Player " + player.getName() + " wins this game with " + resultSet.getInt("points") + " points");
                            return true;
                        }
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}