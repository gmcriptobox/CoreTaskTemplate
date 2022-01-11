package jm.task.core.jdbc;


import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl service = new UserServiceImpl();

        service.dropUsersTable();
        service.createUsersTable();

        service.saveUser("Danil", "Danilovich", (byte)21);
        service.saveUser("Petr", "Petrov", (byte)26);
        service.saveUser("Alexandr", "Alexandrov",(byte) 30);
        service.saveUser("Roman", "Romanov", (byte)50);

        service.getAllUsers().forEach((u)->System.out.println(u));

        service.removeUserById(1);
        service.cleanUsersTable();

        service.dropUsersTable();
    }
}
