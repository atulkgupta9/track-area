package com.apogee.trackarea;

import static org.junit.Assert.assertEquals;

import com.apogee.trackarea.algo.ComputePolygonArea;
import com.apogee.trackarea.algo.ConvexHull;
import com.apogee.trackarea.algo.Point;
import java.util.Arrays;
import java.util.List;
import javax.xml.ws.Action;
import org.junit.Test;

public class ComputePolygonAreaTest {
    @Test
    public void testAreaOfRectangle(){
        List<Point> points = Arrays.asList(new Point(-3, 2), new Point(1, 2), new Point(1, -4), new Point(-3, -4));
        List<Point> actual = ConvexHull.makeHull(points);
        double ans = ComputePolygonArea.computeArea(actual);
        assertEquals(24, ans, 0.0);
    }

    @Test
    public void testAreaOfTriangle(){
        List<Point> points = Arrays.asList(new Point(-3, 2), new Point(1, 2), new Point(0,0));
        List<Point> actual = ConvexHull.makeHull(points);
        double ans = ComputePolygonArea.computeArea(actual);
        assertEquals(4, ans, 0.0);
    }


}

