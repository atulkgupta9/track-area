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
    public void addReportAndDeletePoints(List<PointPojo> points)  {
        pointApi.deleteAllPoints(points);
    }

    @Transactional
    public void addReport(Long deviceId, ReportPojo ReportPojo) throws ApiException {
        DevicePojo device = getCheckById(deviceId);
        device.getReports().add(ReportPojo);
    }

    @Transactional
    public void updateReport(Long deviceId, ReportPojo ReportPojo, String awsS3Url) throws ApiException {
        DevicePojo device = getCheckById(deviceId);
        List<ReportPojo> list = device.getReports();
        list.forEach(x-> {
            if(x.getReportId().equals(ReportPojo.getReportId()))
                x.setUrl(awsS3Url);
        });
        device.setReports(list);
    }

    @Transactional
    public void updateDevice(Long deviceId, PointPojo PointPojo) throws ApiException {
        DevicePojo device = getCheckById(deviceId);
        device.getPoints().add(PointPojo);
    }
}
