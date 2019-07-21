package com.apogee.trackarea.util;

import com.apogee.trackarea.constant.UserType;
import com.apogee.trackarea.pojo.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public static CustomUserDetails currentUser(){
        return (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static Integer entityId(){
        return currentUser().getEntityId();
    }

    public static UserType userType(){
        return currentUser().getUserType();
    }
}
