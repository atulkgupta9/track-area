package com.apogee.trackarea.api;

import com.apogee.trackarea.algo.ComputePolygonArea;
import com.apogee.trackarea.algo.ConvexHull;
import com.apogee.trackarea.algo.Point;
import com.apogee.trackarea.dao.PointDao;
import com.apogee.trackarea.pojo.PointPojo;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PointApi {

    @Value("${app.file}")
    private String file;

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

    @EventListener(ApplicationReadyEvent.class)
    public void readAndPlotFile() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();
        while(line != null ){
            String[] ans = line.split(",");
            System.out.println(ans[4]+ " " + ans[6]);
//            Point  p = new Point(Double.parseDouble(ans[4]), Double.parseDouble(ans[6]));
            PointPojo pojo = new PointPojo();
            pojo.setX(Double.parseDouble(ans[6]));
            pojo.setY(Double.parseDouble(ans[4]));
            savePoint(pojo);
            line = br.readLine();
        }
    }

}
