package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static org.hibernate.cfg.AvailableSettings.JPA_JDBC_DRIVER;


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
    public static SessionFactory getSession(){
        Properties prop= new Properties();
        prop.setProperty("hibernate.connection.url", URL);
        prop.setProperty("dialect", "org.hibernate.dialect.MySQL8Dialect");
        prop.setProperty("hibernate.connection.username", USER_NAME);
        prop.setProperty("hibernate.connection.password", PASSWORD);
        prop.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        prop.setProperty("show_sql", "true");
        return new Configuration().addProperties(prop).addAnnotatedClass(User.class).buildSessionFactory();
    }
}
