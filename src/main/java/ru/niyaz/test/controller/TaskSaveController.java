package ru.niyaz.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.niyaz.test.pojo.TaskObject;
import ru.niyaz.test.serivce.TasksService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by user on 09.09.15.
 */

@Controller
public class TaskSaveController {

    @Autowired
    private TasksService tasksService;

    @RequestMapping(value = "/saveTask", method = RequestMethod.POST)
    public void saveTask(HttpServletRequest request, HttpServletResponse response,
                         @RequestBody(required = true) TaskObject taskObject) {
        Long taskId = tasksService.saveTask(taskObject);
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

}
