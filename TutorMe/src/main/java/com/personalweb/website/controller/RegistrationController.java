package com.personalweb.website.controller;

import com.personalweb.website.service.RegistrationService;
import com.personalweb.website.form.PageUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.thymeleaf.TemplateRepository;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.AbstractTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.TemplateResolver;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import javax.validation.Valid;

@Controller
@RequestMapping(value="/Register")
public class RegistrationController {

    @Autowired
    RegistrationService registrationService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("user", new PageUser());
        return "RegistrationPage";
    }

    @RequestMapping(method = RequestMethod.POST, params="submitButton")
    public String submitUser(@Valid PageUser user, BindingResult bindingResult, Model model) {
        if(!bindingResult.hasErrors() && registrationService.checkUser(user)) {
            registrationService.addUser(user);
            //kui success siis registrationsuccess page else registrationfail page
            return "LoginPage";
        } else {
            System.out.println("XD" + bindingResult.getFieldErrors());
            model.addAttribute("user", user);
            return "RegistrationPage";
        }
    }

    @RequestMapping(method = RequestMethod.POST, params="loginButton")
    public String login(Model model) {
        model.addAttribute("user", new PageUser());
        return "LoginPage";
    }
}
