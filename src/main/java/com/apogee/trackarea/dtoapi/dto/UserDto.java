package com.apogee.trackarea.dtoapi.dto;

import com.apogee.trackarea.db.pojo.UserPojo;
import com.apogee.trackarea.dtoapi.api.UserApi;
import com.apogee.trackarea.exceptions.ApiException;
import com.apogee.trackarea.exceptions.ApiStatus;
import com.apogee.trackarea.helpers.constant.UserType;
import com.apogee.trackarea.model.data.AdminDetailsData;
import com.apogee.trackarea.model.data.UserDetailsData;
import com.apogee.trackarea.model.form.AdminUpdateForm;
import com.apogee.trackarea.model.form.UserUpdateForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDto {

    @Autowired
    private UserApi userApi;

    public UserDetailsData getAllUsers(){
        List<UserPojo> users = userApi.getAllNormalUsers();
        UserDetailsData userDetailsData = new UserDetailsData();
        userDetailsData.setUsers(users);
        return userDetailsData;
    }

    public AdminDetailsData getAllAdmins(){
        List<UserPojo> admins = userApi.getAllAdmins();
        AdminDetailsData adminDetailsData = new AdminDetailsData();
        adminDetailsData.setAdmins(admins);
        return adminDetailsData;
    }

    public void updateAdminDetails(Long userId, AdminUpdateForm form) throws ApiException {
        UserPojo admin = userApi.getCheckById(userId);
        if(!admin.getUserType().equals(UserType.ADMIN)){
            throw new ApiException(ApiStatus.BAD_DATA,"You are not updating admin");
        }
        UserPojo pojo = new UserPojo();
        BeanUtils.copyProperties(form, pojo);
        userApi.update(userId, pojo);
    }

    public void updateUserDetails(Long userId, UserUpdateForm form) throws ApiException {
        UserPojo admin = userApi.getCheckById(userId);
        if(!admin.getUserType().equals(UserType.USER)){
            throw new ApiException(ApiStatus.BAD_DATA,"You are not updating user");
        }
        UserPojo pojo = new UserPojo();
        BeanUtils.copyProperties(form, pojo);
        userApi.update(userId, pojo);
    }

    public UserPojo getAdminDetails(Long adminId) throws ApiException {
        UserPojo admin = userApi.getCheckById(adminId);
        if(!admin.getUserType().equals(UserType.ADMIN)){
            throw new ApiException(ApiStatus.BAD_DATA, "This user is not admin");
        }
        return admin;

    }
}
