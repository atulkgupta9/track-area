package com.apogee.trackarea.helpers.util;

import java.time.Duration;
import java.time.LocalDateTime;

public class Helper {
    public static Long timeDifference(LocalDateTime a, LocalDateTime b){
        Duration duration = Duration.between(a,b);
        return duration.getSeconds();
    }
}
