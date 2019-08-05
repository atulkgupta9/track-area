package com.apogee.trackarea;

import com.apogee.trackarea.db.dao.UserDao;
import com.apogee.trackarea.db.pojo.UserPojo;
import com.apogee.trackarea.exceptions.ApiException;
import com.apogee.trackarea.helpers.constant.UserType;
import com.apogee.trackarea.helpers.util.StartUpScript;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

public class UserDaoTest extends TrackAreaApplicationTests {

    @Autowired
    private StartUpScript startUpScript;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testDao() throws IOException, ApiException {
        startUpScript.createUsers();

        List<UserPojo> ans1, ans2, ans3, ans4, ans5;
        ans1 = userDao.getByForm(UserType.USER, null, null);

        ans2 = userDao.getByForm(UserType.USER, "6", null);

        ans3 = userDao.getByForm(UserType.USER, null, "U");

        ans4 = userDao.getByForm(UserType.USER, "A2658", "U");

        ans5 = userDao.getByForm(UserType.USER, null, "UA001");

        System.out.println(objectMapper.writeValueAsString(userDao.findAll()));
        System.out.println(objectMapper.writeValueAsString(ans1));
        System.out.println(objectMapper.writeValueAsString(ans2));
        System.out.println(objectMapper.writeValueAsString(ans3));
        System.out.println(objectMapper.writeValueAsString(ans4));
        System.out.println(objectMapper.writeValueAsString(ans5));


    }

}
