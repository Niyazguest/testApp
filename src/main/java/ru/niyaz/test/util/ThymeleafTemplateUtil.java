package ru.niyaz.test.util;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

/**
 * Created by user on 05.12.15.
 */
public class ThymeleafTemplateUtil {

    private static TemplateEngine templateEngine = createTemplateEngine();

    private static TemplateEngine createTemplateEngine() {
        ServletContextTemplateResolver servletContextTemplateResolver = new ServletContextTemplateResolver();
        servletContextTemplateResolver.setTemplateMode("HTML5");
        servletContextTemplateResolver.setPrefix("/WEB-INF/templates/");
        servletContextTemplateResolver.setSuffix(".html");
        servletContextTemplateResolver.setCharacterEncoding("UTF-8");
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(servletContextTemplateResolver);
        return templateEngine;
    }

    public static TemplateEngine getTemplateEngine() {
        return templateEngine;
    }
}
