package ru.niyaz.test.dao;

import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.SessionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.niyaz.test.entity.User;
import ru.niyaz.test.util.HibernateUtil;

/**
 * Created by user on 04.09.15.
 */

@Repository("userDao")
public class UserDao {

    @Autowired
    private SessionFactory sessionFactory;

 //   @Transactional(propagation = Propagation.MANDATORY)
    public Long saveUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(user);
        return user.getUserId();
    }

    public boolean isExist(String login) {
        boolean exist = false;
        try {
            Session session = sessionFactory.openSession();
            Criteria userCriteria = session.createCriteria(User.class);
            userCriteria.add(Restrictions.eq("login", login));
            if (userCriteria.uniqueResult() != null)
                exist = true;
            else
                exist = false;
            session.close();
        } catch (HibernateException ex) {
            return false;
        }
        return exist;
    }

    public User getUser(String login) {
        User user = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Criteria userCriteria = session.createCriteria(User.class);
            userCriteria.add(Restrictions.eq("login", login));
            user = (User) userCriteria.uniqueResult();
        } catch (HibernateException ex) {
            return null;
        } finally {
            if (session != null)
                session.close();
        }
        return user;
    }

}