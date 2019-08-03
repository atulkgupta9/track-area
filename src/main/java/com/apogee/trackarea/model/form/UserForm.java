package com.apogee.trackarea.model.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserForm extends LoginForm {

    @NotNull
    private String phone;
    @NotNull
    private String device;
    @NotNull
    private UserProfileForm userProfile;
}
