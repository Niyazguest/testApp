package ru.niyaz.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.niyaz.test.dao.PriorityDao;
import ru.niyaz.test.dao.TypeDao;
import ru.niyaz.test.entity.Tasks;
import ru.niyaz.test.pojo.TaskObject;
import ru.niyaz.test.serivce.TasksService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by user on 09.09.15.
 */

@Controller
public class TaskSaveController {

    @Autowired
    private TasksService tasksService;

    @Autowired
    private TypeDao typeDao;

    @Autowired
    private PriorityDao priorityDao;

    @RequestMapping(value = "/saveTask", method = RequestMethod.POST)
    public void saveTask(HttpServletRequest request, HttpServletResponse response,
                         @RequestBody(required = true) TaskObject taskObject) {
        Tasks task = new Tasks();
        task.setName(taskObject.getName());
        task.setDefinition(taskObject.getDefinition());
        task.setTaskDate(parseDate(taskObject.getTaskDate()));
        task.setType(typeDao.getTypeById(new Long(taskObject.getTypeId())));
        task.setPriority(priorityDao.getPriorityById(new Long(taskObject.getPriorityId())));
        Long taskId = tasksService.saveTask(task);
        response.addHeader("taskId", taskId.toString());
    }

    @RequestMapping(value = "/deleteTask", method = RequestMethod.POST)
    public void deleteTask(HttpServletRequest request, HttpServletResponse response,
                           @RequestParam(value = "taskId", required = true) Long taskId) {
        if (tasksService.deleteTask(taskId))
            response.addHeader("delete", "yes");
        else
            response.addHeader("delete", "no");
    }

    public Date parseDate(String dateString) {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
        Date date = null;
        try {
            date = new Date(dateFormat.parse(dateString).getTime());
        } catch (ParseException ex) {

        }
        return date;
    }
}
