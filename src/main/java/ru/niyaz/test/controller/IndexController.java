package ru.niyaz.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.thymeleaf.context.WebContext;
import ru.niyaz.test.util.ThymeleafTemplateUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by user on 05.09.15.
 */

@Controller
public class IndexController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public void loginPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("text/html;charset=UTF-8");
            WebContext webContext = new WebContext(request, response, request.getSession().getServletContext());
            ThymeleafTemplateUtil.getTemplateEngine().process("login", webContext, response.getWriter());
        } catch (Exception ex) {
            return;
        }
    }

}
