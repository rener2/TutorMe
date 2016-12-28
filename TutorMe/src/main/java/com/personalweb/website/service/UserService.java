package com.personalweb.website.service;

import com.personalweb.website.dao.UserDAO;
import com.personalweb.website.form.InboxMessage;
import com.personalweb.website.form.PageUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserDAO userDao;

    public PageUser getUser(Long userId) {
        return userDao.getUser(userId);
    }

    public PageUser getUser(String email) {
        return userDao.getUser(email);
    }

    public void setUserInfo(PageUser user, Long userId) {
        userDao.setUserInfo(user, userId);
    }

    public void setProfilePic(byte [] pic, Long userId) {
        userDao.uploadProfilePic(pic, userId);
    }

    public List<PageUser> findUsersByName(String keyword) {
        return userDao.findUsersByName(keyword);
    }

    public boolean userExists(String email) {
       return userDao.userExists(email);
    }

    public List<InboxMessage> getUserMessages(Long userId) {
        return userDao.getUserMessages(userId);
    }

    public void sendMessage(InboxMessage message) {
        userDao.sendMessage(message);
    }

    public InboxMessage getMessage(Long messageId) {
        return userDao.getMessage(messageId);
    }
}
