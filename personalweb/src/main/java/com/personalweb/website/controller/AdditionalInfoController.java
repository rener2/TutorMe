package com.personalweb.website.controller;

import com.personalweb.website.form.PageUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;


@Controller
@RequestMapping(value="/Info")
public class AdditionalInfoController {

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        return "UserInformation";
    }

    @RequestMapping(method = RequestMethod.POST, params="saveButton")
    public String save(@Valid PageUser user, BindingResult bindingResult, Model model) {
        //model.addAttribute("user", new PageUser());
        return "UserInformation";
    }

    @RequestMapping(method = RequestMethod.POST, params="backButton")
    public String back(Model model) {
        //model.addAttribute("user", new PageUser());
        return "LoginSuccess";
    }

}
