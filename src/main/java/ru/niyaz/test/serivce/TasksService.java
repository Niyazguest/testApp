package ru.niyaz.test.serivce;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.niyaz.test.dao.TasksDao;
import ru.niyaz.test.dao.UserDao;
import ru.niyaz.test.entity.Tasks;
import ru.niyaz.test.entity.User;
import ru.niyaz.test.security.UserDetailsImpl;

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
    private UserDao userDao;

    @Autowired
    private SessionFactory sessionFactory;

    public List<Tasks> loadTasksByUserName(String userName) {
        return tasksDao.getTasksByUserName(userName);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Long saveTask(Tasks task) {
        String login = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userDao.getUser(login);
        List<Tasks> tasksList = new ArrayList<Tasks>();
        tasksList.add(task);
        user.setTasks(tasksList);
        task.setUser(user);
        userDao.saveUser(user);
        return task.getTaskId();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean deleteTask(Long taskId) {
        return tasksDao.deleteTask(taskId);
    }

}
