package com.apogee.trackarea.helpers.algo;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Input: a list P of points in the plane.
 * Precondition: There must be at least 3 points.
 * Sort the points of P by x-coordinate (in case of a tie, sort by y-coordinate).
 * Initialize U and L as empty lists.
 * The lists will hold the vertices of upper and lower hulls respectively.
 * for i = 1, 2, ..., n:
     * while L contains at least two points and the sequence of last two points
     * of L and the point P[i] does not make a counter-clockwise turn:
            *remove the last point from L
     * append P[i] to L
 * for i = n, n-1, ..., 1:
     * while U contains at least two points and the sequence of last two points
     * of U and the point P[i] does not make a counter-clockwise turn:
            * remove the last point from U
     * append P[i] to U
 * Remove the last point of each list (it's the same as the first point of the other list).
 * Concatenate L and U to obtain the convex hull of P.
 * Points in the result will be listed in counter-clockwise order.
 */

public class ConvexHull {

    public static List<Point> getHull(List<Point> points) {
        if (CollectionUtils.isEmpty(points) || points.size() <= 1) {
            return points;
        }
        Collections.sort(points);
        List<Point> upper = new ArrayList<>();
        List<Point> lower = new ArrayList<>();
        for (Point point : points) {
            iteratePoints(lower, point);
        }

        for (int i = points.size() - 1; i >= 0; i--) {
            iteratePoints(upper, points.get(i));

        }
        lower.remove(lower.size() - 1);
        upper.remove(upper.size() - 1);

        if (!(lower.size() == 1 && lower.equals(upper)))
            lower.addAll(upper);
        return lower;

    }

    private static void iteratePoints(List<Point> list, Point p) {
        while (list.size() >= 2 && cross(list.get(list.size() - 2), list.get(list.size() - 1), p) <= 0) {
            list.remove(list.size() - 1);
        }
        list.add(p);
    }

    public static double cross(Point O, Point A, Point B) {
        return (A.x - O.x) * (B.y - O.y) - (A.y - O.y) * (B.x - O.x);
    }


}
