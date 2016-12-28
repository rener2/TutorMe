package com.personalweb.website.controller;

import com.personalweb.website.form.PageUser;
import com.personalweb.website.service.AdService;
import com.personalweb.website.service.InfoService;
import com.personalweb.website.service.LoginService;
import com.personalweb.website.service.UserService;
import com.personalweb.website.utils.SessionHelper;
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
@RequestMapping(value="/")
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    InfoService service;

    @Autowired
    AdService adService;

    @Autowired
    UserService userService;


    @RequestMapping(method = RequestMethod.GET)
    public String forgot(Model model) {
        model.addAttribute("user", new PageUser());
        return "index";
    }
    @RequestMapping(value="/forgot", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("user", new PageUser());
        return "forgot-password";
    }

    @RequestMapping(value="/signup" ,method = RequestMethod.POST, params = "registerButton")
    public String register(Model model) {
        model.addAttribute("user", new PageUser());
        return "index";
    }

    @RequestMapping(value="/logout" ,method = RequestMethod.GET)
    public String register(Model model, HttpServletRequest request ) {
        request.getSession().removeAttribute("userId");
        return "index";
    }

    @RequestMapping(value="/forgot" ,method = RequestMethod.POST, params = "forgotPassword")
    public String userRecovery(Model model) {
        //TODO
        return "index";
    }

    @RequestMapping(value="/main" ,method = RequestMethod.POST)
    public String login(HttpServletRequest request, @Valid PageUser user, BindingResult bindingResult, Model model) throws IOException {
        PageUser pageUser = userService.getUser(user.getEmail());
        model.addAttribute("popularAds", adService.getPopularAds());
        request.getSession().setAttribute( "userId", pageUser.getUserID());

        if (pageUser.getEmail() != null) {
            model.addAttribute("user", pageUser);
            return "FrontPage";
        } else {
            model.addAttribute("errorText", "email or password incorrect");
            model.addAttribute("user", user);
            return "index";
        }
    }



}
