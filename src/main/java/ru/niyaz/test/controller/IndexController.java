package ru.niyaz.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
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
 * Created by user on 05.09.15.
 */

@Controller
public class IndexController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView loginPage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView model = new ModelAndView("login");
        model.addObject("resource_root", request.getContextPath() + "/resources");
        return model;
    }

}
