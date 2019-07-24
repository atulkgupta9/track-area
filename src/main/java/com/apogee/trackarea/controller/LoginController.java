package com.apogee.trackarea.controller;


import com.apogee.trackarea.algo.ComputePolygonArea;
import com.apogee.trackarea.algo.ConvexHull;
import com.apogee.trackarea.algo.Point;
import com.apogee.trackarea.api.LoginDto;
import com.apogee.trackarea.api.PointApi;
import com.apogee.trackarea.dto.PointDto;
import com.apogee.trackarea.exceptions.ApiException;
import com.apogee.trackarea.model.*;
import com.apogee.trackarea.pojo.PointPojo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/auth/")
@Slf4j
public class LoginController {


    @Autowired
    private LoginDto loginDto;

    @Autowired
    private PointApi pointApi;

    @Autowired
    private PointDto pointDto;

    @PostMapping("signin")
    public JwtAuthenticationResponse loginUser(@Valid @RequestBody LoginForm form, HttpServletRequest request,  HttpServletResponse response) {
        JwtAuthenticationResponse res = loginDto.loginUser(form);
        Cookie cookie = new Cookie("Bearer", res.getAccessToken());
        cookie.setHttpOnly(true);
        cookie.setSecure(request.isSecure());
        cookie.setPath("/");
        cookie.setMaxAge(1233);
        response.addCookie(cookie);

        return res;
    }

    @PostMapping("signup")
    public void registerUser(@Valid @RequestBody RegularUserSignUpForm form) throws ApiException {
        loginDto.registerUser(form);
    }

    @PostMapping("add-points")
    public HullAreaData addPoints(@RequestBody PointsForm form){
        List<PointsForm.PointForm> points = form.getPoints();
        List<Point> newList = new ArrayList<>();

        for(PointsForm.PointForm point : points){
            try {
                PointPojo z = new PointPojo();
                z.setX(point.getX());
                z.setY(point.getY());
                Point ptr = new Point(point.getX(), point.getY());
                newList.add(ptr);
            }catch (Exception e){
                log.error(e.getMessage());
            }
        }
        HullAreaData ans = new HullAreaData();
        List<Point> hullPoints = ConvexHull.makeHull(newList);
        ans.setPolygon(hullPoints);
        ans.setArea(ComputePolygonArea.computeArea(hullPoints));
        return ans;
    }

    @PostMapping("by-date")
    public List<List<Point>> getHullsForDate(@RequestBody HullQueryForm form){
        return pointDto.processPointsForDate(form.getDate());
    }


    @PostMapping("test")
    public void testStringAdd(@RequestBody String message){
//        message = "$GPGGA,142202.00,2232.7794629,N,07255.6007712,E,4,25,0.5,54.7268,M,-57.702,M,01,0001*4D";
        String split[] = message.split(",");
        double x = Double.parseDouble(split[4]);
        double y = Double.parseDouble(split[2]);

        PointPojo point = new PointPojo();
        point.setX(x);
        point.setY(y);

        pointApi.savePoint(point);
    }
}
