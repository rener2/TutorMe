package com.personalweb.website.controller;

import com.personalweb.website.dao.AdDAO;
import com.personalweb.website.dao.UserDAO;
import com.personalweb.website.form.InboxMessage;
import com.personalweb.website.form.PageUser;
import com.personalweb.website.service.AdService;
import com.personalweb.website.service.UserService;
import com.personalweb.website.utils.SessionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;


@Controller
@RequestMapping
public class SearchController {

    @Autowired
    UserService userService;

    @Autowired
    AdService adService;

    @RequestMapping(value="/search/{keyword}", method = RequestMethod.GET)
    public String myUser(@PathVariable(value = "keyword") String keyword, HttpServletRequest request, Model model) throws IOException {
        model.addAttribute("user", userService.getUser(SessionHelper.getUserId(request)));
        model.addAttribute("ads", adService.findAdsBySubject(keyword));
        model.addAttribute("users", userService.findUsersByName(keyword));
        return "SearchResults";
    }
    @RequestMapping(value="/other/{userID}", method = RequestMethod.GET)
    public String showUser(@PathVariable(value = "userID") Long searchUserId, HttpServletRequest request, Model model) throws IOException {
        model.addAttribute("user", userService.getUser(SessionHelper.getUserId(request)));
        model.addAttribute("otherUser", userService.getUser(searchUserId));
        return "OtherUserProfile";
    }
    @RequestMapping(value="/other/{userID}/message", method = RequestMethod.GET)
    public String sendMessage(@PathVariable(value = "userID") Long receiverId,
                              HttpServletRequest request, Model model) throws IOException {
        InboxMessage newMessage = new InboxMessage();
        model.addAttribute("newMessage", newMessage);
        model.addAttribute("user", userService.getUser(SessionHelper.getUserId(request)));
        model.addAttribute("otherUser", userService.getUser(receiverId));
        return "letter-send";
    }
    @RequestMapping(value="/other/{userID}/message", method = RequestMethod.POST)
    public String sendMessagePost(@PathVariable(value = "userID") Long receiverId,
                                  HttpServletRequest request, @Valid InboxMessage message,
                                  BindingResult bindingResult, Model model) throws IOException {
        message.setFromId(SessionHelper.getUserId(request));
        message.setToId(receiverId);
        userService.sendMessage(message);
        model.addAttribute("user", userService.getUser(SessionHelper.getUserId(request)));
        model.addAttribute("otherUser", userService.getUser(receiverId));
        return "OtherUserProfile";
    }
    @RequestMapping(value="/other/{userId}/ads", method = RequestMethod.GET)
    public String sendMessagePost(@PathVariable(value = "userId") Long otherUserId,
                                  HttpServletRequest request, Model model) throws IOException {
        model.addAttribute("userAds", adService.getUserAds(otherUserId));
        model.addAttribute("user", userService.getUser(SessionHelper.getUserId(request)));
        model.addAttribute("otherUser", userService.getUser(otherUserId));
        return "otherUserAds";
    }
}
