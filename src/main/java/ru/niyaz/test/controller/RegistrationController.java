package ru.niyaz.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.context.WebContext;
import ru.niyaz.test.security.UserDetailsImpl;
import ru.niyaz.test.serivce.RegistrationService;
import ru.niyaz.test.util.ThymeleafTemplateUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by user on 04.09.15.
 */

@Controller
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    @Qualifier("authManager")
    private AuthenticationManager authenticationManager;

    @RequestMapping(value = "registration", method = RequestMethod.POST)
    public void registration(HttpServletRequest request, HttpServletResponse response,
                             @RequestParam(value = "name", required = true) String name,
                             @RequestParam(value = "login", required = true) String login,
                             @RequestParam(value = "password", required = true) String password) {

        Long id = registrationService.userRegistration(name, login, password);
        if (id > 0) {
            try {
                SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password)));
                response.sendRedirect(request.getContextPath() + "/myTasks");
            } catch (IOException ex) {
                return;
            }
        }
        if (id == 0) {
            try {
                response.getOutputStream().print("This login already exist");
            } catch (IOException ex) {

            }
        }
    }

    @RequestMapping(value = "toRegistration", method = RequestMethod.GET)
    public void toRegistration(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("text/html;charset=UTF-8");
            WebContext webContext = new WebContext(request, response, request.getSession().getServletContext());
            ThymeleafTemplateUtil.getTemplateEngine().process("registration", webContext, response.getWriter());
        } catch (Exception ex) {
            return;
        }
    }

}
