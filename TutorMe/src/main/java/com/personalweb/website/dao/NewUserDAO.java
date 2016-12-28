package com.personalweb.website.dao;


import com.personalweb.website.form.PageUser;

public interface NewUserDAO {

    String addUser(PageUser user);
    String addFbUser(PageUser user);
}
