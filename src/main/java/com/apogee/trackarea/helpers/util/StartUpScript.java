package com.apogee.trackarea.helpers.util;


import com.apogee.trackarea.controller.AdminController;
import com.apogee.trackarea.controller.LoginController;
import com.apogee.trackarea.controller.SuperadminController;
import com.apogee.trackarea.controller.UserController;
import com.apogee.trackarea.db.pojo.UserPojo;
import com.apogee.trackarea.dtoapi.api.UserApi;
import com.apogee.trackarea.exceptions.ApiException;
import com.apogee.trackarea.helpers.constant.Authorities;
import com.apogee.trackarea.helpers.constant.UserType;
import com.apogee.trackarea.model.data.SingleUserDetails;
import com.apogee.trackarea.model.form.AdminForm;
import com.apogee.trackarea.model.form.DeviceForm;
import com.apogee.trackarea.model.form.LoginForm;
import com.apogee.trackarea.model.form.UserForm;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private String superadmins;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserApi userApi;

    @Autowired
    private AdminController adminController;

    @Autowired
    private UserController userController;

    @Autowired
    private SuperadminController superadminController;

    @Autowired
    private LoginController loginController;

    @Autowired
    private ObjectMapper mapper;


    @EventListener(ApplicationReadyEvent.class)
    public void createUsers() throws ApiException, IOException {
        //TODO remove this in real time
        if (!userApi.getAllEntities().isEmpty()) {
            return;
        }
        List<String> superadmins = Collections.arrayToList(this.superadmins.split(","));
        for (String admin : superadmins) {
            UserPojo newUser = new UserPojo();
            newUser.setUsername(admin);
            newUser.setPassword(passwordEncoder.encode("password@123"));
            newUser.setAuthorities(Authorities.SUPERADMIN);
            newUser.setUserType(UserType.SUPERADMIN);
            newUser.setPwdplain("password@123");
            newUser.setPhone("948504843");
            userApi.saveEntity(newUser);
        }

        AdminForm admin1 = Data.getAdminForm( "password", "981949404");
        AdminForm admin2 = Data.getAdminForm( "password", "439838344");

        SingleUserDetails adminDetail1 = superadminController.createAdmin(admin1);
        SingleUserDetails adminDetail2 = superadminController.createAdmin(admin2);

        UserForm user1 = Data.getUserForm("Ganesh Gaitonde", "password", "9712388888");
        UserForm user2 = Data.getUserForm("Ramadhir Singh", "password", "9712388880");

        SingleUserDetails userDetails1 = adminController.createUser(user1);
        SingleUserDetails userDetails2 = adminController.createUser(user2);
        System.out.println(mapper.writeValueAsString(adminDetail1));
        System.out.println(mapper.writeValueAsString(adminDetail2));
        System.out.println(mapper.writeValueAsString(userDetails1));
        System.out.println(mapper.writeValueAsString(userDetails2));
        LoginForm loginForm = Data.getLoginForm("UA001", "password");
        loginController.loginUser(loginForm);

        DeviceForm deviceForm1 = Data.getDeviceForm("A2658");
        DeviceForm deviceForm2 = Data.getDeviceForm("B2658");

        userController.addDeviceLoggedInUser(deviceForm1);
        userController.addDeviceLoggedInUser(deviceForm2);
//        InputStream resource = new ClassPathResource("ggpaString").getInputStream();
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource))) {
//            List<String> ggpas = reader.lines().collect(Collectors.toList());
//            for(String ggpa : ggpas){
//                userController.addGpggaPoint(ggpa);
//            }
//        }
//        InputStream resource = new ClassPathResource("ggpaString").getInputStream();
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource))) {
//            List<String> ggpas = reader.lines().collect(Collectors.toList());
//            LocalDateTime startingTime = LocalDateTime.now().minusSeconds(30);
//            for(int i=0; i<10; i++){
//                userController.addGpggaPoint(ggpas.get(i), startingTime.plusSeconds(i));
//            }
//
//            for(int i=10; i<20; i++){
//                userController.addGpggaPoint(ggpas.get(i),startingTime.plusSeconds(i+15));
//            }
//        } catch (ApiException e) {
//            throw e;
//        }

    }
}