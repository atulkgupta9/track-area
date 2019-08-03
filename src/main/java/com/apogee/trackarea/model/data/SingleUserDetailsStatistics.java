package com.apogee.trackarea.model.data;

import com.apogee.trackarea.db.pojo.UserPojo;
import lombok.Data;

@Data
public class SingleUserDetailsStatistics {
    private UserPojo user;
    private Statistics  statistics;

    @Data
    public static class Statistics{
        private Integer deviceCount ;
        private Integer projectCount;
     }
}
