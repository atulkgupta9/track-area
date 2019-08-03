package com.apogee.trackarea.controller;


import com.apogee.trackarea.dtoapi.dto.LoginDto;
import com.apogee.trackarea.dtoapi.dto.UserDto;
import com.apogee.trackarea.exceptions.ApiException;
import com.apogee.trackarea.model.data.SingleUserDetails;
import com.apogee.trackarea.model.data.UserDetailsData;
import com.apogee.trackarea.model.form.UserForm;
import com.apogee.trackarea.model.form.UserUpdateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/admin/")
public class AdminController {

    @Autowired
    private UserDto userDto;

    @Autowired
    private LoginDto loginDto;


    @PutMapping("user/{id}")
    public void updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateForm form) throws ApiException {
        userDto.updateUserDetails(id,form);
    }

    @PostMapping("user")
    public SingleUserDetails createUser(@Valid @RequestBody UserForm form) throws ApiException {
        return loginDto.registerUser(form);
    }

    @GetMapping("users")
    public UserDetailsData getUserDetailsData(){
        return userDto.getAllUsers();
    }
}
