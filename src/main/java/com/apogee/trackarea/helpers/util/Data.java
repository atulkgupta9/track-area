package com.apogee.trackarea.helpers.util;

import com.apogee.trackarea.model.form.*;

public class Data {

    public static AdminForm getAdminForm(String password, String phone) {
        AdminForm adminForm = new AdminForm();
        adminForm.setPassword(password);
        adminForm.setPhone(phone);
        return adminForm;
    }

    public static UserForm getUserForm(String name, String password, String phone) {
        UserForm userForm = new UserForm();
        userForm.setPassword(password);
        UserProfileForm userProfileForm = new UserProfileForm();
        userProfileForm.setDistrict("Gwalior");
        userProfileForm.setBlock("Datia");
        userProfileForm.setTractor("UP93-2959");
        userProfileForm.setName(name);
        userForm.setPhone(phone);
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
