package ru.niyaz.test.serivce;

import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.niyaz.test.dao.PriorityDao;
import ru.niyaz.test.dao.TasksDao;
import ru.niyaz.test.dao.TypeDao;
import ru.niyaz.test.dao.UserDao;
import ru.niyaz.test.entity.Priority;
import ru.niyaz.test.entity.Tasks;
import ru.niyaz.test.entity.Type;
import ru.niyaz.test.entity.User;
import ru.niyaz.test.pojo.TaskObject;
import ru.niyaz.test.security.UserDetailsImpl;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 07.09.15.
 */

@Service
public class TasksService {

    @Autowired
    private TasksDao tasksDao;

    @Autowired
    private SessionFactory sessionFactory;

    public List<TaskObject> loadTasksByUserName(String userName) {
        List<Tasks> tasksList = tasksDao.getTasksByUserName(userName);
        List<TaskObject> taskObjects = new ArrayList<TaskObject>();
        DateFormat dateFormat=new SimpleDateFormat("dd.MM.yyyy");
        for(Tasks task : tasksList) {
           taskObjects.add(new TaskObject(task.getTaskId(),task.getName(), task.getDefinition(),dateFormat.format(task.getTaskDate()),task.getPriority().getPriorityId(),task.getType().getTypeId()));
        }
        return taskObjects;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Long saveTask(TaskObject taskObject) {
        Session session = sessionFactory.getCurrentSession();
        User user = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCurrentUser();
        Tasks task = new Tasks();
        if (taskObject.getId() != null) {
            task = (Tasks) session.load(Tasks.class, new Long(taskObject.getId()));
            task.setName(taskObject.getName());
            task.setDefinition(taskObject.getDefinition());
            task.setTaskDate(parseDate(taskObject.getTaskDate()));
            task.setPriority((Priority) session.load(Priority.class, new Long(taskObject.getPriorityId())));
            task.setType((Type) session.load(Type.class, new Long(taskObject.getTypeId())));
            return task.getTaskId();
        }
        task.setName(taskObject.getName());
        task.setDefinition(taskObject.getDefinition());
        task.setTaskDate(parseDate(taskObject.getTaskDate()));
        task.setPriority((Priority) session.load(Priority.class, new Long(taskObject.getPriorityId())));
        task.setType((Type) session.load(Type.class, new Long(taskObject.getTypeId())));
        task.setUser(user);
        session.saveOrUpdate(task);
        return task.getTaskId();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean deleteTask(Long taskId) {
        return tasksDao.deleteTask(taskId);
    }

    public Date parseDate(String dateString) {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date = null;
        try {
            date = new Date(dateFormat.parse(dateString).getTime());
        } catch (ParseException ex) {

        }
        return date;
    }
}
