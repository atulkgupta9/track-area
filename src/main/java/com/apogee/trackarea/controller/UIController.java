package com.apogee.trackarea.controller;

import com.apogee.trackarea.exceptions.ApiException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UIController {



    @RequestMapping(value = "/ui/index", method = RequestMethod.GET)
    public String greeting() {
        return "index.html";
    }

    @RequestMapping(value = "/ui/signin", method = RequestMethod.GET)
    public String signin() {
        return "user-signin.html";
    }

    @RequestMapping(value = "/ui/dashboard", method = RequestMethod.GET)
    public String dashboard() {
        return "dashboard.html";
    }


    @RequestMapping(value = "/ui/admin/user", method = RequestMethod.GET)
    public String  adminUser() throws ApiException {
        return "user-signup.html";
    }
    @RequestMapping(value = "/ui/testdb", method = RequestMethod.GET)
    public String  testDb() throws ApiException {
        return "testdb.html";
    }

    @RequestMapping(value = "/ui/user/dashboard", method = RequestMethod.GET)
    public String userDashboard() {
        return "user-dashboard.html";
    }


}
