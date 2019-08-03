package com.apogee.trackarea.controller;


import com.apogee.trackarea.db.pojo.UserPojo;
import com.apogee.trackarea.dtoapi.dto.LoginDto;
import com.apogee.trackarea.dtoapi.dto.UserDto;
import com.apogee.trackarea.exceptions.ApiException;
import com.apogee.trackarea.model.data.AdminDetailsData;
import com.apogee.trackarea.model.data.SingleUserDetails;
import com.apogee.trackarea.model.form.AdminForm;
import com.apogee.trackarea.model.form.AdminUpdateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/superadmin/")
public class SuperadminController {

    @Autowired
    private UserDto userDto;

    @Autowired
    private LoginDto loginDto;

    //get all admins
    @GetMapping("admins")
    public AdminDetailsData getAllAdmins(){
        return userDto.getAllAdmins();
    }

    @GetMapping("admin/{adminId}")
    public UserPojo getAdminData(@PathVariable Long adminId) throws ApiException {
        return userDto.getAdminDetails(adminId);
    }

    @PostMapping("admin")
    public SingleUserDetails createAdmin(@Valid @RequestBody AdminForm adminForm) throws ApiException {
        return loginDto.createAdmin(adminForm);
    }
    //update admins
    @PutMapping("admin/{adminId}")
    public void updateAdmin(@PathVariable Long id, @Valid @RequestBody AdminUpdateForm form) throws ApiException {
        userDto.updateAdminDetails(id,form);
    }


}
