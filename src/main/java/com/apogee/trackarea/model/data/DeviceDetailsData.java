package com.apogee.trackarea.model.data;

import com.apogee.trackarea.db.pojo.DevicePojo;
import lombok.Data;

import java.util.List;

@Data
public class DeviceDetailsData {
    private List<DevicePojo> devices;
}
