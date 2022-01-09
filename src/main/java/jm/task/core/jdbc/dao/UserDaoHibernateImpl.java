package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.jws.soap.SOAPBinding;
import javax.persistence.criteria.CriteriaQuery;
import org.hibernate.query.Query;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {


    private static long USERS_COUNT = 0;
    private static final String TABLE_NAME = "User";

    private SessionFactory sessionFactory = Util.getSession();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            String sqlQuery = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" ("
                    + "Id BIGINT NOT NULL,"
                    + "Name VARCHAR(255) NOT NULL,"
                    + "LastName VARCHAR(255) NOT NULL,"
                    + "Age TINYINT NOT NULL)";

            Query query = session.createSQLQuery(sqlQuery).addEntity(User.class);;
            query.executeUpdate();
            transaction.commit();
        }catch(org.hibernate.HibernateException e){
            sessionFactory.getCurrentSession().getTransaction().rollback();
            System.out.println("Create table error");
        }
    }

    @Override
    public void dropUsersTable() {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createSQLQuery("DROP TABLE IF EXISTS "+TABLE_NAME).addEntity(User.class);;
            query.executeUpdate();
            transaction.commit();
        }catch(org.hibernate.HibernateException e){
            sessionFactory.getCurrentSession().getTransaction().rollback();
            System.out.println("Delete table error");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try(Session session = sessionFactory.openSession()) {
            User user = new User(name,lastName,age);
            user.setId(USERS_COUNT++);
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        }catch(org.hibernate.HibernateException e){
            sessionFactory.getCurrentSession().getTransaction().rollback();
            System.out.println("Add user error");
        }
        System.out.printf("User с именем – %s добавлен в базу данных\n", name);
    }

    @Override
    public void removeUserById(long id) {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(session.get(User.class, id));
            transaction.commit();
        }catch(org.hibernate.HibernateException e){
            sessionFactory.getCurrentSession().getTransaction().rollback();
            System.out.println("Remove user error");
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = null;
        try(Session session = sessionFactory.openSession()) {
            EntityManager em = sessionFactory.createEntityManager();
            em.getTransaction().begin();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<User> personCriteria =  cb.createQuery(User.class);
            Root<User> personRoot = personCriteria.from(User.class);
            personCriteria.select(personRoot);
            list = em.createQuery(personCriteria).getResultList();
            em.getTransaction().commit();
        }catch(org.hibernate.HibernateException e){
            sessionFactory.getCurrentSession().getTransaction().rollback();
            System.out.println("GetList user error");
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaDelete<User> query = builder.createCriteriaDelete(User.class);
            query.from(User.class);
            session.createQuery(query).executeUpdate();
            transaction.commit();
        }catch(org.hibernate.HibernateException e){
            sessionFactory.getCurrentSession().getTransaction().rollback();
            System.out.println("Clear error");
        }
    }
}
