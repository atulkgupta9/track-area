package com.apogee.trackarea.controller;


import com.apogee.trackarea.db.pojo.UserPojo;
import com.apogee.trackarea.helpers.util.SecurityUtil;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/common/")
public class CommonController {

    @GetMapping("user-details")
    public UserPojo getLoggedInUserDetails(){
        return SecurityUtil.currentUser();
    }



}
