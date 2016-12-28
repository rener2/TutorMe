package com.personalweb.website.controller;

import com.personalweb.website.service.AdService;
import com.personalweb.website.service.UserService;
import com.personalweb.website.utils.SessionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@Controller
@RequestMapping(value="/main")
public class FrontPageController {

    @Autowired
    AdService adService;

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String myUser(HttpServletRequest request, Model model) throws IOException{
        model.addAttribute("popularAds", adService.getPopularAds());
        model.addAttribute("user", userService.getUser(SessionHelper.getUserId(request)));
        return "FrontPage";
    }

}
