package com.apogee.trackarea.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RegularUserSignUpForm extends LoginForm {

    @NotNull
    private String phone;



}
