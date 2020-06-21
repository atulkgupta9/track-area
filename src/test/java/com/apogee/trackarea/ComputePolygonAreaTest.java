package com.apogee.trackarea;

import com.apogee.trackarea.helpers.algo.ComputePolygonArea;
import com.apogee.trackarea.helpers.algo.ConvexHull;
import com.apogee.trackarea.helpers.algo.Point;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ComputePolygonAreaTest extends TrackAreaApplicationTests {
    @Test
    public void testAreaOfRectangle() {
        List<Point> points = Arrays.asList(new Point(-3, 2), new Point(1, 2), new Point(1, -4), new Point(-3, -4));
        List<Point> actual = ConvexHull.getHull(points);
        double ans = ComputePolygonArea.computeArea(actual);
        assertEquals(24, ans, 0.0);
    }

    @Test
    public void testAreaOfTriangle() {
        List<Point> points = Arrays.asList(new Point(-3, 2), new Point(1, 2), new Point(0, 0));
        List<Point> actual = ConvexHull.getHull(points);
        double ans = ComputePolygonArea.computeArea(actual);
        assertEquals(4, ans, 0.0);
    }


}

