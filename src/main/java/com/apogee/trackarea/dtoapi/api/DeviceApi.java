package com.apogee.trackarea.dtoapi.api;


import com.apogee.trackarea.db.dao.DeviceDao;
import com.apogee.trackarea.db.pojo.DevicePojo;
import com.apogee.trackarea.db.pojo.PointPojo;
import com.apogee.trackarea.db.pojo.ReportPojo;
import com.apogee.trackarea.exceptions.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DeviceApi extends AbstractApi<DevicePojo, Long, DeviceDao> {

    @Autowired
    private PointApi pointApi;

    @Transactional(rollbackFor = ApiException.class)
    public void addReportAndDeletePoints(Long deviceId, ReportPojo report, List<PointPojo> points) throws ApiException {
        DevicePojo device = getCheckById(deviceId);
        device.getReports().add(report);
        pointApi.deleteAllPoints(points);
    }
}
