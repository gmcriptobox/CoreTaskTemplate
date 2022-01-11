package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static long USERS_COUNT = 0;
    private static final String TABLE_NAME = "User";
    private static final Connection connection = Util.getConnection();



    public UserDaoJDBCImpl() {

    }




    public void createUsersTable() {
        try(Statement statement = connection.createStatement()) {
            String sqlQuery = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" ("
                    + "Id BIGINT NOT NULL,"
                    + "Name VARCHAR(255) NOT NULL,"
                    + "LastName VARCHAR(255) NOT NULL,"
                    + "Age INTEGER NOT NULL)";
            statement.execute(sqlQuery);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.out.println("Create table error");
        }
    }

    public void dropUsersTable() {
        try(Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS "+TABLE_NAME);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.out.println("Drop table error");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name,lastName,age);
        user.setId(USERS_COUNT++);

        try(PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO "
                    + TABLE_NAME +
                    " VALUES(?,?,?,?)")) {

            statement.setLong(1,user.getId());
            statement.setString(2,name);
            statement.setString(3,lastName);
            statement.setInt(4,age);

            statement.executeUpdate();
            System.out.printf("User с именем – %s добавлен в базу данных\n", name);

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.out.println("Add error");
        }

    }

    public void removeUserById(long id) {
        try(PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM "
                    + TABLE_NAME +
                    " WHERE id =?")) {
            statement.setLong(1,id);
            statement.executeUpdate();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.out.println("Remove error");
        }
    }

    public List<User> getAllUsers(){
        List<User> users = new ArrayList<>();
        try(Statement statement = connection.createStatement()) {
            String sqlQuery = "SELECT * FROM " + TABLE_NAME;
            ResultSet result = statement.executeQuery(sqlQuery);

            while(result.next()){
                User user = new User(
                        result.getString("Name"),
                        result.getString("LastName"),
                        (byte)result.getInt("Age"));

                user.setId(result.getLong("Id"));
                users.add(user);
            }
            result.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.out.println("Get all error");
        }
        return users;
    }

    public void cleanUsersTable() {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE " + TABLE_NAME);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.out.println("Clean error");
        }

    }
}
