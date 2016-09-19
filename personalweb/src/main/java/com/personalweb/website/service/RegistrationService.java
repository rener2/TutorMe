package com.personalweb.website.service;

import com.personalweb.website.dao.NewUserDAO;
import com.personalweb.website.form.PageUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    @Autowired
    NewUserDAO newUserDao;

    public String addUser(PageUser user) {
        return newUserDao.addUser(user);
    }

    public boolean checkUser (PageUser user) {
        return true;
    }
}
