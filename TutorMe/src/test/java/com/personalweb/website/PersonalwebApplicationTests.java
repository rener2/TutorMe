package com.personalweb.website;

import com.personalweb.website.config.H2ServletConfig;
import com.personalweb.website.controller.FacebookController;
import com.personalweb.website.dao.NewUserDAOImpl;
import com.personalweb.website.dao.UpdateInfoDAOImpl;
import com.personalweb.website.dao.UserDAO;
import com.personalweb.website.form.Advertisement;
import com.personalweb.website.form.PageUser;
import com.personalweb.website.service.*;
import com.personalweb.website.utils.Parser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PersonalwebApplication.class)
public class PersonalwebApplicationTests {

    @Autowired
    H2ServletConfig config;

    @Autowired
	InfoService infoService;
    @Autowired
    AdService adService;
    @Autowired
    LoginService loginService;
    @Autowired
    RegistrationService registrationService;
    @Autowired
    UserService userService;
    @Autowired
    UpdateInfoDAOImpl updateInfoDAO;
    @Autowired
    FacebookController facebookController;
    @Autowired
    UserDAO userDAO;

    @Autowired
    NewUserDAOImpl newUserDAO;

    PageUser admin;

    //adservice
    //addAdvertisement(Advertisement ad, Long userId) {
    //public List<Advertisement> getPopularAds() {
    //public List<Advertisement> getUserAds(Long userId)
    //public List<Advertisement> findAdsBySubject

    @Before
    public void setUp() {
        admin = new PageUser();
        admin.setEmail("admin");
        System.out.println(admin.getEmail());
        admin.setPassword("admin");
    }


    @Test
    public void appStarted() {
        //just to know that app works
    }

	@Test
    public void getPeopleCount() {
		assertNotEquals(0, infoService.getPeopleCount().intValue());
	}

	@Test
    public void adminLoginTest() {
        assertNotNull(loginService.getUser(admin));
    }

    @Test
    public void loginWhitNullUser() {
        assertNull(loginService.getUser(new PageUser()));
    }

    @Test
    public void loginWithWrongUser() {
        PageUser testUser = new PageUser();
        testUser.setEmail("test@gmail.com");
        testUser.setPassword("teeeeest");
        assertNull(loginService.getUser(testUser));
    }


    @Test
    public void getUserByWrongID() {
        assertNotNull(userService.getUser((long)2).getEmail());
    }

    @Test
    public void sendMessage() {
    }

    @Test(expected = org.springframework.jdbc.BadSqlGrammarException.class)
    public void updateInfo() {
        updateInfoDAO.updateInfo(admin);
    }

    @Test
    public void addUser() {
        PageUser pageUser = new PageUser();
        pageUser.setEmail("email");
        pageUser.setPassword("email");
        newUserDAO.addUser(new PageUser());

    }

    @Test
    public void getPopularAds() {
        assertNotEquals(0, adService.getPopularAds().size());
    }

    @Test
    public void getUserAds() {
        assertEquals(0, adService.getUserAds((long) 2).size());
    }

    @Test
    public void findAds() {
        assertEquals(0, adService.findAdsBySubject("asdasd").size());
    }

    @Test
    public void addAd() {
        adService.addAdvertisement(new Advertisement(), (long) 5464564);
    }

    @Test
    public void userExists() {
        assertTrue(userService.userExists("admin"));
    }

    @Test
    public void getMessages() {
        assertEquals(0, userService.getUserMessages(admin.getUserID()).size());
    }

    @Test
    public void findAdsBySubjectKeyword()  {
       assertNotEquals(0, adService.findAdsBySubject("maatika").size());
    }

    @Test
    public void findUsersBySubjectKeyword()  {
        assertNotEquals(0, userService.findUsersByName("rene").size());
    }


}
