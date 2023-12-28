package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

   SessionFactory sessionFactory = Util.getSessionFactory();
    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        String sql = "CREATE TABLE IF NOT EXISTS users (\n" +
                "id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                "name VARCHAR(100) NOT NULL,\n" +
                "lastname VARCHAR(100) NOT NULL,\n" +
                "age TINYINT NOT NULL)";
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
            transaction.commit();
        } catch (Exception e){
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        String sql = "DROP TABLE IF EXISTS users";
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
            transaction.commit();
        } catch (Exception e){
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
            System.out.println("User c именем " + name + " добавлен в базу данных.");
        } catch(Exception e){
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
        } catch(Exception e){
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        List<User> users = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            users = session.createQuery("FROM User", User.class).list();
            transaction.commit();
        } catch(Exception e){
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            transaction.commit();
        } catch(Exception e){
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
