package com.personalweb.website.controller;


import com.personalweb.website.form.PageUser;
import com.personalweb.website.service.AdService;
import com.personalweb.website.service.LoginService;
import com.personalweb.website.service.RegistrationService;
import com.personalweb.website.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping(value = "/login/facebook")
public class FacebookController {

    @Autowired
    RegistrationService registrationService;

    @Autowired
    UserService userService;

    @Autowired
    AdService adService;

    @Autowired
    LoginService loginService;

    @Autowired
    private ConnectionRepository connectionRepository;

    @GetMapping
    public String helloFacebook(HttpServletRequest request, Model model) {
        PageUser dbUser = null;
        if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
            return "redirect:/signup";
        }
        PageUser pageUser = loginService.getFbUser(connectionRepository.findPrimaryConnection(Facebook.class).getApi());

        if(userService.userExists(pageUser.getEmail())) {
            dbUser = userService.getUser(pageUser.getEmail());
        }
        if (!userService.userExists(pageUser.getEmail())) {
            registrationService.addFbUser(pageUser);
            dbUser = userService.getUser(pageUser.getEmail());
        }
        request.getSession().setAttribute("userId", dbUser.getUserID());
        model.addAttribute("user", dbUser);
        model.addAttribute("popularAds", adService.getPopularAds());
        connectionRepository.removeConnections("facebook");
        return "FrontPage";
    }
}


