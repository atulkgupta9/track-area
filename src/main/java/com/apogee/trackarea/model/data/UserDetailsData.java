package com.apogee.trackarea.model.data;


import com.apogee.trackarea.db.pojo.UserPojo;
import lombok.Data;

import java.util.List;

@Data
public class UserDetailsData {
    private List<UserPojo> users;
}
