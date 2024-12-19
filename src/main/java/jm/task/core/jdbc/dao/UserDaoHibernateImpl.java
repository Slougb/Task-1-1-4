package jm.task.core.jdbc.dao;

import com.mysql.cj.protocol.x.Notice;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS `users` (" +
                "  `id` int NOT NULL AUTO_INCREMENT," +
                "  `Name` varchar(45) NOT NULL," +
                "  `LastName` varchar(45) NOT NULL," +
                "  `Age` int NOT NULL," +
                "  PRIMARY KEY (`id`)" +
                ") ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";
        Transaction transaction = null;
        try (SessionFactory sessionFactory = Util.getSessionFactory();
             Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS `users`;";
        Transaction transaction = null;
        try (SessionFactory sessionFactory = Util.getSessionFactory();
             Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (SessionFactory sessionFactory = Util.getSessionFactory();
             Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (SessionFactory sessionFactory = Util.getSessionFactory();
             Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
                transaction.commit();
            }
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        Transaction transaction = null;
        try (SessionFactory sessionFactory = Util.getSessionFactory();
             Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            users = session.createNativeQuery("SELECT * FROM users", User.class).list();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE users;";
        Transaction transaction = null;
        try (SessionFactory sessionFactory = Util.getSessionFactory();
        Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }
}
