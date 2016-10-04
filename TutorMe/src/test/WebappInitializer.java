package com.personalweb.website;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

public class WebappInitializer implements WebApplicationInitializer {


    public void onStartup(ServletContext servletContext) throws ServletException {

        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(AppConfig.class);
        ctx.setServletContext(servletContext);
        Dynamic dynamic = servletContext.addServlet("personalweb", new DispatcherServlet(ctx));
        dynamic.addMapping("/");
        dynamic.setLoadOnStartup(1);
    }

}