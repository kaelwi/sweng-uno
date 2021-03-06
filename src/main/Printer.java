package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 *  Printer class. A helper class to keep the code clean from bigger console outputs.
 *
 *  Last Modified: 21.07.2021
 *  @author  Paulina Safar
 *           Julia Summer
 *           Karoline E. Wild
 */

public class Printer {
    private static final PrintStream output = System.out;

    public static void printBegin(List<Player> players) {
        output.println();
        output.println();
        output.print("Welcome ");
        for (int i = 0; i < players.size() - 1; i++) {
            output.print(players.get(i) + ", ");
        }
        output.print(players.get(players.size() - 1) + "!");
        output.println();

        output.println("In case you need help: type \"help\".");
        output.println("If you wish to end the game: type \"exit\".");
        output.println("You want to get the actual point status? Type \"status\".");
        output.println("You have no card to play? Type \"draw\".");
        output.println("Still no card to play? Type \"skip\".");
        output.println();

        output.println("Let the Game begin!");
        output.println();
    }

    public static void whoBegins(Player player) {
        output.println("Rolling dice... tossing coins...");
        output.println(player + " may begin.");
        output.println();
        output.println();
    }

    public static void printWin(Player player) {
        output.println();
        output.println("Congratulations! Player " + player + " wins this round.");
        output.println();
    }

    public static void printState(boolean exit, Card card) {
        output.println();
        if (!exit) {
            output.println("Open card deck: " + card);
            output.println();
            if (card.getValue().equals("<->") && card.getPoints() != -1) {
                output.println("Reverse-Card has been played! Reverse direction...\n");
            }
        }
    }

    public static void printEndRound() {
        output.println("Points after this round: ");
        try {
            ResultSet resultSet = DBManager.selectAll();
            while (resultSet != null && resultSet.next()) {
                output.println("Player " + resultSet.getString(1) + ": " + resultSet.getInt(2));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void printHelp() {
        try {
            FileReader fileReader = new FileReader("help.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                output.println(line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        output.println("Everything clear? Let's move on, you still have a turn to play!");
    }

    public static void noSuchCardOnHand() {
        output.println("It seems there is no such card on your hand...");
        output.println("Or did you enter an unknown command? Available commands are: draw, skip, exit, " +
                "help, status or a card color+value from your hand.");
        output.println();
        output.println("Which card do you want to play?");
    }

    public static void printPlayerCards(Player player) {
        output.print("Player " + player + " cards: ");
        player.printCards();
        output.println();
    }

    public static void printEndGame() {
        try {
            ResultSet resultSet = DBManager.selectAll();

            if (!Objects.requireNonNull(resultSet).next()) {
                output.println("The game ended within the first round. No points where given to any player.");
            } else {
                output.println("The game ended before anyone could reach 500 points. Here are your points: ");
                do {
                    output.println("Player " + resultSet.getString(1) + ": " + resultSet.getInt(2));
                } while (resultSet.next());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void startingNewRound() {
        output.println();
        output.println();
        output.println("STARTING NEW ROUND!");
        output.println();
        output.println();
    }

    public static void printStatus() {
        try {
            ResultSet resultSet = DBManager.selectAll();

            if (!(resultSet != null && resultSet.next())) {
                output.println("You are in the first round! Everyone has a point status of 0.");
            } else {
                output.println("Status: ");
                do {
                    output.println("Player " + resultSet.getString(1) + ": " + resultSet.getInt(2));
                } while (resultSet.next());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
