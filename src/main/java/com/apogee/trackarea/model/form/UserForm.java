package com.apogee.trackarea.model.form;

import lombok.Data;

@Data
public class UserForm extends LoginForm {

    private UserProfileForm userProfile;
}
