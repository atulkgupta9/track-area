package com.apogee.trackarea.helpers.util;

import com.apogee.trackarea.exceptions.ApiException;

import java.time.Duration;
import java.time.LocalDateTime;

public class Helper {
    public static Long timeDifference(LocalDateTime a, LocalDateTime b) {
        Duration duration = Duration.between(a, b);
        return duration.getSeconds();
    }

    public static String getUserSequence(long count) throws ApiException {
        if (count > 999 * 26) {
            return "%";
        }
        Long prefix = count / 1000;
        char pref = 'A';
        pref = (char) (pref + prefix);
        String series = "U" + pref;
        series = series + String.format("%03d", count);
        return series;

    }

    public static String getAdminSequence(long count) {
        String series = "AD";
        series = series + String.format("%03d", count);
        return series;

    }
}
