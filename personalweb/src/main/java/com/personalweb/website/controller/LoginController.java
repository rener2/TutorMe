package com.personalweb.website.controller;

import com.personalweb.website.form.PageUser;
import com.personalweb.website.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping(value="/Login")
public class LoginController {

    @Autowired
    LoginService loginService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("user", new PageUser());
        return "LoginPage";
    }

    @RequestMapping(method = RequestMethod.POST, params = "registerButton")
    public String register(Model model) {
        model.addAttribute("user", new PageUser());
        return "RegistrationPage";
    }

    @RequestMapping(method = RequestMethod.POST, params = "loginButton")
    public String login(@Valid PageUser user, BindingResult bindingResult, Model model) {
        PageUser pageUser = loginService.getUser(user);
        if (pageUser != null) {
            model.addAttribute("user", pageUser);
            return "LoginSuccess";
        } else {
            model.addAttribute("errorText", "email or password incorrect");
            model.addAttribute("user", user);
            return "LoginPage";
        }
    }

}
