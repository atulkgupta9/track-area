package com.apogee.trackarea.dtoapi.dto;

import com.apogee.trackarea.db.pojo.DevicePojo;
import com.apogee.trackarea.db.pojo.PointPojo;
import com.apogee.trackarea.db.pojo.ReportPojo;
import com.apogee.trackarea.db.pojo.UserPojo;
import com.apogee.trackarea.dtoapi.api.DeviceApi;
import com.apogee.trackarea.dtoapi.api.PdfApi;
import com.apogee.trackarea.dtoapi.api.PointApi;
import com.apogee.trackarea.dtoapi.api.UserApi;
import com.apogee.trackarea.exceptions.ApiException;
import com.apogee.trackarea.exceptions.ApiStatus;
import com.apogee.trackarea.helpers.algo.ComputePolygonArea;
import com.apogee.trackarea.helpers.algo.ConvexHull;
import com.apogee.trackarea.helpers.algo.Point;
import com.apogee.trackarea.helpers.util.GeoConvert;
import com.apogee.trackarea.helpers.util.SecurityUtil;
import com.apogee.trackarea.model.data.DeviceDetailsData;
import com.apogee.trackarea.model.data.ReportDetailsData;
import com.apogee.trackarea.model.form.DeviceForm;
import com.itextpdf.text.DocumentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DeviceDto {

    //depends on our case  for the purpose of demo I think 10 seconds is enough
    //in real time it is 900 seconds
    public final Double ACCEPTABLE = 10.0;


    @Autowired
    private PointApi pointApi;

    @Autowired
    private DeviceApi deviceApi;

    @Autowired
    private UserApi userApi;

    @Autowired
    private PdfApi pdfApi;

    //Get All points by device 
    //Create corresponding reports for those points
    //create reports pdf store in aws
    //return results
    
    public ReportDetailsData getAllReportsById(Long deviceId) throws ApiException {
        if(!checkIfValidDevice(deviceId)){
            throw new ApiException(ApiStatus.BAD_DATA, "This device does not belong to you");
        }
        ReportDetailsData reportDetailsData = new ReportDetailsData();
        List<ReportPojo> reports = getAllReportsByDevice(deviceId);
        reportDetailsData.setReports(reports);
        return reportDetailsData;
    }

    public List<ReportPojo> processPoints(Long deviceId) throws ApiException, IOException, DocumentException {
        List<List<PointPojo>> allList = new ArrayList<>();
        List<PointPojo> points = deviceApi.getCheckById(deviceId).getPoints();
        points.sort((o1, o2) -> {
            if(o1.getCreatedAt().isBefore(o2.getCreatedAt())){
                return -1;
            }else if(o1.getCreatedAt().equals(o2.getCreatedAt())){
                return 0;
            }
            else{
                return 1;
            }
        });
        int n = points.size();
        int i = 0;
        //TODO replace with binary search in later steps
        while (i < n) {
            List<PointPojo> temp = new ArrayList<>();
            int j = i;
            temp.add(points.get(j));
            while (j + 1 < n && acceptable(points.get(j + 1), points.get(j))) {
                temp.add(points.get(j + 1));
                j++;
            }
            allList.add(temp);
            i = j + 1;
        }

        List<ReportPojo> reports  = new ArrayList<>();
        for(List<PointPojo> list : allList){
            if(list.isEmpty()){
                continue;
            }
            int lsize = list.size();
            if( lastPointIsActive(list.get(lsize-1))){
                continue;
            }
            //make point from pointpojo and get their hull
            List<Point> tmp = list.stream().map(o -> new Point(o.getUtmNorthing(), o.getUtmEasting())).collect(Collectors.toList());
            List<Point> hull = ConvexHull.makeHull(tmp);

            //write to awsS3
            String awsS3Url = pdfApi.writeFileToAwsS3(hull);
            Double calculatedArea = ComputePolygonArea.computeArea(hull);
            ReportPojo report = new ReportPojo();
            report.setCalculatedArea(calculatedArea);
            report.setStartTime(list.get(0).getCreatedAt());
            report.setEndTime(list.get(list.size()-1).getCreatedAt());
            report.setActualPointsCaptured(list.size());
            report.setAreaPointsCaptured(hull.size());
            report.setDevice(deviceApi.getById(deviceId));
            report.setUrl(awsS3Url);
            report.setStartGeoCordinate(list.get(0).getLat() + "N, " + list.get(0).getLon() + "E");
            report.setStartGeoCordinate(list.get(list.size()-1).getLat() + "N, " + list.get(list.size()-1).getLon() + "E");
            deviceApi.addReportAndDeletePoints(deviceId, report, list);
            reports.add(report);
        }
        return reports;
    }

    public DeviceDetailsData getDevicesLoggedInUser() {
        List<DevicePojo> devices = SecurityUtil.currentUser().getDevices();
        DeviceDetailsData data = new DeviceDetailsData();
        data.setDevices(devices);
        return data;
    }

    @Transactional
    public DeviceDetailsData addDeviceLoggedInUser(DeviceForm deviceForm) throws ApiException {
        UserPojo user = SecurityUtil.currentUser();
        List<DevicePojo> devices = user.getDevices();
        DevicePojo newDevice = new DevicePojo();
        newDevice.setDeviceImei(deviceForm.getDeviceImei());
        newDevice.setUser(user);
        devices.add(newDevice);
        deviceApi.saveEntity(newDevice);
        user.setDevices(devices);
        DeviceDetailsData data = new DeviceDetailsData();
        data.setDevices(user.getDevices());
        return data;

    }

    public void addGpggaPoint(String gpgga) throws ApiException {
        DevicePojo device = SecurityUtil.currentUser().getDevices().get(0);
        PointPojo point = validateAndPreprocessGpggaString(device.getDeviceId(), gpgga);
        pointApi.savePoint(point);
    }

    private PointPojo validateAndPreprocessGpggaString(Long deviceId, String gpgga) throws ApiException {
        try {
            //gpgga = "$GPGGA,142202.00,2232.7794629,N,07255.6007712,E,4,25,0.5,54.7268,M,-57.702,M,01,0001*4D";

            //22.52 N -> latitude
            //72.92 E -> longitude

            String split[] = gpgga.split(",");
            double lat  = Double.parseDouble(split[2]); //22.32N
            double lon = Double.parseDouble(split[4]); //72.55E
            lat = convertToDegrees(lat);
            lon = convertToDegrees(lon);

            PointPojo point = new PointPojo();
            point.setLat(lat);
            point.setLon(lon);

            double[]ans =  GeoConvert.toUtm(lon,lat);
            point.setUtmEasting(ans[0]);
            point.setUtmNorthing(ans[1]);
            point.setGpgga(gpgga);
            point.setDevice(deviceApi.getById(deviceId));
            return point;
        }catch (Exception e){
            log.info("Error occured while validating and transforming GPGGA String : ", e);
            throw new ApiException(ApiStatus.BAD_DATA, "Could not add point " + e.getMessage());
        }
    }

    private double convertToDegrees(double lat){
        lat = lat*0.01;
        return  (Math.floor(lat)*60+ ((lat-Math.floor(lat))*100) )/60;
    }

    private boolean acceptable(PointPojo a, PointPojo b) {
        return (a.getCreatedAt().toEpochSecond(ZoneOffset.UTC) - b.getCreatedAt().toEpochSecond(ZoneOffset.UTC)) <= ACCEPTABLE;
    }

    private boolean checkIfValidDevice(Long deviceId){
        List<Long> devices =  SecurityUtil.currentUser().getDevices().stream().map(x->x.getDeviceId()).collect(Collectors.toList());
        return devices.contains(deviceId);
    }

    private List<ReportPojo> getAllReportsByDevice(Long deviceId) {
        return deviceApi.getById(deviceId).getReports();
    }

    private boolean lastPointIsActive(PointPojo pointPojo) {
        return (LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)- pointPojo.getCreatedAt().toEpochSecond(ZoneOffset.UTC))<=ACCEPTABLE;
    }

}
