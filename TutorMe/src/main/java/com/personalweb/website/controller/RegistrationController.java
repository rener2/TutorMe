package com.personalweb.website.controller;

import com.personalweb.website.form.RegisterUser;
import com.personalweb.website.service.AdService;
import com.personalweb.website.service.RegistrationService;
import com.personalweb.website.form.PageUser;
import com.personalweb.website.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping(value="/Register")
public class RegistrationController {

    @Autowired
    RegistrationService registrationService;

    @Autowired
    UserService userService;

    @Autowired
    AdService adService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("user", new RegisterUser());
        return "signup";
    }

    @RequestMapping(method = RequestMethod.POST, params="submitButton")
    public String submitUser(@Valid RegisterUser user, BindingResult bindingResult, Model model, HttpServletRequest request) throws IOException{
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            model.addAttribute("user", new RegisterUser());
            model.addAttribute("errorText", "Passwords must match!");
            System.out.println("Passwords must match!");
            return "signup";
        }
        else if(!bindingResult.hasErrors() && !userService.userExists(user.getEmail())) {
            registrationService.addUser(user);
            PageUser dbUser = userService.getUser(user.getEmail());
            request.getSession().setAttribute("userId", dbUser.getUserID());
            model.addAttribute("user", dbUser);
            model.addAttribute("popularAds", adService.getPopularAds());
            return "index";
        }
        System.out.println("User with this email already exists!");
        return "signup";
    }

    @RequestMapping(method = RequestMethod.POST, params="loginButton")
    public String login(Model model) {
        model.addAttribute("user", new PageUser());
        return "index";
    }

    @RequestMapping(value="facebook",method = RequestMethod.POST, params="loginButton")
    public String facebookRegister(Model model) {
        model.addAttribute("user", new PageUser());
        return "index";
    }


}
