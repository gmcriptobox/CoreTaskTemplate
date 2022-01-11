package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {


    private static long USERS_COUNT = 0;
    private static final String TABLE_NAME = "User";

    private SessionFactory sessionFactory = Util.getSession();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            String sqlQuery = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" ("
                    + "Id BIGINT NOT NULL,"
                    + "Name VARCHAR(255) NOT NULL,"
                    + "LastName VARCHAR(255) NOT NULL,"
                    + "Age TINYINT NOT NULL)";

            session.createSQLQuery(sqlQuery).addEntity(User.class).executeUpdate();
            transaction.commit();
        }catch(HibernateException e){
            if(transaction != null)
                transaction.rollback();
            System.out.println("Create table error");
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS "+TABLE_NAME).addEntity(User.class).executeUpdate();
            transaction.commit();
        }catch(HibernateException e){
            if(transaction != null)
                transaction.rollback();
            System.out.println("Delete table error");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try(Session session = sessionFactory.openSession()) {
            User user = new User(name,lastName,age);
            user.setId(++USERS_COUNT);
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        }catch(HibernateException e){
            sessionFactory.getCurrentSession().getTransaction().rollback();
            System.out.println("Add user error");
        }
        System.out.printf("User с именем – %s добавлен в базу данных\n", name);
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("DELETE User WHERE id = :id").setParameter("id",id).executeUpdate();
            transaction.commit();
        }catch(HibernateException e){
            if(transaction != null)
                transaction.rollback();
            System.out.println("Remove user error");
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = null;
        try(Session session = sessionFactory.openSession()) {
            list = session.createQuery("from User").getResultList();
        }catch(HibernateException e){
            System.out.println("GetList user error");
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            transaction.commit();
        }catch(HibernateException e){
            if(transaction != null)
                transaction.rollback();
            System.out.println("Clear error");
        }
    }
}
