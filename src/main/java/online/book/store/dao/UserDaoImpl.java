package online.book.store.dao;

import online.book.store.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import java.util.regex.Pattern;

@Component
public class UserDaoImpl implements UserDao{

    private final SessionFactory sessionFactory =
            SessionFactorySingleton.getInitializationFactory();

    @Override
    public void saveUser(User user) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {
                if (session.getTransaction() != null) {
                    session.getTransaction().rollback();
                }
            }
        } finally {
            if (session != null) {
                session.close();
            }

        }
    }

    @Override
    public User getUserByLogin(String login) {
        Pattern email = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                Pattern.CASE_INSENSITIVE);

        String column = "USERNAME";

        if (email.matcher(login).find()) column = "EMAIL";

        User user = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            user = (User) session.createSQLQuery("SELECT * FROM USERS " +
                            "WHERE " + column + "=:login").addEntity(User.class).
                    setParameter("login", login).getSingleResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {
                if (session.getTransaction() != null) {
                    session.getTransaction().rollback();
                    if (e instanceof NoResultException) return null;
                }
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return user;
    }

    @Override
    public void updateUser(User user) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {
                if (session.getTransaction() != null) {
                    session.getTransaction().rollback();
                }
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

}
