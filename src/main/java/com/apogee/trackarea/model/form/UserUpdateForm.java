package com.apogee.trackarea.model.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserUpdateForm {
    @NotNull
    private UserProfileForm userProfile;

    @NotNull
    private DeviceForm deviceForm;



}
