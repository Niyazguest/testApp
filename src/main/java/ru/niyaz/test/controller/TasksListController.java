package ru.niyaz.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.niyaz.test.dao.PriorityDao;
import ru.niyaz.test.dao.TypeDao;
import ru.niyaz.test.entity.Tasks;
import ru.niyaz.test.security.UserDetailsImpl;
import ru.niyaz.test.serivce.TasksService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by user on 14.09.15.
 */

@Controller
public class TasksListController {

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private TasksService tasksService;

    @Autowired
    private TypeDao typeDao;

    @Autowired
    private PriorityDao priorityDao;

    @RequestMapping(value = "/myTasks", method = RequestMethod.GET)
    public void tasksPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            UserDetailsImpl user = null;
            try {
                user = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
                if (user == null)
                    servletContext.getRequestDispatcher("/login?error=true").forward(request, response);
            } catch (ClassCastException ex) {
                servletContext.getRequestDispatcher("/login?error=true").forward(request, response);
            }

            List<Tasks> tasksList = tasksService.loadTasksByUserName(user.getUsername());
            servletContext.setAttribute("userName", user.getName());
            servletContext.setAttribute("tasks", tasksList);
            servletContext.setAttribute("types", typeDao.getTypes());
            servletContext.setAttribute("priorities", priorityDao.getPriorities());
            servletContext.getRequestDispatcher("/tasks.jsp").forward(request, response);

        } catch (ServletException ex) {

        } catch (IOException ex) {

        }
    }
}
