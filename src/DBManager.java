/**
 *  This class manages access to a database and the insertion and/or update of data.
 *
 *  Last Modified: 01.07.2021
 *  Authors: Paulina Safar
 *           Julia Summer
 *           Karoline E. Wild
 */

import java.sql.*;
import java.util.List;

public class DBManager {

    private static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:uno.sqlite");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

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

    public static void insertOrUpdate(List<Player> players) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM uno");
        if (resultSet.next()) {
            update(players);
        } else {
            insert(players);
        }
    }

    private static void update(List<Player> players) throws SQLException {
        for (Player player : players) {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE uno SET points = ? WHERE player = ?");
            int points = selectPoints(player).getInt("points") + player.getPoints();
            preparedStatement.setInt(1, points);
            preparedStatement.setString(2, player.getName());
            preparedStatement.executeUpdate();
        }
    }

    private static void insert(List<Player> players) throws SQLException {
        for (Player player : players) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO uno (player, points) VALUES (?, ?)");
            preparedStatement.setString(1, player.getName());
            preparedStatement.setInt(2, player.getPoints());
            preparedStatement.executeUpdate();
        }
    }

    public static ResultSet selectPoints(Player player) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT points FROM uno WHERE player = ?");
        preparedStatement.setString(1, player.getName());
        return preparedStatement.executeQuery();
    }

    public static ResultSet selectAll() throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery("SELECT * FROM uno");
    }
}
