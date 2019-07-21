package com.apogee.trackarea.controller;

import com.apogee.trackarea.algo.Point;
import com.apogee.trackarea.api.PointApi;
import com.apogee.trackarea.pojo.PointPojo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/point/")
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
}
