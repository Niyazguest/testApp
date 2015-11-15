package ru.niyaz.test.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.niyaz.test.entity.Tasks;
import ru.niyaz.test.entity.User;
import ru.niyaz.test.security.UserDetailsImpl;
import ru.niyaz.test.util.HibernateUtil;

import org.hibernate.Query;

import java.util.List;

/**
 * Created by user on 07.09.15.
 */

@Repository
public class TasksDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private UserDao userDao;

    public List<Tasks> getTasksByUserName(String userName) {
        List<Tasks> tasksList = null;
        try {
            User user = userDao.getUser(userName);
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.refresh(user);
            tasksList = user.getTasks();
            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.getMessage();
        }
        return tasksList;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void saveTask(Tasks task) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(task);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public boolean deleteTask(Long taskId) {
        try {
            User currentUser = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCurrentUser();
            Session session = sessionFactory.getCurrentSession();
            Tasks task = new Tasks();
            task.setTaskId(taskId);
            session.delete(task);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}
