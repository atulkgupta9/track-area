package com.apogee.trackarea.model.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserForm extends LoginForm {

    @NotNull
    private String phone;
    @NotNull
    private String device;
    @NotNull
    private UserProfileForm userProfile;
}
