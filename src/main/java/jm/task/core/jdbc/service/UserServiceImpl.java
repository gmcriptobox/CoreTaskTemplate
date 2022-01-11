package jm.task.core.jdbc.service;


import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;


import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDao dao = new UserDaoHibernateImpl();

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
