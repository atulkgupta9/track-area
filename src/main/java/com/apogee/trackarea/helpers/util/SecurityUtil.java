package com.apogee.trackarea.helpers.util;

import com.apogee.trackarea.helpers.constant.UserType;
import com.apogee.trackarea.db.pojo.UserPojo;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public static UserPojo currentUser() {
        return (UserPojo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static UserType userType() {
        return currentUser().getUserType();
    }
}
