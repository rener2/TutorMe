package com.personalweb.website.service;

import com.personalweb.website.dao.InfoDAO;
import com.personalweb.website.dao.UpdateInfoDAO;
import com.personalweb.website.form.PageUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class InfoService {

    @Autowired
    UpdateInfoDAO updateInfoDAO;
    @Autowired
    InfoDAO infoDAO;

    public Integer getPeopleCount() {
        return infoDAO.getPeopleCount();
    }

    public void updateInfo(PageUser user) {
        updateInfoDAO.updateInfo(user);
    }

}
