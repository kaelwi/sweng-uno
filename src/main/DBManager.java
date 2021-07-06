package main;

import java.sql.*;
import java.util.List;
import java.util.Objects;

/**
 *  This class manages access to a database and the insertion and/or update of data.
 *
 *  Last Modified: 01.07.2021
 *  @author  Paulina Safar
 *           Julia Summer
 *           Karoline E. Wild
 */

public class DBManager {

    private static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:uno.sqlite");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     *  Drop table if already exists (from previous games). Create new one.
     */
    public static void createTable() {
        String create = "CREATE TABLE uno (player varchar(255) PRIMARY KEY, points int)";
        String drop = "DROP TABLE IF EXISTS uno";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(drop);
            statement.executeUpdate(create);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * If there is an existing table, update values (points). Otherwise insert new values.
     *
     * @param players List of all players to fill the table with their names and points after each round.
     */
    public static void insertOrUpdate(List<Player> players) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM uno");
            if (resultSet.next()) {
                update(players);
            } else {
                insert(players);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void update(List<Player> players) {
        for (Player player : players) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE uno SET points = ? WHERE player = ?");
                int points = Objects.requireNonNull(selectPoints(player)).getInt("points") + player.getPoints();
                preparedStatement.setInt(1, points);
                preparedStatement.setString(2, player.getName());
                preparedStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    private static void insert(List<Player> players) {
        for (Player player : players) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO uno (player, points) VALUES (?, ?)");
                preparedStatement.setString(1, player.getName());
                preparedStatement.setInt(2, player.getPoints());
                preparedStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /**
     * Get points of a specific player.
     *
     * @param player The player whose points are wished for
     * @return ResultSet result of the SELECT statement constraint by the player (null if anything goes wrong)
     */
    public static ResultSet selectPoints(Player player) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT points FROM uno WHERE player = ?");
            preparedStatement.setString(1, player.getName());
            return preparedStatement.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * Gets SELECT * FROM.
     *
     * @return ResultSet of the above Query (or null if anything goes wrong)
     */
    public static ResultSet selectAll() {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery("SELECT * FROM uno");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
