package com.apogee.trackarea.model.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserProfileForm  {
    @NotNull
    private String district;
    private String block;
    private String village;
    private String tractor;
    private String phone;
    private String address;
    private String name;
}
