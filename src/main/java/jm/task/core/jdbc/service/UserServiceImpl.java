package jm.task.core.jdbc.service;


import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDaoJDBCImpl dao = new UserDaoJDBCImpl();

    /**
     *
     * Create Data base
     */

    public void createUsersTable() {
        dao.createUsersTable();
    }

    /**
     *
     * Delete Data base
     */
    public void dropUsersTable() {
        dao.dropUsersTable();
    }

    /**
     *
     * Save users data base
     */
    public void saveUser(String name, String lastName, byte age) {
        dao.saveUser(name, lastName, age);
    }

    /**
     *
     * Remove users data base by id
     */
    public void removeUserById(long id) {
        dao.removeUserById(id);
    }

    /**
     *
     * Get all users list
     */
    public List<User> getAllUsers() {
        return dao.getAllUsers();
    }


    /**
     *
     * Clean data base
     */
    public void cleanUsersTable() {
        dao.cleanUsersTable();
    }
}
