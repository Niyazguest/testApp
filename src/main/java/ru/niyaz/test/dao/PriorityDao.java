package ru.niyaz.test.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.niyaz.test.entity.Priority;
import ru.niyaz.test.entity.Type;
import ru.niyaz.test.util.HibernateUtil;

import java.util.List;

/**
 * Created by user on 14.09.15.
 */

@Repository
public class PriorityDao {

    @Autowired
    private SessionFactory sessionFactory;

    public List<Priority> getPriorities() {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Priority.class);
        List<Priority> priorities = criteria.list();
        session.close();
        return priorities;
    }

    public Priority getPriorityById(Long priorityId) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Priority.class);
        criteria.add(Restrictions.eq("priorityId", priorityId));
        Priority priority = (Priority) criteria.uniqueResult();
        session.close();
        return priority;
    }
}
