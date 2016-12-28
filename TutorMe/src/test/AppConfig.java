package com.personalweb.website;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;


@Configuration
@EnableWebMvc
@ComponentScan(basePackages ={"com.personalweb.website"})
public class AppConfig extends WebMvcConfigurerAdapter {
/**
    @Bean
    public ServletContextTemplateResolver getTemplateResolver() {
        //  properties.setPrefix("classpath:/webapp/WEB-INF/templates/");
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
        templateResolver.setPrefix("classpath:/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine getTemplateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        ServletContextTemplateResolver resolver = getTemplateResolver();
        templateEngine.setTemplateResolver(resolver);
        return templateEngine;
    }

    @Bean
    public ThymeleafViewResolver getViewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(getTemplateEngine());
        viewResolver.setOrder(1);
        return viewResolver;
    }
*/
    //end Thymeleaf specific configuration
    @Bean(name ="messageSource")
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:/error");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }


}




