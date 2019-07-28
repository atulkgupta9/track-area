package com.apogee.trackarea.config;

import com.apogee.trackarea.db.pojo.DevicePojo;
import com.apogee.trackarea.db.pojo.UserPojo;
import com.apogee.trackarea.dtoapi.api.UserApi;
import com.apogee.trackarea.dtoapi.dto.DeviceDto;
import com.apogee.trackarea.exceptions.ApiException;
import com.itextpdf.text.DocumentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;


@Configuration
@EnableScheduling
@Slf4j
public class SchedulerConfig {


    @Autowired
    private UserApi userApi;

    @Autowired
    private DeviceDto deviceDto;

    @Scheduled(fixedDelay = 60000)
    public void generateReports() throws ApiException, IOException, DocumentException {
        log.info("Starting Scheduler at : {}", LocalDateTime.now());
        List<UserPojo> users = userApi.getAllEntities();
        for(UserPojo user : users){
            List<DevicePojo> devices = user.getDevices();
            for(DevicePojo device : devices){
                deviceDto.processPoints(device.getDeviceId());
            }
        }
    }
}
