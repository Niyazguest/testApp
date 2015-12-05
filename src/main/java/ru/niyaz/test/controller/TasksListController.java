package ru.niyaz.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import ru.niyaz.test.dao.PriorityDao;
import ru.niyaz.test.dao.TypeDao;
import ru.niyaz.test.entity.Tasks;
import ru.niyaz.test.pojo.TaskObject;
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

            List<TaskObject> tasksList = tasksService.loadTasksByUserName(user.getUsername());
            response.setContentType("text/html;charset=UTF-8");
            ServletContextTemplateResolver servletContextTemplateResolver = new ServletContextTemplateResolver();
            servletContextTemplateResolver.setTemplateMode("HTML5");
            servletContextTemplateResolver.setPrefix("/WEB-INF/jsp/");
            servletContextTemplateResolver.setSuffix(".html");
            servletContextTemplateResolver.setCharacterEncoding("UTF-8");
            TemplateEngine templateEngine = new TemplateEngine();
            templateEngine.setTemplateResolver(servletContextTemplateResolver);
            WebContext webContext = new WebContext(request, response, servletContext);
            webContext.setVariable("userName", user.getName());
            webContext.setVariable("tasks", tasksList);
            webContext.setVariable("types", typeDao.getTypes());
            webContext.setVariable("priorities", priorityDao.getPriorities());
            webContext.setVariable("contextPath", request.getContextPath());
            templateEngine.process("tasks", webContext, response.getWriter());
        } catch (Exception ex) {
            return;
        }
    }
}
