package com.apogee.trackarea.controller;

import com.apogee.trackarea.dtoapi.dto.DeviceDto;
import com.apogee.trackarea.exceptions.ApiException;
import com.apogee.trackarea.model.data.DeviceDetailsData;
import com.apogee.trackarea.model.data.ReportDetailsData;
import com.apogee.trackarea.model.form.DeviceForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/user/")
public class UserController {

    @Autowired
    private DeviceDto deviceDto;

    @GetMapping("reports/{deviceId}")
    public ReportDetailsData getAllReportsByDevice(@PathVariable Long deviceId) throws ApiException {
        return deviceDto.getAllReportsById(deviceId);
    }

    @GetMapping("devices")
    public DeviceDetailsData getAllDevicesByUser() {
        return deviceDto.getDevicesLoggedInUser();
    }

    @PostMapping("device")
    public DeviceDetailsData addDeviceLoggedInUser(@Valid @RequestBody DeviceForm deviceForm) throws ApiException {
        return deviceDto.addDeviceLoggedInUser(deviceForm);
    }

    @PostMapping("point")
    public void addGpggaPoint(@RequestBody String gpgga) throws ApiException {
        deviceDto.addGpggaPoint(gpgga);
    }

    public void addGpggaPoint(String s, LocalDateTime plusSeconds) throws ApiException {
        deviceDto.addGpggaPoint(s,plusSeconds);
    }
}
