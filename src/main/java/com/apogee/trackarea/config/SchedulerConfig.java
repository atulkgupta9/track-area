package com.apogee.trackarea.config;

import com.apogee.trackarea.dtoapi.dto.DeviceDto;
import com.apogee.trackarea.exceptions.ApiException;
import com.itextpdf.text.DocumentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;


@Configuration
@EnableScheduling
@Slf4j
public class SchedulerConfig {

    @Autowired
    private DeviceDto deviceDto;

    @Scheduled(fixedDelay = 20000)
    public void generateReports() {
        try {
            deviceDto.runJob();
        } catch (ApiException | IOException | DocumentException e) {
            log.error("Exception occurred in running job ", e);
        }
    }
}
