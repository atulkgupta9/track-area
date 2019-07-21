package com.apogee.trackarea.controller;


import com.apogee.trackarea.api.LoginDto;
import com.apogee.trackarea.exceptions.ApiException;
import com.apogee.trackarea.model.JwtAuthenticationResponse;
import com.apogee.trackarea.model.LoginForm;
import com.apogee.trackarea.model.RegularUserSignUpForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/client/auth/")
public class LoginController {


    @Autowired
    private LoginDto loginDto;

    @PostMapping("signin")
    public JwtAuthenticationResponse loginUser(@Valid @RequestBody LoginForm form) {
        return loginDto.loginUser(form);
    }

    @PostMapping("signup")
    public void registerUser(@Valid @RequestBody RegularUserSignUpForm form) throws ApiException {
        loginDto.registerUser(form);
    }

}
