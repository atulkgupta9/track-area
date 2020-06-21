package com.apogee.trackarea.helpers.algo;

import java.util.List;

public class ComputePolygonArea {

    public static double computeArea(List<Point> points) {
        double area = 0.0;
        if (points == null || points.isEmpty()) {
            return area;
        }
        int n = points.size();
        int j = n - 1;
        for (int i = 0; i < n; i++) {
            area += ((points.get(j).x + points.get(i).x) * (points.get(j).y - points.get(i).y));
            j = i;
        }
        return Math.abs(area / 2.0);
    }
}
