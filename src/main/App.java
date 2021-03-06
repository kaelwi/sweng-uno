package main;

import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 *  The App class implements a game loop (incl. initialization, entrance point of reading the user input, update of
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
        Printer.printState(exit, Game.getDiscardDeck().getDiscardDeckCard());

        while (!exit) {
            Printer.printPlayerCards(Game.getPlayer(Game.getTurn()));
            readUserInput(Game.getPlayer(Game.getTurn()));
            Printer.printState(exit, Game.getDiscardDeck().getDiscardDeckCard());
        }
    }

    private void initialize() {
        DBManager.createTable();
        Game.startGame(input);
        Printer.printBegin(Game.getPlayers());
        Printer.whoBegins(Game.getPlayer(Game.getTurn()));
        Game.doChecks(beginning);
        beginning = false;
    }

    private void readUserInput(Player player) {
        output.println("Tell me your next step.");
        Card cardToBePlayed = player.turn(Game.getDiscardDeck().getDiscardDeckCard());
        updateState(cardToBePlayed, player);
    }

    private void updateState(Card card, Player player) {
        if (exit) {
            Printer.printEndGame();
        } else {
            if (card != null) {
                if (Game.moveValidation(card, player)) {
                    Game.getDiscardDeck().addCardToDiscardDeck(card);
                }
            } else {
                Card helperCard = new Card(Game.getDiscardDeck().getDiscardDeckCard().getColor(), Game.getDiscardDeck().getDiscardDeckCard().getValue(), -1, Game.getDiscardDeck().getDiscardDeckCard().getColorCode());
                Game.getDiscardDeck().addCardToDiscardDeck(helperCard);
            }

            player.setCanDraw(true);
            Game.doChecks(beginning);
            checkWinner(player);
        }
    }

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
                beginning = true;
                Game.newRound();
                Printer.startingNewRound();
                Printer.whoBegins(Game.getPlayer(Game.getTurn()));
                Game.doChecks(beginning);
                beginning = false;
                // Game.checkStartingColor();
            }
        }
    }

    private boolean checkOverallWinner(List<Player> players) {
        try {
            for (Player player : players) {
                ResultSet resultSet = DBManager.selectPoints(player);
                if (resultSet != null) {
                    while (resultSet.next()) {
                        if (resultSet.getInt("points") >= 500) {
                            output.println();
                            output.println("Player " + player.getName() + " wins this game with " + resultSet.getInt("points") + " points");
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
