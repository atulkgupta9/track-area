package com.apogee.trackarea.model.data;

import com.apogee.trackarea.helpers.constant.UserType;
import lombok.Data;

@Data
public class SingleUserDetails {
    private String username;
    private UserType userType;
}
