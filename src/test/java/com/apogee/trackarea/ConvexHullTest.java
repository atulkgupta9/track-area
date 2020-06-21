package com.apogee.trackarea;

import com.apogee.trackarea.helpers.algo.ConvexHull;
import com.apogee.trackarea.helpers.algo.Point;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class ConvexHullTest extends TrackAreaApplicationTests {
    @Test
    public void testEmpty() {
        List<Point> points = Collections.emptyList();
        List<Point> actual = ConvexHull.getHull(points);
        List<Point> expect = Collections.emptyList();
        assertEquals(expect, actual);
    }

    @Test
    public void testOne() {
        List<Point> points = Collections.singletonList(new Point(3, 1));
        List<Point> actual = ConvexHull.getHull(points);
        List<Point> expect = points;
        assertEquals(expect, actual);
    }

    @Test
    public void testTwoDuplicate() {
        List<Point> points = Arrays.asList(new Point(0, 0), new Point(0, 0));
        List<Point> actual = ConvexHull.getHull(points);
        List<Point> expect = Collections.singletonList(new Point(0, 0));
        assertEquals(expect, actual);
    }

    @Test
    public void testTwoHorizontal0() {
        List<Point> points = Arrays.asList(new Point(2, 0), new Point(5, 0));
        List<Point> actual = ConvexHull.getHull(points);
        List<Point> expect = points;
        assertEquals(expect, actual);
    }

    @Test
    public void testTwoHorizontal1() {
        List<Point> points = Arrays.asList(new Point(-6, -3), new Point(-8, -3));
        List<Point> actual = ConvexHull.getHull(points);
        List<Point> expect = Arrays.asList(new Point(-8, -3), new Point(-6, -3));
        assertEquals(expect, actual);
    }

    @Test
    public void testTwoVertical0() {
        List<Point> points = Arrays.asList(new Point(1, -4), new Point(1, 4));
        List<Point> actual = ConvexHull.getHull(points);
        List<Point> expect = points;
        assertEquals(expect, actual);
    }


    @Test
    public void testTwoVertical1() {
        List<Point> points = Arrays.asList(new Point(-1, 2), new Point(-1, -3));
        List<Point> actual = ConvexHull.getHull(points);
        List<Point> expect = Arrays.asList(new Point(-1, -3), new Point(-1, 2));
        assertEquals(expect, actual);
    }

    @Test
    public void testTwoDiagonal0() {
        List<Point> points = Arrays.asList(new Point(-2, -3), new Point(2, 0));
        List<Point> actual = ConvexHull.getHull(points);
        List<Point> expect = points;
        assertEquals(expect, actual);
    }


    @Test
    public void testTwoDiagonal1() {
        List<Point> points = Arrays.asList(new Point(-2, 3), new Point(2, 0));
        List<Point> actual = ConvexHull.getHull(points);
        List<Point> expect = points;
        assertEquals(expect, actual);
    }


    @Test
    public void testRectangle() {
        List<Point> points = Arrays.asList(new Point(-3, 2), new Point(1, 2), new Point(1, -4), new Point(-3, -4));
        List<Point> actual = ConvexHull.getHull(points);
        List<Point> expect = Arrays.asList(new Point(-3, -4), new Point(1, -4), new Point(1, 2), new Point(-3, 2));
        assertEquals(expect, actual);
    }



    /*---- Randomized testing ----*/

    @Test
    public void testHorizontalRandomly() {
        final int TRIALS = 100000;
        for (int i = 0; i < TRIALS; i++) {
            int len = rand.nextInt(30) + 1;
            List<Point> points = new ArrayList<>();
            if (rand.nextBoolean()) {
                double y = rand.nextGaussian();
                for (int j = 0; j < len; j++)
                    points.add(new Point(rand.nextGaussian(), y));
            } else {
                int y = rand.nextInt(20) - 10;
                for (int j = 0; j < len; j++)
                    points.add(new Point(rand.nextInt(30), y));
            }
            List<Point> actual = ConvexHull.getHull(points);
            List<Point> expected = new ArrayList<>();
            expected.add(Collections.min(points));
            if (!Collections.max(points).equals(expected.get(0)))
                expected.add(Collections.max(points));
            assertEquals(expected, actual);
        }
    }


    @Test
    public void testVerticalRandomly() {
        final int TRIALS = 100000;
        for (int i = 0; i < TRIALS; i++) {
            int len = rand.nextInt(30) + 1;
            List<Point> points = new ArrayList<>();
            if (rand.nextBoolean()) {
                double x = rand.nextGaussian();
                for (int j = 0; j < len; j++)
                    points.add(new Point(x, rand.nextGaussian()));
            } else {
                int x = rand.nextInt(20) - 10;
                for (int j = 0; j < len; j++)
                    points.add(new Point(x, rand.nextInt(30)));
            }
            List<Point> actual = ConvexHull.getHull(points);
            List<Point> expected = new ArrayList<>();
            expected.add(Collections.min(points));
            if (!Collections.max(points).equals(expected.get(0)))
                expected.add(Collections.max(points));
            assertEquals(expected, actual);
        }
    }


    private static final Random rand = new Random();


}
