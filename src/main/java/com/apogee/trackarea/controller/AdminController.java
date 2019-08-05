package com.apogee.trackarea.controller;


import com.apogee.trackarea.db.pojo.UserPojo;
import com.apogee.trackarea.dtoapi.dto.LoginDto;
import com.apogee.trackarea.dtoapi.dto.UserDto;
import com.apogee.trackarea.exceptions.ApiException;
import com.apogee.trackarea.model.data.SingleUserDetails;
import com.apogee.trackarea.model.data.SingleUserDetailsStatistics;
import com.apogee.trackarea.model.data.UserDetailsData;
import com.apogee.trackarea.model.form.UserForm;
import com.apogee.trackarea.model.form.UserSearchForm;
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

    @GetMapping("user/{id}")
    public SingleUserDetailsStatistics getUserData(@PathVariable Long id) throws ApiException {
        SingleUserDetailsStatistics data = new SingleUserDetailsStatistics();
        UserPojo user = userDto.getUserById(id);
        SingleUserDetailsStatistics.Statistics st = new SingleUserDetailsStatistics.Statistics();
        st.setDeviceCount(user.getDevices().size());
        int ans = user.getDevices().stream().mapToInt(x->x.getReports().size()).sum();
        st.setProjectCount(ans);
        data.setStatistics(st);
        data.setUser(user);
        return data;
    }

    @PostMapping("user/search")
    public UserDetailsData searchUserByForm(@Valid @RequestBody  UserSearchForm form) throws ApiException {
        return userDto.searchUserByForm(form);
    }
}
