package com.personalweb.website.service;

import com.personalweb.website.dao.CheckUserDAO;
import com.personalweb.website.form.PageUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Service;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Exchanger;

@Service
public class LoginService {

    @Autowired
    CheckUserDAO checkUserDao;

    public PageUser getUser(PageUser user) {
        return checkUserDao.getUser(user);
    }

    public PageUser getFbUser(Facebook facebook) {
        User profile = facebook.fetchObject("me", User.class, "id", "name", "email", "first_name", "last_name",
                "birthday", "education", "gender", "work");
        PageUser pageUser = new PageUser();
        pageUser.setFirstName(profile.getFirstName());
        pageUser.setLastName(profile.getLastName());
        pageUser.setEmail(profile.getEmail());
        if (profile.getBirthday() != null) {
            Date date = null;
            try {
                DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                date = formatter.parse(profile.getBirthday());
            } catch (Exception x) {
                System.out.println("Problems parsing date received from facebook: " + x.getMessage());
            }
            pageUser.setBirthdate(date);
        }
        pageUser.setProfilePic(facebook.userOperations().getUserProfileImage());
        return pageUser;
    }
}
