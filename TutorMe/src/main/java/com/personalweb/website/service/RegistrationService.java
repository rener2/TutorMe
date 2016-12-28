package com.personalweb.website.service;

import com.personalweb.website.dao.NewUserDAO;
import com.personalweb.website.dao.UserDAO;
import com.personalweb.website.form.PageUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;


import javax.servlet.http.HttpServletRequest;


@Service
public class RegistrationService {

    @Autowired
    NewUserDAO newUserDao;

    @Autowired
    UserDAO userDAO;

    public String addUser(PageUser user) throws IOException {
        user = setDefaultPic(user);
        String error = newUserDao.addUser(user);
        sendConfirmationEmail(user);
        return error;
    }

    public String addFbUser(PageUser user) {
        return newUserDao.addFbUser(user);
    }

    public boolean checkUser (PageUser user) {
        //todo
        return true;
    }

    private PageUser setDefaultPic(PageUser user) throws IOException{
      //  ApplicationContext appContext = new ClassPathXmlApplicationContext(new String[] {"applicationContext.xml"});
      //  Resource resource = appContext.getResource("classpath:static/images/defaultProfilePic.png");
      //  byte [] data = Files.readAllBytes(Paths.get(resource.getFile().getPath()));
      //  user.setProfilePic(data);// todo fix sellele, sest kui tavaliselt reggada siis tuleb error
        return user;
    }

    private boolean sendConfirmationEmail(PageUser user) {
        Long userId = userDAO.getUser(user.getEmail()).getUserID();
        String link = "tutorme.com/confirm?id="+String.valueOf(userId);
        //// TODO
        return true;
    }
}
