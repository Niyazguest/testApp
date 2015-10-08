package ru.niyaz.test.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.niyaz.test.entity.Type;
import ru.niyaz.test.util.HibernateUtil;

import java.util.List;

/**
 * Created by user on 14.09.15.
 */

@Repository
public class TypeDao {

    @Autowired
    private SessionFactory sessionFactory;

    public List<Type> getTypes() {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Type.class);
        List<Type> types = criteria.list();
        session.close();
        return types;
    }

    public Type getTypeById(Long typeId) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Type.class);
        criteria.add(Restrictions.eq("typeId", typeId));
        Type type = (Type) criteria.uniqueResult();
        session.close();
        return type;
    }
}
