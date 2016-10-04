package com.personalweb.website.service;

import com.personalweb.website.dao.CheckUserDAO;
import com.personalweb.website.form.PageUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    CheckUserDAO checkUserDao;

    public PageUser getUser(PageUser user) {
        return checkUserDao.getUser(user);
    }
}
