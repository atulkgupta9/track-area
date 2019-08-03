package com.apogee.trackarea.controller;


import com.apogee.trackarea.db.pojo.UserPojo;
import com.apogee.trackarea.helpers.util.SecurityUtil;
import com.apogee.trackarea.model.data.SingleUserDetailsStatistics;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/common/")
public class CommonController {

    @GetMapping("user-details")
    public SingleUserDetailsStatistics getLoggedInUserDetails() {
        SingleUserDetailsStatistics data = new SingleUserDetailsStatistics();
        UserPojo user = SecurityUtil.currentUser();
        data.setUser(user);
        SingleUserDetailsStatistics.Statistics st = new SingleUserDetailsStatistics.Statistics();
        st.setDeviceCount(user.getDevices().size());
        int ans = user.getDevices().stream().mapToInt(x->x.getReports().size()).sum();
        st.setProjectCount(ans);
        data.setStatistics(st);
        return data;
    }

}
