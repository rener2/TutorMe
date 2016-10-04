package com.personalweb.website.controller;

import com.personalweb.website.form.PageUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/LoginSuccess")
public class LoginSuccessController {

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        return "LoginSuccess";
    }

    @RequestMapping(method = RequestMethod.POST, params="additionalButton")
    public String login(Model model) {
        //model.addAttribute("user", new PageUser());
        return "UserInformation";
    }
}
