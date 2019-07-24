package com.apogee.trackarea.model;


import com.apogee.trackarea.algo.Point;
import lombok.Data;

import java.util.List;

@Data
public class HullAreaData {
    private List<Point> polygon ;
    private Double area;
}
