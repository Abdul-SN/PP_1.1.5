package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    Connection connection = Util.getConnection();

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS dbusers (\n" +
                "id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                "name VARCHAR(100) NOT NULL,\n" +
                "lastname VARCHAR(100) NOT NULL,\n" +
                "age TINYINT NOT NULL)";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            connection.commit();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ignore) {
                //ignore
            }
            ex.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS dbusers";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            connection.commit();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ignore) {
                //ignore
            }
            ex.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO dbusers (name, lastname, age) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();
            connection.commit();
            System.out.println("User c именем " + name + " добавлен в базу данных.");
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ignore) {
                //ignore
            }
            ex.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM dbusers WHERE ID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ignore) {
                //ignore
            }
            ex.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM dbusers";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));

                userList.add(user);
            }
            connection.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM dbusers";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            connection.commit();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ignore) {
                //ignore
            }
            ex.printStackTrace();
        }
    }
}
