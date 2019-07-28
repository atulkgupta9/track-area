package com.apogee.trackarea.helpers.util;

import com.apogee.trackarea.model.form.*;

public class Data {

    public static AdminForm getAdminForm(String username, String password) {
        AdminForm adminForm = new AdminForm();
        adminForm.setUsername(username);
        adminForm.setPassword(password);
        return adminForm;
    }

    public static UserForm getUserForm(String username, String password, String phone) {
        UserForm userForm = new UserForm();
        userForm.setPassword(password);
        userForm.setUsername(username);

        UserProfileForm userProfileForm = new UserProfileForm();
        userProfileForm.setDistrict("Gwalior");
        userProfileForm.setBlock("Datia");
        userProfileForm.setVillage("Datia");
        userProfileForm.setPhone(phone);
        userProfileForm.setTractor("UP93-2959");
        userProfileForm.setName("Atul Gupta");
        userForm.setUserProfile(userProfileForm);
        return userForm;
    }

    public static LoginForm getLoginForm(String username, String password) {
        LoginForm loginForm = new LoginForm();
        loginForm.setUsername(username);
        loginForm.setPassword(password);
        return loginForm;
    }

    public static DeviceForm getDeviceForm(String imei) {
        DeviceForm deviceForm = new DeviceForm();
        deviceForm.setDeviceImei(imei);
        return deviceForm;
    }
}
