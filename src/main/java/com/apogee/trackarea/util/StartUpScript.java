package com.apogee.trackarea.util;


import com.apogee.trackarea.api.CustomUserDetailsService;
import com.apogee.trackarea.constant.Authorities;
import com.apogee.trackarea.constant.UserType;
import com.apogee.trackarea.controller.LoginController;
import com.apogee.trackarea.controller.PointController;
import com.apogee.trackarea.exceptions.ApiException;
import com.apogee.trackarea.pojo.CustomUserDetails;
import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class StartUpScript {

    @Value("${app.superadmins}")
    private String admins;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserDetailsService userApi;


    @Autowired
    private LoginController loginController;

    @Autowired
    private PointController pointController;


    @EventListener(ApplicationReadyEvent.class)
    public void createSomeVendors() throws ApiException, IOException {
        if(!userApi.getAllEntities().isEmpty()){
            return;
        }
        List<String> superadmins = Collections.arrayToList(admins.split(","));
        for(String admin : superadmins){
            CustomUserDetails newUser = new CustomUserDetails();
            newUser.setUsername(admin);
            newUser.setPassword(passwordEncoder.encode("password@123"));
            newUser.setAuthorities(Authorities.ADMIN);
            newUser.setUserType(UserType.ADMIN);
            newUser.setPwdplain("password@123");
            userApi.saveEntity(newUser);
        }

    }

}
