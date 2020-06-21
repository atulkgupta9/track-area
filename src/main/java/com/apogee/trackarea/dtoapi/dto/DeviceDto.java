package com.apogee.trackarea.dtoapi.dto;

import com.apogee.trackarea.controller.LoginController;
import com.apogee.trackarea.db.pojo.*;
import com.apogee.trackarea.dtoapi.api.DeviceApi;
import com.apogee.trackarea.dtoapi.api.PdfApi;
import com.apogee.trackarea.dtoapi.api.UserApi;
import com.apogee.trackarea.exceptions.ApiException;
import com.apogee.trackarea.exceptions.ApiStatus;
import com.apogee.trackarea.helpers.algo.ComputePolygonArea;
import com.apogee.trackarea.helpers.algo.ConvexHull;
import com.apogee.trackarea.helpers.util.GeoConvert;
import com.apogee.trackarea.helpers.util.Helper;
import com.apogee.trackarea.helpers.util.SecurityUtil;
import com.apogee.trackarea.model.data.DeviceDetailsData;
import com.apogee.trackarea.model.data.ReportDetailsData;
import com.apogee.trackarea.model.form.DeviceForm;
import com.apogee.trackarea.model.form.LoginForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.DocumentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
    public final Double ACCEPTABLE = 120.0;


    @Autowired
    private DeviceApi deviceApi;

    @Autowired
    private UserApi userApi;

    @Autowired
    private PdfApi pdfApi;


    @Autowired
    private LoginController loginController;

    @Autowired
    private ObjectMapper objectMapper;

    public void runJob() throws DocumentException, ApiException, IOException {
        log.info("Starting Scheduler at : {}", LocalDateTime.now());
        LocalDateTime startTime = LocalDateTime.now();
        List<UserPojo> users = userApi.getAllEntities();
        for (UserPojo user : users) {
            List<DevicePojo> devices = user.getDevices();
            for (DevicePojo device : devices) {
                processPoints(user.getUserProfile(), device.getDeviceId());
            }
        }
        log.info("Time taken to complete the scheduler process : {}", Helper.timeDifference(startTime, LocalDateTime.now()));

    }
    //Get All points by device
    //Create corresponding reports for those points
    //create reports pdf store in aws
    //return results

    public ReportDetailsData getAllReportsById(Long deviceId) throws ApiException {
        login();
        if (!checkIfValidDevice(deviceId)) {
            throw new ApiException(ApiStatus.BAD_DATA, "This device does not belong to you");
        }
        ReportDetailsData reportDetailsData = new ReportDetailsData();
        List<ReportPojo> reports = getAllReportsByDevice(deviceId);
        reportDetailsData.setReports(reports);
        return reportDetailsData;
    }

    public List<ReportPojo> processPoints(UserProfilePojo userProfile, Long deviceId) throws ApiException, IOException, DocumentException {
        login();
        List<PointPojo> points = getSortedPoints(deviceApi.getCheckById(deviceId).getPoints());
        LocalDateTime startTime = LocalDateTime.now();
        log.info("Getting clusters for device {} ", deviceId);
        List<List<PointPojo>> allList = getClusters(points);
        log.info("Total clusters found were : {}, Time taken : {} ", allList.size(), Helper.timeDifference(startTime, LocalDateTime.now()));
        startTime = LocalDateTime.now();
        List<ReportPojo> reports = getReportsFromClusters(userProfile, allList, deviceId);
        log.info("Total reports generated are : {}, Time taken : {} ", reports.size(), Helper.timeDifference(startTime, LocalDateTime.now()));
        return reports;
    }

    public DeviceDetailsData getDevicesLoggedInUser() {
        login();
        List<DevicePojo> devices = SecurityUtil.currentUser().getDevices();
        DeviceDetailsData data = new DeviceDetailsData();
        data.setDevices(devices);
        return data;
    }

    @Transactional
    public DeviceDetailsData addDeviceLoggedInUser(DeviceForm deviceForm) throws ApiException {
        login();
        UserPojo user = SecurityUtil.currentUser();
        DevicePojo newDevice = new DevicePojo();
        newDevice.setDeviceImei(deviceForm.getDeviceImei());
        userApi.update(user.getUserId(), newDevice);

        DeviceDetailsData userDetailsData = new DeviceDetailsData();
        userDetailsData.setDevices(user.getDevices());
        return userDetailsData;
    }

    public void addGpggaPoint(String gpgga) throws ApiException {
        login();
        DevicePojo device = SecurityUtil.currentUser().getDevices().get(0);
        PointPojo point = validateAndPreprocessGpggaString(gpgga);
        deviceApi.updateDevice(device.getDeviceId(), point);
    }

    private void login() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null != authentication && !("anonymousUser").equals(authentication.getName())) {
            return;
        }

        LoginForm loginForm = new LoginForm();
        loginForm.setUsername("UA001");
        loginForm.setPassword("password");
        loginController.loginUser(loginForm);
    }

    private PointPojo validateAndPreprocessGpggaString(String gpgga) throws ApiException {
        try {
            //gpgga = "$GPGGA,142202.00,2232.7794629,N,07255.6007712,E,4,25,0.5,54.7268,M,-57.702,M,01,0001*4D";

            //22.52 N -> latitude
            //72.92 E -> longitude

            String[] split = gpgga.split(",");
            double lat = Double.parseDouble(split[2]); //22.32N
            double lon = Double.parseDouble(split[4]); //72.55E
            lat = convertToDegrees(lat);
            lon = convertToDegrees(lon);

            PointPojo point = new PointPojo();
            point.setLat(lat);
            point.setLon(lon);

            double[] ans = GeoConvert.toUtm(lon, lat);
            point.setUtmEasting(ans[0]);
            point.setUtmNorthing(ans[1]);
            point.setGpgga(gpgga);
            return point;
        } catch (Exception e) {
            log.info("Error occured while validating and transforming GPGGA String : ", e);
            throw new ApiException(ApiStatus.BAD_DATA, "Could not add point " + e.getMessage());
        }
    }

    private double convertToDegrees(double lat) {
        lat = lat * 0.01;
        return (Math.floor(lat) * 60 + ((lat - Math.floor(lat)) * 100)) / 60;
    }

    private boolean acceptable(PointPojo a, PointPojo b) {
        return (a.getCreatedAt().toEpochSecond(ZoneOffset.UTC) - b.getCreatedAt().toEpochSecond(ZoneOffset.UTC)) <= ACCEPTABLE;
    }

    private boolean checkIfValidDevice(Long deviceId) {
        List<Long> devices = SecurityUtil.currentUser().getDevices().stream().map(DevicePojo::getDeviceId).collect(Collectors.toList());
        return devices.contains(deviceId);
    }

    private List<ReportPojo> getAllReportsByDevice(Long deviceId) {
        return deviceApi.getById(deviceId).getReports();
    }

    private boolean lastPointIsActive(PointPojo point) {
        return (LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) - point.getCreatedAt().toEpochSecond(ZoneOffset.UTC)) <= ACCEPTABLE;
    }

    private List<PointPojo> getSortedPoints(List<PointPojo> points) {
        points.sort((o1, o2) -> {
            if (o1.getCreatedAt().isBefore(o2.getCreatedAt())) {
                return -1;
            } else if (o1.getCreatedAt().equals(o2.getCreatedAt())) {
                return 0;
            } else {
                return 1;
            }
        });
        return points;
    }

    private List<List<PointPojo>> getClusters(List<PointPojo> points) {
        List<List<PointPojo>> allList = new ArrayList<>();
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
        return allList;
    }

    private List<ReportPojo> getReportsFromClusters(UserProfilePojo userProfile, List<List<PointPojo>> allList, Long deviceId) throws IOException, DocumentException, ApiException {
        log.info("allList :  {} ", objectMapper.writeValueAsString(allList));

        List<ReportPojo> reports = new ArrayList<>();
        if (!checkForValidList(allList)) {
            return reports;
        }
        for (List<PointPojo> list : allList) {
            if (list.isEmpty()) {
                continue;
            }
            int lsize = list.size();
            if (lastPointIsActive(list.get(lsize - 1))) {
                continue;
            }
            //make point from pointpojo and get their hull
            List<com.apogee.trackarea.helpers.algo.Point> tmp = list.stream().map(o -> new com.apogee.trackarea.helpers.algo.Point(o.getUtmNorthing(), o.getUtmEasting())).collect(Collectors.toList());
            List<com.apogee.trackarea.helpers.algo.Point> hull = ConvexHull.getHull(tmp);

            LocalDateTime startTime = LocalDateTime.now();
            //write to awsS3
            Double calculatedArea = ComputePolygonArea.computeArea(hull);
            ReportPojo report = new ReportPojo();
            report.setCalculatedArea(calculatedArea);
            report.setStartTime(list.get(0).getCreatedAt());
            report.setEndTime(list.get(lsize - 1).getCreatedAt());
            report.setActualPointsCaptured(lsize);
            report.setAreaPointsCaptured(hull.size());
            report.setStartGeoCoordinate(String.format("%.6f, %.6f", list.get(0).getLat(), list.get(0).getLon()));
            report.setEndGeoCoordinate(list.get(lsize - 1).getLat() + " N, " + list.get(lsize - 1).getLon() + " E");
            log.info("Writing file to Aws : {}", startTime);
            deviceApi.addReport(deviceId, report);
            String awsS3Url = pdfApi.writeFileToAwsS3(deviceApi.getById(deviceId).getDeviceImei(), hull, report, userProfile);
            log.info("Time Taken : {}, Pdf Url : {} ", Helper.timeDifference(startTime, LocalDateTime.now()), awsS3Url);
            deviceApi.updateReport(deviceId, report, awsS3Url);

            deviceApi.addReportAndDeletePoints(list);
            reports.add(report);
            log.info("Reports : {} ", objectMapper.writeValueAsString(reports));
        }
        return reports;
    }

    private boolean checkForValidList(List<List<PointPojo>> list) {
        return !CollectionUtils.isEmpty(list);
    }
}
