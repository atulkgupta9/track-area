package com.apogee.trackarea.api;

import com.apogee.trackarea.algo.ComputePolygonArea;
import com.apogee.trackarea.algo.ConvexHull;
import com.apogee.trackarea.algo.Point;
import com.apogee.trackarea.dao.PointDao;
import com.apogee.trackarea.pojo.PointPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PointApi {

    @Autowired
    private PointDao dao;

    @Transactional(readOnly = true)
    public List<PointPojo> getAllPoints(){
        return dao.findAll();
    }

    @Transactional(readOnly = true)
    public PointPojo getPoint(Integer id){
        Optional<PointPojo> ans =  dao.findById(id);
        if(!ans.isPresent())
            return null;
        return ans.get();
    }

    @Transactional
    public void savePoint(PointPojo pojo){
        dao.save(pojo);
    }

    public double computeArea(){
        List<Point> saved = getConvexHullPoints();
        return ComputePolygonArea.computeArea(saved);
    }

    @Transactional(readOnly = true)
    public List<Point> getConvexHullPoints(){
        List<PointPojo> saved = getAllPoints();
        List<Point>points = saved.stream().map(point -> new Point(point.getX(), point.getY())).distinct().collect(Collectors.toList());
        return ConvexHull.makeHull(points);
    }

    @Transactional(readOnly = true)
    public List<PointPojo> getPointsForDate(LocalDate date){
        LocalDateTime starting = date.atStartOfDay();
        LocalDateTime ending = date.plusDays(1).atStartOfDay();
        return dao.getPointsForDate(starting, ending);
    }

}
