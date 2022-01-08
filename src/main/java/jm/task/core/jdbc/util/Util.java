package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    static private String URL = "jdbc:mysql://localhost:3306/testbase?autoReconnect=true&useSSL=false&serverTimezone=UTC";
    static private String USER_NAME = "admin";
    static private String PASSWORD = "pro100pass"; //create file property

    static{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error bd connection");
            System.exit(1);
        }
        return connection;
    }
}
