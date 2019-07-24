package com.apogee.trackarea.dto;

import com.apogee.trackarea.algo.ConvexHull;
import com.apogee.trackarea.algo.Point;
import com.apogee.trackarea.api.PointApi;
import com.apogee.trackarea.pojo.PointPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PointDto {
    @Autowired
    private PointApi pointApi;

    public final Double ACCEPTABLE = 300.0;

    public List<List<Point>> processPointsForDate(LocalDate date) {
        List<PointPojo> points = pointApi.getPointsForDate(date);

        List<List<PointPojo>> list = new ArrayList<>();
        int n = points.size();
        int i = 0;
        while (i < n) {
            List<PointPojo> temp = new ArrayList<>();
            int j = i;
            temp.add(points.get(j));
            while (j + 1 < n && acceptable(points.get(j + 1), points.get(j))) {
                temp.add(points.get(j + 1));
                j++;
            }
            list.add(temp);
            i = j + 1;
        }

        List<List<Point>> hulls = new ArrayList<>();
        for(List<PointPojo> l : list){
            List<Point> x = l.stream().map(o -> new Point(o.getX(), o.getY())).collect(Collectors.toList());
            List<Point> hull = ConvexHull.makeHull(x);
            hulls.add(hull);
        }
        return hulls;
    }

    private boolean acceptable(PointPojo a, PointPojo b) {
        return (a.getReceivedAt().toEpochSecond(ZoneOffset.UTC) - b.getReceivedAt().toEpochSecond(ZoneOffset.UTC)) <= ACCEPTABLE;
    }
}
