package com.personalweb.website.controller;

import com.personalweb.website.form.Advertisement;
import com.personalweb.website.form.PageUser;
import com.personalweb.website.service.AdService;
import com.personalweb.website.service.UserService;
import com.personalweb.website.utils.SessionHelper;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;


@Controller
@RequestMapping(value = "/user")
public class UserInfoController {

    @Autowired
    UserService userService;

    @Autowired
    AdService adService;

    @RequestMapping(method = RequestMethod.GET)
    public String myUser(HttpServletRequest request, Model model) throws IOException{
        PageUser user = userService.getUser(SessionHelper.getUserId(request));
        model.addAttribute("user", user);
        return "myUser";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String getfile(@RequestParam("uploadedImage") MultipartFile file, HttpServletRequest request, Model model ) throws IOException, ServletException{
        PageUser user = userService.getUser(SessionHelper.getUserId(request));
        //todo kontrollida enne pildi vahetamist ja andmebaasi laadimist, et tegu oleks pildiga
        userService.setProfilePic(file.getBytes(), user.getUserID());
        user.setProfilePic(file.getBytes());
        model.addAttribute("user", user);
        return "myUser";
    }


    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String advertisement(HttpServletRequest request, Model model) {
        model.addAttribute("user", userService.getUser(SessionHelper.getUserId(request)));
        model.addAttribute("ad", new Advertisement());
        return "advertisement";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addAdvertisement(HttpServletRequest request, Advertisement ad, Model model) {
        adService.addAdvertisement(ad, SessionHelper.getUserId(request));
        model.addAttribute("user", userService.getUser(SessionHelper.getUserId(request)));
        model.addAttribute("ad", new Advertisement());
        return "advertisement";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(HttpServletRequest request, Model model) {
        model.addAttribute("user", userService.getUser(SessionHelper.getUserId(request)));
        return "editInfo";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String postEdit(HttpServletRequest request, PageUser user, Model model) {
        userService.setUserInfo(user, SessionHelper.getUserId(request));
        model.addAttribute("user", userService.getUser(SessionHelper.getUserId(request)));
        return "editInfo";
    }

    @RequestMapping(value = "/myAds", method = RequestMethod.GET)
    public String showAds(HttpServletRequest request, PageUser user, Model model) {
        model.addAttribute("userAds", adService.getUserAds(SessionHelper.getUserId(request)));
        model.addAttribute("user", userService.getUser(SessionHelper.getUserId(request)));
        return "userAds";
    }

    @RequestMapping(value = "/inbox", method = RequestMethod.GET)
    public String inbox(HttpServletRequest request, PageUser user, Model model) {
        model.addAttribute("inboxMessages", userService.getUserMessages(SessionHelper.getUserId(request)));
        //model.addAttribute("userAds", adService.getUserAds(SessionHelper.getUserId(request)));
        model.addAttribute("user", userService.getUser(SessionHelper.getUserId(request)));
        return "inbox";
    }

    @RequestMapping(value = "/inbox/{messageId}", method = RequestMethod.GET)
    public String inbox(@PathVariable(value="messageId") Long messageId,  HttpServletRequest request, Model model) {
        System.out.println(messageId);
        model.addAttribute("currentMessage", userService.getMessage(messageId));
        model.addAttribute("user", userService.getUser(SessionHelper.getUserId(request)));
        return "letter-recieve";
    }



}
