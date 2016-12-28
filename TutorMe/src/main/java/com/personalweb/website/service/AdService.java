package com.personalweb.website.service;

import com.personalweb.website.dao.AdDAO;
import com.personalweb.website.form.Advertisement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AdService {

    @Autowired
    AdDAO adDAO;

    public void addAdvertisement(Advertisement ad, Long userId) {
        adDAO.addAd(ad, userId);
    }

    public List<Advertisement> getPopularAds() {
        return adDAO.getPopularAds();
    }

    public List<Advertisement> getUserAds(Long userId) {
        return adDAO.getUserAds(userId);
    }

    public List<Advertisement> findAdsBySubject(String keyword) {
        return adDAO.findAdsBySubject(keyword);
    }
}

