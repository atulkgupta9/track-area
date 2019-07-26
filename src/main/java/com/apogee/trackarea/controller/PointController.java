package com.apogee.trackarea.controller;

import com.apogee.trackarea.algo.Point;
import com.apogee.trackarea.api.PointApi;
import com.apogee.trackarea.model.HullAreaData;
import com.apogee.trackarea.pojo.PointPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/user/point/")
public class PointController {

    @Autowired
    private PointApi api;

    @GetMapping("")
    public List<PointPojo> getAllPoints(){
        return api.getAllPoints();
    }

    @PostMapping("")
    public void savePoint(@RequestBody PointPojo point){
        api.savePoint(point);
    }

    @GetMapping("{id}")
    public PointPojo getPoint(@PathVariable Integer id){
        return api.getPoint(id);
    }
    @GetMapping("area")
    public double getArea(){
        return api.computeArea();
    }
    @GetMapping("hull")
    public List<Point> getHull(){
        return api.getConvexHullPoints();
    }

    @GetMapping("hull/geo")
    public HullAreaData getHull2(){
        return api.getConvexHullPoints2();
    }
}
